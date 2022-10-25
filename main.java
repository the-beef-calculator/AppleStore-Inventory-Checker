import com.google.gson.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Scanner;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;


public class main {

	public static void main(String[] args) throws JsonIOException, JsonSyntaxException, IOException, InterruptedException, URISyntaxException {
		
	Gson gson = new Gson();
	
	
	System.out.println("Apple Inventory Checker\n");
	
	Scanner scan = new Scanner(System.in);
	
	System.out.println("What is the store number?");
	
	String storeNumber = scan.nextLine();
	
	System.out.println("What is the part number? Enter it without the trailing '/A' at the end. (e.g: MQ8R3LL)");
	
	String partNumber = scan.nextLine();
	
	storeNumber = storeNumber.replaceAll("\\s", ""); //removes any whitespace if there's any in the input
	partNumber = partNumber.replaceAll("\\s", "");
	
	String completePartNumber = partNumber+"/A";  //API returns JSON with full part name, this is needed to reference the correct field name
	
	
	
	String url = "https://www.apple.com/shop/fulfillment-messages?store="+storeNumber+"&parts.0="+partNumber+"%2FA";
	
	System.out.println("Contacting server...");
	
	
	HttpResponse<String> getResponse = sendRequest(url);
	
	verifyCode(getResponse.statusCode()); 
	
	String verifyInputs = getResponse.body();
	
	if(verifyInputs.contains("errorMessage"))
	{
		System.out.println("Server has indicated that either the store number or part number is invalid.");
		System.exit(3);
	}
	
	
	
	System.out.println("\n");

	JsonObject JsonObject = gson.fromJson(getResponse.body(), JsonObject.class);

	AppleStore AppleStore = new AppleStore(setAllObjectValues(JsonObject, completePartNumber));
	
	System.out.println(AppleStore.toString()+"\n");
		
	
	if(AppleStore.isAvailable() == false)
		{
			System.out.println("Enable text message alerts for when the product is in stock? (YES/NO):");
		}
		
	if(scan.nextLine().equalsIgnoreCase("yes") == true)
	{
		System.out.println("The program will now check every few minutes and notify you via SMS when the product is in stock. \n" );
		auth auth = new auth();
		Twilio.init(auth.getACCOUNT_SID(), auth.getAUTH_TOKEN());
		
		int timesChecked = 1;
		while (true)
		{
			Thread.sleep(90000);  // wait 1 minute 30 seconds before trying again
			timesChecked++;
			
			System.out.println("Contacting server...");
			HttpResponse<String> getResponse2 = sendRequest(url);
			verifyCode(getResponse2.statusCode());
			
			if (getResponse2.statusCode() == 503)
			{
				System.out.println("Server returned error 503. Retrying in 30 seconds...");
				Thread.sleep(30000);
				continue;
			}
			
			

			JsonObject JsonObject2 = gson.fromJson(getResponse.body(), JsonObject.class);
			
			AppleStore.setAllValues(setAllObjectValues(JsonObject2, completePartNumber));
			
			
			if (AppleStore.isAvailable() == true)
			{
				System.out.println("Product is now available!");
				Message message = Message.creator(new PhoneNumber(auth.getMY_PHONE_NUMBER()), new PhoneNumber(auth.getTWILIO_PHONE()), 
						"The requested product " + AppleStore.getProductName() + " is now available at the " + AppleStore.getStoreName()
						+ " location! The program checked Apple's website a total of " + timesChecked + " times.")
						.create();
				
				System.out.println("SMS has now been sent to " + auth.getMY_PHONE_NUMBER()+".");
				System.exit(0);
			}
			System.out.println("Times Checked: " + timesChecked);	
			}
	}
	else
	{
		System.out.print("Thanks. See you next time!");
		System.exit(0);
	}
	
	}

	public static HttpResponse<String> sendRequest(String url) throws IOException, InterruptedException, URISyntaxException
	{
		HttpResponse<String> getResponse;
		
		try {
		HttpRequest getRequest = HttpRequest.newBuilder()
				.uri(new URI(url))
				.GET()
				.build();
		
		HttpClient httpClient = HttpClient.newHttpClient();
		
		getResponse = httpClient.send(getRequest, BodyHandlers.ofString());
		
		return getResponse;
		}
		catch(java.net.ConnectException JNC)
		{
			System.out.println("Connection Error... Trying again in 30 seconds.");
			Thread.sleep(30000);
			return sendRequest(url);
			
		}
		
		
		
		
	}
	
	public static void verifyCode(int statusCode)
	{
		if (statusCode == 503)
		{
			System.out.println("Server returned error code 503. Retrying in 30 seconds...");
			return;
			
		}
		if (statusCode != 200)
		{
			System.out.println("Server returned an error code.");
			System.out.println("Error Code: " + statusCode);
			System.exit(2);
		}
		else
		{
			System.out.println("Server has completed request.");
		}
	}
	
	
	public static String getPickupQuote(JsonObject object, String completePartNumber)
	{
		JsonElement objectElement = object.get("body")
				.getAsJsonObject().get("content")
				.getAsJsonObject().get("pickupMessage")
				.getAsJsonObject()
				.getAsJsonArray("stores").get(0)
				.getAsJsonObject().get("partsAvailability")
				.getAsJsonObject().get(completePartNumber)
				.getAsJsonObject().get("pickupSearchQuote");
				String pickupQuote = objectElement.getAsString();
				
				
				return pickupQuote;
	}
	
	public static String getProductName(JsonObject object, String completePartNumber)
	{
		JsonElement objectElement = object.get("body")
				.getAsJsonObject().get("content")
				.getAsJsonObject().get("pickupMessage")
				.getAsJsonObject()
				.getAsJsonArray("stores").get(0)
				.getAsJsonObject().get("partsAvailability")
				.getAsJsonObject().get(completePartNumber)
				.getAsJsonObject().get("messageTypes")
				.getAsJsonObject().get("regular")
				.getAsJsonObject().get("storePickupProductTitle");
		
		String productName = objectElement.getAsString();
		
		return productName;
	}
	
	public static String getStoreName(JsonObject object)
	{
		JsonElement objectElement = object.get("body")
				.getAsJsonObject().get("content")
				.getAsJsonObject().get("pickupMessage")
				.getAsJsonObject()
				.getAsJsonArray("stores").get(0)
				.getAsJsonObject().get("storeName");
		
		String storeName = objectElement.getAsString();
		return storeName;
		
			
	}
	
	public static boolean isAvailable(String pickupQuote)
	{
		if (pickupQuote.equals("Unavailable for Pickup"))
		{
			return false;
		}
		
		return true;
	}
	
	public static AppleStore setAllObjectValues(JsonObject object, String completePartNumber)
	{
		AppleStore AppleStore = new AppleStore();
		
		AppleStore.setPickupQuote(getPickupQuote(object,completePartNumber));
		
		AppleStore.setProductName(getProductName(object,completePartNumber));
		
		AppleStore.setStoreName(getStoreName(object));
		
		AppleStore.setPickupStatus(isAvailable(getPickupQuote(object,completePartNumber)));
		
		
		return AppleStore;
	}
	
	
	
}	
