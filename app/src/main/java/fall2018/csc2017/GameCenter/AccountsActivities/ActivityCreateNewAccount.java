package fall2018.csc2017.GameCenter.AccountsActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import java.util.HashMap;

import fall2018.csc2017.GameCenter.*;
import fall2018.csc2017.GameCenter.Accounts.Account;
import fall2018.csc2017.GameCenter.Accounts.AccountManager;
import fall2018.csc2017.GameCenter.Technical.FileManager;
import fall2018.csc2017.GameCenter.Technical.Initializable;

/**
 * Activity for the create new account screen
 */
public class ActivityCreateNewAccount extends AppCompatActivity implements Initializable {
    /**
     * The related file manager for this account.
     */
    private FileManager fileManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_account);

        init();
        addButtonListeners();
    }

    /**
     * Adds the account button listener
     */
    private void addCreateAccountButtonListener() {
        Button button = findViewById(R.id.CreateAccountB);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });
    }

    /**
     * Create the user account as an object and save it to files.
     */
    private void createAccount() {
        TextView userT = findViewById(R.id.NewUserNameT);
        TextView passwordT = findViewById(R.id.NewPasswordT);

        String newUserName = userT.getText().toString();
        String newPassword = passwordT.getText().toString();

        if (createAccountAttempt(newUserName, newPassword)) {
            Intent intent = new Intent(getApplicationContext(), ActivityLogIn.class);
            startActivity(intent);
        }
    }

    /**
     * Attempt to create a new account if no such account already exists.
     *
     * @param newUserName user name to be used by the new account
     * @param newPassword password to be used by the new account
     * @return whether the account creation was successful or not
     */
    private boolean createAccountAttempt(String newUserName, String newPassword) {
        Account account = new Account(newPassword);
        AccountManager accountManager = AccountManager.getInstance();
        HashMap<String, Account> accountMap = accountManager.getAccountMap();

        // Add the account if the username is not taken, and neither field is null
        if ((newUserName.equals("")) || (newPassword.equals(""))) {
            emptyFieldToast();
            return false;
        } else if (!accountMap.containsKey(newUserName)) {
            accountMap.put(newUserName, account);
            fileManager.save(accountMap, "Accounts.ser");
            successToast();
            return true;
        } else {
            nameTakenToast();
            return false;
        }
    }

    /**
     * Make a new emptyFieldToast
     */
    private void emptyFieldToast() {
        Toast.makeText(getApplicationContext(), "You must fill in all fields",
                Toast.LENGTH_SHORT).show();
    }

    /**
     * Make a new toast alerting the user that the username is already taken.
     */
    private void nameTakenToast() {
        Toast.makeText(getApplicationContext(),
                "This username already exists. Try being more original next time.",
                Toast.LENGTH_SHORT).show();
    }

    /**
     * Make a new toast indicating success of a given operation.
     */
    private void successToast() {
        Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void init() {
        fileManager = new FileManager(this.getApplicationContext());
    }

    @Override
    public void addButtonListeners() {
        addCreateAccountButtonListener();
    }
}
