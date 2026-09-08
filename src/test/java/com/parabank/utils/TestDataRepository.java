package com.parabank.utils;
import java.util.List;
import java.util.Map;

public class TestDataRepository {
    private final static List <Map.Entry<String,String>> INVALID_CREDENTIALS =List.of(
            Map.entry("[VALID_USER]", " "),
            Map.entry("[VALID_USER]", "password"),
            Map.entry(" ", "[VALID_PASSWORD]"),
            Map.entry("Abrakadabra", "[VALID_PASSWORD]"),
            Map.entry("' OR '1'='1", "' OR '1'='1"),
            Map.entry("admin' --", "anything"),
            Map.entry("' OR 1=1 --", "password"),
            Map.entry("') OR ('1'='1", "') OR ('1'='1")
    );
    private final static Map<String,Double> TRANSACTIONS_DATA = Map.of(
            "loanAmount", 500.0,
            "billAmount", 15.5,
            "transferNegativeAmount", -15.0,
            "transferAmount", 10.5,
            "depositAmount", 20.5
    );

    private final static double[] LOAN_DATA = {2999.00, 3500.00, 5001.00};

    public static Double getAmount(String transactionName){
        return TRANSACTIONS_DATA.get(transactionName);
    }

    public static Object [][] getInvalidCredentials(){
      Object[][] data = new Object[INVALID_CREDENTIALS.size()][2];
      for(int index =0; index < INVALID_CREDENTIALS.size(); index ++){
            data[index][0] = INVALID_CREDENTIALS.get(index).getKey();
            data[index][1] = INVALID_CREDENTIALS.get(index).getValue();
        }
        return data;
    }

    public static Object [][] getLoanAmount(){
        Object[][] data = new Object[LOAN_DATA.length][1];
        for (int i =0; i< LOAN_DATA.length; i++){
            data[i][0] = LOAN_DATA[i];
        }
        return data;
    }
}
