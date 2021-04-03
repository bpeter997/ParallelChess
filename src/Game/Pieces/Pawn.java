package Game.Pieces;

import Game.Table;
import Helpers.Directions;
import Helpers.Point;

public class Pawn extends Piece {

    public Pawn(Point position, Table table, int player, int team) {
        super(position, table, player, team);
        Directions[] tempArr;
        if (team == 1) {
            tempArr = new Directions[]{Directions.LEFT_DOWN_DIAGONAL, Directions.RIGHT_DOWN_DIAGONAL, Directions.DOWN};
        } else {
            tempArr = new Directions[]{Directions.UP, Directions.RIGHT_UP_DIAGONAL, Directions.LEFT_UP_DIAGONAL};
        }
        this.fillEnabledDirectionsArray(tempArr);
    }

    @Override
    protected void getPossibleDownPositions() {
        checkPosition(this.position.getX() + 1, this.position.getY());
    }

    @Override
    protected void getPossibleUpPositions() {
        checkPosition(this.position.getX() - 1, this.position.getY());
    }

    @Override
    protected void getPossibleLeftDownPositions() {
        if (checkPosition(this.position.getX() + 1, this.position.getY() - 1)) return;
        if (!isDiagonalMoveKick(this.position.getX() + 1, this.position.getY() - 1)) this.possibleMoveCoordinates.remove(this.possibleMoveCoordinates.size()-1);
    }

    @Override
    protected void getPossibleLeftUpPositions() {
        if (checkPosition(this.position.getX() - 1, this.position.getY() - 1)) return;
        if (!isDiagonalMoveKick(this.position.getX() - 1, this.position.getY() - 1)) this.possibleMoveCoordinates.remove(this.possibleMoveCoordinates.size()-1);
    }

    @Override
    protected void getPossibleRightUpPositions() {
        if (checkPosition(this.position.getX() - 1, this.position.getY() + 1)) return;
        if (!isDiagonalMoveKick(this.position.getX() - 1, this.position.getY() + 1)) this.possibleMoveCoordinates.remove(this.possibleMoveCoordinates.size()-1);
    }

    @Override
    protected void getPossibleRightDownPositions() {
        if (checkPosition(this.position.getX() + 1, this.position.getY() + 1)) return;
        if (!isDiagonalMoveKick(this.position.getX() + 1, this.position.getY() + 1)) this.possibleMoveCoordinates.remove(this.possibleMoveCoordinates.size()-1);

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
