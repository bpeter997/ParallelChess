package Helpers.Exceptions;

public class Winner extends Exception {
    public Winner(int team) {
        super("Check mate! Winner is team: " + team);
    }
}
