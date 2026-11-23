package com.parabank.models.api;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class LoanRequest {
    private int customerId;
    private double amount;
    private double downPayment;
    private int fromAccountId;
}
