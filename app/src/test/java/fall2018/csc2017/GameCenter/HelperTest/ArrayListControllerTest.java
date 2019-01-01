package fall2018.csc2017.GameCenter.HelperTest;


import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

import fall2018.csc2017.GameCenter.GameStructure.BoardSlidingTiles;
import fall2018.csc2017.GameCenter.Tiles.SlidingTile;
import fall2018.csc2017.GameCenter.Helper.ArrayListController;
import fall2018.csc2017.GameCenter.Tiles.Tile;

/**
 * Test class for testing methods inside the ArrayListController class
 */
public class ArrayListControllerTest {

    /**
     * ArrayLists and Board to be used to aid testing
     */
    private ArrayList<Integer> emptyList;
    private ArrayList<Integer> nonEmptyList;
    private BoardSlidingTiles board;

    /**
     * ArrayListController being tested
     */
    private ArrayListController controller = new ArrayListController();

    /**
     * Test whether fillWithInts method clears the ArrayList to be filled
     */
    @Test
    public void testFillWithIntsClearsList() {
        setUp();
        controller.fillWithInts(nonEmptyList, 7, 18);
        assertEquals(12, nonEmptyList.size());
        tearDown();
    }

    /**
     * Test whether fillWidthInts method correctly includes the min and max values in the provided
     * range
     */
    @Test
    public void testFillWithIntsIncludesMinMax() {
        setUp();
        controller.fillWithInts(emptyList, -3, 21);
        assertEquals(-3, (int) emptyList.get(0));
        assertEquals(21, (int) emptyList.get(emptyList.size() - 1));
        tearDown();
    }

    /**
     * Test whether getInversions returns the correct number of inversions in a list
     */
    @Test
    public void testGetInversions() {
        setUp();
        assertEquals(36, controller.getInversions(nonEmptyList));
        nonEmptyList.add(0, -1);
        assertEquals(36, controller.getInversions(nonEmptyList));
        nonEmptyList.add(0, 3);
        assertEquals(40, controller.getInversions(nonEmptyList));
        tearDown();
    }

    /**
     * Test whether the blank tile is correctly left out when an ArrayList is filled with tile ids
     * using fillListNoBlank
     */
    @Test
    public void testFillListNoBlank() {
        setUp();
        controller.fillListNoBlank(board, emptyList);
        assertFalse(emptyList.contains(25));
        tearDown();
    }

    /**
     * Set up the variables required for testing
     */
    private void setUp() {
        ArrayList<Tile> tiles = new ArrayList<>();
        int[] nums = new int[]{3, 4, 9, 6, 5, 10, 2, 11, 32, 15, 0, 7, 1};
        emptyList = new ArrayList<>();
        nonEmptyList = new ArrayList<>();

        fillListFromArray(nums);
        makeBoard(tiles);
    }

    /**
     * Clear all variables used in testing
     */
    private void tearDown() {
        emptyList = null;
        nonEmptyList = null;
        board = null;
    }

    /**
     * Fills nonEmptyList with integers in the nums array.
     *
     * @param nums array that contains integers used to fill nonEmptyList
     */
    private void fillListFromArray(int[] nums) {
        for (int num : nums) {
            nonEmptyList.add(num);
        }
    }

    /**
     * Set up a Board used for testing
     *
     * @param tiles list of tiles to fill the board
     */
    private void makeBoard(ArrayList<Tile> tiles) {
        for (int i = 0; i < 25; i++) {
            tiles.add(new SlidingTile(i, "5"));
        }
        board = new BoardSlidingTiles(tiles, 5, 5);
    }
}