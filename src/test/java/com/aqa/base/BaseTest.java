package com.aqa.base;

import com.aqa.utils.DriverManager;
import com.aqa.utils.Logging;

import org.slf4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

	protected final Logger log = Logging.get(getClass());

    @BeforeMethod
    public void setUp() {
        DriverManager.initDriver();
    }

    @AfterMethod
    public void tearDown() {
        DriverManager.quitDriver();
    }
}
