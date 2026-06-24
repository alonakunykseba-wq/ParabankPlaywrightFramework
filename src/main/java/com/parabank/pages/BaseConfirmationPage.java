package com.parabank.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class BaseConfirmationPage {
    protected final Page page;
    protected final Locator accountIdLocator;

    public BaseConfirmationPage(Page page){
        this.page = page;
        this.accountIdLocator = page.locator("a[id='newAccountId']");
    }

    public int getAccountNumber() {
        accountIdLocator.waitFor();
        return Integer.parseInt(accountIdLocator.textContent());
    }
}
