package fall2018.csc2017.GameCenter.AccountsTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import fall2018.csc2017.GameCenter.Accounts.Account;
import fall2018.csc2017.GameCenter.GameManagers.BoardManager;
import fall2018.csc2017.GameCenter.GameManagers.BoardManager4096;
import fall2018.csc2017.GameCenter.GameManagers.BoardManagerPipelines;
import fall2018.csc2017.GameCenter.GameManagers.BoardManagerSlidingTiles;
import fall2018.csc2017.GameCenter.GameManagers.GridMover;
import fall2018.csc2017.GameCenter.Scoring.Score;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Test class for Accounts
 */
public class AccountTest {

    /**
     * Account variable to test on.
     */
    private Account account;
    /**
     * A 2d array list of board managers that represents an account on which to compare with
     * Account account.
     */
    private ArrayList<ArrayList<BoardManager>> testManagers;

    /**
     * Creates a generic new account.
     */
    @Before
    public void setUp() {
        account = new Account("test_pass");
    }

    /**
     * Resets the account.
     */
    @After
    public void tearDown() {
        account = null;
    }

    /**
     * Tests the constructor if it creates the proper Account.
     */
    @Test
    public void testConstructor() {
        assertEquals("test_pass", account.getPassword());
    }

    /**
     * Tests if the getManagersList() method returns the correct lists of managers if they are empty
     * and if they are not.
     */
    @Test
    public void testGetManagersList() {
        addTestManagerLists();
        assertEquals(testManagers.get(0), account.getManagersList(0));
        assertEquals(testManagers.get(1), account.getManagersList(1));
        assertEquals(testManagers.get(2), account.getManagersList(2));

        addManagersToTestManagers();
        addManagersToAccount();
        assertEquals(testManagers.get(0).size(), account.getManagersList((0)).size());
        assertEquals(testManagers.get(1).size(), account.getManagersList((1)).size());
        assertEquals(testManagers.get(2).size(), account.getManagersList((2)).size());
    }

    /**
     * Tests if the getLastManager() method returns the correct manager if the ArrayList is empty
     * and if it is not.
     */
    @Test
    public void testGetLastManager() {
        assertNull(account.getLastManager(0));
        assertNull(account.getLastManager(1));
        assertNull(account.getLastManager(2));

        addTestManagerLists();
        addManagersToAccount();
        assert (account.getLastManager(0) instanceof BoardManagerSlidingTiles);
        assert (account.getLastManager(1) instanceof BoardManager4096);
        assert (account.getLastManager(2) instanceof BoardManagerPipelines);
    }

    /**
     * Tests if the setLastManager() method correctly sets the last element of the ArrayList to
     * the correct board manager.
     */
    @Test
    public void testSetLastManager() {
        addTestManagerLists();
        addManagersToAccount();

        BoardManagerSlidingTiles boardManagerSlidingTiles = new BoardManagerSlidingTiles(3,
                3);
        account.setLastManager(0, boardManagerSlidingTiles);

        assertEquals(boardManagerSlidingTiles, account.getLastManager(0));

    }

    /**
     * Check if NoSuchElement exception is correctly thrown by setLastManager() when there is no
     * manager to be set.
     */
    @Test(expected = NoSuchElementException.class)
    public void testSetLastManagerNoManager() {
        for (int i = 0; i < account.getManagerList().size(); i++) {
            account.getManagerList().get(i).clear();
        }
        account.setLastManager(0, new BoardManagerSlidingTiles(4, 4));
    }

    /**
     * Tests if the addSlidingTilesManager() correctly adds a sliding tile manager to the end of
     * the appropriate ArrayList.
     */
    @Test
    public void testAddSlidingTilesManager() {
        addTestManagerLists();

        BoardManagerSlidingTiles boardManagerSlidingTiles = new BoardManagerSlidingTiles(3,
                3);

        account.addSlidingManager(boardManagerSlidingTiles);
        assertEquals(boardManagerSlidingTiles, account.getLastManager(0));
    }

    /**
     * Tests if the add4096Manager() method correctly adds a 4096 manager to the end of the
     * appropriate ArrayList.
     */
    @Test
    public void testAdd4096Manager() {
        addTestManagerLists();

        BoardManager4096 boardManager4096 = new BoardManager4096(new GridMover(), new Score(0));

        account.add4096Manager(boardManager4096);
        assertEquals(boardManager4096, account.getLastManager(1));
    }

    /**
     * Tests if the addPipelinesManager() method correctly adds a pipelines manager to the end of
     * the appropriate ArrayList.
     */
    @Test
    public void testAddPipelinesManager() {
        addTestManagerLists();

        BoardManagerPipelines boardManagerPipelines = new BoardManagerPipelines(8, 8);

        account.addPipelinesManager(boardManagerPipelines);
        assertEquals(boardManagerPipelines, account.getLastManager(2));
    }

    /**
     * Tests if the getPassword() method returns the correct password for the account.
     */
    @Test
    public void testGetPassword() {
        assertEquals("test_pass", account.getPassword());
    }

    /**
     * Populates the testManagers ArrayList with 3 ArrayLists representing the types of
     * board managers.
     */
    private void addTestManagerLists() {
        testManagers = new ArrayList<>();

        ArrayList<BoardManager> slidingManagers = new ArrayList<>(0);
        ArrayList<BoardManager> _4096Managers = new ArrayList<>(0);
        ArrayList<BoardManager> pipelinesManagers = new ArrayList<>(0);

        testManagers.add(slidingManagers);
        testManagers.add(_4096Managers);
        testManagers.add(pipelinesManagers);
    }

    /**
     * Adds a single board manager of the appropriate type to each board manager list in the test
     * managers ArrayList.
     */
    private void addManagersToTestManagers() {
        BoardManagerSlidingTiles boardManagerSlidingTiles = new BoardManagerSlidingTiles(3,
                3);
        BoardManager4096 boardManager4096 = new BoardManager4096(new GridMover(), new Score(0));
        BoardManagerPipelines boardManagerPipelines = new BoardManagerPipelines(8, 8);
        testManagers.get(0).add(boardManagerSlidingTiles);
        testManagers.get(1).add(boardManager4096);
        testManagers.get(2).add(boardManagerPipelines);
    }

    /**
     * Adds a single board manager of the appropriate type to each of the boardmanager lists in the
     * account ArrayList.
     */
    private void addManagersToAccount() {
        BoardManagerSlidingTiles boardManagerSlidingTiles = new BoardManagerSlidingTiles(3,
                3);
        BoardManager4096 boardManager4096 = new BoardManager4096(new GridMover(), new Score(0));
        BoardManagerPipelines boardManagerPipelines = new BoardManagerPipelines(8, 8);
        account.getManagersList(0).add(boardManagerSlidingTiles);
        account.getManagersList(1).add(boardManager4096);
        account.getManagersList(2).add(boardManagerPipelines);

    }
}
