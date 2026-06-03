package com.parabank.tests;

import com.parabank.base.BaseTestWithRegistration;
import com.parabank.pages.AccountsOverviewPage;
import com.parabank.pages.MainPage;
import com.parabank.pages.NewAccountPage;
import com.parabank.pages.OpenAccountSuccessPage;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import java.util.regex.Pattern;

public class AccountManagementTest extends BaseTestWithRegistration {

    @Test(description ="TC 05: verifyOpenNewCheckingAccountTest" )
    @Description("""
            Verifies that a user can successfully open a new Checking account.
            Expected Result: The account is opened successfully, and a dynamically generated numeric Account ID is displayed to the user.
            """)
    public void  verifyOpenNewCheckingAccountTest (){
        MainPage mainPage = new MainPage(page);
        NewAccountPage newAccountPage = mainPage.openNewAccountPage();
        OpenAccountSuccessPage successPage=newAccountPage.openNewAccount("checking");
        assertThat(successPage.successHeading())
                .isVisible();
        assertThat(successPage.successDescription())
                .isVisible();
        Assert.assertTrue(Pattern.matches("\\d+", successPage.getAccountNumber()),
                "Account number was empty or not numeric! Found: " + successPage.getAccountNumber());
    }

    @Test(description ="TC 06: verifyNewSavingsAccountAppearsInOverviewTest" )
    @Description("""
                  Verifies that a newly created Savings account appears correctly in the user's account list.
                  Expected Result: The new account number is visible in the Accounts Overview table with an initial balance of $100.00.
            """)
    public void  verifyNewSavingsAccountAppearsInOverviewTest (){
        String expectedBalance = "$100.00";
        MainPage mainPage = new MainPage(page);
        NewAccountPage newAccountPage = mainPage.openNewAccountPage();
        OpenAccountSuccessPage successPage=newAccountPage.openNewAccount("savings");
        String savingsAccount = successPage.getAccountNumber();
        AccountsOverviewPage accountsOverview = mainPage.openAccountsOverview();
        assertThat(accountsOverview.accountNumber(savingsAccount))
                .isVisible();
        assertThat(accountsOverview.balance(savingsAccount))
                .hasText(expectedBalance);
    }
}
