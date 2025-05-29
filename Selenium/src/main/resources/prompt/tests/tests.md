# Generate a **Java TestNG test class** using the provided **Page Object Model (POM) Java file**. The test class should adhere to the following structure:

---

## **Package & Class Setup:**
- The test class should be inside the **`testCases`** package.
- Name of the class should be ${className}
- The test class should **extend `BaseTest`** to inherit:
  - WebDriver initialization.
  - Property file loading (`config.properties`).
  - WebDriver teardown.

---

## **Test Configuration:**
- Use **TestNG annotations**:  
  - `@BeforeMethod` to initialize the required Page Object.
  - `@Test` for executing test steps.
  - `@AfterMethod` to clean up WebDriver (handled by `BaseTest`).
- Ensure `WebDriver` is **inherited from `BaseTest`**, avoiding reinitialization.

---

## **Test Method Implementation:**
- The test method should:
  - **Read test data** (e.g., username, password, input values) from `config.properties`.
  - **Call the appropriate methods** from the **POM file** to perform actions.
  - **Use explicit waits (`WebDriverWait`)** for handling dynamic elements.
  - **Assert expected results** using `Assert.assertTrue(...)`.

---

## **Login Handling (If Applicable):**
- If the test requires authentication, ensure:
  - **`LoginPage` is initialized** and used for login before proceeding.
  - **Explicit wait** is used to confirm successful login.
  - **The test starts from the expected page** after authentication.

---

## Page class
```
${pomFile}
```

## **BaseTest Reference (Do Not Generate Again):**
- The test class should inherit from the following existing `BaseTest` class:
```java
package testCases;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {
    protected WebDriver driver;
    protected Properties properties;

    @BeforeMethod
    public void setUp() {
        if (properties == null) {
            properties = new Properties();
            loadProperties();
        }

        if (driver == null) {
            System.setProperty("webdriver.chrome.driver", properties.getProperty("chrome.driver.path"));
            ChromeOptions options = new ChromeOptions();
            options.setBinary(properties.getProperty("chrome.binary.path"));
            driver = new ChromeDriver(options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.get(properties.getProperty("base.url"));
        }
    }

    public void loadProperties() {
        try {
            FileInputStream fileInputStream = new FileInputStream("src/test/resources/config.properties");
            properties.load(fileInputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties file", e);
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
```
**Do not regenerate the `BaseTest` class.** Just ensure the test class extends it.

---

## **Example Output Structure (Do Not Modify)**
The expected test class should follow this format:
```java
package testCases;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.time.Duration;
import pages.<POMClassName>;

public class <GeneratedTestClassName> extends BaseTest {
    private <POMClassName> page;
    private WebDriverWait wait;

    @BeforeMethod
    public void setupTest() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        page = new <POMClassName>(driver);
    }

    @Test
    public void testFeature() {
        wait.until(ExpectedConditions.urlContains("<expected_url_after_action>"));
        page.performAction();
        Assert.assertTrue(page.isActionSuccessful(), "Action failed!");
    }
}
```
# Instruction:
- Don't generate comments, notes.