package fall2018.csc2017.GameCenter.GameStructureTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import fall2018.csc2017.GameCenter.GameManagers.GridMover;
import fall2018.csc2017.GameCenter.GameStructure.Board4096;
import fall2018.csc2017.GameCenter.Tiles.AddableTile;
import fall2018.csc2017.GameCenter.Tiles.Tile;

import static org.junit.Assert.*;

/**
 * Test class for testing a Board4096.
 */
public class Board4096Test {

    /**
     * Board to be used during testing.
     */
    private Board4096 testBoard;

    /**
     * Grid mover to move tiles on the board grid.
     */
    private GridMover mover;

    /**
     * Initialize objects needed during testing before each test.
     */
    @Before
    public void setUp() {
        testBoard = new Board4096(makeTiles(), 4, 4);
        mover = new GridMover();
        mover.setBoard(testBoard);
    }

    /**
     * Discard test objects after each test.
     */
    @After
    public void tearDown() {
        testBoard = null;
        mover = null;
    }

    /**
     * Test whether two identical tiles are combined into one when adjacent and a move is performed.
     */
    @Test
    public void combineTiles() {
        testBoard.getTiles()[0][1] = new AddableTile(2);
        mover.shiftBoardRight();
        assertEquals(4, ((AddableTile) testBoard.getTile(0, 3)).getValue());
    }

    /**
     * Test whether the board correctly adds a 2 tile to a random empty space on the grid after a
     * move.
     */
    @Test
    public void generateNewTile() {
        testBoard.generateNewTile();
        assertTrue(newTileAdded());
    }

    /**
     * Test whether the isBoardFull() correctly identifies a board with no empty spaces left.
     */
    @Test
    public void isBoardFull() {
        testBoard = new Board4096(fillBoard(), 4, 4);
        assertTrue(testBoard.isBoardFull());
    }

    /**
     * Test whether the change in the position of tiles on the board is correctly identified after
     * a move.
     */
    @Test
    public void boardChanged() {
        Tile[] tempTiles = new Tile[16];
        saveBoard(tempTiles);
        mover.shiftBoardLeft();
        assertFalse(testBoard.boardChanged(tempTiles));
        saveBoard(tempTiles);
        mover.shiftBoardRight();
        assertTrue(testBoard.boardChanged(tempTiles));
    }

    /**
     * Test whether the total sum from combining tiles is returned properly.
     */
    @Test
    public void getCombinationSum() {
        testBoard.getTiles()[0][1] = new AddableTile(2);
        mover.shiftBoardRight();
        assertEquals(4, testBoard.getCombinationSum());
    }

    /**
     * Test whether the board identifies when it has a 4096 tile on it correctly.
     */
    @Test
    public void containsTile4096() {
        assertFalse(testBoard.containsTile4096());
        testBoard.getTiles()[1][1] = new AddableTile(4096);
        assertTrue(testBoard.containsTile4096());
    }

    /**
     * Return a board with a single 2 tile on it.
     *
     * @return a Board4096 with a 2 tile
     */
    private List<Tile> makeTiles() {
        List<Tile> tiles = new ArrayList<>();

        for (int i = 1; i < 17; i++) {
            if (i == 1) {
                tiles.add(new AddableTile(2));
            } else {
                tiles.add(new AddableTile(0));
            }
        }
        return tiles;
    }

    /**
     * Return whether the board contains a new tile on it.
     *
     * @return true if a new tile exists on the board, false otherwise
     */
    private boolean newTileAdded() {
        boolean tileGenerated = false;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (((AddableTile) testBoard.getTile(i, j)).getValue() == 0 && i != 0 && j != 0) {
                    tileGenerated = true;
                }
            }
        }
        return tileGenerated;
    }

    /**
     * Return a board completely filled with tiles.
     *
     * @return a full board
     */
    private List<Tile> fillBoard() {
        List<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                tiles.add(new AddableTile(2));
            }
        }
        return tiles;
    }

    /**
     * Save the current state of the board into the passed tile array.
     *
     * @param list array of tiles to be filled
     */
    private void saveBoard(Tile[] list) {
        int i = 0;
        for (Tile t : testBoard) {
            list[i] = t;
            i++;
        }
    }
}