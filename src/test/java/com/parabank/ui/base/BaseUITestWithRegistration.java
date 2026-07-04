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

    // Isolate the registered user context per thread so tests can access credentials safely
    private static final ThreadLocal<User> threadUser = new ThreadLocal<>();

    /**
     * Public getter for test classes (like LoginTest) to safely retrieve 
     * the specific user credentials registered by this thread execution.
     */
    public User getRegisteredUser() {
        return threadUser.get();
    }

    @BeforeMethod
    public void setupTestState() {
        // Local variable allocation: completely safe from cross-thread overrides
        User user = DataGenerator.generateRandomUser();
        
        // Save to the ThreadLocal wrapper for downstream test access
        threadUser.set(user);

        // Fetching page context dynamically from our thread-safe factory
        LoginPage loginPage = new LoginPage(PlaywrightFactory.getPage());
        
        MainPage mainPage = loginPage
                .openRegistrationForm() // Matches your exact method sequence in 42232.png
                .registerNewUserWithSuccess(user);

        String expectedText = String.format("Welcome %s", user.getUsername());
        assertThat(mainPage.welcomeMessageLocator()).hasText(expectedText);
    }

    @AfterMethod
    public void cleanUpUserThreadState() {
        // Wipe the thread context when the individual test finishes to avoid memory leaks
        threadUser.remove();
    }
}
