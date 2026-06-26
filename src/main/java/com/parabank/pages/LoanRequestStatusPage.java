package com.parabank.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.parabank.utils.LocatorUtil;

import static com.microsoft.playwright.options.AriaRole.*;

public class LoanRequestStatusPage extends BaseConfirmationPage{


    public LoanRequestStatusPage(Page page){
        super(page);
    }

    public Locator successHeadingLocator() {
        return page.getByRole(HEADING, LocatorUtil.name("Loan Request Processed"));
    }

    public Locator loanRequestStatusLocator(){
        return page.locator("tr")
                .filter(new Locator.FilterOptions().setHasText("Status"))
                .locator("td").nth(1);
    }

    public String getLoanRequestStatus(){
        loanRequestStatusLocator().waitFor();
        return loanRequestStatusLocator().textContent();
    }
}
