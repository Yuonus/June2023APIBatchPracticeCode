package PostAPIs;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.util.UUID;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import pojo.User;

public class CreateUserWithPojoTest {
	
	/* CRUD WORKFLOW
	 * lets automate CRUD Workflow -> POST 1: Create a user from Postman 
	 * Note: Upon running this test change the value of the email every time. other wise the test will fail giving u the 
	 * error --> java.lang.AssertionError: 1 expectation failed.
											Expected status code <201> but was <422>.
	 * How will this problem be resolved dynamically?
	 * Ans: In order to prevent "email id has already taken", we will create a random or unique email ID using a static
	 * method with the help of "System.currentTimeMillis" method. and you can call this method in the object part, instead of hard coded email.
	 * 		CurrentTimems --> will give us a random number
	 * 
	 */
	
	// To generate random emails; we have two ways
	// 1: System.currentTimeMillis() method
	// 2: UUID class
	public String getRandomEmailId() {
		return"ApiAutomation"+System.currentTimeMillis() + "@mil.com";
//		return "ApiAutomation" + UUID.randomUUID() + "@mail.com";
	}

	@Test
	public void addUserTest() {
		RestAssured.baseURI = "https://gorest.co.in";
		User user = new User("sabawoon", getRandomEmailId(), "male", "active"); // Here the placement of name, email, gender, and status matters. if you just change the placement of each, it will throw an error and the test will fail.
		
		// 1: Add user --> POST Call
	int userId = given().log().all()
			.contentType(ContentType.JSON)
				.header("Authorization", "Bearer 201e815ea55b653e1dc98e9216347621ca7bbc26b909ee17dfd093985efa994e")
				.body(user)
		.when().log().all()
			.post("/public/v2/users/").
		then().log().all()
			.assertThat()
				.statusCode(201)
				.and()
				.body("name", equalTo(user.getName()))
				.extract()
				.path("id");
		System.out.println("User ID --> " + userId);
		
		// 2: Get the same user and verify it. --> Get Call
	
		given()
			.header("Authorization", "Bearer 201e815ea55b653e1dc98e9216347621ca7bbc26b909ee17dfd093985efa994e")
				.when().log().all()
					.get("/public/v2/users/" + userId)
						.then().log().all()
							.assertThat()
								.statusCode(200)
									.and()
										.body("id", equalTo(userId))
											.and()
												.body("name", equalTo(user.getName()))
													.and()
														.body("status", equalTo(user.getStatus()))
															.and()
																.body("email", equalTo(user.getEmail()));
	}
	
	/*	In Qs: How many types of POST Operation can we perform?
	 * 1: Directly supply the json string
	 * 2: pass the json file
	 * 3: pojo - java object -- to json with the help of jackson & rest assured
	 */

}
