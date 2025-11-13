package com.example._50zo.model;

import com.example._50zo.model.exceptions.NonPlayableCard;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class that represents a player in the Cincuentazo game.
 *
 * Each player has a name, a list of cards (their hand),
 * and a flag that indicates whether the player has been eliminated.
 *
 * This class defines common methods for both human and machine players.
 *
 */
public abstract class Player {

    protected String name;
    protected List<Card> hand;
    protected boolean eliminated;

    /**
     * Creates a player with a given name and an empty hand.
     * @param name the player's name
     */
    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.eliminated = false;
    }

    /**
     * Adds a card to the player's hand.
     * @param card the card to add
     */
    public void addCard(Card card) {
        if (card != null) {
            hand.add(card);
        }
    }

    /**
     * Removes a specific card from the player's hand.
     * @param card the card to remove
     */
    public void removeCard(Card card) {
        hand.remove(card);
    }

    /**
     * Checks if the player has at least one playable card
     * according to the current sum on the table.
     * @param tableSum current total sum of the table
     * @return true if the player can play, false otherwise
     */
    public boolean canPlay(int tableSum) {
        for (Card c : hand) {
            int value = c.getGameValue(tableSum);
            if (tableSum + value <= 20) {
                return true;
            }
        }
        return false;
    }

    /**
     * Plays a card. The specific behavior is defined
     * in subclasses (HumanPlayer or MachinePlayer).
     * @param tableSum current table sum
     * @return the card played
     * @throws NonPlayableCard if the player has no valid card to play
     */
    public abstract Card playCard(int tableSum) throws NonPlayableCard;

    /**
     * Marks the player as eliminated.
     */
    public void eliminate() {
        this.eliminated = true;
    }

    /**
     * Checks if the player is eliminated.
     * @return true if eliminated, false otherwise
     */
    public boolean isEliminated() {
        return eliminated;
    }

    /**
     * Returns the player's hand (list of cards).
     * @return the player's current hand
     */
    public List<Card> getHand() {
        return hand;
    }

    /**
     * Returns the player's name.
     * @return player's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns a string with the player's information.
     * @return name and number of cards
     */
    @Override
    public String toString() {
        return name + " - Cartas en mano: " + hand.size();
    }
}