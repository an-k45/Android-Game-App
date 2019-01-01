package fall2018.csc2017.GameCenter.GameManagers;

import java.io.Serializable;
import java.util.Observable;

import fall2018.csc2017.GameCenter.GameStructure.Board;
import fall2018.csc2017.GameCenter.GameStructure.LimitedStack;
import fall2018.csc2017.GameCenter.Scoring.Score;

/**
 * Abstract implementation of a BoardManager to manage various games in GameCenter
 */
public abstract class BoardManager extends Observable implements Serializable, Moveable {

    /**
     * The board to be managed by this manager.
     */
    private Board board;

    /**
     * A LimitedStack that stores moves that can be undone.
     */
    private LimitedStack undoStack;

    /**
     * Number of moves currently made
     */
    private int moves = 0;

    /**
     * The score stored in this manager.
     */
    private Score score;

    /**
     * The number of  moves after which the game is saved.
     */
    private final int SAVE_MOVE_LIMIT = 3;

    /**
     * Undoes the most recent move, swapping the tiles involved, and reducing move counter by 1.
     * Implementation is subclass specific.
     */
    public void undoMove() {
        throw new UnsupportedOperationException();
    }

    /**
     * Return the number of moves made in this session of sliding tiles
     *
     * @return number of moves made
     */
    public int getMoves() {
        return moves;
    }

    /**
     * Return the number of moves that needs to be made before the game is saved.
     *
     * @return current SAVE_MOVE_LIMIT
     */
    public int getSAVE_MOVE_LIMIT() {
        return SAVE_MOVE_LIMIT;
    }

    /**
     * Set the score of this manager's scoreboard to score
     *
     * @param score new score
     */
    public void setScore(int score) {
        this.score.setScore(score);
    }

    /**
     * Return the current scoreboard of this manager
     *
     * @return scoreboard of this manager
     */
    public Score getScoreObject() {
        return this.score;
    }

    /**
     * Set the score of this manager to a new score object
     *
     * @param score new score
     */
    public void setScore(Score score) {
        this.score = score;
    }

    /**
     * Return the undoStack of this manager
     *
     * @return undoStack of this manager
     */
    public LimitedStack getUndoStack() {
        return this.undoStack;
    }

    /**
     * Set the number of moves performed in this manager
     *
     * @param moves new number of moves
     */
    public void setMoves(int moves) {
        this.moves = moves;
    }

    /**
     * Set the undoStack of this manager to a new undoStack
     *
     * @param undoStack undoStack to be used
     */
    public void setUndoStack(LimitedStack undoStack) {
        this.undoStack = undoStack;
    }

    /**
     * Calculate the score of the current user at the end of a game
     * if they are logged in and check if the current
     * score is greater than the previous score, if it is, set it to the new score.
     */
    public abstract void calculateUserScore();

    @Override
    public void touchMove(int position) {
        makeMove(position);

        setChanged();
        notifyObservers();
    }

    /**
     * Make a move at the specified position on this manager's board.
     *
     * @param position position from which the move was initiated
     */
    protected abstract void makeMove(int position);

    /**
     * Return whether the move attempted is a legal one based on subclass definitions
     *
     * @param position the position of the tile to check, -1 if move affects all tiles at once
     * @return whether the move is legal
     */
    public abstract boolean isValidMove(int position);

    /**
     * Return whether the puzzle has been solved or not based on conditions implemented by
     * subclasses
     *
     * @return whether the puzzle is solved.
     */
    public abstract boolean puzzleSolved();

    /**
     * Return the board being managed by this manager
     *
     * @return board being managed
     */
    public Board getBoard() {
        return this.board;
    }

    /**
     * Set this manager's board to the specified board reference.
     *
     * @param board board reference to use
     */
    public void setBoard(Board board) {
        this.board = board;
    }

}
