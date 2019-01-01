package fall2018.csc2017.GameCenter.GameStructure;

import java.util.List;
import java.util.Random;

import fall2018.csc2017.GameCenter.Tiles.Tile;
import fall2018.csc2017.GameCenter.Tiles.TilePipelines;
import fall2018.csc2017.GameCenter.Helper.PipelineBoardParameter;

/**
 * A board for the pipelines game.
 */
public class BoardPipelines extends Board {

    /**
     * row of the starting tile of the board
     */
    private int startRow;

    /**
     * column of the starting tile of the board
     */
    private int startCol;

    /**
     * row of the ending tile of the board
     */
    private int endRow;

    /**
     * column of the ending tile of the board
     */
    private int endCol;

    /**
     * Create a new BoardPipelines with the list of tiles, based on the number of rows and columns.
     *
     * @param tiles the list of tiles in this board
     * @param para  parameter list for the board
     */
    public BoardPipelines(List<Tile> tiles, PipelineBoardParameter para) {
        super(tiles, para.getRows(), para.getCols());
        this.startCol = para.getStartCol();
        this.startRow = para.getStartRow();
        this.endCol = para.getEndCol();
        this.endRow = para.getEndRow();
    }

    /**
     * Randomize the orientation of all tiles on the board one time
     */
    public void randomizeTiles() {
        Random r = new Random();
        for (int i = 0; i < this.getNumRows(); i++) {
            for (int j = 0; j < this.getNumCols(); j++) {
                int rotate = r.nextInt(3) + 1;
                while (rotate > 0) {
                    makeSwap(i, j, -1, -1);
                    rotate--;
                }
            }
        }
    }

    @Override
    protected void makeSwap(int row1, int col1, int row2, int col2) {
        // Rotate the tile clockwise so long as it is not a start or end tile.
        if ((row1 != startRow | col1 != startCol) & (row1 != endRow | col1 != endCol)) {
            Tile[][] tTiles = getTiles();
            TilePipelines[][] pTiles = new TilePipelines[tTiles.length][tTiles[0].length];
            for (int i = 0; i < tTiles.length; i++) {
                for (int j = 0; j < tTiles[0].length; j++) {
                    pTiles[i][j] = (TilePipelines) tTiles[i][j];
                }
            }
            int[] id = pTiles[row1][col1].getHoles();
            getTiles()[row1][col1] = new TilePipelines(rotateId(id), false);
        }
        setChanged();
        notifyObservers();
    }

    /**
     * Return the ID list rotated to the clockwise rotation.
     *
     * @param id the id list of the tile before it is being rotated
     * @return the ID list rotated to the clockwise rotation
     */
    private int[] rotateId(int[] id) {
        int[] newId = new int[4];
        newId[0] = id[3];
        newId[1] = id[0];
        newId[2] = id[1];
        newId[3] = id[2];
        return newId;
    }

    /**
     * return starting row
     *
     * @return starting row
     */
    public int getStartRow() {
        return startRow;
    }

    /**
     * return starting col
     *
     * @return start col
     */
    public int getStartCol() {
        return startCol;
    }

    /**
     * return ending row
     *
     * @return end row
     */
    public int getEndRow() {
        return endRow;
    }

    /**
     * return ending column
     *
     * @return ending column
     */
    public int getEndCol() {
        return endCol;
    }
}
