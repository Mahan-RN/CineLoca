package model;

import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVParser;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

// Reads a CSV file of movie metadata and loads them into MovieCollection
public class MovieCSVReader {

    private CSVReader csvReader;
    private File csvFile;
    private String filePathName;
    private FileReader fileReader;
    private ArrayList<String> parsedRow;

    // EFFECTS: creates a CSV reader to read the CSV file at the given file path
    // throws FileNotFoundException if the provided file path doesn't exist
    public MovieCSVReader(String filePathName) throws FileNotFoundException {
        this.filePathName = filePathName;
        this.fileReader = createFileReader(filePathName);
        this.csvReader = createCSVReader(fileReader);
    }

    // MODIFIES: MovieCollection
    // EFFECTS: reads each row of the CSV file and stores it as a Movie in the
    // MovieCollection
    public void loadMoviesFromCSV() {
        // TODO
    }

    // =====================
    // Private Helper Methods
    // =====================

    // MODIFIES: Movie
    // EFFECTS: takes the list of strings corresponding to a row of the CSV
    // file and creates a movie if IMDb ID and title are provided. It sets
    // the remaining Movie attributes if they are provided and valid:
    // - string attributes are valid if they are not empty
    // - boolean attributes are valid if the corresponding string is "true" or
    // "false"
    // - int attributes are valid if the corresponding string is non-empty
    // and contains only numerical values
    private Movie rowToMovie(List<String> strings) {
        Movie movie;
        String imdbID = trimMovieID(strings.get(0));
        String title = trimMovieData(strings.get(1));
        String release = trimMovieData(strings.get(2));
        String director = trimMovieData(strings.get(3));
        String length = trimMovieData(strings.get(4));
        String countary = trimMovieData(strings.get(5));
        String actors = trimMovieData(strings.get(6));
        String englishSub = trimMovieData(strings.get(7));
        if (title.isBlank() || imdbID.isBlank()) {
            return null;
        } else {
            movie = new Movie(imdbID, title);
        }
        if (!release.isBlank() && isValidNum(release)) {
            int releaseYear = Integer.parseInt(release);
            movie.setReleaseYear(releaseYear);
        }
        if (!director.isBlank()) {
            movie.setDirector(director);
        }
        if (!length.isBlank() && isValidNum(length)) {
            int movieLength = Integer.parseInt(length);
        }
        if (!countary.isBlank()) {
            movie.setCountary(countary);
        }
        if (!actors.isBlank()) {
            List<String> actorsList = actorsToList(actors);
            for (String actor : actorsList) {
                movie.addActor(trimMovieData(actor));
            }
        }
        if (englishSub == "true" || englishSub == "false") {
            boolean b = Boolean.parseBoolean(englishSub);
            movie.setEnglishSubs(b);
        }
        return movie;
    }

    // EFFECTS: creats a file reader with the given path.
    // Throws FileNotFoundException if there is no file at the given path
    private FileReader createFileReader(String filePathName) throws FileNotFoundException {
        csvFile = new File(filePathName);
        if (!csvFile.exists()) {
            throw new FileNotFoundException("File not found: "
                    + filePathName);
        } else {
            fileReader = new FileReader(csvFile);
            return fileReader;
        }
    }

    // MODIFIES: CSVReader
    // EFFECTS: creats a CSV reader with the given file reader. The constructed
    // reader:
    // - Ignores the first line of the CSV file, which should correspond to
    // headers
    // - Ignores leading whitespaces
    private CSVReader createCSVReader(FileReader fileReader) {
        CSVParser parser = new CSVParserBuilder()
                .withIgnoreLeadingWhiteSpace(true)
                .build(); // takes a CSVParserBuilder and returns a CSVParser
        csvReader = new CSVReaderBuilder(fileReader)
                .withCSVParser(parser)
                .withSkipLines(1)
                .build(); // takes a CSVReaderBuilder and returns a CSVReader
        return csvReader;
    }

    // EFFECTS: removes leading and trailing whitespaces of the given string
    private String trimMovieData(String s) {
        return s.strip();
    }

    // EFFECTS: removes ALL whitespaces in the given string. Converts the string
    // into all lower case
    private String trimMovieID(String s) {
        return s.replaceAll("\\s+", " ").toLowerCase();
    }

    // EFFECTS: takes a string and splits it by ";" into a list of strings
    private List<String> actorsToList(String actors) {
        String[] tempList = actors.split(";");
        ArrayList<String> actorsList = new ArrayList<>(Arrays.asList(tempList));
        return actorsList;
    }

    // EFFECTS: returns true if the given string contains only numbers
    private boolean isValidNum(String s) {
        Pattern pattern = Pattern.compile("[^0-9]", Pattern.CASE_INSENSITIVE);
        Matcher match = pattern.matcher(s);
        boolean matchFound = match.find();
        return !matchFound;
    }

}
