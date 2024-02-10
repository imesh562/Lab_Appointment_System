package com.imesh.lab.utils.database;

import java.util.Properties;

public class ConnectionFactory {
    public DataSource getDatabase() {
        Properties properties = PropertyLoader.getPropertyData();
        switch(properties.getProperty("selected_db")) {
            case "mysql":
                return new MySqlDataSourceImpl();
            default: return null;
        }
    }
}
