package fxmlControllers;

import Utility.FTPServerFunctions;

import javafx.beans.property.SimpleStringProperty;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;

import java.net.URL;

import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import lombok.SneakyThrows;

public class FTPShare implements Initializable
{
    @FXML
    private Button shareButton;
    @FXML
    private TreeTableColumn<String, String> userList;
    @FXML
    private TreeTableView<String> userTable;

    private final TreeItem<String> userRoot = new TreeItem<>("Users");


    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        ArrayList<String> usersArray = FTPServerFunctions.getallUsers();
        for (String s : usersArray)
        {
            userRoot.getChildren().add(new TreeItem<>(s));
        }
        userList.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue()));
        userTable.setRoot(userRoot);
    }

    public void shareFile()
    {

    }
}
