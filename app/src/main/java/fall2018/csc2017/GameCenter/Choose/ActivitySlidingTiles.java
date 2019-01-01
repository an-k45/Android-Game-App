package fall2018.csc2017.GameCenter.Choose;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.HashMap;

import fall2018.csc2017.GameCenter.Accounts.Account;
import fall2018.csc2017.GameCenter.Accounts.AccountManager;
import fall2018.csc2017.GameCenter.GameActivites.GameActivitySlidingTiles;
import fall2018.csc2017.GameCenter.GameManagers.BoardManagerSlidingTiles;
import fall2018.csc2017.GameCenter.R;
import fall2018.csc2017.GameCenter.Technical.FileManager;

/**
 * The menu activity that allows the user to start a new game and load a game if they are logged in.
 */
public class ActivitySlidingTiles extends AppCompatActivity {

    /**
     * The name of the file to save the BoardManagerSlidingTiles to permanently.
     */
    public static final String TEMP_SAVE_FILENAME = "save_file.ser";

    /**
     * The board manager for this game
     */
    private BoardManagerSlidingTiles boardManagerSlidingTiles;

    /**
     * The file manager for this game
     */
    private FileManager fileManager;

    /**
     * Current instance of AccountManager
     */
    AccountManager accountManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_tiles);

        initManagers();
        addButtonListeners();
    }

    /**
     * Initializes the managers.
     */
    private void initManagers() {
        fileManager = new FileManager(this.getApplicationContext());
        accountManager = AccountManager.getInstance();
    }

    /**
     * Saves the board manager to the temporary save file.
     */
    private void saveBMToTemp() {
        fileManager.save(boardManagerSlidingTiles, TEMP_SAVE_FILENAME);
    }

    /**
     * Starts all the button listeners in this activity.
     */
    private void addButtonListeners() {
        addNewGameButtonListener();
        addLoadGameButtonListener();
    }

    /**
     * Adds a listener for the new game button.
     */
    private void addNewGameButtonListener() {
        Button NewGameB = findViewById(R.id.NewGameB);
        NewGameB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToChoose();
            }
        });
    }

    /**
     * Adds a listener for the load game button.
     */
    private void addLoadGameButtonListener() {
        Button LoadGameB = findViewById(R.id.LoadGameB);
        LoadGameB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadGameButtonAction();
            }
        });
    }

    /**
     * Called when the load button is tapped. Preform a load of a request game state for a logged in user.
     */
    public void loadGameButtonAction() {
        if (accountManager.getCurrentUser() == null) {
            Toast.makeText(this.getApplicationContext(), "You have to be logged in to load your games.", Toast.LENGTH_LONG).show();
        } else if (accountManager.getCurrentAccount().getManagersList(0).isEmpty()) {
            Toast.makeText(this, "You have no saved games to load", Toast.LENGTH_SHORT).show();
        } else {
            setAccountMapFromAccountsFile();
            boardManagerSlidingTiles = (BoardManagerSlidingTiles) accountManager.getCurrentAccount().getLastManager(0);
            saveBMToTemp();
            makeToastLoadedText();
            switchToGame();
        }
    }

    /**
     * Reload the most recent instance of the game upon resuming the game.
     */
    protected void onResume() {
        super.onResume();
        boardManagerSlidingTiles = fileManager.load(TEMP_SAVE_FILENAME);
    }

    /**
     * Tell the user that the game was loaded.
     */
    private void makeToastLoadedText() {
        Toast.makeText(this, "Loaded Game", Toast.LENGTH_SHORT).show();
    }

    /**
     * Send the user to the game activity from current activity.
     */
    private void switchToGame() {
        Intent tmp = new Intent(this, GameActivitySlidingTiles.class);
        fileManager.save(boardManagerSlidingTiles, TEMP_SAVE_FILENAME);
        startActivity(tmp);
    }

    /**
     * Send the user to the choose activity from current activity.
     */
    private void switchToChoose() {
        Intent tmp = new Intent(this, ChooseActivitySlidingTilesPhoto.class);
        startActivity(tmp);
    }

    /**
     * Loads the saved account HashMap from file and sets the current AccountManager's account map
     * to the one loaded from the file.
     */
    @SuppressWarnings("unchecked")
    private void setAccountMapFromAccountsFile() {
        accountManager.setAccountMap((HashMap<String, Account>) fileManager.load("Accounts.ser"));
    }
}
