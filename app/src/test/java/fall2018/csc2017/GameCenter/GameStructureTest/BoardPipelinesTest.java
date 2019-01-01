package fall2018.csc2017.GameCenter.GameStructureTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import fall2018.csc2017.GameCenter.GameStructure.BoardPipelines;
import fall2018.csc2017.GameCenter.Tiles.Tile;
import fall2018.csc2017.GameCenter.Tiles.TilePipelines;
import fall2018.csc2017.GameCenter.Helper.PipelineBoardParameter;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Test class for testing BoardPipelines.
 */
public class BoardPipelinesTest extends BoardTest {

    /**
     * Make a set of tiles.
     *
     * @return a set of tiles that are in order
     */
    private List<Tile> makeTiles(int rows, int cols) {
        List<Tile> tiles = new ArrayList<>();
        int[] idCornerPipe = {1, 1, 0, 0};
        int numTiles = rows * cols;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new TilePipelines(idCornerPipe, false));
        }

        return tiles;
    }

    /**
     * Sets up the board for testing
     */
    @Before
    public void setUpFourByFour() {
        List<Tile> tiles = makeTiles(4, 4);
        PipelineBoardParameter para = new PipelineBoardParameter(4, 4);
        para.setEndCol(3);
        para.setEndRow(3);
        board = new BoardPipelines(tiles, para);
    }

    /**
     * Clears the board variable
     */
    @After
    public void tearDown() {
        board = null;
    }

    /**
     * Test to ensure tile rotates properly in a central tile.
     */
    @Test
    public void testSwapTilesOnCentralTile() {
        setUpFourByFour();
        int[] originId = ((TilePipelines) board.getTile(1, 1)).getHoles();
        int[] expectedResultId = {originId[3], originId[0], originId[1], originId[2]};
        board.swapTiles(1, 1, 0, 0);
        int[] resultId = ((TilePipelines) board.getTile(1, 1)).getHoles();
        assertArrayEquals(expectedResultId, resultId);
        tearDown();
    }

    /**
     * Test to ensure tile doesn't rotate on a start tile.
     */
    @Test
    public void testSwapTilesOnStartTile() {
        setUpFourByFour();
        int[] startId = ((TilePipelines) board.getTile(((BoardPipelines) board).getStartRow(), ((BoardPipelines) board).getStartCol()))
                .getHoles();
        board.swapTiles(((BoardPipelines) board).getStartRow(), ((BoardPipelines) board).getStartCol(), 0, 0);
        int[] resultId = ((TilePipelines) board.getTile(((BoardPipelines) board).getStartRow(),
                ((BoardPipelines) board).getStartCol())).getHoles();
        assertArrayEquals(startId, resultId);
        tearDown();
    }

    /**
     * Test to ensure tile doesn't rotate on a start tile.
     */
    @Test
    public void testSwapTilesOnEndTile() {
        setUpFourByFour();
        int[] endId = ((TilePipelines) board.getTile(((BoardPipelines) board).getEndRow(),
                ((BoardPipelines) board).getEndCol()))
                .getHoles();
        board.swapTiles(((BoardPipelines) board).getEndRow(), ((BoardPipelines) board).getEndCol(), 0, 0);
        int[] resultId = ((TilePipelines) board.getTile(((BoardPipelines) board).getEndRow(),
                ((BoardPipelines) board).getEndCol())).getHoles();
        assertArrayEquals(endId, resultId);
        tearDown();
    }

    /**
     * Tests to ensure that trying to rotate a tile that does not exist produces an error.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testSwapTilesOutOfBounds() {
        setUpFourByFour();
        board.swapTiles(5, 5, 0, 0);
        tearDown();
    }

    /**
     * Tests to ensure that the ending row is returned properly.
     */
    @Test
    public void getEndRow() {
        setUpFourByFour();
        assertEquals(((BoardPipelines) board).getEndRow(), 3);
        tearDown();
    }

    /**
     * Tests to ensure that the ending column is returned properly.
     */
    @Test
    public void getEndCol() {
        setUpFourByFour();
        assertEquals(((BoardPipelines) board).getEndCol(), 3);
        tearDown();
    }

    /**
     * Tests to ensure that the non-start and non-end tiles randomized are not the same as the
     * original tiles
     */
    @Test
    public void testRandomizeTiles() {
        setUpFourByFour();
        int[][][] originIds = new int[4][4][4];
        int[][][] resultIds = new int[4][4][4];
        int i = 0, j = 0;

        for (Tile tile : board) {
            originIds[j][i] = ((TilePipelines) tile).getHoles();
            if (i == 3) {
                i = 0;
                j++;
            } else {
                i++;
            }
        }

        ((BoardPipelines) board).randomizeTiles();

        i = 0;
        j = 0;
        for (Tile tile : board) {
            resultIds[j][i] = ((TilePipelines) tile).getHoles();
            if (i == 3) {
                i = 0;
                j++;
            } else {
                i++;
            }
        }

        for (i = 0; i < originIds.length; i++) {
            for (j = 0; j < originIds[i].length; j++) {
                if ((i != ((BoardPipelines) board).getStartRow() || j !=
                        ((BoardPipelines) board).getStartCol()) && (i !=
                        ((BoardPipelines) board).getEndRow() | j !=
                        ((BoardPipelines) board).getEndCol())) {
                    assertThat(originIds[i][j], not(equalTo(resultIds[i][j])));
                } else {
                    assertArrayEquals(originIds[i][j], resultIds[i][j]);
                }
            }
        }
        tearDown();
    }

    /**
     * Make sure that an exception is thrown when the end of iterator is reached
     */
    @Test(expected = NoSuchElementException.class)
    public void testEndOfIterator() {
        setUpFourByFour();
        int i = 0;
        Iterator<Tile> iterator = board.iterator();
        while (i < 17) {
            iterator.next();
            i++;
        }
    }
}