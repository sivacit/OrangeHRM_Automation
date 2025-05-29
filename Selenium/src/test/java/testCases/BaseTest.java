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

    @BeforeMethod
    public void setUp() {
        if (properties == null) {  // ✅ Load properties only once
            properties = new Properties();
            loadProperties();
        }

        // Set system property for ChromeDriver
        System.setProperty("webdriver.chrome.driver", "C:\\programs\\chromedriver.exe");
        
        // Configure Chrome options
        ChromeOptions options = new ChromeOptions();
        
        // Add critical arguments to fix connection issues
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-dev-shm-usage");
        // options.addArguments("--user-data-dir=./chrome-data");
        
        
        // Important: Do NOT use debuggerAddress option that was causing the error
        
        // Create driver with options
        driver = new ChromeDriver(options);
        // Configure browser settings
        driver.get(properties.getProperty("base.url"));
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
    }

    public void setUpBackup() {
       
        
        if (driver == null) {
            System.setProperty("webdriver.chrome.driver", properties.getProperty("chrome.driver.path"));
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-debugging-port=9222"); // Use a fixed port for WebSocket
            options.addArguments("--whitelisted-ips=0.0.0.0/0"); // Allow external connections
            options.addArguments("--ignore-certificate-errors");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--headless=new");
            System.setProperty("webdriver.chrome.driver", "C:\\programs\\chromedriver.exe");
	        driver = new ChromeDriver(options);
            options.addArguments("--ignore-certificate-errors"); // Ignore SSL errors
            driver.get(properties.getProperty("base.url"));
	        driver.manage().window().maximize();

            // options.setBinary(properties.getProperty("chrome.binary.path"));
            // options.addArguments("--headless");
            // options.addArguments("--disable-gpu");
            // options.addArguments("--no-sandbox"); // Avoid sandboxing issues
            // options.addArguments("--remote-debugging-port=65483");
            // driver = new ChromeDriver(options);
            // driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            // driver.get(properties.getProperty("base.url"));
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