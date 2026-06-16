package com.parabank.ui;

import com.parabank.ui.base.BaseUITestWithRegistration;

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
        overviewPage.submitLogOut();
    }

    @Test(description = "TC-03 (UI): User with valid credentials should be able to log in successfully")
    @Description(""" 
            Verifies that a registered user can successfully log in with valid credentials.
            Expected Result: The user is redirected to the Accounts Overview page and the Welcome banner is displayed.
            """)
    public void userWithValidCredentialsShouldBeAbleToLogin() {
        MainPage mainPage = new LoginPage(page)
                .loginWithValidCredentials(user.getUsername(), user.getPassword());
        String fullName = user.getFirstName() + " " + user.getLastName();
        assertThat(mainPage.welcomeUserLocator())
                .containsText(fullName);
    }

    @Test(description = "TC-04: Login attempt with invalid credentials should show an error message")
    @Description("""
            Verifies that user with invalid credentials cannot  log in.
            Expected Result: The error message is displayed, user stayed on the main page.
            """)
   public void loginWithInvalidCredentialsShouldDisplayErrorMessage(){
        String expectedMessage= "The username and password could not be verified.";
        LoginPage loginPage = new LoginPage(page)
                .loginWithInvalidCredentials(user.getUsername(), "123");
        assertThat(loginPage.errorTitleLocator())
                .hasText("Error!");
        assertThat(loginPage.errorMessageLocator())
                .hasText(expectedMessage);
    }
}
