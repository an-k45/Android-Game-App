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
import fall2018.csc2017.GameCenter.ActivityControllers.SlidingTilesController;
import fall2018.csc2017.GameCenter.Choose.ActivitySlidingTiles;
import fall2018.csc2017.GameCenter.GameManagers.BoardManagerSlidingTiles;
import fall2018.csc2017.GameCenter.R;
import fall2018.csc2017.GameCenter.Technical.CustomAdapter;
import fall2018.csc2017.GameCenter.Technical.FileManager;
import fall2018.csc2017.GameCenter.Technical.GestureDetectGridView;
import fall2018.csc2017.GameCenter.Technical.Initializable;

/**
 * The game activity.
 */
public class GameActivitySlidingTiles extends AppCompatActivity implements Observer, Initializable {

    /**
     * Controller for logic of this activity
     */
    private SlidingTilesController control;

    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;

    /**
     * Grid View and calculated column height and width based on device size
     */
    private GestureDetectGridView gridView;

    /**
     * Column width and height.
     */
    private static int columnWidth, columnHeight;

    /**
     * Current instance of the AccountManager used by the app
     */
    private AccountManager accountManager;

    /**
     * The manager used by this activity to load and save files
     */
    private FileManager fileManager;

    /**
     * All other UI elements.
     */
    private TextView movesTV;
    private TextView lastSaveTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity_sliding_tiles);
        control = new SlidingTilesController();

        init();
        setUpGrid();
        setUpOtherUI();
        addButtonListeners();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (accountManager.currentlyLoggedIn()) {
            fileManager.save(control.getBoardManager(), ActivitySlidingTiles.TEMP_SAVE_FILENAME);
        }
    }

    /**
     * The height and width of the columns used to make the board.
     */
    @Override
    public void init() {
        fileManager = new FileManager(this.getApplicationContext());
        control.setBoardManager((BoardManagerSlidingTiles) (fileManager.load(ActivitySlidingTiles.TEMP_SAVE_FILENAME)));
        accountManager = AccountManager.getInstance();
    }

    /**
     * Sets up the grid.
     */
    private void setUpGrid() {
        control.constructImages(getResources());
        tileButtons = control.createTileButtons(this);
        addViewToActivity();
        setUpDimensions();
    }

    /**
     * Sets up the UI elements that are not the grid.
     */
    private void setUpOtherUI() {
        movesTV = findViewById(R.id.slidingCurrentMoveTV);
        lastSaveTV = findViewById(R.id.slidingLastSaveTV);

        updateMovesTV();
        updateLastSaveTV();
    }

    @Override
    public void addButtonListeners() {
        addSaveGameButtonListener();
        addUndoButtonListener();
    }

    /**
     * Adds functionality to the undo button
     */
    private void addUndoButtonListener() {
        Button undoButton = findViewById(R.id.UndoB);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                control.undo(getApplicationContext());

            }
        });
    }

    /**
     * Adds a listener for the save button.
     */
    private void addSaveGameButtonListener() {
        Button saveButton = findViewById(R.id.slidingSaveGameButton);
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
            accountManager.getCurrentAccount().setLastManager(0, control.getBoardManager());
            fileManager.save(accountManager.getAccountMap(), "Accounts.ser");
            updateLastSaveTV();
            makeToastSavedText();
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
     * Sets up the dimensions.
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
     * Updates the moves TextView to reflect anychanges.
     */
    private void updateMovesTV() {
        String currentMoveText = getString(R.string.current_move_sliding);
        int currentMoveInt = control.getMoves();

        currentMoveText = String.format(currentMoveText, currentMoveInt);
        movesTV.setText(currentMoveText);
    }

    /**
     * Updates the last save TextView to reflect any changes.
     */
    private void updateLastSaveTV() {
        String lastSaveText = getString(R.string.last_save_sliding);
        int lastSaveInt = control.getMoves();

        lastSaveText = String.format(lastSaveText, lastSaveInt);
        lastSaveTV.setText(lastSaveText);
    }

    @Override
    public void update(Observable o, Object arg) {
        updateMovesTV();

        if (isTimeToAutoSave()) {
            saveAction();
        }
        display();
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
     * Tell the user that they are not logged in
     */
    private void makeToastNotLogged() {
        Toast.makeText(this.getApplicationContext(),
                "You have to be logged in to save your games.", Toast.LENGTH_LONG).show();
    }


    /**
     * Returns whether or not it is time to autosave.
     *
     * @return whether or not the number of moves is divisible by 3.
     */
    private boolean isTimeToAutoSave() {
        return control.getMoves() % control.getBoardManager().getSAVE_MOVE_LIMIT() == 0 &&
                accountManager.currentlyLoggedIn();
    }
}
