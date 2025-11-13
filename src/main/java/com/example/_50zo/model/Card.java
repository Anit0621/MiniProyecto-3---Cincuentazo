package com.example._50zo.model;

/**
 * Represents a playing card used in the Cincuentazo game.
 *
 * Each card has a name (rank), a suit, a numeric value, and an optional image path.
 * This class is simple and only stores basic card data.
 *
 *
 */
public class    Card {

    private String name;       // A, 2, 3, ..., J, Q, K
    private String suit;       // Corazones, treboles, diamantes, picas
    private int value;         // Base numeric value
    private String imagePath;  // Path for the card image (used in the GUI)

    /**
     * Creates a new Card with the given parameters.
     *
     * @param name the card rank (A, 2, 3, ..., K)
     * @param suit the card suit (Corazones, treboles, diamantes, picas)
     * @param value the numeric value of the card
     * @param imagePath the path to the image file
     */
    public Card(String name, String suit, int value, String imagePath) {
        this.name = name;
        this.suit = suit;
        this.value = value;
        this.imagePath = imagePath;
    }


    public String getName() {
        return name;
    }

    public String getSuit() {
        return suit;
    }

    public int getValue() {
        return value;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    /**
     * Calculates the card's value in the game according to the Cincuentazo rules.
     * <p>
     * Aces (A) can be worth 1 or 10 depending on the current sum
     * Face cards (J, Q, K) subtract 10
     * Card 9 adds 0
     * Cards 2–8 and 10 add their number
     *
     * @return the numeric value to be applied (positive or negative)
     */
    public int getGameValue(int currentSum) {
        switch (name) {
            case "A":
                if (currentSum + 10 <= 50) {
                    return 10;
                } else {
                    return 1;
                }
            case "J":
            case "Q":
            case "K":
                return -10;
            case "9":
                return 0;
            default:
                return value;
        }
    }
    /**
     * Returns a text representation of the card.
     * @return a string containing the card name, suit, and value
     */
    @Override
    public String toString() {
        return name + " of " + suit + " (" + value + ")";
    }
}