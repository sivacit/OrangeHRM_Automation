package steps;

import io.cucumber.java.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.testng.Assert.*;
import java.util.concurrent.TimeUnit;

public class LoginSteps {
    private WebDriver driver;

    @Given("I am on the login page")
    public void navigateToLoginPage() {
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://www.orangehrm.com/orangehrm-30-day-trial/");
    }

    @When("I enter {string} as username and {string} as password")
    public void inputCredentials(String username, String password) {
        WebElement usernameField = driver.findElement(By.id("txtUsername"));
        WebElement passwordField = driver.findElement(By.id("txtPassword"));
        WebElement loginButton = driver.findElement(By.id("btnLogin"));

        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
    }

    @And("I click the login button")
    public void clickLoginButton() {
        WebElement loginButton = driver.findElement(By.id("btnLogin"));
        loginButton.click();
    }

    @Then("I should be redirected to the dashboard")
    public void verifyDashboard() {
        String expectedTitle = "Dashboard - OrangeHRM";
        assertEquals(driver.getTitle(), expectedTitle);
    }

    @Then("an error message should appear")
    public void verifyErrorMessage() {
        WebElement errorMessage = driver.findElement(By.cssSelector("div[id='spanMessage']"));
        String actualErrorMessage = errorMessage.getText();
        assertTrue(actualErrorMessage.contains("Invalid username or password."));
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}


Note: Replace `"path/to/chromedriver"` with the path to your chromedriver executable on your local machine.