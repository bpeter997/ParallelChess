package Game.Pieces;

import Game.Table;
import Helpers.Directions;
import Helpers.Point;

public class King extends Piece {

    public King(Point position, Table table, int player, int team) {
        super(position, table, player, team);
        Directions[] tempArr = {Directions.LEFT_DOWN_DIAGONAL, Directions.LEFT_UP_DIAGONAL,
                Directions.RIGHT_DOWN_DIAGONAL, Directions.RIGHT_UP_DIAGONAL, Directions.DOWN, Directions.LEFT,
                Directions.RIGHT, Directions.UP};
        this.fillEnabledDirectionsArray(tempArr);
        this.calcPossibleMoveCoordinates();
    }

    @Override
    public void tryMove(Point newPos) throws Exception {
        super.tryMove(newPos);
    }

    @Override
    public String toString() {
        return "K" + this.player;
    }
}
