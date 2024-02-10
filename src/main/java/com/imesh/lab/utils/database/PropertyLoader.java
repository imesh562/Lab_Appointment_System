package com.imesh.lab.utils.database;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyLoader {
    public static Properties getPropertyData() {
        try {
            Properties property = new Properties();
            InputStream inputStream = PropertyLoader.class
                    .getClassLoader()
                    .getResourceAsStream("database.properties");
            property.load(inputStream);
            inputStream.close();
            return property;
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
}