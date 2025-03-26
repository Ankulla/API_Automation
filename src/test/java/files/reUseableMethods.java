package files;

import io.restassured.path.json.JsonPath;

public class reUseableMethods {
	
	public static JsonPath rawJson(String response) {
		
		JsonPath js=new JsonPath(response);
		
		return js;
	}

}
