package fxmlControllers;

import Utility.FTPServerFunctions;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class FTPAdmin
{
    @FXML
    private Button userCreate;
    @FXML
    private Button userDelete;
    @FXML
    private TextField userField;
    @FXML
    private TextField passField;

    public void clearFields() {userField.setText(""); passField.setText("");}

    public void deleteUser() throws SQLException
    {
        FTPServerFunctions.deleteUser(userField.getText());
        clearFields();
    }

    public void createUser() throws SQLException
    {
        FTPServerFunctions.addUser(userField.getText(), passField.getText());
        clearFields();
    }
}
