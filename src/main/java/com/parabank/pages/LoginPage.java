package com.parabank.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.parabank.utils.LocatorUtil;

import static com.microsoft.playwright.options.AriaRole.*;

public class LoginPage {
    private final Page page;
    private final Locator registerLink;
    private final Locator usernameField;
    private final Locator passwordField;
    private final Locator logInButton;

    public LoginPage(Page page) {
        this.page = page;
        this.registerLink = page.getByRole(LINK, LocatorUtil.name("Register"));
        this.usernameField =page.locator("input[name='username']");
        this.passwordField =page.locator("input[name='password']");
        this.logInButton = page.getByRole(BUTTON, LocatorUtil.name("Log In"));
    }

    public RegistrationPage openRegistrationForm(){
        registerLink.click();
        return new RegistrationPage(page);
    }

    public void logIn(String username, String password){
        usernameField.fill(username);
        passwordField.fill(password);
        logInButton.click();
    }

    public Locator errorTitle() {
        return page.locator("h1.title");
    }

    public Locator errorMessage() {
        return page.locator(".error");
    }
}
