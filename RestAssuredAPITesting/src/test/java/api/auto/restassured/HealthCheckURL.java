package api.auto.restassured;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;

public class HealthCheckURL extends BaseTest
{
	@Test
	public void urlhealthcheck() 
	{
		
		given().spec(spec).
		when().
				get("/ping").
		then().
				assertThat().
				statusCode(201);
	}
	
	@Test
	public void headersandCookiescheck() 
	{
		
		Header someheader = new Header("Some_name","Some_value");
		spec.header(someheader);
		
		Cookie somecookie = new Cookie.Builder("Some_cookie_name", "Some_cookie_value").build();
		spec.cookie(somecookie); 
		
		Response res = RestAssured.given(spec).
				cookie("Test cookie name", "Test cookie value").
				header("Test header name", "Test header value").log().all().get("/ping");
		
		
		//Response res = RestAssured.given(spec).get("/ping");
		
		//get Headers
		Headers headers = res.getHeaders();
		System.out.println("Headers: "+ headers);
		
		Header serverheader1 = headers.get("Server");
		System.out.println(serverheader1.getName() + ": " + serverheader1.getValue());
		
		String serverheader2 = res.getHeader("Server");
		System.out.println("Server : "+ serverheader2);
		
		//get Cookies
		Cookies cookies = res.getDetailedCookies();
		System.out.println("Cookies: "+ cookies);
		
		
		
	}
}
