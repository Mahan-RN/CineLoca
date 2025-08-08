package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

// Represents a file reader to read files from the OS
public class MovieFileReader extends AbstractFileReader {

    // EFFECTS: creates a file reader for movies
    // Throws FileNotFoundException if the provided file path is invalid
    public MovieFileReader(String pathName) throws FileNotFoundException {
        super(pathName);
    }

    // MODIFIES: this, Movie, MediaCollection
    // EFFECTS: adds the path of each file in the directory to the corresponding
    // media object in the collection based on the media id in the file name
    // Throws IOException if getCannoicalPath fails
    public void addPathsToCollection(boolean image) throws IOException {
        if (image) {
            for (File file : this.files) {
                addImagePath(file);
            }
        } else {
            for (File file : this.files) {
                addMediaPath(file);
            }
        }
    }

    @Override
    protected void addMediaPath(File file) {
        if (!file.isDirectory()) {
            String id = ParsingUtilities.fileNameToMediaID(file.getName());
            if (null != id) {
                Media media = collection.getMediaMap().get(id);
                if (media != null) {
                    media.setFilePath(file.getPath());
                }
            }
        }
    }
}
