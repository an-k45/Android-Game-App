package fall2018.csc2017.GameCenter.ActivityControllers;

import android.content.Context;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import fall2018.csc2017.GameCenter.GameManagers.BoardManager;
import fall2018.csc2017.GameCenter.GameStructure.Board;

/**
 * Generic controller for logic of game activities
 */
public class ActivityController {

    /**
     * The board manager.
     */
    protected BoardManager boardManager;


    /**
     * Create a new controller object
     */
    public ActivityController() {

    }

    /**
     * Create buttons of the board
     *
     * @param context the context to create the buttons in
     * @return a list of tile buttons
     */
    public ArrayList<Button> createTileButtons(Context context) {
        Board board = boardManager.getBoard();
        ArrayList<Button> tileButtons = new ArrayList<>();
        for (int row = 0; row != boardManager.getBoard().getNumRows(); row++) {
            for (int col = 0; col != boardManager.getBoard().getNumCols(); col++) {
                Button tmp = new Button(context);
                tmp.setBackgroundResource(board.getTile(row, col).getBackground());
                tileButtons.add(tmp);
            }
        }
        return tileButtons;
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    public void updateTileButtons(ArrayList<Button> tileButtons) {
        Board board = boardManager.getBoard();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / boardManager.getBoard().getNumRows();
            int col = nextPos % boardManager.getBoard().getNumCols();

            b.setBackgroundResource(board.getTile(row, col).getBackground());

            nextPos++;
        }
    }

    /**
     * Undo the last move if it is allowed
     *
     * @param c the context of the activity for the toast to be displayed in
     */
    public void undo(Context c) {
        if (boardManager.getUndoStack().isEmpty()) {
            Toast.makeText(c, "No Undo Available", Toast.LENGTH_SHORT).show();
        } else {
            boardManager.undoMove();
        }
    }

    /**
     * Return the board manager
     *
     * @return board manager object
     */
    public BoardManager getBoardManager() {
        return boardManager;
    }

    /**
     * Set the current board manager object
     *
     * @param boardManager a new board manager
     */
    public void setBoardManager(BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    /**
     * Get the number of columns in the board
     *
     * @return number of columns in the current board
     */
    public int getCols() {
        return boardManager.getBoard().getNumCols();
    }

    /**
     * Get the number of rows in the board
     *
     * @return number of rows in the current board
     */
    public int getRows() {
        return boardManager.getBoard().getNumRows();
    }

    /**
     * Get the current board object
     *
     * @return the current board
     */
    public Board getBoard() {
        return boardManager.getBoard();
    }

    /**
     * Return the number of moves in the stack
     *
     * @return number of moves in move stack
     */
    public int getMoves() {
        return boardManager.getMoves();
    }
}
