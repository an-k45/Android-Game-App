package fall2018.csc2017.GameCenter.Choose;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;

import fall2018.csc2017.GameCenter.Accounts.AccountManager;
import fall2018.csc2017.GameCenter.ActivityControllers.SlidingTilesPhotoController;
import fall2018.csc2017.GameCenter.GameActivites.GameActivitySlidingTiles;
import fall2018.csc2017.GameCenter.R;
import fall2018.csc2017.GameCenter.Technical.FileManager;
import fall2018.csc2017.GameCenter.Technical.Initializable;

/**
 * Activity for setting options for a game of Sliding Tiles.
 */
public class ChooseActivitySlidingTilesPhoto extends AppCompatActivity implements Initializable {

    /**
     * Controller object for this view
     */
    private SlidingTilesPhotoController control = new SlidingTilesPhotoController();

    /**
     * Constant used to check if image was loaded from gallery
     */
    private static final int RESULT_LOAD_IMAGE = 1;

    /**
     * The image that the user will choose
     */
    ImageView chosenImage;

    /**
     * The image that the user will choose
     */
    EditText urlImage;

    /**
     * File manager used to save and load from files.
     */
    private FileManager fileManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        init();
        addButtonListeners();

    }

    /**
     * Activate the button to choose easy level
     */
    private void addEasyButtonListener() {
        Button easyButton = findViewById(R.id.easy);
        easyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeToastDifficulty("Easy", 3);
            }
        });
    }

    /**
     * Activate the button to choose medium level
     */
    private void addMediumButtonListener() {
        Button mediumButton = findViewById(R.id.medium);
        mediumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeToastDifficulty("Medium", 4);
            }
        });
    }

    /**
     * Activate the button to choose hard level
     */
    private void addHardButtonListener() {
        Button hardButton = findViewById(R.id.hard);
        hardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeToastDifficulty("Hard", 5);
            }
        });
    }

    /**
     * Display text to tell user difficulty
     *
     * @param difficulty The selected difficulty
     */
    private void makeToastDifficulty(String message, int difficulty) {
        control.setDifficulty(difficulty);
        Toast.makeText(this, "Difficulty: " + message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Initialize URL button
     */
    private void addUrlButtonListener() {
        Button urlButton = findViewById(R.id.urlButton);
        urlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImageFromUrl();
            }
        });
    }

    /**
     * Get an image from a URL, if it is valid
     */
    private void getImageFromUrl() {
        String text = urlImage.getText().toString();
        new DownloadImageTask(chosenImage).execute(text);
    }

    /**
     * Display text if URL is valid
     */
    public void makeToastURLOK() {
        Toast.makeText(this, "Online Image Selected", Toast.LENGTH_SHORT).show();
    }

    /**
     * Display text if URL is not valid
     */
    public void makeToastURLNotOK() {
        Toast.makeText(this, "Not a Valid URL", Toast.LENGTH_SHORT).show();
    }

    /**
     * Activate the start button and switch to the game
     */
    private void addStartButtonListener() {
        Button backButton = findViewById(R.id.start);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToGame();
            }
        });
    }

    /**
     * Switch back to the sliding tiles activity
     */
    private void switchToGame() {
        fileManager.save(control.getBoardManager(), ActivitySlidingTiles.TEMP_SAVE_FILENAME);
        Intent tmp = new Intent(this, GameActivitySlidingTiles.class);

        if (AccountManager.getInstance().currentlyLoggedIn()) {
            AccountManager.getInstance().getCurrentAccount().addSlidingManager(control.getBoardManager());
        }
        startActivity(tmp);
    }

    /**
     * Switch back to the sliding tiles activity
     */
    private void switchToStart() {
        Intent tmp = new Intent(this, ActivitySlidingTiles.class);
        startActivity(tmp);
    }

    /**
     * Activate the back button
     */
    private void addBackButtonListener() {
        Button backButton = findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToStart();
            }
        });
    }

    /**
     * Activate the button to go back to original style
     */
    private void addNumberButtonListener() {
        Button numberButton = findViewById(R.id.numbers);
        numberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeToastSwitchText();
            }
        });
    }

    /**
     * Display text for choosing original style
     */
    private void makeToastSwitchText() {
        control.removeImage();
        chosenImage.setImageResource(R.drawable.tile_1);
        Toast.makeText(this, "Original Style Selected", Toast.LENGTH_SHORT).show();
    }

    /**
     * Activate button to switch image
     */
    private void addSelectButtonListener() {
        Button selectButton = findViewById(R.id.select);
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sends user to the gallery and gets ready to take in image they select
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);

            }
        });
    }

    /**
     * Sets the ImageView to the selected image and splits the image
     *
     * @param requestCode determines if the gallery was successfully
     * @param resultCode  determines if the image was successfully loaded from gallery
     * @param data        image data being used
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            chosenImage.setImageURI(selectedImage);
            control.setChosenImage(chosenImage);
            Toast.makeText(this, "Image Selected", Toast.LENGTH_LONG).show();
        }
    }


    /**
     * Class used to convert a URL to an image. Does not use the main thread
     * Taken from https://medium.com/@crossphd/android-image-loading-from-a-string-url-6c8290b82c5e
     */
    @SuppressLint("StaticFieldLeak")
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        /**
         * tries to convert a URL to a bitmap
         *
         * @param urls URLs to be converted
         * @return bitmap of the url
         */
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap bmp = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                bmp = BitmapFactory.decodeStream(in);

            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bmp;
        }

        /**
         * Sets image is url is valid, tells user if it is not
         *
         * @param result bitmap gotten from url
         */
        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                bmImage.setImageBitmap(result);
                control.setChosenImage(chosenImage);
                makeToastURLOK();
            } else {
                makeToastURLNotOK();
            }
        }
    }

    @Override
    public void init() {
        chosenImage = findViewById(R.id.chosenImage);
        urlImage = findViewById(R.id.urlText);
        chosenImage.setImageResource(R.drawable.tile_1);
        fileManager = new FileManager(this.getApplicationContext());
    }

    @Override
    public void addButtonListeners() {
        addSelectButtonListener();
        addNumberButtonListener();
        addBackButtonListener();
        addEasyButtonListener();
        addMediumButtonListener();
        addHardButtonListener();
        addStartButtonListener();
        addUrlButtonListener();
    }
}
