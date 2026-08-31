package com.parabank.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationManager {
    private static final Properties properties;

    static {
        properties = new Properties();
        try (FileInputStream file = new FileInputStream("src/main/resources/config.properties")) {
            properties.load(file);
        } catch (IOException e) {

            throw new RuntimeException("Failed to load config.properties file!", e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static void setProperty(String key, String value) {
      properties.setProperty(key, value);
    }
}
