package Game.Pieces;

import java.util.Arrays;

import Game.Table;
import Helpers.InvalidMove;
import Helpers.InvalidPiece;
import Helpers.Point;
import Helpers.Directions;

public abstract class Piece {

    protected Point position;
    protected int team;
    protected int player;
    protected Table table;
    protected Directions[] enabledDirections;

    protected Piece(Point position, Table table, int player, int team) {
        this.position = position;
        this.team = team;
        this.player = player;
        this.table = table;
    }

    public void checkMove(Point newPos, int playerTeam) throws Exception {
        if (playerTeam != team)
            throw new InvalidPiece();
        if (newPos.getX() < 0 || newPos.getX() > Table.ROW_NUMBER)
            throw new InvalidMove();
        if (newPos.getY() < 0 || newPos.getY() > Table.COLUMN_NUMBER)
            throw new InvalidMove();
        if (this.position.getX() == newPos.getX() && this.position.getY() == newPos.getY())
            throw new InvalidMove();
        Directions actualMoveDirection = this.getMoveDirection(newPos, this.position);
        if (!Arrays.asList(this.enabledDirections).contains(actualMoveDirection))
            throw new InvalidMove();

    }

    public final void move(Point newPos) {

        Piece p = table.getPieceOnPosition(newPos);
        if (p != null) {
            System.out.println(p.toString() + " " + p.player + " babuja leutve");
        }

        table.removePieceFromPosition(position);
        table.removePieceFromPosition(newPos);
        table.movePieceToPosition(this, newPos);
    }

    protected final void fillEnabledDirectionsArray(Directions[] usableDirections) {
        this.enabledDirections = new Directions[usableDirections.length];
        System.arraycopy(usableDirections, 0, this.enabledDirections, 0, usableDirections.length);
        System.out.println(usableDirections);
        System.out.println(this.enabledDirections);
    }

    private Directions getMoveDirection(Point newPos, Point oldPos) {
        // moving vertically
        if (newPos.getX() == oldPos.getX()) {
            return getVertivalDirection(newPos, oldPos);
            // moving horisontally
        } else if (newPos.getY() == oldPos.getY()) {
            return getHorisontallyDirection(newPos, oldPos);
            // moving diagonally
        } else {
            return getDiagonalDirection(newPos, oldPos);
        }
    }

    private Directions getDiagonalDirection(Point newPos, Point oldPos) {
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

    private Directions getHorisontallyDirection(Point newPos, Point oldPos) {
        if (newPos.getX() > oldPos.getX()) {
            return (this.team == 1) ? Directions.LEFT : Directions.RIGHT;
        } else {
            return (this.team == 1) ? Directions.RIGHT : Directions.LEFT;
        }
    }

    private Directions getVertivalDirection(Point newPos, Point oldPos) {
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

}
