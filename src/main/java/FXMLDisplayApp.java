import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Objects;

public class FXMLDisplayApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/ftpmain.fxml")));

        // Create a scene with the loaded FXML content
        Scene scene = new Scene(root, 600, 400); // Set width and height as needed

        // Set the scene on the primary stage
        primaryStage.setScene(scene);

        // Set the stage title
        primaryStage.setTitle("FXML Display Example");

        // Show the stage
        primaryStage.show();

        // some db testing
        Database db = new Database();
        System.out.println(db.login("chase", "chase")); //should print username of user if successful login

        //some ftp testing
        Ftp ftp = new Ftp();
        ftp.test();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
