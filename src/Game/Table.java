package Game;
import Game.Pieces.Piece;
import Helpers.Point;

public class Table {

    public static final int ROW_NUMBER = 8;
    public static final int COLUMN_NUMBER = 16;

    private Piece[][] table;

    public Table() {
        table = new Piece[ROW_NUMBER][COLUMN_NUMBER];
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table.length; j++) {
                table[i][j] = null;
            }
        }
    }

    public Piece getPieceOnPosition(Point position) { 
        return table[position.getX()][position.getY()];
    }

    public void movePieceToPosition(Piece piece, Point position) {
        table[position.getX()][position.getY()] = piece;
    }

    public void removePieceFromPosition(Point position) {
        table[position.getX()][position.getY()] = null;
    }

    public void drawTable() {
        for (int i = 0; i < table.length; i++) {
            System.out.println("\n");
            for (int j = 0; j < table[i].length; j++) {
                Piece piece = table[i][j];
                if (piece == null) {
                    System.out.print("  0  ");
                } else {
                    System.out.print("  " + table[i][j].toString() + "  ");
                }
            }
        }
        System.out.println("\n");
    }

}
