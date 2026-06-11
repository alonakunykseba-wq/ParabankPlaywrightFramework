package com.parabank.models.API;

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
