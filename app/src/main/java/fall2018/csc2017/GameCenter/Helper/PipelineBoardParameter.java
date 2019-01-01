package fall2018.csc2017.GameCenter.Helper;

import java.util.Random;

/**
 * Track the row and column parameters of a pipeline board.
 */
public class PipelineBoardParameter {

    /**
     * side that has the starting tile
     */
    private int startSide;

    /**
     * column of the starting tile
     */
    private int startCol;

    /**
     * row of the starting tile
     */
    private int startRow;

    /**
     * number of rows in the board
     */
    private int rows;

    /**
     * number of columns in the board
     */
    private int cols;

    /**
     * column of the ending tile
     */
    private int endCol;

    /**
     * row of the ending tile
     */
    private int endRow;

    /**
     * makes a new parameter list with a random starting direction and corresponding
     * starting row and column.
     *
     * @param rows number of rows in the board
     * @param cols number of columns in the board.
     */
    public PipelineBoardParameter(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        Random r = new Random();
        startSide = r.nextInt(4);
        startRow = startCol = endRow = endCol = -1;
        switch (startSide) {
            case 0:
                startRow = 0;
                startCol = r.nextInt(cols);
                endRow = rows - 1;
                break;
            case 1:
                startRow = r.nextInt(rows);
                startCol = cols - 1;
                endCol = 0;
                break;
            case 2:
                startRow = rows - 1;
                startCol = r.nextInt(cols);
                endRow = 0;
                break;
            case 3:
                startRow = r.nextInt(rows);
                startCol = 0;
                endCol = cols - 1;
                break;
        }
    }

    /**
     * Return the number of columns in this board
     *
     * @return number of columns
     */
    public int getCols() {
        return cols;
    }

    /**
     * Return the number of rows in this board
     *
     * @return number of rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * Return the starting column of this board
     *
     * @return starting column
     */
    public int getStartCol() {
        return startCol;
    }

    /**
     * Return the starting row of this board
     *
     * @return starting column
     */
    public int getStartRow() {
        return startRow;
    }

    /**
     * Return the starting side of this board
     *
     * @return starting side
     */
    public int getStartSide() {
        return startSide;
    }

    /**
     * Return the ending column of this board
     *
     * @return ending column
     */
    public int getEndCol() {
        return endCol;
    }

    /**
     * Set the ending column of this board
     *
     * @param endCol new ending column
     */
    public void setEndCol(int endCol) {
        this.endCol = endCol;
    }

    /**
     * Return the ending row of this board
     *
     * @return The rending row of this board
     */
    public int getEndRow() {
        return endRow;
    }

    /**
     * Set the ending row of this board
     *
     * @param endRow new ending row
     */
    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }
}