package fall2018.csc2017.GameCenter.AccountsActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import fall2018.csc2017.GameCenter.Accounts.Account;
import fall2018.csc2017.GameCenter.Accounts.AccountManager;
import fall2018.csc2017.GameCenter.R;
import fall2018.csc2017.GameCenter.StartingActivity;
import fall2018.csc2017.GameCenter.Technical.Initializable;


/**
 * Activity for logging into the user account.
 */
public class ActivityLogIn extends AppCompatActivity implements Initializable {
    /**
     * Text view of the username.
     */
    private TextView usernameText;
    /**
     * Text view of the password.
     */
    private TextView passText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        addButtonListeners();
        init();
    }

    /**
     * Return whether the account info is correct or not.
     *
     * @param user the username
     * @param pass the password
     * @return whether the account info is correct or not.
     */
    private boolean correctAccountInfo(String user, String pass) {
        HashMap<String, Account> accounts = AccountManager.getInstance().getAccountMap();
        if (accounts.containsKey(user) && accounts.get(user).getPassword().equals(pass)) {
            ActivityYourAccount.setLoggedIn(true);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check log in info and starts activates if valid.
     *
     * @param user     the user's username
     * @param password the user's password
     */
    private void logInInfoCheck(String user, String password) {
        if (correctAccountInfo(user, password)) {
            AccountManager.getInstance().setCurrentUser(user);
            Intent intent = new Intent(getApplicationContext(), StartingActivity.class);
            startActivity(intent);
        } else {
            makeWrongAccountInfoToast();
        }
    }

    /**
     * Wrong account info toast text.
     */
    private void makeWrongAccountInfoToast() {
        Toast.makeText(this, "Invalid username or password!", Toast.LENGTH_LONG).show();
    }

    /**
     * Create the on-screen button for logging in.
     */
    private void addLogInButtonListener() {
        Button LogInB = findViewById(R.id.LogInB);
        LogInB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user = usernameText.getText().toString();
                String password = passText.getText().toString();

                logInInfoCheck(user, password);
            }
        });
    }

    /**
     * Create the on-screen button for signing up.
     */
    private void addSignUpButtonListener() {
        Button SignUpB = findViewById(R.id.SignUpB);
        SignUpB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityCreateNewAccount.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void init() {
        usernameText = findViewById(R.id.UserNameT);
        passText = findViewById(R.id.PasswordT);
    }

    @Override
    public void addButtonListeners() {
        addLogInButtonListener();
        addSignUpButtonListener();
    }
}
