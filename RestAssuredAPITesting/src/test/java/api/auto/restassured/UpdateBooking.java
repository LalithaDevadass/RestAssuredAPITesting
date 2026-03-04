package api.auto.restassured;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UpdateBooking extends BaseTest {
	@Test
	public void updateBookingTest() {
		// create booking
		Response resCreate = createBooking();

		resCreate.print();

		// get bookingid of new booking
		int bookingid = resCreate.jsonPath().getInt("bookingid");

		// Create JSON body
		JSONObject body = new JSONObject();
		body.put("firstname", "John");
		body.put("lastname", "David");
		body.put("totalprice", 100);
		body.put("depositpaid", false);

		JSONObject bookingdates = new JSONObject();
		bookingdates.put("checkin", "2026-01-01");
		bookingdates.put("checkout", "2026-01-05");
		body.put("bookingdates", bookingdates);
		body.put("additionalneeds", "Dinner");

		// update booking
		Response resUpdate = RestAssured.given(spec).auth().preemptive().basic("admin", "password123").
				contentType(ContentType.JSON).body(body.toString())
				.put("/booking/" + bookingid);
		
		resUpdate.print();
		

		// verifications
		Assert.assertEquals(resUpdate.statusCode(), 200, "Status code should be 200");

		SoftAssert softassert = new SoftAssert();

		String actualfirstname = resUpdate.jsonPath().getString("firstname");
		softassert.assertEquals(actualfirstname, "John", "Firstname in response is not expected");

		String actuallastname = resUpdate.jsonPath().getString("lastname");
		softassert.assertEquals(actuallastname, "David", "Lastname in response is not expected");

		int price = resUpdate.jsonPath().getInt("totalprice");
		softassert.assertEquals(price, 100, "Price in response is not expected");

		boolean depositpaid = resUpdate.jsonPath().getBoolean("depositpaid");
		softassert.assertFalse(depositpaid, "Deposit paid should be False");

		String actualcheckin = resUpdate.jsonPath().getString("bookingdates.checkin");
		softassert.assertEquals(actualcheckin, "2026-01-01", "checkin in response is not expected");

		String actualcheckout = resUpdate.jsonPath().getString("bookingdates.checkout");
		softassert.assertEquals(actualcheckout, "2026-01-05", "checkout in response is not expected");

		String actualadditionalneeds = resUpdate.jsonPath().getString("additionalneeds");
		softassert.assertEquals(actualadditionalneeds, "Dinner", "additionalneeds in response is not expected");

		softassert.assertAll();

	}

}
