package data.process;

import data.utils.DatabaseUtil;
import data.utils.GeneralUtil;
import data.utils.ReadWriteTxt;

import java.util.LinkedList;
import java.util.List;

public class CustomerDataProcess {
    public static void main(String[] args) {
        long start, dataReadTime, findUniqueCustomerTime, findDataValidateTime, findInsertionTimeToDB;
        long totalCustomers, totalUniqueCustomers, totalUniqueValidCustomers, totalCustomersInserted;

        start = System.currentTimeMillis();
        List<String> data = ReadWriteTxt.getCustomersData("1M-customers.txt");
        dataReadTime = System.currentTimeMillis() - start;
        totalCustomers = data.size();
        System.out.println("Total customer Data: " + totalCustomers + "     -------------Execution Time: " + dataReadTime);

        start = System.currentTimeMillis();
        data = GeneralUtil.getUniqueFromCustomerListBasedOnEmailAndPhone(data);
        findUniqueCustomerTime = System.currentTimeMillis() - start;
        totalUniqueCustomers = data.size();
        System.out.println("Total Unique customer Data: " + totalUniqueCustomers + "     -------------Execution Time: " + findUniqueCustomerTime);

        start = System.currentTimeMillis();
        data = GeneralUtil.getCustomerValidData(data);
        findDataValidateTime = System.currentTimeMillis() - start;
        totalUniqueValidCustomers = data.size();
        System.out.println("Total Unique customer Data after validation(if any one from phone and email is valid then considered the data as valid): " + totalUniqueValidCustomers + "     -------------Execution Time: " + findDataValidateTime);

        start = System.currentTimeMillis();
        DatabaseUtil.insertCustomersInfo(data);
        findInsertionTimeToDB = System.currentTimeMillis() - start;
        totalCustomersInserted = data.size();
        System.out.println("Total unique valid customer data inserted to database: " + totalCustomersInserted + "     -------------Execution Time: " + findInsertionTimeToDB);


        List<String> precessDetails = new LinkedList<>();
        precessDetails.add("Customers read from txt file," + totalCustomers + "," + dataReadTime);
        precessDetails.add("Removed duplicate customers," + totalUniqueCustomers + "," + findUniqueCustomerTime);
        precessDetails.add("Removed invalid customers (if any of phone or email is valid then considered as valid)," + totalUniqueValidCustomers + "," + findDataValidateTime);
        precessDetails.add("Insert customers into database," + totalCustomersInserted + "," + findInsertionTimeToDB);
        DatabaseUtil.insertCustomersDataProcessDetails(precessDetails);
    }

}
