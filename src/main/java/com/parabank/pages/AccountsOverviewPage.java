package com.parabank.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.parabank.utils.LocatorUtil;

import static com.microsoft.playwright.options.AriaRole.*;

public class AccountsOverviewPage {
    private final Page page;

    public AccountsOverviewPage(Page page) {
        this.page = page;
    }

    public Locator accountNumber(String accountNumber){
        return page.getByRole(LINK, LocatorUtil.name(accountNumber));
    }

    public Locator balance(String accountNumber){
        return page.locator("tr")
                .filter(new Locator.FilterOptions().setHasText(accountNumber))
                .locator("td").nth(1);
    }

    public Locator defaultAcccountIdLocator(){
        return page.locator("#accountTable").getByRole(LINK).first();
    }

    public String getDefaultAccountId(){
        defaultAcccountIdLocator().waitFor();
        return defaultAcccountIdLocator().textContent();
    }
}
