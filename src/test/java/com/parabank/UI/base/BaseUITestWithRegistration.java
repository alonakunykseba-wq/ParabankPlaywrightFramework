package com.parabank.UI.base;

import com.parabank.models.UI.User;
import com.parabank.pages.LoginPage;
import com.parabank.pages.MainPage;
import com.parabank.utils.DataGenerator;
import org.testng.annotations.BeforeMethod;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


public class BaseUITestWithRegistration extends BaseUITest {
    protected User user;

    @BeforeMethod
    public void setupTestState() {
        this.user = DataGenerator.generateRandomUser();
        LoginPage loginPage = new LoginPage(page);
        loginPage.openRegistrationForm().registerNewUser(user);
        MainPage mainPage = new MainPage(page);
        String expectedText = String.format("Welcome %s", user.getUsername());
        assertThat(mainPage.welcomeMessageLocator())
                .hasText(expectedText);
    }
}
