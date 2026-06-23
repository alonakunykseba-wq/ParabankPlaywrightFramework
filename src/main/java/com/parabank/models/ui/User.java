package com.parabank.models.ui;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private String firstName;
    private String lastName;
    @JsonProperty("street")
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String phoneNumber;
    private String ssn;
    private String username;
    private String password;
}
