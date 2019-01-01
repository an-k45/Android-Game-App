package fall2018.csc2017.GameCenter.ScoringActivities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;

import fall2018.csc2017.GameCenter.Accounts.Account;
import fall2018.csc2017.GameCenter.Accounts.AccountManager;
import fall2018.csc2017.GameCenter.GameManagers.BoardManager;
import fall2018.csc2017.GameCenter.R;
import fall2018.csc2017.GameCenter.Scoring.Score;

/**
 * The scoreboard for the 4096 game.
 */
public class SlidingTilesFragment extends Fragment {
    /**
     * All scores from all users for 4096
     */
    ArrayList<Score> slidingScores;

    /**
     * All accounts which have scores for this game
     */
    HashMap<String, Account> slidingAccounts = AccountManager.getInstance().getAccountMap();

    /**
     * High score display.
     */
    private TextView highscoreTV;

    /**
     * Inflates this fragment over the given container
     */
    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sliding_tiles, null);
    }

    /**
     * On the creation of this fragment, assigns each Text view to a variable.
     * Then, sets the contents of these TextBoxes based off the list of scores, and the current
     * user's personal high score
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        highscoreTV = Objects.requireNonNull(getView()).findViewById(R.id.slidingHighscore);
        scoreRanking();
        if (AccountManager.getInstance().currentlyLoggedIn()) {
            personalScore();
        } else {
            highscoreTV.setText(getString(R.string.high_score_log_in_text));
        }
    }

    /**
     * Sets the personal highscore TextView with the appropriate highscore.
     */
    private void personalScore() {
        String currentUser = AccountManager.getInstance().getCurrentUser();
        ArrayList<BoardManager> currentUserBMs = slidingAccounts.get(currentUser).getManagersList(0);
        ArrayList<Integer> currentUserScores = new ArrayList<>();
        for (BoardManager boardManager : currentUserBMs) {
            currentUserScores.add(boardManager.getScoreObject().getScore());
        }
        int personalHighScore = 0;
        for (Integer i : currentUserScores) {
            if (i > personalHighScore) {
                personalHighScore = i;
            }
        }
        String personalHSText = getString(R.string.personal_high_score_text);
        personalHSText = String.format(personalHSText, Integer.toString(personalHighScore));
        highscoreTV.setText(personalHSText);
    }

    /**
     * Sorts scores fom highest to lowest
     */
    private void scoreRanking() {
        slidingScores = new ArrayList<>();

        for (Account account : slidingAccounts.values()) {
            ArrayList<BoardManager> boardManagers = account.getManagersList(0);
            for (int i = 0; i < boardManagers.size(); i++) {
                Score score = boardManagers.get(i).getScoreObject();
                slidingScores.add(score);
            }
        }

        Collections.sort(slidingScores);
        Collections.reverse(slidingScores);

        ListAdapter listAdapter = new CustomListAdapter(Objects.requireNonNull(getContext()), slidingScores);
        ListView listView = Objects.requireNonNull(getView()).findViewById(R.id.slidingListView);
        listView.setAdapter(listAdapter);

    }
}
