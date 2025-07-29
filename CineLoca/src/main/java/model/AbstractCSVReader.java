package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

public abstract class AbstractCSVReader {

    protected CSVReader csvReader;
    protected File csvFile;
    protected InputStreamReader fileReader;
    protected ArrayList<String> parsedRow;
    protected MediaCollection collection = MediaCollection.getInstance();

    public AbstractCSVReader(String filePathName) throws FileNotFoundException {
        this.fileReader = createFileReader(filePathName);
        this.csvReader = createCSVReader(fileReader);
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

    // =====================
    // Abstract Methods
    // =====================

    // MODIFIES: AbstractMedia
    // EFFECTS: takes the list of strings corresponding to a row of the CSV
    // file and creates a Movie or Series if IMDb ID and title are provided. It sets
    // the remaining media attributes if they are provided and valid:
    // - string attributes are valid if they are not empty
    // - boolean attributes are valid if the corresponding string is "true" or
    // "false"
    // - int attributes are valid if the corresponding string is non-empty
    // and contains only numerical values
    protected abstract AbstractMedia rowToMedia(List<String> strings);

    // MODIFIES: MediaCollection
    // EFFECTS: reads each row of the CSV file and stores it as a Movie/Series
    // in MediaCollection
    protected abstract void loadMediaFromCSV() throws IOException,
            CsvValidationException;

    // =====================
    // Private Helper Methods
    // =====================

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
