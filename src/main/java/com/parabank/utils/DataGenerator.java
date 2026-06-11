package com.parabank.utils;

import com.parabank.models.API.Address;
import com.parabank.models.API.BillPayload;
import com.parabank.models.UI.User;
import net.datafaker.Faker;

public class DataGenerator {
    static Faker faker = new Faker();

    public static User generateRandomUser(){
        Address randomAddress = generateRandomAddress();
        return User.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .address(randomAddress.getStreet())
                .city(randomAddress.getCity())
                .state(randomAddress.getState())
                .zipCode(randomAddress.getZipCode())
                .phoneNumber(faker.phoneNumber().phoneNumberInternational())
                .ssn(faker.idNumber().ssnValid())
                .username(faker.credentials().username())
                .password(ConfigurationManager.getProperty("defaultTestPassword"))
                .build();
    }
    public static Address generateRandomAddress(){
        return Address.builder()
                .street(faker.address().streetAddress())
                .city(faker.address().city())
                .state(faker.address().state())
                .zipCode(faker.address().zipCode())
                .build();
    }

    public static BillPayload generateBillPayload(int accountId){
        return BillPayload.builder()
                .name(faker.name().name())
                .phoneNumber(faker.phoneNumber().phoneNumberInternational())
                .address(generateRandomAddress())
                .accountNumber(accountId)
                .build();
    }
}
