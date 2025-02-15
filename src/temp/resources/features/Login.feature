gherkin
Feature: Login functionality for OrangeHRM website
  As a user
  I want to be able to log in successfully or handle the case of invalid credentials
  So that I can access the dashboard or receive an error message, respectively

Scenario: Successful login with valid credentials
  Given I am on the OrangeHRM login page
  When I enter "admin" as username and "admin123" as password
  And I click the login button
  Then I should be navigated to the dashboard page

Scenario: Handling invalid credentials case
  Given I am on the OrangeHRM login page
  When I enter an invalid username and invalid password
  And I click the login button
  Then I should see an error message "Invalid credentials" displayed


With these Cucumber BDD steps, you can write the corresponding step definitions in your preferred programming language (such as Java or Ruby) to implement the test automation for the login functionality of the OrangeHRM website. This way, your tests will be clear and easy to understand, which is a key aspect of Behavior-Driven Development (BDD).