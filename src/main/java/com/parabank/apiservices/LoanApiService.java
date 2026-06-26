package com.parabank.apiservices;

import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.parabank.utils.ConfigurationManager;
import com.parabank.utils.api.HeaderUtil;

public class LoanApiService {
    private final APIRequestContext request;

    public LoanApiService(APIRequestContext request) {
        this.request = request;
    }

    public record LoanRequest(int customerId, double amount, double downPayment, int fromAccountId) {}

    public APIResponse postLoanRequestWithSession(LoanRequest loanRequest) {
        return request.post(ConfigurationManager.getProperty("baseApiUrl") + "/requestLoan",
                HeaderUtil.withDefaultHeaders()
                        .setQueryParam("customerId", String.valueOf(loanRequest.customerId()))
                        .setQueryParam("amount", String.valueOf(loanRequest.amount()))
                        .setQueryParam("downPayment", String.valueOf(loanRequest.downPayment()))
                        .setQueryParam("fromAccountId", String.valueOf(loanRequest.fromAccountId())));
    }
}
