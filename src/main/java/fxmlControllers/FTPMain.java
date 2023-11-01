package fxmlControllers;

import Entities.FileItem;
import Utility.FTPServerFunctions;
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
import java.net.URL;
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

    @Override
    public void initialize(URL location, ResourceBundle resources){

        // Set cell value factories
        fname.setCellValueFactory(new TreeItemPropertyValueFactory<>("fname"));
        fsize.setCellValueFactory(new TreeItemPropertyValueFactory<>("fsize"));
        //fid.setCellValueFactory(new TreeItemPropertyValueFactory<>("fid"));
        dadded.setCellValueFactory(new TreeItemPropertyValueFactory<>("dadded"));

        try {
            FTPServerFunctions.getUserFiles();
        } catch (SQLException e) {
            e.printStackTrace();
        }

  
        try {
            final ArrayList<FileItem> generatedList = new ArrayList<>(FTPServerFunctions.getUserFiles());
            ftable.setRoot(generateTreeItems(new ArrayList<FileItem>(){{
                for (FileItem fileItem : generatedList) {
                    if (!fileItem.isFolder())
                        add(fileItem);

                }
            }}, new FileItem(FTPServerFunctions.getUsername())));
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        FTPServerFunctions.uploadFileInfo(fileItem);
        FTPServerFunctions.uploadFileFTP(file);

        // Refresh the table
        initialize(null, null);
    }

    /*
    @FXML
    private void handleDownload() throws Exception {
        // Let's get whatever the user has selected
        TreeItem<FileItem> selectedFile = ftable.getSelectionModel().getSelectedItem();
        if (selectedFile == null) return;

        // File Explorer for selecting where you want to save the file
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File As");
        fileChooser.setInitialFileName(selectedFile.getValue().getFname());

        // Set localFile = to the users chosen file
        File localFile = fileChooser.showSaveDialog(downloadButton.getScene().getWindow());

        // If canceled, return.. Otherwise selected file is localFile
        if (localFile == null) return;
        FileOutputStream fos = new FileOutputStream(localFile);

        // Use FTPServerFunctions to download file
        FTPServerFunctions.downloadFile(selectedFile.getValue().getFname(), fos);

        // Close the output stream
        fos.close();

        // No need to at the moment... BUT if we want to add a progress bar..
        // Or even just increment the amount of times a file was downloaded.. We could do that too?
        initialize(null, null);
    }

     */


}


