package com.example._50zo.model;

import com.example._50zo.model.exceptions.NonPlayableCard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit tests for the {@link MachinePlayer} class, focusing on validating
 * its decision-making logic when selecting and playing cards.
 */


class MachinePlayerTest {

    /**
     * Ensures that the machine player throws a {@link NonPlayableCard} exception
     * when attempting to play a card that would exceed the allowed table sum limit.
     */

    @Test
    void PlayingInvalidCardShouldThrowNonPlayableCard (){
        var tableSum = 49;
        var machineplayer = new MachinePlayer("Maquina de prueba",null);
        machineplayer.getHand().add(new Card("Ace","Spades",10,null));
        assertThrows(NonPlayableCard.class, () -> {
            machineplayer.playCard(40);
        });


    }

    /**
     * Verifies that the machine player correctly selects and plays a valid card
     * when one is available in its hand.
     *
     * @throws Exception if an unexpected error occurs during card selection.
     */

    @Test
    void MachineShouldPlayAValidCard() throws Exception {
        var machine = new MachinePlayer("Maquina Test", null);
        machine.getHand().add(new Card("5", "Hearts", 5, null)); // 5 siempre es jugable si suma < 20
        var played = machine.playCard(0);
        assertEquals("5", played.getName());
    }

    /**
     * Confirms that after the machine player plays a card, that card
     * is properly removed from its hand.
     *
     * @throws Exception if an unexpected error occurs during card playing.
     */

    @Test
    void MachineShouldRemovePlayedCardFromHand() throws Exception {
        var machine = new MachinePlayer("Maquina Test", null);
        var card = new Card("7", "Clubs", 7, null);
        machine.getHand().add(card);
        machine.playCard(0);
        assertFalse(machine.getHand().contains(card));
    }

}