package gson;

public class AppleStore {
	private String storeName,productName,pickupQuote;
	private boolean pickupStatus;
	
	
	public AppleStore()
	{
		
	}
	public AppleStore(AppleStore object)
	{
		if (object == null)
		{
			System.out.println("Object is null!");
			System.exit(1);
		}
		this.storeName = object.storeName;
		this.productName = object.productName;
		this.pickupQuote = object.pickupQuote;
		this.pickupStatus = object.pickupStatus;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getPickupQuote() {
		return pickupQuote;
	}
	public void setPickupQuote(String pickupQuote) {
		this.pickupQuote = pickupQuote;
	}
	
	public void setAllValues(AppleStore object)
	{
		if (object == null)
		{
			System.out.println("Object is null!");
			System.exit(1);
		}
		this.storeName = object.storeName;
		this.productName = object.productName;
		this.pickupQuote = object.pickupQuote;
		this.pickupStatus = object.pickupStatus;
	}
	public boolean isAvailable() {
		return pickupStatus;
	}
	public void setPickupStatus(boolean pickupStatus) {
		this.pickupStatus = pickupStatus;
	}
	
	
	public String toString()
	{
		return "Store Name: " + getStoreName() + "\n" + "Requested Product: " + getProductName() + "\n" 
				+ "Pickup Status: " + getPickupQuote();
	}

	
	
	
}
