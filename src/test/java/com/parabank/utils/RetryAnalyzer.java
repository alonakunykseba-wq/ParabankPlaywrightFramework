package com.parabank.utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer  implements IRetryAnalyzer {
        private int count = 0;
        private static final int MAX_RETRY_LIMIT = 2;

        @Override
        public boolean retry(ITestResult result) {
            if (count < MAX_RETRY_LIMIT) {
                count++;
                System.out.println("[INFO] Test '" + result.getName() + "' failed. Retrying... (Attempt " + count + " of " + MAX_RETRY_LIMIT + ")");
                return true;
            }
            return false;
        }
    }

