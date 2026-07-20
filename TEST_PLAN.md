# Test Automation Strategy & Plan

## Overview

This document outlines the 22 core test cases implemented in this hybrid automation framework. The strategy utilizes the
**Test Automation Pyramid**, relying heavily on fast, isolated API tests while reserving UI and Hybrid tests for
critical end-to-end user journeys.

---

## Risk-Based Test Plan

### Likelihood Scale:
| Score | Label | Meaning |
| :---: | :--- | :--- |
| 1 | Rare | Unlikely to occur. Stable code, no recent changes, proven technology |
| 2 | Unlikely | Could occur but probably will not. Some complexity but well-tested |
| 3 | Possible | Might occur. Moderate complexity, some changes, typical integration |
| 4 | Likely | Probably will occur. New code, complex logic, new dependencies |
| 5 | Almost Certain | Will occur. Untested paths, known fragile areas, experimental features |

### Impact Scale:
| Score | Label | Meaning |
| :---: | :--- | :--- |
| 1 | Negligible | Minor inconvenience. Cosmetic issues, rarely used features |
| 2 | Minor | Some users affected. Workarounds exist. No data loss |
| 3 | Moderate | Significant functionality affected. Many users impacted. Manual workarounds possible |
| 4 | Major | Core functionality broken. Business operations affected. Customer complaints |
| 5 | Critical | Revenue loss, data corruption, regulatory violation, security breach, public failure |

### Risk Category:
| Risk Category | Score Range | Testing Approach |
| :--- | :---: | :--- |
| Critical | 20-25 | Comprehensive testing. All scenarios, edge cases, negative tests. Multiple test types. |
| High | 12-19 | Thorough testing. Main scenarios plus key edge cases. Strong regression coverage. Run on every PR / Smoke test. |
| Medium | 6-11 | Standard testing. Core functionality, run in nightly regressions |
| Low | 1-5 | Basic testing manually or occasionally. |

### Test Matrix:
| Test ID | Scenario Description | Identified Risks | Probability (1-5) | Impact (1-5) | Total Risk Score (P×I) | Mitigation Action |
| :--- | :--- | :--- | :---: | :---: | :---: | :--- |
| TC-01 | userWithAllRequiredFieldsShouldBeRegistered | User registration fails | 3 | 5 | 15 (High) | run with every PR, smoke testing scope. |
| TC-02 | registrationWithMissingFieldsShouldDisplayValidationErrors | User with empty mandatory fields can register | 2 | 5 | 10 (Medium) | part of a weekly regression. Data-Driven Test: testing each missing mandatory field independently. |
| TC-03 | userWithValidCredentialsShouldBeAbleToLogin | User with valid credentials cannot log in to their account | 3 | 5 | 15 (High) | run with every PR, smoke testing scope. |
| TC-04 | loginWithInvalidCredentialsShouldDisplayErrorMessage | Users don’t have error message displayed | 3 | 3 | 9 (Medium) | part of a weekly regression. Data-Driven Test covering invalid username, wrong password, empty fields, SQL injection. |
| TC-05 | newCheckingAccountShouldBeSuccessfullyOpened | Account creation fails (e.g. unable to select/submit checking account). | 2 | 5 | 10 (Medium) | Part of weekly regression. |
| TC-06 | newSavingsAccountShouldAppearInOverviewWithCorrectBalance | Incorrect balance can be displayed | 2 | 5 | 10 (Medium) | Part of a weekly regression |
| TC-07 | accountDetailsSchemaShouldMatchExpectations | Schema of response for account details request can differ from specification | 3 | 4 | 12 (High) | run with every PR, smoke testing scope. |
| TC-08 | requestForNonExistentAccountShouldReturnNotFoundError | Error can have different code or text (e.g. 500 error instead of 404). | 3 | 3 | 9 (Medium) | Part of a weekly regression |
| TC-09 | fundsTransferBetweenAccountsShouldBeSuccessful | Transfer fails | 3 | 4 | 12 (High) | run with every PR, smoke testing scope. |
| TC-10 | billPaymentViaApiShouldDeductCorrectAmountFromUiBalance | Inaccurate calculation may happen | 3 | 3 | 9 (Medium) | Part of a weekly regression |
| TC-11 | negativeTransferAmountShouldBeRejectedWithBadRequest | Transfer may be successful. Error can have different code or text | 2 | 3 | 6 (Medium) | Part of a weekly regression |
| TC-12 | apiDepositShouldCorrectlyIncreaseAccountBalance | Inaccurate calculation may happen | 3 | 3 | 9 (Medium) | Part of a weekly regression |
| TC-13 | loanRequestWithinAvailableFundsShouldBeApproved | Incorrect status of request may happen | 3 | 4 | 12 (High) | run with every PR, smoke testing scope. |
| TC-14 | loanRequestBeyondAvailableFundsShouldBeDenied | The loan request may be approved when it shouldn’t be approved. | 3 | 5 | 15 (High) | run with every PR, smoke testing scope. Implement Data-Driven Test with several limit options. |
| TC-15 | customerContactInfoUpdateShouldBeSuccessful | Updating customer info may fail. | 2 | 3 | 6 (Medium) | Part of a weekly regression |
| TC-16 | profileUpdateViaUiShouldPersistInBackendDatabase | Backend Database is not updated | 2 | 3 | 6 (Medium) | Part of a weekly regression |
| TC-17 | transactionSearchByIdShouldFindMatchingRecord | Transaction cannot be found despite correct ID is provided. | 2 | 3 | 6 (Medium) | Part of a weekly regression |
| TC-18 | filteringTransactionsByAmountShouldReturnMatchingRecords | The amount value of returned transactions doesn’t match the filter (lowest amount is lower or the largest amount is larger than filter inputs) | 2 | 2 | 4 (Low) | Testing occasionally |
| TC-19 | filteringTransactionsByMonthAndTypeShouldReturnMatchingRecords | Only one filter condition works (amount or type) | 2 | 2 | 4 (Low) | Testing occasionally |
| TC-20 | filteringTransactionsByDateRangeShouldReturnMatchingRecords | The date of returned transactions doesn’t match the filter (oldest transaction is older or the newest transaction is newer than filter inputs) | 2 | 2 | 4 (Low) | Testing occasionally |
| TC-21 | filteringTransactionsBySpecificDateShouldReturnMatchingRecords | The date of returned transaction doesn’t math the filter inpout. | 2 | 2 | 4 (Low) | Testing occasionally |
| TC-22 | filteringTransactionsByInvalidDateRangeShouldReturnEmptyList | No protection from providing invalid range | 2 | 1 | 2 | 4 (Low) | Testing occasionally |

---

## Module 1: Authentication & Registration

* **Test 1 (UI):** "userWithAllRequiredFieldsShouldBeRegistered"
    * **Goal:** To confirm that a user can successfully register via the UI form and is immediately logged in.
* **Test 2 (UI DataProvider):** "registrationWithMissingFieldsShouldDisplayValidationErrors"
    * **Goal:** To prove the frontend validation works correctly by attempting to register with missing required fields
      provided by an external JSON/Excel file.

## Module 2: Login

* **Test 3 (UI):** "userWithValidCredentialsShouldBeAbleToLogin"
    * **Goal:** To verify that an existing user can log in with valid credentials and access the accounts overview.
* **Test 4 (UI):** "loginWithInvalidCredentialsShouldFail"
    * **Goal:** To verify that attempting to log in with an incorrect username or password displays the appropriate
      error message.

## Module 3: Account Management

* **Test 5 (UI):** "newCheckingAccountShouldBeSuccessfullyOpened"
    * **Goal:** To verify the UI successfully creates a checking account and displays the new Account ID on the success
      screen.
* **Test 6 (UI):** "newSavingsAccountShouldAppearInOverviewWithCorrectBalance"
    * **Goal:** To ensure that once a new savings account is created, the UI table in the "Accounts Overview" page
      correctly updates to show the new account and balance.
* **Test 7 (Hybrid):** "accountDetailsSchemaShouldMatchExpectations"
    * **Goal:** Via UI create a new user and retrieve default account ID, via API -verify account details for a specific account ID, and assert
      the JSON schema matches expectations.
* **Test 8 (API - Negative):** "requestForNonExistentAccountShouldReturnNotFoundError"
    * **Goal:** GET account details with a non-existing account ID and assert a "404" response is returned.

## Module 4: Money Movement (Transfers & Bill Pay)

* **Test 9 (UI):** "fundsTransferBetweenAccountsShouldBeSuccessful"
    * **Goal:** To confirm the UI dropdowns and transfer buttons work correctly to move money from Checking to Savings. Check success message.
    * Preconditions: register a new user and create a saving account.
* **Test 10 (Hybrid - The Ultimate Test):** "billPaymentViaApiShouldDeductCorrectAmountFromUiBalance"
    * **Goal:** To prove that an action performed strictly in the API (paying a bill) is reflected in UI.
    * Pre-conditions: create a new user and fetch the default account number (A).
    * "API GET" : Check balance of account A.
    * "API POST": Pay a bill from Account A.
    * "UI": Check account A balance: it is exactly bill amount less.
* **Test 11 (API - Negative):** "negativeTransferAmountShouldBeRejectedWithBadRequest"
    * **Goal:** Attempt to transfer a negative amount via POST and assert it returns a "400 Bad Request".
* **Test 12 (Hybrid):** "apiDepositShouldCorrectlyIncreaseAccountBalance"
  * **Goal:** Make a deposit to an account and verify the balance increases appropriately.
  * Pre-conditions: create a new user and fetch the default account number (A). Assert that balance is 515.
  * "API POST": make deposit to the default account number. Assert response text.
  * "API GET":  check default account balance: the balance is the deposit amount more.

## Module 5: Loan Processing

* **Test 13 (Hybrid):** "loanRequestWithinAvailableFundsShouldBeApproved"
    * **Goal:** Via UI - Apply for a loan within available funds, assert the UI displays an Approved status and retrieve loan account id.
    * Via API -verify that account type is LOAN and balance is equal to request loan amount.
* **Test 14 (API):** "loanRequestBeyondAvailableFundsShouldBeDenied"
    * **Goal:** Apply for a massive loan via API and assert the backend logic enforces a Denied
      status.

## Module 6: Customer Profile Management

* **Test 15 (UI):** "customerContactInfoUpdateShouldBeSuccessful"
    * **Goal:** Update customer contact info in the UI and verify the success message.
* **Test 16 (Hybrid):** "profileUpdateViaUiShouldPersistInBackendDatabase"
    * **Goal:** Update customer info via UI -> Call API to fetch customer info -> Assert the API returns the updated
      data.

## Module 7: Transactions Filtering

* **Test 17 (Hybrid):** "transactionSearchByIdShouldFindMatchingRecord"
    * **Goal:** Prove the UI search accurately finds data matching the database.
    * "API": Check transactions and select a random transaction ID.
    * "UI": Navigate to transactions and search for that specific transaction by the ID retrieved via API.
* **Test 18 (API):** "filteringTransactionsByAmountShouldReturnMatchingRecords"
    * **Goal:** Filter transactions by amount "/accounts/{accountId}/transactions/amount/{amount}"
* **Test 19 (API):** "filteringTransactionsByMonthAndTypeShouldReturnMatchingRecords"
    * **Goal:** Filter transactions by month/type "/accounts/{accountId}/transactions/month/{month}/type/{type}"
* **Test 20 (API):** "filteringTransactionsByDateRangeShouldReturnMatchingRecords"
    * **Goal:** Filter transactions by valid date range "
      /accounts/{accountId}/transactions/fromDate/{fromDate}/toDate/{toDate}"
* **Test 21 (API):** "filteringTransactionsBySpecificDateShouldReturnMatchingRecords"
    * **Goal:** Filter transactions by specific date "/accounts/{accountId}/transactions/onDate/{onDate}"
* **Test 22 (API - Negative):** "filteringTransactionsByInvalidDateRangeShouldReturnEmptyList"
    * **Goal:** Filter transactions by impossible date range (Future date to Past date) and verify appropriate
      error/empty response.
