package com.example._50zo.model.exceptions;

/**
 * Exception thrown when trying to draw a card from an empty deck.
 *
 * This class represents a custom checked exception used in the Cincuentazo game.
 * The message should be shown in Spanish since it is part of the game dialogue.
 *
 */
public class EmptyDeck extends Exception {

    /**
     * Creates a new EmptyDeck exception with a custom message.
     * @param message specific message
     */
    public EmptyDeck(String message) {
        super(message);
    }

}