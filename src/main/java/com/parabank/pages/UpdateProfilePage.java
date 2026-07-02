package com.parabank.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.parabank.models.ui.User;

public class UpdateProfilePage extends BaseRegistrationPage{
    private final Locator updateProfileButton;

    public UpdateProfilePage(Page page){
        super(page);
        this.updateProfileButton = page.locator("[value='Update Profile']");
    }

    public UpdateProfilePage updateDataProfile(User user){
        fillPersonalData(user);
        updateProfileButton.click();
        return this;
    }

}
