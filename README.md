# Libraries Used / Important Notes

This program utilizes GSON, Google's JSON Serializer/Deserializer library. Ensure that you have added the Maven or Gradle dependency for it to function properly.

Additionally, the program utilizes the Twilio library for sending SMS text messages. To use this feature, you need to sign up for an account [here](https://www.twilio.com/) and include the provided authentication codes. Make sure to add the corresponding Twilio Maven/Gradle dependencies.

Please update the auth.java file with your Twilio authentication details to enable text message functionality. Alternatively, you can also read the information from a text file.

It's crucial to note that the API used by this program is undocumented (because this is Apple we're talking about) and subject to potential modifications or changes, which may render the program ineffective.

**Also, please note that currently only US Apple Retail Locations are supported.**

I plan on overhauling this program soon since It's been nearly a year since I wrote this, and I've learned much more :) 

# How to Use

1. Clone this repository.
2. If you plan on enabling text message notifications, sign up for a twilio account [here.](https://www.twilio.com/)
3. Enter your twilio information into the `auth.java` file.*
4. Run the program. 
5. Keep your computer awake with a stable internet connection.**

<small>**Note: Hard coding sensitive API information is an unsafe coding practice and is generally frowned upon. This will be updated soon!*<\small>


***Note: I've written exceptions for many of the possible issues (such as a loss of internet connection), though this is generally inefficient and I've learned is better suited towards utilizating the Spring framework, which I'll work to incorporate this project soon.*


# Screenshot of the program in action 

<img width="758" alt="Screen Shot 2022-10-13 at 03 36 46" src="https://user-images.githubusercontent.com/77137812/195533965-c90d6a31-f8c1-4f1e-afdc-d3a66fcc5256.png">

# Finding the part number and store number

I do plan on adding a list of all the US apple retail stores, but will limit part numbers only to the most popular purchases (iPhones, iPads, MacBooks and possible Apple Watches) 

## iPhones (US Only)

### iPhone 14 Pro Max

|  **Deep Purple** | **Silver**         | **Gold**          | **Space Black**   |
| ---------------- | ------------------ | ----------------- | ----------------- |
| 128GB - MQ8R3LL/A| 128GB - MQ8P3LL/A  | 128GB - MQ8Q3LL/A | 128GB - MQ8N3LL/A |
| 256GB - MQ8W3LL/A| 256GB - MQ8U3LL/A  | 256GB - MQ8V3LL/A | 256GB - MQ8T3LL/A |
| 512GB - MQ913LL/A| 512GB - MQ8Y3LL/A  | 512GB - MQ903LL/A | 512GB - MQ8X3LL/A |
| 1TB   - MQ953LL/A| 1TB - MQ933LL/A    | 1TB - MQ943LL/A   | 1TB - MQ923LL/A   |

### iPhone 14 Pro

|  **Deep Purple** | **Silver**         | **Gold**          | **Space Black**   |
| ---------------- | ------------------ | ----------------- | ----------------- |
| 128GB - MQ0E3LL/A| 128GB - MQ003LL/A  | 128GB - MQ063LL/A | 128GB - MPXT3LL/A |
| 256GB - MQ1D3LL/A| 256GB - MQ0X3LL/A | 256GB - MQ163LL/A | 256GB - MQ0N3LL/A |
| 512GB - MQ273LL/A| 512GB - MQ1U3LL/A  | 512GB - MQ213LL/A | 512GB - MQ1K3LL/A |
| 1TB   - MQ303LL/A| 1TB - MQ2L3LL/A    | 1TB - MQ2T3LL/A   | 1TB - MQ2E3LL/A   |






