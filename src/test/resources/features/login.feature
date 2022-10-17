@login
Feature: User should be able to login using correct credentials

  Background: user is on the login page
    Given Navigate to main page

  @positiveLogin
  Scenario: Positive login scenario
    When Click to "Log In" button
    And Click to "User Log In" button
    Then Verify to "Welkom bij Wentsy.com" message
    When Login to application with valid credentials
    Then Verify username "Hello, New User" is displayed

  @negativeLogin
  Scenario Outline: Negative login scenario
    When Click to "Log In" button
    And Click to "User Log In" button
    Then Verify to "Welkom bij Wentsy.com" message
    When Login to application with invalid credentials "<username>" and "<password>"
    Then Verify that error message "<errorMessage>" is displayed

    Examples:
      | username             | password  | errorMessage                      |
      | met_oruc@hotmail.com | 123456    | E-mailadres of wachtwoord onjuist |
      | abc@def.com          | Comu0852! | E-mailadres of wachtwoord onjuist |
      | def@cfg.fr           | 878654    | E-mailadres of wachtwoord onjuist |
      | met_oruc@hotmail.com |           | Please fill out this field        |
      |                      | Comu0852! | Please fill out this field        |
      |                      |           | Please fill out this field        |



