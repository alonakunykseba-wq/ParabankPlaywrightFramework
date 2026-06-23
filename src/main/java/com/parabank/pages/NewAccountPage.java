package com.parabank.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.SelectOption;
import com.parabank.utils.LocatorUtil;

import static com.microsoft.playwright.options.AriaRole.*;

public class NewAccountPage {
    private final Page page;
    private final Locator accountTypeList;
    private final Locator openNewAccountButton;
    private final Locator fromAccountIdOption;

    public NewAccountPage(Page page) {
        this.page = page;
        this.accountTypeList = page.getByRole(COMBOBOX).first();
        this.openNewAccountButton = page.getByRole(BUTTON, LocatorUtil.name("Open New Account"));
        this.fromAccountIdOption = page.locator("#fromAccountId");
    }

    public OpenAccountSuccessPage openNewAccount(String accountType){
        fromAccountIdOption.selectOption(new SelectOption().setIndex(0));
        accountTypeList.selectOption(accountType.toUpperCase());
        openNewAccountButton.click();
        return new OpenAccountSuccessPage(page);
    }
}
