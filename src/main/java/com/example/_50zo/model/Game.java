package com.example._50zo.model;

import com.example._50zo.model.exceptions.EmptyDeck;

import java.util.ArrayList;
import java.util.List;

import com.example._50zo.controller.GameController;
/**
 * Represents the main game logic for Cincuentazo.
 *
 * This class controls the deck, the players, the table sum,
 * and manages the game turns.
 *
 * Author: Ani & Team
 */
public class Game {

    private Deck deck;
    private List<Player> players;
    private int tableSum;
    private boolean gameOver;
    private int numPlayers;
    private MachinePlayer machinePlayer;

    private GameController gameController;

    public Deck getDeck() {
        return deck;
    }

    /**
     * Creates a new Cincuentazo game.
     */
    public Game(int numPlayers) {
        this.deck = new Deck();
        this.players = new ArrayList<>();
        this.tableSum = 0;
        this.gameOver = false;
        this.numPlayers = numPlayers;
        this.gameOver = false;
    }

    /**
     * Initializes the game:
     * - Fills and shuffles the deck.
     * - Adds players to the game.
     * - Deals 5 cards to each player.
     */
    public void initializeGame() {
        deck.initializeStandard52Deck();
        deck.shuffle();
        HumanPlayer humanPlayer = new HumanPlayer("Jugador humano");
        players.add(humanPlayer);

         for(int i = 1 ; i < numPlayers; i++){
            MachinePlayer machineplayerr = new MachinePlayer("máquina " + i,this);
             players.add(machineplayerr);
            Thread machineplayer = new Thread(machineplayerr);
             machineplayer.setDaemon(true);
            machineplayer.start();

             System.out.println("Máquina " + i + " creada e iniciada");


        }


        for (int i = 0; i < 4; i++) {
            for (Player p : players) {
                try {
                    p.addCard(deck.drawCard());
                } catch (EmptyDeck e) {
                    System.out.println("No hay más cartas para repartir.");
                }
            }
        }

        System.out.println("El juego ha comenzado. ¡Buena suerte!");
    }

    /**
     * Executes a full turn for a given player.
     *
     * @param player the player whose turn it is
     */
    public void playTurn(Player player) {


            if (player instanceof MachinePlayer) {
                System.out.println("Valor actual de la mesa: " + getTableSum());

                if (player.isEliminated()) {
                    System.out.println(player.getName() + " ya está eliminado.");
                    return;
                }

                if (!player.canPlay(tableSum)) {
                    player.eliminate();
                    ((MachinePlayer) player).stopRunning();
                    numPlayers -= numPlayers;
                    System.out.println(player.getName() + " no puede jugar más y ha sido eliminado.");



                }
                MachinePlayer machine = (MachinePlayer) player;
                machine.setTableSum(tableSum);
                machine.setMyTurn(true);
                synchronized (machine) {
                    machine.notify();
                }

                synchronized (machine) {
                    try {
                        while(machine.isMyTurn()){
                            machine.wait();
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }

            }

    }

    /**
     * Checks if the game is over.
     *
     * @return true if the game has ended
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Gets the current sum of the table.
     *
     * @return current table sum
     */
    public int getTableSum() {
        return tableSum;
    }

    /**
     * Returns the list of players in the game.
     *
     * @return all players
     */
    public List<Player> getPlayers() {
        return players;
    }

    public void setTableSum(int tableSum) {
        this.tableSum = tableSum;
    }

    public void updateTablesum(Card card){
        if (gameController != null){
            int value = card.getGameValue(tableSum);
            tableSum += value;
            gameController.updateTableSumandView(card);
        }
    }

    public void setController(GameController gameController) {
        this.gameController = gameController;
    }
}
