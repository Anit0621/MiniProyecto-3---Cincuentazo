package com.example._50zo.model.Enum;

public enum Suit {

    SPADES("♠", "spades"),
    HEARTS("♥", "hearts"),
    CLUBS("♣", "clubs"),
    DIAMONDS("♦", "diamonds");

    private final String symbol;
    private final String folder;

    Suit(String symbol, String folder) {
        this.symbol = symbol;
        this.folder = folder;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getFolder(){
        return folder;
    }




}
