package Game;

import Pieces.Piece;
import Helpers.Exceptions.InvalidCoordinates;
import Helpers.Exceptions.Winner;
import Helpers.Point;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Scanner;

public class RoundHandler {
    private final Table table;
    private static RoundHandler INSTANCE = null;
    private boolean isCheckMate;
    private static int callNumber = 0;

    private final Player[] players;

    private RoundHandler(Player[] players) throws Winner {
        this.table = Table.getInstance();
        this.players = players;
    }

    public synchronized void handle_round(int player) throws Winner {
        // pick piece
        while (Thread.currentThread().getPriority() == Thread.MIN_PRIORITY) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Piece selectedPiece = null;
        Scanner sc = new Scanner(System.in);
        selectedPiece = handle_selectPiece(player, selectedPiece, sc);
        handle_movePiece(selectedPiece, sc);

        players[callNumber % players.length].setPriority(Thread.MIN_PRIORITY);
        callNumber++;
        players[callNumber % players.length].setPriority(Thread.MAX_PRIORITY);

        notifyAll();
    }

    private void handle_movePiece(Piece selectedPiece, Scanner sc) {
        boolean successFullMove = false;
        while (!successFullMove) {
            System.out.println("Move to! (x,y)");
            System.out.println();
            try {
                Point position = getPointFromUserINPUT(sc);
                selectedPiece.tryMove(position);
                successFullMove = true;
                table.drawTable();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.toString());
            }
        }
        notifyAll();
    }

    private Piece handle_selectPiece(int player, Piece selectedPiece, Scanner sc) throws Winner {
        boolean successFullPic = false;
        while (!successFullPic) {
            try {
                table.analyzeCheck();
                table.recalculatePossibleMoves(player);
                System.out.println("Pick a piece player" + player + "!");
                System.out.println();
                Point position = getPointFromUserINPUT(sc);
                selectedPiece = table.pickPiece(player, position);
                successFullPic = true;
            } catch (Winner w) {
                throw w;
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
        return selectedPiece;
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

    public static RoundHandler getInstance(Player[] players) throws Winner {
        if (INSTANCE == null) {
            INSTANCE = new RoundHandler(players);
        }
        return INSTANCE;
    }

    @Contract(pure = true)
    public static RoundHandler getInstance() {
        return INSTANCE;
    }

    public boolean isCheckMate() {
        return isCheckMate;
    }

    public void setCheckMate(boolean checkMate) {
        isCheckMate = checkMate;
    }
}