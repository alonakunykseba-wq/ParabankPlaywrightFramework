package com.parabank.ui;

import com.parabank.setup.PlaywrightFactory;
import com.parabank.ui.base.BaseUITestWithRegistration;

import com.parabank.pages.MainPage;
import com.parabank.pages.LoginPage;
import io.qameta.allure.Description;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginTest extends BaseUITestWithRegistration {

    @BeforeMethod
    private void setPreconditions() {
        MainPage overviewPage = new MainPage(PlaywrightFactory.getPage());
        overviewPage.submitLogOut();
    }

    @DataProvider(name ="invalidLoginCredentials")
    public Object[][] getInvalidCredentials(){
        return new Object[][]{
                {"[VALID_USER]", " "},
                {"[VALID_USER]", "password"},
                {" ", "[VALID_PASSWORD]"},
                {"Abrakadabra", "[VALID_PASSWORD]"},
                {"' OR '1'='1", "' OR '1'='1"},
                {"admin' --", "anything"},
                {"' OR 1=1 --", "password"},
                {"') OR ('1'='1", "') OR ('1'='1"}
        };
    }
    @Test(description = "TC-03 (UI): User with valid credentials should be able to log in successfully")
    @Description(""" 
            Verifies that a registered user can successfully log in with valid credentials.
            Expected Result: The user is redirected to the Accounts Overview page and the Welcome banner is displayed.
            """)
    public void userWithValidCredentialsShouldBeAbleToLogin() {
        MainPage mainPage = new LoginPage(PlaywrightFactory.getPage())
                .loginWithValidCredentials(getRegisteredUser().getUsername(), getRegisteredUser().getPassword());
        String fullName = getRegisteredUser().getFirstName() + " " + getRegisteredUser().getLastName();
        assertThat(mainPage.welcomeUserLocator())
                .containsText(fullName);
    }

    @Test(description = "TC-04: Login attempt with invalid credentials should fail and show an error message",
    dataProvider = "invalidLoginCredentials")
    @Description("""
            Verifies that user with invalid credentials cannot  log in.
            Expected Result: The error message is displayed, user stayed on the main page.
            """)
   public void loginWithInvalidCredentialsShouldFail(String usernamePlaceholder, String passwordPlaceholder){
        String expectedMessage= "The username and password could not be verified.";
        String username = usernamePlaceholder.replace("[VALID_USER]", getRegisteredUser().getUsername());
        String password = passwordPlaceholder.replace("[VALID_PASSWORD]", getRegisteredUser().getPassword());
        LoginPage loginPage = new LoginPage(PlaywrightFactory.getPage())
                .loginWithInvalidCredentials(username, password);
        assertThat(loginPage.errorTitleLocator())
                .hasText("Error!");
        assertThat(loginPage.errorMessageLocator())
                .hasText(expectedMessage);
    }
}
