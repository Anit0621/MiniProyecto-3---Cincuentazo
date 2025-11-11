package com.example._50zo.model;

import com.example._50zo.model.exceptions.EmptyDeck;
import com.example._50zo.model.exceptions.NonPlayableCard;
import java.util.ArrayList;
import java.util.List;
import com.example._50zo.model.MachinePlayer;
import com.example._50zo.model.HumanPlayer;
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

    /**
     * Creates a new Cincuentazo game.
     */
    public Game(int numPlayers) {
        this.deck = new Deck();
        this.players = new ArrayList<>();
        this.tableSum = 0;
        this.gameOver = false;
        this.numPlayers = numPlayers;
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

         for(int i = 0 ; i < numPlayers; i++){
            MachinePlayer machineplayerr = new MachinePlayer("máquina " + i);
            Thread machineplayer = new Thread(machineplayerr);
             System.out.println("Máquina " + i + "creada");
             players.add(machineplayerr);
        }

        // Deal 5 cards to each player
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
        if (player.isEliminated()) {
            System.out.println(player.getName() + " ya está eliminado.");
            return;
        }

        try {
            // Machine player runs in a separate thread
            if (player instanceof MachinePlayer) {
                MachinePlayer machine = (MachinePlayer) player;
                machine.setTableSum(tableSum);

                Thread t = new Thread(machine);
                t.start();
                t.join(); // Wait for the machine to finish

                Card played = machine.getPlayedCard();
                if (played != null) {
                    tableSum += played.getGameValue(tableSum);
                    System.out.println("Nueva suma en la mesa: " + tableSum);
                }
            }
            // Human player plays directly
            else if (player instanceof HumanPlayer) {
                Card played = player.playCard(tableSum);
                tableSum += played.getGameValue(tableSum);
                System.out.println(player.getName() + " jugó " + played);
                System.out.println("Nueva suma en la mesa: " + tableSum);
            }

            // Check if the player reached exactly 50
            if (tableSum == 50) {
                System.out.println(player.getName() + " ha ganado la ronda 🎉");
                gameOver = true;
            }

            // Check if player can’t play anymore
            if (!player.canPlay(tableSum)) {
                player.eliminate();
                System.out.println(player.getName() + " no puede jugar más y ha sido eliminado.");
            }

        } catch (NonPlayableCard e) {
            System.out.println(player.getName() + " no puede jugar una carta válida.");
            player.eliminate();
        } catch (InterruptedException e) {
            System.out.println("El turno fue interrumpido.");
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
}
