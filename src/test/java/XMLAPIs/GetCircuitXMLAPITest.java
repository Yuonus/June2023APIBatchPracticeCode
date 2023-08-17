package XMLAPIs;

import java.util.List;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class GetCircuitXMLAPITest {
	
	/*		Test Case
	 * Validate the data which is coming in the form of XML format.
	 * --> URL: http://ergast.com/api/f1/2017/circuits.xml
	 * --> Call: GET
	 * 
	 * 		--> Fetch all the circuitNames
	 * 		--> Fetch all the circuit IDs, and verify how many circuit IDs are there
	 * 		--> Give me the locality where circuitId = americas
	 * 		--> Give me the lat & long values where circuitId = bahrain
	 * 		--> Give me the locality where circuitId = americas & circuitId = bahrain
	 * 		--> Give me the circuit name where circuitId = americas
	 * 		--> Give me the url Where circuitId = americas
	 * 
	 * 	HomeWork
	 * 		--> Give me the location & locality where circuitId = americas & circuitId = bahrain
	 * 		--> Collect all the urls, and findout how many URLs are used in the circuit
	 * 		--> Collect the list of all Countries names. 
	 */
	
	@Test
	public void xmlTest() {
		RestAssured.baseURI = "http://ergast.com";
	Response response =  RestAssured.given() // When don't statically import, we can use RestAssured.given()
		 			.when()
		 				.get("/api/f1/2017/circuits.xml")
		 					.then()
		 						.extract().response();
	/* To print the response body or to get response body capture the body, and convert it to string 
	 * and store it in a string variable.
	 */
	String responseBody = response.body().asString();
	System.out.println(responseBody);
	
	/*	After capturing the body, Create the object of XMLPath. XMLPath is coming from rest assured.
	 *  Supply the response body as an argument to XmlPath. using this xmlPath we can write queries on the XML response 
	 *  body that we have captured in the form of string.
	 */
	XmlPath xmlPath = new XmlPath(responseBody);
	
		// Fetching all the circuitNames
		List<String> circuitNames = xmlPath.getList("MRData.CircuitTable.Circuit.CircuitName");
		for(String e : circuitNames) {
			System.out.println(e);
		}
		System.out.println("Number of circuits: " + circuitNames.size());
		System.out.println("---------------------------------------");
		
		// Fetching all the Circuit IDs
		List<String> circuitIds = xmlPath.getList("MRData.CircuitTable.Circuit.@circuitId");
		for(String e : circuitIds) {
			System.out.println(e);
		}
		System.out.println("There are "+ circuitIds.size()+ " Circuit IDs");
		System.out.println("------------------------------------------");
		
		/* Fetch locality where circuitId = americas
		 *  Write two stars, use the deep scan method findAll, use two curly brackets, inside two curly brackets use
		 *  another object which is called "it" object. this syntax is called "groovy"syntax.
		 *  At the end convert it to string using toString() method, if it is not working.
		 *  "**.findAll {it.@circuitId == 'americas'}" --> groovy syntax. Rest assured uses the groovy syntax internally
		 *  to validate such kind of syntaxes
		 *  
		 *  		---> **.findAll => deep scan
		 *  		---> it => groovy object which is available inside the rest assured
		 *  		---> With groovy it is recommended to fetch only one attribute
		 */
		String locality = xmlPath.get("**.findAll {it.@circuitId == 'americas'}.Location.Locality").toString();
		// You can also remove toString(), the test will not fail
		System.out.println("Lcality: " + locality);
		System.out.println("------------------------------------------");
		
		//Fetch the lat & long values where circuitId = bahrain
		String latVal = xmlPath.get("**.findAll {it.@circuitId == 'bahrain'}.Location.@lat");
		String longVal = xmlPath.get("**.findAll {it.@circuitId == 'bahrain'}.Location.@long");
		System.out.println("Bahrain Lat value is: " + latVal);
		System.out.println("Bahrain Long value is: " + longVal);
		
		System.out.println("------------------------------------------");
		//Fetch the locality where circuitId = americas & circuitId = bahrain
		String data = xmlPath.get("**.findAll {it.@circuitId =='americas' || it.@circuitId =='bahrain'}.Location.Locality").toString();
		System.out.println(data);
		System.out.println("------------------------------------------");
		
		//the circuit name where circuitId = americas
		String CircuitName = xmlPath.get("**.findAll {it.@circuitId == 'americas'}.CircuitName").toString();
		System.out.println(CircuitName);
		
		System.out.println("-----------------------------------------");
		//Fetch the url Where circuitId = americas
		String url = xmlPath.get("**.findAll {it.@circuitId =='americas'}.@url");
		System.out.println(url);
		
		System.out.println("--------------------HomeWork------------------------------");
		//Fetching all the URLs
		List<String> urls= xmlPath.getList("MRData.CircuitTable.Circuit.@url");
		for(String e : urls) {
			System.out.println(e);
		}
		System.out.println("Number of URLs used: " + urls.size());
		
		System.out.println("-----------------------------------------");
		//Collect the list of all Countries names.
		List<String> countryNames = xmlPath.getList("MRData.CircuitTable.Circuit.Location.Country");
		for(String e : countryNames) {
			System.out.println(e);
		}
		
		System.out.println("-----------------------------------------");
		//Fetch the location & locality where circuitId = americas & circuitId = bahrain
//		String location = xmlPath.get("**.findAll {it.@circuitId =='americas'}.Location");
//		System.out.println(location);
		
	}

}
