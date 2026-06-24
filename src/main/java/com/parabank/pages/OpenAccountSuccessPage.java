package com.parabank.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class OpenAccountSuccessPage extends BaseConfirmationPage{

    public OpenAccountSuccessPage(Page page) {
       super(page);
    }

    public Locator successHeadingLocator() {
        return page.getByText("Account Opened!", new Page.GetByTextOptions().setExact(true));
    }

    public Locator successDescriptionLocator() {
        return page.getByText("Congratulations, your account is now open.", new Page.GetByTextOptions().setExact(true));
    }

}
