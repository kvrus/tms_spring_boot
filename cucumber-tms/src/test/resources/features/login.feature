# language: en
Feature: Sample UI Test

  Scenario: Проверить что пользователь может авторизоваться с верным логином
    Given open page "/login"
    Given put text "admin" in field "username"
    Given put text "admin" in field "password"
    When press button "login"
    Then check current page "/plans"
    Then check drop down menu "dropdownMenuButto" item with text "Administration" is displayed


  Scenario: Проверить что пользователь НЕ может авторизоваться с НЕ верным логином
    Given open page "/login"
    Given put text "admin123" in field "username"
    Given put text "admin123" in field "password"
    When press button "login"
    Then check current page "/login?error"

  Scenario: Проверить что пользователь с правами USER не видит раздел Администрирование
    Given open page "/login"
    Given put text "user" in field "username"
    Given put text "123456" in field "password"
    When press button "login"
    Then check current page "/plans"
    Then check drop down menu "dropdownMenuButto" item with text "Administration" is NOT displayed
