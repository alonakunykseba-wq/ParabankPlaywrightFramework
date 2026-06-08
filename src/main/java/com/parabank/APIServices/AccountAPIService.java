package com.parabank.APIServices;

import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import com.parabank.utils.ConfigurationManager;

public class AccountAPIService {
    private final APIRequestContext request;

    public AccountAPIService(APIRequestContext request) {
        this.request = request;
    }

    public APIResponse getAccountDetailsViaUi(int accountId) {
        return request.get(ConfigurationManager.getProperty("baseApiUrl") + "/accounts/" + accountId,
                RequestOptions.create().setHeader("Accept", "application/json"));
    }

    public APIResponse getAccountDetails(int accountId) {
        return request.get("/accounts/" + accountId,
                RequestOptions.create().setHeader("Accept", "application/json"));
    }
}

