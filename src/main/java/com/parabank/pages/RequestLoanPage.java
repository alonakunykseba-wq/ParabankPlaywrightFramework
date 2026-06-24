package com.parabank.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.parabank.utils.LocatorUtil;

import static com.microsoft.playwright.options.AriaRole.*;

public class RequestLoanPage {
    private final Page page;

    private final Locator loanAmountField;
    private final Locator downPaymentField;
    private final Locator fromAccountField;
    private final Locator applyLoanButton;

    public RequestLoanPage(Page page){
        this.page = page;
        this.loanAmountField = page.locator("#amount");
        this.downPaymentField = page.locator("#downPayment");
        this.fromAccountField = page.locator("#fromAccountId");
        this.applyLoanButton = page.getByRole(BUTTON, LocatorUtil.name("Apply Now"));
    }

    public LoanRequestStatusPage submitLoanApplication(double loanAmount, double downPayment, int fromAccountId){
        loanAmountField.fill(String.valueOf(loanAmount));
        downPaymentField.fill(String.valueOf(downPayment));
        fromAccountField.selectOption(String.valueOf(fromAccountId));
        applyLoanButton.click();
        return new LoanRequestStatusPage(page);
    }
}
