import com.google.gson.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class ResponseDataParser {
    private JsonObject jObject;
    private String completePartNumber;
    Gson gson = new Gson();

    public ResponseDataParser(JsonObject jObject, String completePartNumber)
    {
        this.jObject = jObject;
        this.completePartNumber = completePartNumber;
    }

    public AppleStore fetchData(JsonObject jObject, Mono<String> ApiResponse)
    {
        AppleStore store = new AppleStore();

        this.jObject = jObject;
        jObject = gson.fromJson(ApiResponse.toString(), JsonObject.class);

        store.setPickupQuote(getPickupQuote(jObject, completePartNumber));
        store.setProductName(getProductName(jObject, completePartNumber));
        store.setStoreName(getStoreName(jObject));
        store.setPickupStatus(isAvailable(getPickupQuote(jObject,completePartNumber)));

        return store;

    }


    private String getPickupQuote(JsonObject object, String completePartNumber)
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

    private static String getProductName(JsonObject object, String completePartNumber)
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

    private String getStoreName(JsonObject object)
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

    private boolean isAvailable(String pickupQuote)
    {
        return !pickupQuote.equals("Unavailable for Pickup");
    }
}