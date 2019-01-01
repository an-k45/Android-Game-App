package fall2018.csc2017.GameCenter.HelperTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import fall2018.csc2017.GameCenter.Helper.PipelineBoardParameter;

import static org.junit.Assert.*;

/**
 * Test class for testing PipelineBoardParameter.
 */
public class PipelineBoardParameterTest {

    /**
     * PipelineBoardParameter object to be used in testing.
     */
    private PipelineBoardParameter testParam;

    /**
     * ArrayList that contains the range of possible values for the randomly generated
     * attributes of testParam.
     */
    private ArrayList<Integer> valueRange = new ArrayList<>();

    /**
     * Set up the testParam used in testing before each test.
     */
    @Before
    public void setUp() {
        testParam = new PipelineBoardParameter(3, 3);
        for (int i = 0; i < 3; i++) {
            valueRange.add(i);
        }
    }

    /**
     * Discard the testParam after each test.
     */
    @After
    public void tearDown() {
        testParam = null;
        valueRange.clear();
    }

    /**
     * Test whether getCols() returns the correct value of columns.
     */
    @Test
    public void getCols() {
        assertEquals(3, testParam.getCols());
    }

    /**
     * Test whether getRows() returns the correct value of rows.
     */
    @Test
    public void getRows() {
        assertEquals(3, testParam.getRows());
    }

    /**
     * Test whether randomly determined value startCol is in the appropriate range of values.
     */
    @Test
    public void getStartCol() {
        assertTrue(valueRange.contains(testParam.getStartCol()));
    }

    /**
     * Test whether randomly determined value startRow is in the appropriate range of values.
     */
    @Test
    public void getStartRow() {
        assertTrue(valueRange.contains(testParam.getStartRow()));
    }

    /**
     * Test whether the randomly decided startSide is in the appropriate range of values.
     */
    @Test
    public void getStartSide() {
        valueRange.add(3);
        assertTrue(valueRange.contains(testParam.getStartSide()));
    }

    /**
     * Test whether getEndCol() returns a value of endCol in the possible range of values.
     */
    @Test
    public void getEndCol() {
        valueRange.add(-1);
        valueRange.remove(1);
        assertTrue(valueRange.contains(testParam.getEndCol()));
    }

    /**
     * Test whether setEndCol() correctly sets the specified variable to the secified value.
     */
    @Test
    public void setEndCol() {
        testParam.setEndCol(0);
        assertEquals(0, testParam.getEndCol());
    }

    /**
     * Test whether getEndRow() returns a value of endRow in the possible range of values.
     */
    @Test
    public void getEndRow() {
        valueRange.add(-1);
        valueRange.remove(1);
        assertTrue(valueRange.contains(testParam.getEndRow()));
    }

    /**
     * Test whether setEndRow() correctly sets the specified variable to the secified value.
     */
    @Test
    public void setEndRow() {
        testParam.setEndRow(0);
        assertEquals(0, testParam.getEndRow());
    }
}