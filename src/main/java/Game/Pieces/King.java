package Game.Pieces;

import Game.Table;
import Helpers.Directions;
import Helpers.Point;

import java.util.ArrayList;

import static com.diogonunes.jcolor.Ansi.colorize;

public class King extends Piece {

    private boolean inCheck;
    private ArrayList<Point> checkZone;

    public King(Point position, Table table, int player, int team) {
        super(position, table, player, team);
        this.inCheck = false;
        this.checkZone = new ArrayList<>();
        Directions[] tempArr = {Directions.LEFT_DOWN_DIAGONAL, Directions.LEFT_UP_DIAGONAL,
                Directions.RIGHT_DOWN_DIAGONAL, Directions.RIGHT_UP_DIAGONAL, Directions.DOWN, Directions.LEFT,
                Directions.RIGHT, Directions.UP};
        this.fillEnabledDirectionsArray(tempArr);
    }

    @Override
    protected void getPossibleLeftPositions() {
        checkPosition(this.position.getX(), this.position.getY() - 1, null);
    }

    @Override
    protected void getPossibleRightPositions() {
        checkPosition(this.position.getX(), this.position.getY() + 1, null);
    }

    @Override
    protected void getPossibleDownPositions() {
        checkPosition(this.position.getX() + 1, this.position.getY(), null);
    }

    @Override
    protected void getPossibleUpPositions() {
        checkPosition(this.position.getX() - 1, this.position.getY(), null);
    }

    @Override
    protected void getPossibleLeftDownPositions() {
        checkPosition(this.position.getX() + 1, this.position.getY() - 1, null);
    }

    @Override
    protected void getPossibleLeftUpPositions() {
        checkPosition(this.position.getX() - 1, this.position.getY() - 1, null);
    }

    @Override
    protected void getPossibleRightUpPositions() {
        checkPosition(this.position.getX() - 1, this.position.getY() + 1, null);
    }

    @Override
    protected void getPossibleRightDownPositions() {
        checkPosition(this.position.getX() + 1, this.position.getY() + 1, null);
    }

    protected void addPointToList(Point position) {
        if (position.getX() >= 0 && position.getX() < Table.ROW_NUMBER && position.getY() >= 0 && position.getY() < Table.COLUMN_NUMBER) {
            if (this.isInCheck()) {
                if (this.isPossibleValue(position, getCheckZone())) return;
            }
            this.possibleMoveCoordinates.add(position);
        }
    }


    @Override
    public String toString() {
        return colorize("K" + this.player, this.getColorsByPlayerId(this.player));
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
