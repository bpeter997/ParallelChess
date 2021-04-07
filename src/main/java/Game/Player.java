package Game;

import Helpers.Exceptions.Winner;

public class Player extends Thread {
    RoundHandler roundHandler;
    int playerId;

    Player(int playerId) throws Winner {
        this.playerId = playerId;
        this.roundHandler = RoundHandler.getInstance();
    }

    @Override
    public void run() {
        while (!this.roundHandler.isCheckMate()) {
                this.roundHandler.handle_round(this.playerId);
        }
    }
}
