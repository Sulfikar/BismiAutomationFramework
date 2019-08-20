#Author: Sulfikar Ali Nazar

@LoginFunction
Feature: Feature to test multiple login scenarios
  
  @validLogin
  Scenario: Valid login verification scenario
    Given I Launch critical application
    When I see login page
    And I enter valid credentials
    Then should be navigated to home page

  