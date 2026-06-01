package com.parabank.tests;

import com.parabank.base.BaseTestWithRegistration;

import com.parabank.pages.AccountOverviewPage;
import com.parabank.pages.MainPage;
import io.qameta.allure.Description;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginTest extends BaseTestWithRegistration {

    @BeforeMethod
    private void setPreconditions() {
        AccountOverviewPage overviewPage = new AccountOverviewPage(page);
        overviewPage.logOut();
    }

    @Test(description = "TC-03:(UI): verifyValidUserLoginTest")
    @Description(""" 
            Verifies that a registered user can successfully log in with valid credentials.
            Expected Result: The user is redirected to the Accounts Overview page and the Welcome banner is displayed.
            """)
    public void verifyValidUserLoginTest() {
        MainPage mainPage = new MainPage(page);
        mainPage.logIn(user.getUsername(), user.getPassword());
        AccountOverviewPage overviewPage = new AccountOverviewPage(page);
        String fullName = user.getFirstName() + " " + user.getLastName();
        assertThat(overviewPage.welcomeUser()).containsText(fullName);
    }

    @Test(description = "TC 04: verifyInvalidLoginCredentialsTest")
    @Description("""
            Verifies that user with invalid credentials cannot  log in.
            Expected Result: The error message is displayed, user stayed on the main page.
            """)
   public  void verifyInvalidLoginCredentialsTest(){
        String expectedMessage= "The username and password could not be verified.";
        MainPage mainPage = new MainPage(page);
        mainPage.logIn(user.getUsername(), "123");
        assertThat(mainPage.errorTitle()).hasText("Error!");
        assertThat(mainPage.errorMessage()).hasText(expectedMessage);
    }
}
