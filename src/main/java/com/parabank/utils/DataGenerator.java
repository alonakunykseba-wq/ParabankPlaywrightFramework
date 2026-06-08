package com.parabank.utils;

import com.parabank.models.UI.User;
import net.datafaker.Faker;

public class DataGenerator {

    public static User generateRandomUser(){
        Faker faker = new Faker();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String address = faker.address().streetAddress();
        String city = faker.address().city();
        String state = faker.address().state();
        String zipCode = faker.address().zipCode();
        String phoneNumber = faker.phoneNumber().phoneNumberInternational();
        String ssn = faker.idNumber().ssnValid();
        String username = faker.credentials().username();
        String password = ConfigurationManager.getProperty("defaultTestPassword");

        return User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .address(address)
                .city(city)
                .state(state)
                .zipCode(zipCode)
                .phoneNumber(phoneNumber)
                .ssn(ssn)
                .username(username)
                .password(password)
                .build();
    }
}
