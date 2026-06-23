package com.parabank.api;

import com.microsoft.playwright.APIResponse;
import com.parabank.apiservices.AccountApiService;
import io.qameta.allure.Description;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class AccountManagementApiTest extends BaseApiTest {
    @Test(description = "TC-08: Requesting details for a non-existent account ID should return a 404 error")
    @Description("""
             Verifies the Account Details API endpoint handles invalid account IDs correctly.
             Expected Result: The API returns a 404  status code instead of crashing or returning empty data.
            """)
    public void requestForNonExistentAccountShouldReturnNotFoundError() {
        AccountApiService accountApi = new AccountApiService(request);
        APIResponse response = accountApi.getAccountDetails(1000);
        assertEquals(response.status(), 404, "The status code is not as expected");
        assertTrue(response.text().contains("Status 404 – Not Found"), "Response text mismatch:" +response.text());
    }
}
