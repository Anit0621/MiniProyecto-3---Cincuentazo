package com.example._50zo.model;

import com.example._50zo.model.exceptions.EmptyDeck;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class DeckTest {

    @Test
    void drawingCardWithEmptyDeckShouldBeEmpty() {
        var deck = new Deck();
        assertThrows(EmptyDeck.class,() -> {
            deck.drawCard();
        });
    }

    @Test
    void initializeStandard52DeckShouldHave52Cards() {
        var deck = new Deck();
        deck.initializeStandard52Deck();
        assertEquals(52, deck.size());
    }

    @Test
    void drawCardShouldDecreaseDeckSizeByOne() throws Exception {
        var deck = new Deck();
        deck.initializeStandard52Deck();
        var initialSize = deck.size();
        deck.drawCard();
        assertEquals(initialSize - 1, deck.size());
    }

  
}