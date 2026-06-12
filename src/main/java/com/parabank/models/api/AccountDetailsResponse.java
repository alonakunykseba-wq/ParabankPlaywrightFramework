package com.parabank.models.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class AccountDetailsResponse {
    private int id;
    private int customerId;
    private String type;
    private double balance;

}


