package com.parabank.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.parabank.utils.LocatorUtil;

import static com.microsoft.playwright.options.AriaRole.*;

public class RequestLoanPage extends BasePage{
    private final Locator loanAmountField;
    private final Locator downPaymentField;
    private final Locator fromAccountField;
    private final Locator applyLoanButton;

    public RequestLoanPage(Page page){
        super(page);
        this.loanAmountField = page.locator("input#amount");
        this.downPaymentField = page.locator("#downPayment");
        this.fromAccountField = page.locator("#fromAccountId");
        this.applyLoanButton = page.getByRole(BUTTON, LocatorUtil.name("Apply Now"));
    }

    public LoanRequestStatusPage submitLoanApplication(double loanAmount, double downPayment, int fromAccountId){
        safeFill(loanAmountField, String.valueOf(loanAmount));
        safeFill(downPaymentField, String.valueOf(downPayment));
        safeSelectOption(fromAccountField, String.valueOf(fromAccountId));
        safeClick(applyLoanButton);
        return new LoanRequestStatusPage(page);
    }
}
