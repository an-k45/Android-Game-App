package fall2018.csc2017.GameCenter.TilesTest;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

import fall2018.csc2017.GameCenter.R;
import fall2018.csc2017.GameCenter.Tiles.SlidingTile;

/**
 * Test class for testing SlidingTile
 */
public class SlidingTileTest extends TileTest {
    /**
     * Tile object to be used for testing
     */
    private SlidingTile testTile;

    /**
     * Test whether the background of a non blank tile is initialized correctly
     */
    @Test
    @Override
    public void testGetBackground() {
        setUp(4);
        Assert.assertEquals(R.drawable.tile_5, testTile.getBackground());
        tearDown();
    }

    /**
     * Test whether the background of a blank tile is initialized correctly
     */
    @Test
    public void testGetBackgroundBlank() {
        setUp(24);
        assertEquals(R.drawable.tile_blank, testTile.getBackground());
        tearDown();
    }

    /**
     * Test whether the id of a tile is initialized correctly
     */
    @Test
    public void testGetId() {
        setUp(19);
        assertEquals(20, testTile.getId());
        tearDown();
    }

    /**
     * Set up the variables for testing
     *
     * @param id id of tile to be used
     */
    private void setUp(int id) {
        testTile = new SlidingTile(id, "5");
    }

    /**
     * Clear all test variables
     */
    private void tearDown() {
        testTile = null;
    }
}