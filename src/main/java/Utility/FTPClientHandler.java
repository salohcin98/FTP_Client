package Utility;

import lombok.Setter;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;

public class FTPClientHandler extends FTPClient {

    @Setter
    private final String username;
    @Setter
    private final String password;

    public FTPClientHandler(String username, String password) throws IOException {
        this.username = username;
        this.password = password;
    }

    /**
     * Simplifies logging in. can now be exposed without worrying about also passing along login info
     * @throws IOException
     */
    public boolean login() throws IOException {
        return login(username,  password);
    }

    @Override
    public boolean login(String username, String password) throws IOException {
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

    @Override
    public boolean logout() throws IOException {
        boolean result = super.logout();
        super.disconnect();
        return result;
    }
}
