package steps;

import io.cucumber.java.AfterStep;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import pages.OrangeHRMLoginPage;
import utils.DriverFactory;

public class OrangeHRMLoginSteps {
    private WebDriver driver;
    private OrangeHRMLoginPage loginPage;

    @BeforeStep
    public void setUp() {
        driver = DriverFactory.getDriver();
        loginPage = PageFactory.initElements(driver, OrangeHRMLoginPage.class);
    }

    @AfterStep
    public void tearDown() {
        DriverFactory.quitDriver();
    }

    @Given("I open the OrangeHRM login page")
    public void openOrangeHRMLoginPage() {
        loginPage.open();
    }

    @When("I enter username \"{string}\"")
    public void enterUsername(String username) {
        loginPage.setUsername(username);
    }

    @And("I enter password \"{string}\"")
    public void enterPassword(String password) {
        loginPage.setPassword(password);
    }

    @And("I click on the login button")
    public void clickOnLoginButton() {
        loginPage.clickLogin();
    }

    @Then("I should be redirected to the dashboard")
    public void verifyRedirectToDashboard() {
        // Add assertions here for verifying redirection to the dashboard (e.g., URL verification, presence of specific elements)
    }

    @Then("I should see an error message \"{string}\"")
    public void verifyErrorMessage(String expectedErrorMessage) {
        String actualErrorMessage = loginPage.getLoginErrorMessage();
        if (!expectedErrorMessage.equals(actualErrorMessage)) {
            throw new AssertionError("Expected error message: " + expectedErrorMessage + ", but was: " + actualErrorMessage);
        }
    }
}


This Java code creates step definitions for the provided Cucumber feature file. The `OrangeHRMLoginSteps` class contains methods corresponding to each step in the scenario outlines, and these steps make use of an `OrangeHRMLoginPage` page object. The `BeforeStep` and `AfterStep` annotations are used to set up and tear down the WebDriver instance for each scenario.