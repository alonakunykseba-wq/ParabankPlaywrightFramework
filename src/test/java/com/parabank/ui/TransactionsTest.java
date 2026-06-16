package com.parabank.ui;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.microsoft.playwright.APIResponse;
import com.parabank.apiservices.AccountApiService;
import com.parabank.pages.NewAccountPage;
import com.parabank.pages.OpenAccountSuccessPage;
import com.parabank.ui.base.BaseUITestWithRegistration;

import com.parabank.models.api.AccountDetailsResponse;
import com.parabank.pages.MainPage;
import com.parabank.pages.TransferFundsPage;
import org.testng.annotations.Test;
import io.qameta.allure.Description;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.testng.Assert.assertEquals;

public class TransactionsTest extends BaseUITestWithRegistration {

    @Test(description = "TC-09 (UI): Transferring funds between checking and savings accounts should succeed")
    @Description("""
            Verifies that a user can successfully transfer funds between two of their own accounts via the UI.
            Expected Result: The UI dropdowns populate correctly, the transfer processes, and the system displays a "Transfer Complete!" success message.
            """)
    public void fundsTransferBetweenAccountsShouldBeSuccessful() {
        MainPage mainPage = new MainPage(page);
        String checkingAccountNumber = mainPage
                .openAccountsOverview()
                .getDefaultAccountId();
        String savingsAccountNumber = mainPage
                .openNewAccountPage()
                .openNewAccount("SAVINGS")
                .getAccountNumberLocator();
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
        String defaultAccount = mainPage
                .openAccountsOverview()
                .getDefaultAccountId();
        int accountId = Integer.parseInt(defaultAccount);
        AccountApiService accountApiService = new AccountApiService(page.context().request());
        APIResponse accountDetailsResponseJson = accountApiService.getAccountDetailsWithSession(accountId);
        AccountDetailsResponse accountDetails = accountApiService.deserializeResponse(accountDetailsResponseJson);
        double accountBalanceBeforeBill = accountDetails.getBalance();
        APIResponse payBillResponse = accountApiService.payBillWithSession(accountId, billAmount);
        assertEquals(payBillResponse.status(), 200);
        String defaultAccountBalance= mainPage
                .openAccountsOverview()
                .balanceLocator(defaultAccount)
                .textContent();
        double accountBalanceAfterBill = Double.parseDouble(defaultAccountBalance.replace("$", ""));
        assertEquals(accountBalanceAfterBill, (accountBalanceBeforeBill - billAmount), "account balance in web doesn't reflect bill amount deduction");
    }

    // The test is currently disabled because the call returns 200 instead of 400
    // and posts transfer with negative amount. This is a known bug of the API.
    @ Test( enabled = false, description = "TC-11 (Hybrid) negativeTransferAmountShouldBeRejectedWithBadRequest")
    @Description("""
                Verifies that posting a transfer with negative amount via API is not possible.
                Expected Result: The backend API returns Status code 400.
            """)

    public void negativeTransferAmountShouldBeRejectedWithBadRequest(){
        double amount = -15.00;
        MainPage mainPage = new MainPage(page);
        String checkingAccount = mainPage
                .openAccountsOverview()
                .getDefaultAccountId();
        int checkingAccountId = Integer.parseInt(checkingAccount);
        OpenAccountSuccessPage successPage  = mainPage
                .openNewAccountPage()
                .openNewAccount("savings");
        String savingsAccount = successPage.getAccountNumberLocator();
        int savingsAccountId = Integer.parseInt(savingsAccount);
        AccountApiService accountApiService = new AccountApiService(page.context().request());
        APIResponse transferResponse = accountApiService
                .postTransferWithSession(checkingAccountId,savingsAccountId, amount);
        assertEquals(transferResponse.status(), 400, "The status code is not as expected");
    }
}
