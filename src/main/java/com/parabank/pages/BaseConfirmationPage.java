package com.parabank.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public abstract class BaseConfirmationPage extends BasePage{
    protected final Locator accountIdLocator;

    public BaseConfirmationPage(Page page){
        super(page);
        this.accountIdLocator = page.locator("a#newAccountId");
    }

    public int getAccountNumber() {
        return Integer.parseInt(safeGetText(accountIdLocator));
    }
}
