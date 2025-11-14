package com.example._50zo.model;

import com.example._50zo.model.exceptions.NonPlayableCard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MachinePlayerTest {

    @Test
    void PlayingInvalidCardShouldThrowNonPlayableCard (){
        var tableSum = 49;
        var machineplayer = new MachinePlayer("Maquina de prueba",null);
        machineplayer.getHand().add(new Card("Ace","Spades",10,null));
        assertThrows(NonPlayableCard.class, () -> {
            machineplayer.playCard(40);
        });


    }

    @Test
    void MachineShouldPlayAValidCard() throws Exception {
        var machine = new MachinePlayer("Maquina Test", null);
        machine.getHand().add(new Card("5", "Hearts", 5, null)); // 5 siempre es jugable si suma < 20
        var played = machine.playCard(0);
        assertEquals("5", played.getName());
    }

    @Test
    void MachineShouldRemovePlayedCardFromHand() throws Exception {
        var machine = new MachinePlayer("Maquina Test", null);
        var card = new Card("7", "Clubs", 7, null);
        machine.getHand().add(card);
        machine.playCard(0);
        assertFalse(machine.getHand().contains(card));
    }

}