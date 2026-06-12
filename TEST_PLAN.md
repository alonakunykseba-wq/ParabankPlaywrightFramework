# Test Automation Strategy & Plan

## Overview

This document outlines the 22 core test cases implemented in this hybrid automation framework. The strategy utilizes the
**Test Automation Pyramid**, relying heavily on fast, isolated API tests while reserving UI and Hybrid tests for
critical end-to-end user journeys.

---

## Module 1: Authentication & Registration

* **Test 1 (UI):** "verifyValidUserRegistrationTest"
    * **Goal:** To confirm that a user can successfully register via the UI form and is immediately logged in.
* **Test 2 (UI DataProvider):** "verifyInvalidRegistrationValidationsTest"
    * **Goal:** To prove the frontend validation works correctly by attempting to register with missing required fields
      provided by an external JSON/Excel file.

## Module 2: Login

* **Test 3 (UI):** "verifyValidUserLoginTest"
    * **Goal:** To verify that an existing user can log in with valid credentials and access the accounts overview.
* **Test 4 (UI):** "verifyInvalidLoginCredentialsTest"
    * **Goal:** To verify that attempting to log in with an incorrect username or password displays the appropriate
      error message.

## Module 3: Account Management

* **Test 5 (UI):** "verifyOpenNewCheckingAccountTest"
    * **Goal:** To verify the UI successfully creates a checking account and displays the new Account ID on the success
      screen.
* **Test 6 (UI):** "verifyNewSavingsAccountAppearsInOverviewTest"
    * **Goal:** To ensure that once a new savings account is created, the UI table in the "Accounts Overview" page
      correctly updates to show the new account and balance.
* **Test 7 (Hybrid):** "verifyAccountDetailsSchemaTest"
    * **Goal:** GET customer accounts, choose any account, verify account details for a specific account ID, and assert
      the JSON schema matches expectations.
* **Test 8 (API - Negative):** "verifyNonExistentAccountReturnsErrorTest"
    * **Goal:** GET account details with a non-existing account ID and assert a "404" response is returned.

## Module 4: Money Movement (Transfers & Bill Pay)

* **Test 9 (UI):** "verifyTransferFundsBetweenAccountsTest"
    * **Goal:** To confirm the UI dropdowns and transfer buttons work correctly to move money from Checking to Savings. Check success message.
    * Preconditions: register a new user and create a saving account.
* **Test 10 (Hybrid - The Ultimate Test):** "verifyBillPaymentDeductsCorrectAmountHybridTest"
    * **Goal:** To prove that an action performed strictly in the API (paying a bill) is reflected in UI.
    * Pre-conditions: create a new user and fetch the default account number (A).
    * "API GET" : Check balance of account A.
    * "API POST": Pay a bill from Account A.
    * "UI": Check account A balance: it is exactly bill amount less.
* **Test 11 (API - Negative):** "verifyNegativeTransferAmountIsRejectedTest"
    * **Goal:** Attempt to transfer a negative amount via POST and assert it returns a "400 Bad Request".
* **Test 12 (Hybrid):** "verifyApiDepositIncreasesBalanceTest"
  * **Goal:** Make a deposit to an account and verify the balance increases appropriately.
  * Pre-conditions: create a new user and fetch the default account number (A). Assert that balance is 515.
  * "API POST": make deposit to the default account number. Assert response text.
  * "API GET":  check default account balance: the balance is the deposit amount more.

## Module 5: Loan Processing

* **Test 13 (UI):** "verifyLoanApplicationApprovedTest"
    * **Goal:** Apply for a loan with a high down payment and assert the UI displays an Approved status.
* **Test 14 (API):** "verifyZeroDownPaymentLoanIsDeniedTest"
    * **Goal:** Apply for a massive loan with a $0 down payment via API and assert the backend logic enforces a Denied
      status.

## Module 6: Customer Profile Management

* **Test 15 (UI):** "verifyUpdateCustomerContactInfoTest"
    * **Goal:** Update customer contact info in the UI and verify the success message.
* **Test 16 (Hybrid):** "verifyProfileUpdatePersistsInBackendHybridTest"
    * **Goal:** Update customer info via UI -> Call API to fetch customer info -> Assert the API returns the updated
      data.

## Module 7: Transactions Filtering

* **Test 17 (Hybrid):** "verifyUiTransactionSearchByIdTest"
    * **Goal:** Prove the UI search accurately finds data matching the database.
    * "API": Check transactions and select a random transaction ID.
    * "UI": Navigate to transactions and search for that specific transaction by the ID retrieved via API.
* **Test 18 (API):** "verifyFilterTransactionsByAmountTest"
    * **Goal:** Filter transactions by amount "/accounts/{accountId}/transactions/amount/{amount}"
* **Test 19 (API):** "verifyFilterTransactionsByMonthAndTypeTest"
    * **Goal:** Filter transactions by month/type "/accounts/{accountId}/transactions/month/{month}/type/{type}"
* **Test 20 (API):** "verifyFilterTransactionsByValidDateRangeTest"
    * **Goal:** Filter transactions by valid date range "
      /accounts/{accountId}/transactions/fromDate/{fromDate}/toDate/{toDate}"
* **Test 21 (API):** "verifyFilterTransactionsBySpecificDateTest"
    * **Goal:** Filter transactions by specific date "/accounts/{accountId}/transactions/onDate/{onDate}"
* **Test 22 (API - Negative):** "verifyInvalidDateRangeReturnsEmptyListTest"
    * **Goal:** Filter transactions by impossible date range (Future date to Past date) and verify appropriate
      error/empty response.
