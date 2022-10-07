# AppleStore-Inventory-Checker
Program that contact's Apple's API and checks to see if a product is in-stock. Will notify via SMS when there is!


# Libraries Used / Important Notes

This program utilizes GSON, Google's JSON Serializer/Deserializer library. In order for this program to work, you must have the Maven or Gradle dependency
added. 

This program also uses the Twilio library in order to send SMS text messages. In order to use their service, you must sign up for an account at https://www.twilio.com/ and use the authentication codes they give you. 

The auth.java file will contain all your authentication details with twilio, so make sure you update your information there so that text message functionality will work. 

