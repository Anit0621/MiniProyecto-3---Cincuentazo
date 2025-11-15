package com.example._50zo.controller;

import com.example._50zo.model.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.example._50zo.view.GameStage;
import com.example._50zo.model.Game;



import java.io.IOException;
import java.util.Optional;


/**
 * Controller responsible for managing the start screen of the game.
 * <p>
 * This controller handles player name input, initializes a new game instance,
 * and displays informational dialogs such as game rules. It serves as the
 * entry point before transitioning to the main game interface.
 * </p>
 *
 * @see Game
 * @see GameStage
 */

public class StartController {

    @FXML
    private Button helpBtn;

    @FXML
    private Button startButton;

    @FXML
    private TextField nameField;

    /**
     * The number of players participating in the game.
     * This value is set externally before starting the game.
     */

    public int numPlayers;

    /**
     * Reference to the game instance that will be initialized
     * once the user starts the game.
     */

    public Game game;

    /**
     * Starts the game when the user presses the Start button.
     * <p>
     * If the player name field is empty, a warning dialog is shown.
     * Otherwise, a new {@code Game} instance is created with the
     * selected number of players, the human player's name is set,
     * and the main game stage is displayed.
     * </p>
     *
     * @param event the action event triggered by the Start button.
     * @throws IOException if the game stage fails to load.
     * @see GameStage
     * @see Game
     */

    @FXML
    void startGame(ActionEvent event) throws IOException {

        if (nameField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Acción inválida");
            alert.setHeaderText("¡No ingresaste nombre!");
            alert.setResizable(false);
            DialogPane dialogpane = alert.getDialogPane();
            dialogpane.setPrefSize(400, 200);
            dialogpane.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
            dialogpane.getStyleClass().add("warning-label");
            alert.showAndWait();
        } else {
            String playerName = nameField.getText();

            game = new Game(numPlayers);
            game.setHumanName(playerName);

            GameStage gameStage = GameStage.getInstance();
            gameStage.show();
        }
    }

    /**
     * Displays an informational dialog containing the complete
     * rules of the game when the Help button is pressed.
     *
     * @param event the action event triggered by the Help button.
     */

    @FXML
    void HelpBtnPressed(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información importante");
        alert.setHeaderText("REGLAS DEL JUEGO");
        alert.setResizable(true);
        DialogPane dialogPane = alert.getDialogPane();
        TextArea textArea = new TextArea("Las reglas del juego son las siguientes:\n1. Cada jugador siempre ha de tener 4" +
                " cartas en su mazo\n2. Después de jugar una carta, el jugador siempre debe tomar una carta del mazo\n3. Las cartas" +
                " que se encuentran sobre la mesa no pueden exceder la suma de 50; los valores para las cartas son los siguientes:" +
                "\n•Todas las cartas con números del 2 al 8 y el 10 suman su número. \n•Todas las cartas con número 9 ni suman ni restan." +
                "\n•Todas las cartas con letras J, Q, K restan 10. \n•Todas las cartas con letra A suman 1 o 10, según convenga. " +
                "\n4. Si un jugador no puede jugar ninguna de sus cartas sin exceder la suma de 50, será eliminado \n5. Gana aquel quien sea el ultimo" +
                " jugador en juego. ");
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setPrefSize(540, 500);
        dialogPane.setContent(textArea);
        dialogPane.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        dialogPane.getStyleClass().add("information-label");
        alert.getDialogPane().setPrefSize(640, 480);
        alert.showAndWait();

    }

}


