package fall2018.csc2017.GameCenter.Helper;

/**
 * Class for storing a position of a point on a grid.
 */
public class Position {

    /**
     * Horizontal coordinate of point.
     */
    private int x;

    /**
     * Vertical coordinate of point.
     */
    private int y;

    /**
     * Create a new position with coordinates x, y.
     *
     * @param x x coordinate of point
     * @param y y coordinate of point
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Set the x coordinate of this position.
     * Increment if true, decrement if false
     *
     * @param plus whether to increment x
     */
    public void incX(boolean plus) {
        this.x = plus ? x + 1 : x - 1;
    }

    /**
     * Set the y coordinate of this position.
     * Increment if true, decrement if false
     *
     * @param plus whether to increment y
     */
    public void incY(boolean plus) {
        this.y = plus ? y + 1 : y - 1;
    }

    /**
     * Return the x coordinate of this position
     *
     * @return x coordinate of the position
     */
    public int getX() {
        return this.x;
    }

    /**
     * Return the y coordinate of this position.
     *
     * @return y coordinate of the position
     */
    public int getY() {
        return this.y;
    }
}
