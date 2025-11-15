package com.example._50zo.view;
import com.example._50zo.controller.StartController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOError;
import java.io.IOException;


/**
 * Represents the initial stage of the application where the user is welcomed
 * and prompted to enter their information before starting the game.
 * <p>
 * This class is responsible for loading the {@code StartView.fxml} layout,
 * initializing its associated controller, configuring the window properties,
 * and displaying the start screen. It acts as the entry point of the UI workflow
 * before transitioning to the actual game stage.
 * </p>
 */

public class StartStage extends Stage {


    /**
     * The controller associated with the start view.
     * */
    private final StartController startController;

    /**
     * Creates a new instance of {@code StartStage}.
     * <p>
     * This constructor loads the FXML file for the start view, retrieves its
     * controller, sets the scene, configures window properties such as the title
     * and application icon, and displays the stage.
     * </p>
     *
     * @throws IOException if the FXML file or any associated resource fails to load.
     */

    public StartStage() throws IOException{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/StartView.fxml"));
        Parent root;
       root = loader.load();
        startController = loader.getController();
        Scene scene = new Scene(root);
        setTitle("Bienvenido");

        getIcons().add(new Image(String.valueOf(getClass().getResource("/images/favicon.png"))));
        setScene(scene);
        setResizable(false);
        show();
    }

    /**
     * Returns the controller associated with this start stage.
     *
     * @return the {@link StartController} instance.
     */


    public StartController getController(){
        return startController;
    }

    /**
     * Closes the instance of StartStage.
     * This method is used to clean up resources when the game stage is no longer needed.
     */

    public static void deleteInstance() {
        StartStageHolder.INSTANCE.close();
        StartStageHolder.INSTANCE = null;
    }

    /**
     * Retrieves the singleton instance of StartStage.
     *
     * @return the singleton instance of StartStage.
     * @throws IOException if an error occurs while creating the instance.
     */
    public static StartStage getInstance() throws IOException {
        return StartStageHolder.INSTANCE != null ?
                StartStageHolder.INSTANCE :
                (StartStageHolder.INSTANCE = new StartStage());
    }

    /**
     * Holder class for the singleton instance of StartStage.
     * This class ensures lazy initialization of the singleton instance.
     */
    private static class StartStageHolder {
        private static StartStage INSTANCE;
    }
}




