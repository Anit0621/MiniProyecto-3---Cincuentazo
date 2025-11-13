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


    private static class EndStageHolder {
        private static EndStage INSTANCE;
    }


    public static EndStage getInstance() throws Exception {
        return EndStage.EndStageHolder.INSTANCE != null ?
                EndStage.EndStageHolder.INSTANCE :
                (EndStage.EndStageHolder.INSTANCE = new EndStage());
    }

    public static void deleteInstance(){
        EndStage.EndStageHolder.INSTANCE.close();
        EndStage.EndStageHolder.INSTANCE = null;
    }


}




