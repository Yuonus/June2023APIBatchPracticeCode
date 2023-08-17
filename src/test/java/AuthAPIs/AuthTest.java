package AuthAPIs;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class AuthTest {
	
	// Allure Report
	@BeforeTest
	public void allureSetup() {
		RestAssured.filters(new AllureRestAssured());
	}
	
	/*			JWT Authorization
	 * 	--> URL: https://fakestoreapi.com/auth/login
	 * 	--> Method:	POST
	 * 	--> Body:	{
    					"username": "mor_2314",
    					"password": "83r5^_"
					}
	 * Get the JWT token, and break it into three parts
	 * In Qs: How will you get the JWT token and how will you break it into three parts?
	 */
	
	@Test
	public void jwtAuthWithJsonBody() {
		RestAssured.baseURI = "https://fakestoreapi.com";
		String jwtTokenId = given()
			.contentType(ContentType.JSON)
				.body("{\r\n"
						+ "    \"username\": \"mor_2314\",\r\n"
						+ "    \"password\": \"83r5^_\"\r\n"
						+ "}")
				.when()
					.post("/auth/login")
						.then()
							.assertThat().statusCode(200)
								.and()
									.extract().path("token");
		System.out.println("JWT Token ID: " + jwtTokenId);
	
	/*eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOjIsInVzZXIiOiJtb3JfMjMxNCIsImlhdCI6MTY5MDQ3NTEwOH0.
	 * eV129poxiQYKEKepiZRtrexVM4Md60ItMhI_7vX8QaA
	 * -------------------------------------------
	 * Let's break the token into three parts:
	 * PayLoad: --> eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9
	 * Header: --> eyJzdWIiOjIsInVzZXIiOiJtb3JfMjMxNCIsImlhdCI6MTY5MDQ3NTEwOH0
	 * Signature: --> eV129poxiQYKEKepiZRtrexVM4Md60ItMhI_7vX8QaA
	 * -----------------------------------------------------------------
	 * Now as we know that JWT token we captured is String, so we can break this string using the split() function. And we will use dot "."
	 * as splitter (but you will have to add two backslashes \\ as escape character before dot). But remember if the JWT
	 * Token was separated by semi-colon you don't need to add any \\.
	 */
		String tokenArr[] = jwtTokenId.split("\\.");
		System.out.println("Payload: " + tokenArr[0]);
		System.out.println("Header: " + tokenArr[1]);
		System.out.println("Signature: " + tokenArr[2]);
	}
	
	/*			Basic Authorization
	 * To get the url > google "herokuapp selenium" > click on "The-internet(Heroku) > click on "Basic Auth"
	 * Username & password is both = admin > copy the url from browser
	 * 	--> URL: https://the-internet.herokuapp.com/basic_auth
	 * 	--> Method:	GET
	 * 	--> Status code: 200
	 */
	
	@Test
	public void basicAuthTest() {
		RestAssured.baseURI = "https://the-internet.herokuapp.com";
		String responseBody = given()
			.auth().basic("admin", "admin") // These methods are added to take care of the basic auth pop up.
				.when()
					.get("/basic_auth")
						.then()
							.assertThat().statusCode(200)
								.and()
									.extract().body().asString();
		System.out.println("Basic Auth response body: " + responseBody);
	}
	
	/*			Preemptive Authorization
	 * 	Preemptive auth is more advanced version of basic auth, in terms of authentication preemptive auth is faster performance wise than basic auth
	 * To get the url > google "herokuapp selenium" > click on "The-internet(Heroku) > click on "Basic Auth"
	 * Username & password is both = admin > copy the url from browser
	 * 	--> URL: https://the-internet.herokuapp.com/basic_auth
	 * 	--> Method:	GET
	 * 	--> Status code: 200
	 */
	
	@Test
	public void preemptiveAuthTest() {
		RestAssured.baseURI = "https://the-internet.herokuapp.com";
		String responseBody = given()
			.auth().preemptive().basic("admin", "admin") // These methods are added to take care of the preemptive auth pop up.
				.when()
					.get("/basic_auth")
						.then()
							.assertThat().statusCode(200)
								.and()
									.extract().body().asString();
		System.out.println("Preemptive Auth response body: " + responseBody);

	}
	
	/*			Digestive Authorization
	 * 	Digestive Auth is more secure and much faster performance wise in comparison to preemptive auth
	 * To get the url > google "herokuapp selenium" > click on "The-internet(Heroku) > click on "Basic Auth"
	 * Username & password is both = admin > copy the url from browser
	 * 	--> URL: https://the-internet.herokuapp.com/basic_auth
	 * 	--> Method:	GET
	 * 	--> Status code: 200
	 */
	
	@Test
	public void digestAuthTest() {
		RestAssured.baseURI = "https://the-internet.herokuapp.com";
		String responseBody = given()
			.auth().digest("admin", "admin") // These methods are added to take care of the preemptive auth pop up.
				.when()
					.get("/basic_auth")
						.then()
							.assertThat().statusCode(200)
								.and()
									.extract().body().asString();
		System.out.println("Digestive Auth response body: " + responseBody);

	}
	
	/*			API Key Authorization
	 * --> URL:http://api.weatherapi.com/v1/current.json?q=London&aqi=no
	 * --> Method: GET
	 * --> Status code:
	 */
	
	@Test
	public void apiKeyAuthTest() {
		RestAssured.baseURI = "http://api.weatherapi.com";
		String responseBody = given()
				.queryParam("q", "London")
				.queryParam("aqi", "no")
				.queryParam("key", "8bb595aecdbd42a5bd1193420232206")
					.when()
						.get("/v1/current.json")
							.then()
								.assertThat().statusCode(200)
									.and()
										.extract().body().asString();
			System.out.println("Digestive Auth response body: " + responseBody);
			
	}
	
}
