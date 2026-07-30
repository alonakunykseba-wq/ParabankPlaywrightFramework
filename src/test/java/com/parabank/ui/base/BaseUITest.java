package com.parabank.ui.base;

import com.parabank.setup.PlaywrightFactory;
import com.parabank.utils.AiTriageEngine;
import com.parabank.utils.ConfigurationManager;
import org.json.JSONObject;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.parabank.setup.PlaywrightFactory.getPage;


public class BaseUITest {

    @BeforeMethod
    public void setup() {
        PlaywrightFactory.initBrowser(ConfigurationManager.getProperty("browser"));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        try {
            if (result.getStatus() == ITestResult.FAILURE) {
                try {
                    // Safeguard: Ensure the page is initialized and open before capturing state
                    if (getPage() != null && !getPage().isClosed()) {
                        String htmlContent = getPage().content();
                        JSONObject failurePayload = new JSONObject();
                        failurePayload.put("testName", result.getName());
                        failurePayload.put("errorMessage", result.getThrowable().getMessage());
                        failurePayload.put("htmlSource", htmlContent);
                        Files.createDirectories(Paths.get("./target/triage"));
                        try (FileWriter file = new FileWriter("./target/triage/" + result.getName() + "_failure.json")) {
                            file.write(failurePayload.toString(4));
                        }
                        System.out.println("[AI Analyzer Prep] Exported runtime layout state for failed test: " + result.getName());
                        String aiFeedback = AiTriageEngine.analyzeFailure(result.getThrowable().getMessage(), htmlContent);
                        System.out.println("\n [AI Root Cause Analysis]:\n" + aiFeedback);
                    } else {
                        System.out.println("[AI Analyzer Prep] Page was null or closed. Skipping AI analysis.");
                    }
                } catch (Exception e) {
                    System.err.println("Failed to capture metadata for AI processing: " + e.getMessage());
                }
            }
        } finally {
            PlaywrightFactory.removeThreadLocals();
        }
    }
}
