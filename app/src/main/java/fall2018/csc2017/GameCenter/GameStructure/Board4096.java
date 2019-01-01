package fall2018.csc2017.GameCenter.GameStructure;

import android.support.v4.util.SparseArrayCompat;

import java.util.List;
import java.util.Random;

import fall2018.csc2017.GameCenter.Tiles.AddableTile;
import fall2018.csc2017.GameCenter.Tiles.Tile;

/**
 * A board for the 4096 game.
 */
public class Board4096 extends Board {

    /**
     * Total sum of the values of all combined tiles.
     */
    private int combinationSum = 0;

    /**
     * Create a new Board4096 with a list of tiles and the number of rows and columns on the grid
     *
     * @param tiles tiles to be used
     * @param rows  number of rows on the board
     * @param cols  number of columns on the board
     */
    public Board4096(List<Tile> tiles, int rows, int cols) {
        super(tiles, rows, cols);
    }

    @Override
    protected void makeSwap(int row1, int col1, int row2, int col2) {
        if (((AddableTile) this.getTile(row2, col2)).getValue() == 0) {
            swapWithBlank(row1, col1, row2, col2);
        }
        this.getTiles()[row1][col1] = new AddableTile(0);
    }

    /**
     * Create new tile at (row2, col2) on the board with the combined value of tiles at (row1, col1)
     * and (row2, col2) and a blank tile at (row1, col1).
     *
     * @param row1 row of tile to be replaced by a blank
     * @param col1 column of tile to be replaced by a blank
     * @param row2 row of tile to be set to the new combined tile
     * @param col2 column of tile to be set to the new combined tile
     */
    public void combineTiles(int row1, int col1, int row2, int col2) {
        this.getTiles()[row2][col2] = new AddableTile((
                (AddableTile) this.getTile(row1, col1)).getValue() +
                ((AddableTile) this.getTile(row2, col2)).getValue());
        this.getTiles()[row1][col1] = new AddableTile(0);

        combinationSum += ((AddableTile) this.getTile(row2, col2)).getValue();
    }

    /**
     * Swap the tile at row1, col1 with the blank tile (value == 0) at row2, col2
     *
     * @param row1 row of current tile
     * @param col1 column of current tile
     * @param row2 row of blank tile
     * @param col2 column of blank tile
     */
    private void swapWithBlank(int row1, int col1, int row2, int col2) {
        this.getTiles()[row2][col2] = new AddableTile(
                ((AddableTile) this.getTile(row1, col1)).getValue());
    }

    /**
     * Generate a new tile on the board at a randomly chosen empty grid space with a random value
     * of either 2 or 4.
     */
    public void generateNewTile() {
        int[] newTileLocation;
        newTileLocation = findEmptySpace();
        createNewTile(newTileLocation[0], newTileLocation[1]);
    }

    /**
     * Create a new tile at the specified row, col on the grid with a random value of 2 or 4.
     *
     * @param row row of the new tile
     * @param col column of the new tile
     */
    private void createNewTile(int row, int col) {
        Random random = new Random();
        this.getTiles()[row][col] = new AddableTile((random.nextInt(2) + 1) * 2);
    }

    /**
     * Return an array [row, col] that contains the grid coordinates of a random empty grid space
     * on this board.
     *
     * @return an array with the row and column of the empty grid space
     */
    private int[] findEmptySpace() {
        Random rand = new Random();
        SparseArrayCompat<Integer> rowToCol = getEmptyPositions().clone();

        int val = rand.nextInt(rowToCol.size());
        return new int[]{rowToCol.keyAt(val), rowToCol.valueAt(val)};
    }

    /**
     * Return a mapping of row -> column of all grid spaces that contain a tile with value 0.
     *
     * @return a SparseArrayCompat with rows as keys and columns as values
     */
    private SparseArrayCompat<Integer> getEmptyPositions() {
        SparseArrayCompat<Integer> emptyPositions = new SparseArrayCompat<>();
        Tile[][] tempTiles = this.getTiles();

        for (int i = 0; i < tempTiles.length; i++) {
            for (int j = 0; j < tempTiles[i].length; j++) {
                if (((AddableTile) this.getTile(i, j)).getValue() == 0) {
                    emptyPositions.append(i, j);
                }
            }
        }
        return emptyPositions;
    }

    /**
     * Return whether this board is completely filled with tiles or not
     *
     * @return true if there are not empty grid spaces on the board, false otherwise
     */
    public boolean isBoardFull() {
        for (Tile t : this) {
            if (((AddableTile) t).getValue() == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Return if the position of tiles on the board was changed by the latest swipe gesture.
     *
     * @param previousState array of tile values in the previous state
     * @return true if the previousState array values differ form the current tiles on the board,
     * false otherwise
     */
    public boolean boardChanged(Tile[] previousState) {
        int j = 0;
        for (int row = 0; row <= 3; row++) {
            for (int col = 0; col <= 3; col++) {
                if (((AddableTile) this.getTile(row, col)).getValue() !=
                        ((AddableTile) previousState[j]).getValue()) {
                    return true;
                }
                j++;
            }
        }
        return false;
    }

    /**
     * Return the combinationSum of this board.
     *
     * @return current combination sum.
     */
    public int getCombinationSum() {
        return combinationSum;
    }


    /**
     * Return whether this board contains a 4096 tile or not.
     *
     * @return true if the board contains a 4096 tile, false otherwise.
     */
    public boolean containsTile4096() {
        for (Tile t : this) {
            if (((AddableTile) t).getValue() == 4096) {
                return true;
            }
        }
        return false;
    }
}
