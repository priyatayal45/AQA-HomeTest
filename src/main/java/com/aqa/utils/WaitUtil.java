package com.aqa.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtil {

    private WaitUtil() {}

    public static WebElement waitForVisible(WebDriver driver, By locator, int timeoutSec) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSec))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForClickable(WebDriver driver, By locator, int timeoutSec) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSec))
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static void waitForUrlContains(WebDriver driver, String urlPart, int timeoutSec) {
        new WebDriverWait(driver, Duration.ofSeconds(timeoutSec))
                .until(ExpectedConditions.urlContains(urlPart));
    }

    public static WebElement waitForPresence(WebDriver driver, By locator, int timeoutSec) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSec))
                .until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static void waitForPageLoad(WebDriver driver, int timeoutSec) {
        new WebDriverWait(driver, Duration.ofSeconds(timeoutSec)).until(
            d -> ((JavascriptExecutor) d)
                    .executeScript("return document.readyState")
                    .equals("complete")
        );
    }

    public static void waitForInvisible(WebDriver driver, By locator, int timeoutSec) {
        new WebDriverWait(driver, Duration.ofSeconds(timeoutSec))
                .until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
}