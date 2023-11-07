package fxmlControllers;

import Exceptions.UserAlreadyExists;

import Utility.FTPServerFunctions;
import Utility.FXMLSceneController;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

import org.apache.commons.net.ftp.FTP;

import java.io.IOException;
import java.sql.SQLException;

public class FTPAdmin
{
//    @FXML
//    private Button userCreate;
//    @FXML
//    private Button userDelete;
    @FXML
    private TextField userField;
    @FXML
    private TextField passField;
    @FXML
    private Label errorLabel;

    public void clearFields() {userField.setText(""); passField.setText("");}

    //deletes user from the database
    public void deleteUser()
    {
        try{
            FTPServerFunctions.deleteUser(userField.getText());
            clearFields();
        }catch(SQLException e){
            showError("");
        }
    }

    //adds user to the database
    public void createUser()
    {
        try
        {
            FTPServerFunctions.addUser(userField.getText(), passField.getText(), false);
            clearFields();
        } catch (UserAlreadyExists e) {
            showError("Account Already Exists!");
            clearFields();
        } catch (SQLException e){
            showError("Username or password contains invalid characters!");
            clearFields();
        }

    }

    private void showError(String message)
    {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }

}
