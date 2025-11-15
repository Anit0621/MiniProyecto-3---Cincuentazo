package com.example._50zo.view;

import com.example._50zo.controller.EndController;
import com.example._50zo.controller.GameController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Represents the final window of the game (EndView).
 * This stage displays the winner and the final message.
 */
public class EndStage extends Stage {

    private final EndController endController;

    /**
     * Creates and shows the EndStage.
     *
     * @throws Exception if the FXML file cannot be loaded
     */
    public EndStage() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EndView.fxml"));
        Parent root = loader.load();
        endController = loader.getController();

        Scene scene = new Scene(root);
        setTitle("Fin del Juego");
        getIcons().add(new Image(String.valueOf(getClass().getResource("/images/favicon.png"))));
        setScene(scene);
        setResizable(false);
        show();
    }

    /**
     * Gets the controller of this stage.
     *
     * @return the EndController instance
     */
    public EndController getController() {
        return endController;
    }

    /**
     * Holder class for the {@link EndStage} singleton instance.
     * <p>
     * This class leverages the Initialization-on-demand holder idiom,
     * ensuring lazy initialization and thread-safe access to the singleton
     * without explicit synchronization.
     * </p>
     */


    private static class EndStageHolder {
        private static EndStage INSTANCE;
    }

    /**
     * Returns the singleton instance of {@link EndStage}.
     * <p>
     * If the instance does not yet exist, it is created and stored.
     * Subsequent calls return the same instance unless it has been
     * explicitly deleted via {@link #deleteInstance()}.
     * </p>
     *
     * @return the singleton instance of {@code EndStage}
     * @throws Exception if an error occurs during the initialization of the {@code EndStage} object
     */


    public static EndStage getInstance() throws Exception {
        return EndStage.EndStageHolder.INSTANCE != null ?
                EndStage.EndStageHolder.INSTANCE :
                (EndStage.EndStageHolder.INSTANCE = new EndStage());
    }

    /**
     * Disposes of the current {@link EndStage} singleton instance and resets it to {@code null}.
     * <p>
     * This method closes the active stage (via {@link EndStage#close()}), and clears
     * the stored instance so that a new one may be created on the next call to
     * {@link #getInstance()}.
     * </p>
     *
     * <p><b>Note:</b> This method should be used cautiously, as deleting the singleton
     * breaks the guarantees of a persistent singleton lifecycle and may affect other
     * components depending on the instance.</p>
     */

    public static void deleteInstance(){
        EndStage.EndStageHolder.INSTANCE.close();
        EndStage.EndStageHolder.INSTANCE = null;
    }


}




