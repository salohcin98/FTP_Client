package Utility;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


/**
 * Simplifies our FTPClient implementation
 */
public class FTPClientHandler extends FTPClient {

    /**
     * username of current user
     */
    private final String username;

    /**
     * password of current user
     */
    private final String password;

    /**
     * sets the initial username/password of the current user
     * @param username the username
     * @param password the password
     */
    public FTPClientHandler(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Simplifies logging in. Can now be exposed without worrying about also passing along login info
     */
    public boolean login() throws IOException {
        PropertiesLoader properties = new PropertiesLoader("ftp");
        String server = properties.getProperty("ftp.host");
        int port = Integer.parseInt(properties.getProperty("ftp.port"));

        super.connect(server, port);
        super.enterLocalPassiveMode();
        super.setFileType(FTP.BINARY_FILE_TYPE);

        if (super.login(username, password)) {
            // Check the FTP server's reply code for successful login
            return FTPReply.isPositiveCompletion(super.getReplyCode());
        }

        return false;
    }

    /**
     * logout with in-built disconnect.
     * @return boolean indicating success
     */
    @Override
    public boolean logout() throws IOException {
        boolean result = super.logout();
        super.disconnect();
        return result;
    }

    /**
     * Store file using the {@link File}.
     * no need to login or connect when using this. may eventually convert this class from implementing
     * {@link FTPClient} to simply storing it as a private variable
     * @param file the {@link File}
     */
    public boolean storeFile(File file) throws IOException {
        // login and connect to the ftp server
        login();

        //send the file
        FileInputStream inputStream = new FileInputStream(file);
        boolean uploaded = super.storeFile(file.getName(), inputStream);
        inputStream.close();

        //logout and disconnect
        logout();

        return uploaded;
    }

    /**
     * Get username of current user
     * @return username
     */
}
