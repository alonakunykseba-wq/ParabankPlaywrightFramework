package com.parabank.ui;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.microsoft.playwright.APIResponse;
import com.parabank.apiservices.AccountApiService;
import com.parabank.pages.*;
import com.parabank.ui.base.BaseUITestWithRegistration;

import com.parabank.models.api.AccountDetailsResponse;
import com.parabank.utils.api.JacksonUtil;
import org.testng.annotations.Test;
import io.qameta.allure.Description;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class TransactionsTest extends BaseUITestWithRegistration {

    @Test(description = "TC-09 (UI): Transferring funds between checking and savings accounts should succeed")
    @Description("""
            Verifies that a user can successfully transfer funds between two of their own accounts via the UI.
            Expected Result: The UI dropdowns populate correctly, the transfer processes, and the system displays a "Transfer Complete!" success message.
            """)
    public void fundsTransferBetweenAccountsShouldBeSuccessful() {
        MainPage mainPage = new MainPage(page);
        int checkingAccountNumber = mainPage
                .openAccountsOverview()
                .getDefaultAccountId();
        int savingsAccountNumber = mainPage
                .openNewAccountPage()
                .openNewAccount("SAVINGS")
                .getAccountNumber();
        TransferFundsPage transferPage = mainPage.openTransferFundsPage();
        transferPage.createTransfer(10.5, checkingAccountNumber, savingsAccountNumber);
        assertThat(transferPage.transferSuccessHeadingLocator()).containsText("Transfer Complete!");
    }

    @Test(description = "TC-10 (Hybrid): Paying a bill via the API should correctly reduce the account balance on the UI")
    @Description("""
                Verifies that submitting a Bill Payment via API instantly updates the User's balance in the UI.
                Expected Result: The Frontend UI balance perfectly reflects the backend API deduction.
            """)
    public void billPaymentViaApiShouldDeductCorrectAmountFromUiBalance() throws JsonProcessingException {
        double billAmount = 15.5;
        MainPage mainPage = new MainPage(page);
        int defaultAccountId = mainPage
                .openAccountsOverview()
                .getDefaultAccountId();
        AccountApiService accountApiService = new AccountApiService(page.context().request());
        APIResponse accountDetailsResponseRaw = accountApiService.getAccountDetailsWithSession(defaultAccountId);
        AccountDetailsResponse accountDetails = JacksonUtil.deserialize(accountDetailsResponseRaw, AccountDetailsResponse.class);
        double accountBalanceBeforeBill = accountDetails.getBalance();
        APIResponse payBillResponse = accountApiService.payBillWithSession(defaultAccountId, billAmount);
        assertEquals(payBillResponse.status(), 200);
        String defaultAccountBalance = mainPage
                .openAccountsOverview()
                .balanceLocator(defaultAccountId)
                .textContent();
        double accountBalanceAfterBill = Double.parseDouble(defaultAccountBalance.replace("$", ""));
        assertEquals(accountBalanceAfterBill, (accountBalanceBeforeBill - billAmount), "account balance in web doesn't reflect bill amount deduction");
    }

    // The test is currently disabled because the call returns 200 instead of 400
    // and posts transfer with negative amount. This is a known bug of the API.
    @Test(enabled = false, description = "TC-11 (Hybrid) negativeTransferAmountShouldBeRejectedWithBadRequest")
    @Description("""
                Verifies that posting a transfer with negative amount via API is not possible.
                Expected Result: The backend API returns Status code 400.
            """)

    public void negativeTransferAmountShouldBeRejectedWithBadRequest() {
        double amount = -15.00;
        MainPage mainPage = new MainPage(page);
        int checkingAccountId = mainPage
                .openAccountsOverview()
                .getDefaultAccountId();
        OpenAccountSuccessPage successPage = mainPage
                .openNewAccountPage()
                .openNewAccount("savings");
        int savingsAccountId = successPage.getAccountNumber();
        AccountApiService accountApiService = new AccountApiService(page.context().request());
        APIResponse transferResponse = accountApiService
                .postTransferWithSession(checkingAccountId, savingsAccountId, amount);
        assertEquals(transferResponse.status(), 400, "The status code is not as expected");
        assertTrue(transferResponse.text().contains("Status 400 – Bad Request"), "Response text mismatch:" + transferResponse.text());
    }

    @Test(description = "TC- 12: apiDepositShouldCorrectlyIncreaseAccountBalance")
    @Description("""
               Verifies that performing a deposit via the API successfully increases the account balance.
               Expected Result: The deposit succeeds with status code 200,
               and the updated account details retrieved via the API show that the balance has increased by the deposited amount.
            """)
    void apiDepositShouldCorrectlyIncreaseAccountBalance() throws JsonProcessingException {
        double amount = 10.5;
        AccountsOverviewPage overview = new MainPage(page).openAccountsOverview();
        int checkingAccountId = overview.getDefaultAccountId();
        double initialBalance = overview.getAccountBalance(checkingAccountId);
        AccountApiService accountApiService = new AccountApiService(page.context().request());
        APIResponse depositResponse = accountApiService.postDepositWithSession(checkingAccountId, amount);
        assertEquals(depositResponse.status(), 200, "Status code mismatch: 200 is expected");
        assertTrue(depositResponse.text().contains("Successfully deposited"), "Response text mismatch");
        assertTrue(depositResponse.text().contains(String.valueOf(amount)), "Response text mismatch in amount value");
        assertTrue(depositResponse.text().contains(String.valueOf(checkingAccountId)), "Response text mismatch in account Id");
        APIResponse accountDetailsResponseRaw = accountApiService.getAccountDetailsWithSession(checkingAccountId);
        AccountDetailsResponse accountDetailsResponseJson = JacksonUtil.deserialize(accountDetailsResponseRaw,AccountDetailsResponse.class);
        assertEquals(accountDetailsResponseJson.getBalance(), initialBalance + amount, "Account balance mismatch");
    }
}
