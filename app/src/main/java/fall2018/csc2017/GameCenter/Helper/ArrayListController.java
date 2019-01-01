package fall2018.csc2017.GameCenter.Helper;

import java.io.Serializable;
import java.util.ArrayList;

import fall2018.csc2017.GameCenter.GameStructure.Board;
import fall2018.csc2017.GameCenter.Tiles.SlidingTile;
import fall2018.csc2017.GameCenter.Tiles.Tile;

/**
 * Controller class to manipulate array lists.
 */
public class ArrayListController implements Serializable {

    /**
     * Fill the passed empty ArrayList with integers from min to max inclusive. If the array list
     * is not empty, it will be cleared prior to appending.
     * <p>
     * precondition: list is empty
     *
     * @param list array list to be filled
     * @param min  smallest value in the list
     * @param max  largest value in the list
     */
    public void fillWithInts(ArrayList<Integer> list, int min, int max) {
        if (!list.isEmpty()) {
            list.clear();
        }
        for (int i = min; i <= max; i++) {
            list.add(i);
        }
    }

    /**
     * Return the number of inversions in the provided list
     *
     * @param list ArrayList of integers to be searched for inversions
     * @return number of inversions in list
     */
    public int getInversions(ArrayList<Integer> list) {
        int inversions = 0;

        for (int i = 0; i < list.size(); i++) {
            for (int j = i; j < list.size(); j++) {
                if (list.get(i) > list.get(j)) {
                    inversions++;
                }
            }
        }
        return inversions;
    }

    /**
     * Fill the given ArrayList with tile ids from the provided Board object, skipping the
     * blank tile.
     *
     * @param board board the tiles of which are to be used
     * @param list  list to be filled
     */
    public void fillListNoBlank(Board board, ArrayList<Integer> list) {
        for (Tile t : board) {
            if (((SlidingTile) t).getId() != board.getNumRows() * board.getNumRows()) {
                list.add(((SlidingTile) t).getId());
            }
        }
    }
}
