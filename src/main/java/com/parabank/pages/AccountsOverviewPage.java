package com.parabank.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import static com.microsoft.playwright.options.AriaRole.*;

public class AccountsOverviewPage {
    private final Page page;

    public AccountsOverviewPage(Page page) {
        this.page = page;
    }

    public Locator accountNumber(String accountNumber){
        return page.getByRole(LINK, new Page.GetByRoleOptions().setName(accountNumber));
    }

    public Locator balance(String accountNumber){
        return page.locator("tr")
                .filter(new Locator.FilterOptions().setHasText(accountNumber))
                .locator("td").nth(1);
    }
}
