package com.parabank.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import static com.microsoft.playwright.options.AriaRole.*;

public class NewAccountPage {
    private final Page page;
    private final Locator accountTypeList;
    private final Locator openNewAccountButton;

    public NewAccountPage(Page page) {
        this.page = page;
        this.accountTypeList = page.getByRole(COMBOBOX).first();
        this.openNewAccountButton = page.getByRole(BUTTON, new Page.GetByRoleOptions().setName("Open New Account"));
    }

    public OpenAccountSuccessPage openNewAccount(String accountType){
        accountTypeList.selectOption(accountType.toUpperCase());
        openNewAccountButton.click();
        return new OpenAccountSuccessPage(page);
    }

}
