package steps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;


public class DriverManager {
    private static WebDriver driver;
    private static Properties properties;

    private static void loadProperties() {
        properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream("src/test/resources/config.properties");
            properties.load(fileInputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties file", e);
        }
    }

    public static WebDriver getDriver() {
        if (driver == null) {
            loadProperties();
            String chromeDriverPath = properties.getProperty("chrome.driver.path");

            System.setProperty("webdriver.chrome.driver", chromeDriverPath);

            ChromeOptions options = new ChromeOptions();
            driver = new ChromeDriver(options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null; // Ensure it does not persist
        }
    }
}
