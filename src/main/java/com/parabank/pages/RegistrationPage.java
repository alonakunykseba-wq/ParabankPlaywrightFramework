package com.parabank.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.parabank.models.ui.User;

public class RegistrationPage extends BaseProfileInfoPage {

    private final Locator ssnField;
    private final Locator usernameField;
    private final Locator passwordField;
    private final Locator confirmField;
    private final Locator registerButton;

    public RegistrationPage(Page page) {
        super(page);
        this.ssnField = page.locator("#customer\\.ssn");
        this.usernameField = page.locator("#customer\\.username");
        this.passwordField = page.locator("#customer\\.password");
        this.confirmField = page.locator("#repeatedPassword");
        this.registerButton = page.locator("[value='Register']");
    }

    private void fillFormAndSubmit(User user) {
        fillPersonalData(user);
        safeFill(ssnField,user.getSsn());
        safeFill(usernameField,user.getUsername());
        safeFill(passwordField,user.getPassword());
        safeFill(confirmField,user.getPassword());
        safeClick(registerButton);
        page.waitForLoadState(LoadState.NETWORKIDLE);
    }

    public MainPage registerNewUserWithSuccess(User user) {
        fillFormAndSubmit(user);
        page.waitForLoadState(LoadState.NETWORKIDLE);
        return new MainPage(page);
    }

    public RegistrationPage registerNewUserWithFailure(User user) {
        fillFormAndSubmit(user);
        return this;
    }

    public Locator signUpMessageLocator() {
        return page.locator("h1.title");
    }

}
