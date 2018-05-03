package com.codecool.shop.config;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class ConnectionManager {

    private static ConnectionManager instance = null;

    private ConnectionManager() {
    }

    public static ConnectionManager getInstance() {
        if(instance == null) {
            instance = new ConnectionManager();
        }
        return instance;
    }

    private Properties loadPropertiesFile() throws Exception {
        Properties prop = new Properties();
        InputStream in = new FileInputStream("src/main/resources/jdbc.properties");
        prop.load(in);
        in.close();
        return prop;
    }

    public java.sql.Connection getConnection() throws SQLException {
        Properties prop = null;
        try {
            prop = loadPropertiesFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return DriverManager.getConnection(
                prop.getProperty("url"),
                prop.getProperty("username"),
                prop.getProperty("password"));
    }

}