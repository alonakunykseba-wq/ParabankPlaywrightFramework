package com.parabank.utils.listeners;
import com.microsoft.playwright.Page;
import com.parabank.setup.PlaywrightFactory;
import org.testng.IConfigurationListener;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.nio.file.Paths;

public class TestFailureListener  implements ITestListener, IConfigurationListener {
    private void takeScreenshot(ITestResult result) {
        Page page = PlaywrightFactory.getPage();
        if (page != null) {
            try {
                // Save screenshot with timestamp to avoid overwrites
                String path = "target/screenshots/" + result.getName() + "_" + System.currentTimeMillis() + ".png";
                page.screenshot(new Page.ScreenshotOptions()
                        .setPath(Paths.get(path))
                        .setFullPage(true));
                System.out.println("[INFO] Screenshot captured on failure: " + path);
            } catch (Exception e) {
                System.err.println("[ERROR] Failed to capture screenshot: " + e.getMessage());
            }
        }
    }
    @Override
    public void onTestFailure(ITestResult result) {
        takeScreenshot(result);
    }
    @Override
    public void onConfigurationFailure(ITestResult result) {
        takeScreenshot(result);
    }
}
