package Utils;

public enum DataBase {
    URL("jdbc:mysql://localhost:3306/taskmaster_app_db"),
    NAME("root"),
    CNM("com.mysql.cj.jdbc.Driver"),//connection manager class name
    POOL_SIZE("10"),
    PWD("_9L#2*6n");
    public final String value;
    DataBase(String value) {
        this.value = value;
    }
}
