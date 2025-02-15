package steps;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import io.cucumber.java.*;

public class LoginSteps {
    private WebDriver driver;
    private String url = "https://orangehrm.qwikker.pro/symfony/web/index.php/auth/login";
    private By usernameInput = By.id("txtUsername");
    private By passwordInput = By.name("txtPassword");
    private By loginButton = By.id("btnLogin");
    private By dashboardHeader = By.xpath("//h5[contains(text(),'Dashboard')]");
    private By errorMessage = By.xpath("//div[@id='login-box']/div[3]/div/div[2]");

    @Given("I am on the login page")
    public void openLoginPage() {
        driver = new ChromeDriver();
        driver.get(url);
    }

    @When("I enter {string} as username and {string} as password")
    public void inputCredentials(String username, String password) {
        WebElement userInput = driver.findElement(usernameInput);
        userInput.sendKeys(username);
        WebElement passInput = driver.findElement(passwordInput);
        passInput.sendKeys(password);
    }

    @And("I click the login button")
    public void clickLoginButton() {
        WebElement loginBtn = driver.findElement(loginButton);
        loginBtn.click();
    }

    @Then("I should be redirected to the dashboard")
    public void verifySuccessfulLogin() {
        WebElement dashboardHeaderElement = driver.findElement(dashboardHeader);
        Assert.assertTrue(dashboardHeaderElement.isDisplayed(), "Failed to navigate to Dashboard");
    }

    @Then("an error message should appear indicating invalid credentials")
    public void verifyInvalidLogin() {
        WebElement errorMessageElement = driver.findElement(errorMessage);
        String actualErrorMessage = errorMessageElement.getText();
        Assert.assertTrue(actualErrorMessage.contains("Invalid username or password."), "Invalid error message displayed.");
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}