package fxmlControllers;

import javafx.beans.property.SimpleStringProperty;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;

import java.net.URL;

import java.util.ResourceBundle;

import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;

public class FTPShare implements Initializable
{
    @FXML
    private Button shareButton;
    @FXML
    private TreeTableColumn<String, String> userList;
    @FXML
    private TreeTableView<String> userTable;

    private TreeItem<String> userRoot = new TreeItem<>("Users");
    private String[] usersArray = {"Nick", "Chase", "Eric", "Joshua", "Juan"};


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        for (String s : usersArray)
        {
            userRoot.getChildren().add(new TreeItem<>(s));
        }


        userList.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue()));
        userTable.setRoot(userRoot);
    }
}
