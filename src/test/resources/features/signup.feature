Feature: Signup
  as a guest user
  I want to register

  Scenario: Successfull Signup
    Given the register link is clicked
    And the username is "test2"
    And the email is "test@spoozer.de"
    And the password is "Test1234"
    And the password confirm is "Test1234"
    When the register button is clicked
    Then the login page is shown

  Scenario: Signup faild, beacuse username in use
    Given the register link is clicked
    And the username is "test"
    And the email is "test@spoozer.de"
    And the password is "Test1234"
    And the password confirm is "Test1234"
    When the register button is clicked
    Then the register page is shown
    And the error message is "Dieser Benutzername wird bereits verwendet"

  Scenario: Signup failed, because weak password
    Given the register link is clicked
    And the username is "test3"
    And the email is "test@spoozer.de"
    And the password is "test"
    And the password confirm is "test"
    When the register button is clicked
    Then the register page is shown
    And the error message is "Das Passwort muss Groß- und Keinbuchstaben, sowie Zahlen enthalten"