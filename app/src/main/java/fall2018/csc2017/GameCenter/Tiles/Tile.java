package fall2018.csc2017.GameCenter.Tiles;

import java.io.Serializable;

/**
 * A Tile in a sliding tiles puzzle.
 */
public abstract class Tile implements Serializable {

    /**
     * The background id to find the tile image.
     */
    private int background;

    /**
     * Empty constructor for subclasses to use.
     */
    public Tile() {
    }

    /**
     * Return the background id.
     *
     * @return the background id
     */
    public int getBackground() {
        return background;
    }

    /**
     * Set the background image of this tile to image with id background.
     *
     * @param background R.drawable id of image to set background as
     */
    public void setBackground(int background) {
        this.background = background;
    }

}
