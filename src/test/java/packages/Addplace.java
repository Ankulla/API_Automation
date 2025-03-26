package packages;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

import org.testng.Assert;

import files.payload;
import files.reUseableMethods;


public class Addplace {

	public static void main(String[] args) {
	
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String response=given().log().all().queryParams("key", "qaclick123").header( "Content-Type", "application/json")
		.body(payload.addPlace()).when().post("/maps/api/place/add/json")
		.then().assertThat().statusCode(200).body("scope", equalTo("APP"))
		.header("Server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();

		System.out.println(response);
		JsonPath js = reUseableMethods.rawJson(response);
		String placeId=js.getString("place_id");
		System.out.println(placeId);
		
		String newAddres="70 summer walk, MSA";
		
		given().log().all().queryParams("key", "qaclick123").header( "Content-Type", "application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+placeId+"\",\r\n"
				+ "\"address\":\""+newAddres+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}\r\n"
				+ "").
		when().put("/maps/api/place/update/json")
		.then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		String getPlaceRespone=given().log().all().queryParams("key", "qaclick123").queryParam("place_id", placeId).
		when().get("/maps/api/place/get/json").
		then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		System.out.println(getPlaceRespone);
		
		JsonPath js1=reUseableMethods.rawJson(getPlaceRespone);
		String acutualAddress=js1.getString("address");
		System.out.println(acutualAddress);
		Assert.assertEquals(acutualAddress, newAddres);
		
	}

}
