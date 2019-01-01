package fall2018.csc2017.GameCenter.TilesTest;

import org.junit.Assert;
import org.junit.Test;

import fall2018.csc2017.GameCenter.R;
import fall2018.csc2017.GameCenter.Tiles.TilePipelines;

import static org.junit.Assert.*;

/**
 * Test class for TilePipelines
 */
public class TilePipelinesTest extends TileTest {
    /**
     * TilePipelines object to be used for testing.
     */
    private TilePipelines testTilePipelines;

    /**
     * Test whether the background of a tile without water is initialized correctly.
     */
    @Test
    @Override
    public void testGetBackground() {
        setUpNormal(new int[]{1, 0, 1, 1}, false);
        Assert.assertEquals(R.drawable.pipetee_2, testTilePipelines.getBackground());
        tearDown();
    }

    /**
     * Test whether the background of a tile with water is initialized correctly.
     */
    @Test
    public void testGetBackgroundHasWater() {
        setUpNormal(new int[]{0, 1, 0, 1}, true);
        assertEquals(R.drawable.pipestraight_2w, testTilePipelines.getBackground());
        tearDown();
    }

    /**
     * Test whether the holes of a tile are initialized correctly.
     */
    @Test
    public void testGetHoles() {
        setUpNormal(new int[]{0, 1, 0, 1}, false);
        assertArrayEquals(new int[]{0, 1, 0, 1}, testTilePipelines.getHoles());
        tearDown();
    }

    /**
     * Test whether an empty tile is initialized properly.
     */
    @Test
    public void testGetHolesEmpty() {
        setUpEmpty();
        assertArrayEquals(new int[]{-1}, testTilePipelines.getHoles());
        tearDown();
    }

    /**
     * Set up the variables for testing with a normal tile.
     *
     * @param id the id of the tile to be used
     * @param on whether the tile has water or not.
     */
    private void setUpNormal(int[] id, boolean on) {
        testTilePipelines = new TilePipelines(id, on);
    }

    /**
     * Set up the variables for testing with an empty tile.
     */
    private void setUpEmpty() {
        testTilePipelines = new TilePipelines();
    }

    /**
     * Clear all test variables.
     */
    private void tearDown() {
        testTilePipelines = null;
    }
}
