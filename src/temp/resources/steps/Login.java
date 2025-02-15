package steps;

import io.cucumber.java.AfterStep;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.Then;
import io.cucumber.java.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pages.OrangeHRMLoginPage;
import utils.BrowserUtils;
import utils.Driver;

public class LoginSteps {

    private OrangeHRMLoginPage loginPage;

    @BeforeStep
    public void setup() {
        Driver.getDriver();
        this.loginPage = new OrangeHRMLoginPage(Driver.getDriver());
        PageFactory.initElements(Driver.getDriver(), this.loginPage);
    }

    @AfterStep
    public void teardown() {
        Driver.quitDriver();
    }

    @Then("I should be redirected to the dashboard")
    public void verifyDashboardPage() {
        // Verify the URL, title or any other element specific to the dashboard page
    }

    @Then("I should see an error message {string}")
    public void verifyErrorMessage(String expectedErrorMessage) {
        BrowserUtils.verifyElementDisplayed(loginPage.getErrorMessage(), expectedErrorMessage);
    }

    @When("I enter username {string}")
    public void enterUsername(String username) {
        loginPage.setUsername(username);
    }

    @When("I enter password {string}")
    public void enterPassword(String password) {
        loginPage.setPassword(password);
    }

    @When("I click on the login button")
    public void clickLoginButton() {
        loginPage.clickOnLoginButton();
    }
}


It's important to have the following files for this code to work:
- `pages/OrangeHRMLoginPage.java` with the necessary elements locators (e.g., @FindBy annotations) and methods to interact with them.
- `utils/BrowserUtils.java` class containing utility methods used in your step definitions.
- `utils/Driver.java` class for initializing, managing, and quitting a web driver instance.
- `LoginSteps.java` contains step definitions following the Cucumber Java annotations (e.g., @Given, @When, @Then).