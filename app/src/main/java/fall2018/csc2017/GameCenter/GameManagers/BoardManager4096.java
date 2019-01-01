package fall2018.csc2017.GameCenter.GameManagers;

import java.util.ArrayList;
import java.util.List;

import fall2018.csc2017.GameCenter.Accounts.AccountManager;
import fall2018.csc2017.GameCenter.GameStructure.Board4096;
import fall2018.csc2017.GameCenter.GameStructure.MovementController;
import fall2018.csc2017.GameCenter.Scoring.Score;
import fall2018.csc2017.GameCenter.Tiles.AddableTile;
import fall2018.csc2017.GameCenter.Tiles.Tile;

/**
 * Class for managing a board for the 4096 game.
 */
public class BoardManager4096 extends BoardManager {

    /**
     * Grid mover used to shift this manager's board up, down, left and right
     */
    private GridMover mover;

    /**
     * Create a new instance of BoardManager4096 with one filled in tile and the others blank
     */
    public BoardManager4096(GridMover mover, Score score) {
        setBoard(new Board4096(generateBoard(), 4, 4));
        this.mover = mover;
        this.mover.setBoard((Board4096) getBoard());
        this.setScore(score);
        if (AccountManager.getInstance().currentlyLoggedIn()) {
            this.getScoreObject().setUser(AccountManager.getInstance().getCurrentUser());
        }
    }

    /**
     * Return a list of tiles to fill the board with at the beginning of a game.
     *
     * @return list of tiles to go on the board.
     */
    private List<Tile> generateBoard() {
        List<Tile> tiles = new ArrayList<>();

        for (int i = 1; i < 17; i++) {
            if (i == 1) {
                tiles.add(new AddableTile(2));
            } else {
                tiles.add(new AddableTile(0));
            }
        }
        return tiles;
    }

    /**
     * Save the current state of this manager's board into the provided array of tiles.
     *
     * @param tempTiles array to be used to store the current board's tiles
     */
    private void saveBoardState(Tile[] tempTiles) {
        int i = 0;
        for (Tile t : getBoard()) {
            tempTiles[i] = t;
            i++;
        }
    }

    /**
     * Return whether the board has been completely filled with tiles and no further moves are
     * possible.
     *
     * @return true if all grid spaces on the board are filled and no moves are left, false
     * otherwise
     */
    public boolean gameLost() {
        if (((Board4096) getBoard()).isBoardFull()) {
            return noVerticalMoves() && noHorizontalMoves();
        }
        return false;
    }

    /**
     * Return if there are vertical moves possible on this manager's board.
     *
     * @return true if any two vertically adjacent tiles have the same value, false otherwise
     */
    private boolean noVerticalMoves() {
        int col = 0;
        while (col <= 3) {
            for (int row = 1; row <= 3; row++) {
                if (((AddableTile) getBoard().getTile(row, col)).getValue() ==
                        ((AddableTile) getBoard().getTile(row - 1, col)).getValue()) {
                    return false;
                }
            }
            col++;
        }
        return true;
    }

    /**
     * Return whether there are horizontal moves possible on this manager's board.
     *
     * @return true if any two horizontally adjacent tiles have the same value, false otherwise
     */
    private boolean noHorizontalMoves() {
        int row = 0;
        while (row <= 3) {
            for (int column = 1; column <= 3; column++) {
                if (((AddableTile) getBoard().getTile(row, column)).getValue() ==
                        ((AddableTile) getBoard().getTile(row, column - 1)).getValue()) {
                    return false;
                }
            }
            row++;
        }
        return true;
    }

    @Override
    public boolean isValidMove(int position) {
        return !gameLost() && !puzzleSolved();
    }

    @Override
    public void calculateUserScore() {
        this.getScoreObject().setScore(((Board4096) getBoard()).getCombinationSum());
    }

    @Override
    public boolean puzzleSolved() {
        return ((Board4096) getBoard()).containsTile4096();
    }

    @Override
    public void makeMove(int position) {
        Tile[] tempTiles = new Tile[getBoard().getTiles().length * getBoard().getTiles().length];
        saveBoardState(tempTiles);
        switch (position) {
            case MovementController.UP:
                mover.shiftBoardUp();
                break;
            case MovementController.DOWN:
                mover.shiftBoardDown();
                break;
            case MovementController.LEFT:
                mover.shiftBoardLeft();
                break;
            case MovementController.RIGHT:
                mover.shiftBoardRight();
                break;
        }

        if (((Board4096) getBoard()).boardChanged(tempTiles)) {
            ((Board4096) getBoard()).generateNewTile();
            this.setMoves(this.getMoves() + 1);
        }
        setScore(((Board4096) getBoard()).getCombinationSum());
    }

    /**
     * Return the current grid mover begin used by this manager.
     */
    public GridMover getMover() {
        return this.mover;
    }

}
