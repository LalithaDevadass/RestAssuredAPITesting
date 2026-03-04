package steps;

import java.time.Duration;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class NewLoginLogout {
	
	WebDriver driver;
	
	@Given("user is on the login page")
	public void user_is_on_the_login_page() {
		driver = new EdgeDriver();
		driver.get("https://www.saucedemo.com/");
		
	}

	@When("user enter username")
	public void user_enter_username() {
		driver.findElement(By.id("user-name")).sendKeys("standard_user");
	}

	@When("user eneter password")
	public void user_eneter_password() {
	     driver.findElement(By.id("password")).sendKeys("secret_sauce");
	}

	@When("user click on login button")
	public void user_click_on_login_button() {
		driver.findElement(By.id("login-button")).click();
	}

	@Then("user should be able to login and navigate to product page")
	public void user_should_be_able_to_login_and_navigate_to_product_page() {
		Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html");
	}

	@When("user click on logout")
	public void user_click_on_logout() {
		driver.findElement(By.xpath("//button[text()='Open Menu']")).click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.findElement(By.xpath("//a[text()='Logout']")).click();
	}

	@Then("user should be able to logout and come back to login page")
	public void user_should_be_able_to_logout_and_come_back_to_login_page() {
		Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/");
	}

}
