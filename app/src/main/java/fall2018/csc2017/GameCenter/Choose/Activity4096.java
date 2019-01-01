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
import fall2018.csc2017.GameCenter.GameActivites.GameActivity4096;
import fall2018.csc2017.GameCenter.GameManagers.BoardManager4096;
import fall2018.csc2017.GameCenter.GameManagers.GridMover;
import fall2018.csc2017.GameCenter.R;
import fall2018.csc2017.GameCenter.Scoring.Score;
import fall2018.csc2017.GameCenter.Technical.FileManager;

/**
 * The menu activity that allows the user to start a new game and load a game if they are logged in.
 */
public class Activity4096 extends AppCompatActivity {

    /**
     * The name of the file to save the BoardManagerSlidingTiles to permanently.
     */
    public static final String TEMP_SAVE_FILENAME2 = "save_file_tmp2.ser";

    /**
     * The board manager for this game
     */
    private BoardManager4096 boardManager4096;

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
        setContentView(R.layout.activity_4096);

        initManagers();
        addButtonListeners();
    }

    /**
     * Initializes the managers needed for this activity to work.
     */
    private void initManagers() {
        fileManager = new FileManager(this.getApplicationContext());
        accountManager = AccountManager.getInstance();
    }

    /**
     * Creates a new board manager that has an untouched board and score 0
     */
    private void makeCleanBM() {
        boardManager4096 = new BoardManager4096(new GridMover(), new Score(0));
    }

    /**
     * Saves the the current board manager to the temporary save file to be opened by the
     * game activity.
     */
    private void saveBMToTemp() {
        fileManager.save(boardManager4096, TEMP_SAVE_FILENAME2);
    }

    /**
     * Loads the contents from the temporary save file and creates a new board manager accordingly.
     */
    private void loadBMFromTemp() {
        boardManager4096 = fileManager.load(TEMP_SAVE_FILENAME2);
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
        Button NewGameB = findViewById(R.id.NewGame4096);
        NewGameB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newGameAction();
                Intent intent = new Intent(getApplicationContext(), GameActivity4096.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Performs the necessary functions required to start a new game.
     */
    private void newGameAction() {
        makeCleanBM();
        saveBMToTemp();
        if (accountManager.currentlyLoggedIn()) {
            accountManager.getCurrentAccount().add4096Manager(boardManager4096);
        }
    }

    /**
     * Adds a listener for the load game button.
     */
    private void addLoadGameButtonListener() {
        Button LoadGameB = findViewById(R.id.LoadGame4096);
        LoadGameB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadGameAction();
            }
        });
    }

    /**
     * Reload the most recent instance of the game upon resuming the game.
     */
    protected void onResume() {
        super.onResume();
        loadBMFromTemp();
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
        Intent tmp = new Intent(this, GameActivity4096.class);
        startActivity(tmp);
    }

    /**
     * Called when the load button is tapped. Preform a load of a request game state for a logged in user.
     */
    private void loadGameAction() {
        if (accountManager.getCurrentUser() == null) {
            Toast.makeText(getApplicationContext(), "You have to be logged in to load your games.", Toast.LENGTH_LONG).show();
        } else if (accountManager.getCurrentAccount().getManagersList(1).isEmpty()) {
            Toast.makeText(this, "You have no saved games to load", Toast.LENGTH_SHORT).show();
        } else {
            setAccountMapFromAccountsFile();
            boardManager4096 = (BoardManager4096) accountManager.getCurrentAccount().getLastManager(1); //Removing this line allows for easy temp storage
            saveBMToTemp();
            makeToastLoadedText();
            switchToGame();
        }
    }

    /**
     * Updates the account map in the account manager to reflect the contents of the accounts.ser file.
     * The accounts.ser file contains the account map of the latest save, therefore, loading from it
     * will give the latest saves and put it in the account map for later use.
     */
    @SuppressWarnings("unchecked")
    private void setAccountMapFromAccountsFile() {
        accountManager.setAccountMap((HashMap<String, Account>) fileManager.load("Accounts.ser"));
    }
}
