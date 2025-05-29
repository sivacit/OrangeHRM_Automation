package steps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.asserts.SoftAssert;

public class LoginSteps {
    private WebDriver driver = DriverManager.getDriver();
    private static final String URL = "https://orangehrm.com/orangehrm-live-60day-trial/";
    private SoftAssert softAssert = new SoftAssert();

    public void openURL() {
        driver.get(URL);
    }

    @FindBy(id="txtUsername")
    WebElement usernameField;

    @FindBy(id="password")
    WebElement passwordField;

    @FindBy(id="btnLogin")
    WebElement loginButton;

    public void enterCredentials(String user, String pass) {
        usernameField.sendKeys(user);
        passwordField.sendKeys(pass);
    }

    public void clickLoginButton() {
        loginButton.click();
    }

    public void verifyDashboardPage() {
        softAssert.assertTrue(driver.getTitle().contains("Dashboard"));
        softAssert.assertAll();
    }

    @FindBy(xpath="//div[text()='Invalid username or password.']")
    WebElement invalidCredentialsError;

    public void verifyInvalidCredentialsError() {
        softAssert.assertTrue(invalidCredentialsError.isDisplayed());
        softAssert.assertAll();
    }
}
