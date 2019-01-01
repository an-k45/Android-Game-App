package fall2018.csc2017.GameCenter.GameStructure;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Implementation of the Stack ADT that auto-removes the furthest back if the maxSize limit is exceeded.
 */
public class LimitedStack implements Serializable {
    /**
     * The maximum size of contents, null if unlimited.
     */
    private Integer maxSize;
    /**
     * The contents of the stack.
     */
    private ArrayList<Move> contents;

    /**
     * Creates a LimitedStack with a set number or infinite amount of spaces.
     *
     * @param maxSize: the maximum size of contents
     */
    public LimitedStack(int maxSize) {
        this.maxSize = maxSize == -1 ? null : maxSize;
        contents = new ArrayList<>();
    }

    /**
     * Add a Move to the front of the Stack.
     *
     * @param Move: the most recent Move
     */
    public void add(Move Move) {
        contents.add(Move);
        if (maxSize != null && contents.size() > maxSize) {
            contents.remove(0);
        }
    }

    /**
     * Return the last item in the Stack, else null is empty.
     *
     * @return the last item in the Stack, else null if empty.
     */
    public Move remove() {
        if (this.isEmpty()) {
            return null;
        }
        return contents.remove(contents.size() - 1);
    }

    /**
     * @return whether the contents list is empty
     */
    public boolean isEmpty() {
        return contents.isEmpty();
    }
}
