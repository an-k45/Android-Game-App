package fall2018.csc2017.GameCenter.GameManagersTest;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import fall2018.csc2017.GameCenter.GameStructure.Board4096;
import fall2018.csc2017.GameCenter.Tiles.AddableTile;
import fall2018.csc2017.GameCenter.Tiles.Tile;
import fall2018.csc2017.GameCenter.GameManagers.GridMover;

import static org.junit.Assert.*;

/**
 * Test class for testing GridMover.
 */
public class GridMoverTest {
    /**
     * GridMover object to be used for testing.
     */
    private GridMover mover;

    /**
     * Set up variables for testing.
     */
    private void setUp() {
        Board4096 board = new Board4096(generateBoard(), 4, 4);
        mover = new GridMover();
        mover.setBoard(board);
    }

    /**
     * Return a list of tiles to fill the board with at the beginning of a game.
     *
     * @return list of tiles to go on the board.
     */
    private List<Tile> generateBoard() {
        List<Tile> tiles = new ArrayList<>();
        int[] tileValues = new int[]{2, 0, 2, 4, 0, 16, 0, 0, 0, 0, 2, 4, 8, 0, 16, 8};

        for (int i = 0; i < 16; i++) {
            if (tileValues[i] != 0) {
                tiles.add(new AddableTile(tileValues[i]));
            } else {
                tiles.add(new AddableTile(0));
            }
        }
        return tiles;
    }

    /**
     * Clear all test variables.
     */
    private void tearDown() {
        mover = null;
    }

    /**
     * Test whether shiftBoardLeft shifts and merges tiles properly.
     */
    @Test
    public void testShiftBoardLeft() {
        setUp();
        mover.shiftBoardLeft();
        int[][] correctValues = new int[][]{{4, 4, 0, 0}, {16, 0, 0, 0}, {2, 4, 0, 0}, {8, 16, 8, 0}};
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                assertEquals(correctValues[row][col], ((AddableTile) mover.getBoard().getTiles()[row][col]).getValue());
            }
        }
        tearDown();
    }

    /**
     * Test whether shiftBoardLeft ran twice shifts and merges tiles properly.
     */
    @Test
    public void testShiftBoardLeftTwice() {
        setUp();
        mover.shiftBoardLeft();
        mover.shiftBoardLeft();
        int[][] correctValues = new int[][]{{8, 0, 0, 0}, {16, 0, 0, 0}, {2, 4, 0, 0}, {8, 16, 8, 0}};
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                assertEquals(correctValues[row][col], ((AddableTile) mover.getBoard().getTiles()[row][col]).getValue());
            }
        }
        tearDown();
    }

    /**
     * Test whether shiftBoardRight shifts and merges tiles properly.
     */
    @Test
    public void testShiftBoardRight() {
        setUp();
        mover.shiftBoardRight();
        int[][] correctValues = new int[][]{{0, 0, 4, 4}, {0, 0, 0, 16}, {0, 0, 2, 4}, {0, 8, 16, 8}};
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                assertEquals(correctValues[row][col], ((AddableTile) mover.getBoard().getTiles()[row][col]).getValue());
            }
        }
        tearDown();
    }

    /**
     * Test whether shiftBoardRight ran twice shifts and merges tiles properly.
     */
    @Test
    public void testShiftBoardRightTwice() {
        setUp();
        mover.shiftBoardRight();
        mover.shiftBoardRight();
        int[][] correctValues = new int[][]{{0, 0, 0, 8}, {0, 0, 0, 16}, {0, 0, 2, 4}, {0, 8, 16, 8}};
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                assertEquals(correctValues[row][col], ((AddableTile) mover.getBoard().getTiles()[row][col]).getValue());
            }
        }
        tearDown();
    }

    /**
     * Test whether shiftBoardDown shifts and merges tiles properly.
     */
    @Test
    public void testShiftBoardDown() {
        setUp();
        mover.shiftBoardDown();
        int[][] correctValues = new int[][]{{0, 0, 0, 0}, {0, 0, 0, 0}, {2, 0, 4, 8}, {8, 16, 16, 8}};
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                assertEquals(correctValues[row][col], ((AddableTile) mover.getBoard().getTiles()[row][col]).getValue());
            }
        }
        tearDown();
    }

    /**
     * Test whether shiftBoardDown ran twice shifts and merges tiles properly.
     */
    @Test
    public void testShiftBoardDownTwice() {
        setUp();
        mover.shiftBoardDown();
        mover.shiftBoardDown();
        int[][] correctValues = new int[][]{{0, 0, 0, 0}, {0, 0, 0, 0}, {2, 0, 4, 0}, {8, 16, 16, 16}};
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                assertEquals(correctValues[row][col], ((AddableTile) mover.getBoard().getTiles()[row][col]).getValue());
            }
        }
        tearDown();
    }

    /**
     * Test whether shiftBoardUp shifts and merges tiles properly.
     */
    @Test
    public void testShiftBoardUp() {
        setUp();
        mover.shiftBoardUp();
        int[][] correctValues = new int[][]{{2, 16, 4, 8}, {8, 0, 16, 8}, {0, 0, 0, 0}, {0, 0, 0, 0}};
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                assertEquals(correctValues[row][col], ((AddableTile) mover.getBoard().getTiles()[row][col]).getValue());
            }
        }
        tearDown();
    }

    /**
     * Test whether shiftBoardUp ran twice shifts and merges tiles properly.
     */
    @Test
    public void testShiftBoardUpTwice() {
        setUp();
        mover.shiftBoardUp();
        mover.shiftBoardUp();
        int[][] correctValues = new int[][]{{2, 16, 4, 16}, {8, 0, 16, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                assertEquals(correctValues[row][col], ((AddableTile) mover.getBoard().getTiles()[row][col]).getValue());
            }
        }
        tearDown();
    }
}
