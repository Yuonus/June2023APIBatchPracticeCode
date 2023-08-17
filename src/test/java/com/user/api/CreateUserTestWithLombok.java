package com.user.api;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CreateUserTestWithLombok {
	
	// To dynamically create the email ID
	public static String getRandomEmailId() {
		return "S_Tech" + System.currentTimeMillis() + "@mail.com";
	}
	
	@Test
	public void createUserTest() {
		RestAssured.baseURI = "https://gorest.co.in";
		User user = new User("Sabawoon", getRandomEmailId(), "male", "inactive");
		
		Response response = given()
			.contentType(ContentType.JSON)
			.header("Authorization", "Bearer 201e815ea55b653e1dc98e9216347621ca7bbc26b909ee17dfd093985efa994e")
			.body(user)
			.when().log().all()
				.post("/public/v2/users/");
		
		Integer userId = response.jsonPath().get("id");
		System.out.println("User ID: " + userId);
		
		//Get API, to get the same user
		Response getResponse = given()
				.header("Authorization", "Bearer 201e815ea55b653e1dc98e9216347621ca7bbc26b909ee17dfd093985efa994e")
					.when()
						.get("/public/v2/users/" + userId);
	// De-serialization,  --> json-pojo
		ObjectMapper mapper = new ObjectMapper();  // comes from jackson API Library
		try {
			User userRes = mapper.readValue(getResponse.getBody().asString(), User.class);
			System.out.println(userRes.getId() + " :" + userRes.getEmail() + " :" + userRes.getGender() + " :" + userRes.getStatus());
			// Lets write the assertion now
			Assert.assertEquals(user.getName(), userRes.getName());
			Assert.assertEquals(userId, userRes.getId());
			Assert.assertEquals(user.getEmail(), userRes.getEmail());
			
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
	}
	
	/*	Builder Pattern
	 * Builder pattern means chaining concept, when we are writing multiple actions are methods like in Selenium
	 * .moveToElement, .drag&drop, mouseover ... and at the end we write .Build(). And every method will return the current
	 * class object. Here in Rest assured we will use "@Builder" annotation. --> import it from Lombok
	 * 
	 */
	
	@Test
	public void createUser_WithBuilderPattern_Test() {
		RestAssured.baseURI = "https://gorest.co.in";
	// Here in Builder method we have to select the related master class (POJO class) Builder, which can be imported after
	// once we annotate our class with "@Builder" Annotation. --> Master class = User
		User user = new User.UserBuilder()
					.name("Sabawoon")
					.email(getRandomEmailId())
					.gender("male")
					.status("active")
					.build();
		
		Response response = given()
			.contentType(ContentType.JSON)
			.header("Authorization", "Bearer 201e815ea55b653e1dc98e9216347621ca7bbc26b909ee17dfd093985efa994e")
			.body(user)
			.when().log().all()
				.post("/public/v2/users/");
		
		Integer userId = response.jsonPath().get("id");
		System.out.println("User ID: " + userId);
		
		//Get API, to get the same user
		Response getResponse = given()
				.header("Authorization", "Bearer 201e815ea55b653e1dc98e9216347621ca7bbc26b909ee17dfd093985efa994e")
					.when()
						.get("/public/v2/users/" + userId);
	// De-serialization,  --> json-pojo
		ObjectMapper mapper = new ObjectMapper();  // comes from jackson API Library
		try {
			User userRes = mapper.readValue(getResponse.getBody().asString(), User.class);
			System.out.println(userRes.getId() + " :" + userRes.getEmail() + " :" + userRes.getGender() + " :" + userRes.getStatus());
			// Lets write the assertion now
			Assert.assertEquals(user.getName(), userRes.getName());
			Assert.assertEquals(userId, userRes.getId());
			Assert.assertEquals(user.getEmail(), userRes.getEmail());
			
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
	}
	
	/*	Upon running this Builder "createUserTest" && "createUser_WithBuilderPattern_Test" the console shows ("id": null,) Why?
	 * because the Builder Pattern is taking "@AllArgsConstructor" in request, but in fact it should take the custom
	 * constructor created in POJO class. And the "id" is not generated/given in the body, because the server itself
	 * generate the id, that is Y it is coming null. How to solve this issue?
	 * To solve this issue:
	 * 		-> Go to the related POJO class
	 * 		-> Create one more annotation
	 * 		-> @JsonInclude(JsonInclude.Include.NON_NULL)
	 * 		-> This annotation helps to ignore the null values
	 * 		-> Run the test, The ID will be gone
	 * 		-> This annotation comes from Jackson APIs
	 */

}
