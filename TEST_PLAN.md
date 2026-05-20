# Test Automation Strategy & Plan

## Overview
This document outlines the 20 core test cases implemented in this hybrid automation framework. The strategy utilizes the **Test Automation Pyramid**, relying heavily on fast, isolated API tests while reserving UI and Hybrid tests for critical end-to-end user journeys.

---

## Module 1: Authentication & Registration
*   **Test 1 (UI):** Register a new user with valid data.
*   **Test 2 (UI DataProvider):** Attempt registration with invalid/missing data (driven by an external JSON/Excel file).

## Module 2: Account Management
*   **Test 3 (UI):** Open a new Checking account and verify the success message.
*   **Test 4 (UI):** Open a new Savings account and verify UI updates in the Account Overview.
*   **Test 5 (API):** GET customer accounts, choose any account, verify account details for a specific account ID, and assert the JSON schema.
*   **Test 6 (API - Negative):** GET account details with a non-existing account ID and assert a `400` response is returned.

## Module 3: Money Movement (Transfers & Bill Pay)
*   **Test 7 (UI):** Transfer funds from Checking to Savings.
*   **Test 8 (Hybrid - The Ultimate Test):** 
    *   `API GET`: Fetch the balance of Account A.
    *   `UI Page`: Pay a bill of $50 from Account A.
    *   `API GET`: Fetch Account A again and assert the backend balance is exactly $50 less.
*   **Test 9 (API - Negative):** Attempt to transfer a negative amount via POST and assert it returns a `400 Bad Request`.
*   **Test 10 (API):** POST a deposit to an account and verify the balance increases.

## Module 4: Loan Processing
*   **Test 11 (UI):** Apply for a loan with a high down payment (Expected: Approved).
*   **Test 12 (API):** Apply for a massive loan with a $0 down payment (Expected: Denied).

## Module 5: Customer Profile Management
*   **Test 13 (UI):** Update customer contact info and save.
*   **Test 14 (Hybrid):** Update customer info via UI -> Call API to fetch customer info -> Assert the API returns the updated data.

## Module 6: Transactions Filtering
*   **Test 15 (Hybrid):** 
    *   `API`: Check transactions and select a random transaction ID.
    *   `UI`: Navigate to transactions and search for that specific transaction by the ID retrieved via API.
*   **Test 16 (API):** Filter transactions by amount `/accounts/{accountId}/transactions/amount/{amount}`
*   **Test 17 (API):** Filter transactions by month/type `/accounts/{accountId}/transactions/month/{month}/type/{type}`
*   **Test 18 (API):** Filter transactions by valid date range `/accounts/{accountId}/transactions/fromDate/{fromDate}/toDate/{toDate}`
*   **Test 19 (API):** Filter transactions by specific date `/accounts/{accountId}/transactions/onDate/{onDate}`
*   **Test 20 (API - Negative):** Filter transactions by impossible date range (Future date to Past date) and verify appropriate error/empty response.
