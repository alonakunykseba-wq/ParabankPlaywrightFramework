package com.parabank.ui.base;

import com.microsoft.playwright.Page;
import com.parabank.setup.PlaywrightFactory;
import com.parabank.utils.ConfigurationManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseUITest {
    protected Page page;

    @BeforeMethod
    public void setup() {
        this.page = PlaywrightFactory.initBrowser(ConfigurationManager.getProperty("browser"));
        page.navigate(ConfigurationManager.getProperty("baseUrl"));
    }

    @AfterMethod
    public void tearDown() {
        PlaywrightFactory.removeThreadLocals();
    }
}
