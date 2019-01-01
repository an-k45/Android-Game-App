package fall2018.csc2017.GameCenter.AccountsActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import fall2018.csc2017.GameCenter.*;
import fall2018.csc2017.GameCenter.Accounts.AccountManager;
import fall2018.csc2017.GameCenter.Technical.FileManager;
import fall2018.csc2017.GameCenter.Technical.Initializable;

/**
 * User account activity.
 */
public class ActivityYourAccount extends AppCompatActivity implements Initializable {

    /**
     * Whether the user is logged in or not.
     */
    private static boolean loggedIn;

    /**
     * The file manager for user account activity.
     */
    FileManager fileManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_account);

        setLogButton();

        addButtonListeners();
        init();
    }

    /**
     * Create the on-screen button to log in and log out.
     */
    private void addLogInOutButtonListener() {
        Button LogInOutB = findViewById(R.id.LogInOutB);

        if (loggedIn) {
            loggedIn = false;
            LogInOutB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), StartingActivity.class);
                    fileManager.save(AccountManager.getInstance().getAccountMap(), "Accounts.ser");
                    loggedIn = false;
                    AccountManager.getInstance().setCurrentUser(null);
                    startActivity(intent);
                }
            });
        } else {
            LogInOutB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), ActivityLogIn.class);
                    startActivity(intent);
                }
            });
        }
    }

    /**
     * Set text for the log in/out button, depending on whether the user is logged in or not.
     */
    private void setLogButton() {
        Button LogInOutB = findViewById(R.id.LogInOutB);

        if (loggedIn) {
            LogInOutB.setText(getString(R.string.log_out_button_text));
        } else {
            LogInOutB.setText(getString(R.string.log_in_button_text));
        }
    }

    /**
     * Set the status of the user being logged in.
     *
     * @param isLoggedIn whether the user should be logged in or not
     */
    public static void setLoggedIn(boolean isLoggedIn) {
        loggedIn = isLoggedIn;
    }

    @Override
    public void init() {
        fileManager = new FileManager(this.getApplicationContext());
    }

    @Override
    public void addButtonListeners() {
        addLogInOutButtonListener();
    }
}
