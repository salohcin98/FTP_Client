package Connections;

import java.sql.*;

import Entities.FTPUser;
import Utility.PropertiesLoader;

public class DBConnection {
    private final static PropertiesLoader properties;

    private final static String URL;
    private final static String GUEST_USERNAME;
    private final static String GUEST_PASSWORD;

    // get initial database info for guest account / database url
    static {
        properties = new PropertiesLoader("database");
        GUEST_USERNAME = properties.getProperty("db.guest_username");
        GUEST_PASSWORD = properties.getProperty("db.guest_password");

        URL = String.format(
                "jdbc:mysql://%s:%s/%s",
                properties.getProperty("db.host"),
                properties.getProperty("db.port"),
                "users"
        );
    }

    // the Connection this database instance currently has
    private Connection connection;

    /**
     * Connect to the MySQL database as a guest (only checking username/password info allowed)
     * @throws SQLException Sql error, should only appear if we do something wrong
     */
    public DBConnection() throws SQLException {
        connect(); // login as guest
    }

    /**
     * Connect to the MySQL database as a specific user
     * @param USERNAME the username of your account
     * @param PASSWORD the password of your account
     * @throws SQLException Sql error, should only appear if we do something wrong
     */
    public DBConnection(String USERNAME, String PASSWORD) throws SQLException {
        connect(USERNAME, PASSWORD); // login as user
    }

    /**
     * Establishes a connection with the mysql database using the provided login info. Use with no parameters to login as guest.
     * @throws SQLException Sql error, should only appear if we do something wrong
     */
    public void connect() throws SQLException {
        connect(GUEST_USERNAME, GUEST_PASSWORD);
    }

    /**
     * Establishes a connection with the mysql database using the provided login info. Use with no parameters to login as guest.
     * @param USERNAME the username of the account
     * @param PASSWORD the username of the account
     * @throws SQLException Sql error, should only appear if we do something wrong
     */
    public void connect(String USERNAME, String PASSWORD) throws SQLException {
        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    /**
     * Establishes a connection with the ftp server (this is where we will get/send files)
     *
     * @param userid the username for the ftp connection
     * @param passwd the password for the ftp connection
     * @return {@link FTPConnection}
     * @throws SQLException Sql error, should only appear if we do something wrong
     */
    public FTPUser login(String userid, String passwd) throws SQLException {

        // sql query to check if user exists
        String queryUser = "SELECT * FROM ftpuser WHERE userid = ? AND passwd = ?";
        ResultSet results = SQLQuery(queryUser, userid, passwd);

        if (results.next()) { // if we get at least one result, use that one result
            int id = results.getInt("id");
            int dir = results.getInt("homedir");
            // Add other columns as needed if we need to get that data

            // Create and return the user
            return new FTPUser(id, userid, passwd); // Assuming you have a FTPUser class
        }

        // FTPUser not found
        return null;
    }

    /**
     * Helper method for queries
     *
     * Example:
     * - query = "SELECT * FROM ftpuser WHERE userid = ? AND passwd = ?"
     * - SQLQuery(query, ? #1, ? #2)
     *
     * @param query the sql query
     * @param params the parameters for the query
     * @return the {@link ResultSet}
     * @throws SQLException Sql error, should only appear if we do something wrong
     */
    private ResultSet SQLQuery(String query, String... params) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        // pass the parameters if needed
        for (int i = 0; i < params.length; i++)
            preparedStatement.setString(i+1, params[i]);

        return preparedStatement.executeQuery();
    }
}
