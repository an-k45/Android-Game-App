package fall2018.csc2017.GameCenter.Accounts;

import java.io.Serializable;
import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * Singleton class that allows for the managing of accounts by all activities in the app.
 */
public class AccountManager implements Serializable {/*BASED ON: https://www.geeksforgeeks.org/singleton-class-java/
ALL CREDIT FOR THE ORIGINAL IMPLEMENTATION OF A SIMILAR SINGLETON GOES TO THE ORIGINAL AUTHOR OF
    THE CODE.*/
    /**
     * Current instance of the Singleton
     */
    private static AccountManager accManagerInstance = null;

    /**
     * Map of usernames to their account information.
     */
    private HashMap<String, Account> accountMap;

    /**
     * Username of the currently logged in user.
     */
    private String currentUser = null;

    /**
     * Create an empty instance of AccountManager singleton
     */
    private AccountManager() {
    }

    /**
     * Return and provide access to the current instance of the AccountManager singleton or
     * make a new one if the current one is null.
     *
     * @return the current instance of the AccountManager or a new instance if the current is null
     */
    public static AccountManager getInstance() {
        if (accManagerInstance == null) {
            accManagerInstance = new AccountManager();
        }
        return accManagerInstance;
    }

    /**
     * Return the map of usernames to their Account objects stored by this AccountManager.
     *
     * @return the current account hash map
     */
    public HashMap<String, Account> getAccountMap() {
        return accountMap;
    }

    /**
     * Set the current account hash map to a new map
     *
     * @param accountMap new map to set the old one to
     */
    public void setAccountMap(HashMap<String, Account> accountMap) {
        this.accountMap = accountMap;
    }

    /**
     * Return the username of the currently logged in user.
     *
     * @return account user name of the user
     */
    public String getCurrentUser() {
        return currentUser;
    }

    /**
     * Set the username of the current user to a another
     *
     * @param currentUser the username to be switched to
     */
    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * Return the current user's Account.
     *
     * @return Account associated with current user
     */
    public Account getCurrentAccount() {
        if (accountMap.containsKey(currentUser)) {
            return accountMap.get(currentUser);
        } else {
            throw new NoSuchElementException();
        }
    }

    /**
     * Return true or false based on whether there is a user currently logged in.
     *
     * @return true if a user is logged in, false otherwise
     */
    public boolean currentlyLoggedIn() {
        return currentUser != null;
    }
}
