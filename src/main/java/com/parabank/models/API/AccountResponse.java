package com.parabank.models.API;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class AccountResponse {
    private int id;
    private int customerId;
    private String type;
    private double balance;
}
