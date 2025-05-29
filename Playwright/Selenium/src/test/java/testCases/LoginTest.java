package testCases;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import java.time.Duration;

public class LoginTest extends BaseTest {
    private LoginPage loginPage;

    @Test
    public void testValidLogin() {
        loginPage = new LoginPage(driver);  // ✅ Initialize loginPage instance
        String username = properties.getProperty("login.username");
        String password = properties.getProperty("login.password");
        
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

        // ✅ Ensure login was successful
        Assert.assertTrue(loginPage.isLoginSuccessful(), "Login failed!");
    }
}
