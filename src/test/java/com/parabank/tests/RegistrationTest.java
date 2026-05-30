package com.parabank.tests;

import com.parabank.base.BaseTest;
import com.parabank.models.User;
import com.parabank.pages.MainPage;
import com.parabank.pages.RegistrationPage;
import com.parabank.utils.DataGenerator;
import io.qameta.allure.Description;
import org.testng.annotations.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class RegistrationTest extends BaseTest {
    protected User user = DataGenerator.generateRandomUser();

    protected RegistrationPage executeRegistration(User user) {
        MainPage mainPage = new MainPage(page);
        mainPage.openRegistrationForm().registerNewUser(user);
        return new RegistrationPage(page);
    }

    @Test(description = "TC-01: verifyValidUserRegistrationTest")
    @Description("""
            Verifies that a new user can successfully register using a valid, dynamically generated dataset.
            Expected Result: The form submits successfully and the user is immediately logged in and greeted with a personalized Welcome banner.
            """)
    public void verifyValidUserRegistrationTest() {
        RegistrationPage regPage = executeRegistration(this.user);
        String expectedText = String.format("Welcome %s", user.getUsername());
        assertThat(regPage.registrationConfirmation()).hasText(expectedText);
    }

    @Test(description = "TC-02: verifyInvalidRegistrationValidationsTest")
    @Description("""
            Verifies that the registration form correctly enforces mandatory field validations.
            Expected Result: Submitting the form with an empty Last Name prevents account creation and the user remains on the Registration page.
            """)
    public void verifyInvalidRegistrationValidationsTest() {
        user.setLastName("");
        RegistrationPage regPage = executeRegistration(this.user);
        assertThat(regPage.registrationConfirmation()).hasText("Signing up is easy!");
    }


}
