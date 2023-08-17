package com.product.api;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ProductAPITest {
	
	@Test
	public void getProductTest_With_POJO() {
		RestAssured.baseURI = "https://fakestoreapi.com";
		Response response = given()
								.when()
									.get("/products");
		/*		json to POJO Mapping--> De-serialization
		 * -> For deserialization well use jackson API and ObjectMapper class which is available inside the deserialization
		 * With the help of OjbectMapper class and readValue() method we will convert json to pojo. as we will pass the 
		 * captured response as parameter to readValue() and then, firstly; we will capture the body and we will convert 
		 * the body to normal string using asString() method. after that map it with the related class which is "Product.class"
		 * and while doing this process any kind of exception that is coming surround it in try-catch-block.
		 * 
		 * Why did we store readValue() in product array and Y did we change the product.class to Product[].class?
		 * Because if we just directly run the test without explicitly storing them in an array it will throw "MismatchedInputException"
		 * and the exception will tell you "cannot deserialize value of Product from Array" that is Y we need to store
		 * it in an array.
		 */
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			Product product[] = mapper.readValue(response.getBody().asString(), Product[].class);
			// Fetch/validate Id, title, price, category, ...
			for(Product p : product) {
				System.out.println("ID: " + p.getId());
				System.out.println("Title: " + p.getTitle());
				System.out.println("Price: " + p.getPrice());
				System.out.println("Description: " + p.getDescription());
				System.out.println("Category: " + p.getCategory());
				System.out.println("Image: " + p.getImage());
				System.out.println("Rate: " + p.getRating().getRate());
				System.out.println("Count: " + p.getRating().getCount());
				System.out.println("------------------");
			}
			
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	/*		By now we have covered two approaches: 1: using the JQL queries and 2: de-serialization
	 * Both approaches are best, it is in case if you join a company that uses one of them, so you should have a prior
	 * knowledge, but I will recommend the JQL query approach.
	 */
	
	@Test
	public void getProductTest_With_POJO_Lombok() {
		RestAssured.baseURI = "https://fakestoreapi.com";
		Response response = given()
								.when()
									.get("/products");
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			ProductLombok product[] = mapper.readValue(response.getBody().asString(), ProductLombok[].class);
			// Fetch/validate Id, title, price, category, ...
			for(ProductLombok p : product) {
				System.out.println("ID: " + p.getId());
				System.out.println("Title: " + p.getTitle());
				System.out.println("Price: " + p.getPrice());
				System.out.println("Description: " + p.getDescription());
				System.out.println("Category: " + p.getCategory());
				System.out.println("Image: " + p.getImage());
				System.out.println("Rate: " + p.getRating().getRate());
				System.out.println("Count: " + p.getRating().getCount());
				System.out.println("------------------");
			}
			
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getProductTest_With_POJO_Lombok_BuilderPattern() {
		RestAssured.baseURI = "https://fakestoreapi.com";
		
	}
}
