package GETAPIs;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class GetAPIWithPathParam {
	
	/*	
	 * 	Remember not forget importing the static import
	 *  	import static io.restassured.RestAssured.*;
	 *  	import static org.hamcrest.Matchers.*;For this specific automation we will be using Ergast APIs. (Formula 1 APIs)
	 *   http://ergast.com/api/f1/2016/circuits.json
	 * In order to make your code more dynamic in the service/actual URI replace the value with curly brackets and add
	 * the key you have specified.
	 * 			/api/f1/2016/circuits.json
	 * 			/api/f1/{year}/circuits.json
	 * --> in the validation part also validate the body and fetch the value of season
	 * --- Fetch the value of the circuit ID  --> This is an inner Json Array
	 */
	
	@Test
	public void getCircuitDatAPIWith_YearTest() {
		RestAssured.baseURI = "http://ergast.com";
			given().log().all()
				.pathParam("year", "2017")
					.when().log().all()
						.get("/api/f1/{year}/circuits.json")
							.then().log().all()
								.assertThat()
									.statusCode(200)
										.and()
											.body("MRData.CircuitTable.season", equalTo("2017"))
												.and()
													.body("MRData.CircuitTable.Circuits.circuitId", hasSize(20));
	}
	
	/*  Total Circuits
	 * 		2023 --> 22
	 * 		2016 --> 21
	 * 		2017 --> 20
	 * 		1996 --> 16
	 * 		1966 --> 9
	 * 
	 * How can we pass all these total of circuits to the Test without Hard coding them?
	 * We can pass them using testNG Data Provider. and then we can attach or append it with our expected method
	 * And also the method that we are passing these data should maintain a two holding parameter, because the
	 * data that we are passing has two columns
	 * Data Provider:
	 * 		--> it should be annotated with @DataProvider
	 * 		--> Data provider return type in testNG is TWO DIMENSION Array
	 */

	@DataProvider
	public Object[][] getCircuitYearData() {
		return new Object[][] {
			{"2016", 21},
			{"2017", 20},
			{"1996", 16},   // I can call this a 5X2 (five by two) Matrix
			{"1966", 9},	
			{"2023", 22}
		};
	}
	
	@Test(dataProvider = "getCircuitYearData")
	public void getCircuitDatAPIWith_Year_DataProvider(String seasonYear, int totalCircuits) {
		RestAssured.baseURI = "http://ergast.com";
			given().log().all()
				.pathParam("year", seasonYear)
					.when().log().all()
						.get("/api/f1/{year}/circuits.json")
							.then()
								.assertThat()
									.statusCode(200)
										.and()
											.body("MRData.CircuitTable.season", equalTo(seasonYear))
												.and()
													.body("MRData.CircuitTable.Circuits.circuitId", hasSize(totalCircuits));
	}
	
	/* 		--> Assignment
	 * Extract all the IDs from Circuits > CircuitId, store it in a list and print it to the console using for loop.
	 * 
	 * Hints:
	 * 		http://ergast.com/api/f1/2017/circuits.json
	 * 		"Circuits": [
                {
                    "circuitId": "albert_park",
                    "url": "http://en.wikipedia.org/wiki/Melbourne_Grand_Prix_Circuit",
                    "circuitName": "Albert Park Grand Prix Circuit",
                    "Location": {
                        "lat": "-37.8497",
                        "long": "144.968",
                        "locality": "Melbourne",
                        "country": "Australia"
                    }
	 * 
	 */
}
