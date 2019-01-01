package fall2018.csc2017.GameCenter.GameStructure;

import android.content.Context;
import android.widget.Toast;

import fall2018.csc2017.GameCenter.GameManagers.BoardManager;
import fall2018.csc2017.GameCenter.GameManagers.BoardManager4096;
import fall2018.csc2017.GameCenter.GameManagers.BoardManagerSlidingTiles;

/**
 * Class for handling in game movements using board managers.
 */
public class MovementController {

    /**
     * Constants for swiping directions. Should be an enum, probably.
     */
    public static final int UP = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;
    public static final int RIGHT = 4;

    /**
     * Board manager used to perform in-game movements.
     */
    private BoardManager boardManager = null;

    /**
     * Create a new MovementController.
     */
    public MovementController() {
    }

    /**
     * Set the BoardManager of this MovementController to the specified BoardManager.
     *
     * @param boardManager reference to the BoardManager to be used
     */
    public void setBoardManager(BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    /**
     * Perform a tap movement in the specified context, at the specified position.
     *
     * @param context  context of the tap movement
     * @param position position of the tap on screen
     */
    public void processTapMovement(Context context, int position) {
        // Parameter 'display' must be kept for Android Studio technical tasks.
        processMove(context, position);
        if (boardManager.puzzleSolved()) {
            Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Perform a swipe movement using the board manager in the given context and in the given
     * direction on screen.
     *
     * @param context   context of movement
     * @param direction direction of movement
     */
    public void processSwipeMovement(Context context, int direction) {
        if (boardManager.getClass() == BoardManager4096.class) {
            processMove(context, direction);
            makeEndGameToasts(context);
        }
    }

    /**
     * Make corresponding toasts when the user either solves the puzzle or loses
     *
     * @param context context of the toast
     */
    private void makeEndGameToasts(Context context) {
        if (((BoardManager4096) boardManager).gameLost()) {
            boardManager.calculateUserScore();
            Toast.makeText(context, "GAME OVER. YOUR SCORE IS: " + boardManager.getScoreObject().getScore(), Toast.LENGTH_LONG).show();
        } else if (boardManager.puzzleSolved()) {
            boardManager.calculateUserScore();
            Toast.makeText(context, "GAME WON! YOUR SCORE IS: " + boardManager.getScoreObject().getScore(), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Process a swipe move initiated at the given position, if the move is valid
     *
     * @param context  context of move
     * @param position position where the move was initiated on screen
     */
    private void processMove(Context context, int position) {
        if (boardManager.isValidMove(position)) {
            boardManager.touchMove(position);
        } else if (boardManager.getClass() == BoardManagerSlidingTiles.class &&
                !boardManager.isValidMove(position)) {
            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
        }
    }
}
