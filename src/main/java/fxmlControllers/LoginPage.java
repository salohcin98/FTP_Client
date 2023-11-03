package fxmlControllers;

import Connections.FTPConnection;
import Utility.FTPServerFunctions;
import Utility.FXMLSceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginPage {

    @FXML
    public TextField usernameInput;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private Label errorLabel; // The Label for displaying the error message

    @FXML
    private void handleLogin() throws Exception {
        FTPServerFunctions.ftpClient = null;
        // Get the values from the username and password fields
        String username = usernameInput.getText();
        String password = passwordInput.getText();


        // try logging in and check if it failed
        if (!FTPConnection.connect(username, password)){
            errorLabel.setVisible(true);
            passwordInput.setText(""); // clear input field
        }
        else{
            FXMLSceneController.swapScene("FTPMain");
        }
        usernameInput.setText("");
        passwordInput.setText("");
    }
    @FXML
    private void handleAccountCreate() throws Exception {
        FTPServerFunctions.ftpClient = null;
        // Get the values from the username and password fields
        String username = usernameInput.getText();
        String password = passwordInput.getText();
        FTPServerFunctions.addUser(username, password);

        // try logging in and check if it failed
        if (!FTPConnection.connect(username, password)){
            errorLabel.setVisible(true);
            passwordInput.setText(""); // clear input field
        }
        else{
            FXMLSceneController.swapScene("FTPMain");
        }
        usernameInput.setText("");
        passwordInput.setText("");
    }
}
