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

    public Locator accountNumberLocator(int accountNumber){
        return page.getByRole(LINK, LocatorUtil.name(String.valueOf(accountNumber)));
    }

    public Locator balanceLocator(int accountNumber){
        return page.locator("tr")
                .filter(new Locator.FilterOptions().setHasText(String.valueOf(accountNumber)))
                .locator("td").nth(1);
    }

    public double getAccountBalance(int accountNumber) {
        balanceLocator(accountNumber).waitFor();
        String rawBalance = balanceLocator(accountNumber).textContent();
        return Double.parseDouble(rawBalance.replace("$", "").trim());
    }

    public Locator defaultAccountIdLocator(){
        return page.locator("#accountTable").getByRole(LINK).first();
    }

    public int getDefaultAccountId(){
        defaultAccountIdLocator().waitFor();
        return Integer.parseInt(defaultAccountIdLocator().textContent());
    }
}
