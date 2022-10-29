package data.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class ReadWriteTxt {
    private static final String projectPath = System.getProperty("user.dir");
    private static final String customersDataPath = projectPath + "\\src\\main\\resources\\files\\customersData\\";

    public static List<String> getCustomersData(String txtFileName) {
        Path filePath = Path.of(customersDataPath + txtFileName);
        List<String> customersDataList = new LinkedList<>();
        if (Files.exists(filePath)) {
            try (BufferedReader br = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
                for (String line = null; (line = br.readLine()) != null; )
                    customersDataList.add(line);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return customersDataList;
        }
        return new LinkedList<>();
    }

}
