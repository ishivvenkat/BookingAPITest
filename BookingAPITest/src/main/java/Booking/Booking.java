package Booking;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;

import Components.Base;
import io.restassured.RestAssured;

public class Booking extends Base{
	
public static void getAccess_Method(String userName, String passWord) {
		
		RestAssured.baseURI = BASEURI_AUTH;
		
		HashMap<String,String> postUserCredentials = new HashMap<String,String>();
		
		postUserCredentials.put("username",userName);	
		
		postUserCredentials.put("password",passWord);	
		
		apiResponse = given().header("Content-Type","application/json").body(postUserCredentials).when().post();
		
		ANNOTATE("INFO : getAccess_Method 		: Post API Call completed for getting access token. Post Data : [ " + postUserCredentials + " ]");
	}
	
	public static HashMap<String,Object> addNewBooking_Method(String firstName, String lastName, Integer price, boolean payment,String checkIn, String checkOut, String additional, String token) {
		
		RestAssured.baseURI = BASEURI_BOOKING;
		
		HashMap<String,Object> postData = new HashMap<String,Object>();
		
		HashMap<String,Object> dates = new HashMap<String,Object>();
		
		dates.put("checkin",checkIn);
		
		dates.put("checkout",checkOut);
		
		postData.put("firstname",firstName);
		
		postData.put("lastname",lastName);
		
		postData.put("totalprice",price);
		
		postData.put("depositpaid",payment);
		
		postData.put("bookingdates",dates);
		
		postData.put("additionalneeds",additional);
		
		apiResponse = given().header("Content-Type","application/json").auth().oauth2(token).body(postData).when().post();
		
		ANNOTATE("INFO : addNewBooking_Method		: Post API Call completed for adding new Booking. Post Data : [ " + postData + " ]");
		
		return postData;
			
	}
	
	public static HashMap<String, Object> updateBooking_Method(String firstName, String lastName, Integer price, boolean payment,String checkIn, String checkOut, String additional, String token, Integer id) {
		
		RestAssured.baseURI = BASEURI_BOOKING;
		
		RestAssured.basePath = String.valueOf(id);
		
		HashMap<String,Object> postData = new HashMap<String,Object>();
		
		HashMap<String,Object> dates = new HashMap<String,Object>();
		
		dates.put("checkin",checkIn);
		
		dates.put("checkout",checkOut);
		
		postData.put("firstname",firstName);
		
		postData.put("lastname",lastName);
		
		postData.put("totalprice",price);
		
		postData.put("depositpaid",payment);
		
		postData.put("bookingdates",dates);
		
		postData.put("additionalneeds",additional);
		
		apiResponse = given().header("Content-Type","application/json").auth().preemptive().basic("admin", "password123").body(postData).when().put();
		
		ANNOTATE("INFO : updateBooking_Method 		: Put API Call completed for updating Booking. Post Data : [ " + postData + " ]");
		
		ANNOTATE("INFO : updateBooking_Method 		: REQUEST DATA : [ " + postData + " ]");
		
		ANNOTATE("INFO : updateBooking_Method 		: RESPONSE : [ " + apiResponse.asString() + " ]");
		
		ANNOTATE("PASS : updateBooking_Method 		: Booking ID [ " + id + "] updated in API ");
		
		return postData;	
			
	}
	
	public static void getBooking_Method(Integer id) {
		
		RestAssured.baseURI = BASEURI_BOOKING;
		
		RestAssured.basePath = String.valueOf(id);
		
		apiResponse = given().get();
		
		ANNOTATE("PASS : getBooking_Method 		: Response DATA : [ " + apiResponse.asString() + " ]");
	}
	
	public static void deleteBooking_Method(Integer id) {
		
		RestAssured.baseURI = BASEURI_BOOKING;
		
		RestAssured.basePath = String.valueOf(id);
		
		apiResponse = given().auth().preemptive().basic("admin", "password123").delete();
		
		ANNOTATE("INFO : deleteBooking_Method 		: Delete API Call completed for deleting the Booking. Booking ID : [ " + id + " ]");
		
		ANNOTATE("PASS : deleteBooking_Method 		: Booking ID [ " + id + "] removed from API ");	
	}
	
	public static void validateStatusCode_Method(int expectedStatusCode) {
		
		int actualStatusCode = apiResponse.getStatusCode();
		
		if (actualStatusCode == expectedStatusCode) {
			
			ANNOTATE("PASS : validateStatusCode_Method 	: Status Code : Expected : [ " + expectedStatusCode + " ] : Actual : [ " + actualStatusCode + " ]");
		
		}	else {
			
			ANNOTATE("FAIL : validateStatusCode_Method 	: Status Code : Expected : [ "+expectedStatusCode+" ] : Actual : [ " + actualStatusCode + " ]");
			
			Assert.fail("FAIL : validateStatusCode_Method 	: Status Code : Expected : [ "+expectedStatusCode+" ] : Actual : [ " + actualStatusCode + " ]");
		}		
		
	}
	
	public static String getAccessToken_Method(String userName) {
		
		String token = apiResponse.path("token");
		
		if (token.trim() != null) {
			
			ANNOTATE("PASS : getAccessToken_Method	 	: AccessToken : Token ID : [ " + token + " ] Generated for the user - [ " + userName + " ]");
			
		} else {
			
			ANNOTATE("FAIL : getAccessToken_Method 		: Authentication	Unsuccessful : Token ID is not generated");
			
			Assert.fail("FAIL : getAccessToken_Method 		: Authentication	Unsuccessful : Token ID is not generated");
		}
		
		return token;
	}
	
	public static Integer getBookingID_Method(HashMap<String,Object> postData) {
		
		Integer id = apiResponse.path("bookingid");
		
		if (id != null) {
			
			ANNOTATE("INFO : getBookingID_Method	 	: REQUEST DATA : [ " + postData + " ]");
					
			ANNOTATE("INFO : getBookingID_Method 		: RESPONSE : [ " + apiResponse.body().asString() + " ]");
			
			ANNOTATE("PASS : getBookingID_Method 		: New Booking : ID [ " + id + "] added to API ");	
			
		} else {
			
			ANNOTATE("FAIL : getBookingID_Method 		: Booking ID is not generated");
			
			Assert.fail("FAIL : getBookingID_Method 		: Booking ID is not generated");
		}
		
		return id;
		
	}

	@SuppressWarnings("unchecked")
	
	public static void validateRequestAndResponseData_Method(HashMap<String, Object> postData) {
		
		JSONParser parser = new JSONParser();
		
		JSONObject responseJson = null;	
		
		try {
			
			 responseJson = (JSONObject) parser.parse(apiResponse.asString());
			
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		
		HashMap<String,Object> responseData = new HashMap<String,Object>();
		
		responseData = (HashMap<String, Object>) responseJson;
		
		verifyFieldvalues_Method(postData, responseData);
		
	}
	
	@SuppressWarnings("unchecked")
	
	public static void validateRequestAndResponseData_Post_Method(HashMap<String, Object> postData) {
		
		HashMap<String,Object> responseData = new HashMap<String,Object>();
		
		responseData = (HashMap<String, Object>) apiResponse.path("booking");
		
		verifyFieldvalues_Method(postData, responseData);
		
	}
	
	@SuppressWarnings("unchecked")
	
	public static void verifyFieldvalues_Method(HashMap<String, Object> exp, HashMap<String, Object> act) {
		
		try {
		
			HashMap<String,Object> subExp = new HashMap<String,Object>();
			
			HashMap<String,Object> subAct = new HashMap<String,Object>();	
			
			for (String key1 : exp.keySet()) {
	
				if (String.valueOf(exp.get(key1)).equals(String.valueOf(act.get(key1)))) {
					
					ANNOTATE("PASS : verifyFieldvalues_Method	: Field Name - [ " + key1 + " ] : Expected Value - [ " + exp.get(key1) + " ] : Actual Value - [ " + exp.get(key1) + " ]");
					
				} else {
	
					subExp = (HashMap<String, Object>) exp.get(key1);
					
					subAct =  (HashMap<String, Object>) act.get(key1);
					
					if (subExp.size() > 0) {
						
						ANNOTATE("INFO : verifyFieldvalues_Method 	: Field Name - [ " + key1 + " ]");
						
						verifyFieldvalues_Method(subExp, subAct);
						
					} else {
						
						ANNOTATE("FAIL : verifyFieldvalues_Method	: Field Name - [ " + key1 + " ] : Expected Value - [ " + exp.get(key1) + " ] : Actual Value - [ " + exp.get(key1) + " ]");
					}
				}
			}	
		
		} catch(Exception error) {
			
			ANNOTATE("FAIL : verifyFieldvalues_Method	: Data posted does not matches with data from API Response. Expectd : " + String.valueOf(exp) + " | Actual : " + String.valueOf(act));
		}
	}

}
