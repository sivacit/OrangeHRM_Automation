import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.junit.Assert.*;

public class LoginSteps {
    private WebDriver driver;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "/path/to/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void goToLoginPage() {
        driver.get("https://www.orangehrm.com/orangehrm-3x-demo/login");
    }

    @Given("I open the OrangeHRM login page")
    public void iOpenTheOrangeHRMLoginPage() {
        goToLoginPage();
    }

    @When("I enter username {string}")
    public void iEnterUsername(String username) {
        WebElement userNameField = driver.findElement(By.id("txtUsername"));
        userNameField.sendKeys(username);
    }

    @And("I enter password {string}")
    public void iEnterPassword(String password) {
        WebElement passWordField = driver.findElement(By.id("txtPassword"));
        passWordField.sendKeys(password);
    }

    @And("I click on the login button")
    public void iClickOnTheLoginButton() {
        WebElement loginButton = driver.findElement(By.id("btnLogin"));
        loginButton.click();
    }

    @Then("I should be redirected to the dashboard")
    public void iShouldBeRedirectedToTheDashboard() {
        String expectedTitle = "OrangeHRM";
        String actualTitle = driver.getTitle();
        assertEquals(expectedTitle, actualTitle);
    }

    @Then("I should see an error message {string}")
    public void iShouldSeeAnErrorMessage(String errorMessage) {
        WebElement errorDiv = driver.findElement(By.id("yw0"));
        String actualErrorMessage = errorDiv.getText();
        assertEquals(errorMessage, actualErrorMessage);
    }
}