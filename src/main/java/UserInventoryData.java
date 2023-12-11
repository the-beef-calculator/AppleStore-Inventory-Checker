import java.util.Scanner;

public class UserInventoryData
{
    private String storeNumber, partialPartNumber, completePartNumber;


    public void requestUserInventoryData()
    {
        Scanner scan = new Scanner(System.in);

        System.out.println("What is the store number?");
        setStoreNumber(scan.nextLine());

        System.out.println("What is the part number? (e.g: MQ8R3LL/A)");
        setCompletePartNumber(scan.nextLine());
        setPartialPartNumber(completePartNumber);

        scan.close();

    }

    public String getPartialPartNumber()
    {
        return partialPartNumber;
    }

    public void setPartialPartNumber (String completePartNumber)
    {

        if (completePartNumber.contains("/"))
        {
            this.partialPartNumber = completePartNumber.substring(0, completePartNumber.indexOf('/')); //API requires trailing '/A' to be removed when processing request
        }
        else
        {
            System.out.println("Invalid part number.");
            System.exit(1);
        }

    }

    public String getStoreNumber()
    {
        return storeNumber;
    }

    public void setStoreNumber(String storeNumber)
    {
        storeNumber = storeNumber.replaceAll("\\s", ""); //removes any whitespace if there's any in the input
        this.storeNumber = storeNumber;
    }


    public String getCompletePartNumber()
    {
        return completePartNumber;
    }

    public void setCompletePartNumber(String completePartNumber)
    {
        completePartNumber = completePartNumber.replaceAll("\\s", "");
        this.completePartNumber = completePartNumber;
    }

}
