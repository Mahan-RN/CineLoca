package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVParser;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

// Reads a CSV file of movie metadata and loads them into MovieCollection
public class MovieCSVReader {

    private CSVReader csvReader;
    private File csvFile;
    private InputStreamReader fileReader;
    private ArrayList<String> parsedRow;
    private MediaCollection collection;

    // EFFECTS: creates a CSV reader to read the CSV file at the given file path
    // throws FileNotFoundException if the provided file path doesn't exist
    public MovieCSVReader(String filePathName) throws FileNotFoundException {
        this.fileReader = createFileReader(filePathName);
        this.csvReader = createCSVReader(fileReader);
        collection = MediaCollection.getInstance();
    }

    // MODIFIES: MovieCollection
    // EFFECTS: reads each row of the CSV file and stores it as a Movie in the
    // MovieCollection
    public void loadMoviesFromCSV() throws IOException, CsvValidationException {
        String[] row = null;
        while ((row = csvReader.readNext()) != null) {
            parsedRow = new ArrayList<>(Arrays.asList(row));
            Movie movie = rowToMovie(parsedRow);
            if (movie != null) {
                collection.addMedia(movie);
            }
        }
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
        String imdbID = ParsingUtilities.trimMediaID(strings.get(0));
        String title = ParsingUtilities.trimMediaData(strings.get(1));
        String release = ParsingUtilities.trimMediaData(strings.get(2));
        String director = ParsingUtilities.trimMediaData(strings.get(3));
        String length = ParsingUtilities.trimMediaData(strings.get(4));
        String countary = ParsingUtilities.trimMediaData(strings.get(5));
        String actors = ParsingUtilities.trimMediaData(strings.get(6));
        String englishSub = ParsingUtilities
                .trimMediaData(strings.get(7)).toLowerCase();

        if (title.isBlank() || imdbID.isBlank()) {
            return null;
        } else {
            movie = new Movie(imdbID, title);
        }
        if (!release.isBlank() && ParsingUtilities.isValidNum(release)) {
            int releaseYear = Integer.parseInt(release);
            movie.setReleaseYear(releaseYear);
        }
        if (!director.isBlank()) {
            movie.setDirector(director);
        }
        if (!length.isBlank() && ParsingUtilities.isValidNum(length)) {
            int movieLength = Integer.parseInt(length);
            movie.setLengthMinutes(movieLength);
        }
        if (!countary.isBlank()) {
            movie.setCountary(countary);
        }
        if (!actors.isBlank()) {
            List<String> actorsList = ParsingUtilities.actorsToList(actors);
            for (String actor : actorsList) {
                movie.addActor(ParsingUtilities.trimMediaData(actor));
            }
        }
        if (englishSub.equals("true") || englishSub.equals("false")) {
            boolean b = Boolean.parseBoolean(englishSub);
            movie.setEnglishSubs(b);
        }
        return movie;
    }

    // getter
    public MediaCollection getMovieCollection() {
        return this.collection;
    }

    // EFFECTS: creats a file reader with the given path.
    // Throws FileNotFoundException if there is no file at the given path
    private InputStreamReader createFileReader(String filePathName)
            throws FileNotFoundException {
        csvFile = new File(filePathName);
        if (!csvFile.exists()) {
            throw new FileNotFoundException("File not found: "
                    + filePathName);
        } else {
            InputStreamReader reader = new InputStreamReader(
                    new FileInputStream(csvFile), StandardCharsets.UTF_8);
            return reader;
        }
    }

    // MODIFIES: CSVReader
    // EFFECTS: creats a CSV reader with the given file reader. The constructed
    // reader:
    // - Ignores the first line of the CSV file, which should correspond to
    // headers
    // - Ignores leading whitespaces
    private CSVReader createCSVReader(InputStreamReader fileReader) {
        CSVParser parser = new CSVParserBuilder()
                .withIgnoreLeadingWhiteSpace(true)
                .build(); // takes a CSVParserBuilder and returns a CSVParser
        csvReader = new CSVReaderBuilder(fileReader)
                .withCSVParser(parser)
                .withSkipLines(1)
                .build(); // takes a CSVReaderBuilder and returns a CSVReader
        return csvReader;
    }
}
