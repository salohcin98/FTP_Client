package fxmlControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class FTPError extends Stage {

    @FXML
    private Button okButton;
    @FXML
    private Label errorMessage;

    public FTPError(String message) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FTPError.fxml"));
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        errorMessage.setText(message);

        okButton.setOnAction(event -> close());

        // Set the stage properties
        initModality(Modality.APPLICATION_MODAL);
        setTitle("Error");
        setScene(new Scene(loader.getRoot()));
    }

    public void display() {
        showAndWait();
    }
}

