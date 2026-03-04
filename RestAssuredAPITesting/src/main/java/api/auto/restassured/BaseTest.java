package api.auto.restassured;

import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseTest 
{
	protected RequestSpecification spec;
	
	@BeforeMethod
	public void setup()
	{
		spec = new RequestSpecBuilder().
			setBaseUri("https://restful-booker.herokuapp.com").
			build();
	}
	
	
	protected Response createBooking() 
	{
		//Create JSON body
		JSONObject body = new JSONObject();
		body.put("firstname","Kim");
		body.put("lastname", "Brown");
		body.put("totalprice", 150);
		body.put("depositpaid", true);
		
		JSONObject bookingdates = new JSONObject();
		bookingdates.put("checkin", "2026-01-01");
		bookingdates.put("checkout", "2026-01-05");
		body.put("bookingdates", bookingdates);
		body.put("additionalneeds", "Dinner");
		
		//Get response
		Response res = RestAssured.given(spec).contentType(ContentType.JSON).
				body(body.toString()).post("/booking");
		return res;
	}
}
