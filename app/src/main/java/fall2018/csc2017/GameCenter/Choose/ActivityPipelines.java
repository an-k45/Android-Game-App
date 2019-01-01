package fall2018.csc2017.GameCenter.Choose;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import fall2018.csc2017.GameCenter.Accounts.AccountManager;
import fall2018.csc2017.GameCenter.GameActivites.GameActivityPipelines;
import fall2018.csc2017.GameCenter.GameManagers.BoardManagerPipelines;
import fall2018.csc2017.GameCenter.R;
import fall2018.csc2017.GameCenter.Technical.Initializable;

/**
 * Menu activity for Pipelines game
 */
public class ActivityPipelines extends AppCompatActivity implements Initializable {

    private AccountManager accountManager = AccountManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pipelines);

        addButtonListeners();
    }

    /**
     * Add a play game button listener
     */
    private void addPlayGameButtonListener() {
        final Button newGameButton = findViewById(R.id.PlayGameB);

        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newGameAction();
                Intent intent = new Intent(getApplicationContext(), GameActivityPipelines.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Performs the actions necessary to start a new game.
     */
    private void newGameAction() {
        if (accountManager.currentlyLoggedIn()) {
            accountManager.getCurrentAccount().addPipelinesManager(new BoardManagerPipelines(8, 8));
        }
    }


    @Override
    public void init() {

    }

    @Override
    public void addButtonListeners() {
        addPlayGameButtonListener();
    }

}
