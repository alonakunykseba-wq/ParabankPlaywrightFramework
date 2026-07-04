package com.parabank.ui.base;

import com.parabank.models.ui.User;
import com.parabank.pages.LoginPage;
import com.parabank.pages.MainPage;
import com.parabank.setup.PlaywrightFactory;
import com.parabank.utils.DataGenerator;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class BaseUITestWithRegistration extends BaseUITest {

    @BeforeMethod
    public void setupTestState() {
        User user = DataGenerator.generateRandomUser();
        LoginPage loginPage = new LoginPage(PlaywrightFactory.getPage());
        MainPage mainPage = loginPage
                .openRegistrationForm()
                .registerNewUserWithSuccess(user);
        String expectedText = String.format("Welcome %s", user.getUsername());
        assertThat(mainPage.welcomeMessageLocator())
                .hasText(expectedText);
    }
}
