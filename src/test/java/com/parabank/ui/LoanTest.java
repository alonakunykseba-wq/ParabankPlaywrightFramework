package com.parabank.ui;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.parabank.apiservices.AccountApiService;
import com.parabank.apiservices.LoanApiService;
import com.parabank.models.api.LoanRequest;
import com.parabank.models.api.AccountDetailsResponse;
import com.parabank.models.api.LoanResponseDenied;
import com.parabank.pages.LoanRequestStatusPage;
import com.parabank.pages.MainPage;
import com.parabank.setup.PlaywrightFactory;
import com.parabank.ui.base.BaseUITestWithRegistration;
import com.parabank.utils.TestDataRepository;
import com.parabank.utils.api.JacksonUtil;
import io.qameta.allure.Description;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.testng.Assert.*;

public class LoanTest extends BaseUITestWithRegistration {
    private MainPage mainPage;
    private APIRequestContext request;
    private int fromAccountId;

    @BeforeMethod (alwaysRun = true)
    public void setUpTest() {
        mainPage = new MainPage(PlaywrightFactory.getPage());
        request = PlaywrightFactory.getPage().context().request();
        fromAccountId = mainPage
                .openAccountsOverview()
                .getDefaultAccountId();
    }

    @DataProvider(name ="loanAmountData")
    Object[][] getLoanAmountData(){
        return TestDataRepository.getLoanAmount();
        }

    @Test(description = "TC-13 (Hybrid): loanRequestWithinAvailableFundsShouldBeApproved")
    @Description("""
            Verifies that loan request with amount within available funds is approved.
            Expected Result: loan request is approved, a new loan account is created, the loan account balance equal to the requested loan amount
            """)
    void loanRequestWithinAvailableFundsShouldBeApproved() throws JsonProcessingException {

        double loanAmount = TestDataRepository.getAmount("loanAmount");
        LoanRequestStatusPage loanRequestStatusPage = mainPage
                .openRequestLoanPage()
                .submitLoanApplication(loanAmount, loanAmount * 0.10, fromAccountId);
        int loanAccountId = loanRequestStatusPage.getAccountNumber();
        assertThat(loanRequestStatusPage.successHeadingLocator()).isVisible();
        assertEquals(loanRequestStatusPage.getLoanRequestStatus().trim(), "Approved", "Loan Request status mismatch");
        AccountApiService accountApiService = new AccountApiService(request);
        APIResponse loanAccountResponseRaw = accountApiService.getAccountDetailsWithSession(loanAccountId);
        AccountDetailsResponse loanAccountResponseJson = JacksonUtil.deserialize(loanAccountResponseRaw, AccountDetailsResponse.class );
        assertEquals(loanAccountResponseJson.getType(), "LOAN", "Account type mismatch");
        assertEquals(loanAccountResponseJson.getBalance(), loanAmount, "Loan account balance mismatch");
    }

    @Test(description = "TC-14 (Negative): loanRequestBeyondAvailableFundsShouldBeDenied",
    dataProvider = "loanAmountData")
    @Description("""
            Verifies that a massive loan request is declined.
            Expected Result:
            """)
    void loanRequestBeyondAvailableFundsShouldBeDenied(double loanAmount) throws JsonProcessingException {
        AccountApiService accountApiService = new AccountApiService(request);
        AccountDetailsResponse loanAccountResponseJson = JacksonUtil.deserialize(
                accountApiService.getAccountDetailsWithSession(fromAccountId), AccountDetailsResponse.class);
        int customerId = loanAccountResponseJson.getCustomerId();
        LoanApiService loanApiService = new LoanApiService(request);
        LoanRequest loanRequest = new LoanRequest()
                .setCustomerId(customerId)
                .setAmount(loanAmount)
                .setDownPayment(loanAmount * 0.10)
                .setFromAccountId(fromAccountId);
        APIResponse loanResponseRaw = loanApiService.postLoanRequestWithSession(loanRequest);
        LoanResponseDenied loanResponseJson = JacksonUtil.deserialize(loanResponseRaw, LoanResponseDenied.class);
        assertFalse(loanResponseJson.isApproved(),  "Expected loan request status mismatch");
        assertNull(loanResponseJson.getAccountId(), "Loan account Id should be null");
    }
}
