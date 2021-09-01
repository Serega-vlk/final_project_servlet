package com.dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DBConnection {
    private static DBConnection dbConnection;
    private Connection connection;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private DBConnection() throws SQLException {
        ResourceBundle bundle = ResourceBundle.getBundle("applicationProperties");
        this.connection = DriverManager.getConnection(bundle.getString("db.url"),
                bundle.getString("db.username"),
                bundle.getString("db.password"));
    }

    public static DBConnection getDBConnection() throws SQLException{
        if (dbConnection == null){
            dbConnection = new DBConnection();
        }
        return dbConnection;
    }

    public Connection getConnection() {
        return connection;
    }
}
