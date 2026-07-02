package com.parabank.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class UpdateProfileSuccessPage {
    private final Page page;
    private final Locator title;

    public UpdateProfileSuccessPage(Page page){
        this.page = page;
        this.title = page.locator("#updateProfileResult .title");
    }

    public String getUpdateProfileResultTitle(){
        title.waitFor();
        return title.textContent();
    }
}
