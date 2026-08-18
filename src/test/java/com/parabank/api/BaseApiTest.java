package com.parabank.api;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;

import com.parabank.setup.PlaywrightFactory;
import com.parabank.utils.ConfigurationManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseApiTest {
    protected APIRequestContext request;


    @BeforeMethod
    public void setupApi() {
        request = PlaywrightFactory.getPlaywright().request().newContext(new APIRequest.NewContextOptions()
                .setBaseURL(ConfigurationManager.getProperty("baseApiUrl")));
    }

    @AfterMethod
    public void tearDownApi() {
        request.dispose();
    }
}
