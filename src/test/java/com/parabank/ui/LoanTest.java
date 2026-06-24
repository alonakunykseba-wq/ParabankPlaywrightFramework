package com.parabank.ui;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.microsoft.playwright.APIResponse;
import com.parabank.apiservices.AccountApiService;
import com.parabank.models.api.AccountDetailsResponse;
import com.parabank.pages.LoanRequestStatusPage;
import com.parabank.pages.MainPage;
import com.parabank.ui.base.BaseUITestWithRegistration;
import io.qameta.allure.Description;
import org.testng.annotations.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class LoanTest extends BaseUITestWithRegistration {
    @Test(description = "TC-13 (Hybrid): loanRequestWithinAvailableFundsShouldBeApproved")
    @Description("""
            Verifies that loan request with amount within available funds is approved.
            Expected Result: loan request is approved, a new loan account is created, the loan account balance equal to the requested loan amount
            """)
    void loanRequestWithinAvailableFundsShouldBeApproved() throws JsonProcessingException {
        MainPage mainPage = new MainPage(page);
        int checkingAccountNumber = mainPage
                .openAccountsOverview()
                .getDefaultAccountId();
        double loanAmount = 500.00;
        double downPayment = 10.00;
        LoanRequestStatusPage loanRequestStatusPage = mainPage
                .openRequestLoanPage()
                .submitLoanApplication(loanAmount, downPayment, checkingAccountNumber);
        int loanAccountId = loanRequestStatusPage.getAccountNumber();
        assertThat(loanRequestStatusPage.successHeadingLocator()).isVisible();
        assertEquals(loanRequestStatusPage.getLoanRequestStatus().trim(),"Approved", "Loan Request status mismatch");
        AccountApiService accountApiService = new AccountApiService(page.context().request());
        APIResponse loanAccountResponseRaw = accountApiService.getAccountDetailsWithSession(loanAccountId);
        AccountDetailsResponse loanAccountResponseJson = accountApiService.deserializeResponse(loanAccountResponseRaw);
        assertEquals(loanAccountResponseJson.getType(), "LOAN", "Account type mismatch");
        assertEquals(loanAccountResponseJson.getBalance(),loanAmount, "Loan account balance mismatch");
    }
}
