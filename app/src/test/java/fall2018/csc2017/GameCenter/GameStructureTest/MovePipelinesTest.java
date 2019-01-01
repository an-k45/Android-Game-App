package fall2018.csc2017.GameCenter.GameStructureTest;

import org.junit.Test;

import fall2018.csc2017.GameCenter.GameStructure.MovePipelines;

import static org.junit.Assert.*;

/**
 * Test class of testing MovePipelines
 */
public class MovePipelinesTest {
    /**
     * MovePipelines object to be used for testing.
     */
    private MovePipelines move;

    /**
     * Set up the variables for testing.
     */
    private void setUp() {
        move = new MovePipelines(2, 3);
    }

    /**
     * Clear all test variables.
     */
    private void tearDown() {
        move = null;
    }

    /**
     * Test whether row is initialized correctly.
     */
    @Test
    public void testGetRow() {
        setUp();
        assertEquals(2, move.getRow());
        tearDown();
    }

    /**
     * Test whether column is initialized correctly.
     */
    @Test
    public void testGetCol() {
        setUp();
        assertEquals(3, move.getCol());
        tearDown();
    }
}
