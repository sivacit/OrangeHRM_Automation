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
    protected WebDriver driver;  // ✅ Instance-level WebDriver
    protected Properties properties;  // ✅ Instance-level Properties

    @BeforeMethod  // ✅ Runs before EACH test method in a class
    public void setUp() {
        if (properties == null) {  // ✅ Load properties only once
            properties = new Properties();
            loadProperties();
        }
        
        if (driver == null) {  // ✅ Initialize WebDriver only once per test method
            System.setProperty("webdriver.chrome.driver", properties.getProperty("chrome.driver.path"));
           
            ChromeOptions options = new ChromeOptions();
            options.setBinary(properties.getProperty("chrome.binary.path"));
            driver = new ChromeDriver(options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.get(properties.getProperty("base.url"));
        }
    }

    public void relax(int ofSeconds){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    }

    public void loadProperties() {
        try {
            FileInputStream fileInputStream = new FileInputStream("src/test/resources/config.properties");
            properties.load(fileInputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties file", e);
        }
    }

    @AfterMethod  // ✅ Runs after EACH test method in a class
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;  // ✅ Reset driver instance
        }
    }
}