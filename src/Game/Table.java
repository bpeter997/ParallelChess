package Game;

import Game.Pieces.*;
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
        System.out.println(piece.getPossibleMoveCoordinates());
        if (piece.getPossibleMoveCoordinates().size() == 0) throw new Exception("You cant move this piece!");
        System.out.println("piece player:  " + piece.getPlayer() + "   parameter player:  " + playerNumber);
        if (piece.getPlayer() != playerNumber) throw new InvalidPiece();
        return piece;
    }

    public Piece getPieceOnPosition(Point position) {
        return table[position.getX()][position.getY()];
    }

    public void movePieceToPosition(Piece piece, Point position) {
        piece.setPosition(position);
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
        this.initPlayer(1, 1, 1, 0, 0);
        this.initPlayer(2, 2, 6, 7, 0);
        this.initPlayer(3, 1, 1, 0, 8);
        this.initPlayer(4, 2, 6, 7, 8);
        this.recalculatePossibleMoves();
    }

    private void initPlayer(int player, int team, int pawnLineStartIndex, int otherLineStartIndex, int columnStartIndex) {
        for (int i = columnStartIndex; i < columnStartIndex + 8; i++) {
            new Pawn(new Point(pawnLineStartIndex, i), this, player, team);
        }
        new Rook(new Point(otherLineStartIndex, columnStartIndex), this, player, team);
        new Knight(new Point(otherLineStartIndex, columnStartIndex + 1), this, player, team);
        new Bishop(new Point(otherLineStartIndex, columnStartIndex + 2), this, player, team);
        new Queen(new Point(otherLineStartIndex, columnStartIndex + 3), this, player, team);
        new King(new Point(otherLineStartIndex, columnStartIndex + 4), this, player, team);
        new Bishop(new Point(otherLineStartIndex, columnStartIndex + 5), this, player, team);
        new Knight(new Point(otherLineStartIndex, columnStartIndex + 6), this, player, team);
        new Rook(new Point(otherLineStartIndex, columnStartIndex + 7), this, player, team);
    }

    public static Table getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Table();
        }
        return INSTANCE;
    }

    public void recalculatePossibleMoves() {
        for (int i = 0; i < this.table.length; i++) {
            for (int j = 0; j < this.table[i].length; j++) {
                Piece piece = this.getPieceOnPosition(new Point(i,j));
                if (piece != null) piece.calcPossibleMoveCoordinates();
            }
        }
    }
}
