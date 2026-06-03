package com.parabank.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static com.microsoft.playwright.options.WaitForSelectorState.*;

public class OpenAccountSuccessPage {
    private final Page page;
    private final Locator accountNumber;

    public OpenAccountSuccessPage(Page page) {
        this.page = page;
        this.accountNumber = page.locator("a[id='newAccountId']");
    }

    public Locator successHeading() {
        return page.getByText("Account Opened!", new Page.GetByTextOptions().setExact(true));
    }

    public Locator successDescription() {
        return page.getByText("Congratulations, your account is now open.", new Page.GetByTextOptions().setExact(true));
    }

    public String getAccountNumber() {
        assertThat(accountNumber).hasText(Pattern.compile("\\d+"));
        return accountNumber.textContent();
    }
}
