# Generate a Java Page Object Model (POM) class named LoginPage for the website: https://opensource-demo.orangehrmlive.com/web/index.php/auth/login.

## Requirements:
- The class should be inside the pages package.
- Use Selenium WebDriver for browser interaction.
- Implement explicit waits using WebDriverWait (15-second timeout).
- Optimize XPath locators for Chrome compatibility, with fallback options if necessary.
- Ensure input fields are cleared before entering new values.
- Implement the following methods:
- enterUsername(String username): Enters the username.
- enterPassword(String password): Enters the password.
- clickLogin(): Clicks the login button.
- isLoginSuccessful(): Verifies login success by checking if the URL contains "dashboard".
- Additional Instructions for Code Quality:

- Use new WebDriverWait(driver, Duration.ofSeconds(15)) (not new WebDriverWait(driver, 15)).
- Ensure wait.until(...) statements return the correct type and are properly stored in variables.
- Do not include setup details, explanations, or extra comments—just provide the Java code.
- Now, generate similar POM classes for:

- DashboardPage (Verify successful login, navigate to sections).
- EmployeeListPage (Search for employees, open employee details).
- AddEmployeePage (Fill out new employee form, save data).
- Follow the same structure and coding standards for these pages.