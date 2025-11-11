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

public class GameStage extends Stage {

    private final GameController gameController;



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



    public GameController getController() {
        return gameController;
    }

    private static class GameStageHolder {
        private static GameStage INSTANCE;
    }


    public static GameStage getInstance() throws IOException {
        return GameStage.GameStageHolder.INSTANCE != null ?
                GameStage.GameStageHolder.INSTANCE :
                (GameStage.GameStageHolder.INSTANCE = new GameStage());
    }

    public void deleteInstance(){
        GameStage.GameStageHolder.INSTANCE.close();
        GameStage.GameStageHolder.INSTANCE = null;
    }


}
