package fall2018.csc2017.GameCenter.AccountsTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.NoSuchElementException;

import fall2018.csc2017.GameCenter.Accounts.Account;
import fall2018.csc2017.GameCenter.Accounts.AccountManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Class for testing the AccountManager singleton.
 */
public class AccountManagerTest {

    /**
     * Singleton instance to be used during testing.
     */
    private AccountManager testManagerInstance;

    /**
     * Set up a private singleton instance before each test.
     */
    @Before
    public void setUp() {
        testManagerInstance = AccountManager.getInstance();
    }

    /**
     * Discard the private singleton instance after each test.
     */
    @After
    public void tearDown() {
        testManagerInstance.setCurrentUser(null);
        testManagerInstance.setAccountMap(null);
        testManagerInstance = null;
    }

    /**
     * Test whether getInstance() correctly returns a non null instance of the singleton.
     */
    @Test
    public void getInstance() {
        assertNotNull(testManagerInstance);
    }

    /**
     * Test whether the current instance of AccountManager creates a new HashMap when it is made.
     */
    @Test
    public void getAccountMap() {
        assertNull(testManagerInstance.getAccountMap());
    }

    /**
     * Test whether the setAccountMap() of the AccountManager instance is set correctly.
     */
    @Test
    public void setAccountMap() {
        HashMap<String, Account> testMap = new HashMap<>();
        testMap.put("test_user", new Account("123"));
        assertNull(testManagerInstance.getAccountMap());
        testManagerInstance.setAccountMap(testMap);
        assertTrue(testManagerInstance.getAccountMap().containsKey("test_user"));
    }

    /**
     * Test whether current user is properly returned as null before the user is set and then
     * changed when a new user is set.
     */
    @Test
    public void getCurrentUser() {
        assertNull(testManagerInstance.getCurrentUser());
        testManagerInstance.setCurrentUser("test");
        assertEquals("test", testManagerInstance.getCurrentUser());
    }

    /**
     * Test whether a user is properly set by setCurrentUser().
     */
    @Test
    public void setCurrentUser() {
        testManagerInstance.setCurrentUser("test");
        assertEquals("test", testManagerInstance.getCurrentUser());
        testManagerInstance.setCurrentUser("test1");
        assertEquals("test1", testManagerInstance.getCurrentUser());
    }

    /**
     * Test whether getCurrentAccount() returns the correct active account.
     */
    @Test
    public void getCurrentAccount() {
        HashMap<String, Account> testMap = new HashMap<>();
        testMap.put("test_user", new Account("123"));
        testManagerInstance.setAccountMap(testMap);
        testManagerInstance.setCurrentUser("test_user");
        assertNotNull(testManagerInstance.getCurrentAccount());
    }

    /**
     * Test whether getCurrentAccount() throws the proper exception when no account is active.
     */
    @Test(expected = NoSuchElementException.class)
    public void getCurrentAccountNoAccount() {
        testManagerInstance.setAccountMap(new HashMap<String, Account>());
        testManagerInstance.getCurrentAccount();
    }

    /**
     * Test whether AccountManager correctly identifies when a user is logged in and when they are
     * not.
     */
    @Test
    public void currentlyLoggedIn() {
        assertFalse(testManagerInstance.currentlyLoggedIn());
        testManagerInstance.setCurrentUser("test");
        assertTrue(testManagerInstance.currentlyLoggedIn());
    }
}