package fall2018.csc2017.GameCenter.GameActivites;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import fall2018.csc2017.GameCenter.Accounts.AccountManager;
import fall2018.csc2017.GameCenter.ActivityControllers.ActivityController;
import fall2018.csc2017.GameCenter.GameManagers.BoardManagerPipelines;
import fall2018.csc2017.GameCenter.R;
import fall2018.csc2017.GameCenter.Technical.CustomAdapter;
import fall2018.csc2017.GameCenter.Technical.FileManager;
import fall2018.csc2017.GameCenter.Technical.GestureDetectGridView;
import fall2018.csc2017.GameCenter.Technical.Initializable;

/**
 * Game activity for pipelines game
 */
public class GameActivityPipelines extends AppCompatActivity implements Observer, Initializable {

    /**
     * Controller for the logic of this activity
     */
    private ActivityController control;

    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;

    /**
     * Grid View and calculated column height and width based on device size
     */
    private GestureDetectGridView gridView;

    /**
     * The height and width of the columns used to make the board.
     */
    private static int columnWidth, columnHeight;

    /**
     * Current instance of the AccountManager used by the app
     */
    private AccountManager manager = AccountManager.getInstance();

    /**
     * The manager used by this activity to load and save files
     */
    private FileManager fileManager;

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    // Display
    public void display() {
        control.updateTileButtons(tileButtons);
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        control = new ActivityController();
        init();
        tileButtons = control.createTileButtons(this);
        setContentView(R.layout.game_activity_pipelines);

        // Add View to activity
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(control.getCols());
        gridView.setBoardManager(control.getBoardManager());
        control.getBoard().addObserver(this);
        // Observer sets up desired dimensions as well as calls our display function
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
        addButtonListeners();
    }

    /**
     * Adds functionality to the undo button
     */
    private void addUndoButtonListener() {
        Button UndoButton = findViewById(R.id.UndoB);
        UndoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                control.undo(getApplicationContext());
            }
        });
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (manager.currentlyLoggedIn()) {
            fileManager.save(control.getBoardManager(), "save_file_tmp.ser");
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (AccountManager.getInstance().currentlyLoggedIn()) {
            AccountManager.getInstance().getCurrentAccount().setLastManager(2, control.getBoardManager());
        }

        display();
    }

    /**
     * If the user is logged in, sets their SlidingTiles BoardManagerSlidingTiles to the one currently being
     * used.
     */
    public void setAccountGameManager() {
        if (manager.getAccountMap().containsKey(manager.getCurrentUser())) {
            control.getBoardManager().getScoreObject().setUser(manager.getCurrentUser());
        }
    }

    @Override
    public void init() {
        fileManager = new FileManager(this.getApplicationContext());
        control.setBoardManager(new BoardManagerPipelines(8, 8));
        setAccountGameManager();
    }

    @Override
    public void addButtonListeners() {
        addUndoButtonListener();
    }
}
