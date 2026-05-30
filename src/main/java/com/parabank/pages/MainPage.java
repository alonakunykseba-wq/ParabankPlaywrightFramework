package com.parabank.pages;


import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class MainPage {
    Page page;
    private final Locator registerLink;

    public MainPage(Page page) {
        this.page = page;
        this.registerLink =page.getByText("Register");
    }

    public RegistrationPage openRegistrationForm(){
        registerLink.click();
        return new RegistrationPage(page);
    }

}
