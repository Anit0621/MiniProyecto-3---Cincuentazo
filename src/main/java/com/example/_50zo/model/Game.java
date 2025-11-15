package com.example._50zo.model;

import com.example._50zo.model.exceptions.EmptyDeck;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.example._50zo.controller.GameController;

/**
 * Represents the main game logic for Cincuentazo.
 *
 * This class controls the deck, the players, the table sum,
 * and manages the game turns.
 */
public class Game {

    private Deck deck;
    private List<Player> players;
    private int tableSum;
    private boolean gameOver;
    private int numPlayers;
    private MachinePlayer machinePlayer;
    private GameController gameController;


    /**
     * Returns the {@code Deck} object set for this game.
     */

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

        HumanPlayer humanPlayer = new HumanPlayer("Jugador Humano");
        players.add(humanPlayer);
    }

    /**
     * Initializes the game:
     * - Fills and shuffles the deck.
     * - Adds players to the game.
     * - Deals 4 cards to each player.
     */
    public void initializeGame() {
        deck.initializeStandard52Deck();
        deck.shuffle();

        for (int i = 1; i < numPlayers; i++) {
            MachinePlayer machineplayerr = new MachinePlayer("maquina " + i, this);
            players.add(machineplayerr);
            Thread machineplayer = new Thread(machineplayerr);
            machineplayer.setDaemon(true);
            machineplayer.start();
        }

        for (int i = 0; i < 4; i++) {
            for (Player p : players) {
                try {
                    p.addCard(deck.drawCard());
                } catch (EmptyDeck e) {
                    if (gameController != null) {
                        gameController.showMessage("No hay más cartas para repartir.");
                    }
                }
            }
        }

        if (gameController != null) {
            gameController.showMessage("El juego ha comenzado. ¡Buena suerte!");
        }
    }

    /**
     * Executes the turn for the specified player.
     * <p>
     * If the game is over, the player is {@code null}, or the player has already
     * been eliminated, the method immediately returns. Otherwise, the method
     * processes the player's turn based on whether they are a human or a
     * {@code MachinePlayer}.

     *
     * @param player the player whose turn is being executed.
     * @throws InterruptedException if the machine player's thread is interrupted
     *         during the {@code wait()} operation (wrapped internally).
     * @see MachinePlayer
     * @see #eliminatePlayer(Player)
     * @see #checkForWinner()
     */


    public void playTurn(Player player) {
        if (gameOver || player == null || player.isEliminated()) return;

        System.out.println("Turno de: " + player.getName() + " | Suma actual: " + getTableSum());

        if (player instanceof MachinePlayer) {
            MachinePlayer machine = (MachinePlayer) player;

            if (!machine.canPlay(tableSum)) {
                System.out.println("Entra al ciclo de eliminar, la suma actual de la mesa es: " + tableSum);
                for ( Card c : machine.getHand()){
                    System.out.println("las cartas restantes son");
                    System.out.println(c.getGameValue(tableSum));
                }
                eliminatePlayer(machine);
                return;
            }

            machine.setTableSum(tableSum);
            machine.setMyTurn(true);

            synchronized (machine) {
                machine.notify();
            }

            synchronized (machine) {
                try {
                    while (machine.isMyTurn()) {
                        machine.wait();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

        }

        checkForWinner();
    }

    /**
     * Eliminates the specified player from the game.
     * <p>
     * If the player is {@code null}, already eliminated, or the game is already
     * over, the method does nothing. Otherwise, the player's remaining cards are
     * returned to the deck, the player's state is updated to eliminated, and
     * machine players are instructed to stop their background execution thread.
     * </p>
     *
     * <p>
     * The number of active players is decreased by one, and if a
     * {@code GameController} instance is present, a message is displayed in the UI
     * notifying that the player has been removed. Finally, the game state is checked
     * to determine whether a winner has emerged.
     * </p>
     *
     * @param p the player to eliminate from the game.
     * @see MachinePlayer#stopRunning()
     * @see #checkForWinner()
     */


    public void eliminatePlayer(Player p) {
        if (p == null || p.isEliminated() || gameOver) return;

        if (!p.getHand().isEmpty()) {
            deck.addCards(new ArrayList<>(p.getHand()));
            p.getHand().clear();
        }

        if (p instanceof MachinePlayer) {
            try {
                ((MachinePlayer) p).stopRunning();
            } catch (Exception ignored) {}
        }
        System.out.println("Numero de jugadores antes de eliminar al jugador actual " + numPlayers);
        numPlayers = numPlayers-1;
        System.out.println("Numero de jugadores despues de eliminar al jugador actal " +numPlayers);
        p.eliminate();

        if (gameController != null) {
            gameController.showMessage(p.getName() + " ha sido eliminado.");
        }

        checkForWinner();
    }

    /**
     * Removes eliminated players from the game, updating the count of active players.
     * <p>
     * This method iterates through the list of players and recalculates the number of
     * players who are still active (i.e., not eliminated). It does not modify the
     * player list itself, only updates the internal counter {@code numPlayers}.
     * </p>
     *
     * <p><b>Note:</b> As currently implemented, this method does not perform the intended
     * recalculation and should be revised. It simply resets the local variable
     * {@code currentNumPlayers} but does not affect the game's state.</p>
     */

    public void removeEliminatedPlayers() {
        var currentNumPlayers = numPlayers;
        currentNumPlayers -= currentNumPlayers;
    }

    /**
     * Checks the current state of the game to determine whether a winner exists.
     * <p>
     * This method evaluates all players and counts how many remain active
     * (i.e., not eliminated). There are three possible outcomes:
     * </p>
     *
     * <ul>
     *   <li><b>One player remaining:</b> That player is declared the winner and the game ends.</li>
     *   <li><b>No players remaining:</b> The game ends with no winner.</li>
     *   <li><b>More than one active player:</b> The game continues.</li>
     * </ul>
     *
     * <p>
     * If a winner is detected, the method interacts with the {@link GameController}
     * to show a message and trigger the end-of-game screen.
     * </p>
     *
     * @throws RuntimeException if an unexpected error occurs while notifying the controller
     * (typically wrapping an internal IOException).
     */


    public void checkForWinner() {
        System.out.println("Al revisar el numero de jugadores para determinar ganador, hay " + numPlayers);
        if (gameOver) return;
        int counterForLeftPlayers = 0;
        Player lastStanding = null;
        for (Player p : players) {
            if (!p.isEliminated()) {
                counterForLeftPlayers++;
                lastStanding = p;


            }
        }

        if (lastStanding != null){
        if (counterForLeftPlayers == 1) {
            Player winner = lastStanding;
            gameOver = true;

            String winnerName = lastStanding.getName();
            boolean playerWon = winner instanceof HumanPlayer;
            try {
                if (gameController != null) {
                    System.out.println("OngameEnded sera llamado");
                    gameController.showMessage("El ganador es: " + winnerName);
                    gameController.onGameEnded(winnerName, playerWon);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        } else if (counterForLeftPlayers == 0) {
            gameOver = true;
            if (gameController != null) {
                gameController.showMessage("Todos los jugadores han sido eliminados. No hay ganador.");
            }
        }
    }

    /**
     * Checks if the game is over.
     * @return true if the game has ended
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Gets the current sum of the table.
     * @return current table sum
     */
    public int getTableSum() {
        return tableSum;
    }

    /**
     * Returns the list of players in the game.
     * @return all players
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Updates the tableSum
     * @param tableSum the int value to set for the TableSum.
     */

    public void setTableSum(int tableSum) {
        this.tableSum = tableSum;
    }

    public void updateTablesum(Card card) {
        if (gameController != null) {
            int value = card.getGameValue(tableSum);
            tableSum += value;
            gameController.updateTableSumAndView(card);
        }
    }

    /**
     * Sets the name of the human player (the user) at the start of the game.
     * This method updates the first player in the list if it's a HumanPlayer.
     *
     * @param playerName The name entered by the user.
     */
    public void setHumanName(String playerName) {
        if (!players.isEmpty() && players.get(0) instanceof HumanPlayer) {
            players.get(0).name = playerName;
        }
    }

    /**
     * Sets the GameController for this Game instance.
     * @param gameController the GameController instance to be set.
     */

    public void setController(GameController gameController) {
        this.gameController = gameController;
    }
}