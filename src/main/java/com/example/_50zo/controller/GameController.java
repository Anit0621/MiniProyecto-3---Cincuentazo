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
    private GridPane machine1GridPane;

    @FXML
    private GridPane machine2GridPane;

    @FXML
    private GridPane machine3GridPane;

    @FXML
    private Button deckofCards;

    @FXML
    private GridPane playerGridPane;

    @FXML

    private ImageView tableImageView;

    private int numPlayers;

    private Game game;


    private boolean mustDrawCard = false;

    private Thread machinePlayer;

    @FXML
    public void initialize() throws IOException {
        setNumPlayers(askNumberOfPlayers());
        initVariables();
        StartStage.deleteInstance();


    }


    @FXML
    void handleTakeCard(ActionEvent event) {
        try {
            Player humanplayer = game.getPlayers().get(0);

            if (!mustDrawCard) {
                System.out.println("Primero debes jugar una carta antes de tomar del mazo.");
                return;
            }

            if (!game.getDeck().isEmpty()) {
                Card newCard = game.getDeck().drawCard();
                humanplayer.addCard(newCard);
                printCardsHumanPlayer();
                System.out.println("Has tomado una nueva carta: " + newCard);

                mustDrawCard = false;
            } else {
                System.out.println("El mazo está vacío. No hay más cartas para tomar.");
            }

        } catch (Exception e) {
            System.out.println("Error al tomar una carta: " + e.getMessage());
        }
    }


    public void initVariables() {
        this.game = new Game(numPlayers);
        this.game.initializeGame();
        printCardsHumanPlayer();
        printCardsMachinePlayers();
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
                if (num < 2 || num > 4) {
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

    private void printCardsHumanPlayer() {
        playerGridPane.getChildren().clear();

        Player humanPlayer = game.getPlayers().get(0);

        int col = 0;
        for (Card card : humanPlayer.getHand()) {
            try {
                System.out.println("Cargando imagen: " + card.getImagePath());
                Image cardImage = new Image(getClass().getResourceAsStream(card.getImagePath()));
                ImageView imageView = new ImageView(cardImage);
                imageView.setFitWidth(80);
                imageView.setFitHeight(90);
                imageView.setPreserveRatio(true);

                playerGridPane.add(imageView, col++, 0);

                imageView.setOnMouseClicked(e -> {
                    try {
                        if (mustDrawCard) {
                            System.out.println("Debes tomar una carta del mazo antes de jugar otra.");
                            return;
                        }

                        humanPlayer.getHand().remove(card);
                        tableImageView.setImage(new Image(getClass().getResourceAsStream(card.getImagePath())));

                        mustDrawCard = true;

                        printCardsHumanPlayer();

                    } catch (Exception ex) {
                        System.out.println("Error al jugar la carta: " + ex.getMessage());
                    }
                });

            } catch (Exception e) {
                System.out.println("No se pudo cargar la imagen de la carta " + card.getImagePath());
            }
        }
    }

    private void printCardsMachinePlayers() {
        if (machine1GridPane != null) machine1GridPane.getChildren().clear();
        if (machine2GridPane != null) machine2GridPane.getChildren().clear();
        if (machine3GridPane != null) machine3GridPane.getChildren().clear();

        Image backImage = new Image(getClass().getResourceAsStream("/images/back.png"));

        int numMachines = numPlayers - 1;

        for (int i = 1; i <= numMachines; i++) {
            Player machine = game.getPlayers().get(i);

            for (int j = 0; j < machine.getHand().size(); j++) {
                ImageView cardBack = new ImageView(backImage);
                cardBack.setFitWidth(80);
                cardBack.setFitHeight(90);
                cardBack.setPreserveRatio(true);

                if (i == 1 && machine1GridPane != null) {
                    machine1GridPane.add(cardBack, 0, j);
                } else if (i == 2 && machine2GridPane != null) {
                    machine2GridPane.add(cardBack, j, 0);
                } else if (i == 3 && machine3GridPane != null) {
                    machine3GridPane.add(cardBack, 0, j);
                }
            }
        }
    }
}
