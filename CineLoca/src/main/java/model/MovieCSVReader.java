package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.opencsv.exceptions.CsvValidationException;

// Reads a CSV file of movie metadata and loads them into MovieCollection
public class MovieCSVReader extends AbstractCSVReader {

    // EFFECTS: creates a CSV reader to read the CSV file at the given file path
    // throws FileNotFoundException if the provided file path doesn't exist
    public MovieCSVReader(String filePathName) throws FileNotFoundException {
        super(filePathName);
    }

    @Override
    public void loadMediaFromCSV() throws IOException, CsvValidationException {
        String[] row = null;
        while ((row = csvReader.readNext()) != null) {
            parsedRow = new ArrayList<>(Arrays.asList(row));
            Movie movie = rowToMedia(parsedRow);
            if (movie != null) {
                collection.addMedia(movie);
            }
        }
    }

    // =====================
    // Private Helper Methods
    // =====================

    // MODIFIES: Movie
    @Override
    protected Movie rowToMedia(List<String> strings) {
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
}
