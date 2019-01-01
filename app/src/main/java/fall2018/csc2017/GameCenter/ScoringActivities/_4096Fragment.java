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
public class _4096Fragment extends Fragment {

    /**
     * All accounts which have scores for this game
     */
    private HashMap<String, Account> _4096Accounts = AccountManager.getInstance().getAccountMap();

    private TextView highscoreTV;

    /**
     * Inflates this fragment over the given container
     */
    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_4096, null);
    }

    /**
     * On the creation of this fragment, assigns each Text Box view to a variable.
     * Then, sets the contents of these Text Boxes based off the list of scores, and the current
     * user's personal high score
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        highscoreTV = Objects.requireNonNull(getView()).findViewById(R.id._4096Highscore);
        scoreRanking();
        if (AccountManager.getInstance().currentlyLoggedIn()) {
            personalScore();
        } else {
            highscoreTV.setText(getString(R.string.high_score_log_in_text));
        }
    }

    /**
     * Sets the personal highscore TextView with the appropriate highscore.
     * Set the text for a high score if the user is logged in.
     */
    private void personalScore() {
        String currentUser = AccountManager.getInstance().getCurrentUser();
        ArrayList<BoardManager> currentUserBMs = _4096Accounts.get(currentUser).getManagersList(1);
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
     * Sort scores fom highest to lowest
     */
    private void scoreRanking() {
        ArrayList<Score> _4096Scores = new ArrayList<>();
        for (Account account : _4096Accounts.values()) {
            ArrayList<BoardManager> boardManagers = account.getManagersList(1);
            for (int i = 0; i < boardManagers.size(); i++) {
                Score score = boardManagers.get(i).getScoreObject();
                _4096Scores.add(score);
            }
        }

        Collections.sort(_4096Scores);
        Collections.reverse(_4096Scores);

        ListAdapter listAdapter = new CustomListAdapter(Objects.requireNonNull(getContext()), _4096Scores);
        ListView listView = Objects.requireNonNull(getView()).findViewById(R.id._4096ListView);
        listView.setAdapter(listAdapter);
    }
}
