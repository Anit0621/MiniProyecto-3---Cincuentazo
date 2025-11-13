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

}