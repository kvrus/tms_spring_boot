package com.example.steps;

import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.*;

public class FeatureSteps {
    private final StepsContext context = new StepsContext();

    @Given("open page {string}")
    public void open_page(String string) {
        context.driver.get(context.baseUrl + string);
    }

    @Given("put text {string} in field {string}")
    public void put_text_in_field(String text, String field) {
        WebElement usernameField = context.driver.findElement(By.id(field));
        usernameField.clear();
        usernameField.sendKeys(text);
    }

    @When("press button {string}")
    public void press_button(String string) {
        WebElement loginButton = context.driver.findElement(By.cssSelector("button[type='submit']"));
        loginButton.click();
    }

    @Then("check current page {string}")
    public void check_current_page(String string) {
        assertEquals(context.baseUrl + string, context.driver.getCurrentUrl());
    }

    @Then("check item {string} is displayed")
    public void check_item_is_displayed(String string) {
        WebDriverWait wait = new WebDriverWait(context.driver, 1);
        WebElement dropdownButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("dropdownMenuButto")));
        dropdownButton.click();
        WebElement adminMenuItem = context.driver.findElement(By.xpath("//a[text()='Administration']"));
        assertTrue(adminMenuItem.isDisplayed());
    }

    @Then("check drop down menu {string} item with text {string} is displayed")
    public void check_item_is_displayed(String menu, String item) {
        WebDriverWait wait = new WebDriverWait(context.driver, 1);
        WebElement dropdownButton = wait.until(ExpectedConditions.elementToBeClickable(By.id(menu)));
        dropdownButton.click();
        WebElement adminMenuItem = context.driver.findElement(By.xpath("//a[text()='"+item+"']"));
        assertTrue(adminMenuItem.isDisplayed());
    }

    @Then("check drop down menu {string} item with text {string} is NOT displayed")
    public void checkDropDownMenuItemWithTextIsNOTDisplayed(String menu, String item) {
        WebDriverWait wait = new WebDriverWait(context.driver, 1);
        WebElement dropdownButton = wait.until(ExpectedConditions.elementToBeClickable(By.id(menu)));
        dropdownButton.click();
        WebElement adminMenuItem = context.driver.findElement(By.xpath("//a[text()='"+item+"']"));
        assertFalse(adminMenuItem.isDisplayed());
    }


    private static int scenarioCount = 0;
    private static int completedScenarios = 0;

    @Before
    public void init() {
        scenarioCount++;
    }

    @After
    public void tearDown() {
        completedScenarios++;
        if (completedScenarios == scenarioCount) {
            context.deactivate(); // Close the browser after each scenario
            scenarioCount = 0;
            completedScenarios = 0;
        }
    }
}
