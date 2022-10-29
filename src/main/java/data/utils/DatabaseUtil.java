package data.utils;

import data.utils.enums.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

public class DatabaseUtil {
    private static Statement stmt = null;
    private static Connection con = null;
    private static PreparedStatement prestmt = null;

    private static void createCustomerInfoTableIfNotExist() {
        String queryCreateTable = "CREATE TABLE IF NOT EXISTS `customer_info` (`id` INT NOT NULL AUTO_INCREMENT , `first_name` VARCHAR(50) NOT NULL , `last_name` VARCHAR(50) NOT NULL , `city_name` VARCHAR(50) NOT NULL , `state` VARCHAR(20) NOT NULL , `zip_code` VARCHAR(20) NOT NULL , `phone` VARCHAR(20) NOT NULL , `email` VARCHAR(50) NOT NULL , `ip` VARCHAR(20) NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB;";
        singleExecutionToDB(queryCreateTable);
    }

    private static void createCustomerDataProcessDetailsTableIfNotExist() {
        String queryCreateTable = "CREATE TABLE IF NOT EXISTS `customer_data_process_details` (`id` INT NOT NULL AUTO_INCREMENT , `title` VARCHAR(100) NOT NULL , `total_customers` VARCHAR(20) NOT NULL , `execution_time_in_ms` VARCHAR(20) NOT NULL, PRIMARY KEY (`id`)) ENGINE = InnoDB;";
        singleExecutionToDB(queryCreateTable);
    }

    private static void dropCustomerInfoTable() {
        String queryDropTable = "DROP TABLE IF EXISTS `orange_toolz`.`customer_info`";
        singleExecutionToDB(queryDropTable);
    }

    private static void dropCustomerDataProcessDetailsTable() {
        String queryDropTable = "DROP TABLE IF EXISTS `orange_toolz`.`customer_data_process_details`";
        singleExecutionToDB(queryDropTable);
    }

    public static void insertCustomersInfo(List<String> data) {
        dropCustomerInfoTable();
        createCustomerInfoTableIfNotExist();

        String prepareInsertQuery = "INSERT INTO customer_info (`first_name`, `last_name`, `city_name`, `state`, `zip_code`, `phone`, `email`, `ip`) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            con = MySqlConnection.getMySqlConnection();
            PreparedStatement prestmt = con.prepareStatement(prepareInsertQuery);
            int index = 0;
            for (String s : data) {
                List<String> sSplit = Arrays.asList(s.split(",", 8));

                prestmt.setString(1, sSplit.get(Customer.FIRST_NAME.getId()));
                prestmt.setString(2, sSplit.get(Customer.LAST_NAME.getId()));
                prestmt.setString(3, sSplit.get(Customer.CITY.getId()));
                prestmt.setString(4, sSplit.get(Customer.STATE.getId()));
                prestmt.setString(5, sSplit.get(Customer.ZIP_CODE.getId()));
                prestmt.setString(6, sSplit.get(Customer.PHONE.getId()));
                prestmt.setString(7, sSplit.get(Customer.EMAIL.getId()));
                prestmt.setString(8, sSplit.get(Customer.IP.getId()));
                prestmt.addBatch();

                if (index++ >= 1000) {
                    prestmt.executeBatch();
                    prestmt.clearBatch();
                    index = 0;
                }
            }
            if (index > 0)
                prestmt.executeBatch();

            con.commit();
            con.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public static void insertCustomersDataProcessDetails(List<String> data) {
        dropCustomerDataProcessDetailsTable();
        createCustomerDataProcessDetailsTableIfNotExist();
        String prepareInsertQuery = "INSERT INTO customer_data_process_details (`title`, `total_customers`, `execution_time_in_ms`) VALUES (?, ?, ?);";
        try {
            con = MySqlConnection.getMySqlConnection();
            PreparedStatement prestmt = con.prepareStatement(prepareInsertQuery);
            int index = 0;
            for (String s : data) {
                List<String> sSplit = Arrays.asList(s.split(",", 3));

                prestmt.setString(1, sSplit.get(0));
                prestmt.setString(2, sSplit.get(1));
                prestmt.setString(3, sSplit.get(2));
                prestmt.addBatch();

                if (index++ >= 1000) {
                    prestmt.executeBatch();
                    prestmt.clearBatch();
                    index = 0;
                }
            }
            if (index > 0)
                prestmt.executeBatch();

            con.commit();
            con.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    private static void singleExecutionToDB(String sql) {
        try {
            con = MySqlConnection.getMySqlConnection();
            stmt = con.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
}
