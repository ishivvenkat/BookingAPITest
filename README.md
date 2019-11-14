# BookingAPITest

Project to test and validate REST API services with Rest Assured & Selenium.

## Getting Started

These instructions will get you the project up and running on your local machine for development and testing purposes.

## Built with

Following are the technologies used to implement the Rest API tests.

* [Java](https://www.java.com/)
* [Rest Assured] (http://rest-assured.io/) - for validating rest services
* [Selenium] (https://www.seleniumhq.org/) - automation framework used
* [TestNG] (https://testng.org/doc/) - testing framework used

### Prerequisites

Things you need to install or run the test.

```
Java JDK 1.8 or higher
Development IDE (Optional)
```

## Running the tests

Steps to run the API test

### Option 1

```
Option 1 is to run the runnable test in any environment using command line 

Steps:
Download and go to BookingAPITest_Runnable folder in command line and execute command java -jar BookingAPITest_Runnable.jar

Output:
1. Verify the /Logs folder for log text files for step by step test execution results.
2. Verify the test-output folder for testNG results - example: emailable-report.html (optional)
```

### Option 2

```
Option 2 is to run or develop the test inside IDE development environment (ex: Eclipse)

Steps:
1. Download and import the BookingAPITest project into development IDE as existing Maven project
2. Right click the testng.xml and choose to execute the test as Run as-> 1 TestNG Suite

Output:
1. Verify the /Logs folder for log text files for step by step test execution results.
2. Verify the test-output folder for testNG results - example: emailable-report.html (optional)
```

## Break down on tests

Test suite contains five tests to verify and validate the Rest API calls and access credentials validations, etc

### Test Case Details
```
Test Name & Description.
AccessToken - Post API Call to fetch the access token for using in the PUT & DELETE API calls
API_Post - Post API call for adding new booking into the Booking Rest API and validate the response
API_Put - PUT API call for updating the booking details into the Booking Rest API and validate the response
API_GET - GET API call to fetch the details using the booking ID created and validate the response
API DELETE - DELETE API call to remove the booking from Rest API and validate the response
```
