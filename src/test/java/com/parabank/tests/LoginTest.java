package com.parabank.tests;

import com.parabank.base.BaseTestWithRegistration;

import com.parabank.pages.MainPage;
import com.parabank.pages.LoginPage;
import io.qameta.allure.Description;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginTest extends BaseTestWithRegistration {

    @BeforeMethod
    private void setPreconditions() {
        MainPage overviewPage = new MainPage(page);
        overviewPage.logOut();
    }

    @Test(description = "TC-03:(UI): verifyValidUserLoginTest")
    @Description(""" 
            Verifies that a registered user can successfully log in with valid credentials.
            Expected Result: The user is redirected to the Accounts Overview page and the Welcome banner is displayed.
            """)
    public void verifyValidUserLoginTest() {
        LoginPage loginPage = new LoginPage(page);
        loginPage.logIn(user.getUsername(), user.getPassword());
        MainPage mainPage = new MainPage(page);
        String fullName = user.getFirstName() + " " + user.getLastName();
        assertThat(mainPage.welcomeUser())
                .containsText(fullName);
    }

    @Test(description = "TC 04: verifyInvalidLoginCredentialsTest")
    @Description("""
            Verifies that user with invalid credentials cannot  log in.
            Expected Result: The error message is displayed, user stayed on the main page.
            """)
   public  void verifyInvalidLoginCredentialsTest(){
        String expectedMessage= "The username and password could not be verified.";
        LoginPage loginPage = new LoginPage(page);
        loginPage.logIn(user.getUsername(), "123");
        assertThat(loginPage.errorTitle())
                .hasText("Error!");
        assertThat(loginPage.errorMessage())
                .hasText(expectedMessage);
    }
}
