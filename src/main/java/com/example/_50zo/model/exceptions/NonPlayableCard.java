package com.example._50zo.model.exceptions;

/**
 * Exception thrown when a player cannot play any valid card
 * without exceeding the maximum table sum of 50.
 *
 * This is a custom checked exception used in the Cincuentazo game.
 * The message is written in Spanish because it is part of the game dialogue.
 *
 */
public class NonPlayableCard extends Exception {

    /**
     * Creates a NonPlayableCard exception with a default message in Spanish.
     */
    public NonPlayableCard() {
        super("No tienes cartas válidas para jugar sin pasarte de 50.");
    }

}