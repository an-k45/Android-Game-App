package fall2018.csc2017.GameCenter.GameStructure;

import android.support.annotation.NonNull;

import java.util.NoSuchElementException;
import java.util.Observable;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import fall2018.csc2017.GameCenter.Tiles.Tile;

/**
 * The sliding tiles board.
 */
public abstract class Board extends Observable implements Serializable, Iterable<Tile>, Swappable {

    /**
     * The number of rows.
     */
    private int numRows;

    /**
     * The number of rows.
     */
    private int numCols;

    /**
     * The tiles on the board in row-major order.
     */
    private Tile[][] tiles;

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == numRows * numCols
     *
     * @param tiles the tiles for the board
     */
    public Board(List<Tile> tiles, int rows, int cols) {
        numRows = rows;
        numCols = cols;
        this.tiles = new Tile[numCols][numRows];

        Iterator<Tile> iter = tiles.iterator();

        for (int row = 0; row != numRows; row++) {
            for (int col = 0; col != numCols; col++) {
                this.tiles[row][col] = iter.next();
            }
        }
    }

    /**
     * Swap tile at row1, col1 and row2, col2 on the grid
     *
     * @param row1 row of current tile
     * @param col1 column of current tile
     * @param row2 row of tile to swap with
     * @param col2 column of tile to swap with
     */
    protected abstract void makeSwap(int row1, int col1, int row2, int col2);

    /**
     * Return the number of tiles on the board.
     *
     * @return the number of tiles on the board
     */
    public int numTiles() {
        return this.tiles.length * this.tiles[0].length;
    }

    /**
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    public Tile getTile(int row, int col) {
        return tiles[row][col];
    }

    /**
     * Return the number of columns this board has.
     *
     * @return number of columns on this board
     */
    public int getNumCols() {
        return numCols;
    }

    /**
     * Return the number of rows this board has.
     *
     * @return number of rows on this board
     */
    public int getNumRows() {
        return numRows;
    }

    /**
     * Return the 2d tile array that represents this board.
     *
     * @return array of tiles on the grid
     */
    public Tile[][] getTiles() {
        return this.tiles;
    }

    @Override
    public void swapTiles(int row1, int col1, int row2, int col2) {
        makeSwap(row1, col1, row2, col2);
    }

    @Override
    public String toString() {
        return "Board{" +
                "tiles=" + Arrays.toString(tiles) +
                '}';
    }

    @NonNull
    @Override
    public Iterator<Tile> iterator() {
        return new TileIterator();
    }

    /**
     * Custom Iterator used by Board.
     */
    class TileIterator implements Iterator<Tile> {
        /**
         * The row of the tile that the iterator is currently on
         */
        private int currRow;
        /**
         * The column of the tile that the iterator is currently on
         */
        private int currCol = -1;

        @Override
        public boolean hasNext() {
            return currRow != numRows - 1 || currCol != numRows - 1;
        }

        @Override
        public Tile next() {
            if (hasNext()) {
                if (currCol < numCols - 1) {
                    currCol++;
                    return tiles[currRow][currCol];
                } else {
                    currRow++;
                    currCol = 0;
                    return tiles[currRow][currCol];
                }
            }
            throw new NoSuchElementException();
        }
    }
}
