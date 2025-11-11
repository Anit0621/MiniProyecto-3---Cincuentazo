package com.example._50zo.controller;

import com.example._50zo.model.*;
import com.example._50zo.view.GameStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import com.example._50zo.view.StartStage;



import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


public class GameController {

    @FXML
    private Button deckofCards;

    @FXML
    private GridPane playerGridPane;

    @FXML

    private ImageView tableImageView;

    private int numPlayers;

    private Game game;

    private Deck deck;

    private Thread machinePlayer;

    @FXML
    public void initialize() throws IOException {
        setNumPlayers(askNumberOfPlayers());
        initVariables();
        StartStage.deleteInstance();


    }


    @FXML
    void handleTakeCard(ActionEvent event) {

    }



    public void initVariables() {
        this.game = new Game(numPlayers);
        this.game.initializeGame();
        printCardsHumanPlayer();
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    private int askNumberOfPlayers() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Número de jugadores");
        dialog.setHeaderText("Configura la partida");
        dialog.setContentText("¿Cuántos jugadores participarán?");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            try {
                int num = Integer.parseInt(result.get());
                if (num < 2 || num > 4){
                    System.out.println(num);
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Acción inválida");
                    alert.setHeaderText("Numero de jugadores invalido");
                    DialogPane dialogPane = alert.getDialogPane();
                    dialogPane.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
                    dialogPane.getStyleClass().add("warning-label");
                    alert.showAndWait();

                    return askNumberOfPlayers();

                }
                return num;


            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Acción inválida");
                alert.setHeaderText("Debe ingresar un número");
                alert.showAndWait();
                return askNumberOfPlayers();

            }
        }
        return 1;
    }

    private void printCardsHumanPlayer(){
        this.playerGridPane.getChildren().clear();
        Player humanplayer = game.getPlayers().get(0);
        int col = 0;
        for (Card card : humanplayer.getHand()){
            try {
                System.out.println("Loading image: " + card.getImagePath());
                System.out.println(getClass().getResource(card.getImagePath()));
                Image cardImage = new Image(getClass().getResourceAsStream(card.getImagePath()));
                ImageView imageView = new ImageView(cardImage);
                imageView.setFitWidth(80);
                imageView.setFitHeight(90);
                imageView.setPreserveRatio(true);
                playerGridPane.add(imageView, col++, 0);
            } catch (Exception e) {
                System.out.println("No se pudo cargar la imagen de la carta " + card.getImagePath());
            }
        }


    }

    private void printCardsMachinePlayer(){
        this.playerGridPane.getChildren().clear();
    }
}
