package fall2018.csc2017.GameCenter.Technical;

/**
 * Interface that provides initialization functionality for any class that implements it.
 */
public interface Initializable {

    /**
     * Run methods and assign variables required to set the initial state of activity that
     * implements this interface.
     */
    void init();

    /**
     * Adds all required button listeners to the activity.
     */
    void addButtonListeners();
}
