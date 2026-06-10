package com.parabank.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import static com.microsoft.playwright.options.AriaRole.*;

public class MainPage {
    private final Page page;
    private final Locator openNewAccount;
    private final Locator accountsOverview;
    private final Locator findTransactions;
    private final Locator updateContactInfo;
    private final Locator transferFunds;
    private final Locator signOut;

    public MainPage(Page page) {
        this.page = page;
        this.openNewAccount = page.getByRole(LINK, new Page.GetByRoleOptions().setName("Open New Account"));
        this.accountsOverview = page.getByRole(LINK, new Page.GetByRoleOptions().setName("Accounts Overview"));
        this.findTransactions = page.getByRole(LINK, new Page.GetByRoleOptions().setName("Find Transactions"));
        this.updateContactInfo = page.getByRole(LINK, new Page.GetByRoleOptions().setName("Update Contact Info"));
        this.transferFunds = page.getByRole(LINK,new Page.GetByRoleOptions().setName("Transfer Funds"));
        this.signOut = page.getByRole(LINK, new Page.GetByRoleOptions().setName("Log Out"));
    }

    public Locator welcomeMessage() {
        return page.locator("h1.title");
    }

    public LoginPage logOut(){
        signOut.click();
        return new LoginPage(page);
    }

    public Locator welcomeUser(){
        return page.locator("p.smallText");
    }

    public AccountsOverviewPage openAccountsOverview (){
        accountsOverview.click();
        return new AccountsOverviewPage(page);
    }

    public NewAccountPage openNewAccountPage(){
        openNewAccount.click();
        return new NewAccountPage(page);
    }

    public TransferFundsPage openTransferFundsPage(){
        transferFunds.click();
        return new TransferFundsPage(page);
    }
}
