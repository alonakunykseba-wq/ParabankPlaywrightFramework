package com.parabank.models.api;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BillPayload {
    private String name;
    private Address address;
    private String phoneNumber;
    private int accountNumber;
}
