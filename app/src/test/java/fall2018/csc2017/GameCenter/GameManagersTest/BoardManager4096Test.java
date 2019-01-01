package fall2018.csc2017.GameCenter.GameManagersTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import fall2018.csc2017.GameCenter.Accounts.AccountManager;
import fall2018.csc2017.GameCenter.GameManagers.BoardManager4096;
import fall2018.csc2017.GameCenter.GameManagers.GridMover;
import fall2018.csc2017.GameCenter.GameStructure.Board4096;
import fall2018.csc2017.GameCenter.GameStructure.MovementController;
import fall2018.csc2017.GameCenter.Scoring.Score;
import fall2018.csc2017.GameCenter.Tiles.AddableTile;
import fall2018.csc2017.GameCenter.Tiles.Tile;

import static org.junit.Assert.*;

/**
 * Test class for BoardManager4096
 */
public class BoardManager4096Test extends BoardManagerTest {

    /**
     * Manager to be used to test the BoardManager4096 class.
     */
    private BoardManager4096 testManager;

    /**
     * Set up the testManager for testing before each test.
     */
    @Before
    public void setUp() {
        testManager = new BoardManager4096(new GridMover(), new Score(0));
    }

    /**
     * Discard the testManager after each test.
     */
    @After
    public void tearDown() {
        testManager = null;
    }

    /**
     * Test that the initial state of a new BoardManager4096 defined in the constructor is correctly
     * set after the constructor executes.
     */
    @Test
    public void testConstructor() {
        assertEquals(0, testManager.getScoreObject().getScore());
        assertNotNull(testManager.getBoard());
        assertEquals(0, testManager.getMoves());
        assertEquals(4, testManager.getBoard().getNumCols());
        assertEquals(4, testManager.getBoard().getNumRows());
    }

    /**
     * Test whether generate board creates the correct board with an AddableTile with value 2
     * at row = 0, col = 0 and a blank tile everywhere else.
     */
    @Test
    public void testGenerateBoard() {
        assertEquals(2,
                ((AddableTile) testManager.getBoard().getTile(0, 0)).getValue());
        assertTrue(allBlank());
    }

    /**
     * Test whether gameLost() correctly identifies a game that is over whent he board is filled
     * and no more moves are possible.
     */
    @Test
    public void testGameLost() {
        assertFalse(testManager.gameLost());
        testManager.setBoard(provideFullBoard(-1, 2));
        assertTrue(testManager.gameLost());
    }

    /**
     * Test whether gameLost() correctly identifies a game that is not over with a filled board,
     * but moves still being possible.
     */
    @Test
    public void testGameLostMovesPossible() {
        testManager.setBoard(provideFullBoard(1, 2));
        assertFalse(testManager.gameLost());
        addTileToBoard(1, 0, 2);
        assertFalse(testManager.gameLost());
    }

    @Override
    @Test
    public void testPuzzleSolved() {
        assertFalse(testManager.puzzleSolved());
    }

    /**
     * Test whether a board that leads to a loss is incorrectly identified as a solved puzzle board.
     */
    @Test
    public void testPuzzleSolvedFullBoard() {
        testManager.setBoard(provideFullBoard(-1, 2));
        assertFalse(testManager.puzzleSolved());
    }

    /**
     * Test whether an unsolved board is considered unsolved after some moves.
     */
    @Test
    public void testPuzzleSolvedSomeMoves() {
        testManager.getMover().setBoard((Board4096) testManager.getBoard());
        assertFalse(testManager.puzzleSolved());
        testManager.touchMove(MovementController.RIGHT);
        assertFalse(testManager.puzzleSolved());
        testManager.touchMove(MovementController.DOWN);
        assertFalse(testManager.puzzleSolved());
    }

    /**
     * Test whether a solved board is correctly identified after the winning move.
     */
    @Test
    public void testPuzzleSolvedWithSolvedMove() {
        addTileToBoard(3, 3, 2048);
        addTileToBoard(3, 2, 2048);
        testManager.getMover().setBoard((Board4096) testManager.getBoard());
        assertFalse(testManager.puzzleSolved());
        testManager.touchMove(MovementController.RIGHT);
        assertTrue(testManager.puzzleSolved());
    }

    @Override
    @Test
    public void testIsValidMove() {
        assertTrue(testManager.isValidMove(-1));
    }

    /**
     * Test whether a move is correctly identified as invalid once the board is completely filled
     * and no moves are left.
     */
    @Test
    public void testIsValidMoveGameLost() {
        testManager.setBoard(provideFullBoard(3, 4));
        assertFalse(testManager.isValidMove(-1));
    }

    /**
     * Test whether a move is correctly identified as invalid once the 4096 tile appears on the
     * board.
     */
    @Test
    public void testIsValidMovePuzzleSolved() {
        addTileToBoard(2, 3, 4096);
        assertFalse(testManager.isValidMove(-1));
    }

    /**
     * Test whether the score is correctly updated by calculateUserScore() after a move is
     * performed.
     */
    @Test
    public void testCalculateUserScore() {
        addTileToBoard(0, 1, 2);
        assertEquals(0, testManager.getScoreObject().getScore());
        testManager.touchMove(MovementController.RIGHT);
        testManager.calculateUserScore();
        assertEquals(4, testManager.getScoreObject().getScore());
    }

    /**
     * Test whether the board manager correctly performs an upwards touchMove using makeMove.
     */
    @Test
    public void testMakeMoveUp() {
        addTileToBoard(3, 1, 2);
        assertEquals(0, ((AddableTile) testManager.getBoard().getTile(0, 1))
                .getValue());
        testManager.touchMove(MovementController.UP);
        assertEquals(2, ((AddableTile) testManager.getBoard().getTile(0, 1))
                .getValue());
        assertEquals(2, ((AddableTile) testManager.getBoard().getTile(0, 0))
                .getValue());
    }

    /**
     * Test whether the board manager correctly performs a downwards touchMove using makeMove.
     */
    @Test
    public void testMakeMoveDown() {
        addTileToBoard(0, 1, 2);
        assertEquals(0, ((AddableTile) testManager.getBoard().getTile(3, 1))
                .getValue());
        assertEquals(0, ((AddableTile) testManager.getBoard().getTile(3, 0))
                .getValue());
        testManager.touchMove(MovementController.DOWN);
        assertEquals(2, ((AddableTile) testManager.getBoard().getTile(3, 1))
                .getValue());
        assertEquals(2, ((AddableTile) testManager.getBoard().getTile(3, 0))
                .getValue());
    }

    /**
     * Test whether the board manager correctly performs a leftward touchMove using makeMove.
     */
    @Test
    public void testMakeMoveLeft() {
        addTileToBoard(0, 3, 4);
        addTileToBoard(3, 3, 4);
        assertEquals(0, ((AddableTile) testManager.getBoard().getTile(0, 1))
                .getValue());
        assertEquals(0, ((AddableTile) testManager.getBoard().getTile(3, 0))
                .getValue());
        testManager.touchMove(MovementController.LEFT);
        assertEquals(4, ((AddableTile) testManager.getBoard().getTile(0, 1))
                .getValue());
        assertEquals(4, ((AddableTile) testManager.getBoard().getTile(3, 0))
                .getValue());
    }

    /**
     * Test whether the board manager correctly performs a rightward touchMove using makeMove.
     */
    @Test
    public void testMakeMoveRight() {
        addTileToBoard(1, 1, 4);
        addTileToBoard(2, 0, 4);
        assertEquals(0, ((AddableTile) testManager.getBoard().getTile(1, 3))
                .getValue());
        assertEquals(0, ((AddableTile) testManager.getBoard().getTile(2, 3))
                .getValue());
        testManager.touchMove(MovementController.RIGHT);
        assertEquals(4, ((AddableTile) testManager.getBoard().getTile(1, 3))
                .getValue());
        assertEquals(4, ((AddableTile) testManager.getBoard().getTile(2, 3))
                .getValue());
        assertEquals(2, ((AddableTile) testManager.getBoard().getTile(0, 3))
                .getValue());
    }

    /**
     * Return whether the board contains blank tiles in each grid space after the first on
     * (top right).
     *
     * @return true if all grid spaces after the top right corner one are filled with blank tiles,
     * false otherwise.
     */
    private boolean allBlank() {
        Tile[][] tempTiles = testManager.getBoard().getTiles();
        for (int i = 1; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (!(((AddableTile) tempTiles[i][j]).getValue() == 0)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Return a full board with a value tile at the specified position.
     *
     * @param position position of value tile on the board
     * @param value    tile value to be used
     * @return a board with
     */
    private Board4096 provideFullBoard(int position, int value) {
        ArrayList<Tile> tempTiles = new ArrayList<>();
        int min = 2;
        int max = 16;
        for (int i = 0; i < 4; i++) {
            for (int j = min; j <= max; j += j) {
                tempTiles.add(new AddableTile(j));
            }
            min += min;
            max += max;
        }
        if (position != -1) {
            tempTiles.set(position, new AddableTile(value));
        }
        return new Board4096(tempTiles, 4, 4);
    }

    /**
     * test the user is set correctly when a board is created
     */
    @Test
    public void testUserSet() {
        AccountManager.getInstance().setCurrentUser("test_user");
        setUp();
        assertEquals(testManager.getScoreObject().getUser(), "test_user");
    }

    /**
     * Ensure that undo cannon be called on a 4096 board
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testNoUndoAllowed() {
        setUp();
        testManager.undoMove();
    }

    /**
     * Add a tile with the specified value to the testing board.
     *
     * @param row   row of tile
     * @param col   column of tile
     * @param value value of tile
     */
    private void addTileToBoard(int row, int col, int value) {
        testManager.getBoard().getTiles()[row][col] = new AddableTile(value);
    }
}
