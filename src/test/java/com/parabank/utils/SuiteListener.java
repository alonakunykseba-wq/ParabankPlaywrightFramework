package com.parabank.utils;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import java.time.Duration;

public class SuiteListener implements ISuiteListener {
    private static GenericContainer<?> parabankContainer;

    @Override
    public void onStart(ISuite suite) {
        System.out.println("[INFO] Starting Parabank Docker container via Testcontainers...");

        parabankContainer = new GenericContainer<>("parasoft/parabank:latest")
                .withExposedPorts(8080)
                .waitingFor(Wait.forHttp("/parabank/")
                        .forStatusCode(200)
                        .withStartupTimeout(Duration.ofMinutes(2)));

        parabankContainer.start();
        String host = parabankContainer.getHost();
        Integer port = parabankContainer.getMappedPort(8080);
        String baseUrl = String.format("http://%s:%d/parabank", host, port);
        String baseApiUrl = String.format("http://%s:%d/parabank/services/bank", host, port);

        System.out.println("[INFO] Parabank container started successfully!");
        System.out.println("[INFO] Dynamic Base URL: " + baseUrl);

        ConfigurationManager.setProperty("baseUrl", baseUrl);
        ConfigurationManager.setProperty("baseApiUrl", baseApiUrl);
    }

    @Override
    public void onFinish(ISuite suite){
        if(parabankContainer != null){
            parabankContainer.stop();
            System.out.println("[INFO] Parabank Docker container stopped successfully!");
            }
        }
    }

