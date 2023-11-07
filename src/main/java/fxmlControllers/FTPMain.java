package fxmlControllers;

import Entities.FileItem;
import Entities.Folder;
import Entities.Item;
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
import javafx.scene.control.Menu;

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
    private TreeTableView<Item> ftable;

    @FXML
    private TreeTableColumn<Item, String> fname;
    @FXML
    private TreeTableColumn<Item, String> fsize;
    @FXML
    private TreeTableColumn<Item, String> fid;
    @FXML
    private TreeTableColumn<Item, String> dadded;

    @FXML
    private Button uploadButton;
    @FXML
    private Button shareButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button downloadButton;
    @FXML
    private Menu menuAdmin;

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

        System.out.println(userFiles);

        Folder userFolder = new Folder(FTPServerFunctions.getUsername(), new ArrayList<Item>(){{
            this.addAll(userFiles);
        }});

        Folder sharedFolder = new Folder("Shared", new ArrayList<Item>(){{
            this.addAll(sharedFiles);
        }});

        ftable.setRoot(generateTreeItems(new ArrayList<Item>(){{add(userFolder); add(sharedFolder);}}
                , new Folder("root")));
        ftable.setShowRoot(false);

        //check if admin, if not hide the Menu Admin
        if(!FTPServerFunctions.isUserAdmin())
        {
            menuAdmin.setVisible(false);
        }

        } catch (SQLException e) {
            e.printStackTrace();}
    }

    // Helper method to generate TreeItems from the data list
    private TreeItem<Item> generateTreeItems(List<Item> data, Folder RootFolder) {
        TreeItem<Item> root = new TreeItem<>(RootFolder);
        data.forEach(item -> {
            if (item instanceof Folder)
                root.getChildren().add(generateTreeItems(((Folder) item).getChildren(), (Folder) item));
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

        // Refresh the table (it should be fine to refresh here)
        initialize(null, null);
    }

    @FXML
    private void handleDelete() throws SQLException, IOException {
        // Whatever the user has selected
        TreeItem<Item> selectedFile = ftable.getSelectionModel().getSelectedItem();

        if (selectedFile == null) return; //check null
        if (!(selectedFile.getValue() instanceof FileItem)) return; //check that file is selected and not folder

        // Use FTPServerFunctions to delete file
        FTPServerFunctions.deleteFile((FileItem) selectedFile.getValue());

        // remove the item (don't refresh as it closes all drop-downs)
        selectedFile.getParent().getChildren().remove(selectedFile);
    }

    @FXML
    private void handleDownload() throws Exception {
        // Whatever the user has selected
        TreeItem<Item> selectedFile = ftable.getSelectionModel().getSelectedItem();

        if (selectedFile == null) return; //check null
        if (!(selectedFile.getValue() instanceof FileItem)) return; //check that file is selected and not folder

        // File Explorer for selecting where you want to save the file
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File As");
        fileChooser.setInitialFileName(((FileItem) selectedFile.getValue()).getFname());

        // Path to save the file to
        File localFilePath = fileChooser.showSaveDialog(downloadButton.getScene().getWindow());

        // If canceled; return
        if (localFilePath == null) return;

        // OutputStream
        OutputStream fos = Files.newOutputStream(localFilePath.toPath());

        // Use FTPServerFunctions to download file
        FTPServerFunctions.downloadFile((FileItem) selectedFile.getValue(), fos);

        // Refresh the table
        initialize(null, null);
    }

    public void handleFileAdmin() throws IOException, SQLException {
        if(FTPServerFunctions.isUserAdmin())
            FXMLSceneController.createPopUp("FTPAdmin.fxml", "Admin");
        else {

            /**
            * front end, add a popup error message here.
            * Make it so the admin button is separate and doesn't show up if they're not actually an admin
            * call the ftpserverfunctions.isuseradmin to return boolean
             */
        }

    }

    public void handleFileLogout() throws IOException
    {
        FXMLSceneController.swapScene("LoginPage");
        FTPServerFunctions.clearUsername();
    }

    public void handleShareButton() throws IOException
    {
        // Whatever the user has selected
        TreeItem<Item> selectedFile = ftable.getSelectionModel().getSelectedItem();

        if (selectedFile == null) return; //check null
        if (!(selectedFile.getValue() instanceof FileItem)) return; //check that file is selected and not folder

        // Get selected file from tree
        FTPServerFunctions.setFile((FileItem) selectedFile.getValue());
        FXMLSceneController.createPopUp("FTPShare.fxml", "Share");
    }
}


