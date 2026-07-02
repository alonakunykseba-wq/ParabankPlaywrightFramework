package com.parabank.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.parabank.utils.LocatorUtil;

import static com.microsoft.playwright.options.AriaRole.*;

public class MainPage {
    private final Page page;
    private final Locator openNewAccountLink;
    private final Locator accountsOverviewLink;
    private final Locator findTransactionsLink;
    private final Locator updateContactInfoLink;
    private final Locator transferFundsLink;
    private final Locator requestLoanLink;
    private final Locator signOutLink;

    public MainPage(Page page) {
        this.page = page;
        this.openNewAccountLink = page.getByRole(LINK, LocatorUtil.name("Open New Account"));
        this.accountsOverviewLink = page.getByRole(LINK, LocatorUtil.name("Accounts Overview"));
        this.findTransactionsLink = page.getByRole(LINK, LocatorUtil.name("Find Transactions"));
        this.updateContactInfoLink = page.getByRole(LINK, LocatorUtil.name("Update Contact Info"));
        this.transferFundsLink = page.getByRole(LINK, LocatorUtil.name("Transfer Funds"));
        this.requestLoanLink = page.getByRole(LINK, LocatorUtil.name("Request Loan"));
        this.signOutLink = page.getByRole(LINK, LocatorUtil.name("Log Out"));
    }

    public Locator welcomeMessageLocator() {
        return page.locator("h1.title");
    }

    public LoginPage submitLogOut(){
        signOutLink.click();
        return new LoginPage(page);
    }

    public Locator welcomeUserLocator(){
        return page.locator("p.smallText");
    }

    public AccountsOverviewPage openAccountsOverview() {
        accountsOverviewLink.click();
        return new AccountsOverviewPage(page);
    }

    public NewAccountPage openNewAccountPage(){
        openNewAccountLink.click();
        return new NewAccountPage(page);
    }

    public TransferFundsPage openTransferFundsPage(){
        transferFundsLink.click();
        return new TransferFundsPage(page);
    }

    public RequestLoanPage openRequestLoanPage(){
        requestLoanLink.click();
        return new RequestLoanPage(page);
    }

    public UpdateProfileSuccessPage openUpdateProfilePage(){
        updateContactInfoLink.click();
        return new UpdateProfileSuccessPage(page);
    }
}
