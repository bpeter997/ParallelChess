import Game.Game;
import Game.Table;

public class App {

    public static void main(String[] args) throws Exception {
        Game game = Game.getInstance();
        game.startGame();
    }
}
