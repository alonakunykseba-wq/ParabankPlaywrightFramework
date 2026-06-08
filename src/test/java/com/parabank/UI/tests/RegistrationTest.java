package com.parabank.UI.tests;

import com.parabank.UI.base.BaseTest;
import com.parabank.models.UI.User;
import com.parabank.pages.MainPage;
import com.parabank.pages.LoginPage;
import com.parabank.pages.RegistrationPage;
import com.parabank.utils.DataGenerator;
import io.qameta.allure.Description;
import org.testng.annotations.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class RegistrationTest extends BaseTest {

    @Test(description = "TC-01: verifyValidUserRegistrationTest")
    @Description("""
            Verifies that a new user can successfully register using a valid, dynamically generated dataset.
            Expected Result: The form submits successfully and the user is immediately logged in and greeted with a personalized Welcome banner.
            """)
    public void verifyValidUserRegistrationTest() {
        User user = DataGenerator.generateRandomUser();
        LoginPage loginPage = new LoginPage(page);
        loginPage.openRegistrationForm().registerNewUser(user);
        MainPage overviewPage = new MainPage(page);
        String expectedText = String.format("Welcome %s", user.getUsername());
        assertThat(overviewPage.welcomeMessage())
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
        loginPage.openRegistrationForm().registerNewUser(user);
        RegistrationPage regPage = new RegistrationPage(page);
        assertThat(regPage.signUpMessage())
                .hasText("Signing up is easy!");
    }


}
