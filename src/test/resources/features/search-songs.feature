Feature: Search Songs
  as an user
  I want to search songs

  Scenario: Search result is shown
    Given the user with username "test" and password "Test1234" is logged in
    And a view is shown
    And the search text is "test"
    When the search button is clicked
    Then the search is shown
    And the search results are shown

  Scenario: No search, because empty search text
    Given the user with username "test" and password "Test1234" is logged in
    And a view is shown
    And the search text is ""
    When the search button is clicked
    Then the search is not shown

  Scenario: Empty result, because too complex search text
    Given the user with username "test" and password "Test1234" is logged in
    And a view is shown
    And the search text is "vkahjsdvkashjbkfzrvkdhjvakbfrk"
    When the search button is clicked
    Then the search is shown
    And the search result is empty
