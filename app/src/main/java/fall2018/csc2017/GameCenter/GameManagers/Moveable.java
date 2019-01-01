package fall2018.csc2017.GameCenter.GameManagers;

/**
 * Interface to enable a class to move items on a Board
 */
public interface Moveable {

    /**
     * Make a move on the board at the given position
     */
    void touchMove(int position);
}
