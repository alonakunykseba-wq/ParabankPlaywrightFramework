package com.parabank.UI;

import com.parabank.UI.base.BaseUITestWithRegistration;

import com.parabank.pages.MainPage;
import com.parabank.pages.TransferFundsPage;
import org.testng.annotations.Test;
import io.qameta.allure.Description;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class TransactionsTest extends BaseUITestWithRegistration {

    @Test(description = "TC-09(UI): verifyTransferFundsBetweenAccountsTest")
    @Description("""
            Verifies that a user can successfully transfer funds between two of their own accounts via the UI.
            Expected Result: The UI dropdowns populate correctly, the transfer processes, and the system displays a "Transfer Complete!" success message.
            """)
    public void verifyTransferFundsBetweenAccountsTest(){
        MainPage mainPage = new MainPage(page);
        String checkingAccountNumber = mainPage.openAccountsOverview().getDefaultAccountId();
        String savingsAccountNumber = mainPage
                .openNewAccountPage()
                .openNewAccount("SAVINGS")
                .getAccountNumber();
        TransferFundsPage transferPage = mainPage.openTransferFundsPage();
        transferPage.createTransfer(10.5, checkingAccountNumber, savingsAccountNumber);
        assertThat(transferPage.transferSuccessHeading()).containsText("Transfer Complete!");
    }
}
