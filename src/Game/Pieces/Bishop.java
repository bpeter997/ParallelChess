package Game.Pieces;

import Game.Table;
import Helpers.Point;

public class Bishop extends Piece {
    Bishop(Point position,Table table, int player, int team){
        super(position, table, player, team);
    }

    @Override
    public void checkMove(Point newPos, int playerTeam) throws Exception {
        super.checkMove(newPos, playerTeam);
    }
}