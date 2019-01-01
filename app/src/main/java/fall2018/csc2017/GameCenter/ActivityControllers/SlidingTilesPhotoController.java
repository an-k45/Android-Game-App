package fall2018.csc2017.GameCenter.ActivityControllers;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import java.util.ArrayList;

import fall2018.csc2017.GameCenter.Choose.ProxyBitmap;
import fall2018.csc2017.GameCenter.GameManagers.BoardManagerSlidingTiles;

/**
 * Control for the logic of ChooseActivitySlidingTilesPhoto
 */
public class SlidingTilesPhotoController {

    /**
     * The difficulty of the game. Default value is 3.
     */
    private int difficulty = 3;

    /**
     * Whether or not the user has selected an image
     */
    private boolean useImage = false;

    /**
     * Image to be split
     */
    private ImageView chosenImage;

    /**
     * Make a new controller object
     */
    public SlidingTilesPhotoController() {

    }

    /**
     * Splits given image into an ArrayList of chunks to be used for tiles
     * Taken from http://www.chansek.com/splittingdividing-image-into-smaller/
     *
     * @param image the user selected image
     */
    private ArrayList<ProxyBitmap> splitImage(ImageView image) {
        //Turn the image the user selects into a bitmap with dimensions of the phone
        BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(),
                bitmap.getHeight(), true);

        return createdChunkedImages(bitmap, scaledBitmap);
    }

    /**
     * Return the array of chunks of an image.
     *
     * @param bitmap       the bitmap for the image
     * @param scaledBitmap the image scaled to the phone
     * @return the list of the pieces of the image
     */
    private ArrayList<ProxyBitmap> createdChunkedImages(Bitmap bitmap, Bitmap scaledBitmap) {
        ArrayList<ProxyBitmap> chunkedImages = new ArrayList<>(difficulty * difficulty);
        int chunkHeight = bitmap.getHeight() / difficulty;
        int chunkWidth = bitmap.getWidth() / difficulty;

        int yCoord = 0;
        for (int x = 0; x < difficulty; x++) {
            int xCoord = 0;
            for (int y = 0; y < difficulty; y++) {
                Bitmap b = Bitmap.createBitmap(scaledBitmap, xCoord, yCoord, chunkWidth, chunkHeight);
                ProxyBitmap p = new ProxyBitmap(b);
                chunkedImages.add(p);
                xCoord += chunkWidth;
            }
            yCoord += chunkHeight;
        }
        return chunkedImages;
    }

    /**
     * Sets the difficulty of the board
     *
     * @param difficulty difficulty of the board
     */
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Sets the current image to be sliced
     *
     * @param chosenImage image to be sliced
     */
    public void setChosenImage(ImageView chosenImage) {
        this.chosenImage = chosenImage;
        useImage = true;
    }

    /**
     * Turns the theme back to the original background
     */
    public void removeImage() {
        useImage = false;
    }

    /**
     * Get a board manager given the current parameters
     *
     * @return a new board manager
     */
    public BoardManagerSlidingTiles getBoardManager() {
        if (useImage)
            return new BoardManagerSlidingTiles(difficulty, difficulty, splitImage(chosenImage));
        else
            return new BoardManagerSlidingTiles(difficulty, difficulty);
    }
}
