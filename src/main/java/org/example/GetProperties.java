package org.example;

import java.io.IOException;
import java.util.Properties;

public class GetProperties {

    private final Properties prop = new Properties();

    GetProperties() {
        try {
            prop.load(Bot.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    String getToken() {
        return prop.getProperty("token");
    }

    String getName() {
        return prop.getProperty("name");
    }

    String getDB() {
        return prop.getProperty("database");
    }

    String getUser() {
        return prop.getProperty("user");
    }

    String getPass() {
        return prop.getProperty("password");
    }
}
