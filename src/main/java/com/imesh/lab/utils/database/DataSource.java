package com.imesh.lab.utils.database;

import java.sql.Connection;
import java.sql.SQLException;

public interface DataSource {
    public Connection getConnection() throws ClassNotFoundException, SQLException;
}
