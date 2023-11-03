package fxmlControllers;

import Entities.FileItem;
import Utility.FTPServerFunctions;
import Utility.FXMLSceneController;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.fxml.FXML;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FTPMain implements Initializable {

    @FXML
    private TreeTableView<FileItem> ftable;

    @FXML
    private TreeTableColumn<FileItem, String> fname;
    @FXML
    private TreeTableColumn<FileItem, String> fsize;
    @FXML
    private TreeTableColumn<FileItem, String> fid;
    @FXML
    private TreeTableColumn<FileItem, String> dadded;

    @FXML
    private Button uploadButton;
    @FXML
    private Button shareButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button downloadButton;

    @Override
    public void initialize(URL location, ResourceBundle resources){

        // Set cell value factories
        fname.setCellValueFactory(new TreeItemPropertyValueFactory<>("fname"));
        fsize.setCellValueFactory(new TreeItemPropertyValueFactory<>("fsize"));
        //fid.setCellValueFactory(new TreeItemPropertyValueFactory<>("fid"));
        dadded.setCellValueFactory(new TreeItemPropertyValueFactory<>("dadded"));

        try {
        ArrayList<FileItem> userFiles = FTPServerFunctions.getUserFiles();
        ArrayList<FileItem> sharedFiles = FTPServerFunctions.getSharedFiles();

        FileItem userFolder = new FileItem(FTPServerFunctions.getUsername(),new ArrayList<FileItem>(){{
            for(FileItem fileItem : userFiles) {
                add(fileItem);
            }}});

        FileItem sharedFolder = new FileItem("Shared",new ArrayList<FileItem>(){{
            for(FileItem fileItem : sharedFiles) {
                add(fileItem);
            }}});

//        ftable.setRoot(generateTreeItems(new ArrayList<FileItem>(){{add(userFolder); add(sharedFolder);}}
//                , new FileItem(FTPServerFunctions.getUsername())));
        TreeItem<FileItem> usernameRoot = new TreeItem<>(userFolder);
        TreeItem<FileItem> sharedRoot = new TreeItem<>(sharedFolder);
        usernameRoot.getChildren().add(sharedRoot);
        TreeItem<FileItem> dummyRoot = new TreeItem<>();
        dummyRoot.getChildren().addAll(usernameRoot, sharedRoot);
        ftable.setRoot(usernameRoot);


    }
        catch (SQLException e) {
            e.printStackTrace();}

    }

    // Helper method to generate TreeItems from the data list
    private TreeItem<FileItem> generateTreeItems(List<FileItem> data, FileItem RootFolder) {
        TreeItem<FileItem> root = new TreeItem<>(RootFolder);
        data.forEach(item -> {
            if (item.isFolder())
                root.getChildren().add(generateTreeItems(item.getChildren(), item));
            else
                root.getChildren().add(new TreeItem<>(item));
        });
        return root;
    }

    @FXML
    private void handleUpload() throws Exception {
        // This gets the OS File Explorer
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File to Upload");
        File file = fileChooser.showOpenDialog(uploadButton.getScene().getWindow());

        // If canceled, return.. Otherwise selected file is file
        if (file == null)
            return;

        FileItem fileItem = new FileItem(file.getName(), Long.toString(file.length()), FTPServerFunctions.getUsername());

        // Use FTPServerFunctions to upload file
        FTPServerFunctions.uploadFileInfo(fileItem, file);

        // Refresh the table
        initialize(null, null);
    }

    @FXML
    private void handleDelete() throws SQLException, IOException {
        // Whatever the user has selected
        TreeItem<FileItem> selectedFile = ftable.getSelectionModel().getSelectedItem();
        if (selectedFile == null) return;

        // Use FTPServerFunctions to delete file
        FTPServerFunctions.deleteFile(selectedFile.getValue());

        // Refresh the table
        initialize(null, null);
    }

    @FXML
    private void handleDownload() throws Exception {
        // Whatever the user has selected
        TreeItem<FileItem> selectedFile = ftable.getSelectionModel().getSelectedItem();
        if (selectedFile == null) return;

        // File Explorer for selecting where you want to save the file
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File As");
        fileChooser.setInitialFileName(selectedFile.getValue().getFname());

        // Path to save the file to
        File localFilePath = fileChooser.showSaveDialog(downloadButton.getScene().getWindow());

        // If canceled; return
        if (localFilePath == null) return;

        // OutputStream
        OutputStream fos = Files.newOutputStream(localFilePath.toPath());

        // Use FTPServerFunctions to download file
        FTPServerFunctions.downloadFile(selectedFile.getValue(), fos);

        // Refresh the table
        initialize(null, null);
    }

    public void handleFileAdmin() throws IOException
    {
        FXMLSceneController.createPopUp("FTPAdmin.fxml", "Admin");
    }

    public void handleFileLogout() throws IOException
    {
        FXMLSceneController.swapScene("LoginPage");
        FTPServerFunctions.clearUsername();
        //need an exit to the FTP Client
    }

    public void handleShareButton() throws IOException
    {
        // Get selected file from tree
        FTPServerFunctions.setFile(ftable.getSelectionModel().getSelectedItem().getValue());
        FXMLSceneController.createPopUp("FTPShare.fxml", "Share");
    }
}


