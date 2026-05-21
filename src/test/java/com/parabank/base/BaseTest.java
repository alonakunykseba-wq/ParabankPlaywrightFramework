package com.parabank.base;

import com.microsoft.playwright.Page;
import com.parabank.setup.PlaywrightFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {
    protected Page page;

    @BeforeMethod
    public void setup() {
        PlaywrightFactory pf = new PlaywrightFactory();
        this.page = pf.initBrowser("chrome");
    }

    @AfterMethod
    public void tearDown() {
        page.context().browser().close();
        PlaywrightFactory.removeThreadLocals();
    }
}
