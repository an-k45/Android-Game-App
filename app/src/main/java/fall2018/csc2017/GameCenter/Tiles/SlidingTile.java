package fall2018.csc2017.GameCenter.Tiles;

import java.io.Serializable;

import fall2018.csc2017.GameCenter.R;

/**
 * A Tile with a numeric background for the Sliding Tiles game.
 */
public class SlidingTile extends Tile implements Serializable {

    /**
     * Drawable ids used to make tiles
     */
    private int[] tileIds = new int[]{R.drawable.tile_1, R.drawable.tile_2, R.drawable.tile_3,
            R.drawable.tile_4, R.drawable.tile_5, R.drawable.tile_6, R.drawable.tile_7,
            R.drawable.tile_8, R.drawable.tile_9, R.drawable.tile_10, R.drawable.tile_11,
            R.drawable.tile_12, R.drawable.tile_13, R.drawable.tile_14, R.drawable.tile_15,
            R.drawable.tile_16, R.drawable.tile_17, R.drawable.tile_18, R.drawable.tile_19,
            R.drawable.tile_20, R.drawable.tile_21, R.drawable.tile_22, R.drawable.tile_23,
            R.drawable.tile_24, R.drawable.tile_blank};

    /**
     * The unique id.
     */
    private int id;

    /**
     * A Tile with id and background. The background may not have a corresponding image.
     *
     * @param id         the id
     * @param background the background
     */
    public SlidingTile(int id, int background) {
        this.setId(id);
        this.setBackground(background);
    }

    /**
     * A tile with a background id; look up and set the id.
     *
     * @param backgroundId id of the background given to the Tile
     * @param difficulty   the game difficulty setting
     */
    public SlidingTile(int backgroundId, String difficulty) {
        this.setId(backgroundId + 1);
        this.setBackground(tileIds[backgroundId]);
        if (backgroundId == Integer.parseInt(difficulty) * Integer.parseInt(difficulty) - 1) {
            this.setBackground(R.drawable.tile_blank);
        }
    }

    /**
     * Set the id of this tile to the specified value
     *
     * @param id new id to be used
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Return the tile id.
     *
     * @return the tile id
     */
    public int getId() {
        return id;
    }
}
