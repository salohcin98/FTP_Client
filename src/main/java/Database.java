import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import Utility.PropertiesLoader;

public class Database {
    private static String URL = "jdbc:mysql://localhost:3306/your_database_name";
    private static String USERNAME;
    private static String PASSWORD;

    public Database(){
        PropertiesLoader properties = new PropertiesLoader("database.properties");
        USERNAME = properties.getProperty("db.username");
        PASSWORD = properties.getProperty("db.password");
        URL = String.format(
                "jdbc:mysql://%s:%s/%s",
                properties.getProperty("db.host"),
                properties.getProperty("db.port"),
                "users"
        );
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
