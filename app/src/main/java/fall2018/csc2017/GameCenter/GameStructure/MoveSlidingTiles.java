package fall2018.csc2017.GameCenter.GameStructure;

import java.io.Serializable;

/**
 * Move information for a single move in game sliding tiles.
 */
public class MoveSlidingTiles extends Move implements Serializable {
    /**
     * The ending row value of the saved move
     */
    private int finalRow;

    /**
     * The ending column value of the saved move
     */
    private int finalCol;

    /**
     * A new MoveSlidingTiles with original coordinates and ending coordinates
     */
    public MoveSlidingTiles(int originRow, int originCol, int finalRow, int finalCol) {
        super(originRow, originCol);
        this.finalRow = finalRow;
        this.finalCol = finalCol;
    }

    /**
     * @return the original row value of the saved move
     */
    public int getOriginRow() {
        return super.getRow();
    }

    /**
     * @return the original column value of the saved move
     */
    public int getOriginCol() {
        return super.getCol();
    }

    /**
     * @return the ending row value of the saved move
     */
    public int getFinalRow() {
        return finalRow;
    }

    /**
     * @return the ending column value of the saved move
     */
    public int getFinalCol() {
        return finalCol;
    }
}
