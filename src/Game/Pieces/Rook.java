package Game.Pieces;

import Game.Table;
import Helpers.Directions;
import Helpers.InvalidMove;
import Helpers.Point;

public class Rook extends Piece {

    Rook(Point position, Table table, int player, int team) {
        super(position, table, player, team);
        Directions tempArr[] = { Directions.DOWN, Directions.LEFT,
                Directions.RIGHT, Directions.UP };
        this.fillEnabledDirectionsArray(tempArr);
    }

    @Override
    public void checkMove(Point newPos, int playerTeam) throws Exception {
        super.checkMove(newPos, playerTeam);
        if (this.position.getX() != newPos.getX() && this.position.getY() != newPos.getY())
            throw new InvalidMove();
    }
}
