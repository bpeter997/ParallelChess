package Helpers;

public class Point {
    private int x;
    private int y;

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
        return "(" + this.getX() + "," + this.getY() + ")";
    }
}
