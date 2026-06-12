package com.parabank.UI.base;

import com.microsoft.playwright.Page;
import com.parabank.setup.PlaywrightFactory;
import com.parabank.utils.ConfigurationManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseUITest {
    protected Page page;

    @BeforeMethod
    public void setup() {
        PlaywrightFactory pf = new PlaywrightFactory();
        this.page = pf.initBrowser(ConfigurationManager.getProperty("browser"));
        page.navigate(ConfigurationManager.getProperty("baseUrl"));
    }

    @AfterMethod
    public void tearDown() {
        page.context().browser().close();
        PlaywrightFactory.removeThreadLocals();
    }
}
