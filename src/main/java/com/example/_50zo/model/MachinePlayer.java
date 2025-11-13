package com.example._50zo.model;

import com.example._50zo.model.exceptions.NonPlayableCard;

/**
 * Represents the machine (computer) player in the Cincuentazo game.
 *
 * This class extends Player and implements Runnable to use threads.
 * The machine waits for a short random time before playing automatically.
 *
 */
public class MachinePlayer extends Player implements Runnable {

    private int tableSum;
    private Card playedCard;
    private volatile boolean myTurn = false;
    private volatile boolean running = true;
    private Game game;

    /**
     * Creates a machine player with a given name
     * @param name the name of the machine player
     */
    public MachinePlayer(String name, Game game) {
        super(name);

        this.game = game;
    }

    /**
     * Defines how the machine automatically plays a card.
     *
     * @param tableSum the current sum on the table
     * @return the card that the machine plays
     * @throws NonPlayableCard if the machine has no playable cards
     */
    @Override
    public Card playCard(int tableSum) throws NonPlayableCard {
        if (!canPlay(tableSum)) {
            throw new NonPlayableCard();

        }

        // Choose the first playable card (simple strategy)
        for (Card c : hand) {
            int value = c.getGameValue(tableSum);
            if (tableSum + value <= 50) {
                hand.remove(c);
                playedCard = c;
                return c;
            }
        }

        throw new NonPlayableCard();
    }

    /**
     * The code that runs when the thread starts.
     * The machine waits between 2 and 4 seconds,
     * then plays its card automatically.
     */
    @Override
    public void run() {
            while(!Thread.currentThread().isInterrupted()) {
                synchronized (this) {
                    try{

                        while(!myTurn) {
                            wait();
                        }
                        int waitTime = (int) (2000 + Math.random() * 2000);
                        Thread.sleep(waitTime);



                            playedCard = playCard(tableSum);
                            System.out.println("Va a jugar la carta " + playedCard.getGameValue(tableSum));
                            setMyTurn(false);
                            System.out.println(name + " jugó la carta: " + playedCard);
                            if (playedCard != null){
                                game.updateTablesum(playedCard);



                            }


                    } catch(InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } catch(NonPlayableCard e) {
                        System.out.println(name + ": No tiene ninguna carta para jugar.");
                        this.eliminated = true;

                    }
                    setMyTurn(false);
                    synchronized (this) {
                        notify();
                    }
                }

            }
    }



    /**
     * Sets the current table sum before the thread starts.
     * @param tableSum the current sum on the table
     */
    public void setTableSum(int tableSum) {
        this.tableSum = tableSum;
    }

    /**
     * Gets the last card played by the machine.
     * @return the last played card, or null if none
     */
    public Card getPlayedCard() {
        return playedCard;
    }

    public void setMyTurn(boolean myTurn) {
        this.myTurn = myTurn;
    }

    public String getName(){
        return name;
    }

    public void stopRunning() {
        eliminated = true;
        running = false;
        synchronized (this) {
            notifyAll();
        }
    }

    public boolean isMyTurn() {
        return myTurn;
    }

}

