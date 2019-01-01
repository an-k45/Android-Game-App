package fall2018.csc2017.GameCenter.GameManagersTest;

import org.junit.Test;

/**
 * Abstract test class for abstract class BoardManager
 */
public abstract class BoardManagerTest {

    /**
     * Test whether inputted moves are recognized as valid or invalid properly.
     */
    @Test
    public abstract void testIsValidMove();

    /**
     * Test whether the board is recognized as solved only when it is.
     */
    @Test
    public abstract void testPuzzleSolved();
}
