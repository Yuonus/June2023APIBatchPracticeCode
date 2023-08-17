package PostAPIs;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class BookingAuthTest {
	
	@Test
	public void getBookingAuthTokenTest_With_Json_String() {
		RestAssured.baseURI = "https://restful-booker.herokuapp.com";
		String tokenId = given()
			.contentType(ContentType.JSON)
				.body("{\r\n"
						+ "    \"username\" : \"admin\",\r\n"
						+ "    \"password\" : \"password123\"\r\n"
						+ "}")
				.when()
					.post("/auth")
						.then()
							.assertThat()
								.statusCode(200)
									.extract()
										.path("token");
		System.out.println("Token ID is: " + tokenId);
		Assert.assertNotNull(tokenId); // Just for verification/validation purpose				
	}
	/* 		Note
	 * Supplying the body in above code is in the hard coded value and it is not a good practice, even though this approach
	 * is available. But, what if you have a big body, with lots of attribute; it will make it hard to check for the body.
	 * so the better way is to create JSON file and store the body in it, and then we can supply the json file to the body.
	 * 		----> Qs: where should the JSON file be created?
	 * Ans: since .Json file is non-java class, and it should not be created under src/main/java & src/test/java. So,
	 * it should be created under resources. excel files/.properties files/.json files/.log4j properties file... such kind of files needs to be created under resources.
	 * How to create Source Folder?
	 * 	Right click on your project > Expand "New" > click on "Source Folder" > and name it as: src/test/resources  -> (all letters need to be in small caps, and dont make any spelling mistakes)
	 * 	Now under resources: Always remember to not create the files directly, firstly you will have to create a "Folder" then your file.
	 * ----> Now remove the hard coded body from the code below
	 * select the body() method that accepts 'file' as parameter
	 * create the object of the file (import file from java) and supply the path of the file as parameter to the file object, how?
	 * --> Right click on the .json file you have created > click on Properties > and copy the path (not the complete path
	 * 		only /src/test/resources/data/basicauth.json and add a dot in the start of the path manually.)
	 */
	
	@Test
	public void getBookingAuthTokenTest_With_JSON_File() {
		RestAssured.baseURI = "https://restful-booker.herokuapp.com";
		String tokenId = given()
			.contentType(ContentType.JSON)
				.body(new File("./src/test/resources/data/basicauth.json"))
				.when()
					.post("/auth")
						.then()
							.assertThat()
								.statusCode(200)
									.extract()
										.path("token");
		System.out.println("Token ID is: " + tokenId);
		Assert.assertNotNull(tokenId); // Just for verification/validation purpose				
	}
	
	/*	How to test POST APIs? we did cover this subject in Postman sessions -> just a refresher here.
	 * 1: Post -- Add a user -- user id = 123 -- assert (201, body)
	 * 2: Get -- Get a user -- user/123 -- status code 200, assert user id = 123
	 * 	--> Remember that for this work flow we will have to write only one Test case meaning under one Test annotation
	 * 		Because this an end to end test.
	 * --> every time you are running this code, since the .json file is still having email as hard coded value so please
	 * 			update the email in your file.
	 * 
	 * ***HEADACHE -> Please dont forget to write the complete Base URI and Service URI
	 * 		--> Do not repeat the /and do not forget to write them. in this specific example I was forgetting the /
	 * 		--> at the end of the service URI an I was keep getting error, until I noticed it after almost working 
	 * 		--> two days on the same code.
	 */
	
	@Test
	public void addUserTest() {
		RestAssured.baseURI = "https://gorest.co.in";
		
		// 1: Add user --> POST Call
	int userId = given().log().all()
			.contentType(ContentType.JSON)
				.header("Authorization", "Bearer 201e815ea55b653e1dc98e9216347621ca7bbc26b909ee17dfd093985efa994e")
				.body(new File("./src/test/resources/data/adduser.json")).
		when().log().all()
			.post("/public/v2/users/").
		then()
			.assertThat()
				.statusCode(201)
				.and()
				.body("name", equalTo("sabawoon Yuonus"))
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
										.body("id", equalTo(userId));
	}

}
