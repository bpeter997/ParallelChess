package Game.Pieces;

import Game.Table;
import Helpers.Directions;
import Helpers.Point;

import static com.diogonunes.jcolor.Ansi.colorize;

public class Rook extends Piece {

    public Rook(Point position, Table table, int player, int team) {
        super(position, table, player, team);
        Directions[] tempArr = { Directions.DOWN, Directions.LEFT,
                Directions.RIGHT, Directions.UP };
        this.fillEnabledDirectionsArray(tempArr);
    }

    @Override
    public void tryMove(Point newPos) throws Exception {
        super.tryMove(newPos);
    }

    @Override
    public String toString() {
        return colorize("R" + this.player, this.getColorsByPlayerId(this.player));
    }
}
