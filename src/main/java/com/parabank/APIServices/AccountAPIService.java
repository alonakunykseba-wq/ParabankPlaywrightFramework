package com.parabank.APIServices;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import com.parabank.models.API.AccountDetailsResponse;
import com.parabank.models.API.BillPayload;
import com.parabank.utils.API.HeaderUtil;
import com.parabank.utils.ConfigurationManager;
import com.parabank.utils.API.JacksonUtil;
import com.parabank.utils.DataGenerator;

public class AccountAPIService {
    private final APIRequestContext request;

    public AccountAPIService(APIRequestContext request) {
        this.request = request;
    }

    public APIResponse getAccountDetailsViaUi(int accountId) {
        return request.get(ConfigurationManager.getProperty("baseApiUrl") + "/accounts/" + accountId,
                HeaderUtil.setDefaultHeader());
    }

    public APIResponse getAccountDetails(int accountId) {
        return request.get("/accounts/" + accountId,
                HeaderUtil.setDefaultHeader());
    }

    public APIResponse payBillViaUi(int accountId, double amount){
        BillPayload billPayload = DataGenerator.generateBillPayload(accountId);
        return request.post(ConfigurationManager.getProperty("baseApiUrl") +"/billpay",
                HeaderUtil.setDefaultHeader()
                        .setQueryParam("accountId",accountId)
                        .setQueryParam("amount", String.valueOf(amount))
                        .setData(billPayload));
    }

    public AccountDetailsResponse deserializeResponse(APIResponse response) throws JsonProcessingException {
        return JacksonUtil
                .getMapper()
                .readValue(response.text(), AccountDetailsResponse.class);
    }
}

