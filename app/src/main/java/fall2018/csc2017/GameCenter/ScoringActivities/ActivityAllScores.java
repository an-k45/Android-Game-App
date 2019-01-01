package fall2018.csc2017.GameCenter.ScoringActivities;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import fall2018.csc2017.GameCenter.R;

/**
 * Shows the scoreboards for all games
 */
public class ActivityAllScores extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    /**
     * On the creation of this activity, loads the default fragment, namely the Sliding Tiles
     * scoreboard
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_scores);

        loadFragment(new SlidingTilesFragment());

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
    }

    /**
     * Replaces the current fragment displayed with another fragment
     */
    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment).commit();
            return true;
        }
        return false;
    }

    /**
     * When a button on the navigation menu is pressed, switches the current fragment with the
     * button's corresponding fragment.
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.navigation_sliding_tiles:
                fragment = new SlidingTilesFragment();
                break;

            case R.id.navigation_pipelines:
                fragment = new PipelinesFragment();
                break;

            case R.id.navigation_4096:
                fragment = new _4096Fragment();
                break;
        }

        return loadFragment(fragment);
    }
}
