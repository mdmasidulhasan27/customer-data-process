package data.utils;

import data.utils.enums.Customer;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class GeneralUtil {
    /*public static <T> List<T> getUniqueFromList(List<T> data) {
        Set<T> dataSet = new HashSet<>(data);
        data.clear();
        data.addAll(dataSet);
        return data;
    }*/

    public static List<String> getUniqueFromCustomerListBasedOnEmailAndPhone(List<String> data) {
        int preSetSize;
        Set<Integer> dataSet = new HashSet<>();
        List<String> newData = new LinkedList<>();
        preSetSize = 0;
        for (String s : data) {
            dataSet.add((s.split(",")[Customer.EMAIL.getId()] + s.split(",")[Customer.PHONE.getId()]).hashCode());
            if (dataSet.size() > preSetSize) {
                preSetSize = dataSet.size();
                newData.add(s);
            }
        }

        return newData;
    }

    public static List<String> getCustomerValidData(List<String> data) {
        List<String> newData = new LinkedList<>();
        for (String s : data) {
            String phone = s.split(",")[Customer.PHONE.getId()];
            String gmail = s.split(",")[Customer.EMAIL.getId()];
            if (GeneralUtil.isValidPhoneNo(phone) || GeneralUtil.isValidGmail(gmail)) //if any one from phone and email is valid then considering the data as valid.
                newData.add(s);
            else
                System.out.println(s);
        }
        return newData;
    }

    public static Boolean isValidGmail(String gmail) {
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"; //RFC5322 standard
        return Pattern.compile(regex).matcher(gmail).matches();
    }

    public static Boolean isValidPhoneNo(String phoneNo) {
        String regex = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"
                + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"
                + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$";
        return Pattern.compile(regex).matcher(phoneNo).matches();
    }
}
