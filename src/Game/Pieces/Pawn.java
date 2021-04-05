package Game.Pieces;

import Game.Table;
import Helpers.Directions;
import Helpers.Point;

import java.util.ArrayList;

public class Pawn extends Piece {
    private Point originalPosition;

    public Pawn(Point position, Table table, int player, int team) {
        super(position, table, player, team);
        Directions[] tempArr;
        this.originalPosition = position;
        if (team == 1) {
            tempArr = new Directions[]{Directions.LEFT_DOWN_DIAGONAL, Directions.RIGHT_DOWN_DIAGONAL, Directions.DOWN};
        } else {
            tempArr = new Directions[]{Directions.UP, Directions.RIGHT_UP_DIAGONAL, Directions.LEFT_UP_DIAGONAL};
        }
        this.fillEnabledDirectionsArray(tempArr);
    }

    @Override
    protected void getPossibleDownPositions() {
        if (this.position.equals(originalPosition)) checkStraightMove(this.position.getX() + 2, this.position.getY());
        checkStraightMove(this.position.getX() + 1, this.position.getY());
    }

    @Override
    protected void getPossibleUpPositions() {
        if (this.position.equals(originalPosition)) checkStraightMove(this.position.getX() - 2, this.position.getY());
        checkStraightMove(this.position.getX() - 1, this.position.getY());
    }

    @Override
    protected void getPossibleLeftDownPositions() {
        if (checkPosition(this.position.getX() + 1, this.position.getY() - 1, null)) return;
        if (!isDiagonalMoveKick(this.position.getX() + 1, this.position.getY() - 1) && this.possibleMoveCoordinates.size() > 0) this.possibleMoveCoordinates.remove(this.possibleMoveCoordinates.size()-1);
    }

    @Override
    protected void getPossibleLeftUpPositions() {
        if (checkPosition(this.position.getX() - 1, this.position.getY() - 1, null)) return;
        if (!isDiagonalMoveKick(this.position.getX() - 1, this.position.getY() - 1) && this.possibleMoveCoordinates.size() > 0) this.possibleMoveCoordinates.remove(this.possibleMoveCoordinates.size()-1);
    }

    @Override
    protected void getPossibleRightUpPositions() {
        if (checkPosition(this.position.getX() - 1, this.position.getY() + 1, null)) return;
        if (!isDiagonalMoveKick(this.position.getX() - 1, this.position.getY() + 1) && this.possibleMoveCoordinates.size() > 0) this.possibleMoveCoordinates.remove(this.possibleMoveCoordinates.size()-1);
    }

    @Override
    protected void getPossibleRightDownPositions() {
        if (checkPosition(this.position.getX() + 1, this.position.getY() + 1, null)) return;
        if (!isDiagonalMoveKick(this.position.getX() + 1, this.position.getY() + 1) && this.possibleMoveCoordinates.size() > 0) this.possibleMoveCoordinates.remove(this.possibleMoveCoordinates.size()-1);
    }

    private boolean checkStraightMove(int x, int y) {
       if (x < 0 || x > 7 || y < 0 || y > 15) return true;
       Point actualPos = new Point(x, y);
       Piece piece = table.getPieceOnPosition(actualPos);
       if (piece != null) return true;
       this.addPointToList(actualPos);
       return false;
   }

    private boolean isDiagonalMoveKick(int x,int y) {
        Piece p = table.getPieceOnPosition(new Point(x,y));
        return p != null && p.team != this.team;
    }


    @Override
    public void tryMove(Point newPos) throws Exception {
        super.tryMove(newPos);
    }

    @Override
    public String toString() {
        return "P" + this.player;
    }
}
