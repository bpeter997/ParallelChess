package Game;

import Game.Pieces.Piece;
import Helpers.Point;
import org.jetbrains.annotations.NotNull;

import java.util.Scanner;

public class Game {

    private static Game INSTANCE = null;

    private final Table table;

    private Game() {
        this.table = Table.getInstance();
    }

    public void startGame() {
        table.drawTable();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                this.handle_round(j+1);
            }
        }
    }

    private void handle_round(int player) {
        // pick piece
        Piece selectedPiece = null;
        Scanner sc = new Scanner(System.in);
        boolean successFullPic = false;
        while (!successFullPic) {
            System.out.println("Pick a piece! (x,y)");
            System.out.println("");
            Point position = getPointFromUserINPUT(sc);
            try {
                selectedPiece = table.pickPiece(player, position);
                successFullPic = true;
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }

        boolean successFullMove = false;
        while (!successFullMove) {
            System.out.println("Move to! (x,y)");
            System.out.println("");
            Point position = getPointFromUserINPUT(sc);
            try {
                selectedPiece.tryMove(position);
                successFullMove = true;
                table.drawTable();
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
    }

    @NotNull
    private Point getPointFromUserINPUT(Scanner sc) {
        String posString = sc.nextLine();
        String[] parts = posString.split(",");
        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1]);
        return new Point(x,y);
    }

    public static Game getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Game();
        }
        return INSTANCE;
    }
}
