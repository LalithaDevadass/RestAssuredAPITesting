package api.auto.restassured;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class GetBookingIds extends BaseTest {
	@Test
	public void getBookingIdswithoutFilterTest() {
		// display all booking ids
		Response res = RestAssured.given(spec).get("/booking");
		res.print();

		// verify response code 200
		Assert.assertEquals(res.statusCode(), 200, "Status code should be 200");

		// verify the bookingids are not empty
		List<Integer> bookingIds = res.jsonPath().getList("bookingid");
		Assert.assertFalse(bookingIds.isEmpty(), "List of Booking Ids are empty");
	}

	@Test
	public void getBookingIdswithFilterTest() 
	{
		//add query parameters
		spec.queryParam("firstname", "Susan");
		spec.queryParam("lastname", "Wilson");
		
		
		// display all booking ids
		Response res = RestAssured.given(spec).get("/booking");
		res.print();

		// verify response code 200
		Assert.assertEquals(res.statusCode(), 200, "Status code should be 200");

		// verify the bookingids are not empty
		List<Integer> bookingIds = res.jsonPath().getList("bookingid");
		Assert.assertFalse(bookingIds.isEmpty(), "List of Booking Ids are empty");
	}

}
