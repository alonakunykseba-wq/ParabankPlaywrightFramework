# Parabank Playwright Hybrid Automation Framework

Welcome to the Parabank Playwright Automation Framework! 
This project is an advanced, enterprise-grade test automation portfolio demonstrating hybrid API and UI testing using modern tools.

## 🚀 Tech Stack
*   **Language:** Java
*   **UI & API Automation:** Playwright (playwright-java)
*   **Test Runner:** TestNG
*   **Build Tool:** Maven
*   **Data Parsing:** Jackson-databind

## 🏗️ Framework Architecture & Features
*   **Hybrid Testing:** Combines fast API setup calls with UI verifications in a single test block.
*   **Parallel Execution:** Implements `ThreadLocal<Page>` and `ThreadLocal<BrowserContext>` to guarantee thread-safe parallel execution of browsers.
*   **Page Object Model (POM):** Clean separation of UI locators from test logic.
*   **Data Driven Testing:** Utilizes TestNG `@DataProvider` combined with JSON/Excel data files.
*   **Test Isolation:** Every test creates a brand new user via the API/UI in the `@BeforeMethod` to guarantee 100% test independence and eliminate data collisions.

## 🛠️ Local Environment Setup
To prevent test data collision on the public internet, this framework runs against a private Docker instance of Parabank.

**1. Start the Server:**
Ensure Docker Desktop is running, then execute the following command in your terminal:
```bash
docker run -p 8080:8080 parasoft/parabank
```

**2. Access the Application:**
*   UI: http://localhost:8080/parabank
*   API Docs (Swagger): http://localhost:8080/parabank/api-docs/index.html

## 📝 Test Scope
Please refer to the [Test Plan](TEST_PLAN.md) for the complete breakdown of the 20 Core Test Cases covering Authentication, Account Management, Money Transfers, Loan Processing, and Transaction Filtering.
