package Utility;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Should be used in 2 places:
 * 1. Within controllers to swap scenes or create pop-ups
 * 2. when starting the app to initialize it with the init method
 */
public class FXMLSceneController {
    private static Stage primaryStage;
    private static final List<Scene> cachedScenes = new ArrayList<>();
    private static final List<String> cachedFiles = new ArrayList<>();

    /**
     * Should only be used once when the program is first started
     *
     * @param primaryStage the primary stage created by JavaFX
     * @param windowTitle the title of the app
     * @param filename the filename of the Scene to be initially loaded
     * @throws IOException file not found error
     */
    public static void init(Stage primaryStage, String windowTitle, String filename) throws IOException {
        FXMLSceneController.primaryStage = primaryStage;

        // Set the scene on the primary stage
        swapScene(filename);

        // Set the stage title
        primaryStage.setTitle(windowTitle);

        // Show the stage
        primaryStage.show();
    }

    /**
     * Swaps the scene of the primary stage with a different one.
     * Should be used to swap the current Scene from within a controller.
     *
     * @param filename filename of the scene
     * @throws IOException file not found
     */
    public static void swapScene(String filename) throws IOException {
        Scene scene = getFXMLScene(filename);
        primaryStage.setScene(scene);
    }

    /**
     * loads the file as a scene and caches it
     * helper method
     *
     * @param filename the filename of the Scene's file
     * @return {@link Scene} Object
     * @throws IOException file not found exception
     */
    private static Scene getFXMLScene(String filename) throws IOException {
        if (!filename.endsWith(".fxml"))
            filename += ".fxml";

        // check if cached
        int index;
        if ((index = cachedFiles.indexOf(filename)) != -1){
            return cachedScenes.get(index);
        }

        // if not cached, load it
        URL resourceUrl = FXMLSceneController.class.getResource("/fxml/" + filename);
        if (resourceUrl == null) {
            throw new IOException("FXML file not found: " + filename);
        }
        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(resourceUrl)));

        //cache it
        cachedScenes.add(scene);
        cachedFiles.add(filename);

        return scene;
    }

    public static void createPopUp(String filename, String windowName) throws IOException {
        // Create a new stage for the secondary window
        Stage secondaryStage = new Stage();
        secondaryStage.setTitle(windowName);

        Scene scene = getFXMLScene(filename);

        // Set the scene for the secondary stage
        secondaryStage.setScene(scene);

        // Set modality (optional) - Modality.WINDOW_MODAL, Modality.APPLICATION_MODAL, or Modality.NONE
        secondaryStage.initModality(Modality.APPLICATION_MODAL);

        // Show the secondary window
        secondaryStage.show();
    }
}
