@pennyDrop
Feature: PL fresh journey android

  @pennyDrop
  Scenario: Verify User is able to complete borrow flow
    Given user has completed login and on get started page
      | mobileNumber | null |
    When user clicks on borrow screen
    And verify the get started page
   