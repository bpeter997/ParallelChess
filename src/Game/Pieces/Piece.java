package Game.Pieces;
import Game.Table;
import Helpers.InvalidMove;
import Helpers.InvalidPiece;
import Helpers.Point;

public abstract class Piece {

    protected Point position;
    protected int team;
    protected int player;
    protected Table table;

    protected Piece(Point position,Table table, int player, int team) {
        this.position = position;
        this.team = team;
        this.player = player;
        this.table = table;
    }

    public void checkMove(Point newPos,int playerTeam) throws Exception {
        if (playerTeam != team)  throw new InvalidPiece();
        if (newPos.getX() < 0 || newPos.getX() > Table.ROW_NUMBER) throw new InvalidMove();
        if (newPos.getY() < 0 || newPos.getY() > Table.COLUMN_NUMBER) throw new InvalidMove();
    }

    public void move(Point newPos) {

        Piece p = table.getPieceOnPosition(newPos);
        if (p != null) {
            System.out.println(p.toString() + " " + p.player + " babuja leutve");
        }

        table.removePieceFromPosition(position);
        table.removePieceFromPosition(newPos);
        table.movePieceToPosition(this, newPos);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
