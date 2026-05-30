package com.parabank.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class AccountOverviewPage {
    Page page;

    public AccountOverviewPage(Page page) {
        this.page = page;
    }

    public Locator registrationConfirmation() {
        return page.locator("h1.title");
    }
}
