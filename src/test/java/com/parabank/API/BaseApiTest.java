package com.parabank.API;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Playwright;

import com.parabank.utils.ConfigurationManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

    public class BaseApiTest {
        protected Playwright playwright;
        protected APIRequestContext request;

        @BeforeMethod
        public void setupApi() {
            playwright = Playwright.create();
            request = playwright.request().newContext(new APIRequest.NewContextOptions()
                    .setBaseURL(ConfigurationManager.getProperty("baseApiUrl")));
        }
        @AfterMethod
        public void tearDownApi() {
            request.dispose();
            playwright.close();
        }
    }
