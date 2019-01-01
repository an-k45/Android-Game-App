package fall2018.csc2017.GameCenter.GameManagers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fall2018.csc2017.GameCenter.Accounts.AccountManager;
import fall2018.csc2017.GameCenter.Choose.ProxyBitmap;
import fall2018.csc2017.GameCenter.GameStructure.BoardSlidingTiles;
import fall2018.csc2017.GameCenter.GameStructure.LimitedStack;
import fall2018.csc2017.GameCenter.GameStructure.MoveSlidingTiles;
import fall2018.csc2017.GameCenter.Scoring.Score;
import fall2018.csc2017.GameCenter.ScoringActivities.Scoreable;
import fall2018.csc2017.GameCenter.Tiles.SlidingTile;
import fall2018.csc2017.GameCenter.Tiles.Tile;
import fall2018.csc2017.GameCenter.Tiles.TileFinder;
import fall2018.csc2017.GameCenter.Helper.ArrayListController;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
public class BoardManagerSlidingTiles extends BoardManager implements Scoreable {

    /**
     * List of split images, stored as proxy bitmap data
     */
    private ArrayList<ProxyBitmap> chunks;

    /**
     * Whether or not the board uses a custom image
     */
    private boolean useImage;

    /**
     * Access point to the AccountManager instance
     */
    private AccountManager accManager = AccountManager.getInstance();

    /**
     * ArrayListController to manipulate array lists
     */
    private ArrayListController listController = new ArrayListController();

    /**
     * TileFinder to find blank tiles on the Board.
     */
    private TileFinder tileFinder;

    /**
     * BoardManager for managing a Board
     *
     * @param rows number of rows on the board
     * @param cols number of columns on the board
     */
    public BoardManagerSlidingTiles(int rows, int cols) {
        List<Tile> tiles = new ArrayList<>();

        createUndoStack(rows);

        final int numTiles = rows * cols;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new SlidingTile(tileNum, Integer.toString(rows)));
        }

        tileFinder = new TileFinder();
        setUpSolvableBoard(tiles, rows, cols);
    }

    /**
     * Manage a Board with a user selected image as tiles.
     *
     * @param rows   number of rows on the board
     * @param cols   number of columns on the board
     * @param chunks image chunks to be used
     */
    public BoardManagerSlidingTiles(int rows, int cols, ArrayList<ProxyBitmap> chunks) {
        List<Tile> tiles = new ArrayList<>();
        createUndoStack(rows);
        this.setChunks(chunks);

        final int numTiles = rows * cols;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new SlidingTile(tileNum, Integer.toString(rows)));
        }

        tileFinder = new TileFinder();
        setUpSolvableBoard(tiles, rows, cols);

        this.setUseImage(true);
    }

    @Override
    public boolean puzzleSolved() {
        boolean solved = true;
        ArrayList<Integer> tileIds = new ArrayList<>();

        listController.fillWithInts(tileIds, 1, getBoard().getNumRows() * getBoard().getNumCols());
        removeOrderedTiles(tileIds);

        if (!tileIds.isEmpty()) {
            solved = false;
        } else {
            calculateUserScore();
        }
        return solved;
    }

    @Override
    public boolean isValidMove(int position) {

        int row = position / getBoard().getNumCols();
        int col = tileFinder.getColumn(position);
        int blankId = getBoard().numTiles();
        // Are any of the 4 the blank tile?
        SlidingTile above = row == 0 ? null : (SlidingTile) getBoard().getTile(row - 1, col);
        SlidingTile below = row == getBoard().getNumRows() - 1 ? null : (SlidingTile) getBoard().getTile(row + 1, col);
        SlidingTile left = col == 0 ? null : (SlidingTile) getBoard().getTile(row, col - 1);
        SlidingTile right = col == getBoard().getNumCols() - 1 ? null : (SlidingTile) getBoard().getTile(row, col + 1);
        return (below != null && below.getId() == blankId)
                || (above != null && above.getId() == blankId)
                || (left != null && left.getId() == blankId)
                || (right != null && right.getId() == blankId);
    }

    @Override
    protected void makeMove(int position) {
        int curRow = tileFinder.getRow(position);
        int curCol = tileFinder.getColumn(position);
        int blankRow = tileFinder.getRowOfBlank(position, getBoard().numTiles());
        int blankCol = tileFinder.getColOfBlank(position, getBoard().numTiles());

        this.getBoard().swapTiles(curRow, curCol, blankRow, blankCol);
        this.setMoves(this.getMoves() + 1);
        this.getUndoStack().add(new MoveSlidingTiles(blankRow, blankCol, curRow, curCol));

        this.updateScore(this.getMoves(), this.getBoard().getNumCols());
    }

    /**
     * Remove all tiles that are in correct order on the grid from passed array list
     *
     * @param tileIds array list of correctly arranged tile ids
     */
    private void removeOrderedTiles(ArrayList<Integer> tileIds) {
        int j = 0;
        for (Tile t : getBoard()) {
            if (((SlidingTile) t).getId() == tileIds.get(j)) {
                tileIds.remove(j);
            } else {
                j++;
            }
        }
    }

    /**
     * Initialize an UndoStack to store the proper number of undos based on number of rows.
     *
     * @param rows number of rows in the current board
     */
    private void createUndoStack(int rows) {
        if (rows == 3) {
            this.setUndoStack(new LimitedStack(-1));
        } else if (rows == 4) {
            this.setUndoStack(new LimitedStack(5));
        } else {
            this.setUndoStack(new LimitedStack(3));
        }
    }

    /**
     * Return if this BoardManager's board is solvable according to the following specifications:
     * A board is solvable iff:
     * <p>
     * 1. The grid width is odd and the number of inversions is even.
     * OR
     * 2. The grid width is even, the blank tile is on an even row from the bottom (starting at 1)
     * and the number of inversions is odd.
     * OR
     * 3. The grid width is even, the blank tile is on an odd row from the bottom and the number
     * of inversions is even.
     *
     * @return true if the board can be solved in a finite number of moves, false otherwise
     */
    private boolean solvable() {
        ArrayList<Integer> tileInversions = new ArrayList<>();
        listController.fillListNoBlank(getBoard(), tileInversions);

        int inversions = listController.getInversions(tileInversions);

        //Adopted from https://www.cs.bham.ac.uk/~mdr/teaching/modules04/java2/TilesSolvability.html
        return (((getBoard().getNumCols()) % 2 == 1) && (inversions % 2 == 0))
                ||
                (((getBoard().getNumCols()) % 2 == 0) &&
                        (((tileFinder.getRowOfBlankFromBottom() + 1) % 2 == 1)
                                == (inversions % 2 == 0)));
    }

    /**
     * Keep reshuffling the board until it is solvable in a finite number of moves.
     *
     * @param tiles tiles to go on the board
     * @param rows  number of rows on the board
     * @param cols  number of columns on the board
     */
    private void setUpSolvableBoard(List<Tile> tiles, int rows, int cols) {
        do {
            Collections.shuffle(tiles);
            setBoard(new BoardSlidingTiles(tiles, rows, cols));
            tileFinder.setBoard(getBoard());
        } while (!solvable());
    }

    @Override
    public void updateScore(int moves, int difficulty) {
        if (accManager.currentlyLoggedIn()) {
            if (moves == 0 || moves >= 500) {
                this.setScore(new Score(0, accManager.getCurrentUser()));
            } else {
                this.setScore(new Score((int) (difficulty * 10 * ((-0.2 * moves) + 100)), accManager.getCurrentUser()));
            }
        }
    }

    @Override
    public void calculateUserScore() {
        if (AccountManager.getInstance().currentlyLoggedIn()) {
            this.updateScore(this.getMoves(), this.getBoard().getNumCols());
            if (accManager.getCurrentAccount().getLastManager(0).getScoreObject().getScore() <
                    this.getScoreObject().getScore()) {
                accManager.getCurrentAccount().getLastManager(0).setScore(this.getScoreObject());
            }
        }
    }

    @Override
    public void undoMove() {
        if (!this.getUndoStack().isEmpty()) {
            MoveSlidingTiles move = (MoveSlidingTiles) this.getUndoStack().remove();
            getBoard().swapTiles(move.getOriginRow(), move.getOriginCol(), move.getFinalRow(),
                    move.getFinalCol());
            this.setMoves(this.getMoves() - 1);
        }
        this.updateScore(this.getMoves(), this.getBoard().getNumCols());
        setChanged();
        notifyObservers();
    }

    /**
     * @return the tileFinder object of this board.
     */
    public TileFinder getTileFinder() {
        return tileFinder;
    }

    /**
     * Whether the board uses a custom image or default
     */
    public boolean getUseImage() {
        return useImage;
    }

    /**
     * Set whether the board uses an image or not
     *
     * @param use_image whether the board uses an image or not
     */
    public void setUseImage(boolean use_image) {
        this.useImage = use_image;
    }

    /**
     * Get list of split images, if user uses one, stored as raw data
     */
    public ArrayList<ProxyBitmap> getChunks() {
        return chunks;
    }

    /**
     * Set the image background data
     *
     * @param chunks list of split images
     */
    public void setChunks(ArrayList<ProxyBitmap> chunks) {
        this.chunks = chunks;
    }
}
