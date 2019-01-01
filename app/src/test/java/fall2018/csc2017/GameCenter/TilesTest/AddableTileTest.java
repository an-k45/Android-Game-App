package fall2018.csc2017.GameCenter.TilesTest;

import org.junit.Test;

import fall2018.csc2017.GameCenter.R;
import fall2018.csc2017.GameCenter.Tiles.AddableTile;

import static org.junit.Assert.*;

/**
 * Test class for AddableTile
 */
public class AddableTileTest extends TileTest {
    /**
     * AddableTile object to be used for testing,
     */
    private AddableTile tile;

    /**
     * Set up variables for testing
     */
    private void setUp(int id) {
        tile = new AddableTile(id);
    }

    /**
     * Clear all test variables
     */
    private void tearDown() {
        tile = null;
    }

    /**
     * Test whether a normal tile's background is initialized properly.
     */
    @Test
    @Override
    public void testGetBackground() {
        setUp(4);
        assertEquals(R.drawable.tile_4, tile.getBackground());
        tearDown();
    }

    /**
     * Test whether a blank tile's background is initialized properly.
     */
    @Test
    public void testGetBackgroundBlank() {
        setUp(0);
        assertEquals(R.drawable.tile_blank, tile.getBackground());
        tearDown();
    }

    /**
     * Test whether a tile value is initialized properly.
     */
    @Test
    public void testGetValue() {
        setUp(8);
        assertEquals(8, tile.getValue());
        tearDown();
    }

    /**
     * Test whether setValue properly sets a tile's value.
     */
    @Test
    public void testSetValue() {
        setUp(16);
        tile.setValue(8);
        assertEquals(8, tile.getValue());
    }
}
