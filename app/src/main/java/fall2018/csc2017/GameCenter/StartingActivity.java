package fall2018.csc2017.GameCenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;

import fall2018.csc2017.GameCenter.Accounts.Account;
import fall2018.csc2017.GameCenter.Accounts.AccountManager;
import fall2018.csc2017.GameCenter.AccountsActivities.ActivityYourAccount;
import fall2018.csc2017.GameCenter.Choose.ActivityGames;
import fall2018.csc2017.GameCenter.ScoringActivities.ActivityAllScores;
import fall2018.csc2017.GameCenter.Technical.FileManager;
import fall2018.csc2017.GameCenter.Technical.Initializable;

/**
 * The main menu activity
 */
public class StartingActivity extends AppCompatActivity implements Initializable {

    /**
     * File manager to be used by this activity to load from and save to files.
     */
    FileManager fileManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_starting_);
        addButtonListeners();
        init();

    }

    /**
     * Load or create account data.
     */
    private void loadAccounts() {
        if (fileManager.load("Accounts.ser") == null) {
            AccountManager.getInstance().setAccountMap(new HashMap<String, Account>());
            fileManager.save(AccountManager.getInstance().getAccountMap(), "Accounts.ser");
        } else if (fileManager.load("Accounts.ser") instanceof HashMap) {
            AccountManager.getInstance().setAccountMap(
                    (HashMap<String, Account>) fileManager.load("Accounts.ser"));
        }
    }

    /**
     * Create the button for your account.
     */
    private void addYourAccountButtonListener() {
        Button YourAccountB = findViewById(R.id.YourAccountB);
        YourAccountB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent YourAccountIntent = new Intent(getApplicationContext(), ActivityYourAccount.class);
                startActivity(YourAccountIntent);
            }
        });
    }

    /**
     * Create the button to access games.
     */
    private void addGamesButtonListener() {
        Button GamesB = findViewById(R.id.GamesB);
        GamesB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityGames.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Create the button to access the scoreboard.
     */
    private void addAllScoresButtonListener() {
        Button AllScoresB = findViewById(R.id.LogInB);
        AllScoresB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityAllScores.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void init() {
        fileManager = new FileManager(this.getApplicationContext());
        loadAccounts();
    }

    @Override
    public void addButtonListeners() {
        addYourAccountButtonListener();
        addGamesButtonListener();
        addAllScoresButtonListener();
    }
}
