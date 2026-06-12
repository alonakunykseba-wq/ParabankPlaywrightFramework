package com.parabank.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.parabank.utils.LocatorUtil;

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
        this.openNewAccount = page.getByRole(LINK, LocatorUtil.name("Open New Account"));
        this.accountsOverview = page.getByRole(LINK, LocatorUtil.name("Accounts Overview"));
        this.findTransactions = page.getByRole(LINK, LocatorUtil.name("Find Transactions"));
        this.updateContactInfo = page.getByRole(LINK, LocatorUtil.name("Update Contact Info"));
        this.transferFunds = page.getByRole(LINK,LocatorUtil.name("Transfer Funds"));
        this.signOut = page.getByRole(LINK, LocatorUtil.name ("Log Out"));
    }

    public Locator welcomeMessageLocator() {
        return page.locator("h1.title");
    }

    public LoginPage logOut(){
        signOut.click();
        return new LoginPage(page);
    }

    public Locator welcomeUserLocator(){
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
