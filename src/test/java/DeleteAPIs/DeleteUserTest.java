package DeleteAPIs;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import putAPIs.User;

public class DeleteUserTest {
	
	/*	In order to delete a user you should create/have a user first
	 * 1: Create a user --> POST call ---> User ID  --> 201
	 * 2: Delete the user --> DELETE call ---> URL+/+User ID (received from the DELETE call) --> 204 
	 * 3: Confirm that user got deleted. --> GET call --> Get user /UserID --> status code 404 resource not found
	 * 
	 * ----> The API URL to use: https://gorest.co.in/public/v2/users
	 */
	
	public static String getRandomEmailId() {
		return "S_Tech" + System.currentTimeMillis() + "@gmail.com";
	}
	
	@Test
	public void deleteUserTest() {
		
		RestAssured.baseURI = "https://gorest.co.in";
		User user = new User("Sabawoon", getRandomEmailId(), "male", "active");
	
	// Post call --> Create a user
	Response response = given().log().all()
		.contentType(ContentType.JSON)
		.header("Authorization", "Bearer 201e815ea55b653e1dc98e9216347621ca7bbc26b909ee17dfd093985efa994e")
		.body(user)
		.when().log().all()
			.post("/public/v2/users");
	
	response.prettyPrint();
	
		Integer userId = response.jsonPath().get("id");
		System.out.println("User ID: " + userId);
		System.out.println("---------------------");
		
	//2. Delete user. --> we don't need to update anything so, we dont need any setter here.
		given().log().all()
		.contentType(ContentType.JSON)
		.header("Authorization", "Bearer 201e815ea55b653e1dc98e9216347621ca7bbc26b909ee17dfd093985efa994e")
		.when().log().all()
			.delete("/public/v2/users/" + userId)
				.then().log().all()
					.assertThat()
						.statusCode(204);
		// Since we dont get anything in the delete call body, so we dont need to assert anything in the body
		
		//3. Confirm user is deleted.
		given().log().all()
		.contentType(ContentType.JSON)
		.header("Authorization", "Bearer 201e815ea55b653e1dc98e9216347621ca7bbc26b909ee17dfd093985efa994e")
		.when()
			.get("/public/v2/users/" + userId)
				.then().log().all() 
					.assertThat()
						.statusCode(404)
							.and()
								.assertThat()
									.body("message", equalTo("Resource not found"));
	}
}
