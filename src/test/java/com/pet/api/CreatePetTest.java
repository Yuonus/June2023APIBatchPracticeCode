package com.pet.api;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pet.api.PetLombok.Category;
import com.pet.api.PetLombok.Tag;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CreatePetTest {
	
	@Test
	public void createPetTest() {
		RestAssured.baseURI = "https://petstore.swagger.io";
		
		Category category = new Category(1, "Dog"); // We have to create the object of the Category class and import it from "com.pet.api" package
		List<String> photoUrls = Arrays.asList("https://www.dog.com", "https://www.dog1.com");
		// we can directly supply photoUrls, because it is not in key-value pair. it is just direct values. and if we have 
		// more photoUrls we can simply supply them separated with a comma.
		Tag tag1 = new Tag(5, "red");
		Tag tag2 = new Tag(6, "black");
		// we cannot directly supply "tag" because it is a List, So we will create the list then supply it.
		List<Tag> tags = Arrays.asList(tag1, tag2);
		PetLombok pet = new PetLombok(300, category, "Ronney", photoUrls, tags, "available");
		
		Response response = given()
			.contentType(ContentType.JSON)
				.body(pet)
					.when()
						.post("/v2/pet");
		System.out.println(response.statusCode());
		response.prettyPrint();
		
		// De-serialization
		ObjectMapper mapper = new ObjectMapper();
		try {
			PetLombok petRes = mapper.readValue(response.getBody().asString(), PetLombok.class);
			// 1st I am fetching super class attributes. id, name, status
			System.out.println(petRes.getId());
			System.out.println(petRes.getName());
			System.out.println(petRes.getStatus());
			
			// 2nd fetching child classes attributes
			System.out.println(petRes.getCategory().getId());
			System.out.println(petRes.getCategory().getName());
			
			System.out.println(petRes.getPhotoUrls());
			
			System.out.println(petRes.getTags().get(0).getId());
			System.out.println(petRes.getTags().get(0).getName());
			System.out.println(petRes.getTags().get(1).getId());
			System.out.println(petRes.getTags().get(1).getName());
			
			// Assertions
			Assert.assertEquals(pet.getName(), petRes.getName());
			Assert.assertEquals(pet.getId(), petRes.getId());
			Assert.assertEquals(pet.getStatus(), petRes.getStatus());
			Assert.assertEquals(pet.getPhotoUrls(), petRes.getPhotoUrls());
			Assert.assertEquals(pet.getCategory().getName(), petRes.getCategory().getName());
			Assert.assertEquals(pet.getTags().get(0).getId(), petRes.getTags().get(0).getId());
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	

	@Test
	public void createPet_WithBuilderPattern_Test() {
		RestAssured.baseURI = "https://petstore.swagger.io";
		
	// Here in Builder method we have to select the related master class (POJO class) Builder, which can be imported after
	// once we annotate our class with "@Builder" Annotation --> Master class = PetLombok
		
		Category category = new Category.CategoryBuilder()
					.id(400)
					.name("Animal")
					.build();
		
		Tag tag1 = new Tag.TagBuilder()
					.id(50)
					.name("red")
					.build();
		Tag tag2 =	new Tag.TagBuilder()
					.id(51)
					.name("black")
					.build();
/*		NOTE:
 * --> Since Tag is an array list it needs to be captured in an array list, other wise we cannot supply/add it directly.
 * --> As you can see we have two tags (1:red & 2:black), we need to create separate TagBuilder() for them.
 * --> What happens if we put them under one tag? The test will fail and it will throw "IndexOutOfBoundsException", Y?
 * 		Because it will only read the first tag and the second tag wont be read; in that case it will cause exception.
 */
		PetLombok pet =	new PetLombok.PetLombokBuilder()
					.id(5000)
					.category(category)
					.name("Robby")
					.photoUrls(Arrays.asList("https://cat.com", "https://cat1.com"))
					.tags(Arrays.asList(tag1, tag2))
					.build();
		
		Response response = given()
			.contentType(ContentType.JSON)
				.body(pet)
					.when()
						.post("/v2/pet");
		System.out.println(response.statusCode());
		response.prettyPrint();
		
		// De-serialization
		ObjectMapper mapper = new ObjectMapper();
		try {
			PetLombok petRes = mapper.readValue(response.getBody().asString(), PetLombok.class);
			// 1st I am fetching super class attributes. id, name, status
			System.out.println(petRes.getId());
			System.out.println(petRes.getName());
			System.out.println(petRes.getStatus());
			
			// 2nd fetching child classes attributes
			System.out.println(petRes.getCategory().getId());
			System.out.println(petRes.getCategory().getName());
			
			System.out.println(petRes.getPhotoUrls());
			
			System.out.println(petRes.getTags().get(0).getId());
			System.out.println(petRes.getTags().get(0).getName());
			System.out.println(petRes.getTags().get(1).getId());
			System.out.println(petRes.getTags().get(1).getName());
			
			// Assertions
			Assert.assertEquals(pet.getName(), petRes.getName());
			Assert.assertEquals(pet.getId(), petRes.getId());
			Assert.assertEquals(pet.getStatus(), petRes.getStatus());
			Assert.assertEquals(pet.getPhotoUrls(), petRes.getPhotoUrls());
			Assert.assertEquals(pet.getCategory().getName(), petRes.getCategory().getName());
			Assert.assertEquals(pet.getTags().get(0).getId(), petRes.getTags().get(0).getId());
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
}
