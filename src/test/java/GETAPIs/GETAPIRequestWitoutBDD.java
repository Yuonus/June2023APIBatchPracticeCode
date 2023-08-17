package GETAPIs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

//public class GETAPIRequestTest {

			//The URI used for this automation -> https://fakestoreapi.com/products
//	// in the testNG we will use @Test annotation and import it from testNG
//	@Test
//	public void getAllUsersAPITest() {
//		
//		// === Request
//		RestAssured.baseURI = "https://gorest.co.in"; // Base URI
//		RequestSpecification request = RestAssured.given(); // this method helps in creation of request specification
//		
//		// Remember to add the request header before the get call
//		request.header("Authorization", "Bearer 201e815ea55b653e1dc98e9216347621ca7bbc26b909ee17dfd093985efa994e");
//		// now start adding your other info like url, http method, body 
//		Response response = request.get("/public/v2/users/"); // Get call or rest of the URI or service URI OR actual URI
//		
//	//====== Response
//		System.out.println("----------------------------------------");
//		// Now lets verify the response sections
//		// status code
//		int statusCode = response.statusCode();
//		System.out.println("status code : " + statusCode);
//		/*
//		 * Verification Point
//		 * Now lets validate the status code = 200
//		 * we will do the validation using the assertions
//		 */
//		Assert.assertEquals(statusCode, 200);
//		
//		// Response line / response message -> 200 OK
//		String statusMesg = response.statusLine();
//		System.out.println(statusMesg);
//		
//		/*
//		 * fetch the body
//		 * Now it is the time to fetch the response body, how will you do that?
//		 * we will use prettyPrint() method to fetch the response body
//		 */
//		response.prettyPrint();
//		
//		/*		- - > Fetching Response headers
//		 * 1: fetch specific response headers. (give the key as parameter, will give you the value
//		 * 2:Fetch all headers. Since fetch all headers return type is "Headers" and I don't know
//		 * what the headers is; so we can append it in asList() method, then store it in a list variable and print it to
//		 * console. Remember to import Header from RestAssured library & List from java library
//		 */
//		
//		// fetch specific response headers
//		String contentType = response.header("Content-Type");
//		System.out.println("Content-Type = " + contentType);
//		
//		// fetch all headers
//		List<Header> headersList = response.headers().asList();
//		System.out.println(headersList);
//		System.out.println("====================================================");
//		// if you want to know the quantity/number/size of the headers use the size() method
//		System.out.println("Numbers of  Headers used in the response: " + headersList.size());
//		
//		// if we would like to print/fetch all the headers in a nice way we can use for each loop
//		
//		for (Header h : headersList) {
//			System.out.println(h.getName() + " : " + h.getValue());
//		}
//	}
//	
//	/*	How to add query parameters?
//	 * 	We will have two ways to add query parameters: 1-> appending the query parameter at the end of Get call or Rest of the URI OR service URI
//	 * 			Response response = request.get("/public/v2/users/?name=sabawoon&gender=male");
//	 * 	But remember this is not a good practice in general to append/add the query parameters at the end of the Get call
//	 * 	because what if next day you want to add some more Q-parameters, which that will need you to manually add them.
//	 * so if you have only two or three (limited) numbers of query parameter this approach will be a good one.
//	 * 	2: request.queryParam("name", "Sabawoon");
//	 * 	   request.queryParam("gender", "male");
//	 */
//	
//	@Test
//	public void getAllUsersWithQueryParameterAPITest() {
//		
//		// === Request
//		RestAssured.baseURI = "https://gorest.co.in"; // Base URI
//		RequestSpecification request = RestAssured.given(); // this method helps in creation of request specification
//		
//		request.queryParam("name", "Sabawoon");
//		request.queryParam("gender", "male");
//		
//		// Remember to add the request header before the get call
//		request.header("Authorization", "Bearer 201e815ea55b653e1dc98e9216347621ca7bbc26b909ee17dfd093985efa994e");
//		// now start adding your other info like url, http method, body 
//		Response response = request.get("/public/v2/users/"); // GET Call with rest of the URI
//		
//	//====== Response
//		System.out.println("----------------------------------------");
//		// Now lets verify the response sections
//		// status code
//		int statusCode = response.statusCode();
//		System.out.println("status code : " + statusCode);
//
//		Assert.assertEquals(statusCode, 200);
//		
//		// Response line / response message -> 200 OK
//		String statusMesg = response.statusLine();
//		System.out.println(statusMesg);
//		
//		response.prettyPrint();
//	}
//		
//		/* Adding all these query parameters are quite lengthy, So, what is the way around?
//		 * We can use HashMap which stores the data in key-value format.
//		 * We can use HashMap with N number of query parameter.
//		 * Note: what will be the generic of key and value <>?
//		 * the generic of key and value will be string, string. what if the value is int, float, ...
//		 * in that case the generic of value will change to Object class, because object can hold string, int, char, double almost all types.
//		 * 	-> both Map & HashMap are imported from Java.util package
//		 */
//		@Test
//		public void getAllUsersWithQueryParameter_WithHasMap_APITest() {
//			
//			// === Request
//			RestAssured.baseURI = "https://gorest.co.in"; // Base URI
//			RequestSpecification request = RestAssured.given(); 
//			
//			Map<String, String> queryParamsMap =  new HashMap<String, String>();
//			queryParamsMap.put("name", "Sabawoon"); //-> Put() is used for adding elements in Map
//			queryParamsMap.put("gender", "male");
//			
//			request.queryParams(queryParamsMap);
//			
//			request.header("Authorization", "Bearer 201e815ea55b653e1dc98e9216347621ca7bbc26b909ee17dfd093985efa994e");
//			Response response = request.get("/public/v2/users/"); // GET Call with rest of the URI
//			
//		//====== Response
//			System.out.println("----------------------------------------");
//			// Now lets verify the response sections
//			// status code
//			int statusCode = response.statusCode();
//			System.out.println("status code : " + statusCode);
//
//			Assert.assertEquals(statusCode, 200);
//			
//			// Response line / response message -> 200 OK
//			String statusMesg = response.statusLine();
//			System.out.println(statusMesg);
//			
//			response.prettyPrint();
			
			/*	Question: In query parameter we are only trying to fetch the name and gender, but why does the 
			 * 	API returns email, status and ID in the console?
			 * 	Ans: Pretend if you are trying to get the info from amazon and you search give the products MacBook with
			 * 	the screen size 17 inch. you will get a whole bunch of options.
			 * the result: it will print the whole object while using the query parameters
			 */
	// ================================================================================================================
			/*	As you can see the code is super lengthy, we can find some common lines like base url, request specification
			 *  request.header (Authentication token)....
			 *  So, to prevent all the common codes repetition we can create @BeforeTest annotation and place
			 *  all these common lines/codes there.
			 *  
			 *  **** Remember not to add service URI in @BeforeTest annotation, because it will run first then 
			 *  what will be the use of query parameters.
			 *  For my future records I will comment out all the code above and repeat the process below.
			 */
//	}

public class GETAPIRequestWitoutBDD {
	RequestSpecification request;
	
	@BeforeTest
	public void setup() {
		RestAssured.baseURI = "https://gorest.co.in"; // Base URI
		request = RestAssured.given(); // this method helps in creation of request specification
		// Remember to add the request header before the get call
		request.header("Authorization", "Bearer 201e815ea55b653e1dc98e9216347621ca7bbc26b909ee17dfd093985efa994e");
	}
	
	@Test
	public void getAllUsersAPITest() {
		
		Response response = request.get("/public/v2/users/"); 
	//====== Response
		System.out.println("----------------------------------------");
		// Now lets verify the response sections
		// status code
		int statusCode = response.statusCode();
		System.out.println("status code : " + statusCode);
	
		Assert.assertEquals(statusCode, 200);
		
		// Response line / response message -> 200 OK
		String statusMesg = response.statusLine();
		System.out.println(statusMesg);
		 
		response.prettyPrint();
		
		// fetch specific response headers
		String contentType = response.header("Content-Type");
		System.out.println("Content-Type = " + contentType);
		
		// fetch all headers
		List<Header> headersList = response.headers().asList();
		System.out.println(headersList);
		System.out.println("====================================================");
		// if you want to know the quantity/number/size of the headers use the size() method
		System.out.println("Numbers of  Headers used in the response: " + headersList.size());
		
		for (Header h : headersList) {
			System.out.println(h.getName() + " : " + h.getValue());
		}
	}
	
	@Test
	public void getAllUsersWithQueryParameterAPITest() {
		
		request.queryParam("name", "Sabawoon");
		request.queryParam("gender", "male");
		
		Response response = request.get("/public/v2/users/"); // GET Call with rest of the URI
		
		System.out.println("----------------------------------------");
	
		int statusCode = response.statusCode();
		System.out.println("status code : " + statusCode);

		Assert.assertEquals(statusCode, 200);
		
		// Response line / response message -> 200 OK
		String statusMesg = response.statusLine();
		System.out.println(statusMesg);
		
		response.prettyPrint();
	}
	
		@Test
		public void getAllUsersWithQueryParameter_WithHasMap_APITest() {
					
			Map<String, String> queryParamsMap =  new HashMap<String, String>();
			queryParamsMap.put("name", "Sabawoon"); //-> Put() is used for adding elements in Map
			queryParamsMap.put("gender", "male");
			
			request.queryParams(queryParamsMap);
			
			Response response = request.get("/public/v2/users/"); // GET Call with rest of the URI
			
		//====== Response
			System.out.println("----------------------------------------");
			// status code
			int statusCode = response.statusCode();
			System.out.println("status code : " + statusCode);

			Assert.assertEquals(statusCode, 200);
			
			// Response line / response message -> 200 OK
			String statusMesg = response.statusLine();
			System.out.println(statusMesg);
			
			response.prettyPrint();
	}
}

	/* Rest Assured support two approaches
	 * 	1: Non-BDD Rest Assured Approach > the one covered above
	 * 	2: BDD Approach
	 */
