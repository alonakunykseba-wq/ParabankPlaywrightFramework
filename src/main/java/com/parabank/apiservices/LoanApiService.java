package com.parabank.apiservices;

import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.parabank.models.api.LoanRequest;
import com.parabank.utils.ConfigurationManager;
import com.parabank.utils.api.HeaderUtil;

public class LoanApiService {
    private final APIRequestContext request;

    public LoanApiService(APIRequestContext request) {
        this.request = request;
    }

    public APIResponse postLoanRequestWithSession(LoanRequest loanRequest) {
        return request.post(ConfigurationManager.getProperty("baseApiUrl") + "/requestLoan",
                HeaderUtil.withDefaultHeaders()
                        .setQueryParam("customerId", String.valueOf(loanRequest.getCustomerId()))
                        .setQueryParam("amount", String.valueOf(loanRequest.getAmount()))
                        .setQueryParam("downPayment", String.valueOf(loanRequest.getDownPayment()))
                        .setQueryParam("fromAccountId", String.valueOf(loanRequest.getFromAccountId())));
    }
}
