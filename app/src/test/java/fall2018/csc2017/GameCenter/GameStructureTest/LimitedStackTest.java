package fall2018.csc2017.GameCenter.GameStructureTest;

import org.junit.Test;

import fall2018.csc2017.GameCenter.GameStructure.LimitedStack;
import fall2018.csc2017.GameCenter.GameStructure.MovePipelines;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Test class for testing LimitedStack
 */
public class LimitedStackTest {
    /**
     * LimitedStack object to be used for testing.
     */
    private LimitedStack stack;

    /**
     * Set up variables for testing.
     *
     * @param maxSize the maximum size of the stack.
     */
    private void setUp(int maxSize) {
        stack = new LimitedStack(maxSize);
    }

    /**
     * Clear all test variables
     */
    private void tearDown() {
        stack = null;
    }

    /**
     * Test whether a LimitedStack capped at 0 elements is always empty.
     */
    @Test
    public void testMaxZeroAlwaysEmpty() {
        setUp(0);
        stack.add(new MovePipelines(1, 1));
        assertTrue(stack.isEmpty());
        tearDown();
    }

    /**
     * Test whether a LimitedStack with items is not empty.
     */
    @Test
    public void testNotIsEmpty() {
        setUp(-1);
        stack.add(new MovePipelines(1, 1));
        stack.add(new MovePipelines(2, 2));
        assertFalse(stack.isEmpty());
        tearDown();
    }

    /**
     * Test whether a bounded LimitedStack adds no more than the bound.
     */
    @Test
    public void testBoundedAddNoMore() {
        setUp(2);
        stack.add(new MovePipelines(1, 1));
        stack.add(new MovePipelines(2, 2));
        stack.add(new MovePipelines(3, 3));
        stack.remove();
        stack.remove();
        assertTrue(stack.isEmpty());
    }

    /**
     * Test whether a bounded LimitedStack removes the oldest item on add.
     */
    @Test
    public void testBoundedAddRemovesOldest() {
        setUp(2);
        stack.add(new MovePipelines(1, 1));
        stack.add(new MovePipelines(2, 2));
        stack.add(new MovePipelines(3, 3));
        MovePipelines moveOne = (MovePipelines) stack.remove();
        MovePipelines moveTwo = (MovePipelines) stack.remove();
        assertEquals(3, moveOne.getCol());
        assertEquals(3, moveOne.getRow());
        assertEquals(2, moveTwo.getCol());
        assertEquals(2, moveTwo.getRow());
        assertTrue(stack.isEmpty());
        tearDown();
    }

    /**
     * Test whether an empty LimitedStack returns null on remove.
     */
    @Test
    public void testEmptyRemove() {
        setUp(-1);
        assertNull(stack.remove());
        tearDown();
    }

    /**
     * Test whether a non-empty LimitedStack removes correctly.
     */
    @Test
    public void testNonEmptyRemove() {
        setUp(-1);
        MovePipelines sampleMove1 = new MovePipelines(1, 1);
        MovePipelines sampleMove2 = new MovePipelines(2, 2);
        stack.add(sampleMove1);
        stack.add(sampleMove2);
        MovePipelines remove1 = (MovePipelines) stack.remove();
        MovePipelines remove2 = (MovePipelines) stack.remove();
        assertEquals(sampleMove2, remove1);
        assertEquals(sampleMove1, remove2);
        tearDown();
    }
}
