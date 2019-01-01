package fall2018.csc2017.GameCenter.GameActivites;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import fall2018.csc2017.GameCenter.Accounts.AccountManager;
import fall2018.csc2017.GameCenter.ActivityControllers.ActivityController;
import fall2018.csc2017.GameCenter.Choose.Activity4096;
import fall2018.csc2017.GameCenter.GameManagers.BoardManager;
import fall2018.csc2017.GameCenter.GameStructure.Board4096;
import fall2018.csc2017.GameCenter.R;
import fall2018.csc2017.GameCenter.Technical.CustomAdapter;
import fall2018.csc2017.GameCenter.Technical.FileManager;
import fall2018.csc2017.GameCenter.Technical.GestureDetectGridView;
import fall2018.csc2017.GameCenter.Technical.Initializable;

/**
 * The game activity for 4096 game
 */
public class GameActivity4096 extends AppCompatActivity implements Observer, Initializable {

    /**
     * Controller for the logic of this activity
     */
    private ActivityController control;

    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;

    /**
     * Current instance of the AccountManager used by the app
     */
    private AccountManager accountManager;

    /**
     * The manager used by this activity to load and save files
     */
    private FileManager fileManager;

    /**
     * UI Elements
     */
    private TextView scoreWindow;
    private TextView movesTV;
    private TextView lastSaveTV;

    /**
     * Grid View and calculated column height and width based on device size
     */
    private GestureDetectGridView gridView;

    /**
     * Column width and height.
     */
    private static int columnWidth, columnHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_4096);
        control = new ActivityController();

        init();
        setUpGrid();
        setUpOtherUI();
        addButtonListeners();
    }

    /**
     * The height and width of the columns used to make the board.
     */
    @Override
    public void init() {
        fileManager = new FileManager(this.getApplicationContext());
        control.setBoardManager((BoardManager) (fileManager.load(Activity4096.TEMP_SAVE_FILENAME2)));
        accountManager = AccountManager.getInstance();
    }

    /**
     * Sets up the grid
     */
    private void setUpGrid() {
        tileButtons = control.createTileButtons(this);
        addViewToActivity();
        setUpDimensions();
    }

    /**
     * Sets up all other UI that is not the grid.
     */
    private void setUpOtherUI() {
        scoreWindow = findViewById(R.id.ScoreView);
        movesTV = findViewById(R.id.MovesTV);
        lastSaveTV = findViewById(R.id.LastSaveTV);

        updateScoreWindow();
        updateMovesTV();
        updateLastSaveTV();
    }

    @Override
    public void addButtonListeners() {
        addSaveGameButtonListener();
    }

    /**
     * Adds a listener for the save button.
     */
    private void addSaveGameButtonListener() {
        Button saveButton = findViewById(R.id.SaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAction();
            }
        });
    }

    /**
     * Called when the save button is tapped. Saves the current game if the user is logged in.
     */
    public void saveAction() {
        if (accountManager.currentlyLoggedIn()) {
            accountManager.getCurrentAccount().setLastManager(1, control.getBoardManager());
            fileManager.save(accountManager.getAccountMap(), "Accounts.ser");
            makeToastSavedText();
            updateLastSaveTV();
        } else {
            makeToastNotLogged();
        }
    }

    /**
     * Adds the view to the activity.
     */
    private void addViewToActivity() {
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(control.getCols());
        gridView.setBoardManager(control.getBoardManager());
        control.getBoard().addObserver(this);
        control.getBoardManager().addObserver(this);
    }

    /**
     * Set up the dimensions
     */
    private void setUpDimensions() {
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        columnWidth = displayWidth / control.getCols();
                        columnHeight = displayHeight / control.getRows();

                        display();
                    }
                });
    }

    /**
     * Update the score view with the new score.
     */
    private void updateScoreWindow() {
        String scoreText = getString(R.string.score_4096);
        scoreText = String.format(scoreText,
                ((Board4096) control.getBoard()).getCombinationSum());
        scoreWindow.setText(scoreText);
    }

    /**
     * Update the current move TextView to reflect any changes.
     */
    private void updateMovesTV() {
        String currentMoveText = getString(R.string.current_move_4096);
        int currentMoveInt = control.getBoardManager().getMoves();

        currentMoveText = String.format(currentMoveText, currentMoveInt);
        movesTV.setText(currentMoveText);
    }

    @Override
    public void update(Observable o, Object arg) {
        updateScoreWindow();
        updateMovesTV();
        if (isTimeToAutoSave()) {
            saveAction();
            updateLastSaveTV();
        }
        display();
    }

    /**
     * Updates the last save TextView to reflect any changes.
     */
    private void updateLastSaveTV() {
        String lastSaveText = getString(R.string.last_save_4096);
        int lastSaveInt = control.getBoardManager().getMoves();

        lastSaveText = String.format(lastSaveText, lastSaveInt);
        lastSaveTV.setText(lastSaveText);
    }

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    public void display() {
        control.updateTileButtons(tileButtons);
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
    }

    /**
     * Tell the user that the game was saved.
     */
    private void makeToastSavedText() {
        Toast.makeText(this, "Game Saved", Toast.LENGTH_SHORT).show();
    }

    /**
     * Alert the user that the user is not logged in.
     */
    private void makeToastNotLogged() {
        Toast.makeText(this.getApplicationContext(),
                "You have to be logged in to save your games.", Toast.LENGTH_LONG).show();
    }

    /**
     * Returns whether or not it is time to save.
     *
     * @return whether or not moves is divisible by 3.
     */
    private boolean isTimeToAutoSave() {
        return control.getBoardManager().getMoves() % control.getBoardManager().getSAVE_MOVE_LIMIT() == 0 &&
                accountManager.currentlyLoggedIn();
    }
}
