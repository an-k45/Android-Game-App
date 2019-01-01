package fall2018.csc2017.GameCenter.HelperTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fall2018.csc2017.GameCenter.Helper.Position;

import static org.junit.Assert.*;

/**
 * Test class for testing Position.
 */
public class PositionTest {

    /**
     * Position object used in testing.
     */
    private Position testPos;

    /**
     * Test whether getX() correctly returns the x value of the position.
     */
    @Test
    public void testGetX() {
        assertEquals(3, testPos.getX());
    }

    /**
     * Test whether getY() correctly returns the y value of the position.
     */
    @Test
    public void testGetY() {
        assertEquals(4, testPos.getY());
    }

    /**
     * Test whether incX() correctly increases the value of x when true is passed in.
     */
    @Test
    public void testIncXTrue() {
        testPos.incX(true);
        assertEquals(4, testPos.getX());
    }

    /**
     * Test whether incX() correctly decreases the value of x when false is passed in.
     */
    @Test
    public void testIncXFalse() {
        testPos.incX(false);
        assertEquals(2, testPos.getX());
    }

    /**
     * Test whether incY() correctly increases the value of y when true is passed in.
     */
    @Test
    public void testIncYTrue() {
        testPos.incY(true);
        assertEquals(5, testPos.getY());
    }

    /**
     * Test whether incY() correctly decreases the value of y when false is passed in.
     */
    @Test
    public void testIncYFalse() {
        testPos.incY(false);
        assertEquals(3, testPos.getY());
    }

    /**
     * Create a new position to be used for testing before each test.
     */
    @Before
    public void setUp() {
        testPos = new Position(3, 4);
    }

    /**
     * Discard the current position after each test.
     */
    @After
    public void tearDown() {
        testPos = null;
    }

}