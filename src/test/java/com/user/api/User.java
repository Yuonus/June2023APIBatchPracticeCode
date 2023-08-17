package com.user.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
	
	@JsonProperty("id") // This variable comes from response, as you know that the body from request side doesn't have any id
	private Integer id; // And the server literally creates the id. since we want to have Request and Response POJOs together that is Y we created the variable for id as well
	
	@JsonProperty ("name")
	private String name;
	
	@JsonProperty ("email")
	private String email;
	
	@JsonProperty ("gender")
	private String gender;
	
	@JsonProperty ("status")
	private String status;

	/*	While creating the object of the User class in Test class it will also call the id at the time of request sending
	 * 	since we don't need any id at request body, and we know that the server will create the id itself and we can also
	 * 	not ignore it, this will cause a problem. SO, to prevent this problem we will create one custom constructor
	 * 	where we will not include the id, then upon calling the constructor we will get two suggestion and we will go with
	 * 	the constructor that doesn't have id.
	 */

	public User(String name, String email, String gender, String status) {
		this.name = name;
		this.email = email;
		this.gender = gender;
		this.status = status;
	}
}

/*	Note:
 * If you want to have a different name for your private variables you can "@JsonPropery()" annotation from jackson
 * 	--> put the exact name of the body's attribute in "@JsonProperty()" annotation
 * 	--> put any name in data fields  --> 
 * Example: But, i will keep the same name for my annotation and data variables
			 	@JsonProperty ("name")
				private String userName;
				
				@JsonProperty ("email")
				private String userEmail;
				
				@JsonProperty ("gender")
				private String userGender;
				
				@JsonProperty ("status")
				private String userStatus;
	Note: when we create a JSON out of this particular POJO, the prices will be taken from "@JsonProperty()" annotation 
 * 
 */

