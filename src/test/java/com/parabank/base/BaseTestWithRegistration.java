package com.parabank.base;

import com.parabank.models.User;
import com.parabank.pages.MainPage;
import com.parabank.pages.RegistrationPage;
import com.parabank.utils.DataGenerator;
import org.testng.annotations.BeforeMethod;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


public class BaseTestWithRegistration extends BaseTest {
    protected User user;

    @BeforeMethod
    public void setupTestState() {
        this.user = DataGenerator.generateRandomUser();
        MainPage mainPage = new MainPage(page);
        RegistrationPage regPage = mainPage.openRegistrationForm();
        regPage.registerNewUser(user);
        String expectedText = String.format("Welcome %s", user.getUsername());
        assertThat(regPage.registrationConfirmation()).hasText(expectedText);
    }
}
