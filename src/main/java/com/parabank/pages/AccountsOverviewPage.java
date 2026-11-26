package com.parabank.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.parabank.utils.LocatorUtil;

import java.util.HashMap;
import java.util.Map;

import static com.microsoft.playwright.options.AriaRole.*;

public class AccountsOverviewPage extends BasePage{


    public AccountsOverviewPage(Page page) {
        super(page);
    }

    public Locator accountNumberLocator(int accountNumber){
        return page.getByRole(LINK, LocatorUtil.name(String.valueOf(accountNumber)));
    }

    public Locator balanceLocator(int accountNumber){
        return page.locator("tr")
                .filter(new Locator.FilterOptions().setHasText(String.valueOf(accountNumber)))
                .locator("td").nth(1);
    }

    public Locator defaultAccountIdLocator(){
        return page.locator("#accountTable").getByRole(LINK).first();
    }

    public int getDefaultAccountId(){
       return Integer.parseInt(safeGetText(defaultAccountIdLocator()));
    }

    public double getAccountBalanceForAccount(int accountNumber) {
        Map <Integer,Double> accountsMap = new HashMap<>();
        Locator rows = page.locator("#accountTable tbody tr");
        rows.first().waitFor();
        for (Locator row : rows.all()) {
            try {
                String accountId = row.locator("td").nth(0).textContent().trim();
                String accountBalance = row.locator("td").nth(1).textContent().replace("$", "").trim();
                accountsMap.put(Integer.parseInt(accountId), Double.parseDouble(accountBalance));
            } catch (NumberFormatException e) {
                System.out.println("The total balance is not a number");
            }
        }
        return accountsMap.get(accountNumber);
    }
}
