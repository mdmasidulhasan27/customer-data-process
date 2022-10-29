package data.utils.enums;

public enum Customer {
    FIRST_NAME("first_name", 0),
    LAST_NAME("last_name", 1),
    CITY("city_name", 2),
    STATE("state", 3),
    ZIP_CODE("zip_code", 4),
    PHONE("phone", 5),
    EMAIL("email", 6),
    IP("ip", 7);

    private String title;
    private int id;
    Customer(String title, int id) {
        this.title = title;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }
}
