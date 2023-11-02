package fxmlControllers;

import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;

import javafx.fxml.FXML;

public class FTPShare
{
    @FXML
    private Button shareButton;
    @FXML
    private TreeTableColumn<String[], String> userList;
    @FXML
    private TreeItem<String> users;

    public void populateTree(String[] userList)
    {

    }
}
