package steps;

import io.cucumber.java.After;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class ProfileSteps {
    private WebDriver driver = DriverManager.getDriver();

    @Given("I am logged in the website {string}")
    public void i_am_logged_in_the_website(String url) {
        driver.get(url);
    }

    @Then("the updated first name should be {string}")
    public void the_updated_first_name_should_be(String newFirstName) {
        WebElement firstNameField = driver.findElement(By.id("firstName"));
        Assert.assertEquals(firstNameField.getAttribute("value"), newFirstName);
    }

    @Then("the updated middle name should be {string}")
    public void the_updated_middle_name_should_be(String newMiddleName) {
        WebElement middleNameField = driver.findElement(By.id("middleName"));
        Assert.assertEquals(middleNameField.getAttribute("value"), newMiddleName);
    }

    @Then("the updated last name should be {string}")
    public void the_updated_last_name_should_be(String newLastName) {
        WebElement lastNameField = driver.findElement(By.id("lastName"));
        Assert.assertEquals(lastNameField.getAttribute("value"), newLastName);
    }

    @When("I update the first name to {string}")
    public void i_update_the_first_name_to(String newFirstName) {
        WebElement firstNameField = driver.findElement(By.id("firstName"));
        firstNameField.clear();
        firstNameField.sendKeys(newFirstName);
    }

    @And("I update the middle name to {string}")
    public void i_update_the_middle_name_to(String newMiddleName) {
        WebElement middleNameField = driver.findElement(By.id("middleName"));
        middleNameField.clear();
        middleNameField.sendKeys(newMiddleName);
    }

    @And("I update the last name to {string}")
    public void i_update_the_last_name_to(String newLastName) {
        WebElement lastNameField = driver.findElement(By.id("lastName"));
        lastNameField.clear();
        lastNameField.sendKeys(newLastName);
    }

    @And("I click on the Save button")
    public void i_click_on_the_Save_button() {
        WebElement saveButton = driver.findElement(By.id("btnSave"));
        saveButton.click();
    }

    @Then("an error message should appear saying {string}")
    public void an_error_message_should_appear_saying(String expectedErrorMessage) {
        WebElement errorMessage = driver.findElement(By.id("system_message"));
        Assert.assertEquals(errorMessage.getText(), expectedErrorMessage);
    }

    @After
    public void tearDown() {
        DriverManager.quitDriver();  // ✅ Close browser after tests
    }
}
