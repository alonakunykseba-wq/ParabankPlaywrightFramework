package com.parabank.ui.base;

import com.microsoft.playwright.Page;
import com.parabank.setup.PlaywrightFactory;
import com.parabank.utils.ConfigurationManager;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.nio.file.Paths;

public class BaseUITest {
    protected Page page;

    @BeforeMethod
    public void setup() {
        this.page = PlaywrightFactory.initBrowser(ConfigurationManager.getProperty("browser"));
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        PlaywrightFactory.removeThreadLocals();
    }
}
