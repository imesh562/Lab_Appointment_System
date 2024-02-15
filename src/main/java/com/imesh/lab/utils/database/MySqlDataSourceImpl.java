package com.imesh.lab.utils.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySqlDataSourceImpl implements DataSource{
    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Properties properties = PropertyLoader.getPropertyData();
        Class.forName("com.mysql.cj.jdbc.Driver");

        String url = properties.getProperty("mysql.url");
        String username = properties.getProperty("mysql.username");
        String password = properties.getProperty("mysql.password")  == null ? "" : properties.getProperty("mysql.password");
        return DriverManager.getConnection(url, username, password);

    }
}
