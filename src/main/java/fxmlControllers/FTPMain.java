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
//        fid.setCellValueFactory(new TreeItemPropertyValueFactory<>("fid"));
        dadded.setCellValueFactory(new TreeItemPropertyValueFactory<>("dadded"));

        try {
            FTPServerFunctions.getUserFiles("nick");
        } catch (SQLException e) {
            e.printStackTrace();
        }

  
        try {
            final ArrayList<FileItem> generatedList = new ArrayList<>(FTPServerFunctions.getUserFiles("Nick"));
            ftable.setRoot(generateTreeItems(new ArrayList<FileItem>(){{
                for (FileItem fileItem : generatedList) {

                }
            }}, new FileItem("Username")));
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

    public void handleFileAdmin() throws IOException
    {
        FXMLSceneController.createPopUp("FTPAdmin.fxml", "Admin");
    }

    public void handleFileLogout() throws IOException
    {
        FXMLSceneController.swapScene("LoginPage");
    }

    public void handleShareButton() throws IOException
    {
        FXMLSceneController.createPopUp("FTPShare.fxml", "Share");
    }
}


