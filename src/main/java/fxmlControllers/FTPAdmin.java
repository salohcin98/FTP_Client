package fxmlControllers;

import Exceptions.UserAlreadyExists;
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

    public void deleteUser()
    {
        clearFields();
    }

    public void createUser() throws UserAlreadyExists, SQLException {
        FTPServerFunctions.addUser(userField.getText(), passField.getText(), false);
        clearFields();

    }
}
