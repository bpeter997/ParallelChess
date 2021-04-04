package Game.Pieces;

import Game.Table;
import Helpers.Directions;
import Helpers.Point;

import java.util.ArrayList;

public class King extends Piece {

    private boolean inCheck;
    private ArrayList<Point> checkZone;

    public King(Point position, Table table, int player, int team) {
        super(position, table, player, team);
        this.inCheck = true;
        this.checkZone = new ArrayList<Point>();
        Directions[] tempArr = {Directions.LEFT_DOWN_DIAGONAL, Directions.LEFT_UP_DIAGONAL,
                Directions.RIGHT_DOWN_DIAGONAL, Directions.RIGHT_UP_DIAGONAL, Directions.DOWN, Directions.LEFT,
                Directions.RIGHT, Directions.UP};
        this.fillEnabledDirectionsArray(tempArr);
    }

    @Override
    public void tryMove(Point newPos) throws Exception {
        super.tryMove(newPos);
    }

    @Override
    public String toString() {
        return "K" + this.player;
    }

    public boolean isInCheck() {
        return inCheck;
    }

    public void setInCheck(boolean inCheck) {
        this.inCheck = inCheck;
    }

    public ArrayList<Point> getCheckZone() {
        return checkZone;
    }

    public void setCheckZone(ArrayList<Point> checkZone) {
        this.checkZone = checkZone;
    }


}
