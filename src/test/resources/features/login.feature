Feature: Login
  as a guest user
  I want to login as user

  Scenario: Successfull Login
    Given the username is "test"
    And the password is "Test1234"
    When the login button is clicked
    Then the dashboard is shown

  Scenario: Login failed
    Given the username is "test"
    And the password is "incorrect"
    When the login button is clicked
    Then the login page is shown
    And the alert message is "Login fehlgeschlagen!"