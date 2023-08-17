package GETAPIs;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

public class Assignment {
	
	/* Change this NON-BDD format to BDD format
	 * 
	 */
	RequestSpecification request;
	
	@Test
	public void getAllUsersWithQueryParameterAPITest() {
		
		request.queryParam("name", "Malti");
		request.queryParam("gender", "male");
		
		Response response = request.get("/public/v2/users/"); // GET Call with rest of the URI
		
		System.out.println("----------------------------------------");
	
		int statusCode = response.statusCode();
		System.out.println("status code : " + statusCode);

		Assert.assertEquals(statusCode, 200);
		
		// Response line / response message -> 200 OK
		String statusMesg = response.statusLine();
		System.out.println(statusMesg);
		
		response.prettyPrint();
	}
	
	@Test
	public void getAllUsersWithQueryParameterAPITestUsingBDD() {
		RestAssured.baseURI = "https://gorest.co.in";
		given().log().all()
			.queryParam("name", "Malti")
			.queryParam("gender", "male")
			.header("Authorization", "Bearer 201e815ea55b653e1dc98e9216347621ca7bbc26b909ee17dfd093985efa994e")
				.when().log().all()
					.get("/public/v2/users/")
						.then().log().all()
							.statusCode(200)
								.and()
									.contentType(ContentType.JSON);				

	}
}
