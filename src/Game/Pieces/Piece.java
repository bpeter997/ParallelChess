package Game.Pieces;

import java.util.Arrays;

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

    protected Piece(Point position, Table table, int player, int team) {
        table.movePieceToPosition(this, position);
        this.position = position;
        this.team = team;
        this.player = player;
        this.table = table;
    }

    public void tryMove(Point newPos) throws Exception {
        if (newPos.getX() < 0 || newPos.getX() > Table.ROW_NUMBER)
            throw new InvalidMove();
        if (newPos.getY() < 0 || newPos.getY() > Table.COLUMN_NUMBER)
            throw new InvalidMove();
        if (this.position.getX() == newPos.getX() && this.position.getY() == newPos.getY())
            throw new InvalidMove();
        Directions actualMoveDirection = this.getMoveDirection(newPos);
        if (!Arrays.asList(this.enabledDirections).contains(actualMoveDirection) || actualMoveDirection == Directions.INVALID)
            throw new InvalidMove();
        this.move(newPos);

    }

    private void move(Point newPos) throws Exception {
        Piece p = table.getPieceOnPosition(newPos);
        if (p != null) {
            if (p.team == this.team) throw new InvalidMove();
            if (p instanceof King) throw new Exception("You cant kick king!");
            System.out.println(p.toString() + " " + p.player + " piece kicked!");
        }

        table.removePieceFromPosition(position);
        table.removePieceFromPosition(newPos);
        table.movePieceToPosition(this, newPos);
    }

    protected final void fillEnabledDirectionsArray(Directions[] usableDirections) {
        this.enabledDirections = new Directions[usableDirections.length];
        System.arraycopy(usableDirections, 0, this.enabledDirections, 0, usableDirections.length);
        System.out.println(Arrays.toString(usableDirections));
        System.out.println(Arrays.toString(this.enabledDirections));
    }

    private Directions getMoveDirection(Point newPos) {
        Point oldPos = this.position;
        // moving vertically
        if (newPos.getX() == oldPos.getX()) {
            return getVerticalDirection(newPos, oldPos);
            // moving horizontally
        } else if (newPos.getY() == oldPos.getY()) {
            return getHorizontallyDirection(newPos, oldPos);
            // moving diagonally
        } else {
            return getOtherDirection(newPos, oldPos);
        }
    }

    private Directions getOtherDirection(Point newPos, Point oldPos) {
        if (Math.abs(newPos.getX() - oldPos.getX()) == Math.abs(newPos.getY() - oldPos.getY())) {
            return getDiagonalDirections(newPos, oldPos);
        } else {
            return getKnightDirection(newPos, oldPos);
        }
    }

    private Directions getKnightDirection(Point newPos, Point oldPos) {
       if ((Math.abs(newPos.getX() - oldPos.getX()) == 2 && Math.abs(newPos.getY() - oldPos.getY()) == 1) || (Math.abs(newPos.getX() - oldPos.getX()) == 1 && Math.abs(newPos.getY() - oldPos.getY()) == 2)) {
           return Directions.KNIGHT;
       }
       return Directions.INVALID;
    }

    private Directions getDiagonalDirections(Point newPos, Point oldPos) {
        if (newPos.getX() > oldPos.getX()) {
            if (newPos.getY() > oldPos.getY()) {
                return (this.team == 1) ? Directions.LEFT_UP_DIAGONAL : Directions.RIGHT_DOWN_DIAGONAL;
            } else {
                return (this.team == 1) ? Directions.LEFT_DOWN_DIAGONAL : Directions.RIGHT_UP_DIAGONAL;
            }
        } else {
            if (newPos.getY() > oldPos.getY()) {
                return (this.team == 1) ? Directions.RIGHT_UP_DIAGONAL : Directions.LEFT_DOWN_DIAGONAL;
            } else {
                return (this.team == 1) ? Directions.RIGHT_DOWN_DIAGONAL : Directions.LEFT_UP_DIAGONAL;
            }
        }
    }

    private Directions getHorizontallyDirection(Point newPos, Point oldPos) {
        if (newPos.getX() > oldPos.getX()) {
            return (this.team == 1) ? Directions.LEFT : Directions.RIGHT;
        } else {
            return (this.team == 1) ? Directions.RIGHT : Directions.LEFT;
        }
    }

    private Directions getVerticalDirection(Point newPos, Point oldPos) {
        if (newPos.getY() > oldPos.getY()) {
            return (this.team == 1) ? Directions.UP : Directions.DOWN;
        } else {
            return (this.team == 1) ? Directions.DOWN : Directions.UP;
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public int getPlayer() {
        return player;
    }
}
