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

    public void removeEliminatedPlayers() {
        var currentNumPlayers = numPlayers;
        currentNumPlayers -= currentNumPlayers;
    }


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

    public void setController(GameController gameController) {
        this.gameController = gameController;
    }
}