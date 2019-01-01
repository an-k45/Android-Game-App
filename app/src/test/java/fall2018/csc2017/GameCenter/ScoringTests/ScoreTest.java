package fall2018.csc2017.GameCenter.ScoringTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fall2018.csc2017.GameCenter.Scoring.Score;

import static org.junit.Assert.*;

/**
 * Test class for testing Score.
 */
public class ScoreTest {

    /**
     * Score object to be used in testing.
     */
    private Score testScore;

    /**
     * Create a new score object with a score and a user before each test.
     */
    @Before
    public void setUp() {
        testScore = new Score(20, "User1");
    }

    /**
     * Discard the current score object after each test.
     */
    @After
    public void tearDown() {
        testScore = null;
    }

    /**
     * Create a new Score object with no user.
     */
    private void setUpScoreWithNoUser() {
        testScore = new Score(15);
    }

    /**
     * Test whether the score of a Score object is correctly returned by getScore().
     */
    @Test
    public void testGetScore() {
        assertEquals(20, testScore.getScore());
        setUpScoreWithNoUser();
        assertEquals(15, testScore.getScore());
    }

    /**
     * Test whether setScore() correctly sets the value of score of this Score object to the
     * specified value.
     */
    @Test
    public void testSetScore() {
        testScore.setScore(0);
        assertEquals(0, testScore.getScore());
    }

    /**
     * Test whether the user associated with the score is correctly returned by getUser()
     * when the score has  a user.
     */
    @Test
    public void testGetUserWithUser() {
        assertEquals("User1", testScore.getUser());
    }

    /**
     * Test whether the user is correctly returned as 'None' when the Score object has no user
     * associated with it.
     */
    @Test
    public void testGetUserNoUser() {
        setUpScoreWithNoUser();
        assertEquals("None", testScore.getUser());
    }

    /**
     * Test whether setUser() correctly sets the user name of the new user associated with the
     * Score object.
     */
    @Test
    public void testSetUser() {
        testScore.setUser("User123");
        assertEquals("User123", testScore.getUser());
    }

    /**
     * Test whether two Score objects are properly compared when they have the same score.
     */
    @Test
    public void testCompareToSameScore() {
        Score tempScore = new Score(15);
        setUpScoreWithNoUser();
        assertEquals(0, testScore.compareTo(tempScore));
    }

    /**
     * Test whether two scores are properly compared when the score being compared to has a
     * higher score than the one initiating the comparison.
     */
    @Test
    public void testCompareToLowerScore() {
        Score tempScore = new Score(100);
        assertEquals(-1, testScore.compareTo(tempScore));
    }

    /**
     * Test whether two scores are properly compared when the score being compared to has a
     * lower score than the one initiating the comparison.
     */
    @Test
    public void testCompareToGreaterScore() {
        Score tempScore = new Score(1);
        assertEquals(1, testScore.compareTo(tempScore));
    }
}