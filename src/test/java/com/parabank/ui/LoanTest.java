package com.parabank.ui;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.microsoft.playwright.APIResponse;
import com.parabank.apiservices.AccountApiService;
import com.parabank.apiservices.LoanApiService;
import com.parabank.models.api.AccountDetailsResponse;
import com.parabank.models.api.LoanResponseDenied;
import com.parabank.pages.LoanRequestStatusPage;
import com.parabank.pages.MainPage;
import com.parabank.setup.PlaywrightFactory;
import com.parabank.ui.base.BaseUITestWithRegistration;
import com.parabank.utils.api.JacksonUtil;
import io.qameta.allure.Description;
import org.testng.annotations.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.testng.Assert.*;

public class LoanTest extends BaseUITestWithRegistration {
    @Test(description = "TC-13 (Hybrid): loanRequestWithinAvailableFundsShouldBeApproved")
    @Description("""
            Verifies that loan request with amount within available funds is approved.
            Expected Result: loan request is approved, a new loan account is created, the loan account balance equal to the requested loan amount
            """)
    void loanRequestWithinAvailableFundsShouldBeApproved() throws JsonProcessingException {
        MainPage mainPage = new MainPage(PlaywrightFactory.getPage());
        int checkingAccountId = mainPage
                .openAccountsOverview()
                .getDefaultAccountId();
        double loanAmount = 500.00;
        double downPayment = 10.00;
        LoanRequestStatusPage loanRequestStatusPage = mainPage
                .openRequestLoanPage()
                .submitLoanApplication(loanAmount, downPayment, checkingAccountId);
        int loanAccountId = loanRequestStatusPage.getAccountNumber();
        assertThat(loanRequestStatusPage.successHeadingLocator()).isVisible();
        assertEquals(loanRequestStatusPage.getLoanRequestStatus().trim(), "Approved", "Loan Request status mismatch");
        AccountApiService accountApiService = new AccountApiService(PlaywrightFactory.getPage().context().request());
        APIResponse loanAccountResponseRaw = accountApiService.getAccountDetailsWithSession(loanAccountId);
        AccountDetailsResponse loanAccountResponseJson = JacksonUtil.deserialize(loanAccountResponseRaw, AccountDetailsResponse.class );
        assertEquals(loanAccountResponseJson.getType(), "LOAN", "Account type mismatch");
        assertEquals(loanAccountResponseJson.getBalance(), loanAmount, "Loan account balance mismatch");
    }

    @Test(description = "TC-14 (Negative): loanRequestBeyondAvailableFundsShouldBeDenied")
    @Description("""
            Verifies that a massive loan request is declined.
            Expected Result:
            """)
    void loanRequestBeyondAvailableFundsShouldBeDenied() throws JsonProcessingException {
        MainPage mainPage = new MainPage(PlaywrightFactory.getPage());
        int checkingAccountId = mainPage
                .openAccountsOverview()
                .getDefaultAccountId();
        double loanAmount = 10000.00;
        double downPayment = 10.00;
        AccountApiService accountApiService = new AccountApiService(PlaywrightFactory.getPage().context().request());
        AccountDetailsResponse loanAccountResponseJson = JacksonUtil.deserialize(
                accountApiService.getAccountDetailsWithSession(checkingAccountId), AccountDetailsResponse.class
        );
        int customerId = loanAccountResponseJson.getCustomerId();
        LoanApiService loanApiService = new LoanApiService(PlaywrightFactory.getPage().context().request());
        LoanApiService.LoanRequest loanRequest = new LoanApiService.LoanRequest(
                customerId,
                loanAmount,
                downPayment,
                checkingAccountId
        );
        APIResponse loanResponseRaw = loanApiService.postLoanRequestWithSession(loanRequest);
        LoanResponseDenied loanResponseJson = JacksonUtil.deserialize(loanResponseRaw, LoanResponseDenied.class);
        assertFalse(loanResponseJson.isApproved(),  "Expected loan request status mismatch");
        assertNull(loanResponseJson.getAccountId(), "Loan account Id should be null");
    }
}
