package Game;

import Helpers.Exceptions.Winner;

public class Game {

    private static Game INSTANCE = null;

    private final Table table;
   //private final RoundHandler roundHandler;
    private final Player[] players;

    private Game() throws Winner {
        this.table = Table.getInstance();
        this.players = new Player[4];
        RoundHandler.getInstance(players);
        for (int i = 0; i < 4; i++) {
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
