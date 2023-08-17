package PostAPIs;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import pojo.Credentials;

public class BookingAuthWithPojoTest {
	
	/*		POJO   --> Plain Old Java Object
	 * 1: Cannot extend any other class
	 * 2: OOP -> Encapsulation
	 * 3: Private class variables --> json body
	 * 4: public Getters & Setters
	 */
	
	//  NOTE NOTE NOTE
	// Do not forget to add Jackson API to your pom.xml file -> maven repository > type Jackson > select "Jackson Databind
	
	@Test
	public void getBookingAuthTokenTest_With_Json_String() {
		RestAssured.baseURI = "https://restful-booker.herokuapp.com";
		Credentials creds = new Credentials("admin", "password123");
		
		String tokenId = given()
			.contentType(ContentType.JSON)
				.body(creds)
					.when().log().all()
						.post("/auth")
							.then().log().all()
								.assertThat()
									.statusCode(200)
										.extract()
											.path("token");
		System.out.println("Token ID is: " + tokenId);
		Assert.assertNotNull(tokenId); // Just for verification/validation purpose				
	}

}
