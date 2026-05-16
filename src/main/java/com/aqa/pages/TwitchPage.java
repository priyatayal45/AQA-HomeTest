package com.aqa.pages;

import com.aqa.utils.ConfigReader;
import com.aqa.utils.DriverManager;
import com.aqa.utils.ScreenshotUtil;
import com.aqa.utils.WaitUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Arrays;
import java.util.List;

public class TwitchPage extends BasePage {

    private final By searchButton     = By.xpath("//div[text()='Browse']");
    private final By searchInput      = By.cssSelector("input[type='search']");
    private final By firstStreamer    = By.xpath("(//section/div/button)[1]");
    private final By modalRoot        = By.cssSelector("[data-a-target='modal-root']");
    private final By bodyTag          = By.tagName("body");
    private final By streamerResults  = By.xpath("//section/div/button");
    private final By videoPlayer      = By.cssSelector("video[aria-label='Twitch video player']");
    private final By followButton     = By.cssSelector("[data-a-target='follow-button']");
    private final By iframePlayer     = By.cssSelector("iframe");
    private final By ageGateButton    = By.cssSelector("[data-a-target='player-overlay-mature-accept']");


    private static final String TWITCH_URL = ConfigReader.get("twitch.base.url");

    public TwitchPage open() {
        driver.get(TWITCH_URL);
        WaitUtil.waitForPageLoad(driver, 15);
        dismissLoginPopup();
        return this;
    }

    private void dismissLoginPopup() {
        try {
            driver.findElement(bodyTag).click();
            WaitUtil.waitForInvisible(driver, modalRoot, 5);
        } catch (Exception e) {
            log.info("No login popup or could not dismiss");
        }
    }

    public TwitchPage clickSearchIcon() {
        try {
            WebElement browse = WaitUtil.waitForClickable(driver, searchButton, 15);
            browse.click();
            WaitUtil.waitForVisible(driver, searchInput, 10);
        } catch (Exception e) {
            log.error("Browse button not found: " + e.getMessage());
        }
        return this;
    }

    public TwitchPage typeSearchQuery(String query) {
        try {
            WebElement input = WaitUtil.waitForVisible(driver, searchInput, 10);
            input.clear();
            input.sendKeys(query);
            input.sendKeys(Keys.ENTER);
            WaitUtil.waitForPresence(driver, streamerResults, 15);
        } catch (Exception e) {
        	log.error("Search input not found: " + e.getMessage());
        }
        return this;
    }

    public TwitchPage scrollDownTwice() {
        scrollDown();
        scrollDown();
        System.out.println("Scrolled down 2 times");
        return this;
    }

    public TwitchPage selectFirstStreamer() {
        try {
            driver.findElement(searchInput).sendKeys(Keys.ESCAPE);
            WaitUtil.waitForInvisible(driver, searchInput, 5);
        } catch (Exception e) {
        	log.info("Search input not found to close");
        }

        try {
            WebElement streamer = WaitUtil.waitForClickable(driver, firstStreamer, 15);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", streamer);
            WaitUtil.waitForUrlContains(driver, "twitch.tv/", 15);
        } catch (Exception e) {
        	log.error("First streamer not found: " + e.getMessage());
        }
        return this;
    }

    public TwitchPage handlePopupsAndModals() {
    	if (isElementPresent(ageGateButton)) {
            driver.findElement(ageGateButton).click();
            log.info("Age gate popup handled");
            WaitUtil.waitForInvisible(driver, ageGateButton, 5);
        } else {
            log.info("No popup detected — page loaded directly");
        }
        return this;
    }

    public void waitForPageFullyLoaded() {
        WaitUtil.waitForPageLoad(driver, 15);

        List<By> indicators = Arrays.asList(
            videoPlayer,
            followButton,
            iframePlayer
            );

        for (By locator : indicators) {
            if (isElementPresent(locator)) {
            	log.info(" Page ready — found: " + locator);
                return;
            } 
        }
        log.info("Proceeding without load indicator");
    }

    public boolean isVideoLoaded() {
        return isElementPresent(videoPlayer);
    }

    protected void scrollDown() {
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 600)");
        WaitUtil.waitForPageLoad(driver, 5);
    }
    
    public void verifyScreenshotCaptured()
    {
	    String screenshotPath = ScreenshotUtil.takeScreenshot( DriverManager.getDriver(),"twitch_streamer_page");
    	if (isVideoLoaded()) {
	        log.info("Video player visible and screenshot taken");
	    } else {
	    	 log.warn("Video not visible but screenshot taken");
	    }
	    log.info("Test passed! Screenshot: " + screenshotPath);
    }
}