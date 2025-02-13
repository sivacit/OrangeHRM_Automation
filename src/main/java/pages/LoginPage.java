package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;


public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators optimized for Chrome
    private By usernameField = By.xpath("//input[@name='username' or @id='txtUsername']");
    private By passwordField = By.xpath("//input[@name='password' or @id='txtPassword']");
    private By loginButton = By.xpath("//button[@type='submit' or contains(text(),'Login')]");

    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // Increased wait time
    }

    // Enter username
    public void enterUsername(String username) {
        WebElement usernameElement = wait.until(ExpectedConditions.presenceOfElementLocated(usernameField));
        usernameElement.clear(); // Clear the field before typing
        usernameElement.sendKeys(username);
    }

    // Enter password
    public void enterPassword(String password) {
        WebElement passwordElement = wait.until(ExpectedConditions.presenceOfElementLocated(passwordField));
        passwordElement.clear(); // Clear the field before typing
        passwordElement.sendKeys(password);
    }

    // Click login button
    public void clickLogin() {
        WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        loginBtn.click();
    }

    // Verify login success (Optional)
    public boolean isLoginSuccessful() {
        return wait.until(ExpectedConditions.urlContains("dashboard")); // Modify this based on the expected page
    }
}
