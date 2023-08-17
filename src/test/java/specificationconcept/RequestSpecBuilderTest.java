package specificationconcept;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RequestSpecBuilderTest {
	
	public static RequestSpecification user_req_spec() {
		RequestSpecification requestSpec = new RequestSpecBuilder()
		.setBaseUri("https://gorest.co.in")
		.setContentType(ContentType.JSON)
		.addHeader("Authorization", "Bearer 201e815ea55b653e1dc98e9216347621ca7bbc26b909ee17dfd093985efa994e")
		.build();
	return requestSpec;
	}
	
	@Test
	public void getUser_With_Request_Spec() {
		
		given().log().all()
			.spec(user_req_spec())
				.get("/public/v2/users/")
					.then().log().all()
						.assertThat()
								.statusCode(200);
	}
	
	@Test
	public void getUser_With_Param_Request_Spec() {
		
		given().log().all()
		.queryParam("name", "Kiran Kaniyar")
		.queryParam("status", "inactive")
			.spec(user_req_spec())
				.get("/public/v2/users/")
					.then().log().all()
						.assertThat()
								.statusCode(200);
	}
	


}
