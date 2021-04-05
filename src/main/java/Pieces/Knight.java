package Pieces;

import Game.Table;
import Helpers.Directions;
import Helpers.Point;

import static com.diogonunes.jcolor.Ansi.colorize;

public class Knight extends Piece {

    public Knight(Point position, Table table, int player, int team) {
        super(position, table, player, team);
        Directions[] tempArr = {Directions.KNIGHT};
        this.fillEnabledDirectionsArray(tempArr);
    }

    @Override
    public void tryMove(Point newPos) throws Exception {
        super.tryMove(newPos);
    }

    @Override
    public String toString() {
        return colorize("♘", this.getColorsByPlayerId(this.player));
    }
}
