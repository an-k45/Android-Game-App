package fall2018.csc2017.GameCenter.GameStructure;

import java.io.Serializable;

/**
 * Class for storing information about moves on the board.
 */
public abstract class Move implements Serializable {
    /**
     * The row location of the recorded move.
     */
    private int row;

    /**
     * The column location of the recorded move.
     */
    private int col;

    /**
     * A new Pipelines game move with coordinates indicated by row and column.
     */
    public Move(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * @return the row location of the recorded move.
     */
    public int getRow() {
        return row;
    }


    /**
     * @return the column location of the recorded move.
     */
    public int getCol() {
        return col;
    }
}
