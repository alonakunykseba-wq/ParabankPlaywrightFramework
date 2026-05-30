package com.parabank.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.parabank.models.User;

public class RegistrationPage {
    private final Page page;

    private final Locator firstName;
    private final Locator lastName;
    private final Locator address;
    private final Locator city;
    private final Locator state;
    private final Locator zipCode;
    private final Locator phoneNumber;
    private final Locator ssn;
    private final Locator username;
    private final Locator password;
    private final Locator confirm;
    private final Locator registerButton;

    public RegistrationPage(Page page) {
        this.page = page;
        this.firstName =page.locator("input[name='customer.firstName']") ;
        this.lastName = page.locator("input[name='customer.lastName']");
        this.address = page.locator("input[name='customer.address.street']");
        this.city = page.locator("input[name='customer.address.city']");
        this.state = page.locator("input[name='customer.address.state']");
        this.zipCode = page.locator("input[name='customer.address.zipCode']");
        this.phoneNumber = page.locator("input[name='customer.phoneNumber']");
        this.ssn = page.locator("input[name='customer.ssn']");
        this.username =page.locator("input[name='customer.username']");
        this.password = page.locator("input[name='customer.password']");
        this.confirm =page.locator("input[name='repeatedPassword']");
        this.registerButton = page.locator("input[value='Register']");
    }

    public void registerNewUser(User user){
        firstName.fill(user.getFirstName());
        lastName.fill(user.getLastName());
        address.fill(user.getAddress());
        city.fill(user.getCity());
        state.fill(user.getState());
        zipCode.fill(user.getZipCode());
        phoneNumber.fill(user.getPhoneNumber());
        ssn.fill(user.getSsn());
        username.fill(user.getUsername());
        password.fill(user.getPassword());
        confirm.fill(user.getPassword());
        registerButton.click();
    }

    public Locator registrationConfirmation() {
        return page.locator("h1.title");
    }
}
