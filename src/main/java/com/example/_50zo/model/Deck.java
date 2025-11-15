package com.example._50zo.model;

import com.example._50zo.model.exceptions.EmptyDeck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * Deck represents the pile of cards used in the Cincuentazo game.
 *
 * Internally it uses a Stack of Card objects.
 * The class provides basic operations like shuffle, drawCard, addCards and check empty.
 *
 * This implementation uses only basic Java collections and stack operations
 * so it matches the data structures and concepts shown in class.
 *
 */
public class Deck {

    private Stack<Card> cards;

    /**
     * Creates an empty Deck.
     */
    public Deck() {
        cards = new Stack<>();
    }

    /**
     * Shuffles the deck of cards.
     *
     * Implementation: convert the stack to a list, shuffle using Collections.
     * then rebuild the stack so the order is randomized.
     */
    public void shuffle() {
        List<Card> list = new ArrayList<>();

        while (!cards.isEmpty()) {
            list.add(cards.pop());
        }

        Collections.shuffle(list);

        for (Card c : list) {
            cards.push(c);
        }
    }

    /**
     * Draws (removes and returns) the top card from the deck.
     *
     * @return the top Card from the deck
     * @throws EmptyDeck if the deck has no cards
     */
    public Card drawCard() throws EmptyDeck {
        if (cards.isEmpty()) {
            throw new EmptyDeck("El mazo esta vacío. No hay mas cartas para tomar.");
        }
        return cards.pop();
    }

    /**
     * Adds a list of cards to the bottom of the deck.
     *
     * Implementation detail: to add them to the bottom while preserving order,
     * we create a temporary list: current stack -> list, then append new cards at end,
     * then rebuild the stack so the first element of the combined list ends at bottom.
     *
     * @param newCards list of cards to add (can be empty or null)
     */
    public void addCards(List<Card> newCards) {
        if (newCards == null || newCards.isEmpty()) {
            return;
        }


        List<Card> combined = new ArrayList<>();


        for (Card c : cards) {
            combined.add(c);
        }


        for (Card c : newCards) {
            combined.add(c);
        }


        cards.clear();
        for (Card c : combined) {
            cards.push(c);
        }
    }

    /**
     * Checks if the deck is empty.
     *
     * @return true if there are no cards, false otherwise
     */
    public boolean isEmpty() {
        return cards.isEmpty();
    }

    /**
     * Returns the number of cards currently in the deck.
     *
     * @return deck size
     */
    public int size() {
        return cards.size();
    }

    /**
     * Utility to fill the deck with a standard 52-card set if needed.
     * This method is optional and simple: it uses the typical ranks and suits.
     * Use only if you prefer to auto-create the deck from code.
     */
    public void initializeStandard52Deck() {
        cards.clear();
        String[] suits = {"Hearts", "Clubs", "Diamonds", "Spades"};
        String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

        for (String suit : suits) {
            for (int i = 0; i < ranks.length; i++) {
                String rank = ranks[i];
                int value;

                if ("A".equals(rank)) {
                    value = 1;
                } else if ("J".equals(rank) || "Q".equals(rank) || "K".equals(rank)) {
                    value = -10;
                } else if ("9".equals(rank)) {
                    value = 0;
                } else {

                    try {
                        value = Integer.parseInt(rank);
                    } catch (NumberFormatException e) {
                        value = 0;
                    }
                }

                String folder = suit.toLowerCase();
                Card card = new Card(rank, suit, value, "");
                card.setImagePath("/Cartas/" + folder + "/" + rank + ".png");
                cards.push(card);
            }
        }
    }


    /**
     * Returns the {@code Stack} object that contains the cards in the deck.
     *
     */

    public Stack<Card> getCards() {
        return cards;
    }
}