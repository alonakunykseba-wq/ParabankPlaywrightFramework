package com.parabank.utils.api;

import com.microsoft.playwright.options.RequestOptions;

public class HeaderUtil {

    public static RequestOptions withDefaultHeaders(){
            return RequestOptions.create().setHeader("Accept", "application/json");
    }

}
