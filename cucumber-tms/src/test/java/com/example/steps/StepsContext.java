package com.example.steps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

public class StepsContext {
    public WebDriver driver;
    public String baseUrl;

    public StepsContext() {
        System.setProperty("webdriver.gecko.driver", "/Users/konstantinvolkov/Dev/spring/tms/drivers/geckodriver");
        System.setProperty("webdriver.firefox.bin", "/usr/local/bin/firefox");

        driver = new FirefoxDriver();
        baseUrl = "http://localhost:8082";
    }

    public void deactivate() {
        if(driver != null) {
            driver.quit();
        }
    }
}
