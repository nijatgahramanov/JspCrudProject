package com.example.jspcrudproject.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AbstractDao {



    private static Connection c = null;


    static {
        String dbUrl = "jdbc:mysql://localhost:3306/jsp_crud";
        String name = "root";
        String password = "nicat12345";

        try {
            Connection c = DriverManager.getConnection(name, password, dbUrl);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return c;
    }


}
