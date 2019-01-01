package fall2018.csc2017.GameCenter.ActivityControllers;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.Button;

import java.util.ArrayList;

import fall2018.csc2017.GameCenter.GameManagers.BoardManagerSlidingTiles;
import fall2018.csc2017.GameCenter.GameStructure.Board;
import fall2018.csc2017.GameCenter.Tiles.SlidingTile;

/**
 * Controller for the logic in sliding tiles game
 */
public class SlidingTilesController extends ActivityController {

    /**
     * Sliced list of images, if the user has chosen one
     */
    private ArrayList<Drawable> chunks;

    /**
     * Creates a new sliding tiles object
     */
    public SlidingTilesController() {

    }

    /**
     * Return drawable split images, if the user has chosen an image
     */
    public void constructImages(Resources r) {
        if (((BoardManagerSlidingTiles) (this.boardManager)).getUseImage()) {
            chunks = new ArrayList<>(boardManager.getBoard().getNumCols() *
                    boardManager.getBoard().getNumRows());
            for (int i = 0; i < boardManager.getBoard().getNumCols() *
                    boardManager.getBoard().getNumRows(); i++) {

                Drawable d = new BitmapDrawable(r, ((BoardManagerSlidingTiles) (boardManager)).getChunks().get(i).getBitmap());
                chunks.add(d);
            }
        }
    }

    @Override
    public ArrayList<Button> createTileButtons(Context context) {
        Board board = boardManager.getBoard();
        ArrayList<Button> tileButtons = new ArrayList<>();
        for (int row = 0; row != boardManager.getBoard().getNumRows(); row++) {
            for (int col = 0; col != boardManager.getBoard().getNumCols(); col++) {
                Button tmp = new Button(context);
                if (!((BoardManagerSlidingTiles) (boardManager)).getUseImage()) {
                    tmp.setBackgroundResource(board.getTile(row, col).getBackground());
                } else {
                    if (((SlidingTile) board.getTile(row, col)).getId() == boardManager.getBoard().getNumCols() * boardManager.getBoard().getNumRows())
                        tmp.setBackgroundResource(board.getTile(row, col).getBackground());
                    else
                        tmp.setBackground(chunks.get(((SlidingTile) board.getTile(row, col)).getId() - 1));
                }
                tileButtons.add(tmp);
            }
        }
        return tileButtons;
    }

    @Override
    public void updateTileButtons(ArrayList<Button> tileButtons) {
        Board board = boardManager.getBoard();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / boardManager.getBoard().getNumRows();
            int col = nextPos % boardManager.getBoard().getNumCols();
            if (!((BoardManagerSlidingTiles) (boardManager)).getUseImage()) {
                b.setBackgroundResource(board.getTile(row, col).getBackground());
            } else {
                if (((SlidingTile) board.getTile(row, col)).getId() == boardManager.getBoard().getNumCols() *
                        boardManager.getBoard().getNumRows())
                    b.setBackgroundResource(board.getTile(row, col).getBackground());
                else
                    b.setBackground((chunks.get(((SlidingTile) board.getTile(row, col)).getId() - 1)));
            }
            nextPos++;
        }
    }
}
