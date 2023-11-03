package fxmlControllers;

import Exceptions.UserAlreadyExists;

import Utility.FTPServerFunctions;
import Utility.FXMLSceneController;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
    public void createUser() throws SQLException, IOException {
        try {
            FTPServerFunctions.addUser(userField.getText(), passField.getText());
            clearFields();
        } catch (UserAlreadyExists uae) {
            FTPError ftpError = new FTPError("This user already exists, please choose a different name or log in");
            ftpError.display();
        }
    }

}
