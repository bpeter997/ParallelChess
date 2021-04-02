package Game.Pieces;

import Game.Table;
import Helpers.Directions;
import Helpers.Point;

public class Bishop extends Piece {
    Bishop(Point position, Table table, int player, int team) {
        super(position, table, player, team);
        Directions[] tempArr = { Directions.LEFT_DOWN_DIAGONAL, Directions.LEFT_UP_DIAGONAL,
                Directions.RIGHT_DOWN_DIAGONAL, Directions.RIGHT_UP_DIAGONAL };
        this.fillEnabledDirectionsArray(tempArr);
    }

    @Override
    public void tryMove(Point newPos) throws Exception {
        super.tryMove(newPos);
    }
}
