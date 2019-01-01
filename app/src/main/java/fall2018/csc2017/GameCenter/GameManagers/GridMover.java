package fall2018.csc2017.GameCenter.GameManagers;

import java.io.Serializable;

import fall2018.csc2017.GameCenter.GameStructure.Board4096;
import fall2018.csc2017.GameCenter.Tiles.AddableTile;

/**
 * Class for shifting a Board4096 object up, down, left and right
 */
public class GridMover implements Serializable {

    /**
     * Board that the mover acts on.
     */
    private Board4096 board;

    /**
     * Create a new instance of a GridMover
     */
    public GridMover() {
    }

    /**
     * Shift all tiles on this mover's board left and combine any adjacent tiles with equal values.
     * (Combination occurs once per row and column, so 2 2 2 2 produces 4 2 2 0).
     */
    public void shiftBoardLeft() {
        shiftGridLeft(board.getNumRows() - 1, board.getNumCols() - 1);
        combineAdjacentLeftOf(board.getNumRows() - 1, board.getNumCols() - 1);
    }

    /**
     * Shift all tiles on this mover's board right and combine any adjacent tiles with equal values.
     * (Combination occurs once per row and column, so 2 2 2 2 produces 0 2 2 4).
     */
    public void shiftBoardRight() {
        shiftGridRight(board.getNumRows() - 1);
        combineAdjacentRightOf(board.getNumRows() - 1);
    }

    /**
     * Shift all tiles on this mover's board down and combine any adjacent tiles with equal values.
     * (Combination occurs once per row and column, so column 2 2 2 2 produces 0 2 2 4).
     */
    public void shiftBoardDown() {
        shiftGridDown(board.getNumCols() - 1);
        combineAdjacentBelow(board.getNumCols() - 1);
    }

    /**
     * Shift all tiles on this mover's board up and combine any adjacent tiles with equal values.
     * (Combination occurs once per row and column, so column 2 2 2 2 produces 4 2 2 0).
     */
    public void shiftBoardUp() {
        shiftGridUp(board.getNumRows() - 1, board.getNumCols() - 1);
        combineAdjacentAbove(board.getNumRows() - 1, board.getNumCols() - 1);
    }

    /**
     * Set this instance's board to the given board.
     *
     * @param board new board to be used
     */
    public void setBoard(Board4096 board) {
        this.board = board;
    }

    /**
     * Return this instance's board.
     *
     * @return this instance's board
     */
    public Board4096 getBoard() {
        return board;
    }

    /**
     * Combine all adjacent tiles inside every column and row checking the tile above in row and
     * column up to endRow and endCol on this mover's board and shift them after combination to
     * fill the created empty gaps.
     *
     * @param endRow last row of tiles to be checked for possible combinations
     * @param endCol last row of tiles to be checked for possible combinations
     */
    private void combineAdjacentAbove(int endRow, int endCol) {
        for (int col = 0; col <= endCol; col++) {
            int colRowLimit = 0;
            for (int row = 1; row <= endRow; row++) {
                combineColumnFromBottom(col, row, colRowLimit);
                colRowLimit++;
            }
        }
    }

    /**
     * Combine adjacent tiles inside the given column on this mover's board going from bottom to top
     * and shift all tiles inside the column up after combination to fill in the gaps starting
     * with the tile at rowInColumn of the column and checking any tiles below row rowLimit.
     *
     * @param column      column to combine and shift tiles in
     * @param rowInColumn starting row inside the column being checked
     * @param rowLimit    lowest(numerically) row of the tile in the column to be considered for
     *                    combinations
     */
    private void combineColumnFromBottom(int column, int rowInColumn, int rowLimit) {
        while (rowInColumn > rowLimit) {
            if (rowInColumn > 0 && isEqualValueTiles(rowInColumn, column,
                    rowInColumn - 1, column)) {
                board.combineTiles(rowInColumn, column, rowInColumn - 1, column);
            } else if (rowInColumn > 0 && isZeroTile(rowInColumn - 1, column)) {
                board.swapTiles(rowInColumn, column, rowInColumn - 1, column);
            }
            rowInColumn--;
        }
    }


    /**
     * Combine tiles on this mover's board in every column up to and including numCols, going up
     * from below and shift tiles that were not combined to fill in the created gaps.
     *
     * @param numCols last column to combine tiles in
     */
    private void combineAdjacentBelow(int numCols) {
        for (int col = 0; col <= numCols; col++) {
            int colRowLimit = numCols;
            for (int row = 2; row >= 0; row--) {
                combineColumnFromTop(col, row, colRowLimit);
                colRowLimit--;
            }
        }
    }

    /**
     * Combine adjacent tiles with the same value in column col of this mover's board, starting the
     * check with the tile at row rowInCol inside the column and going up until rowLimit is reached.
     *
     * @param col      column to combine adjacent tiles in
     * @param rowInCol starting row inside the column being checked
     * @param rowLimit first top most row to not be checked for combinations (everything below it
     *                 is considered)
     */
    private void combineColumnFromTop(int col, int rowInCol, int rowLimit) {
        while (rowInCol < rowLimit) {
            if (rowInCol < 3 && isEqualValueTiles(rowInCol, col, rowInCol + 1, col)) {
                board.combineTiles(rowInCol, col, rowInCol + 1, col);
            } else if ((rowInCol < board.getNumRows() - 1) && isZeroTile(rowInCol + 1, col)) {
                board.swapTiles(rowInCol, col, rowInCol + 1, col);
            }
            rowInCol++;
        }
    }

    /**
     * Combine adjacent tiles with the same values inside the rows on this mover's board going from
     * left to right inside each row and shift tiles left to fill in gaps after combination.
     *
     * @param numRows last row to combine tiles in
     */
    private void combineAdjacentRightOf(int numRows) {
        for (int row = 0; row <= numRows; row++) {
            int rowColLimit = numRows;
            for (int col = 2; col >= 0; col--) {
                combineRowFromLeft(row, col, rowColLimit);
                rowColLimit--;
            }
        }
    }

    /**
     * Combine adjacent tiles with same values on this mover's board inside the row row starting
     * at column colInRow and going right until colLimit is reached, shifting tiles on space to the
     * right to fill in the created gaps.
     *
     * @param row      row to combine tiles in
     * @param colInRow starting column inside the row being checked for tiles to combine
     * @param colLimit first column to not have its tiles checked for combinations
     */
    private void combineRowFromLeft(int row, int colInRow, int colLimit) {
        while (colInRow < colLimit) {
            if ((colInRow < board.getNumCols() - 1) && isEqualValueTiles(row, colInRow, row, colInRow + 1)) {
                board.combineTiles(row, colInRow, row, colInRow + 1);
            } else if (isZeroTile(row, colInRow + 1)) {
                board.swapTiles(row, colInRow, row, colInRow + 1);
            }
            colInRow++;
        }
    }

    /**
     * Combine all adjacent tiles with equal values in all rows of this mover's board going from
     * left to right in each row and then shifting the tiles that were not combined in order to
     * fill in the empty gaps created after combinations.
     *
     * @param numRows last row checked for combinations
     * @param numCols last column checked for combinations
     */
    private void combineAdjacentLeftOf(int numRows, int numCols) {
        for (int row = 0; row <= numRows; row++) {
            for (int col = 1; col <= numCols; col++) {
                combineRowFromRight(row, col, col);
            }
        }
    }

    /**
     * Combine adjacent tiles with equal values on this mover's board inside a row, going from
     * right to left, shuffling tiles that have not been combined to fill in the gaps created by
     * combinations.
     *
     * @param row      row to combine tiles in
     * @param colLimit leftmost column inside the row to be considered
     * @param colInRow starting column position inside the row
     */
    private void combineRowFromRight(int row, int colLimit, int colInRow) {
        while (colInRow >= colLimit) {
            if (isEqualValueTiles(row, colInRow, row, colInRow - 1)) {
                board.combineTiles(row, colInRow, row, colInRow - 1);
            } else if (isZeroTile(row, colInRow - 1)) {
                board.swapTiles(row, colInRow, row, colInRow - 1);
            }
            colInRow--;
        }
    }

    /**
     * Shift the tiles in every row above and including endRow and in any column before and
     * including endColumn this mover's board up, until the tile is combined with another,
     * reaches the top edge of the board or encounters a tile with a different value from its own.
     *
     * @param endRow lowest row to be shifted
     * @param endCol rightmost column to be shifted
     */
    private void shiftGridUp(int endRow, int endCol) {
        for (int col = 0; col <= endCol; col++) {
            for (int row = 1; row <= endRow; row++) {
                shiftColumnUp(row, col);
            }
        }
    }

    /**
     * Shift all tiles in the column currCol of this mover's board up, starting with the tile at
     * startRow.
     *
     * @param startRow the row to start shifting tiles at
     * @param currCol  column the tiles of which are to be shifted
     */
    private void shiftColumnUp(int startRow, int currCol) {
        while (startRow != 0) {
            if (isZeroTile(startRow - 1, currCol)) {
                board.swapTiles(startRow, currCol, startRow - 1, currCol);
            }
            startRow--;
        }
    }

    /**
     * Shift the tiles in every column before and including endColumn of this mover's board down,
     * until the tile is combined with another, reaches the bottom edge of the board or encounters a
     * tile with a different value from its own.
     *
     * @param endCol rightmost column to be shifted
     */
    private void shiftGridDown(int endCol) {
        for (int col = 0; col <= endCol; col++) {
            for (int row = 2; row >= 0; row--) {
                shiftColumnDown(row, col);
            }
        }
    }

    /**
     * Shift all tiles in the column currCol of this mover's board down, starting with the tile
     * at startRow.
     *
     * @param startRow the row to start shifting tiles at
     * @param currCol  column the tiles of which are to be shifted
     */
    private void shiftColumnDown(int startRow, int currCol) {
        while (startRow < board.getNumRows() - 1) {
            if (isZeroTile(startRow + 1, currCol)) {
                board.swapTiles(startRow, currCol, startRow + 1, currCol);
            }
            startRow++;
        }
    }

    /**
     * Shift the tiles in every row above and including endRow and in any column before and
     * including endColumn of this mover's board left, until the tile is combined with another,
     * reaches the left edge of the board or encounters a tile with a different value from its own.
     *
     * @param endRow lowest row to shift left
     * @param endCol column of rightmost tile in the row to be shifted left
     */
    private void shiftGridLeft(int endRow, int endCol) {
        for (int row = 0; row <= endRow; row++) {
            for (int col = 1; col <= endCol; col++) {
                shiftRowLeft(row, col);
            }
        }
    }

    /**
     * Shift every tile on this mover's board in row currRow left starting with the tile with
     * column startCol inside the row.
     *
     * @param currRow  row to be shifted
     * @param startCol column value of the first tile in the row to be shifted
     */
    private void shiftRowLeft(int currRow, int startCol) {
        while (startCol > 0) {
            if (isZeroTile(currRow, startCol - 1)) {
                board.swapTiles(currRow, startCol, currRow, startCol - 1);
            }
            startCol--;
        }
    }

    /**
     * Shift the tiles in every row above and including endRow of this mover's board right,
     * until the tile is combined with another, reaches the right edge of the board or
     * encounters a tile with a different value from its own.
     *
     * @param endRow lowest row to shift right
     */
    private void shiftGridRight(int endRow) {
        for (int row = 0; row <= endRow; row++) {
            for (int col = 2; col >= 0; col--) {
                shiftRowRight(row, col);
            }
        }
    }

    /**
     * Shift every tile on this mover's board in row currRow right starting with the tile with
     * column startCol inside the row.
     *
     * @param currRow  row to be shifted
     * @param startCol column value of the first tile in the row to be shifted
     */
    private void shiftRowRight(int currRow, int startCol) {
        while (startCol < board.getNumCols() - 1) {
            if (isZeroTile(currRow, startCol + 1)) {
                board.swapTiles(currRow, startCol, currRow, startCol + 1);
            }
            startCol++;
        }
    }

    /**
     * Return if the tile at row1,col1 and row2,col2 on the grid have equal values.
     *
     * @param row1 row of first tile
     * @param col1 column of first tile
     * @param row2 row of second tile
     * @param col2 column of first tile
     * @return true if the two tiles have equal values
     */
    private boolean isEqualValueTiles(int row1, int col1, int row2, int col2) {
        return ((AddableTile) board.getTile(row1, col1)).compareTo(
                (AddableTile) board.getTile(row2, col2)) == 0;
    }

    /**
     * Return whether the tile at row, col on the board has value 0
     *
     * @param row row of tile to check
     * @param col column of tile to check
     * @return true if the tile's value is 0
     */
    private boolean isZeroTile(int row, int col) {
        return ((AddableTile) board.getTile(row, col)).getValue() == 0;
    }
}
