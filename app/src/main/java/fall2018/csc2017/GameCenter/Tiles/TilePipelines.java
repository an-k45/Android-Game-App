package fall2018.csc2017.GameCenter.Tiles;

import android.support.v4.util.SparseArrayCompat;

import java.io.Serializable;

import fall2018.csc2017.GameCenter.R;

/**
 * Tile with an image background for the Pipelines game.
 */
public class TilePipelines extends Tile implements Serializable {

    /**
     * Maps the integer from of an id to a background
     */
    private static SparseArrayCompat<Integer> valueToBackground = new SparseArrayCompat<>(22);

    static {
        valueToBackground.put(1100, R.drawable.pipecorner_1);
        valueToBackground.put(110, R.drawable.pipecorner_2);
        valueToBackground.put(11000, R.drawable.pipecorner_3);
        valueToBackground.put(10010, R.drawable.pipecorner_4);
        valueToBackground.put(10100, R.drawable.pipestraight_1);
        valueToBackground.put(1010, R.drawable.pipestraight_2);
        valueToBackground.put(11100, R.drawable.pipetee_1);
        valueToBackground.put(10110, R.drawable.pipetee_2);
        valueToBackground.put(11010, R.drawable.pipetee_3);
        valueToBackground.put(1110, R.drawable.pipetee_4);
        valueToBackground.put(11110, R.drawable.pipecross);
        valueToBackground.put(1101, R.drawable.pipecorner_1w);
        valueToBackground.put(111, R.drawable.pipecorner_2w);
        valueToBackground.put(11001, R.drawable.pipecorner_3w);
        valueToBackground.put(10011, R.drawable.pipecorner_4w);
        valueToBackground.put(10101, R.drawable.pipestraight_1w);
        valueToBackground.put(1011, R.drawable.pipestraight_2w);
        valueToBackground.put(11101, R.drawable.pipetee_1w);
        valueToBackground.put(10111, R.drawable.pipetee_2w);
        valueToBackground.put(11011, R.drawable.pipetee_3w);
        valueToBackground.put(1111, R.drawable.pipetee_4w);
        valueToBackground.put(11111, R.drawable.pipecrossw);

    }

    /**
     * The id of this tile. Each index represents whether there is a connection on that side
     */
    private int[] holes;

    /**
     * Generate a Tile with id as a start/end tile or not.
     *
     * @param id the id of this tile
     * @param on whether this tile is a start or end tile
     */
    public TilePipelines(int[] id, boolean on) {
        super();
        this.holes = id;
        int value = id[0] * 10000 + id[1] * 1000 + id[2] * 100 + id[3] * 10 + (on ? +1 : +0);
        this.setBackground(valueToBackground.get(value));
    }

    /**
     * instantiate a new "empty" tile
     */
    public TilePipelines() {
        super();
        int[] newHoles = new int[1];
        newHoles[0] = -1;
        this.holes = newHoles;
    }

    /**
     * Return the tile id.
     *
     * @return the tile id
     */
    public int[] getHoles() {
        return holes;
    }
}
