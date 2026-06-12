package com.parabank.setup;

import com.microsoft.playwright.*;
import com.parabank.utils.ConfigurationManager;

public class PlaywrightFactory {
    private static final ThreadLocal<Playwright> playwrightThread = new ThreadLocal<>();
    private static final ThreadLocal<Browser> browserThread = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> browserContextThread = new ThreadLocal<>();
    private static final ThreadLocal<Page> pageThread = new ThreadLocal<>();

    public static Playwright getPlaywright() {
        return playwrightThread.get();
    }

    public static Browser getBrowser() {
        return browserThread.get();
    }

    public static BrowserContext getBrowserContext() {
        return browserContextThread.get();
    }

    public static Page getPage() {
        return pageThread.get();
    }

    public static Page initBrowser(String browserName) {
        playwrightThread.set(Playwright.create());

        switch (browserName.toLowerCase()) {
            case "chromium":
                browserThread.set(getPlaywright().chromium().launch());
                break;
            case "firefox":
                browserThread.set(getPlaywright().firefox().launch());
                break;
            case "chrome":
                browserThread.set(getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome")));
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser name: " + browserName);
        }

        browserContextThread.set(getBrowser().newContext());
        pageThread.set(getBrowserContext().newPage());

        getPage().navigate(ConfigurationManager.getProperty("baseUrl"));
        return getPage();
    }

    public static void removeThreadLocals() {
        if (pageThread.get() != null) {
            pageThread.remove();
        }
        if (browserContextThread.get() != null) {
            browserContextThread.get().close();
            browserContextThread.remove();
        }
        if (browserThread.get() != null) {
            browserThread.get().close();
            browserThread.remove();
        }
        if (playwrightThread.get() != null) {
            playwrightThread.get().close();

            playwrightThread.remove();
        }
    }
}
