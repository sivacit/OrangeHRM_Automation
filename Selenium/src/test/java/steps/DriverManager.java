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
            System.setProperty("webdriver.chrome.driver", "C:\\programs\\chromedriver.exe");
            driver = new ChromeDriver();
            // driver.get(url);
            driver.manage().window().maximize();

            // System.setProperty("webdriver.chrome.driver", "C:\\programs\\chromedriver.exe"); 
            // driver = new ChromeDriver();
            // driver.get(url);
            // driver.manage().window().maximize();
            
            // WebDriver driver = new ChromeDriver(options);


            // // ChromeOptions options = new ChromeOptions();
            // ChromeOptions options = new ChromeOptions();
            // options.addArguments("--headless");
            // options.addArguments("--disable-gpu");
            // options.addArguments("--no-sandbox"); // Use this option to avoid issues with Chrome on Linux/Windows
            // options.addArguments("--remote-debugging-port=65483"); // Use a fixed debugging port
            // options.addArguments("start-maximized"); // Start maximized for better compatibility

            // WebDriver driver = new ChromeDriver(options);
            // driver = new ChromeDriver(options);
            // driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
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
