package com.aqa.ui;

import com.aqa.base.UIBaseTest;
import com.aqa.pages.TwitchPage;
import com.aqa.utils.DriverManager;
import com.aqa.utils.ScreenshotUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * TwitchTest - UI Automation Test
 *
 * Test Steps (as per assignment):
 * 1. Go to Twitch
 * 2. Click search icon
 * 3. Input "StarCraft II"
 * 4. Scroll down 2 times
 * 5. Select one streamer
 * 6. Wait until page loads and take screenshot
 */
public class TwitchTest extends UIBaseTest {

	@Test(description = "Search StarCraft II on Twitch and open a streamer page")
	public void searchAndOpenStreamer() throws InterruptedException {

	    TwitchPage twitchPage = new TwitchPage();

	    twitchPage.open();
	    twitchPage.clickSearchIcon();
	    twitchPage.typeSearchQuery("StarCraft II");
	    twitchPage.scrollDownTwice();
	    twitchPage.selectFirstStreamer();
	    twitchPage.handlePopupsAndModals();
	    twitchPage.waitForPageFullyLoaded();
	    twitchPage.verifyScreenshotCaptured();
	}
}
