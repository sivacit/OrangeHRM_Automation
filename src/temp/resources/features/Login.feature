cucumber
Feature: Login Functionality for OrangeHRM Live Website
  As a user
  I want to be able to login with valid and invalid credentials
  So that I can access the application or get notified of an error

Scenario: Successful Login with Valid Credentials
  Given the OrangeHRM Live website is open at "<https://opensource-demo.orangehrmlive.com/web/index.php/auth/login>"
  When I enter "admin" as username and "admin123" as password
  And I click on the 'Login' button
  Then I should be redirected to the OrangeHRM Dashboard

Scenario: Failed Login with Invalid Credentials
  Given the OrangeHRM Live website is open at "<https://opensource-demo.orangehrmlive.com/web/index.php/auth/login>"
  When I enter invalid username and password
  And I click on the 'Login' button
  Then I should see an error message "Invalid credentials" displayed.


This Cucumber BDD feature file tests both successful login with valid credentials and failed login with invalid credentials for the OrangeHRM Live website.