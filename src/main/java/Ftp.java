import Utility.PropertiesLoader;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import java.io.*;

public class Ftp {

    public void sendTestFile() {
        PropertiesLoader properties = new PropertiesLoader("ftp");

        String server = properties.getProperty("ftp.host");
        int port = Integer.parseInt(properties.getProperty("ftp.port")); // FTP server port
        String username = "outcast";
        String password = "test";

        FTPClient ftpClient = new FTPClient();

        try {
            ftpClient.connect(server, port);
            ftpClient.login(username, password);

            // Upload a file
            String localFilePath = "TestFiles/test.txt";
            String remoteFilePath = "/testingjoshua/test.txt";
            FileInputStream inputStream = new FileInputStream(localFilePath);
            //ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.storeFile(remoteFilePath, inputStream);
            inputStream.close();

            // Download a file
            /**
            String localDownloadPath = "path/to/local/downloaded-file.txt";
            FileOutputStream outputStream = new FileOutputStream(localDownloadPath);
            ftpClient.retrieveFile(remoteFilePath, outputStream);
            outputStream.close();
             */

            ftpClient.logout();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
