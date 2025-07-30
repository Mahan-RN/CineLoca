package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.opencsv.exceptions.CsvValidationException;

// Reads a CSV file of movie metadata and loads them into MediaCollection
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
            Movie movie = (Movie) rowToMedia(parsedRow);
            if (movie != null) {
                collection.addMedia(movie);
            }
        }
    }

    // =====================
    // Private Helper Methods
    // =====================

    @Override
    protected AbstractMedia createMedia(String imdbID, String title) {
        Movie movie = new Movie(imdbID, title);
        return movie;
    }

    @Override
    protected void setSpecificFields(AbstractMedia media, List<String> string) {
        // Movie doesn't have any specific fields
    }
}
