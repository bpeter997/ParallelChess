package Game.Pieces;

import java.util.ArrayList;

import Game.Table;
import Helpers.InvalidMove;
import Helpers.Point;
import Helpers.Directions;

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
        this.possibleMoveCoordinates = new ArrayList<Point>();
    }

    public void tryMove(Point newPos) throws Exception {
        if (newPos.getX() < 0 || newPos.getX() > Table.ROW_NUMBER)
            throw new InvalidMove();
        if (newPos.getY() < 0 || newPos.getY() > Table.COLUMN_NUMBER)
            throw new InvalidMove();
        if (this.position.getX() == newPos.getX() && this.position.getY() == newPos.getY())
            throw new InvalidMove();
        //Directions actualMoveDirection = this.getMoveDirection(newPos);
//        if (!Arrays.asList(this.enabledDirections).contains(actualMoveDirection) || actualMoveDirection == Directions.INVALID)
//            throw new InvalidMove();
        if (!this.isPossibleValue(newPos, this.possibleMoveCoordinates)) throw new InvalidMove();
        this.move(newPos);

    }

    private void move(Point newPos) throws Exception {
        Piece p = table.getPieceOnPosition(newPos);
        if (p != null) {
            if (p.team == this.team) throw new InvalidMove();
            if (p instanceof King) throw new Exception("You cant kick king!");
            System.out.println(p.toString() + " piece kicked!");
        }
        table.removePieceFromPosition(position);
        table.removePieceFromPosition(newPos);
        table.movePieceToPosition(this, newPos);
    }

    private boolean isPossibleValue(Point value, ArrayList<Point> arrayList) {
        for (Point possibleMoveCoordinate : arrayList) {
            if (value.isEqual(possibleMoveCoordinate)) return true;
        }
        return false;
    }

    protected final void fillEnabledDirectionsArray(Directions[] usableDirections) {
        this.enabledDirections = new Directions[usableDirections.length];
        System.arraycopy(usableDirections, 0, this.enabledDirections, 0, usableDirections.length);
    }

//    private Directions getMoveDirection(Point newPos) {
//        Point oldPos = this.position;
//        // moving vertically
//        if (newPos.getX() == oldPos.getX()) {
//            return getVerticalDirection(newPos, oldPos);
//            // moving horizontally
//        } else if (newPos.getY() == oldPos.getY()) {
//            return getHorizontallyDirection(newPos, oldPos);
//            // moving diagonally
//        } else {
//            return getOtherDirection(newPos, oldPos);
//        }
//    }
//
//    private Directions getOtherDirection(Point newPos, Point oldPos) {
//        if (Math.abs(newPos.getX() - oldPos.getX()) == Math.abs(newPos.getY() - oldPos.getY())) {
//            return getDiagonalDirections(newPos, oldPos);
//        } else {
//            return getKnightDirection(newPos, oldPos);
//        }
//    }
//
//    private Directions getKnightDirection(Point newPos, Point oldPos) {
//        if ((Math.abs(newPos.getX() - oldPos.getX()) == 2 && Math.abs(newPos.getY() - oldPos.getY()) == 1) || (Math.abs(newPos.getX() - oldPos.getX()) == 1 && Math.abs(newPos.getY() - oldPos.getY()) == 2)) {
//            return Directions.KNIGHT;
//        }
//        return Directions.INVALID;
//    }
//
//    private Directions getDiagonalDirections(Point newPos, Point oldPos) {
//        if (newPos.getX() > oldPos.getX()) {
//            if (newPos.getY() > oldPos.getY()) {
//                return (this.team == 1) ? Directions.LEFT_UP_DIAGONAL : Directions.RIGHT_DOWN_DIAGONAL;
//            } else {
//                return (this.team == 1) ? Directions.LEFT_DOWN_DIAGONAL : Directions.RIGHT_UP_DIAGONAL;
//            }
//        } else {
//            if (newPos.getY() > oldPos.getY()) {
//                return (this.team == 1) ? Directions.RIGHT_UP_DIAGONAL : Directions.LEFT_DOWN_DIAGONAL;
//            } else {
//                return (this.team == 1) ? Directions.RIGHT_DOWN_DIAGONAL : Directions.LEFT_UP_DIAGONAL;
//            }
//        }
//    }
//
//    private Directions getHorizontallyDirection(Point newPos, Point oldPos) {
//        if (newPos.getX() > oldPos.getX()) {
//            return (this.team == 1) ? Directions.LEFT : Directions.RIGHT;
//        } else {
//            return (this.team == 1) ? Directions.RIGHT : Directions.LEFT;
//        }
//    }
//
//    private Directions getVerticalDirection(Point newPos, Point oldPos) {
//        if (newPos.getY() > oldPos.getY()) {
//            return (this.team == 1) ? Directions.UP : Directions.DOWN;
//        } else {
//            return (this.team == 1) ? Directions.DOWN : Directions.UP;
//        }
//    }

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
        ArrayList<Point> tempPoints = new ArrayList<Point>();
        for (int i = this.position.getX() + 1; i < Table.ROW_NUMBER; i++) {
            Point p = new Point(i, this.position.getY());
            tempPoints.add(p);
            if (checkPosition(i, this.position.getY(), tempPoints)) break;
        }
    }

    protected void getPossibleUpPositions() {
        ArrayList<Point> tempPoints = new ArrayList<Point>();
        for (int i = this.position.getX() - 1; i >= 0; i--) {
            Point p = new Point(i, this.position.getY());
            tempPoints.add(p);
            if (checkPosition(i, this.position.getY(), tempPoints)) break;
        }
    }

    protected void getPossibleLeftPositions() {
        ArrayList<Point> tempPoints = new ArrayList<Point>();
        for (int i = this.position.getY() - 1; i >= 0; i--) {
            Point p = new Point(this.position.getX(), i);
            tempPoints.add(p);
            if (checkPosition(this.position.getX(), i, tempPoints)) break;
        }
    }

    protected void getPossibleRightPositions() {
        ArrayList<Point> tempPoints = new ArrayList<Point>();
        for (int i = this.position.getY() + 1; i < Table.COLUMN_NUMBER; i++) {
            Point p = new Point(this.position.getX(), i);
            tempPoints.add(p);
            if (checkPosition(this.position.getX(), i, tempPoints)) break;
        }
    }

    protected void getPossibleLeftDownPositions() {
        int minIndex = Math.min(Table.ROW_NUMBER - this.position.getX(), Table.COLUMN_NUMBER - (Table.COLUMN_NUMBER - this.position.getY()));
        ArrayList<Point> tempPoints = new ArrayList<Point>();
        for (int i = 0; i < minIndex; i++) {
            Point p = new Point(this.position.getX() + i, this.position.getY() - i);
            tempPoints.add(p);
            if (checkPosition(this.position.getX() + i, this.position.getY() - i, tempPoints)) break;
        }
    }

    protected void getPossibleLeftUpPositions() {
        int minIndex = Math.min(Table.ROW_NUMBER - (Table.ROW_NUMBER - this.position.getX()), Table.COLUMN_NUMBER - (Table.COLUMN_NUMBER - this.position.getY()));
        ArrayList<Point> tempPoints = new ArrayList<Point>();
        for (int i = 0; i < minIndex; i++) {
            Point p = new Point(this.position.getX() - i, this.position.getY() - i);
            tempPoints.add(p);
            if (checkPosition(this.position.getX() - i, this.position.getY() - i, tempPoints)) break;
        }
    }

    protected void getPossibleRightUpPositions() {
        int minIndex = Math.min(Table.ROW_NUMBER - (Table.ROW_NUMBER - this.position.getX()), Table.COLUMN_NUMBER - this.position.getY());
        ArrayList<Point> tempPoints = new ArrayList<Point>();
        for (int i = 0; i < minIndex; i++) {
            Point p = new Point(this.position.getX() - i, this.position.getY() + i);
            tempPoints.add(p);
            if (checkPosition(this.position.getX() - i, this.position.getY() + i, tempPoints)) break;
        }
    }

    protected void getPossibleRightDownPositions() {
        int minIndex = Math.min(Table.ROW_NUMBER - this.position.getX(), Table.COLUMN_NUMBER - this.position.getY());
        ArrayList<Point> tempPoints = new ArrayList<Point>();
        for (int i = 0; i < minIndex; i++) {
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
            if (tempPoints == null) tempPoints = new ArrayList<Point>();
            tempPoints.add(this.position);
            ((King) piece).setCheckZone(new ArrayList<Point>(tempPoints));
            return true;
        }
        this.addPointToList(actualPos);
        return piece != null;
    }

    protected void addPointToList(Point position) {
        if (position.getX() >= 0 && position.getX() < Table.ROW_NUMBER && position.getY() >= 0 && position.getY() < Table.COLUMN_NUMBER) {
            King myKing = table.getKings()[this.player-1];
            if (myKing.isInCheck() && !this.isPossibleValue(position, myKing.getCheckZone())) return;
            this.possibleMoveCoordinates.add(position);
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public int getPlayer() {
        return player;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public ArrayList<Point> getPossibleMoveCoordinates() {
        return possibleMoveCoordinates;
    }
}
