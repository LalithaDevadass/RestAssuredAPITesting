package api.auto.restassured;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;

public class GetBooking extends BaseTest 
{
	@Test(enabled=false)
	public void getBookingTest() {
		// create booking
		Response resCreate = createBooking();

		resCreate.print();


		// set path parameter
		spec.pathParam("bookingId", resCreate.jsonPath().getInt("bookingid"));

		// display all booking details
		Response res = RestAssured.given(spec).get("/booking/{bookingId}");
		res.print();

		// verify response code 200
		Assert.assertEquals(res.statusCode(), 200, "Status code should be 200");

		// verify all booking details
		SoftAssert softassert = new SoftAssert();

		String actualfirstname = res.jsonPath().getString("firstname");
		softassert.assertEquals(actualfirstname, "Kim", "Firstname in response is not expected");

		String actuallastname = res.jsonPath().getString("lastname");
		softassert.assertEquals(actuallastname, "Brown", "Lastname in response is not expected");

		int price = res.jsonPath().getInt("totalprice");
		softassert.assertEquals(price, 150, "Price in response is not expected");

		boolean depositpaid = res.jsonPath().getBoolean("depositpaid");
		softassert.assertTrue(depositpaid, "Deposit paid should be true");

		String actualcheckin = res.jsonPath().getString("bookingdates.checkin");
		softassert.assertEquals(actualcheckin, "2026-01-01", "checkin in response is not expected");

		String actualcheckout = res.jsonPath().getString("bookingdates.checkout");
		softassert.assertEquals(actualcheckout, "2026-01-05", "checkout in response is not expected");

		String actualadditionalneeds = res.jsonPath().getString("additionalneeds");
		softassert.assertEquals(actualadditionalneeds, "Dinner", "additionalneeds in response is not expected");

		softassert.assertAll();

	}
	
	@Test
	public void getBookingXMLTest() {
		// create booking
		Response resCreate = createBooking();
		resCreate.print();


		// set path parameter
		spec.pathParam("bookingId", resCreate.jsonPath().getInt("bookingid"));
		
		
		//get response with booking
		Header xml = new Header("Accept","application/xml");
		spec.header(xml);
		

		// display all booking details
		Response res = RestAssured.given(spec).get("/booking/{bookingId}");
		res.print();

		// verify response code 200
		Assert.assertEquals(res.statusCode(), 200, "Status code should be 200");

		// verify all booking details
		SoftAssert softassert = new SoftAssert();

		String actualfirstname = res.xmlPath().getString("booking.firstname");
		softassert.assertEquals(actualfirstname, "Kim", "Firstname in response is not expected");

		String actuallastname = res.xmlPath().getString("booking.lastname");
		softassert.assertEquals(actuallastname, "Brown", "Lastname in response is not expected");

		int price = res.xmlPath().getInt("booking.totalprice");
		softassert.assertEquals(price, 150, "Price in response is not expected");

		boolean depositpaid = res.xmlPath().getBoolean("booking.depositpaid");
		softassert.assertTrue(depositpaid, "Deposit paid should be true");

		String actualcheckin = res.xmlPath().getString("booking.bookingdates.checkin");
		softassert.assertEquals(actualcheckin, "2026-01-01", "checkin in response is not expected");

		String actualcheckout = res.xmlPath().getString("booking.bookingdates.checkout");
		softassert.assertEquals(actualcheckout, "2026-01-05", "checkout in response is not expected");

		String actualadditionalneeds = res.xmlPath().getString("booking.additionalneeds");
		softassert.assertEquals(actualadditionalneeds, "Dinner", "additionalneeds in response is not expected");

		softassert.assertAll();

	}
}
