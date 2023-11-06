package fxmlControllers;

import Exceptions.UserAlreadyExists;

import Utility.FTPServerFunctions;
import Utility.FXMLSceneController;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;

import org.apache.commons.net.ftp.FTP;

import java.io.IOException;
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

    //deletes user from the database
    public void deleteUser() throws SQLException
    {
        FTPServerFunctions.deleteUser(userField.getText());
        clearFields();
    }

    //adds user to the database
    public void createUser() throws SQLException, IOException
    {
        Alert alert = new Alert(Alert.AlertType.ERROR, "User Already Exists", ButtonType.OK);

        try
        {
            FTPServerFunctions.addUser(userField.getText(), passField.getText());
            clearFields();
        } catch (UserAlreadyExists uae) {
            alert.showAndWait();
            clearFields();
        }
    }

}
