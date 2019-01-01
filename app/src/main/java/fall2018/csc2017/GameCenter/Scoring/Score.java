package fall2018.csc2017.GameCenter.Scoring;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * A recorded score for a game and a user
 */
public class Score implements Comparable<Score>, Serializable {

    /**
     * The recorded score value
     */
    private int score;

    /**
     * The user who attained the score
     */
    private String user;

    /**
     * Creates a new score with a recorded score value and a user who is associated with the score
     */
    public Score(int score, String user) {
        this.score = score;
        this.user = user;
    }

    /**
     * Creates a new score with a recorded score value
     */
    public Score(int score) {
        this.score = score;
        this.user = "None";
    }

    /**
     * @return the recorded score value
     */
    public int getScore() {
        return score;
    }

    /**
     * @param score the new score to be set
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * @return the user that attained the score
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the new user to be associated with the score
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @param other the other score to be compared with
     * @return whether this score value is a larger integer compared to the other score value
     */
    @Override
    public int compareTo(@NonNull Score other) {
        return Integer.compare(this.score, other.getScore());
    }
}
