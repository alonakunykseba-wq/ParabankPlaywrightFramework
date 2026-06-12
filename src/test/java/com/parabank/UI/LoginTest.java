package com.parabank.UI;

import com.parabank.UI.base.BaseUITestWithRegistration;

import com.parabank.pages.MainPage;
import com.parabank.pages.LoginPage;
import io.qameta.allure.Description;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginTest extends BaseUITestWithRegistration {

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
        loginPage.loginWithValidCredentials(user.getUsername(), user.getPassword());
        MainPage mainPage = new MainPage(page);
        String fullName = user.getFirstName() + " " + user.getLastName();
        assertThat(mainPage.welcomeUserLocator())
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
        loginPage.loginWithInvalidCredentials(user.getUsername(), "123");
        assertThat(loginPage.errorTitleLocator())
                .hasText("Error!");
        assertThat(loginPage.errorMessageLocator())
                .hasText(expectedMessage);
    }
}
