package fall2018.csc2017.GameCenter.ScoringActivities;

import java.io.Serializable;

/**
 * Interface that enables any class that implements it calculate a score.
 */
public interface Scoreable extends Serializable {

    /**
     * Calculate the score based on the number of moves made and the difficulty setting of the game
     * and set the current score to the new one.
     * <p>
     * Precondition: moves >= 0
     *
     * @param moves      moves made
     * @param difficulty difficulty of the game
     */
    void updateScore(int moves, int difficulty);


}
