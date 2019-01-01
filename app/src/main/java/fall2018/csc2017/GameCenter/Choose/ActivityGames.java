package fall2018.csc2017.GameCenter.Choose;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import fall2018.csc2017.GameCenter.R;
import fall2018.csc2017.GameCenter.Technical.Initializable;

/**
 * Activity for choosing the game to be played.
 */
public class ActivityGames extends AppCompatActivity implements Initializable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);

        addButtonListeners();
    }

    /**
     * Add button listener for the SlidingTilesB button.
     */
    private void addSlidingTilesButtonListener() {
        Button SlidingTiles = findViewById(R.id.SlidingTilesB);
        SlidingTiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivitySlidingTiles.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Add button listener for the 4096 game.
     */
    private void addButton4096Listener() {
        Button button4096 = findViewById(R.id.button4096);
        button4096.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity4096.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Add button listener for Pipelines game.
     */
    private void addPipelinesButtonListener() {
        Button pipelinesButton = findViewById(R.id.PipelinesB);
        pipelinesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityPipelines.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void init() {
    }

    @Override
    public void addButtonListeners() {
        addSlidingTilesButtonListener();
        addButton4096Listener();
        addPipelinesButtonListener();
    }
}
