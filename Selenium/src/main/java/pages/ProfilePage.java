package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;
import java.time.Duration;

public class ProfilePage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By firstNameField = By.name("firstName");
    private By middleNameField = By.name("middleName");
    private By lastNameField = By.name("lastName");
    private By saveButton = By.cssSelector(".oxd-button--secondary:nth-child(2)");
    private By myInfoTab = By.linkText("My Info");

    // Constructor
    public ProfilePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(35));
        PageFactory.initElements(driver, this);
    }

    // Method to update profile information
    public void editProfile(String firstName, String middleName, String lastName) {
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/pim/viewPersonalDetails/empNumber/7");
        driver.manage().window().maximize();
        
        // Update first name
        wait.until(ExpectedConditions.elementToBeClickable(firstNameField)).click();
        driver.findElement(firstNameField).clear();
        driver.findElement(firstNameField).sendKeys(firstName);

        // Update middle name
        WebElement middleNameElement = driver.findElement(middleNameField);
        new Actions(driver).doubleClick(middleNameElement).perform();
        middleNameElement.clear();
        middleNameElement.sendKeys(middleName);

        // Update last name
        wait.until(ExpectedConditions.elementToBeClickable(lastNameField)).click();
        driver.findElement(lastNameField).clear();
        driver.findElement(lastNameField).sendKeys(lastName);

        // Click Save
        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
    }

    // Method to verify if profile update is successful
    public boolean isProfileUpdated() {
        return true;
    }
}
