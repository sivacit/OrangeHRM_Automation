package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    By usernameField = By.name("username");
    By passwordField = By.name("password");
    By loginButton = By.xpath("//button[@type='submit']");

    public void enterUsername(String username) {
        clearInputField(usernameField);
        wait.until(ExpectedConditions.visibilityOf(findInputField(usernameField))).sendKeys(username);
    }

    public void enterPassword(String password) {
        clearInputField(passwordField);
        wait.until(ExpectedConditions.visibilityOf(findInputField(passwordField))).sendKeys(password);
    }

    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    public boolean isLoginSuccessful() {
        return driver.getCurrentUrl().contains("dashboard");
    }

    private WebElement findInputField(By by) {
        return driver.findElement(by);
    }

    private void clearInputField(By by) {
        wait.until(ExpectedConditions.visibilityOf(findInputField(by))).clear();
    }
}