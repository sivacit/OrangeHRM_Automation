package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;

import java.util.Map;

public class LoginSteps {
    private WebDriver driver;

    @Given("I am on the login page of the {string}")
    public void navigateToLoginPage(String url) {
        driver = new FirefoxDriver();
        driver.get(url);
    }

    @When("I enter username as {string} and password as {string}")
    public void enterCredentials(String username, String password) {
        WebElement userNameField = driver.findElement(By.id("txtUsername"));
        WebElement passWordField = driver.findElement(By.id("txtPassword"));
        WebElement signInButton = driver.findElement(By.id("btnLogin"));

        userNameField.sendKeys(username);
        passWordField.sendKeys(password);
        signInButton.click();
    }

    @And("I click 'Sign In' button")
    public void clickSignInButton() {
        WebElement signInButton = driver.findElement(By.id("btnLogin"));
        signInButton.click();
    }

    @Then("I should be logged in successfully")
    public void verifySuccessfulLogin() {
        String expectedDashboardTitle = "Dashboard";
        String actualDashboardTitle = driver.getTitle();
        Assert.assertEquals(actualDashboardTitle, expectedDashboardTitle);
    }

    @Then("I should not be able to log in successfully")
    public void verifyUnsuccessfulLogin() {
        WebElement errorMessage = driver.findElement(By.id("spanMessage"));
        String actualErrorMessage = errorMessage.getText();
        Assert.assertEquals(actualErrorMessage, "Invalid username or password.");
    }

    @When("I enter username as {string} and password as {string}")
    public void enterInvalidCredentials(DataTable table) {
        Map<String, String> credentials = table.asMap(String.class, String.class);
        String username = credentials.get("username");
        String password = credentials.get("password");
        this.enterCredentials(username, password);
    }
}