Feature: Order beverage from React frontend

  As a client,
  I want to order a specific beverage from the React frontend,
  So that I can verify if it was successfully accepted by the system,
  considering the beverage must exist in the Angular menu.

  Scenario: Successfully order a beverage that exists in the menu
    Given the beverage "Coffee" exists in the menu
    When I order the beverage "Coffee" with size "Medium"
    Then the order should be accepted with the message "Order created successfully"

  Scenario: Fail to order a beverage that does not exist in the menu
    Given the beverage "Tea" does not exist in the menu
    When I order the beverage "Tea" with size "Small"
    Then the order should be rejected with the message "Beverage not available"
