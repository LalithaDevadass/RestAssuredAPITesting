package api.auto.restassured;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PartialUpdateBooking extends BaseTest 
{
	@Test
	public void partialupdateBookingTest() 
	{
		// create booking
		Response resCreate = createBooking();

		resCreate.print();

		// get bookingid of new booking
		int bookingid = resCreate.jsonPath().getInt("bookingid");

		// Create JSON body
		JSONObject body = new JSONObject();
		body.put("firstname", "Peter");
		
		JSONObject bookingdates = new JSONObject();
		bookingdates.put("checkin", "2026-01-10");
		bookingdates.put("checkout", "2026-01-15");
		body.put("bookingdates", bookingdates);
		

		// partial update booking
		Response resUpdate = RestAssured.given(spec).auth().preemptive().basic("admin", "password123").
				contentType(ContentType.JSON).body(body.toString())
				.patch("/booking/" + bookingid);
		
		resUpdate.print();
		

		// verifications
		Assert.assertEquals(resUpdate.statusCode(), 200, "Status code should be 200");

		SoftAssert softassert = new SoftAssert();

		String actualfirstname = resUpdate.jsonPath().getString("firstname");
		softassert.assertEquals(actualfirstname, "Peter", "Firstname in response is not expected");

		String actuallastname = resUpdate.jsonPath().getString("lastname");
		softassert.assertEquals(actuallastname, "Brown", "Lastname in response is not expected");

		int price = resUpdate.jsonPath().getInt("totalprice");
		softassert.assertEquals(price, 150, "Price in response is not expected");

		boolean depositpaid = resUpdate.jsonPath().getBoolean("depositpaid");
		softassert.assertTrue(depositpaid, "Deposit paid should be True");

		String actualcheckin = resUpdate.jsonPath().getString("bookingdates.checkin");
		softassert.assertEquals(actualcheckin, "2026-01-10", "checkin in response is not expected");

		String actualcheckout = resUpdate.jsonPath().getString("bookingdates.checkout");
		softassert.assertEquals(actualcheckout, "2026-01-15", "checkout in response is not expected");

		String actualadditionalneeds = resUpdate.jsonPath().getString("additionalneeds");
		softassert.assertEquals(actualadditionalneeds, "Dinner", "additionalneeds in response is not expected");

		softassert.assertAll();

	}

}
