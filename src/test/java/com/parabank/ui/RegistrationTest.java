package com.parabank.ui;

import com.parabank.ui.base.BaseUITest;
import com.parabank.models.ui.User;
import com.parabank.pages.MainPage;
import com.parabank.pages.LoginPage;
import com.parabank.pages.RegistrationPage;
import com.parabank.utils.DataGenerator;
import io.qameta.allure.Description;
import org.testng.annotations.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class RegistrationTest extends BaseUITest {

    @Test(description = "TC-01: verifyValidUserRegistrationTest")
    @Description("""
            Verifies that a new user can successfully register using a valid, dynamically generated dataset.
            Expected Result: The form submits successfully and the user is immediately logged in and greeted with a personalized Welcome banner.
            """)
    public void verifyValidUserRegistrationTest() {
        User user = DataGenerator.generateRandomUser();
        LoginPage loginPage = new LoginPage(page);
        MainPage overviewPage = loginPage
                .openRegistrationForm()
                .registerNewUserWithSuccess(user);
        String expectedText = String.format("Welcome %s", user.getUsername());
        assertThat(overviewPage.welcomeMessageLocator())
                .hasText(expectedText);
    }

    @Test(description = "TC-02: verifyInvalidRegistrationValidationsTest")
    @Description("""
            Verifies that the registration form correctly enforces mandatory field validations.
            Expected Result: Submitting the form with an empty Last Name prevents account creation and the user remains on the Registration page.
            """)
    public void verifyInvalidRegistrationValidationsTest() {
        User user = DataGenerator.generateRandomUser();
        LoginPage loginPage = new LoginPage(page);
        user.setLastName("");
        RegistrationPage regPage  = loginPage
                .openRegistrationForm()
                .registerNewUserWithFailure(user);
        assertThat(regPage.signUpMessageLocator())
                .hasText("Signing up is easy!");
    }


}
