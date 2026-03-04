package api.auto.restassured;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CreateBooking extends BaseTest
{
	@Test(enabled=false)
	public void createBookingTest() {
		Response res = createBooking();
		res.print();

		// verifications
		Assert.assertEquals(res.statusCode(), 200, "Status code should be 200");

		SoftAssert softassert = new SoftAssert();

		String actualfirstname = res.jsonPath().getString("booking.firstname");
		softassert.assertEquals(actualfirstname, "Kim", "Firstname in response is not expected");

		String actuallastname = res.jsonPath().getString("booking.lastname");
		softassert.assertEquals(actuallastname, "Brown", "Lastname in response is not expected");

		int price = res.jsonPath().getInt("booking.totalprice");
		softassert.assertEquals(price, 150, "Price in response is not expected");

		boolean depositpaid = res.jsonPath().getBoolean("booking.depositpaid");
		softassert.assertTrue(depositpaid, "Deposit paid should be true");

		String actualcheckin = res.jsonPath().getString("booking.bookingdates.checkin");
		softassert.assertEquals(actualcheckin, "2026-01-01", "checkin in response is not expected");

		String actualcheckout = res.jsonPath().getString("booking.bookingdates.checkout");
		softassert.assertEquals(actualcheckout, "2026-01-05", "checkout in response is not expected");

		String actualadditionalneeds = res.jsonPath().getString("booking.additionalneeds");
		softassert.assertEquals(actualadditionalneeds, "Dinner", "additionalneeds in response is not expected");

		softassert.assertAll();

	}

	@Test
	public void createBookingwithPOJOTest() 
	{
		// Create body using POJOs (plain old java objects)
		Bookingdates bookingdates = new Bookingdates("2026-01-01", "2026-01-05");
		Booking booking = new Booking("John", "David", 200, false, bookingdates, "Dinner");
		

		// Get response
		Response res = RestAssured.given(spec).contentType(ContentType.JSON).body(booking).post("/booking");
		res.print();
		
		Bookingid bookingid = res.as(Bookingid.class);

		// verifications
		Assert.assertEquals(res.statusCode(), 200, "Status code should be 200");

		System.out.println("Request Booking  :" + booking.toString());
		System.out.println("Response Booking :" + bookingid.getBooking().toString());
		
		Assert.assertEquals(bookingid.getBooking().toString(), booking.toString());

	}

}
