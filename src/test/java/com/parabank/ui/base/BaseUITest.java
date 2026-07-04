package com.parabank.ui.base;

import com.parabank.setup.PlaywrightFactory;
import com.parabank.utils.ConfigurationManager;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;


public class BaseUITest {

    @BeforeMethod
    public void setup() {
        PlaywrightFactory.initBrowser(ConfigurationManager.getProperty("browser"));
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        PlaywrightFactory.removeThreadLocals();
    }
}
