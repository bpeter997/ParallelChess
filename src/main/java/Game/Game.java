package Game;

import Helpers.Exceptions.Winner;

public class Game {

    private static Game INSTANCE = null;

    private final Table table;
    private final Player[] players;

    private Game() throws Winner {
        this.table = Table.getInstance();
        int playersNumber = 4;
        this.players = new Player[playersNumber];
        RoundHandler.getInstance(players);
        for (int i = 0; i < playersNumber; i++) {
             Player player = new Player(i+1);
             players[i] = player;
             if (i!=0) {
                 player.setPriority(Thread.MIN_PRIORITY);
             } else {
                 player.setPriority(Thread.MAX_PRIORITY);
             }
        }
    }

    public void startGame() throws Exception {
        table.drawTable();
        for (Player player : players) {
            player.start();
        }
    }

    public static Game getInstance() throws Winner {
        if (INSTANCE == null) {
            INSTANCE = new Game();
        }
        return INSTANCE;
    }
}
