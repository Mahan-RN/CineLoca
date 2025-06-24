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
    private MovieCollection collection = MovieCollection.getInstance();

    // EFFECTS: creates a file reader
    // Throws FileNotFoundException if the provided file path is invalid
    public FileReader(String pathName) throws FileNotFoundException {
        this.pathName = pathName;
        initialize(pathName);
    }

    // MODIFIES: this, Movie, MovieCollection
    // EFFECTS: adds the path of each file in the directory to the corresponding
    // movie object in the collection based on the movie id in the file name
    // images parameter is used to indicate whether files correspond to movie
    // posters (if set to true) or actual movie files (if set to false)
    // Throws IOException if getCannoicalPath fails
    public void addFilePathsToCollection(boolean images) throws IOException {
        String id;
        for (File file : this.files) {
            if (file.isDirectory()) {
                continue;
            } else {
                if (null == fileNameToMovieID(file.getName())) {
                    continue;
                } else {
                    id = fileNameToMovieID(file.getName());
                }
                Movie movie = collection.getMovieMap().get(id);
                if (movie != null) {
                    if (images) {
                        movie.setImagePath(file.getPath());
                    } else {
                        movie.setFilePath(file.getPath());
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

    // EFFECTS: gets the substring flanked by the first [] in the file name.
    // Example: given "[tt1234]My_Movie", should return "tt1234"
    public String fileNameToMovieID(String fileName) {
        if (!fileName.contains("[") || !fileName.contains("]")) {
            return null;
        } else if (fileName.length() < 4) {
            return null;
        } else if (null == fileName.substring(fileName.indexOf("[") + 1,
                fileName.indexOf("]"))) {
            return null;
        } else {
            String id = fileName.substring(fileName.indexOf("[") + 1,
                    fileName.indexOf("]"));
            return id.toLowerCase();
        }
    }

    // getters

    public String getPathName() {
        return pathName;
    }

    public ArrayList<File> getFiles() {
        return this.files;
    }
}
