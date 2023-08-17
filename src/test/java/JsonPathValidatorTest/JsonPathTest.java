package JsonPathValidatorTest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class JsonPathTest {
	
	/*	Class Task
	 * using the URL "http://ergast.com/api/f1/2017/circuits.json" validate the following by JsonPath queries.
	 * 	1: Validate/check how many countries are there?
	 *  2: Validate/ check how many circuits are there?
	 *  
	 *  Solution:
	 *  		1 --> Get your response
	 *  		2 --> Convert the response to String
	 *  		3 --> Use jsonPath.read() method. (pass the jsonResponse and query as parameters to read method)
	 *  		4 --> Write your JQL queries 
	 *  		5 --> Decide in which data type you would like to store 
	 */

	@Test
	public void getCircuitDatAPIWith_YearTest() {
		RestAssured.baseURI = "http://ergast.com";
		
			Response response = given().log().all()
					.when().log().all()
						.get("/api/f1/2017/circuits.json");
			
		/* I dont want to write rest assured validation, I would like to use JsonPath validation which comes from Jayway.
		 * First thing, we will have to convert the "response" to string using "asString()" method.
		 * 
		 */
			
			String jsonResponse = response.asString();
			System.out.println(jsonResponse);
			// Country validation
			List<String> countryList = JsonPath.read(jsonResponse, "$..Circuits..country"); // JsonPath should be imported from jayway.jsonPath
			System.out.println("Total number of countries: = " + countryList.size());
			System.out.println(countryList);
			// Circuits validations
			int totalCircuits = JsonPath.read(jsonResponse, "$.MRData.CircuitTable.Circuits.length()");
			System.out.println("Total number of Circuits: = " + totalCircuits);	
	}
	
	/*	Class Task
	 * using the URL "https://fakestoreapi.com/products" validate the following by JsonPath queries.
	 * 	1: Validate/return rates which are smaller than 3.
	 *  2: Validate/fetch the price of a product where the ID = 3.
	 *  3: Validate/fetch titles and prices of the product where category = jewelery   --> two attributes
	 *  4: Validate/fetch titles, prices and ID of the product where category = jewelery  --> three attributes
	 *  5: Validate/fetch titles, prices, ID, rate & count of the product where category = jewelery  --> five attributes
	 */

	@Test
	public void getProductTest() {
		RestAssured.baseURI = "https://fakestoreapi.com";
		Response response = given()
								.when()	
									.get("/products");
		String jsonResponse = response.asString();
		System.out.println(jsonResponse);
		
		//Validate/return rates which are smaller than 3.
		List<Float> rateLessThanThree = JsonPath.read(jsonResponse, "$[?(@.rating.rate < 3)].rating.rate");
		System.out.println("Rates less than 3 are: " + rateLessThanThree);
		
		//Validate/fetch the price of a product where the ID = 3.
		List<Float> productPriceID3 = JsonPath.read(jsonResponse, "$[?(@.id==3)].price");
		System.out.println("Price of product # 3: " + productPriceID3);
		
		System.out.println("----------------------------------------");
		
		/*		--> Two attributes. title & price
		 * Validate/fetch titles and prices of the product where category = jewelery
		 * Since the UI from Jsonpath.com looks in key-value pair, then we will select List as data type and Map as
		 * generic.
		 */
//		List<Map<String, Object>> jewelleryList = JsonPath.read(jsonResponse, "$[?(@.category == 'jewelery')].[title,price]");
//		System.out.println("Jewellery list: " + jewelleryList);
		/*	Note: After running, the code will break right in "List<Map<String, Object>> jewelleryList = JsonPath.read(jsonResponse"
		 * 		and will throw (jsonpath.InvalidPathException), Why? although the path is correct.
		 * because the java couldn't recognize title and price, since they are pure strings they need to be covered by
		 * double quotes. Now java will assume that string starts and ended here "$[?(@.category == 'jewelery')].["
		 * in order to prevent that confusion we will use escape character \ back slash
		 */
		
		List<Map<String, Object>> jewelleryList = JsonPath.read(jsonResponse, "$[?(@.category == 'jewelery')].[\"title\",\"price\"]");
		System.out.println("Jewellery list: " + jewelleryList);
		// Qs, for students: Print the title and price separately. --> Use for each loop
		
		for(Map<String, Object> product :jewelleryList) {
			String title = (String)product.get("title");
			Object price = (Object)product.get("price");
			 
			System.out.println("title : " + title);
			System.out.println("price : " + price);
			System.out.println("--------------");
		}
		
		System.out.println("--------------");
		// With Three attributes title, price & id
		// Remember we can store as many keys as we want inside the map
		List<Map<String, Object>> jewelleryList2 = JsonPath.read(jsonResponse,
				"$[?(@.category == 'jewelery')].[\"title\",\"price\", \"id\"]");
		System.out.println("Jewellery list: " + jewelleryList);
		// Qs, for students: Print the title and price separately. --> Use for each loop
		
		for(Map<String, Object> product :jewelleryList2) {
			String title = (String)product.get("title");
			Object price = (Object)product.get("price");
			Integer id = (Integer)product.get("id");
			 
			System.out.println("title : " + title);
			System.out.println("price : " + price);
			System.out.println("id : " + id);
			System.out.println("--------------");
		}
		
		System.out.println("--------------");
		// With five attributes title, price, id, rate, & count
		// Remember we can store as many keys as we want inside the map
		List<Map<String, Object>> jewelleryList3 = JsonPath.read(jsonResponse,
				"$[?(@.category == 'jewelery')].[\"title\",\"price\", \"id\", \"rate\", \"count\"]");
		System.out.println("Jewellery list: " + jewelleryList);
		// Qs, for students: Print the title and price separately. --> Use for each loop
		
		for(Map<String, Object> product :jewelleryList3) {
			String title = (String)product.get("title");
			Object price = (Object)product.get("price");
			Integer id = (Integer)product.get("id");
			Object rate = (Object)product.get("rate");
			Integer count = (Integer)product.get("count");
			 
			System.out.println("title : " + title);
			System.out.println("price : " + price);
			System.out.println("id : " + id);
			System.out.println("rate : " + rate);
			System.out.println("count: " + count);
			System.out.println("--------------");
		}
	}
	// The values that are comma separated like the title and price in $[?(@.category == 'jewelery')].[title,price]
	// The generic for them will be Map
}
