package com.example._50zo.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    @Test
    void nineShouldBeZero() {
        var card = new Card("9", "Hearts", 0, null);
        var result = card.getGameValue(25);
        assertEquals(0, result);
    }

    @Test
    void faceCardsShouldBeMinus10() {
        var jack = new Card("J", "Clubs", -10, null);
        var queen = new Card("Q", "Diamonds", -10, null);
        var king = new Card("K", "Spades", -10, null);

        assertEquals(-10, jack.getGameValue(30));
        assertEquals(-10, queen.getGameValue(30));
        assertEquals(-10, king.getGameValue(30));
    }

    @Test
    void numberCardsShouldReturnTheirValue() {
        var card3 = new Card("3", "Spades", 3, null);
        var card7 = new Card("7", "Clubs", 7, null);
        var card10 = new Card("10", "Hearts", 10, null);

        assertEquals(3, card3.getGameValue(10));
        assertEquals(7, card7.getGameValue(10));
        assertEquals(10, card10.getGameValue(10));
    }
}