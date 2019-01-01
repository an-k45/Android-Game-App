package fall2018.csc2017.GameCenter.TilesTest;

import org.junit.After;
import org.junit.Test;

import java.util.ArrayList;

import fall2018.csc2017.GameCenter.GameStructure.BoardSlidingTiles;
import fall2018.csc2017.GameCenter.Tiles.SlidingTile;
import fall2018.csc2017.GameCenter.Tiles.Tile;
import fall2018.csc2017.GameCenter.Tiles.TileFinder;

import static org.junit.Assert.*;

/**
 * Test class for testing TileFinder.
 */
public class TileFinderTest {

    /**
     * TileFinder object to be used for testing methods inside the class.
     */
    private TileFinder testFinder;

    /**
     * Sliding tiles board to test the TileFinder on.
     */
    private BoardSlidingTiles board;

    /**
     * ArrayList to be used to populate the board with tiles.
     */
    private ArrayList<Tile> tileList = new ArrayList<>();

    /**
     * Test whether the absence of a blank tile above a tile is correctly identified.
     */
    @Test
    public void testIsBlankTopNoBlankAbove() {
        setUpRegularBoard();
        assertFalse(testFinder.isBlankTop(11, 16));
        assertFalse(testFinder.isBlankTop(9, 16));
    }

    /**
     * Test whether the presence of a blank tile above another is correctly identified.
     */
    @Test
    public void testIsBlankTopBlankAbove() {
        setUpSpecialBoard(9);
        assertTrue(testFinder.isBlankTop(13, 16));
    }

    /**
     * Test whether the absence of a blank tile in a non existent grid row is correctly identified
     * on the top row.
     */
    @Test
    public void testIsBlankTopNoRowAbove() {
        setUpRegularBoard();
        assertFalse(testFinder.isBlankTop(0, 16));
    }

    /**
     * Test whether the absence of a blank tile above is correctly identified above the blank tile.
     */
    @Test
    public void testIsBlankTopOnBlank() {
        setUpRegularBoard();
        assertFalse(testFinder.isBlankTop(15, 16));
    }

    /**
     * Test whether the presence of a blank tile below another is correctly identified.
     */
    @Test
    public void testIsBlankBottomBlankBelow() {
        setUpRegularBoard();
        assertTrue(testFinder.isBlankBottom(11, 16));
    }

    /**
     * Test whether the absence of a blank tile below a tile is correctly identified.
     */
    @Test
    public void testIsBlankBottomNoBlankBelow() {
        setUpRegularBoard();
        assertFalse(testFinder.isBlankBottom(5, 16));
    }

    /**
     * Test whether the absence of a blank tile in a non existent grid row is correctly identified
     * on the bottom row.
     */
    @Test
    public void testIsBlankBottomNoRowBelow() {
        setUpRegularBoard();
        assertFalse(testFinder.isBlankBottom(14, 16));
    }

    /**
     * Test whether the absence of a blank tile below is correctly identified below the blank tile.
     */
    @Test
    public void testIsBlankBottomOnBlank() {
        setUpRegularBoard();
        assertFalse(testFinder.isBlankBottom(15, 16));
    }

    /**
     * Test whether the absence of a blank tile to the left of  a tile is correctly identified.
     */
    @Test
    public void testIsBlankLeftNoBlankOnLeft() {
        setUpRegularBoard();
        assertFalse(testFinder.isBlankLeft(14, 16));
        assertFalse(testFinder.isBlankLeft(5, 16));
    }

    /**
     * Test whether the presence of a blank tile to the left of another is correctly identified.
     */
    @Test
    public void testIsBlankLeftBlankOnLeft() {
        setUpSpecialBoard(0);
        assertTrue(testFinder.isBlankLeft(1, 16));
    }

    /**
     * Test whether the absence of a blank tile in a non existent grid column is correctly
     * identified on the left most column.
     */
    @Test
    public void testIsBlankLeftNoColOnLeft() {
        setUpRegularBoard();
        assertFalse(testFinder.isBlankLeft(0, 16));
    }

    /**
     * Test whether the absence of a blank tile on the left is correctly identified to the left of
     * the blank tile.
     */
    @Test
    public void testIsBlankLeftOnBlank() {
        setUpRegularBoard();
        assertFalse(testFinder.isBlankLeft(15, 16));
    }

    /**
     * Test whether the absence of a blank tile to the right of a tile is correctly identified.
     */
    @Test
    public void testIsBlankRightNoBlank() {
        setUpRegularBoard();
        assertFalse(testFinder.isBlankRight(9, 16));
    }

    /**
     * Test whether the presence of a blank tile to the right of another is correctly identified.
     */
    @Test
    public void testIsBlankRightBlankOnRight() {
        setUpRegularBoard();
        assertTrue(testFinder.isBlankRight(14, 16));
        tearDown();
        setUpSpecialBoard(6);
        assertTrue(testFinder.isBlankRight(5, 16));
    }

    /**
     * Test whether the absence of a blank tile in a non existent grid column is correctly
     * identified on the right most column.
     */
    @Test
    public void testIsBlankRightNoColOnRight() {
        setUpRegularBoard();
        assertFalse(testFinder.isBlankRight(7, 16));
    }

    /**
     * Test whether the absence of a blank tile on the right is correctly identified to the right of
     * the blank tile.
     */
    @Test
    public void testIsBlankRightOnBlank() {
        setUpRegularBoard();
        assertFalse(testFinder.isBlankRight(15, 16));
    }

    /**
     * Test whether the tile finder correctly distinguishes between a blank tile and other
     * tiles on the board.
     */
    @Test
    public void testIsBlankTile() {
        setUpRegularBoard();
        assertTrue(testFinder.isBlankTile(3, 3, 16));
        assertFalse(testFinder.isBlankTile(1, 2, 16));
        tearDown();
        setUpSpecialBoard(9);
        assertTrue(testFinder.isBlankTile(2, 1, 16));
    }

    /**
     * Test whether the column of a blank tile is correctly returned by getColumnOfBlank()
     * called on various tiles adjacent to the blank tile.
     */
    @Test
    public void testGetColOfBlankWithBlankAdjacent() {
        setUpRegularBoard();
        assertEquals(3, testFinder.getColOfBlank(11, 16));
        assertEquals(3, testFinder.getColOfBlank(14, 16));
        tearDown();
        setUpSpecialBoard(9);
        assertEquals(1, testFinder.getColOfBlank(10, 16));
    }

    /**
     * Test whether getColOfBlank correctly returns the column of the tile at the position it
     * was called on if there is no tile adjacent to that position.
     */
    @Test
    public void testGetColOfBlankNoBlankAdjacent() {
        setUpRegularBoard();
        assertEquals(1, testFinder.getColOfBlank(5, 16));
    }

    /**
     * Test whether getRowOfBlank correctly returns the row in which the blank tile adjacent
     * to the tile at position is located.
     */
    @Test
    public void testGetRowOfBlankWithBlankAdjacent() {
        setUpRegularBoard();
        assertEquals(3, testFinder.getRowOfBlank(11, 16));
        assertEquals(3, testFinder.getRowOfBlank(14, 16));
        tearDown();
        setUpSpecialBoard(6);
        assertEquals(1, testFinder.getRowOfBlank(5, 16));
        assertEquals(1, testFinder.getRowOfBlank(10, 16));
    }

    /**
     * Test whether getRowOfBlank correctly returns the row of the tile at the position it
     * was called on if there is no tile adjacent to that position.
     */
    @Test
    public void testGetRowOfBlankNoBlankAdjacent() {
        setUpRegularBoard();
        assertEquals(0, testFinder.getRowOfBlank(3, 16));
    }

    /**
     * Test whether the row of the blank tile on the board from the bottom is correctly found.
     */
    @Test
    public void testGetRowOfBlankFromBottom() {
        setUpRegularBoard();
        assertEquals(0, testFinder.getRowOfBlankFromBottom());
        tearDown();
        setUpSpecialBoard(5);
        assertEquals(2, testFinder.getRowOfBlankFromBottom());
    }

    /**
     * Test whether the row of the tile at position is correctly identified and returned.
     */
    @Test
    public void testGetRow() {
        setUpRegularBoard();
        assertEquals(1, testFinder.getRow(6));
    }

    /**
     * Test whether the column of the tile at position is correctly identified and returned.
     */
    @Test
    public void testGetColumn() {
        setUpRegularBoard();
        assertEquals(2, testFinder.getColumn(2));
    }

    /**
     * Set up a regular 4x4 sliding tiles board with the blank tile in row = 3 , col = 3.
     */
    private void setUpRegularBoard() {
        makeTiles();
        board = new BoardSlidingTiles(tileList, 4, 4);
        testFinder = new TileFinder(board);
    }

    /**
     * Populate the tileList with tiles for a 4x4 sliding tiles board.
     */
    private void makeTiles() {
        for (int i = 0; i < 16; i++) {
            tileList.add(new SlidingTile(i, "4"));
        }
    }

    /**
     * Set up a board with a blank tile at blankPosition.
     *
     * @param blankPosition position of blank tile on the new board
     */
    private void setUpSpecialBoard(int blankPosition) {
        setUpRegularBoard();
        board.swapTiles(blankPosition / 4, blankPosition % 4, 3, 3);
    }

    /**
     * Reset objects used for testing before the next test is run.
     */
    @After
    public void tearDown() {
        testFinder = null;
        board = null;
        tileList.clear();
    }
}