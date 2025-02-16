package steps;

import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

public class LoginSteps {
    private static WebDriver driver;

    @Given("I navigate to the login page at {string}")
    public void navigateToLoginPage(String url) {
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        driver = new ChromeDriver();
        driver.get(url);
    }

    @When("I input {string} as username")
    public void inputUsername(String username) {
        WebElement userNameField = driver.findElement(By.id("txtUsername"));
        userNameField.sendKeys(username);
    }

    @And("I input {string} as password")
    public void inputPassword(String password) {
        WebElement passWordField = driver.findElement(By.name("password"));
        passWordField.sendKeys(password);
    }

    @And("I click on the 'Login' button")
    public void clickOnLoginButton() {
        WebElement loginButton = driver.findElement(By.id("btnLogin"));
        loginButton.click();
    }

    @Then("I should be redirected to the dashboard page")
    public void verifyDashboardPage() {
        String expectedTitle = "Dashboard";
        Assert.assertEquals(driver.getTitle(), expectedTitle);
    }

    @Then("an error message should appear")
    public void verifyErrorMessage() {
        String expectedErrorMessage = "Invalid Login";
        WebElement errorMessage = driver.findElement(By.xpath("//div[contains(text(),'" + expectedErrorMessage + "')]"));
        Assert.assertTrue(errorMessage.isDisplayed());
    }
}