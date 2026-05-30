package model;

import java.sql.Connection;
import java.sql.DriverManager;

public class Koneksi {
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(
                "jdbc:mysql://localhost:3307/db_unpam",
                "root",
                ""
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
