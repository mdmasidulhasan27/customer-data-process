package data.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySqlConnection {
    private static final String dbUrl = "jdbc:mysql://localhost:3306/orange_toolz";

    public static Connection getMySqlConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(dbUrl, "root", "");
            connection.setAutoCommit(false);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return connection;
    }

}
