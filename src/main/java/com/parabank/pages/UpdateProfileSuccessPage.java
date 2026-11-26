package com.parabank.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class UpdateProfileSuccessPage extends BasePage {
    private final Locator title;

    public UpdateProfileSuccessPage(Page page) {
        super(page);
        this.title = page.locator("#updateProfileResult .title");
    }

    public String getUpdateProfileResultTitle() {
        return safeGetText(title);
    }
}
