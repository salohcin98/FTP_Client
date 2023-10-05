import java.sql.*;

import Entities.User;
import Utility.PropertiesLoader;

public class Database {
    private final Connection connection;

    public Database() throws SQLException {
        PropertiesLoader properties = new PropertiesLoader("database.properties");

        String USERNAME = properties.getProperty("db.username");
        String PASSWORD = properties.getProperty("db.password");
        String URL = String.format(
                "jdbc:mysql://%s:%s/%s",
                properties.getProperty("db.host"),
                properties.getProperty("db.port"),
                "users"
        );

        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public User login(String userid, String passwd) throws SQLException {
        String query = "SELECT * FROM ftpuser WHERE userid = ? AND passwd = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userid);
            preparedStatement.setString(2, passwd);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                // Add other columns as needed

                // Create and return a User object
                return new User(id, userid, passwd); // Assuming you have a User class
            }
        }

        // User not found
        return null;
    }
}
