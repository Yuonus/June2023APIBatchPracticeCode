package specificationconcept;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;

public class ResponseSpecBuilderTest {
	
	public static ResponseSpecification get_res_spec_200_OK() {
		
		ResponseSpecification res_spec_200_ok = new ResponseSpecBuilder()
			.expectContentType(ContentType.JSON)
			.expectStatusCode(200)
			.expectHeader("Server", "cloudflare")
			.build();
		return res_spec_200_ok;
	
	}
	
	@Test
	public void get_user_res_200_spec_Test() {
		RestAssured.baseURI = "https://gorest.co.in";
		given()
			.header("Authorization", "Bearer 201e815ea55b653e1dc98e9216347621ca7bbc26b909ee17dfd093985efa994e")
				.when()
					.get("/public/v2/users/")
						.then()
							.assertThat()
								.spec(get_res_spec_200_OK());
	}
	
	/* Class Activity
	 *  Create a spec for 401 status code -> Auth Fail gives you 401 status code
	 *  Create a Test method for it and call it.
	 *  Hint -> to fail the Auth add some extra characters at the end of the Authorization token
	 */
	
	public static ResponseSpecification get_res_spec_401_Auth_Fail() {
		
		ResponseSpecification res_spec_401_AUTH_FAIL = new ResponseSpecBuilder()
			.expectStatusCode(401)
			.expectHeader("Server", "cloudflare")
			.build();
		return res_spec_401_AUTH_FAIL;
	
	}
	
	@Test
	public void get_user_res_401_Auth_Fail_spec_Test() {
		RestAssured.baseURI = "https://gorest.co.in";
		given()
			.header("Authorization", "Bearer 201e815ea55b653e1dc98e9216347621ca7bbc26b909ee17dfd093985efa994e99999")
				.when()
					.get("/public/v2/users/")
						.then()
							.assertThat()
								.spec(get_res_spec_401_Auth_Fail());
	}
	/*
	 * Create a spec that should also validate the response body
	 */
	
	public static ResponseSpecification get_res_spec_200_OK_With_Body() {
		
		ResponseSpecification res_spec_200_ok = new ResponseSpecBuilder()
			.expectContentType(ContentType.JSON)
			.expectStatusCode(200)
			.expectHeader("Server", "cloudflare")
			.expectBody("$.size()", equalTo(10))
			.expectBody("id", hasSize(10))
			.build();
		return res_spec_200_ok;
	
	}
	
	@Test
	public void get_users_res_200_spec_Test() {
		RestAssured.baseURI = "https://gorest.co.in";
		given()
			.header("Authorization", "Bearer 201e815ea55b653e1dc98e9216347621ca7bbc26b909ee17dfd093985efa994e")
				.when()
					.get("/public/v2/users/")
						.then()
							.assertThat()
								.spec(get_res_spec_200_OK_With_Body());
	}
	
	/*		ASSIGNMENT
	 * 	Create Request Specification and Response Specification together
	 * 
	 */
}
