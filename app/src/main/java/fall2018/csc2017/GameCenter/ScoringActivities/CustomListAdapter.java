package fall2018.csc2017.GameCenter.ScoringActivities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fall2018.csc2017.GameCenter.R;
import fall2018.csc2017.GameCenter.Scoring.Score;

/**
 * A custom list adapter to populate a ListView with items from an ArrayList of Score Objects.
 */
public class CustomListAdapter extends ArrayAdapter<Score> {
    /**
     * Create a new Custom Adapter for a List View.
     *
     * @param context Context of the list view
     * @param list    list to populate the view with
     */
    public CustomListAdapter(@NonNull Context context, ArrayList<Score> list) {
        super(context, R.layout.custom_row, list);
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        @SuppressLint("ViewHolder") View customView = layoutInflater.inflate(R.layout.custom_row, parent, false);

        Score scoreObject = getItem(position);
        TextView name = customView.findViewById(R.id.Name);
        TextView score = customView.findViewById(R.id.Score);

        assert scoreObject != null;
        name.setText(scoreObject.getUser());
        score.setText(scoreObject.getScore() + "");

        return customView;
    }
}
