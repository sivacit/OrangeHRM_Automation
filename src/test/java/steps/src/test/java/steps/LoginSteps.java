package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;

public class LoginSteps {
    private WebDriver driver;

    @Before
    public void setUp() {
        driver = new FirefoxDriver();
        driver.get("https://www.orangehrm.com/orangehrm-30-day-trial/");
    }

    @Given("I am on the login page")
    public void i_am_on_the_login_page() {
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
    }

    @When("I enter {string} as username and {string} as password")
    public void i_enter_as_username_and_as_password(String username, String password) {
        WebElement userNameField = driver.findElement(By.id("txtUsername"));
        userNameField.sendKeys(username);

        WebElement passwordField = driver.findElement(By.id("txtPassword"));
        passwordField.sendKeys(password);
    }

    @And("I click 'Sign In' button")
    public void i_click_sign_in_button() {
        WebElement signInButton = driver.findElement(By.id("btnLogin"));
        signInButton.click();
    }

    @Then("I should be redirected to the dashboard")
    public void i_should_be_redirected_to_the_dashboard() {
        String dashboardUrl = "https://opensource-demo.orangehrmlive.com/web/index2.php/dashboard/index";
        Assert.assertEquals(driver.getCurrentUrl(), dashboardUrl, "Failed to navigate to the dashboard.");
    }

    @Then("an error message should appear")
    public void an_error_message_should_appear() {
        String errorMessage = driver.findElement(By.id("spanMessage")).getText();
        Assert.assertNotNull(errorMessage, "No error message was displayed.");
        // Additional validation of the actual error message can be added if needed
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}