package fall2018.csc2017.GameCenter.GameStructure;

import java.io.Serializable;

/**
 * Move information for a single move in game pipelines.
 */
public class MovePipelines extends Move implements Serializable {
    /**
     * A new Pipelines game move with coordinates indicated by row and column.
     */
    public MovePipelines(int row, int col) {
        super(row, col);
    }
}
