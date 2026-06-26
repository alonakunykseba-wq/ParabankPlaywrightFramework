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
        if (result.getStatus() == ITestResult.FAILURE) {
            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(Paths.get("target/screenshots/" + result.getName() + ".png"))
                    .setFullPage(true));
        }
        PlaywrightFactory.removeThreadLocals();
    }
}
