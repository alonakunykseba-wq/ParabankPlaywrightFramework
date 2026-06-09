package com.parabank.API;

import com.microsoft.playwright.APIResponse;
import com.parabank.APIServices.AccountAPIService;
import io.qameta.allure.Description;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class AccountManagementApiTest extends BaseApiTest {
    @Test(description = "TC 08: verifyNonExistentAccountReturnsErrorTest")
    @Description("""
             Verifies the Account Details API endpoint handles invalid account IDs correctly.
             Expected Result: The API returns a 404  status code instead of crashing or returning empty data.
            """)
    public void verifyNonExistentAccountReturnsErrorTest() {
        AccountAPIService accountApi = new AccountAPIService(request);
        APIResponse response = accountApi.getAccountDetails(1000);
        assertEquals(response.status(), 404, "The status code is not as expected");
    }
}
