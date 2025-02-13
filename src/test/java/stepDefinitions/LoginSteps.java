package stepDefinitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.LoginPage;
import org.testng.Assert;

public class LoginSteps {
    WebDriver driver;
    LoginPage loginPage;

    @Given("User is on the login page")
    public void user_is_on_the_login_page() {
        driver = new ChromeDriver();
        driver.get("https://opensource-demo.orangehrmlive.com/");
        loginPage = new LoginPage(driver);
    }

    @When("User enters valid username and password")
    public void user_enters_valid_credentials() {
        loginPage.enterUsername("Admin");
        loginPage.enterPassword("admin123");
    }

    @And("Clicks the login button")
    public void clicks_the_login_button() {
        loginPage.clickLogin();
    }

    @Then("User should be redirected to the dashboard")
    public void user_should_be_redirected_to_the_dashboard() {
        Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"));
        driver.quit();
    }
}
