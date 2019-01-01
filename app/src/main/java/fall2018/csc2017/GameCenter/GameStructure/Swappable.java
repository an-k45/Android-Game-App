package fall2018.csc2017.GameCenter.GameStructure;

import java.io.Serializable;

/**
 * Interface implemented by objects that need the ability to swap two items in a 2D array.
 */
public interface Swappable extends Serializable {

    /**
     * Swap the tiles at [row1][col1] and [row2][col2] on the grid
     *
     * @param row1 row of current tile
     * @param col1 column of current tiles
     * @param row2 row of tile to swap with
     * @param col2 column of tile to swap with
     */
    void swapTiles(int row1, int col1, int row2, int col2);
}
