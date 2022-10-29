package data.utils;

import data.utils.enums.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class DatabaseUtil {
    private static Statement stmt = null;
    private static Connection con = null;
    private static PreparedStatement prestmt = null;

    public static void createCustomerInfoTableIfNotExist() {
        String queryCreateTable = "CREATE TABLE IF NOT EXISTS `customer_info` (`id` INT NOT NULL AUTO_INCREMENT , `first_name` VARCHAR(50) NOT NULL , `last_name` VARCHAR(50) NOT NULL , `city_name` VARCHAR(50) NOT NULL , `state` VARCHAR(20) NOT NULL , `zip_code` VARCHAR(20) NOT NULL , `phone` VARCHAR(20) NOT NULL , `email` VARCHAR(50) NOT NULL , `ip` VARCHAR(20) NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB;";
        try {
            con = MySqlConnection.getMySqlConnection();
            stmt = con.createStatement();
            stmt.executeUpdate(queryCreateTable);
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
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

                prestmt.setString(Customer.FIRST_NAME.getId() + 1, sSplit.get(Customer.FIRST_NAME.getId()));
                prestmt.setString(Customer.LAST_NAME.getId() + 1, sSplit.get(Customer.LAST_NAME.getId()));
                prestmt.setString(Customer.CITY.getId() + 1, sSplit.get(Customer.CITY.getId()));
                prestmt.setString(Customer.STATE.getId() + 1, sSplit.get(Customer.STATE.getId()));
                prestmt.setString(Customer.ZIP_CODE.getId() + 1, sSplit.get(Customer.ZIP_CODE.getId()));
                prestmt.setString(Customer.PHONE.getId() + 1, sSplit.get(Customer.PHONE.getId()));
                prestmt.setString(Customer.EMAIL.getId() + 1, sSplit.get(Customer.EMAIL.getId()));
                prestmt.setString(Customer.IP.getId() + 1, sSplit.get(Customer.IP.getId()));
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

    public static void insertCustomerInfo(String data) {
        createCustomerInfoTableIfNotExist();
        String prepareInsertQuery = "INSERT INTO customer_info (`first_name`, `last_name`, `city_name`, `state`, `zip_code`, `phone`, `email`, `ip`) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
        List<String> sSplit = Arrays.asList(data.split(",", 8));
        System.out.println(sSplit.size());
        try {
            con = MySqlConnection.getMySqlConnection();
            prestmt = con.prepareStatement(prepareInsertQuery);
            prestmt.setString(Customer.FIRST_NAME.getId() + 1, sSplit.get(Customer.FIRST_NAME.getId()));
            prestmt.setString(Customer.LAST_NAME.getId() + 1, sSplit.get(Customer.LAST_NAME.getId()));
            prestmt.setString(Customer.CITY.getId() + 1, sSplit.get(Customer.CITY.getId()));
            prestmt.setString(Customer.STATE.getId() + 1, sSplit.get(Customer.STATE.getId()));
            prestmt.setString(Customer.ZIP_CODE.getId() + 1, sSplit.get(Customer.ZIP_CODE.getId()));
            prestmt.setString(Customer.PHONE.getId() + 1, sSplit.get(Customer.PHONE.getId()));
            prestmt.setString(Customer.EMAIL.getId() + 1, sSplit.get(Customer.EMAIL.getId()));
            prestmt.setString(Customer.IP.getId() + 1, sSplit.get(Customer.IP.getId()));
            prestmt.addBatch();
            prestmt.executeBatch();
            con.commit();
            con.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public static void dropCustomerInfoTable() {
        String queryCreateTable = "DROP TABLE IF EXISTS `orange_toolz`.`customer_info`";
        try {
            con = MySqlConnection.getMySqlConnection();
            stmt = con.createStatement();
            stmt.executeUpdate(queryCreateTable);
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        //dropCustomerInfoTable();
        insertCustomersInfo(new LinkedList<>());
    }
}
