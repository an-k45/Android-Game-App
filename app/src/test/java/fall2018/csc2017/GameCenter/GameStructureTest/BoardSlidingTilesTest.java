package fall2018.csc2017.GameCenter.GameStructureTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import fall2018.csc2017.GameCenter.GameStructure.BoardSlidingTiles;
import fall2018.csc2017.GameCenter.Tiles.SlidingTile;
import fall2018.csc2017.GameCenter.Tiles.Tile;

import static org.junit.Assert.assertEquals;

/**
 * Test class for testing BoardSlidingTiles.
 */
public class BoardSlidingTilesTest extends BoardTest {


    /**
     * Make a set of tiles that are in order.
     *
     * @return a set of tiles that are in order
     */
    private List<Tile> makeTiles(int rows, int cols) {
        List<Tile> tiles = new ArrayList<>();
        int numTiles = rows * cols;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new SlidingTile(tileNum + 1, tileNum));
        }

        return tiles;
    }


    /**
     * Sets up the board for testing.
     */
    @Before
    public void setUpFourByFour() {
        List<Tile> tiles = makeTiles(4, 4);
        board = new BoardSlidingTiles(tiles, 4, 4);
    }

    /**
     * Clears the board variable.
     */
    @After
    public void tearDown() {
        board = null;
    }

    /**
     * Tests to ensure that two tiles swap with each other properly.
     */
    @Test
    public void testSwapTiles() {
        setUpFourByFour();
        assertEquals(1, ((SlidingTile) board.getTile(0, 0)).getId());
        assertEquals(2, ((SlidingTile) board.getTile(0, 1)).getId());
        board.swapTiles(0, 0, 0, 1);
        assertEquals(2, ((SlidingTile) board.getTile(0, 0)).getId());
        assertEquals(1, ((SlidingTile) board.getTile(0, 1)).getId());
        tearDown();
    }

    /**
     * Tests to ensure that swapping with a tile that does not exist produces an error.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testSwapTilesOutOfBounds() {
        setUpFourByFour();
        board.swapTiles(3, 3, 3, 4);
        tearDown();
    }

    /**
     * Tests to ensure that swapping with the same tile does not produce an error.
     */
    @Test
    public void testSwapTilesSameTile() {
        setUpFourByFour();
        assertEquals(1, ((SlidingTile) board.getTile(0, 0)).getId());
        board.swapTiles(0, 0, 0, 0);
        assertEquals(1, ((SlidingTile) board.getTile(0, 0)).getId());
        tearDown();
    }
}