package com.example._50zo.model;

import com.example._50zo.model.exceptions.EmptyDeck;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Deck} class, ensuring correct behavior for
 * deck initialization, drawing cards, and handling empty deck scenarios.
 */

class DeckTest {

    /**
     * Verifies that attempting to draw a card from an empty deck
     * results in an {@link EmptyDeck} exception.
     */

    @Test
    void drawingCardWithEmptyDeckShouldBeEmpty() {
        var deck = new Deck();
        assertThrows(EmptyDeck.class,() -> {
            deck.drawCard();
        });
    }

    /**
     * Ensures that initializing a standard 52-card deck correctly
     * populates the deck with exactly 52 cards.
     */

    @Test
    void initializeStandard52DeckShouldHave52Cards() {
        var deck = new Deck();
        deck.initializeStandard52Deck();
        assertEquals(52, deck.size());
    }

    /**
     * Checks that drawing a card from a non-empty deck decreases its size by one.
     *
     * @throws Exception if drawing a card triggers an unexpected exception.
     */

    @Test
    void drawCardShouldDecreaseDeckSizeByOne() throws Exception {
        var deck = new Deck();
        deck.initializeStandard52Deck();
        var initialSize = deck.size();
        deck.drawCard();
        assertEquals(initialSize - 1, deck.size());
    }

  
}