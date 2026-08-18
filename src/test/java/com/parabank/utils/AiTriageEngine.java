package com.parabank.utils;

import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AiTriageEngine {

    public static String analyzeFailure(String stackTrace, String htmlContent) {
        try {
            String systemPrompt = """
    You are an expert Automation QA engineer debugging a Playwright Java test failure.
    Analyze the provided Error Stack Trace and the HTML Page Source.
    
    1. State exactly what went wrong in 1 sentence.
    2. Identify the closest matching element in the HTML source that the test was trying to interact with \
    (for example, if it fails waiting for 'input[name="name"]', find the actual username input in the HTML).
    3. Provide the corrected selector using strictly Playwright Java syntax \
    (e.g., page.locator("input[name='username']")).
    
    Do not suggest the broken selector from the stack trace. Do not use JavaScript or await keywords.
    """;
            String userContent = "### STACK TRACE:\n" + stackTrace + "\n\n### HTML SOURCE:\n" + htmlContent;

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("model", "llama3.2");
            jsonBody.put("prompt", systemPrompt + "\n\nData:\n" + userContent);
            jsonBody.put("stream", false);

            try(HttpClient client = HttpClient.newHttpClient()) {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(ConfigurationManager.getProperty("ollamaApiUrl")))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(jsonBody.toString()))
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() != 200) {
                    return "Failed to communicate with AI engine. HTTP status: " + response.statusCode() + " - "
                            + response.body();
                }
                JSONObject jsonResponse = new JSONObject(response.body());
                return jsonResponse.getString("response");
            }
        } catch (Exception e) {
            return "Failed to communicate with the AI engine: " + e.getMessage();
        }
    }
}
