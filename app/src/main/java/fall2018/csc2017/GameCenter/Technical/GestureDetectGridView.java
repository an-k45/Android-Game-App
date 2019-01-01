package fall2018.csc2017.GameCenter.Technical;
/*
Adapted from:
https://github.com/DaveNOTDavid/sample-puzzle/blob/master/app/src/main/java/com/davenotdavid/samplepuzzle/GestureDetectGridView.java

This extension of GridView contains built in logic for handling swipes between buttons
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.GridView;

import fall2018.csc2017.GameCenter.GameManagers.BoardManager;
import fall2018.csc2017.GameCenter.GameStructure.MovementController;

/**
 * Class for detecting movement gestures on a grid view.
 */
public class GestureDetectGridView extends GridView {

    /**
     * Minimum distance necessary for a gesture to cover to be considered a swipe.
     */
    public static final int SWIPE_MIN_DISTANCE = 100;

    /**
     * Maximum deviation distance on a swipe.
     */
    public static final int SWIPE_MAX_OFF_PATH = 100;

    /**
     * Minimum movement velocity necessary for a gesture to be recognized as a swipe.
     */
    public static final int SWIPE_THRESHOLD_VELOCITY = 100;

    /**
     * Detector for recognizing the types of gestures on screen.
     */
    private GestureDetector gDetector;

    /**
     * Movement controller for making on screen changes when a gesture is detected.
     */
    private MovementController mController;

    /**
     * Confirmation that a fling event has occurred.
     */
    private boolean mFlingConfirmed = false;

    /**
     * Initial x position of a finger touch on screen.
     */
    private float mTouchX;

    /**
     * Initial y position of a finger touch on screen.
     */
    private float mTouchY;

    /**
     * Create a new GestureDetectGridView with the specified context.
     *
     * @param context app context to operate in
     */
    public GestureDetectGridView(Context context) {
        super(context);
        init(context);
    }

    /**
     * Create a new GestureDetectGridView with the specified context and AttributeSet.
     *
     * @param context app context to operate in
     * @param attrs   attribute set to be used
     */
    public GestureDetectGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * Create a new GestureDetectGridView with the specified context and AttributeSet as well as
     * style attribute.
     *
     * @param context      app context to operate in
     * @param attrs        attribute set to be used
     * @param defStyleAttr style attribute id to be used.
     */
    public GestureDetectGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * Override the inherited gesture methods.
     *
     * @param context app context of the gesture
     */
    private void init(final Context context) {
        mController = new MovementController();
        gDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapConfirmed(MotionEvent event) {
                int position = GestureDetectGridView.this.pointToPosition
                        (Math.round(event.getX()), Math.round(event.getY()));

                mController.processTapMovement(context, position);
                return true;
            }

            @Override
            public boolean onDown(MotionEvent event) {
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                // adapted from https://stackoverflow.com/questions/4139288/android-how-to-handle-right-to-left-swipe-gestures
                float distanceX = e2.getX() - e1.getX();
                float distanceY = e2.getY() - e1.getY();

                if (isHorizontalSwipe(distanceX, distanceY, velocityX)) {
                    horizontalMove(context, distanceX);
                    return true;
                } else if (isVerticalSwipe(distanceX, distanceY, velocityY)) {
                    verticalMove(context, distanceY);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getActionMasked();
        gDetector.onTouchEvent(ev);

        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mFlingConfirmed = false;
        } else if (action == MotionEvent.ACTION_DOWN) {
            mTouchX = ev.getX();
            mTouchY = ev.getY();
        } else {
            if (mFlingConfirmed) {
                return true;
            }

            float dX = (Math.abs(ev.getX() - mTouchX));
            float dY = (Math.abs(ev.getY() - mTouchY));
            if ((dX > SWIPE_MIN_DISTANCE) || (dY > SWIPE_MIN_DISTANCE)) {
                mFlingConfirmed = true;
                return true;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * Perform a horizontal move (left or right) in the given context based on horizontal distance
     * travelled by gesture.
     *
     * @param context   gesture context
     * @param distanceX horizontal distance traversed by gesture
     */
    private void horizontalMove(Context context, float distanceX) {
        if (distanceX > 0)
            mController.processSwipeMovement(context, MovementController.RIGHT);
        else {
            mController.processSwipeMovement(context, MovementController.LEFT);
        }
    }

    /**
     * Perform a vertical move (left or right) in the given context based on horizontal distance
     * travelled by gesture.
     *
     * @param context   gesture context
     * @param distanceY vertical distance traversed by gesture
     */
    private void verticalMove(Context context, float distanceY) {
        if (distanceY > 0) {
            mController.processSwipeMovement(context, MovementController.DOWN);
        } else {
            mController.processSwipeMovement(context, MovementController.UP);
        }
    }

    /**
     * Return whether the performed gesture is a horizontal swipe.
     *
     * @param xDist    x distance traversed by gesture
     * @param yDist    y distance traversed by gesture
     * @param velocity gesture velocity
     * @return true if the gesture with the given parameters is a horizontal swipe, false otherwise.
     */
    private boolean isHorizontalSwipe(float xDist, float yDist, float velocity) {
        return isGreaterDistanceX(xDist, yDist) && isOverMaxSwipe(xDist) && isOverMaxVelocity(velocity);
    }

    /**
     * Return whether the performed gesture is a vertical swipe.
     *
     * @param xDist    x distance traversed by gesture
     * @param yDist    y distance traversed by gesture
     * @param velocity gesture velocity
     * @return true if the gesture with the given parameters is a vertical swipe, false otherwise.
     */
    private boolean isVerticalSwipe(float xDist, float yDist, float velocity) {
        return !isGreaterDistanceX(xDist, yDist) && isOverMaxSwipe(yDist) && isOverMaxVelocity(velocity);
    }

    /**
     * Return whether absolute value of the horizontal distance travelled during a swipe is greater
     * than the absolute value of the corresponding vertical distance.
     *
     * @param hor  horizontal distance travelled during swipe
     * @param vert vertical distance travelled during swipe
     * @return true if a horizontal swipe occurred, false if a vertical swipe occurred.
     */
    private boolean isGreaterDistanceX(float hor, float vert) {
        return Math.abs(hor) > Math.abs(vert);
    }

    /**
     * Return whether the absolute value of the swipe distance is greater than SWIPE_MAX_OFF_PATH.
     *
     * @param distance distance travelled during swipe
     * @return true if Math.abs(distance) > SWIPE_MAX_OFF_PATH, false otherwise.
     */
    private boolean isOverMaxSwipe(float distance) {
        return Math.abs(distance) > SWIPE_MAX_OFF_PATH;
    }

    /**
     * Return whether the passed velocity (x or y) of latest swipe is greater than the necessary
     *
     * @param velocity horizontal or vertical velocity of swipe
     * @return true if Math.abs(velocity) > SWIPE_THRESHOLD_VELOCITY, false otherwise.
     */
    private boolean isOverMaxVelocity(float velocity) {
        return Math.abs(velocity) > SWIPE_THRESHOLD_VELOCITY;
    }

    /**
     * Set the board manager of this GestureDetectGridView to the specified board manager.
     *
     * @param boardManager new board manager reference to be used
     */
    public void setBoardManager(BoardManager boardManager) {
        mController.setBoardManager(boardManager);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return gDetector.onTouchEvent(ev);
    }
}
