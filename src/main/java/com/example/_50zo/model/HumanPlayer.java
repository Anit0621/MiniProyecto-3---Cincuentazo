package com.example._50zo.model;

import com.example._50zo.model.exceptions.NonPlayableCard;

/**
 * Represents the human player in the Cincuentazo game.
 *
 * This class extends Player and defines how a human player
 * plays a card. The card choice will come from the GUI
 * (through user interaction in JavaFX controllers).
 *
 */
public class HumanPlayer extends Player {

    /**
     * Creates a new human player with the given name.
     * @param name the player's name
     */
    public HumanPlayer(String name) {
        super(name);
    }

    /**
     * Plays a card chosen by the human player.
     *
     * In this version, the selected card will be passed as a parameter
     * from the graphical interface (not chosen automatically).
     *
     * @param tableSum current total sum on the table
     * @return the card played
     * @throws NonPlayableCard if the player has no valid card to play
     */
    @Override
    public Card playCard(int tableSum) throws NonPlayableCard {
        if (!canPlay(tableSum)) {
            throw new NonPlayableCard();
        }

        // In the actual game, the selected card will come from the GUI.
        // For now, we just return the first playable card for simplicity.
        for (Card c : hand) {
            int value = c.getGameValue(tableSum);
            if (tableSum + value <= 50) {
                hand.remove(c);
                return c;
            }
        }

        // This line should never be reached if canPlay() works correctly
        throw new NonPlayableCard();
    }
}