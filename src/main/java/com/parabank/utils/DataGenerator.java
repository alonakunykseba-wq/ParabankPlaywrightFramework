package com.parabank.utils;

import com.parabank.models.api.Address;
import com.parabank.models.api.BillPayload;
import com.parabank.models.ui.User;
import net.datafaker.Faker;

public class DataGenerator {
    private static final ThreadLocal<Faker> fakerThread = ThreadLocal.withInitial(Faker::new);

    private static Faker getFaker() {
        return fakerThread.get();
    }
    public static User generateRandomUser(){
        Address randomAddress = generateRandomAddress();
        return User.builder()
                .firstName(getFaker().name().firstName())
                .lastName(getFaker().name().lastName())
                .address(randomAddress.getStreet())
                .city(randomAddress.getCity())
                .state(randomAddress.getState())
                .zipCode(randomAddress.getZipCode())
                .phoneNumber(getFaker().phoneNumber().phoneNumberInternational())
                .ssn(getFaker().idNumber().ssnValid())
                .username(getFaker().credentials().username())
                .password(ConfigurationManager.getProperty("defaultTestPassword"))
                .build();
    }
    public static Address generateRandomAddress(){
        return Address.builder()
                .street(getFaker().address().streetAddress())
                .city(getFaker().address().city())
                .state(getFaker().address().state())
                .zipCode(getFaker().address().zipCode())
                .build();
    }

    public static BillPayload generateBillPayload(int accountId){
        return BillPayload.builder()
                .name(getFaker().name().name())
                .phoneNumber(getFaker().phoneNumber().phoneNumberInternational())
                .address(generateRandomAddress())
                .accountNumber(accountId)
                .build();
    }
}
