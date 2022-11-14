package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static String DB_URL = "jdbc:mysql://localhost:3306/db";
    private static String USER_NAME = "root";
    private static String PASSWORD = "Titdepzai123";
    public Connection conn = null;

    public ConnectionManager() throws Exception {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Created DB Connection...");
        } catch (Exception e) {
            throw e;
        }
    }

    public void open() throws SQLException {
        try {
            if (this.conn == null) {
                this.conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
                System.out.println("Database Connected!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void close() throws SQLException {
        try {
            if (this.conn != null) {
                this.conn.close();
                System.out.println("Connection has been close!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private static ConnectionManager getInstance() {
        return ConnectionManagerSingleton.instance.get();
    }
}
