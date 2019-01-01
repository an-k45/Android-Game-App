package fall2018.csc2017.GameCenter.GameManagersTest;
// Some code adapted from provided tests in A2

import org.junit.After;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fall2018.csc2017.GameCenter.Accounts.Account;
import fall2018.csc2017.GameCenter.Accounts.AccountManager;
import fall2018.csc2017.GameCenter.Choose.ProxyBitmap;
import fall2018.csc2017.GameCenter.GameManagers.BoardManagerSlidingTiles;
import fall2018.csc2017.GameCenter.GameStructure.BoardSlidingTiles;
import fall2018.csc2017.GameCenter.Tiles.SlidingTile;
import fall2018.csc2017.GameCenter.Tiles.Tile;

import static org.junit.Assert.*;

/**
 * Test class for BoardManagerSlidingTiles
 */
public class BoardManagerSlidingTilesTest extends BoardManagerTest {

    /**
     * The board manager for testing.
     */
    private BoardManagerSlidingTiles boardManager;

    /**
     *
     */
    private BoardSlidingTiles board;

    /**
     * Make a set of tiles that are in order.
     *
     * @return a set of tiles that are in order
     */
    private List<Tile> makeTiles(int rows, int cols) {
        ArrayList<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < rows * cols; i++) {
            tiles.add(new SlidingTile(i, Integer.toString(cols * rows)));
        }
        return tiles;
    }

    /**
     * Make a rows x cols solved board.
     *
     * @param rows number of rows on the board
     * @param cols number of cols on the board
     */
    private void setUpCorrect(int rows, int cols) {
        List<Tile> tiles = makeTiles(rows, cols);
        board = new BoardSlidingTiles(tiles, rows, cols);
        boardManager = new BoardManagerSlidingTiles(rows, cols);
        boardManager.setBoard(board);
        boardManager.getTileFinder().setBoard(board);
    }

    /**
     * Clear all test variables.
     */
    @After
    public void tearDown() {
        boardManager = null;
        board = null;
    }

    /**
     * Test if the puzzle has been solved or not and check the score
     */
    @Test
    public void testPuzzleSolved() {
        setUpCorrect(4, 4);
        assertTrue(boardManager.puzzleSolved());
    }

    /**
     * Check a new board is created with an image used
     */
    @Test
    public void testBoardWithImage() {
        ArrayList<ProxyBitmap> chunks = new ArrayList<>();
        boardManager = new BoardManagerSlidingTiles(3, 3, chunks);
        assert boardManager.getUseImage();
        assert boardManager.getChunks().isEmpty();
    }

    /**
     * Test whether an unsolved board recognizes it is unsolved.
     */
    @Test
    public void testPuzzleSolvedUnsolved() {
        setUpCorrect(4, 4);
        boardManager.getBoard().swapTiles(0, 0, 0, 1);
        assertFalse(boardManager.puzzleSolved());
    }

    /**
     * Test whether swapping the first two tiles works.
     */
    @Test
    public void testSwapFirstTwo() {
        setUpCorrect(4, 4);
        assertEquals(1, ((SlidingTile) boardManager.getBoard().getTile(0, 0)).getId());
        assertEquals(2, ((SlidingTile) boardManager.getBoard().getTile(0, 1)).getId());
        boardManager.getBoard().swapTiles(0, 0, 0, 1);
        assertEquals(2, ((SlidingTile) boardManager.getBoard().getTile(0, 0)).getId());
        assertEquals(1, ((SlidingTile) boardManager.getBoard().getTile(0, 1)).getId());
    }

    /**
     * Test whether swapping the last two tiles works.
     */
    @Test
    public void testSwapLastTwo() {
        setUpCorrect(4, 4);
        assertEquals(15, ((SlidingTile) boardManager.getBoard().getTile(3, 2)).getId());
        assertEquals(16, ((SlidingTile) boardManager.getBoard().getTile(3, 3)).getId());
        boardManager.getBoard().swapTiles(3, 3, 3, 2);
        assertEquals(16, ((SlidingTile) boardManager.getBoard().getTile(3, 2)).getId());
        assertEquals(15, ((SlidingTile) boardManager.getBoard().getTile(3, 3)).getId());
    }

    /**
     * Test whether isValidMove works.
     */
    @Test
    public void testIsValidMove() {
        setUpCorrect(4, 4);
        assertTrue(boardManager.isValidMove(11));
        assertTrue(boardManager.isValidMove(14));
        assertFalse(boardManager.isValidMove(10));
    }

    /**
     * Test whether score is updated properly for a normal game.
     */
    @Test
    public void testUpdateScore() {
        AccountManager.getInstance().setCurrentUser("test_user");
        setUpCorrect(4, 4);
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
        setUpCorrect(5, 5);
        boardManager.updateScore(500, 4);
        assertEquals(0, boardManager.getScoreObject().getScore());
        AccountManager.getInstance().setCurrentUser(null);
    }

    /**
     * Ensure that the score at the end of the game is calculated correctly
     */
    @Test
    public void testTotalScore() {
        Account acc = new Account("pass");
        setUpCorrect(4, 4);
        acc.addSlidingManager(boardManager);
        HashMap<String, Account> hash = new HashMap<>();
        hash.put("test_user", acc);
        AccountManager.getInstance().setAccountMap(hash);
        AccountManager.getInstance().setCurrentUser("test_user");
        boardManager.updateScore(500, 4);
        assertEquals(0, boardManager.getScoreObject().getScore());
        boardManager.puzzleSolved();
        boardManager.updateScore(100, 4);
        assertEquals(3200, boardManager.getScoreObject().getScore());
        boardManager.setMoves(100);
        boardManager.puzzleSolved();
        AccountManager.getInstance().setCurrentUser(null);
    }

    /**
     * Test whether an undo is made in a regular game.
     */
    @Test
    public void testUndoMove() {
        setUpCorrect(3, 3);
        assertEquals(9, ((SlidingTile) boardManager.getBoard().getTile(2, 2))
                .getId());
        assertEquals(8, ((SlidingTile) boardManager.getBoard().getTile(2, 1))
                .getId());
        boardManager.touchMove(8);
        boardManager.undoMove();
        assertEquals(9, ((SlidingTile) boardManager.getBoard().getTile(2, 2))
                .getId());
        assertEquals(8, ((SlidingTile) boardManager.getBoard().getTile(2, 1))
                .getId());
    }

    /**
     * Test whether an undo is not made when no moves have occurred.
     */
    @Test
    public void testUndoMoveNoMove() {
        setUpCorrect(3, 3);
        assertEquals(9, ((SlidingTile) boardManager.getBoard().getTile(2, 2))
                .getId());
        assertEquals(8, ((SlidingTile) boardManager.getBoard().getTile(2, 1))
                .getId());
        boardManager.undoMove();
        assertEquals(9, ((SlidingTile) boardManager.getBoard().getTile(2, 2))
                .getId());
        assertEquals(8, ((SlidingTile) boardManager.getBoard().getTile(2, 1))
                .getId());
    }

    /**
     * Test that the save move limit is initialized correctly
     */
    @Test
    public void testGetSaveMoveLimit() {
        setUpCorrect(3, 3);
        int save = boardManager.getSAVE_MOVE_LIMIT();
        assert save == 3;
    }

    /**
     * Test whether a move is properly identified and made by touchMove.
     */
    @Test
    public void testMakeMove() {
        setUpCorrect(3, 3);

        assertEquals(9, ((SlidingTile) boardManager.getBoard().getTile(2, 2))
                .getId());
        boardManager.touchMove(5);
        assertEquals(9, ((SlidingTile) boardManager.getBoard().getTile(1, 2))
                .getId());
        assertEquals(6, ((SlidingTile) boardManager.getBoard().getTile(2, 2))
                .getId());


    }

    /**
     * Test whether board is initialized correctly.
     */
    @Test
    public void testGetBoard() {
        setUpCorrect(3, 3);
        assertSame(board, boardManager.getBoard());
    }
}
