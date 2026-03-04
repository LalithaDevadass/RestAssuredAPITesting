package api.auto.restassured;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class DeleteBooking extends BaseTest 
{
	@Test
	public void deleteBookingTest()
	{
		// create booking
		Response resCreate = createBooking();
		resCreate.print();
		
		// get bookingid to delete
		int bookingid = resCreate.jsonPath().getInt("bookingid");

		Response resDelete = RestAssured.given(spec).auth().preemptive().basic("admin", "password123")
				.delete("/booking/" + bookingid);
		
		resDelete.print();
		
		// verifications
		Assert.assertEquals(resDelete.statusCode(), 201, "Status code should be 201");
		
		
		//display all booking details
		Response resGet = RestAssured.given(spec).get("/booking/" + bookingid);
		resGet.print();
		
		Assert.assertEquals(resGet.getBody().asString(), "Not Found", "Body should be 'Not Found'");
		
	}
}
