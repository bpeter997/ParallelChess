package Game;
import Game.Pieces.Piece;
import Game.Pieces.Rook;
import Helpers.InvalidPiece;
import Helpers.Point;

public class Table {

    public static final int ROW_NUMBER = 8;
    public static final int COLUMN_NUMBER = 16;

    private static Table INSTANCE = null;

    private final Piece[][] table;

    private Table() {
        table = new Piece[ROW_NUMBER][COLUMN_NUMBER];
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table.length; j++) {
                table[i][j] = null;
            }
        }
        this.fillTable();
    }

    public Piece pickPiece(int playerNumber, Point position) throws Exception {
        Piece piece = this.getPieceOnPosition(position);
        if (piece == null) throw new Exception("Empty field!");
        System.out.println("piece player:  " + piece.getPlayer() + "   parameter player:  " + playerNumber);
        if (piece.getPlayer() != playerNumber) throw new InvalidPiece();
        return piece;
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
        for (Piece[] pieces : table) {
            System.out.println("\n");
            for (Piece piece : pieces) {
                if (piece == null) {
                    System.out.print("  0   ");
                } else {
                    System.out.print("  " + piece.toString() + "  ");
                }
            }
        }
        System.out.println("\n");
    }

    private void fillTable() {
        new Rook(new Point(0,0), this, 1, 1);
        new Rook(new Point(0,15), this, 2, 1);
        new Rook(new Point(7,0), this, 3, 2);
        new Rook(new Point(7,15), this, 4, 2);
    }

    public static Table getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Table();
        }
        return INSTANCE;
    }

}
