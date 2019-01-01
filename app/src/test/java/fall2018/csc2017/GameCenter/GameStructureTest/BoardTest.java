package fall2018.csc2017.GameCenter.GameStructureTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fall2018.csc2017.GameCenter.GameStructure.Board;
import fall2018.csc2017.GameCenter.GameStructure.BoardSlidingTiles;
import fall2018.csc2017.GameCenter.Tiles.SlidingTile;
import fall2018.csc2017.GameCenter.Tiles.Tile;

import static org.junit.Assert.assertEquals;

/**
 * Test class for Board.
 */
public class BoardTest {

    /**
     * BoardSlidingTiles object to be used for testing.
     */
    Board board;

    private List<Tile> tiles = new ArrayList<>();

    /**
     * Make a set of tiles that are in order.
     */
    private void makeTiles(int rows, int cols) {
        int numTiles = rows * cols;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new SlidingTile(tileNum + 1, tileNum));
        }
    }


    /**
     * Sets up the board for testing.
     */
    @Before
    public void setUpFourByFour() {
        makeTiles(4, 4);
        board = (new BoardSlidingTiles(tiles, 4, 4));
    }

    /**
     * Clears the board variable.
     */
    @After
    public void tearDown() {
        board = null;
    }

    /**
     * Test whether board is the correct number of tiles.
     */
    @Test
    public void testNumTiles() {
        assertEquals(board.numTiles(), 16);
    }

    /**
     * Test whether board records the correct number of columns.
     */
    @Test
    public void getNumCols() {
        assertEquals(board.getNumCols(), 4);
    }

    /**
     * Test whether board records the correct number of rows.
     */
    @Test
    public void getNumRows() {
        assertEquals(board.getNumRows(), 4);
    }

    /**
     * Test whether board returns a string properly.
     */
    @Test
    public void testToString() {
        assertEquals("Board{" + "tiles=" + Arrays.toString(board.getTiles()) + '}', board.toString());
    }
}