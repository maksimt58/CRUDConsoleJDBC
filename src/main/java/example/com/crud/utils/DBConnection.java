package example.com.crud.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    static final String DATABASE_URL = "jdbc:mysql://localhost/habrahabr";
    static final String USER = "root";
    static final String PASSWORD = "2422";
    private static DBConnection instance;
    private Connection connection = null;


    private DBConnection() {
        if (connection == null) {
            try {
                this.connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            } catch (SQLException e) {
                System.out.println("Database Connection Creation Failed : " + e.getMessage());
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static DBConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new DBConnection();
        } else if (instance.getConnection().isClosed()) {
            instance = new DBConnection();
        }

        return instance;
    }
}
