package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

// Represents a file reader to read files from the OS
public class FileReader extends AbstractFileReader {

    // EFFECTS: creates a file reader for Movies
    // Throws FileNotFoundException if the provided file path is invalid
    public FileReader(String pathName) throws FileNotFoundException {
        super(pathName);
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
}
