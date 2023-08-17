package PostAPIs;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class OAuth2Test {
	
	/*	Amadeus - OAuth2.0 Workflow Automation from Postman
	 * 	
	 * 1: Here the catch is that the contentType is not contentType.JSON, the header here is [Content-Type = application/x-www-form-urlencoded]
	 * 	  Because the body for this OAuth2 flow is X-www-form-urlencoded.
	 * 2: Here we have form parameters [grant-type, client-id, client-secret] that needs to be supplied.
	 * 	  So, we will use formParam() method to supply them as key-value pair
	 * 
	 * In order to not worry about the expiration of access tokens we will create @BeforeMethod annotation that will
	 * run before each and every @Test method. and every time will create a new Access Token. and now even in your real life projects u can
	 * have as many @Test methods as you want.
	 * --> Do not try to return anything in your testNG annotations method, it is a better idea or practice
	 * --> to create a global static variable like "static String accessToken"
	 * 
	 */
	static String accessToken;
	
	@BeforeMethod
	public void getAccessToken() {
		RestAssured.baseURI = "https://test.api.amadeus.com";
		
		//1. POST -- Get the access Token
		 accessToken = given()	
			.header("Content-Type", "application/x-www-form-urlencoded")
			.formParam("grant_type", "client_credentials")
			.formParam("client_id", "cPnHRQQbhKR085La5uhPRO9rnycMWVtg")
			.formParam("client_secret", "fKzPSLXpZZV5sbM4")
		.when()
		.post("/v1/security/oauth2/token")
		.then()
			.assertThat()
				.statusCode(200)
					.extract()
						.path("access_token");
		System.out.println("Access Token -> " + accessToken);
	}
	
	@Test
	public void getFlightInfoTest() {
//		The reason I have commented out this portion of the code is because I should have literally moved it to the
//		@BeforeMethod but I wanted to keep record of it.
//		RestAssured.baseURI = "https://test.api.amadeus.com";
//		
//		//1. POST -- Get the access Token
//		String accessToken = given()	
//			.header("Content-Type", "application/x-www-form-urlencoded")
//			.formParam("grant_type", "client_credentials")
//			.formParam("client_id", "cPnHRQQbhKR085La5uhPRO9rnycMWVtg")
//			.formParam("client_secret", "fKzPSLXpZZV5sbM4")
//		.when()
//		.post("/v1/security/oauth2/token")
//		.then()
//			.assertThat()
//				.statusCode(200)
//					.extract()
//						.path("access_token");
//		System.out.println("Access Token -> " + accessToken);
		
		//2. GET -- Get flight info 
	Response flightDataResponse = given()
		.header("Authorization", "Bearer " + accessToken)
		.queryParam("origin", "PAR")
		.queryParam("maxPrice", 200)
			.when().log().all()
				.get("/v1/shopping/flight-destinations")
					.then().log().all()
						.assertThat()
							.statusCode(200)
								.extract().response(); // Here we are extracting/fetching the whole response. Since it returns response, then we are going to store it in a response data type
		JsonPath js = flightDataResponse.jsonPath();
		String type = js.get("data[0].type");
		System.out.println("type of the 0 indexed array is: " + type);
	}
	
	/*			Assignment
	 * 	From the Response body of the above API POst call fetch all the Types, return dates and total.
	 *  Hints: store them in a list and use for loop to print them out
	 *      "data": [
        {
            "type": "flight-destination",
            "origin": "ORY",
            "destination": "OPO",
            "departureDate": "2023-09-13",
            "returnDate": "2023-09-19",
            "price": {
                "total": "100.44"
            },
	 */

}
