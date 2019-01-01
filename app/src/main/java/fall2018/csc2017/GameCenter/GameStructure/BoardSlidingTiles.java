package fall2018.csc2017.GameCenter.GameStructure;

import java.util.List;

import fall2018.csc2017.GameCenter.Tiles.SlidingTile;
import fall2018.csc2017.GameCenter.Tiles.Tile;

/**
 * Board for the SlidingTiles game.
 */
public class BoardSlidingTiles extends Board {

    /**
     * Create a new SlidingTiles board with a list of tiles and the number of rows and columns on
     * the grid
     *
     * @param tiles tiles to be used
     * @param rows  number of rows on the board
     * @param cols  number of columns on the board
     */
    public BoardSlidingTiles(List<Tile> tiles, int rows, int cols) {
        super(tiles, rows, cols);
    }

    @Override
    protected void makeSwap(int row1, int col1, int row2, int col2) {
        Tile tempTile = new SlidingTile(((SlidingTile) getTiles()[row2][col2]).getId(),
                this.getTiles()[row2][col2].getBackground());

        this.getTiles()[row2][col2] = new SlidingTile(((SlidingTile) getTiles()[row1][col1]).getId(),
                this.getTiles()[row1][col1].getBackground());
        this.getTiles()[row1][col1] = new SlidingTile(((SlidingTile) tempTile).getId(), tempTile.getBackground());
    }
}
