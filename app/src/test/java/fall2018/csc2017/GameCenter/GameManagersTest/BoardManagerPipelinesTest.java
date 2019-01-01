package fall2018.csc2017.GameCenter.GameManagersTest;

import org.junit.After;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fall2018.csc2017.GameCenter.Accounts.Account;
import fall2018.csc2017.GameCenter.Accounts.AccountManager;
import fall2018.csc2017.GameCenter.GameManagers.BoardManagerPipelines;
import fall2018.csc2017.GameCenter.GameStructure.BoardPipelines;
import fall2018.csc2017.GameCenter.Tiles.Tile;
import fall2018.csc2017.GameCenter.Tiles.TilePipelines;
import fall2018.csc2017.GameCenter.Helper.PipelineBoardParameter;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * Test class for BoardManagerPipelines
 */
public class BoardManagerPipelinesTest extends BoardManagerTest {

    /**
     * The board manager for testing.
     */
    private BoardManagerPipelines boardManager;

    /**
     * The board for the game
     */
    private BoardPipelines board;

    /**
     * Make a set of tiles.
     *
     * @return a set of tiles that are in order
     */
    private List<Tile> makeTilesSolved(int rows, int cols) {
        List<Tile> tiles = new ArrayList<>();
        int[] idPlusPipe = {1, 1, 1, 1};
        int numTiles = rows * cols;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new TilePipelines(idPlusPipe, false));
        }

        return tiles;
    }

    /**
     * Make a new list of tiles that is unsolved
     *
     * @param rows number of rows in the board
     * @param cols number of columns i the board
     * @return a list of unsolved tiles
     */
    private List<Tile> makeTilesUnsolved(int rows, int cols) {
        List<Tile> tiles = new ArrayList<>();
        int[] idCornerPipe = {1, 1, 0, 0};
        int numTiles = rows * cols;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new TilePipelines(idCornerPipe, false));
        }

        return tiles;
    }

    /**
     * Make a solved board
     *
     * @param rows number of rows in board
     * @param cols number of cols in board
     */
    private void setUpSolved(int rows, int cols) {
        List<Tile> tiles = makeTilesSolved(rows, cols);
        PipelineBoardParameter para = new PipelineBoardParameter(rows, cols);
        para.setEndRow(rows - 1);
        para.setEndCol(cols - 1);
        board = new BoardPipelines(tiles, para);
        boardManager = new BoardManagerPipelines(rows, cols);
        boardManager.setBoard(board);
    }

    /**
     * Make an unsolved board
     *
     * @param rows number of rows of board
     * @param cols number of cols in board
     */
    private void setUpUnsolved(int rows, int cols) {
        List<Tile> tiles = makeTilesUnsolved(rows, cols);
        PipelineBoardParameter para = new PipelineBoardParameter(rows, cols);
        para.setEndRow(rows - 1);
        para.setEndCol(cols - 1);
        board = new BoardPipelines(tiles, para);
        boardManager = new BoardManagerPipelines(rows, cols);
        boardManager.setBoard(board);
    }

    @After
    public void tearDown() {
        boardManager = null;
    }

    /**
     * Test whether an undo is made in a regular game.
     */
    @Test
    public void testUndoMove() {
        setUpUnsolved(4, 4);
        assertArrayEquals(new int[]{1, 1, 0, 0}, ((TilePipelines) boardManager.getBoard().getTile(1, 1))
                .getHoles());
        boardManager.touchMove(5);
        assertArrayEquals(new int[]{0, 1, 1, 0}, ((TilePipelines) boardManager.getBoard().getTile(1, 1))
                .getHoles());
        boardManager.undoMove();
        assertArrayEquals(new int[]{1, 1, 0, 0}, ((TilePipelines) boardManager.getBoard().getTile(1, 1))
                .getHoles());
    }

    /**
     * Test whether an undo is not made when no moves have occurred.
     */
    @Test
    public void testUndoMoveNoMove() {
        setUpUnsolved(4, 4);
        assertArrayEquals(new int[]{1, 1, 0, 0}, ((TilePipelines) boardManager.getBoard().getTile(1, 1))
                .getHoles());
        boardManager.undoMove();
        assertArrayEquals(new int[]{1, 1, 0, 0}, ((TilePipelines) boardManager.getBoard().getTile(1, 1))
                .getHoles());
    }

    /**
     * Test to make sure that score is updated properly
     */
    @Test
    public void testUpdateScore() {
        AccountManager.getInstance().setCurrentUser("test_user");
        setUpUnsolved(4, 4);
        boardManager.updateScore(100, 4);
        assertEquals(3200, boardManager.getScoreObject().getScore());
        AccountManager.getInstance().setCurrentUser(null);
    }

    /**
     * Test whether score is updated properly for a game that should get a zero score.
     */
    @Test
    public void testUpdateScoreZero() {
        AccountManager.getInstance().setCurrentUser("test_user");
        setUpUnsolved(5, 5);
        boardManager.updateScore(500, 5);
        assertEquals(0, boardManager.getScoreObject().getScore());
        AccountManager.getInstance().setCurrentUser(null);
    }

    /**
     * Ensure that the score at the end of the game is calculated correctly
     */
    @Test
    public void testScoreUpdateOnMove() {
        Account acc = new Account("pass");
        setUpSolved(4, 4);
        acc.addPipelinesManager(boardManager);
        HashMap<String, Account> hash = new HashMap<>();
        hash.put("test_user", acc);
        AccountManager.getInstance().setAccountMap(hash);
        AccountManager.getInstance().setCurrentUser("test_user");
        boardManager.touchMove(4);
        AccountManager.getInstance().setCurrentUser(null);
    }

    /**
     * Test whether board is correctly initialized.
     */
    @Test
    public void testGetBoard() {
        setUpSolved(3, 3);
        assertSame(board, boardManager.getBoard());
    }

    @Override
    public void testIsValidMove() {
        setUpUnsolved(8, 8);
        assertFalse(boardManager.isValidMove(63));
        assertTrue(boardManager.isValidMove(9));
    }

    @Override
    public void testPuzzleSolved() {
        setUpSolved(6, 6);
        assertTrue(boardManager.puzzleSolved());
    }
}