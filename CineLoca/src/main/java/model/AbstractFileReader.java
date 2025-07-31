package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class AbstractFileReader {

    protected String pathName;
    protected File directory;
    protected ArrayList<File> files;
    protected MediaCollection collection = MediaCollection.getInstance();

    // EFFECTS: creates a file reader
    // Throws FileNotFoundException if the provided file path is invalid
    public AbstractFileReader(String pathName) throws FileNotFoundException {
        this.pathName = pathName;
        initialize(pathName);
    }

    // MODFIES: Media
    // EFFECTS: adds image path to Media if media ID matches file name
    protected void addImagePath(File file) {
        if (!file.isDirectory()) {
            String id = ParsingUtilities.fileNameToMediaID(file.getName());
            if (null != id) {
                Media media = collection.getMediaMap().get(id);
                if (null != media) {
                    media.setImagePath(file.getPath());
                }
            }
        }
    }

    // =====================
    // Abstract Methods
    // =====================

    // MODIFES: Media
    // EFFECTS: adds file path to media if media ID matches the file name
    protected abstract void addMediaPath(File file);

    // =====================
    // Private Helper Methods
    // =====================

    // MODIFIES: this
    // EFFECTS: creats a directory based on the provided path. Makes a list of
    // all files and folders in the provided directory
    // Throws FileNotFoundException if the provided path does not exist or
    // is a file (rather than a directory)
    private void initialize(String pathName) throws FileNotFoundException {
        this.directory = new File(pathName);
        if (directory.isFile() || !directory.exists()) {
            throw new FileNotFoundException("Provided directory is invalid: "
                    + pathName);
        }
        this.files = new ArrayList<>(Arrays.asList(directory.listFiles()));
    }

    // getters
    public String getPathName() {
        return pathName;
    }

    public ArrayList<File> getFiles() {
        return this.files;
    }
}
