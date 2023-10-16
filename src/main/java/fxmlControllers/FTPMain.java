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
        fid.setCellValueFactory(new TreeItemPropertyValueFactory<>("fid"));
        dadded.setCellValueFactory(new TreeItemPropertyValueFactory<>("dadded"));

        try {
            FTPServerFunctions.getUserFiles("nick");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

       /*FileItem item = new FileItem("test.txt","10000", "nick");

        try {
            FTPServerFunctions.uploadFileInfo(item);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1555050);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1555051);
        }



        // Create some objects and add them to the table
        FileItem item0 = new FileItem("a", "b", "c", "d");
        FileItem item1 = new FileItem("b", "c", "d", "a");
        FileItem item2 = new FileItem("Folder 1", new ArrayList<FileItem>(){{
            add(item1);
            add(item0);
        }});

        FileItem item3 = new FileItem("c", "d", "a", "b");
        FileItem item4 = new FileItem("d", "a", "b", "c");
        FileItem item5 = new FileItem("Folder 2", new ArrayList<FileItem>(){{
            add(item3);
            add(item4);
        }});

        System.out.println("made it");
        */
  
        try {
            final ArrayList<FileItem> generatedList = new ArrayList<>(FTPServerFunctions.getUserFiles("Nick"));
            ftable.setRoot(generateTreeItems(new ArrayList<FileItem>(){{
                for (FileItem fileItem : generatedList) {

                }
            }}, new FileItem("Username")));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
}


