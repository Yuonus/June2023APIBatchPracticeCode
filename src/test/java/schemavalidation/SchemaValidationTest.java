package schemavalidation;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.File;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class SchemaValidationTest {

	/*		Test Case
	 * Validate the schema of the following 
	 * 		--> URL: https://gorest.co.in/public/v2/users
	 * 		--> Call: POST
	 * 		--> Body: {
					    "name": "sabawoon Yuonus",
					    "email": "syuonus@gmail.com",
					    "gender": "male",
					    "status": "active"
						}
	 *		-->  Solution
	 * -> 1st u need to create the schema.
	 * -> Google json schema generator > select "JSON to JSON Schema" > Paste the response body captured from Postman
	 * -> Copy the JSON schema > Create a json file in the same schemavalidation package named as "createuserschema.json"
	 * -> past the copied JSON schema into it and save it. 
	 * -> Remember to add the json-schema-validator dependency to the POM.XML file. Go to Maven repository and search
	 * 		"Json Schema Validator", pick the "io.rest-assured » json-schema-validator" version
	 * -> Save the body in a json file under src/test/resources and supply it as body to the test. I already have a file
	 * 		named as "adduser.json" with the same properties, so I will use it.
	 */
	
	@Test
	public void addUserSchemaTest() {
	
		RestAssured.baseURI = "https://gorest.co.in";
		
		// 1: Add user --> POST Call
		given().log().all()
			.contentType(ContentType.JSON)
				.header("Authorization", "Bearer 201e815ea55b653e1dc98e9216347621ca7bbc26b909ee17dfd093985efa994e")
				.body(new File("./src/test/resources/data/adduser.json")). // This file path has been taken from src/test/resources
				// where we have stored the request body of the API.
		when().log().all()
			.post("/public/v2/users/").
		then().log().all()
			.assertThat()
				.statusCode(201)
					.and()
						.body(matchesJsonSchemaInClasspath("createuserschema.json")); // remember to manually statically import this matches method "import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;"
		/*		Note:
		 * If you run the test with 
		 * 		".body(matchesJsonSchemaInClasspath("./src/test/java/schemavalidation/createuserschema.json"));"  OR
		 * 		".body(matchesJsonSchemaInClasspath("createuserschema.json"));"
		 * The test will fail and will throw --> IllegalArgumentException: Schema to use cannot be null <--
		 * Because the "matchesJsonSchemaInClasspath" is in class path method, and the path from "schemavalidation" package
		 * is not class level (it is only package level), so we will have to move our "createuserschema.json" file from "schemavalidation" package
		 * to under the "src/test/resources" package.
		 * 	--> Remember not to pass the complete path like "/src/test/resources/createuserschema.json", if you pass
		 * 			u will get --> IllegalArgumentException: Schema to use cannot be null <--
		 * 	--> Only pass -->createuserschema.json<-- 
		 * 	--> Remember to update the email each time at "adduser.json" file
		 */
	}
	
	/*		Test Case
	 * Validate the schema of the following 
	 * 		--> URL: https://gorest.co.in/public/v2/users/
	 * 		--> Call: Get
	 * 		--> Validate the schema of all users
	 * 
	 *		-->  Solution
	 * -> 1st u need to create the schema.
	 * -> Google json schema generator > select "JSON to JSON Schema" > Paste the response body captured from Postman
	 * -> Copy the JSON schema > Create a json file in the under src/test/resources package named as "getuserschema.json"
	 * -> paste the copied JSON schema into it and save it. 
	 * 
	 */
	
	@Test
	public void getUserSchemaTest() {
	
		RestAssured.baseURI = "https://gorest.co.in";
		
		// 1: Add user --> POST Call
		given().log().all()
			.contentType(ContentType.JSON)
				.header("Authorization", "Bearer 201e815ea55b653e1dc98e9216347621ca7bbc26b909ee17dfd093985efa994e").
		when().log().all()
			.get("/public/v2/users/").
		then().log().all()
			.assertThat()
				.statusCode(200)
					.and()
						.body(matchesJsonSchemaInClasspath("getuserschema.json"));
	}
}
