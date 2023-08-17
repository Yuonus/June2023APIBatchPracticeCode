package pojo;

public class Credentials {
	/*
	 * This class has been created to represent the json body ->
	 * 					{\r\n"
						+ "    \"username\" : \"admin\",\r\n"
						+ "    \"password\" : \"password123\"\r\n"
						+ "}")			
	 *  So for this body we need two private variables. because we only two attributes in body. [username & password]
	 * --> Remember we will not initialize the username and password with hard coded value, because they could have any value
	 */
	
	private String username;
	private String password;
	
	/* HOw can we define the username and password? to define them we use the constructors.
	 * To create constructor -> right click on the screen > click on source > click on "Generate constructor using Fields"
	 * Constructor needs to be public, because if it is private no one will be able to create the object of it.
	 * And remember to remove the super() keyword. 
	 * Now whenever someone is creating the Object of "Credentials" class, has to pass the username & password and the same
	 * username & password will be passed/supplied to "private String username;" & "private String password123;" with the 
	 * help of "this" keyword.
	 */
	
	public Credentials(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	/*		Now we will create "Getters" & "Setters"
	 * To create Getters & Setters -> right click on the screen > click on source > click on "Generate Getters and Setters"
	 * Click on "Select all" and click on "Generate"
	 */

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
}
