package com.parabank.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import static com.microsoft.playwright.options.AriaRole.*;

public class AccountOverviewPage {
    private final Page page;
    private final Locator openNewAccount;
    private final Locator accountsOverview;
    private final Locator findTransactions;
    private final Locator updateContactInfo;
    private final Locator signOut;

    public AccountOverviewPage(Page page) {
        this.page = page;
        this.openNewAccount = page.getByRole(LINK, new Page.GetByRoleOptions().setName("Open New Account"));
        this.accountsOverview = page.getByRole(LINK, new Page.GetByRoleOptions().setName("Accounts Overview"));
        this.findTransactions = page.getByRole(LINK, new Page.GetByRoleOptions().setName("Find Transactions"));
        this.updateContactInfo = page.getByRole(LINK, new Page.GetByRoleOptions().setName("Update Contact Info"));
        this.signOut = page.getByRole(LINK, new Page.GetByRoleOptions().setName("Log Out"));
    }

    public Locator welcomeMessage() {
        return page.locator("h1.title");
    }

    public MainPage logOut(){
        signOut.click();
        return new MainPage(page);
    }

    public Locator welcomeUser(){
        return page.locator("p.smallText");
    }
}
