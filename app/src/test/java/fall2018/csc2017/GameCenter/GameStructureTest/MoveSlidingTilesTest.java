package fall2018.csc2017.GameCenter.GameStructureTest;

import org.junit.Test;

import fall2018.csc2017.GameCenter.GameStructure.MoveSlidingTiles;

import static org.junit.Assert.*;

/**
 * Test class for testing MoveSlidingTiles
 */
public class MoveSlidingTilesTest {
    /**
     * MoveSlidingTiles object to be used for testing.
     */
    private MoveSlidingTiles move;

    /**
     * Set up the variables for testing
     */
    private void setUp() {
        move = new MoveSlidingTiles(2, 5, 3, 1);
    }

    /**
     * Clear all test variables
     */
    private void tearDown() {
        move = null;
    }

    /**
     * Test whether original row is properly initialized.
     */
    @Test
    public void testGetOriginRow() {
        setUp();
        assertEquals(2, move.getOriginRow());
        tearDown();
    }

    /**
     * Test whether original column is properly initialized.
     */
    @Test
    public void testGetOriginCol() {
        setUp();
        assertEquals(5, move.getOriginCol());
        tearDown();
    }

    /**
     * Test whether final row is properly initialized.
     */
    @Test
    public void testGetFinalRow() {
        setUp();
        assertEquals(3, move.getFinalRow());
        tearDown();
    }

    /**
     * Test whether final column is properly initialized.
     */
    @Test
    public void testGetFinalCol() {
        setUp();
        assertEquals(1, move.getFinalCol());
        tearDown();
    }
}
