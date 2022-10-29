package data.process;

import data.utils.DatabaseUtil;
import data.utils.GeneralUtil;
import data.utils.ReadWriteTxt;

import java.util.List;

public class CustomerDataProcess {
    public static void main(String[] args) {
        Long start, dataReadTime, findUniqueCustomerTime, findDataValidateTime, findInsertionTimeToDB;

        start = System.currentTimeMillis();
        List<String> data = ReadWriteTxt.getCustomersData("testData.txt");
        dataReadTime = System.currentTimeMillis() - start;
        System.out.println("Total customer Data: " + data.size() + "     -------------Execution Time: " + dataReadTime);

        start = System.currentTimeMillis();
        data = GeneralUtil.getUniqueFromCustomerListBasedOnEmailAndPhone(data);
        findUniqueCustomerTime = System.currentTimeMillis() - start;
        System.out.println("Total Unique customer Data: " + data.size() + "     -------------Execution Time: " + findUniqueCustomerTime);

        start = System.currentTimeMillis();
        data = GeneralUtil.getCustomerValidData(data);
        findDataValidateTime = System.currentTimeMillis() - start;
        System.out.println("Total Unique customer Data after validation: " + data.size() + "     -------------Execution Time: " + findDataValidateTime);

        start = System.currentTimeMillis();
        DatabaseUtil.insertCustomersInfo(data);
        findInsertionTimeToDB = System.currentTimeMillis() - start;
        System.out.println("Total unique valid customer data inserted to database: " + data.size() + "     -------------Execution Time: " + findInsertionTimeToDB);

    }

}
