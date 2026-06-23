package com.parabank.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.parabank.models.ui.User;

public class RegistrationPage {
    private final Page page;

    private final Locator firstNameField;
    private final Locator lastNameField;
    private final Locator addressField;
    private final Locator cityField;
    private final Locator stateField;
    private final Locator zipCodeField;
    private final Locator phoneNumberField;
    private final Locator ssnField;
    private final Locator usernameField;
    private final Locator passwordField;
    private final Locator confirmField;
    private final Locator registerButton;

    public RegistrationPage(Page page) {
        this.page = page;
        this.firstNameField = page.locator("#customer\\.firstName");
        this.lastNameField = page.locator("#customer\\.lastName");
        this.addressField = page.locator("#customer\\.address\\.street");
        this.cityField = page.locator("#customer\\.address\\.city");
        this.stateField = page.locator("#customer\\.address\\.state");
        this.zipCodeField = page.locator("#customer\\.address\\.zipCode");
        this.phoneNumberField = page.locator("#customer\\.phoneNumber");
        this.ssnField = page.locator("#customer\\.ssn");
        this.usernameField = page.locator("#customer\\.username");
        this.passwordField = page.locator("#customer\\.password");
        this.confirmField = page.locator("#repeatedPassword");
        this.registerButton = page.locator("[value='Register']");
    }

    private void fillFormAndSubmit(User user) {
        firstNameField.fill(user.getFirstName());
        lastNameField.fill(user.getLastName());
        addressField.fill(user.getAddress());
        cityField.fill(user.getCity());
        stateField.fill(user.getState());
        zipCodeField.fill(user.getZipCode());
        phoneNumberField.fill(user.getPhoneNumber());
        ssnField.fill(user.getSsn());
        usernameField.fill(user.getUsername());
        passwordField.fill(user.getPassword());
        confirmField.fill(user.getPassword());
        registerButton.click();
    }

    public MainPage registerNewUserWithSuccess(User user) {
        fillFormAndSubmit(user);
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
