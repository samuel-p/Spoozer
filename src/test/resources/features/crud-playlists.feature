Feature: Crud Playlists
  as an user
  I want to create, edit and delete playlists

  Scenario: Playlists are shown
    Given the user with username "test" and password "Test1234" is logged in
    And a view is shown
    When the playlists button is clicked
    Then the playlists is shown

  Scenario: Create playlist success
    Given the user with username "test" and password "Test1234" is logged in
    And a view is shown
    And the playlists button is clicked
    When the add playlist button is clicked
    And the playlist name is "Test"
    And the enter key is pressed
    Then the playlist "Test" is added

  Scenario: Delete playlist success
    Given the user with username "test" and password "Test1234" is logged in
    And a view is shown
    And the playlists button is clicked
    When the delete playlist button is clicked
    Then the playlist is deleted