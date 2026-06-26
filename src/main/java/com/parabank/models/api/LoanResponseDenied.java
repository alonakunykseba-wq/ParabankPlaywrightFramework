package com.parabank.models.api;

import lombok.Data;

@Data
public class LoanResponseDenied {
    private String responseDate;
    private String loanProviderName;
    private boolean approved;
    private String message;
    private Integer accountId;
}
