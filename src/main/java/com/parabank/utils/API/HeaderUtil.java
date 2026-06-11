package com.parabank.utils.API;

import com.microsoft.playwright.options.RequestOptions;

public class HeaderUtil {

    public static RequestOptions setDefaultHeader(){
            return RequestOptions.create().setHeader("Accept", "application/json");
    }

}
