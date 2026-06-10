package com.parabank.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import static com.microsoft.playwright.options.AriaRole.*;

public class TransferFundsPage {
    private final Page page;
    private final Locator amountField;
    private final Locator fromAccountField;
    private final Locator toAccountField;
    private final Locator transferButton;

    public TransferFundsPage(Page page){
        this.page = page;
        this.amountField = page.locator("#amount");
        this.fromAccountField = page.locator("#fromAccountId");
        this.toAccountField = page.locator("#toAccountId");
        this.transferButton = page.getByRole(BUTTON, new Page.GetByRoleOptions().setName("Transfer"));
    }

    public void createTransfer(double transferAmount, String fromAccountId, String toAccountId){
        amountField.fill(String.valueOf(transferAmount));
        fromAccountField.selectOption(fromAccountId);
        toAccountField.selectOption(toAccountId);
        transferButton.click();
    }

    public Locator transferSuccessHeading(){
        return page.getByRole(HEADING, new Page.GetByRoleOptions().setName("Transfer Complete!"));
    }
}
