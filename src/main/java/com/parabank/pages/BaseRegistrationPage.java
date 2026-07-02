package com.parabank.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.parabank.models.ui.User;

public class BaseRegistrationPage {
    protected final Page page;
    protected final Locator firstNameField;
    protected final Locator lastNameField;
    protected final Locator addressField;
    protected final Locator cityField;
    protected final Locator stateField;
    protected final Locator zipCodeField;
    protected final Locator phoneNumberField;

    public BaseRegistrationPage(Page page){
        this.page = page;
        this.firstNameField = page.locator("#customer\\.firstName");
        this.lastNameField = page.locator("#customer\\.lastName");
        this.addressField = page.locator("#customer\\.address\\.street");
        this.cityField = page.locator("#customer\\.address\\.city");
        this.stateField = page.locator("#customer\\.address\\.state");
        this.zipCodeField = page.locator("#customer\\.address\\.zipCode");
        this.phoneNumberField = page.locator("#customer\\.phoneNumber");
    }

  public void fillPersonalData (User user) {
      firstNameField.fill(user.getFirstName());
      lastNameField.fill(user.getLastName());
      addressField.fill(user.getAddress());
      cityField.fill(user.getCity());
      stateField.fill(user.getState());
      zipCodeField.fill(user.getZipCode());
      phoneNumberField.fill(user.getPhoneNumber());
  }
}
