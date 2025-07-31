package model;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import java.io.FileNotFoundException;
import java.io.IOException;

// Represents a file reader to read files from the OS
public class FileReader {
    private String pathName;
    private File directory;
    private ArrayList<File> files;
    private MediaCollection collection = MediaCollection.getInstance();

    // EFFECTS: creates a file reader
    // Throws FileNotFoundException if the provided file path is invalid
    public FileReader(String pathName) throws FileNotFoundException {
        this.pathName = pathName;
        initialize(pathName);
    }

    // MODIFIES: this, AbstractMedia, MediaCollection
    // EFFECTS: adds the path of each file in the directory to the corresponding
    // media object in the collection based on the media id in the file name
    // Throws IOException if getCannoicalPath fails
    public void addPathsToCollection(boolean image) throws IOException {
        String id;
        for (File file : this.files) {
            if (file.isDirectory()) {
                continue;
            } else {
                if (null == ParsingUtilities.fileNameToMediaID(file.getName())) {
                    continue;
                } else {
                    id = ParsingUtilities.fileNameToMediaID(file.getName());
                }
                Media media = collection.getMediaMap().get(id);
                if (media != null) {
                    if (image) {
                        media.setImagePath(file.getPath());
                    } else {
                        media.setFilePath(file.getPath());
                    }
                }
            }
        }
    }

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
