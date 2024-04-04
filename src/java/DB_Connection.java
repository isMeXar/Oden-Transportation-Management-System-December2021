package com.example.myapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB_Connection {

    public static Connection getConn() throws SQLException {
        Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bd_javaprojectapp","root","2507");
        return myConn;
    }
}
