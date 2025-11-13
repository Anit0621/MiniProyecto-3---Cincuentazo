package com.example._50zo.controller;

import com.example._50zo.model.Game;
import com.example._50zo.view.StartStage;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import com.example._50zo.view.GameStage;

import java.io.IOException;

/**
 * Controller for the EndView scene.
 * This class handles the final screen displayed when the game ends.
 * It shows the winner's name and a corresponding message depending on
 * whether the human player or a machine won the game.
 *
 */
public class EndController {

    @FXML
    private Label labelWinner;

    @FXML
    private Label labelFinalVictory;

    @FXML

    private Label labelFinalDefeat;



    public void initialize() throws IOException {
        GameStage.deleteInstance();
    }

    /**
     * Sets the winner name and updates the final message accordingly.
     * @param winnerName the name of the player who won
     */
    public void setWinnerName(String winnerName) {
        labelWinner.setText(winnerName);

        if (winnerName.toLowerCase().contains("maquina")) {
            labelFinalDefeat.setText("Suerte para la proxima");
            labelFinalVictory.setVisible(false);
        } else {
            labelFinalVictory.setText("¡Felicidades!");
            labelFinalDefeat.setVisible(false);
        }
    }
}
