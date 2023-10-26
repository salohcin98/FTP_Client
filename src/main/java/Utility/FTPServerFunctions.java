package Utility;
import Connections.DBConnection;
import java.io.IOException;
import java.sql.*;
import Entities.FileItem;
import java.util.ArrayList;

/**
 * static methods within this class are used to get certain information from the FTP server or Database
 */
public class FTPServerFunctions {
    /**
     * Gets the user's list of files.
     *
     * @param user the userid of the user (username)
     * @return {@link ArrayList} containing {@link FileItem}
     * @throws SQLException when sql query is incorrect or some sql error occurs
     */
    public static ArrayList<FileItem> getUserFiles(String user) throws SQLException{

        Connection conn = DBConnection.getConnection();
        String query = "Select fileID,fileName,fileSize,fileUpload from users.ftpfile where fileOwner = '" + user + "'";
        ArrayList<FileItem> fileList = new ArrayList<>();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(query);
        while(rs.next()) {
            String fid = Integer.toString(rs.getInt("fileID"));
            String fsize = Integer.toString(rs.getInt("fileSize"));
            String fName = rs.getString("fileName");
            String fUpload = rs.getString("fileUpload");
            FileItem file = new FileItem(fName, fsize, fid, user, fUpload);
            fileList.add(file);
        }
        st.close();
        rs.close();

        return fileList;
    }

    /**
     * Upload a file's info to the database
     * @param file the FileItem with the file's information
     * @throws SQLException when something goes wrong with the sql database or with the sql statement
     */
    public static void uploadFileInfo(FileItem file) throws SQLException{
        String query = "Select coalesce(max(fileid), 0)+1 as fid from users.ftpfile;";
        int fileid= DBConnection.SQLQuery(query).getInt("fid");
        int filesize = Integer.parseInt(file.getFsize());
        String filename = file.getFname();
        String fileowner = file.getFowner();


        query = "Insert into users.ftpfile (fileID,fileName,fileSize,fileDir,fileOwner)" +
                "values(" + fileid + ",'" + filename + "'," + filesize
                + ",'/mnt/userDir/" + fileid + "/" + filename +"','" + fileowner + "')";
        Connection conn = DBConnection.getConnection();
        Statement st = conn.createStatement();
        st.executeUpdate(query);
        st.close();
    }

    /**
     *
     * @param file the file's info
     * @param user the userid of the user
     * @throws SQLException when something goes wrong with the sql database or with the sql statement
     */
    public void fileShare(FileItem file, String user) throws SQLException{
        String fid = file.getFid();
        String query = "Insert into users.ftpfile_share(fileID,userID) values (" + fid + ",'" + user + "')";
        Connection conn = DBConnection.getConnection();
        Statement st = conn.createStatement();
        st.executeUpdate(query);
        st.close();
    }

    /**
     * deletes a file from the database
     *
     * @param file the file's info
     * @throws SQLException when something goes wrong with the sql database or with the sql statement
     */
    public void deleteFile(FileItem file) throws SQLException{
        String fid = file.getFid();
        String query = "select * from users.ftpfile_share where fileID = " + fid;

        try{
            Connection conn = DBConnection.getConnection();
            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery(query);
            if(rs.next()){
                query = "Delete from users.ftpfile_share where fileID = " + fid;
                st.executeUpdate(query);
            }
            query = "Delete from users.ftpfile where fileID = " + fid;
            st.executeUpdate(query);
            st.close();
            rs.close();

        } catch (SQLException e)
        {
            System.out.println("Message: " + e.getMessage());
        }
    }
}
