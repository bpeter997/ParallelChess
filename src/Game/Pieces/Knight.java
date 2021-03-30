package Game.Pieces;

import Game.Table;
import Helpers.Directions;
import Helpers.Point;

public class Knight extends Piece {

    Knight(Point position, Table table, int player, int team) {
        super(position, table, player, team);
        Directions tempArr[] = { Directions.KNIGHT };
        this.fillEnabledDirectionsArray(tempArr);
    }

    @Override
    public void checkMove(Point newPos, int playerTeam) throws Exception {
        super.checkMove(newPos, playerTeam);
    }
}
