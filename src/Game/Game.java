package Game;

import Game.Pieces.Piece;
import Helpers.Exceptions.InvalidCoordinates;
import Helpers.Exceptions.Winner;
import Helpers.Point;
import org.jetbrains.annotations.NotNull;

import java.util.Scanner;

public class Game {

    private static Game INSTANCE = null;

    private final Table table;

    private Game() throws Winner {
        this.table = Table.getInstance();
    }

    public void startGame() throws Exception {
        table.drawTable();
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 2; j++) {
                try {
                    this.handle_round(j+1);
                } catch (Winner w) {
                    System.out.println(w.toString());
                    return;
                }
            }
        }
    }

    private void handle_round(int player) throws Exception {
        // pick piece
        Piece selectedPiece = null;
        Scanner sc = new Scanner(System.in);
        boolean successFullPic = false;
        while (!successFullPic) {
            try {
                System.out.println("Pick a piece player" + player + "!");
                System.out.println("");
                Point position = getPointFromUserINPUT(sc);
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
                table.recalculatePossibleMoves();
            } catch (Winner w) {
                throw w;
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
    }

    @NotNull
    private Point getPointFromUserINPUT(Scanner sc) throws Exception {
        String posString = sc.nextLine();
        String[] parts = posString.split(",");
        if (parts.length != 2) throw new InvalidCoordinates();
        int x = this.convertLetterToNumber(parts[0]);
        int y = Integer.parseInt(parts[1]);
        return new Point(x,y);
    }

    private int convertLetterToNumber(String letter) throws InvalidCoordinates {
        switch (letter) {
            case "A": return 0;
            case "B": return 1;
            case "C": return 2;
            case "D": return 3;
            case "E": return 4;
            case "F": return 5;
            case "G": return 6;
            case "H": return 7;
        }
        throw new InvalidCoordinates();
    }

    public static Game getInstance() throws Winner {
        if (INSTANCE == null) {
            INSTANCE = new Game();
        }
        return INSTANCE;
    }
}
