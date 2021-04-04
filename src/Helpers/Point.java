package Helpers;

import Helpers.Exceptions.InvalidCoordinates;

public class Point {
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isEqual(Point other) {
        return (this.getX() == other.getX()) && (this.getY() == other.getY());
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "(" + this.getLetterFromNumber(this.getX()) + "," + this.getY() + "),  ";
    }

    private String getLetterFromNumber(int number) {
        return switch (number) {
            case 0 -> "A";
            case 1 -> "B";
            case 2 -> "C";
            case 3 -> "D";
            case 4 -> "E";
            case 5 -> "F";
            case 6 -> "G";
            case 7 -> "H";
            default -> "?";
        };
    }

}
