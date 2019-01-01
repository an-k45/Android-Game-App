package fall2018.csc2017.GameCenter.Accounts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import fall2018.csc2017.GameCenter.GameManagers.BoardManager;

/**
 * Handle individual user accounts
 */
public class Account implements Serializable {

    /**
     * account password
     */
    private String password;

    /**
     * ArrayList that stores all game managers for this account
     * 0 - SlidingTiles
     * 1 - 4096
     * 2 - Pipelines
     */
    private ArrayList<ArrayList<BoardManager>> managers;

    /**
     * Create a new Account with password and username
     *
     * @param password password of this account
     */
    public Account(String password) {
        this.password = password;

        managers = new ArrayList<>();
        ArrayList<BoardManager> slidingManagers = new ArrayList<>(0);
        ArrayList<BoardManager> _4096Managers = new ArrayList<>(0);
        ArrayList<BoardManager> pipelinesManagers = new ArrayList<>(0);

        managers.add(slidingManagers);
        managers.add(_4096Managers);
        managers.add(pipelinesManagers);
    }

    /**
     * Return the overall list of board manager lists for this user's account.
     *
     * @return ArrayList of board managers
     */
    public ArrayList<ArrayList<BoardManager>> getManagerList() {
        return this.managers;
    }

    /**
     * Return the game manager at the requested index
     *
     * @param index index of the game manager being returned
     * @return BoardManager at the specified index in managers ArrayList
     */
    public ArrayList<BoardManager> getManagersList(int index) {
        return managers.get(index);
    }

    /**
     * Returns the last boardmanager at the given index of boardmanager array lists.
     *
     * @param index index of which boardmanager list to use.
     * @return the board manager at the last index of the given board manager array list.
     */
    public BoardManager getLastManager(int index) {
        if (managers.get(index).isEmpty()) {
            return null;
        } else {
            int indexOfLast = managers.get(index).size() - 1;
            return managers.get(index).get(indexOfLast);
        }
    }

    /**
     * Setter for the last board manager at the given index of the boardmanager array lists.
     *
     * @param index        index of which boardmanager list to use.
     * @param boardManager the new board manager to set to.
     */
    public void setLastManager(int index, BoardManager boardManager) {
        if (managers.get(index).isEmpty()) {
            throw new NoSuchElementException("This method should never be" +
                    "called if the managers list is empty.");
        } else {
            int indexOfLast = managers.get(index).size() - 1;
            managers.get(index).set(indexOfLast, boardManager);
        }
    }

    /**
     * Adds a new sliding tiles board manager to the corresponding array list.
     *
     * @param boardManager the new board manager to add.
     */
    public void addSlidingManager(BoardManager boardManager) {
        managers.get(0).add(boardManager);
    }

    /**
     * Adds a new 4096 board manager to the corresponding array list.
     *
     * @param boardManager the new board manager to add
     */
    public void add4096Manager(BoardManager boardManager) {
        managers.get(1).add(boardManager);
    }

    /**
     * Adds a new pipelines board manager to the corresponding array list.
     *
     * @param boardManager the new board manager to add.
     */
    public void addPipelinesManager(BoardManager boardManager) {
        managers.get(2).add(boardManager);
    }

    /**
     * returns the password associated with this account.
     *
     * @return a password string.
     */
    public String getPassword() {
        return password;
    }
}
