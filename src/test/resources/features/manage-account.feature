Feature: Manage Account
  as an user
  I want to manage my account

  Scenario: Profile overview is shown
    Given the user with username "test" and password "Test1234" is logged in
    And a view is shown
    When the profile button is clicked
    Then the profile is shown

  Scenario: Change password success
    Given the user with username "test" and password "Test1234" is logged in
    And a view is shown
    And the profile button is clicked
    And the profile is shown
    And the change password button is clicked
    When the old password is "Test1234"
    And the new password is "Test1234"
    And the confirmed password is "Test1234"
    And the save password button is clicked
    Then the profile overview is shown

  Scenario: Update Profile success
    Given the user with username "test" and password "Test1234" is logged in
    And a view is shown
    And the profile button is clicked
    And the profile is shown
    And the update profile button is clicked
    When the displayed name is "Test Account"
    And the save profile button is clicked
    Then the profile overview is shown