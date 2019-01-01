package fall2018.csc2017.GameCenter.Tiles;

import java.io.Serializable;

import fall2018.csc2017.GameCenter.GameStructure.Board;

/**
 * Class for finding position of blank tile on a board given various conditions.
 */
public class TileFinder implements Serializable {

    /**
     * Board to be used.
     */
    private Board board;

    /**
     * Create a new TileFinder on an empty Board
     */
    public TileFinder() {
    }

    /**
     * Create a new instance of TileFinder that uses the specified Board.
     *
     * @param board Board to be used
     */
    public TileFinder(Board board) {
        this.board = board;
    }

    /**
     * Return the row of the blank tile adjacent to the tile at position or the row of the tile
     * if no blank tile is adjacent to it.
     *
     * @param position position of tile
     * @param blankId  id of blank tile
     * @return row of blank tile
     */
    public int getRowOfBlank(int position, int blankId) {
        if (isBlankTop(position, blankId)) {
            return getRow(position) - 1;
        } else if (isBlankBottom(position, blankId)) {
            return getRow(position) + 1;
        }
        return getRow(position);
    }

    /**
     * Return whether the tile on top of tile at position is blank
     *
     * @param position position of tile
     * @param blankId  id of blank tile
     * @return whether the tile on top of tile at position is blank
     */
    public boolean isBlankTop(int position, int blankId) {
        if (getRow(position) - 1 >= 0) {
            return isBlankTile(getRow(position) - 1, getColumn(position), blankId);
        }
        return false;
    }

    /**
     * Return whether the tile at the bottom of tile at position is blank
     *
     * @param position position of tile
     * @param blankId  id of blank tile
     * @return whether the tile at the bottom of tile at position is blank
     */
    public boolean isBlankBottom(int position, int blankId) {
        if (getRow(position) + 1 < board.getNumRows()) {
            return isBlankTile(getRow(position) + 1, getColumn(position), blankId);
        }
        return false;
    }

    /**
     * Return the column of the blank tile adjacent to tile at position or the column of the current
     * position if no blank tile is adjacent to the tile at position.
     *
     * @param position position of the tile
     * @param blankId  id of blank tile
     * @return the column location of the blank tile adjacent to tile at position
     */
    public int getColOfBlank(int position, int blankId) {
        if (isBlankLeft(position, blankId)) {
            return getColumn(position) - 1;
        } else if (isBlankRight(position, blankId)) {
            return getColumn(position) + 1;
        }
        return getColumn(position);
    }

    /**
     * Return whether tile at (row, col) is blank
     *
     * @param row     row of tile to check
     * @param col     column of tile to check
     * @param blankId id of blank tile
     * @return whether the tile at row and col on the grid is blank
     */
    public boolean isBlankTile(int row, int col, int blankId) {
        return ((SlidingTile) board.getTile(row, col)).getId() == blankId;
    }

    /**
     * Return whether the tile to the left of the tile at position is blank
     *
     * @param position position of tile
     * @param blankId  id of blank tile
     * @return whether the tile to the left of tile at position is blank
     */
    public boolean isBlankLeft(int position, int blankId) {
        if (getColumn(position) - 1 >= 0) {
            return isBlankTile(getRow(position), getColumn(position) - 1, blankId);
        }
        return false;
    }

    /**
     * Return whether the tile to the right of tile at position is blank
     *
     * @param position position of tile
     * @param blankId  id of blank tile
     * @return whether the tile to the right of tile at position is blank
     */
    public boolean isBlankRight(int position, int blankId) {
        if (getColumn(position) + 1 < board.getNumCols()) {
            return isBlankTile(getRow(position), getColumn(position) + 1, blankId);
        }
        return false;
    }

    /**
     * Return the column of tile at position
     *
     * @param position position of tile
     * @return the column of the tile at position
     */
    public int getColumn(int position) {
        return position % board.getNumCols();
    }

    /**
     * Return the row of tile at position
     *
     * @param position position of tile
     * @return the row of the tile at position
     */
    public int getRow(int position) {
        return position / board.getNumRows();
    }

    /**
     * Return the row of the blank tile from the bottom, with the bottom row being 0
     *
     * @return row of blank tile from the bottom
     */
    public int getRowOfBlankFromBottom() {
        int row = 0;
        for (int i = 0; i != board.getNumRows(); i++) {
            for (int j = 0; j != board.getNumCols(); j++) {
                if (((SlidingTile) board.getTile(i, j)).getId() == board.getNumRows() * board.getNumRows()) {
                    row = i;
                }
            }
        }
        return board.getNumRows() - row - 1;
    }

    /**
     * Set the board of this TileFinder to the specified Board object
     *
     * @param board the Board to be used
     */
    public void setBoard(Board board) {
        this.board = board;
    }
}
