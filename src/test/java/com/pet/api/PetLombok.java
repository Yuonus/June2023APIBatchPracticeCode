package com.pet.api;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PetLombok {
	
	/*
	 * {
    "id": 200,
    "category": {
        "id": 1,
        "name": "Pop"
    },
    "name": "Ronney",
    "photoUrls":[
        "https://www.dog.com",
        "https://www.dog1.com"
    ],
    "tags":[
        {
           "id": 10,
           "name": "red" 
        }
    ],
    "status": "available"
}
	 */
	private Integer id;
	private Category category;
	private String name;
	private List<String> photoUrls;
	private List<Tag> tags;
	private String status;
	
	// Time to create static inner classes.
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class Category{
		private Integer id;
		private String name;
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class Tag{
		private Integer id;
		private String name;
	}
	
	/*		Question & Answer
	 * 1: in Generics Sring is a data type so, it can be a generic, but Tag is not a data type how could we define it
	 * 		as generic?
	 * Ans: Listen if you hover the mouse over the "String" it will tell u that it is class, so the same way "Tag" is also
	 * a class. So, in the generics you can have any class & interface
	 * 
	 * How do you read followings? Or what do we store inside them?
	 * 			1: private List<Tag> tags;
	 * 			2: private List<String> photoUrls;
	 * 1: We are going to store number of tag objects 
	 * 2: In this particular list we are going to store number of photoUrls which are string type
	 * 
	 */
	

}
