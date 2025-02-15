package steps;

import io.cucumber.java.AfterStep;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.DriverManager;

public class LoginSteps {
    private WebDriver driver;
    private LoginPage loginPage;

    @BeforeStep
    public void setup() {
        driver = DriverManager.getDriver();
        loginPage = PageFactory.initElements(driver, LoginPage.class);
    }

    @AfterStep
    public void tearDown() {
        DriverManager.quitDriver();
    }

    @Given("I open the OrangeHRM login page")
    public void open_the_orangehrm_login_page() {
        driver.get("http://orangehrm.url");
    }

    @When("I enter username \"(.*)\"")
    public void iEnterUsername(String username) {
        loginPage.setUsername(username);
    }

    @When("I enter password \"(.*)\"")
    public void iEnterPassword(String password) {
        loginPage.setPassword(password);
    }

    @When("I click on the login button")
    public void iClickOnTheLoginButton() {
        loginPage.clickLogin();
    }

    @Then("I should be redirected to the dashboard")
    public void iShouldBeRedirectedToTheDashboard() {
        // Add assertions here to check if user is redirected to the dashboard
    }

    @Then("I should see an error message \"(.*)\"")
    public void iShouldSeeAnErrorMessage(String errorMessage) {
        // Add assertions here to check if error message is displayed
    }

    static class LoginPage {
        @FindBy(id = "txtUsername")
        private WebElement usernameField;

        @FindBy(id = "password")
        private WebElement passwordField;

        @FindBy(id = "btnLogin")
        private WebElement loginButton;

        public void setUsername(String username) {
            usernameField.sendKeys(username);
        }

        public void setPassword(String password) {
            passwordField.sendKeys(password);
        }

        public void clickLogin() {
            loginButton.click();
        }
    }
}