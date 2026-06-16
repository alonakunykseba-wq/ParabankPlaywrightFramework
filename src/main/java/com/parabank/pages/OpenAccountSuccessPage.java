package com.parabank.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class OpenAccountSuccessPage {
    private final Page page;
    private final Locator accountNumberLocator;

    public OpenAccountSuccessPage(Page page) {
        this.page = page;
        this.accountNumberLocator = page.locator("a[id='newAccountId']");
    }

    public Locator successHeadingLocator() {
        return page.getByText("Account Opened!", new Page.GetByTextOptions().setExact(true));
    }

    public Locator successDescriptionLocator() {
        return page.getByText("Congratulations, your account is now open.", new Page.GetByTextOptions().setExact(true));
    }

    public String getAccountNumberLocator() {
        accountNumberLocator.waitFor();
        return accountNumberLocator.textContent();
    }
}
