package fall2018.csc2017.GameCenter.Tiles;

import android.support.annotation.NonNull;
import android.support.v4.util.SparseArrayCompat;

import java.io.Serializable;

import fall2018.csc2017.GameCenter.R;

/**
 * A Tile two instances of which can be combined into one containing the sum of their values
 */
public class AddableTile extends Tile implements Serializable, Comparable<AddableTile> {

    /**
     * Array mapping integer values of tiles to their integer background ids.
     */
    private static SparseArrayCompat<Integer> valueToBackground = new SparseArrayCompat<>();

    static {
        valueToBackground.put(0, R.drawable.tile_blank);
        valueToBackground.put(2, R.drawable.tile_2);
        valueToBackground.put(4, R.drawable.tile_4);
        valueToBackground.put(8, R.drawable.tile_8);
        valueToBackground.put(16, R.drawable.tile_16);
        valueToBackground.put(32, R.drawable.tile_32);
        valueToBackground.put(64, R.drawable.tile_64);
        valueToBackground.put(128, R.drawable.tile_128);
        valueToBackground.put(256, R.drawable.tile_256);
        valueToBackground.put(512, R.drawable.tile_512);
        valueToBackground.put(1024, R.drawable.tile_1024);
        valueToBackground.put(2048, R.drawable.tile_2048);
        valueToBackground.put(4096, R.drawable.tile_4096);
    }

    /**
     * Value of the tile displayed as its background
     */
    private int value;

    /**
     * Create a new instance of AddableTile with specified value to be displayed as background
     *
     * @param value value of tile
     */
    public AddableTile(int value) {
        this.value = value;
        this.setBackground(valueToBackground.get(value));
    }

    /**
     * Return the value of this AddableTile
     *
     * @return value
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Set the value of this AddableTile to val
     *
     * @param val value to use
     */
    public void setValue(int val) {
        this.value = val;
    }

    @Override
    public int compareTo(@NonNull AddableTile o) {
        return this.getValue() - (o).getValue();
    }
}
