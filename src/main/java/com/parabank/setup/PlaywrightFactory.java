package com.parabank.setup;

import com.microsoft.playwright.*;
import com.parabank.utils.ConfigurationManager;

public class PlaywrightFactory {


    private static Playwright playwright;
    private static Browser browser;
    private static final ThreadLocal<BrowserContext> browserContextThread = new ThreadLocal<>();
    private static final ThreadLocal<Page> pageThread = new ThreadLocal<>();

    public static Playwright getPlaywright() {
        if (playwright == null) {
            playwright = Playwright.create();
        }
        return playwright;
    }

    public static BrowserContext getBrowserContext() {
        return browserContextThread.get();
    }

    public static Page getPage() {
        return pageThread.get();
    }

    public static void initSuite(String browserName) {
        if (playwright == null) {
            playwright = Playwright.create();
        }
        if (browser == null) {
            switch (browserName.toLowerCase()) {
                case "chromium":
                    browser = playwright.chromium().launch();
                    break;
                case "firefox":
                    browser = playwright.firefox().launch();
                    break;
                case "chrome":
                    browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome"));
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported browser name: " + browserName);
            }
        }
    }
    public static Page initBrowser(String browserName) {
       if (browser == null) {
           initSuite(browserName);
       }
        browserContextThread.set(browser.newContext());
        pageThread.set(getBrowserContext().newPage());
        getPage().navigate(ConfigurationManager.getProperty("baseUrl"));
        return getPage();
    }

    public static void closeSuite(){
        if (browser != null) {
            browser.close();
            browser = null;
        }
        if (playwright != null) {
            playwright.close();
            playwright = null;
        }
    }

    public static void removeThreadLocals() {
        if (pageThread.get() != null) {
            pageThread.get().close();
            pageThread.remove();
        }
        if (browserContextThread.get() != null) {
            browserContextThread.get().close();
            browserContextThread.remove();
        }
    }
}
