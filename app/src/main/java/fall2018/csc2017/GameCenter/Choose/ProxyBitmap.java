package fall2018.csc2017.GameCenter.Choose;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * A class that stores bitmaps as raw data, so that it is serializable
 * Taken from http://xperience57.blogspot.com/2015/09/android-saving-bitmap-as-serializable.html
 */
public class ProxyBitmap implements Serializable {
    /**
     * Store the pixels of a bitmap
     */
    private final int[] pixels;

    /**
     * Store the width and height of the bitmap
     */
    private final int width, height;

    /**
     * Create a proxy bitmap that emulates a real one.
     *
     * @param bitmap the real bitmap to be emulated.
     */
    public ProxyBitmap(Bitmap bitmap) {
        width = bitmap.getWidth();
        height = bitmap.getHeight();
        pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
    }

    /**
     * Return a bitmap object according to the raw data
     *
     * @return bitmap of the data in the object
     */
    public Bitmap getBitmap() {
        return Bitmap.createBitmap(pixels, width, height, Bitmap.Config.ARGB_8888);
    }
}
