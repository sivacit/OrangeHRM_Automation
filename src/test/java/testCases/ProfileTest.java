package testCases;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.time.Duration;
import pages.LoginPage;
import pages.ProfilePage;

public class ProfileTest extends BaseTest {
    private ProfilePage profilePage;
    private LoginPage loginPage;
    private WebDriverWait wait;

    @BeforeMethod
    public void setupTest() {
        loginPage = new LoginPage(driver);  // ✅ Initialize Login Page
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // ✅ Ensure login before proceeding
        if (!driver.getCurrentUrl().contains("/dashboard/index")) {
            loginPage.enterUsername(properties.getProperty("login.username"));
            loginPage.enterPassword(properties.getProperty("login.password"));
            loginPage.clickLogin();

            // ✅ Wait for dashboard to load
            wait.until(ExpectedConditions.urlContains("/dashboard/index"));
        }

        // ✅ Navigate to Profile Page manually
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/pim/viewPersonalDetails/empNumber/7");

        // ✅ Ensure Profile Page is loaded
        wait.until(ExpectedConditions.urlContains("/pim/viewPersonalDetails"));

        // ✅ Initialize Profile Page
        profilePage = new ProfilePage(driver);
    }

    @Test
    public void testUpdateProfile() {
        // Retrieve test data from properties file
        // Wait for the loader to disappear
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("oxd-form-loader")));
        
        String firstName = properties.getProperty("profile.firstName");
        String middleName = properties.getProperty("profile.middleName");
        String lastName = properties.getProperty("profile.lastName");

        // Perform profile update using ProfilePage methods
        profilePage.editProfile(firstName, middleName, lastName);

        // Validate the profile update
        Assert.assertTrue(profilePage.isProfileUpdated(), "Profile update failed!");
    }
}
