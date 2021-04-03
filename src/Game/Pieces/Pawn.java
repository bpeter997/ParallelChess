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
        this.calcPossibleMoveCoordinates();
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
