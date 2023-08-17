package putAPIs;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UpdateUserTest {
	
	public static String getRandomEmailId() {
		return "S_Tech" + System.currentTimeMillis() + "@gmail.com";
	}
	
	/* In order to update a user, u should have a user first.
	 * Create a user --> POST call ---> User ID
	 * Update the user --> PUT call ---> URL+/+User ID (received from the POST call)
	 * URL--> https://gorest.co.in/public/v2/users
	 * Header --> "Bearer 201e815ea55b653e1dc98e9216347621ca7bbc26b909ee17dfd093985efa994e"
	 */
	
	@Test
	public void updateUserTest() {
		
		RestAssured.baseURI = "https://gorest.co.in";
		User user = new User("Sabawoon", getRandomEmailId(), "male", "active");
	
	// Post call --> Create a user
	Response response = given().log().all()
		.contentType(ContentType.JSON)
		.header("Authorization", "Bearer 201e815ea55b653e1dc98e9216347621ca7bbc26b909ee17dfd093985efa994e")
		.body(user)
		.when().log().all()
			.post("/public/v2/users");
		Integer userId = response.jsonPath().get("id");
		System.out.println("User ID: " + userId);
		System.out.println("---------------------");
		
		// Update the existing user. we will use the setter for this purpose
		user.setName("Abaseen");
		user.setStatus("inactive");
		
		// 
		given().log().all()
		.contentType(ContentType.JSON)
		.header("Authorization", "Bearer 201e815ea55b653e1dc98e9216347621ca7bbc26b909ee17dfd093985efa994e")
		.body(user)
		.when().log().all()
			.put("/public/v2/users/" + userId) // We can also use PATCH call here. (Partial info to be updated.)
				.then().log().all()
					.assertThat()
						.statusCode(200)
							.and()
								.body("id", equalTo(userId))
									.and()
										.body("name" , equalTo(user.getName()))
											.and()
												.body("status", equalTo(user.getStatus()));
		
	}
	
	// Class Task, update the same user using Builder Pattern
	@Test
	public void updateUser_WithBuilderPattern_Test() {
		
		RestAssured.baseURI = "https://gorest.co.in";
		User user = new User.UserBuilder()
					.name("Zala")
					.email(getRandomEmailId())
					.gender("female")
					.status("active")
					.build();
	
	// Post call --> Create a user
	Response response = given().log().all()
		.contentType(ContentType.JSON)
		.header("Authorization", "Bearer 201e815ea55b653e1dc98e9216347621ca7bbc26b909ee17dfd093985efa994e")
		.body(user)
		.when().log().all()
			.post("/public/v2/users");
		Integer userId = response.jsonPath().get("id");
		System.out.println("User ID: " + userId);
		System.out.println("---------------------");
		
		// Update the existing user. we will use the setter for this purpose
		user.setName("Rameen");
		user.setStatus("inactive");
		user.setGender("male");
		
		// 
		given().log().all()
		.contentType(ContentType.JSON)
		.header("Authorization", "Bearer 201e815ea55b653e1dc98e9216347621ca7bbc26b909ee17dfd093985efa994e")
		.body(user)
		.when().log().all()
			.put("/public/v2/users/" + userId)
				.then().log().all()
					.assertThat()
						.statusCode(200)
							.and()
								.body("id", equalTo(userId))
									.and()
										.body("name" , equalTo(user.getName()))
											.and()
												.body("status", equalTo(user.getStatus()));
		
	}
	
}
