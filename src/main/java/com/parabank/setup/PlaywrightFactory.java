package com.parabank.setup;

import com.microsoft.playwright.*;
import com.parabank.utils.ConfigurationManager;

public class PlaywrightFactory {
    private static final ThreadLocal<Playwright> tlPlaywright = new ThreadLocal<>();
    private static final ThreadLocal<Browser> tlBrowser = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> tlBrowserContext = new ThreadLocal<>();
    private static final ThreadLocal<Page> tlPage = new ThreadLocal<>();

    public static Playwright getPlaywright() {
        return tlPlaywright.get();
    }

    public static Browser getBrowser() {
        return tlBrowser.get();
    }

    public static BrowserContext getBrowserContext() {
        return tlBrowserContext.get();
    }

    public static Page getPage() {
        return tlPage.get();
    }

    public Page initBrowser(String browserName){
        tlPlaywright.set(Playwright.create());

        switch (browserName.toLowerCase()){
            case "chromium":
                tlBrowser.set(getPlaywright().chromium().launch());
                break;
            case "firefox":
                tlBrowser.set(getPlaywright().firefox().launch());
                break;
            case "chrome":
                tlBrowser.set(getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome")));
                break;
            default:
                System.out.println("Please pass the right browser name......");
        }

        tlBrowserContext.set(getBrowser().newContext());
        tlPage.set(getBrowserContext().newPage());

        getPage().navigate(ConfigurationManager.getProperty("baseUrl"));
        return getPage();
    }

    public static void removeThreadLocals() {
        tlPage.remove();
        tlBrowserContext.remove();
        tlBrowser.remove();
        tlPlaywright.remove();
    }
}
