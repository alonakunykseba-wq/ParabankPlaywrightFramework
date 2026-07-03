package com.parabank.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.parabank.models.ui.User;

public class BaseProfileInfoPage {
    protected final Page page;
    private final Locator firstNameField;
    private final Locator lastNameField;
    private final Locator addressField;
    private final Locator cityField;
    private final Locator stateField;
    private final Locator zipCodeField;
    private final Locator phoneNumberField;

    public BaseProfileInfoPage(Page page) {
        this.page = page;
        this.firstNameField = page.locator("#customer\\.firstName");
        this.lastNameField = page.locator("#customer\\.lastName");
        this.addressField = page.locator("#customer\\.address\\.street");
        this.cityField = page.locator("#customer\\.address\\.city");
        this.stateField = page.locator("#customer\\.address\\.state");
        this.zipCodeField = page.locator("#customer\\.address\\.zipCode");
        this.phoneNumberField = page.locator("#customer\\.phoneNumber");
    }

    public void fillPersonalData(User user) {
        firstNameField.fill(user.getFirstName());
        lastNameField.fill(user.getLastName());
        addressField.fill(user.getAddress());
        cityField.fill(user.getCity());
        stateField.fill(user.getState());
        zipCodeField.fill(user.getZipCode());
        phoneNumberField.fill(user.getPhoneNumber());
    }
}
