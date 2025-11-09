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

public class StartStage extends Stage {

    private final StartController startController;

    public StartStage() throws IOException{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/StartView.fxml"));
        Parent root;
       root = loader.load();
        startController = loader.getController();
        Scene scene = new Scene(root);
        setTitle("Welcome");

        getIcons().add(new Image(String.valueOf(getClass().getResource("/icons/favicon.png"))));
        setScene(scene);
        setResizable(false);
        show();
    }

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




