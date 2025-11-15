package com.example._50zo.view;

import com.example._50zo.controller.GameController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;


/**
 * Represents the main stage for the active game session.
 * <p>
 * This class is responsible for loading the game view from the corresponding FXML file,
 * initializing its controller, and displaying the main game window. It also implements
 * a lazy-loaded Singleton pattern to ensure that only one {@code GameStage} instance
 * exists at any time.
 * </p>
 */

public class GameStage extends Stage {

    /** The controller associated with the loaded game view.
     *
     * */

    private final GameController gameController;

    /**
     * Creates a new instance of {@code GameStage}.
     * <p>
     * This constructor loads the {@code GameView.fxml} file, retrieves its controller,
     * sets up the application icon, scene, window properties, and displays the stage.
     * </p>
     *
     * @throws IOException if the FXML file or associated resources cannot be loaded.
     */



    public GameStage() throws IOException {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GameView.fxml"));
                Parent root = loader.load();
                gameController = loader.getController();
                Scene scene = new Scene(root);


                getIcons().add(new Image(String.valueOf(getClass().getResource("/images/favicon.png"))));
                setScene(scene);
                setResizable(false);
                show();
    }


    /**
     * Returns the controller associated with this game stage.
     *
     * @return the {@link GameController} instance.
     */



    public GameController getController() {
        return gameController;
    }

    /**
     * Holder class for lazy-loaded singleton instance of {@code GameStage}.
     * <p>
     * This follows the Initialization-on-demand holder idiom, ensuring
     * thread-safe lazy initialization without requiring synchronization.
     * </p>
     */

    private static class GameStageHolder {
        private static GameStage INSTANCE;
    }


    /**
     * Returns the singleton instance of {@code GameStage}.
     * <p>
     * If the instance does not already exist, it is created when this method
     * is first invoked. Subsequent calls return the same instance unless it has
     * been explicitly deleted via {@link #deleteInstance()}.
     * </p>
     *
     * @return the {@code GameStage} singleton instance.
     * @throws IOException if the stage cannot be initialized.
     */


    public static GameStage getInstance() throws IOException {
        return GameStage.GameStageHolder.INSTANCE != null ?
                GameStage.GameStageHolder.INSTANCE :
                (GameStage.GameStageHolder.INSTANCE = new GameStage());
    }

    /**
     * Deletes the current singleton instance of {@code GameStage}.
     * <p>
     * This method closes the stage and clears the stored instance reference,
     * allowing a fresh instance to be created the next time {@link #getInstance()}
     * is called.
     * </p>
     * <p><b>Warning:</b> Call this method only when you are certain the current
     * game stage should be fully disposed, as it resets the singleton lifecycle.</p>
     */

    public static void deleteInstance(){
        GameStage.GameStageHolder.INSTANCE.close();
        GameStage.GameStageHolder.INSTANCE = null;
    }


}
