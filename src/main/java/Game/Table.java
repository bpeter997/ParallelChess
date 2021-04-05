package Game;

import Pieces.*;
import Helpers.Exceptions.InvalidPiece;
import Helpers.Exceptions.Winner;
import Helpers.Point;

public class Table {

    public static final int ROW_NUMBER = 8;
    public static final int COLUMN_NUMBER = 16;

    private static Table INSTANCE = null;

    private final Piece[][] table;
    private final King[] kings;

    private Table() throws Winner {
        kings = new King[4];
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
        piece.printPossibleMoves();
        if (piece.getPossibleMoveCoordinates().size() == 0) throw new Exception("You cant move this piece!");
        System.out.println();
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
        String[] abc = {"A", "B", "C", "D", "E", "F", "G", "H"};
        for (int i = 0; i < this.table.length; i++) {
            if (i == 0) {
                for (int k = 0; k < 16; k++) {
                    if (k == 0) System.out.print("         ");
                    if (k < 10) {
                        System.out.print("  " + k + "  ");
                    } else {
                        System.out.print("  " + k + "  ");
                    }
                }
                System.out.println("\n");
            }
            System.out.println("\n");
            System.out.print(abc[i] + "        ");
            for (int j = 0; j < this.table[i].length; j++) {
                Piece p = this.table[i][j];
                if (p == null) {
                    System.out.print("  0  ");
                } else {
                    System.out.print("  " + p.toString() + "  ");
                }
            }
        }
        System.out.println("\n");
    }

    private void fillTable() throws Winner {
        this.initPlayer(1, 1, 1, 0, 0);
        this.initPlayer(2, 2, 6, 7, 0);
        this.initPlayer(3, 1, 1, 0, 8);
        this.initPlayer(4, 2, 6, 7, 8);
        this.recalculatePossibleMoves(5);
    }

    private void initPlayer(int player, int team, int pawnLineStartIndex, int otherLineStartIndex, int columnStartIndex) {
        for (int i = columnStartIndex; i < columnStartIndex + 8; i++) {
            new Pawn(new Point(pawnLineStartIndex, i), this, player, team);
        }
        new Rook(new Point(otherLineStartIndex, columnStartIndex), this, player, team);
        new Knight(new Point(otherLineStartIndex, columnStartIndex + 1), this, player, team);
        kings[player - 1] = new King(new Point(otherLineStartIndex, columnStartIndex + 4), this, player, team);
        new Bishop(new Point(otherLineStartIndex, columnStartIndex + 2), this, player, team);
        new Queen(new Point(otherLineStartIndex, columnStartIndex + 3), this, player, team);
        new Bishop(new Point(otherLineStartIndex, columnStartIndex + 5), this, player, team);
        new Knight(new Point(otherLineStartIndex, columnStartIndex + 6), this, player, team);
        new Rook(new Point(otherLineStartIndex, columnStartIndex + 7), this, player, team);
    }

    public static Table getInstance() throws Winner {
        if (INSTANCE == null) {
            INSTANCE = new Table();
        }
        return INSTANCE;
    }

    public void analyzeCheck() {
        for (King king : kings) {
            king.setInCheck(false);
            king.getCheckZone().clear();
        }

        for (int i = 0; i < this.table.length; i++) {
            for (int j = 0; j < this.table[i].length; j++) {
                Piece piece = this.getPieceOnPosition(new Point(i, j));
                if (piece == null) continue;
                piece.calcPossibleMoveCoordinates();
            }
        }
    }

    public void recalculatePossibleMoves(int player) throws Winner {
        int possibleMoves = 0;

        for (int i = 0; i < this.table.length; i++) {
            for (int j = 0; j < this.table[i].length; j++) {
                Piece piece = this.getPieceOnPosition(new Point(i, j));
                if (piece == null) continue;
                piece.calcPossibleMoveCoordinates();
                if (piece.getPossibleMoveCoordinates().size() != 0 && piece.getPlayer() == player) possibleMoves++;
            }
        }
        if (player < 5 && possibleMoves == 0) throw new Winner(player % 2 == 0 ? 2 : 1);
    }

    public King[] getKings() {
        return kings;
    }
}
