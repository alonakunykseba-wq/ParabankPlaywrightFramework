package com.parabank.apiservices;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.parabank.models.api.AccountDetailsResponse;
import com.parabank.models.api.BillPayload;
import com.parabank.utils.api.HeaderUtil;
import com.parabank.utils.ConfigurationManager;
import com.parabank.utils.api.JacksonUtil;
import com.parabank.utils.DataGenerator;

public class AccountApiService {
    private final APIRequestContext request;

    public AccountApiService(APIRequestContext request) {
        this.request = request;
    }

    public APIResponse getAccountDetailsWithSession(int accountId) {
        return request.get(ConfigurationManager.getProperty("baseApiUrl") + "/accounts/" + accountId,
                HeaderUtil.withDefaultHeaders());
    }

    public APIResponse getAccountDetails(int accountId) {
        return request.get("/accounts/" + accountId,
                HeaderUtil.withDefaultHeaders());
    }

    public APIResponse payBillWithSession(int accountId, double amount){
        BillPayload billPayload = DataGenerator.generateBillPayload(accountId);
        return request.post(ConfigurationManager.getProperty("baseApiUrl") +"/billpay",
                HeaderUtil.withDefaultHeaders()
                        .setQueryParam("accountId",accountId)
                        .setQueryParam("amount", String.valueOf(amount))
                        .setData(billPayload));
    }

    public AccountDetailsResponse deserializeResponse(APIResponse response) throws JsonProcessingException {
        return JacksonUtil
                .getMapper()
                .readValue(response.text(), AccountDetailsResponse.class);
    }

    public APIResponse postTransferWithSession(int fromAccountId, int toAccountId, double amount){
        return request.post(ConfigurationManager.getProperty("baseApiUrl") + "/transfer",
                HeaderUtil.withDefaultHeaders()
                    .setQueryParam("fromAccountId", fromAccountId)
                    .setQueryParam("toAccountId", toAccountId)
                    .setQueryParam("amount", String.valueOf(amount)));
    }
}

