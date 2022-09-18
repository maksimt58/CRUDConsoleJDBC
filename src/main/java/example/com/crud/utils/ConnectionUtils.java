package example.com.crud.utils;

import java.sql.*;

public class ConnectionUtils {
    private static final String DATABASE_URL = "jdbc:mysql://localhost/habrahabr";
    private static final String USER = "root";
    private static final String PASSWORD = "2422";
    private static Connection connection = null;

    public static Connection getConnection() {
        if(connection == null){
            try {
                connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            } catch (SQLException e) {
                System.out.println("Database Connection Creation Failed : " + e.getMessage());
            }
        }
        return connection;
    }

    public static PreparedStatement preparedStatement(String sql){
        try {
            return getConnection().prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Unable to create prepared statement: " + e.getMessage());
        }
    }

    public static  PreparedStatement preparedStatementWithGeneratedKeys(String sql){
        try {
            return getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException e) {
            throw new RuntimeException("Unable to create prepared statement: " + e.getMessage());
        }
    }

    public static Statement createStatement(){
        try {
            return  getConnection().createStatement();
        } catch (SQLException e) {
            throw new RuntimeException("Unable to create statement: " + e.getMessage());
        }
    }
}
