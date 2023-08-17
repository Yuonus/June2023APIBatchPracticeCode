package GETAPIs;

import static io.restassured.RestAssured.*;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;

import java.util.List;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GETAPIWithBDD {
	
	// The URI used for this automation -> https://fakestoreapi.com/products
	
	@Test
	public void getProductsTest() {
		given().log().all()
			.when().log().all()
				.get("https://fakestoreapi.com/products")
					.then().log().all()
						.assertThat()
							.statusCode(200)
								.and()
									.contentType(ContentType.JSON)
										.and()
											.header("Connection", "keep-alive")
												.and()
													.body("$.size()", equalTo(20))
														.and()
															.body("id", is(notNullValue()))
																.and()
																	.body("title", hasItem("Mens Cotton Jacket"));
		
		/* What kind of pattern that is?
		 * First of all this is using a BDD format, and it is a chain pattern. Chain pattern is called "Builder pattern"
		 * Builder Pattern: This pattern means each and every method is giving you the current class object.
		 */
		
		/* How do you read that pattern?
		 * Given, when I am going to call this get call, then I am getting the response and I am going to assert that status code is 200, content-type is contentType.JSON and the header is …
		 * After running this above BDD format you will not get anything in the console. So how will you print all the required thing to the console?
		 * Since it is a chain method and you cannot write system.out method in the middles, and you cannot store it in a variable and then print it because it will break the chain. 
		 * So, the way around is to write .log() & .all() methods after given, when, then. We can also add the .and() method to separate the assertions.
		 * 
		 * How can you assert the body?
		 * the body assertion will happen with the help of hamcrest library. and you will have to statically import it.
		 * 		import static org.hamcrest.Matchers.*;
		 * $ -> dollar sign means in Rest Assured representing the entire response body. 
		 * 
		 * how do you read this method? .body("id", is(notNullValue())) and is it only fetching first id or all?
		 * Ans: Go to the body and check all IDs and they should not be null. and it is fetching all the IDs
		 * 
		 * How do you read this method? ->.body("title", hasItem("Mens Cotton Jacket"));. and is it only fetching one title or all titles?
		 * Ans: Rest Assured will internally create a title array and will collect all the title array and check in this title array do you have the "Mens Cotton Jacket" or not.
		 * it will fetch all the titles
		 */
		
		/*   ****** Important
		 * Remember that RestAssured Assertions are hard assertions. it does not provide soft assertions. so if one assertion
		 * fails it will not go to the next assertions
		 * 
		 */							
		 
	}
	
	@Test
	public void getUserAPITest() {
		RestAssured.baseURI = "https://gorest.co.in";
			given().log().all()
				.header("Authorization", "Bearer 201e815ea55b653e1dc98e9216347621ca7bbc26b909ee17dfd093985efa994e")
					.when().log().all()
						.get("/public/v2/users")
							.then().log().all()
								.assertThat()
									.statusCode(200)
										.and()
											.contentType(ContentType.JSON)
												.and()
												.body("$.size()", equalTo(10));
											
	}
	
	/* CAN we add query parameter in BDD format?
	 * ANS: Yes -> 
	 *  ----> Remember make it a habit to run the URI from postman first to get the idea.
	 *  ----> Declare your Domain URI. remember that declaring domain URI is optional and a good practice. You can also write the complete URI in get method.
	 * 
	 * What is the return type of GET/POST/PUT/DELETE Call? it is response
	 * Lets use Fake Store API Limit Results URL -> "https://fakestoreapi.com/products?limit=5"
	 */

	
	@Test
	public void getProductDataAPIWithQueryParamTest() {
		RestAssured.baseURI = "https://fakestoreapi.com";
			given().log().all()
				.queryParam("limit", 5) // We have a query parameter limit = 5
				.queryParam("name", "Sabawoon") // in case if you have more query parameters you can add it and the URI will take it. all though this 2nd query paramater is not there. this is just for practice purpose.
//				.header("Accept-Encoding", "gzip, deflate, br") // I optionally tried to play with header
					.when().log().all()
						.get("/products") // limit = 5 has been used in query parameter, and the RestAssured will take care of the ? sign.
							.then().log().all()
									.assertThat()
											.statusCode(200)
												.and()
													.contentType(ContentType.JSON);			
	}
	/* How can we fetch the data from this API call? Or how can we validate the data from this API Call?
	 * Hints:
	 * --> The response is a JSON Array
	 * --> Using JSON path() method we can extract the values from the response.
	 * 
	 * 
	 */
	@Test
	public void getProductDataAPI_With_Extract_Body() {
		RestAssured.baseURI = "https://fakestoreapi.com";
			
		Response response = given().log().all() // import response from Rest Assured Library
							.queryParam("limit", 5) 
								.when().log().all()
									.get("/products"); 
		JsonPath js = response.jsonPath(); // Now the whole response the call is stored in js variable
		/* ---> Get the id of the first product.
		 * ---> Get the title of the first product.
		 * ---> Get the price of the first product
		 * ---> Get the count of the first product
		 */
		int firstProductId = js.getInt("[0].id");
		System.out.println("The first product id is: " + firstProductId);
		String firstProductTitle = js.get("[0].title");
		System.out.println("First Product Title is: " + firstProductTitle);
		float firstProductPrice = js.getFloat("[0].price");
		System.out.println("The price of first product is: " + firstProductPrice);
		int count = js.getInt("[0].rating.count");
		System.out.println("The count of the first product is: " + count);
		String image = js.get("[0].image");
		System.out.println("The image of the first product is: " + image);
		float firstProductRate = js.getFloat("[0].rating.rate");
		System.out.println("The first Product rate is: " + firstProductRate);
	}
	
	/*	Now, I Would like to fetch all the IDs, titles, counts, ratings ... from the whole response.
	 * 
	 */
	
	@Test
	public void getProductDataAPI_With_Extract_Body_withJSONarray() {
		RestAssured.baseURI = "https://fakestoreapi.com";
		Response response = given().log().all()
				.queryParam("limit", 10)
					.when().log().all()
						.get("/products");
		JsonPath js = response.jsonPath();
					
		/* ---> Get all IDs of the response product, and how many IDs are there?. -> we can capture all the IDs in List
		 * ---> Get all the titles of the product available in the array.
		 * ---> Get the ratings of all the products. -> up to number of that we have limited in Query parameters are all Float values, So we can give them Float as generic
		 * ---> Get the counts of all the products.
		 * ---> Comment out all your print statements and loop the IDs, titles, ratings, and counts to print them. 
		 */
		
		List<Integer> idList = js.getList("id");
//		System.out.println("IDs of all products are: " + idList);
		System.out.println("Number of IDs are: " + idList.size());
		/*
		 * Remember if you store the titleList in Integer data type the Rest Assured will print it.
		 * Because the Rest Assured APIs are smart enough, as they understands that you want to fetch titles which are string
		 * and stored in Integer, but still we will internally convert it to the STRING and will print the result.
		 * But remember that this is not a good practice and if someone is reading your code will not get concept out of it.
		 * the good way is to store it in its related generic which is String
		 */
//		List<Integer> titleList = js.getList("title");
//		System.out.println(titleList);
		
		List<String> titleList = js.getList("title");
//		System.out.println("All the titles available for products are:" + titleList);
		
//		List<Object> ratingList = js.getList("rating.rate");
		List<Float> ratingList = js.getList("rating.rate", Float.class);
//		System.out.println("All the rating available for products: " + ratingList);
		
		List<Integer> countList = js.getList("rating.count");
//		System.out.println("Count of all products: " + countList);
		
		for(int i = 0; i<idList.size(); i++){
			int id = idList.get(i);
			String title = titleList.get(i);
			Object rate = ratingList.get(i);
			int count = countList.get(i);
			
			System.out.println("ID: " + id + " Title: " + title + " Rate: " + " Count: " + count);
		}		
	}
	
	
	/* Now change the limit in query parameter to 10 and run the Test getProductDataAPI_With_Extract_Body_witharray.
	 * Tell me why the Test is failing?
	 * Because in product number 7, the value of the rate = 3, and we have stored the value in Float generic and the value of the rate is int, since we cannot store int in float that is why it is failing.  
	 * So, what is the solution?
	 * 		Approach 1
	 * 	--> store the Generic and variable in Object data type.
	 * Refresher of Java: Object data type can store anything in it. it could be int, float, double, char, string, boolean.
	 * As you also know that Object is the super class of all classes.
	 * 		Approach 2
	 * --> Explicitly define the generic type if you don't want to store your data in Object data type.
	 *  Use this method --> getList(String path, Class<T> genericType): List<T> JsonPath
	 * 		 NOTE:
	 *  Remember that second approach could cause a miss match error in the future. because the code will print 3.0 
	 *  and the UI shows 3. this will cause validation error/miss match between UI and Back-end. So, it is alwyas
	 *  recommended to use the Object Approach
	 */
	
	//		---> Assignment
	/*	Hit this given API  --> https://gorest.co.in/public/v2/users/
	 * 	Fetch all the data from the response. ID, name, email, gender, status
	 * 	Use index for loop to print them to console.
	 */

	
	
	/*
	 * How will you fetch the response data from a specific user?
	 *   ---> REMEMBER to use an updated ID for your call, otherwise you will get nullPointerException with GoRest.in APIs
	 * Here the response body is not going to be JSON Array it is only going to simple JSON object.
	 * 		1st Approach
	 * In this case we can directly store the attributes in its related data type. we don't need to use the List
	 * 		2nd Approach
	 * with the help of extract() method along side with path() method
	 * With the extract method you can only fetch one data at a time in a Chain format
	 * 		3rd Approach
	 * You can also fetch all the data using extract() method as long as you break the chain onward the get() method. 
	 */
	@Test
	public void getUserAPI_With_Extract_Body_withJson() {
		RestAssured.baseURI = "https://gorest.co.in";
		Response response = given().log().all()
					.header("Authorization", "Bearer 201e815ea55b653e1dc98e9216347621ca7bbc26b909ee17dfd093985efa994e")
						.when().log().all()
							.get("/public/v2/users/3614610");
		JsonPath js = response.jsonPath();
		int id = js.getInt("id");
		System.out.println("User ID: " + id);
		// We can directly print the attributes
		System.out.println("User name: " + js.getString("name"));
		System.out.println("User gender: " + js.getString("gender"));
		System.out.println("User email: " + js.getString("email"));
		System.out.println("User status: " + js.getString("status"));
		
	}
	
	/*		2nd Approach
	 * Use case of extract method:
	 * whenever there is a single value we use extract() method along with path() method
	 * ---> Remembrance
	 * Booking API Workflow  -> 1 Auth Token API call
	 */
	@Test
	public void getUserAPI_With_Extract_Body_withJson_Extract() {
		RestAssured.baseURI = "https://gorest.co.in";
		int userId = given().log().all()
					.header("Authorization", "Bearer 201e815ea55b653e1dc98e9216347621ca7bbc26b909ee17dfd093985efa994e")
						.when().log().all()
							.get("/public/v2/users/3614610")
								.then()
									.extract().path("id");
		System.out.println("User ID is: " + userId);		 
	}
	
	
	// 3rd Approach
		@Test
		public void getUserAPI_With_Extract_Body_withJson_Extractall() {
			RestAssured.baseURI = "https://gorest.co.in";
		Response response = given().log().all()
						.header("Authorization", "Bearer 201e815ea55b653e1dc98e9216347621ca7bbc26b909ee17dfd093985efa994e")
							.when().log().all()
								.get("/public/v2/users/3614610");

			 int userId = response.then().extract().path("id");
			String name =  response.then().extract().path("name");
			String email = response.then().extract().path("email");
			String gender = response.then().extract().path("gender");
			String status = response.then().extract().path("status");
			
			System.out.println("User Id is: " + userId);
			System.out.println("User name: " + name);
			System.out.println("User email: " + email);
			System.out.println("User gender: " + gender);
			System.out.println("User status: " + status);
		}
		
		/*  	3rd Approach shorter and cleaner way
		 * 	As you can see .then().extract() method is frequently repeated in the code we literally chain it under
		 * 	.get() method. In this way we will not need to repeatedly insert it.
		 */
		@Test
		public void getUserAPI_With_Extract_Body_withJson_Extractall_LessCode() {
			RestAssured.baseURI = "https://gorest.co.in";
		Response response = given().log().all()
						.header("Authorization", "Bearer 201e815ea55b653e1dc98e9216347621ca7bbc26b909ee17dfd093985efa994e")
							.when().log().all()
								.get("/public/v2/users/3614610")
									.then()
										.extract()
											.response(); // Here we have captured the whole response
		
			 int userId = response.path("id");
			String name =  response.path("name");
			String email = response.path("email");
			String gender = response.path("gender");
			String status = response.path("status");
			
			System.out.println("User Id is: " + userId);
			System.out.println("User name: " + name);
			System.out.println("User email: " + email);
			System.out.println("User gender: " + gender);
			System.out.println("User status: " + status);
		}

	
}
