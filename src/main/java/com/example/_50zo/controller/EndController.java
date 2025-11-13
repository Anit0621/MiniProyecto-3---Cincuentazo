package com.example._50zo.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

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
    private Label labelFinal;

    /**
     * Sets the winner name and updates the final message accordingly.
     * @param winnerName the name of the player who won
     */
    public void setWinnerName(String winnerName) {
        labelWinner.setText(winnerName);

        if (winnerName.toLowerCase().contains("maquina")) {
            labelFinal.setText("Suerte para la proxima");
        } else {
            labelFinal.setText("¡Felicidades!");
        }
    }
}
