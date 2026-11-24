package com.parabank.pages;

import com.microsoft.playwright.Page;

public abstract class BasePage {
    protected final Page page;

    BasePage(Page page){
        this.page = page;
    }
}
