import com.google.gson.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.chat.v1.service.User;
import com.twilio.type.PhoneNumber;


public class Main {

	public static void main(String[] args) throws JsonIOException, JsonSyntaxException, IOException, InterruptedException, URISyntaxException
	{
		Gson gson = new Gson();
		UserInventoryData user = new UserInventoryData();

		System.out.println("Apple Inventory Checker\n");

		user.requestUserInventoryData();

		System.out.println("Contacting server...");

		HttpResponse<String> getResponse = sendRequest(url);

		while (verifyCode(getResponse.statusCode()) == false)
		{
			System.out.println("Retrying in 30 seconds...");
			Thread.sleep(40000);
			getResponse = sendRequest(url);}
	
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
		int errorCodes = 0;
		while (true)
		{
			Thread.sleep(90000);  // wait 1 minute 30 seconds before trying again
			timesChecked++;
			
			System.out.println("Contacting server...");
			
			HttpResponse<String> getResponse2 = sendRequest(url);
			
			if (verifyCode(getResponse2.statusCode()) == false)
			{
				System.out.println("Retrying in 30 seconds...");
				Thread.sleep(30000);
				errorCodes++;
				timesChecked--;
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
			System.out.println("Times Checked: " + timesChecked + "\n" + "The server has returned " + errorCodes + " error codes so far.");	
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
				.timeout(Duration.of(10, ChronoUnit.SECONDS)) // connection will time out after 10 seconds in case the request hangs
				.build();
		
		HttpClient httpClient = HttpClient.newHttpClient();
		
		getResponse = httpClient.send(getRequest, BodyHandlers.ofString());
		
		return getResponse;
		}
		catch(java.net.ConnectException JNC)
		{
			System.out.println("Connection lost... Trying again in 30 seconds.");
			Thread.sleep(30000);
			return sendRequest(url);
			
		}
		catch(java.net.http.HttpTimeoutException HTE)
		{
			System.out.println("Connection timed out... Trying again in 5 seconds.");
			Thread.sleep(5000);
			return sendRequest(url);
		}
		
		
		
		
	}
	
	public static boolean verifyCode(int statusCode)
	{
		
		if (statusCode != 200)
		{
			System.out.println("Server returned error code: " + statusCode + "\n");
			return false;
		}
		else
		{
			System.out.println("Server has completed request. \n");
			return true;
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
