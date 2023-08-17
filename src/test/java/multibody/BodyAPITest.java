package multibody;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.File;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;


public class BodyAPITest {

	/*		Test Case  -- request body which the content type is not JSON. it is either text
	 * --> URL: http://httpbin.org/post
	 * --> Call: Post
	 * --> Header: Content-Type = text/plain
	 * --> Body: Hello this is Sabawoon from S_Tech.
	 * 			since it is a simple text, we can store it in a string variable in supply it to the body, or we can
	 * 			directly supply it to the body.
	 * now after hitting the API the response body will be in JSON Format.
	 * and if you want to validate the response body you can use the JsonPath method.
	 */
	
	@Test
	public void bodyWithTextTest() {
		RestAssured.baseURI = "http://httpbin.org";
		String payLoad = "Hello this is Sabawoon from S_Tech.";
		Response response = given()
			.contentType(ContentType.TEXT)
				.body(payLoad)
					.when()
						.post("/post");
		response.prettyPrint();
		System.out.println(response.statusCode());
	}
	
	/*		Test Case  -- request body which the content type is not JSON. it is JavaScript.
	 * --> URL: http://httpbin.org/post
	 * --> Call: Post
	 * --> Header: Content-Type = application/javascript
	 * 		.contentType(ContentType.TEXT) --> after "ContentType" if we write dot we will not get an suggestion for javascritp
	 * 		as we were getting for TEXT, JSON. so we will create a separate header in key-value pair.
	 * 
	 * --> Body: 	function login(){
    				let x = 10;
    				let y = 20;
    				console.log(x+y);
					}
	 * Remember that we can store the JavaScript in a string variable, and then we can supply it to the body.
	 * 
	 */
	
	@Test
	public void bodyWithJavascriptTest() {
		RestAssured.baseURI = "http://httpbin.org";
		String payLoad = "function login(){\r\n"
				+ "    let x = 10;\r\n"
				+ "    let y = 20;\r\n"
				+ "    console.log(x+y);\r\n"
				+ "}";
		
		Response response = given()
			.header("Content-Type", "application/javascript")
				.body(payLoad)
					.when()
						.post("/post");
		response.prettyPrint();
		System.out.println(response.statusCode());
	}
	

	/*		Test Case  -- request body which the content type is not JSON. it is HTML.
	 * --> URL: http://httpbin.org/post
	 * --> Call: Post
	 * --> Header: Content-Type = text/html
	 * 
	 * --> Body: 	<!DOCTYPE html>
					<html dir="ltr" lang="en">
					<head>
					<meta charset="UTF-8" />
					</head>
					</html>
	 * Remember that we can store the HTML in a string variable, and then we can supply it to the body.
	 * 
	 */
	
	@Test
	public void bodyWithHTMLTest() {
		RestAssured.baseURI = "http://httpbin.org";
		String payLoad = "<!DOCTYPE html>\r\n"
				+ "<html dir=\"ltr\" lang=\"en\">\r\n"
				+ "<head>\r\n"
				+ "<meta charset=\"UTF-8\" />\r\n"
				+ "</head>\r\n"
				+ "</html>";
		
		Response response = given()
				.contentType(ContentType.HTML)
				.body(payLoad)
					.when()
						.post("/post");
		response.prettyPrint();
		System.out.println(response.statusCode());
	}
	
	/*		Test Case  -- request body which the content type is not JSON. it is XML.
	 * --> URL: http://httpbin.org/post
	 * --> Call: Post
	 * --> Header: Content-Type = application/xml
	 * --> Body: 	<note>
    					<to>Tove</to>
    					<from>Jani</from>
					    <heading>Reminder</heading>
					    <body>Don't forget me this weekend!</body>
					</note>
	 * Remember that we can store the XML in a string variable, and then we can supply it to the body.
	 * 
	 */
	
	@Test
	public void bodyWithXMLTest() {
		RestAssured.baseURI = "http://httpbin.org";
		String payLoad = "<note>\r\n"
				+ "    <to>Tove</to>\r\n"
				+ "    <from>Jani</from>\r\n"
				+ "    <heading>Reminder</heading>\r\n"
				+ "    <body>Don't forget me this weekend!</body>\r\n"
				+ "</note>";
		
		Response response = given()
				.contentType(ContentType.XML)
				.body(payLoad)
					.when()
						.post("/post");
		response.prettyPrint();
		System.out.println(response.statusCode());
	}
	
	/*		Test Case  -- request body which the content type is not JSON. it is form-Data.
	 * --> URL: http://httpbin.org/post
	 * --> Call: Post
	 * --> Header: Content-Type = multipart/form-data; boundary=<calculated when request is sent>
	 * --> Body: We don't have any body with multipart/form-data. we only have form-data which is in key-value pair format
	 * --> multiPart(): We will use multiPart() to pass the key & value pairs to it
	 * how can we pass the value of a key which is a file?
	 * We can supply/upload the file to the rest assured with the help of File class object. and remember to give the path
	 * of that particular file to the File class object.
	 */
	
	@Test
	public void bodyWithFormDataMultiPartTest() {
		RestAssured.baseURI = "http://httpbin.org";
		Response response = given().log().all()
				.contentType(ContentType.MULTIPART)
				.multiPart("name", "Sabawoon")
				.multiPart("key", "testing")
				.multiPart("fileName", new File("C:\\Users\\syuon\\Documents\\YuonusResume.pdf"))
					.when().log().all()
						.post("/post");
		response.prettyPrint();
		System.out.println(response.statusCode());
	}
	
	/*		Test Case  -- request body which the content type is not JSON. it is binary.
	 * --> URL: http://httpbin.org/post
	 * --> Call: Post
	 * --> Header: Content-Type = image/jpeg . (the type of the header depends on the type of the file, if
	 * 		doc file is attached it will be "application/doc" if it excel, it will be "application/xls" ...)
	 * --> Since we don't get any suggestion for content-type in IDE, we will create our own content-type with the help
	 * 		of the .header() method.
	 * --> With binary only one file is attached at a time
	 * --> Body: We can supply the body by providing the path of the file to .body() method as parameter with the help
	 * 		File class.
	 * Note: Remember when you are copying the path the java will add the escape sequence like 
	 * \"C:\\Users\\syuon\\Desktop\\me.jpg\" --> so try to remove the first and last escape sequence
	 * C:\\Users\\syuon\\Desktop\\me.jpg  --> right one
	 */
	
	@Test
	public void bodyWithBinaryFileTest() {
		RestAssured.baseURI = "http://httpbin.org";
		Response response = given().log().all()
			.header("Content-Type", "image/jpeg")
				.body(new File("C:\\Users\\syuon\\Desktop\\me.jpg"))
					.when()
						.post("/post");
		response.prettyPrint();
		System.out.println(response.statusCode());
	}
	
	/*			X-www-form-Urlecoded
	 * This body was covered right at "PostAPIs" package under "OAuth2Test.java" Class
	 * 
	 */
	@Test
	public void bodyWithX_wwwFormUrlencoded_Test() {

	}
	
}
