# AppleStore-Inventory-Checker
Program that contact's Apple's API and checks to see if a product is in-stock. Will notify via SMS when there is!


# Libraries Used / Important Notes

This program utilizes GSON, Google's JSON Serializer/Deserializer library. In order for this program to work, you must have the Maven or Gradle dependency
added. 

This program also uses the Twilio library in order to send SMS text messages. In order to use their service, you must sign up for an account at https://www.twilio.com/ and use the authentication codes they give you, as well as adding their respectred Maven
/Gradle dependencies

The auth.java file will contain all your authentication details with twilio, so make sure you update your information there so that text message functionality will work. 

# Screenshot of the program in action 

<img width="758" alt="Screen Shot 2022-10-13 at 03 36 46" src="https://user-images.githubusercontent.com/77137812/195533965-c90d6a31-f8c1-4f1e-afdc-d3a66fcc5256.png">

# Finding the part number and store number

I do plan on adding a list of all the US apple retail stores, but will limit part numbers only to the most popular purchases (iPhones, iPads, MacBooks and possible Apple Watches) 

## iPhones

### iPhone 14 Pro Max

|  **Deep Purple** | **Silver**         | **Gold**          | **Space Black**   |
| ---------------- | ------------------ | ----------------- | ----------------- |
| 128GB - MQ8R3LL/A| 128GB - MQ8P3LL/A  | 128GB - MQ8Q3LL/A | 128GB - MQ8N3LL/A |
| 256GB - MQ8W3LL/A| 256GB - MQ8U3LL/A  | 256GB - MQ8V3LL/A | 256GB - MQ8T3LL/A |
| 512GB - MQ913LL/A| 512GB - MQ8Y3LL/A  | 512GB - MQ903LL/A | 512GB - MQ8X3LL/A |
| 1TB   - MQ953LL/A| 1TB - MQ933LL/A    | 1TB - MQ943LL/A   | 1TB - MQ923LL/A   |




