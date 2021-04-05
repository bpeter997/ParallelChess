package Pieces;

import Game.Table;
import Helpers.Directions;
import Helpers.Exceptions.InvalidMove;
import Helpers.Point;
import com.diogonunes.jcolor.Attribute;

import java.util.ArrayList;

import static com.diogonunes.jcolor.Attribute.*;

public abstract class Piece {

    protected Point position;
    protected int team;
    protected int player;
    protected Table table;
    protected Directions[] enabledDirections;

    protected ArrayList<Point> possibleMoveCoordinates;

    protected Piece(Point position, Table table, int player, int team) {
        table.movePieceToPosition(this, position);
        this.position = position;
        this.team = team;
        this.player = player;
        this.table = table;
        this.possibleMoveCoordinates = new ArrayList<>();
    }

    public void tryMove(Point newPos) throws Exception {
        if (newPos.getX() < 0 || newPos.getX() > Table.ROW_NUMBER)
            throw new InvalidMove();
        if (newPos.getY() < 0 || newPos.getY() > Table.COLUMN_NUMBER)
            throw new InvalidMove();
        if (this.position.getX() == newPos.getX() && this.position.getY() == newPos.getY())
            throw new InvalidMove();
        if (!this.isPossibleValue(newPos, this.possibleMoveCoordinates)) throw new InvalidMove();
        this.move(newPos);

    }

    private void move(Point newPos) throws Exception {
        Piece p = table.getPieceOnPosition(newPos);
        if (p != null) {
            if (p.team == this.team) throw new InvalidMove();
            if (p instanceof King) throw new Exception("You cant kick king!");
            System.out.println(p.toString() + " piece kicked!");
            System.out.println();
        }
        table.removePieceFromPosition(position);
        table.removePieceFromPosition(newPos);
        table.movePieceToPosition(this, newPos);
    }

    protected boolean isPossibleValue(Point value, ArrayList<Point> arrayList) {
        for (Point possibleMoveCoordinate : arrayList) {
            if (value.isEqual(possibleMoveCoordinate)) return true;
        }
        return false;
    }

    protected final void fillEnabledDirectionsArray(Directions[] usableDirections) {
        this.enabledDirections = new Directions[usableDirections.length];
        System.arraycopy(usableDirections, 0, this.enabledDirections, 0, usableDirections.length);
    }

    public void calcPossibleMoveCoordinates() {
        this.possibleMoveCoordinates.clear();
        for (Directions enabledDirection : this.enabledDirections) {
            switch (enabledDirection) {
                case KNIGHT -> this.getPossibleKnightPositions();
                case DOWN -> this.getPossibleDownPositions();
                case LEFT -> this.getPossibleLeftPositions();
                case UP -> this.getPossibleUpPositions();
                case RIGHT -> this.getPossibleRightPositions();
                case LEFT_DOWN_DIAGONAL -> this.getPossibleLeftDownPositions();
                case LEFT_UP_DIAGONAL -> this.getPossibleLeftUpPositions();
                case RIGHT_UP_DIAGONAL -> this.getPossibleRightUpPositions();
                case RIGHT_DOWN_DIAGONAL -> this.getPossibleRightDownPositions();
                default -> throw new IllegalStateException("Unexpected value: " + enabledDirection);
            }
        }
    }

    private void getPossibleKnightPositions() {
        this.checkPosition(position.getX() + 1, position.getY() + 2, null);
        this.checkPosition(position.getX() + 1, position.getY() - 2, null);
        this.checkPosition(position.getX() - 1, position.getY() - 2, null);
        this.checkPosition(position.getX() - 1, position.getY() + 2, null);
        this.checkPosition(position.getX() + 2, position.getY() + 1, null);
        this.checkPosition(position.getX() + 2, position.getY() - 1, null);
        this.checkPosition(position.getX() - 2, position.getY() - 1, null);
        this.checkPosition(position.getX() - 2, position.getY() + 1, null);
    }

    protected void getPossibleDownPositions() {
        ArrayList<Point> tempPoints = new ArrayList<>();
        for (int i = this.position.getX() + 1; i < Table.ROW_NUMBER; i++) {
            Point p = new Point(i, this.position.getY());
            tempPoints.add(p);
            if (checkPosition(i, this.position.getY(), tempPoints)) break;
        }
    }

    protected void getPossibleUpPositions() {
        ArrayList<Point> tempPoints = new ArrayList<>();
        for (int i = this.position.getX() - 1; i >= 0; i--) {
            Point p = new Point(i, this.position.getY());
            tempPoints.add(p);
            if (checkPosition(i, this.position.getY(), tempPoints)) break;
        }
    }

    protected void getPossibleLeftPositions() {
        ArrayList<Point> tempPoints = new ArrayList<>();
        for (int i = this.position.getY() - 1; i >= 0; i--) {
            Point p = new Point(this.position.getX(), i);
            tempPoints.add(p);
            if (checkPosition(this.position.getX(), i, tempPoints)) break;
        }
    }

    protected void getPossibleRightPositions() {
        ArrayList<Point> tempPoints = new ArrayList<>();
        for (int i = this.position.getY() + 1; i < Table.COLUMN_NUMBER; i++) {
            Point p = new Point(this.position.getX(), i);
            tempPoints.add(p);
            if (checkPosition(this.position.getX(), i, tempPoints)) break;
        }
    }

    protected void getPossibleLeftDownPositions() {
        int minIndex = Math.min(Table.ROW_NUMBER - this.position.getX(), Table.COLUMN_NUMBER - (Table.COLUMN_NUMBER - this.position.getY()));
        ArrayList<Point> tempPoints = new ArrayList<>();
        for (int i = 1; i <= minIndex; i++) {
            Point p = new Point(this.position.getX() + i, this.position.getY() - i);
            tempPoints.add(p);
            if (checkPosition(this.position.getX() + i, this.position.getY() - i, tempPoints)) break;
        }
    }

    protected void getPossibleLeftUpPositions() {
        int minIndex = Math.min(Table.ROW_NUMBER - (Table.ROW_NUMBER - this.position.getX()), Table.COLUMN_NUMBER - (Table.COLUMN_NUMBER - this.position.getY()));
        ArrayList<Point> tempPoints = new ArrayList<>();
        for (int i = 1; i <= minIndex; i++) {
            Point p = new Point(this.position.getX() - i, this.position.getY() - i);
            tempPoints.add(p);
            if (checkPosition(this.position.getX() - i, this.position.getY() - i, tempPoints)) break;
        }
    }

    protected void getPossibleRightUpPositions() {
        int minIndex = Math.min(Table.ROW_NUMBER - (Table.ROW_NUMBER - this.position.getX()), Table.COLUMN_NUMBER - this.position.getY());
        ArrayList<Point> tempPoints = new ArrayList<>();
        for (int i = 1; i <= minIndex; i++) {
            Point p = new Point(this.position.getX() - i, this.position.getY() + i);
            tempPoints.add(p);
            if (checkPosition(this.position.getX() - i, this.position.getY() + i, tempPoints)) break;
        }
    }

    protected void getPossibleRightDownPositions() {
        int minIndex = Math.min(Table.ROW_NUMBER - this.position.getX(), Table.COLUMN_NUMBER - this.position.getY());
        ArrayList<Point> tempPoints = new ArrayList<>();
        for (int i = 1; i <= minIndex; i++) {
            Point p = new Point(this.position.getX() + i, this.position.getY() + i);
            tempPoints.add(p);
            if (checkPosition(this.position.getX() + i, this.position.getY() + i, tempPoints)) break;
        }
    }

    protected boolean checkPosition(int x, int y, ArrayList<Point> tempPoints) {
        if (x < 0 || x > 7 || y < 0 || y > 15) return true;
        Point actualPos = new Point(x, y);
        Piece piece = table.getPieceOnPosition(actualPos);
        if (piece != null && piece.team == this.team) return true;
        if (piece instanceof King) {
            ((King) piece).setInCheck(true);
            System.out.println(piece.player + " in check by " + this.toString());
            if (tempPoints == null) tempPoints = new ArrayList<>();
            tempPoints.add(this.position);
            ((King) piece).setCheckZone(new ArrayList<>(tempPoints));
            return true;
        }
        this.addPointToList(actualPos);
        return piece != null;
    }

    protected void addPointToList(Point position) {
        if (position.getX() >= 0 && position.getX() < Table.ROW_NUMBER && position.getY() >= 0 && position.getY() < Table.COLUMN_NUMBER) {
            King myKing = table.getKings()[this.player - 1];
            if (myKing.isInCheck()) {
                if (!this.isPossibleValue(position, myKing.getCheckZone())) return;
            }
            this.possibleMoveCoordinates.add(position);
        }
    }

    protected Attribute getColorsByPlayerId(int playerId) {
        return switch (playerId) {
            case 1 -> RED_TEXT();
            case 2 -> YELLOW_TEXT();
            case 3 -> GREEN_TEXT();
            case 4 -> BLUE_TEXT();
            default -> BLACK_TEXT();
        };
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public int getPlayer() {
        return player;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public ArrayList<Point> getPossibleMoveCoordinates() {
        return possibleMoveCoordinates;
    }

    public void printPossibleMoves() {
        System.out.println("Possible moves: ");
        for (Point possibleMoveCoordinate : possibleMoveCoordinates) {
            System.out.print(possibleMoveCoordinate.toString());
        }
        System.out.println();
    }
}
