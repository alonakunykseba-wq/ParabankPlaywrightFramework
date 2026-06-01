package com.parabank.base;

import com.parabank.models.User;
import com.parabank.pages.AccountOverviewPage;
import com.parabank.pages.MainPage;
import com.parabank.utils.DataGenerator;
import org.testng.annotations.BeforeMethod;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


public class BaseTestWithRegistration extends BaseTest {
    protected User user;

    @BeforeMethod
    public void setupTestState() {
        this.user = DataGenerator.generateRandomUser();
        MainPage mainPage = new MainPage(page);
        mainPage.openRegistrationForm().registerNewUser(user);
        AccountOverviewPage overviewPage = new AccountOverviewPage(page);
        String expectedText = String.format("Welcome %s", user.getUsername());
        assertThat(overviewPage.welcomeMessage()).hasText(expectedText);
    }
}
