package com.example._50zo.controller;

import com.example._50zo.model.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import com.example._50zo.view.StartStage;



import java.io.IOException;
import java.io.InputStream;
import java.util.*;


public class GameController {

    @FXML
    private TextArea gameMessages;

    @FXML
    private GridPane machine1GridPane;

    @FXML
    private GridPane machine2GridPane;

    @FXML
    private GridPane machine3GridPane;

    @FXML
    private ImageView deckofCards;

    @FXML
    private GridPane playerGridPane;

    @FXML

    private ImageView tableImageView;

    @FXML
    private Label tableSum;

    private int numPlayers;

    private Game game;

    private Deck deck;

    private Random random;


    private boolean mustDrawCard = false;

    private Thread machinePlayer;

    @FXML
    public void initialize() throws IOException {
        setNumPlayers(askNumberOfPlayers());
        initVariables();
        StartStage.deleteInstance();
        gameMessages.setStyle("-fx-text-alignment: center; -fx-alignment: center;");
    }

    public void initVariables() {
        this.game = new Game(numPlayers);
        game.setController(this);
        int intTableSum = Integer.parseInt(tableSum.getText());
        intTableSum = game.getTableSum();
        this.game.initializeGame();
        printCardsHumanPlayer();
        printCardsMachinePlayers();
        playFirstCard();



    }


    @FXML
    void handleTakeCard(MouseEvent event) {
        try {
            Player humanplayer = game.getPlayers().get(0);

            if (!mustDrawCard) {
                showMessage("Primero debes jugar una carta antes de tomar del mazo.");
                return;
            }

            if (!game.getDeck().isEmpty()) {
                Card newCard = game.getDeck().drawCard();
                humanplayer.addCard(newCard);
                printCardsHumanPlayer();
                showMessage("Has tomado una nueva carta: " + newCard);

                mustDrawCard = false;
            } else {
                showMessage("El mazo está vacío. No hay más cartas para tomar.");
            }

        } catch (Exception e) {
            showMessage("Error al tomar una carta: " + e.getMessage());
        }
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
                showMessage("Cargando imagen: " + card.getImagePath());
                Image cardImage = new Image(getClass().getResourceAsStream(card.getImagePath()));
                ImageView imageView = new ImageView(cardImage);
                imageView.setFitWidth(80);
                imageView.setFitHeight(90);
                imageView.setPreserveRatio(true);

                playerGridPane.add(imageView, col++, 0);

                imageView.setOnMouseClicked(e -> {
                    if (mustDrawCard) {
                        showMessage("Debes tomar una carta del mazo antes de jugar otra.");
                    } else {
                        humanPlayer.removeCard(card);
                        updateTableSumandView(card);
                        printCardsHumanPlayer();

                        mustDrawCard = true;

                        if (!humanPlayer.canPlay(game.getTableSum())) {
                            showMessage("No puedes jugar más cartas. Has sido eliminado.");
                            game.eliminatePlayer(humanPlayer);

                            if (!game.isGameOver()) {
                                new Thread(() -> continueMachinesAfterHumanLost()).start();
                            }
                        } else {
                            new Thread(() -> playMachinesTurn()).start();
                        }
                    }
                });


            } catch (Exception e) {
                showMessage("No se pudo cargar la imagen de la carta " + card.getImagePath());
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

    public void updateTableSumandView(Card card) {
        Platform.runLater(() -> {
            int currentSum = Integer.parseInt(tableSum.getText());
            int valueToSum = card.getGameValue(currentSum);
            currentSum += valueToSum;
            game.setTableSum(currentSum);
            tableSum.setText(String.valueOf(currentSum));
            tableImageView.setImage(new Image(getClass().getResourceAsStream(card.getImagePath())));
            showMessage("Nueva suma en la mesa: " + currentSum);

            if (currentSum > 50) {
                Player human = game.getPlayers().get(0);
                showMessage("Te pasaste de 50. Has sido eliminado.");
                game.eliminatePlayer(human);

                new Thread(() -> continueMachinesAfterHumanLost()).start();
            }
        });
    }

    private void playFirstCard(){
        deck = game.getDeck();
        Random random = new Random();
        Stack<Card> cards = deck.getCards();
        Card randomCard = cards.pop();
        updateTableSumandView(randomCard);
    }

    private void playMachinesTurn() {
        List<Player> currentPlayers = new ArrayList<>(game.getPlayers());

        for (Player player : currentPlayers) {
            if (player instanceof MachinePlayer && !player.isEliminated()) {
                String name = player.getName();

                if ((name.equals("maquina 1") && machine1GridPane != null)
                        || (name.equals("maquina 2") && machine2GridPane != null)
                        || (name.equals("maquina 3") && machine3GridPane != null)) {
                    game.playTurn(player);
                }
            }
        }

        game.removeEliminatedPlayers();
    }


    private void continueMachinesAfterHumanLost() {
        showMessage("El jugador humano ha sido eliminado. Las máquinas seguirán jugando...");

        while (!game.isGameOver()) {
            List<Player> currentPlayers = new ArrayList<>(game.getPlayers());

            for (Player player : currentPlayers) {
                if (player instanceof MachinePlayer && !player.isEliminated()) {
                    game.playTurn(player);

                    try {
                        Thread.sleep((int)(Math.random() * 2000) + 2000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }

            game.removeEliminatedPlayers();
        }
    }
    /**
     * Handles the end of the game by closing the current GameStage
     * and showing the EndStage with the winner's information.
     *
     * @param winnerName the name of the winner
     * @param playerWon true if the human player won, false if a machine won
     */
    public void onGameEnded(String winnerName, boolean playerWon) {
        Platform.runLater(() -> {
            try {
                com.example._50zo.view.EndStage endStage = new com.example._50zo.view.EndStage();
                endStage.getController().setWinnerName(winnerName);

                if (StartStage.getInstance() != null) {
                    StartStage.deleteInstance();
                }

                javafx.stage.Stage currentStage = (javafx.stage.Stage) tableImageView.getScene().getWindow();
                currentStage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Displays a message in the game's text area safely from any thread.
     * Ensures UI updates happen on the JavaFX Application Thread.
     *
     * @param message The message to display
     */
    public void showMessage(String message) {
        javafx.application.Platform.runLater(() -> {
            gameMessages.setText(message);
        });
    }

}
