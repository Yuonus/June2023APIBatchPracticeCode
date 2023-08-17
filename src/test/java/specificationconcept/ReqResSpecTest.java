package specificationconcept;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class ReqResSpecTest {
	
	/*		Assignment
	 *  Create Request Specification and Response Specification together?
	 */

	public static RequestSpecification user_req_spec() {
		RequestSpecification requestSpec = new RequestSpecBuilder()
		.setBaseUri("https://gorest.co.in")
		.setContentType(ContentType.JSON)
		.addHeader("Authorization", "Bearer 201e815ea55b653e1dc98e9216347621ca7bbc26b909ee17dfd093985efa994e")
		.build();
		
	return requestSpec;
	
	}
	
	public static ResponseSpecification get_res_spec_200_OK() {
			
			ResponseSpecification res_spec_200_ok = new ResponseSpecBuilder()
				.expectContentType(ContentType.JSON)
				.expectStatusCode(200)
				.expectHeader("Server", "cloudflare")
				.build();
			return res_spec_200_ok;
		
		}
	
	@Test
	public void getUser_With_Req_Res_Spec_Test() {
		given()
			.spec(user_req_spec())
				.get("/public/v2/users/")
					.then()
						.assertThat()
							.spec(get_res_spec_200_OK());		
	}

	
}
