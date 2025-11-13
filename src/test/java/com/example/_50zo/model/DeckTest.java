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
  
}