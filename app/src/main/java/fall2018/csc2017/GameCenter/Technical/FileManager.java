package fall2018.csc2017.GameCenter.Technical;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


import static android.content.Context.MODE_PRIVATE;

/**
 * Class for loading and reading from a file.
 */
public class FileManager {

    /**
     * Context that determines the save location to be app\data\files
     */
    private Context appContext;

    /**
     * Create a new FileManager to save and load files in the given application context
     *
     * @param context application context to be used
     */
    public FileManager(Context context) {
        appContext = context;
    }

    /**
     * Save the given single object to the provided file
     *
     * @param object   object to be saved
     * @param fileName name of file to save to
     * @param <T>      type parameter of the object being saved
     */
    public <T> void save(T object, String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    appContext.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(object);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Load the object saved in the given file.
     *
     * @param fileName name of file to load from
     * @param <T>      type parameter of object stored in the file
     * @return object stored in the file
     */
    public <T> T load(String fileName) {
        T toReturn = null;
        try {
            InputStream inputStream = appContext.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                toReturn = (T) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File Does Not Exist.");
            e.printStackTrace();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return toReturn;
    }
}
