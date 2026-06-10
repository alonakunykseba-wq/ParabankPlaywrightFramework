package com.parabank.UI;

import com.microsoft.playwright.APIResponse;
import com.parabank.APIServices.AccountAPIService;
import com.parabank.UI.base.BaseUITestWithRegistration;
import com.parabank.models.API.AccountResponse;
import com.parabank.pages.AccountsOverviewPage;
import com.parabank.pages.MainPage;
import com.parabank.pages.NewAccountPage;
import com.parabank.pages.OpenAccountSuccessPage;
import com.parabank.utils.JacksonUtil;
import io.qameta.allure.Description;
import org.testng.annotations.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.testng.Assert.*;

import java.util.regex.Pattern;

public class AccountManagementTest extends BaseUITestWithRegistration {

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
        assertTrue(Pattern.matches("\\d+", successPage.getAccountNumber()),
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

    @Test (description = "TC 07: verifyAccountDetailsSchemaTest")
    @Description ("""
             Verifies the Account Details API endpoint returns the correct JSON schema for a valid account.
             Expected Result: The API responds with a 200 OK and the expected JSON structure containing valid ID, Customer ID, Account Type, and Balance fields.
            """)
    public void verifyAccountDetailsSchemaTest() throws Exception {
        AccountsOverviewPage accountsOverview = new MainPage(page).openAccountsOverview();
        int accountId = Integer.parseInt(accountsOverview.getDefaultAccountId());
        AccountAPIService accountApi = new AccountAPIService(page.context().request());
        APIResponse response = accountApi.getAccountDetailsViaUi(accountId);
        assertEquals(response.status(), 200, "The returned code is not as expected");
        AccountResponse accountDetailsResponse = JacksonUtil.getMapper().readValue(response.text(), AccountResponse.class);
        assertTrue(accountDetailsResponse.getId()>0);
        assertTrue(accountDetailsResponse.getCustomerId() >0);
        assertFalse(accountDetailsResponse.getType().isEmpty());
        assertTrue(accountDetailsResponse.getBalance()>0.00);
    }

}
