package Game.Pieces;

import Game.Table;
import Helpers.Directions;
import Helpers.Point;

public class Knight extends Piece {

    Knight(Point position, Table table, int player, int team) {
        super(position, table, player, team);
        Directions[] tempArr = { Directions.KNIGHT };
        this.fillEnabledDirectionsArray(tempArr);
    }

    @Override
    public void tryMove(Point newPos) throws Exception {
        super.tryMove(newPos);
    }
}
