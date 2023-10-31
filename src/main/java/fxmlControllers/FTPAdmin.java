package fxmlControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class FTPAdmin
{
    @FXML
    private Button userCreate;
    @FXML
    private Button userDelete;
    @FXML
    private TextField userField;

    public void createUser()
    {
        userField.setText("");
    }

    public void deleteUser()
    {
        userField.setText("");
    }
}
