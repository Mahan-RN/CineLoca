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

    // MODIFIES: AbstractMedia
    // EFFECTS: takes the list of strings corresponding to a row of the CSV
    // file and creates a Movie or Series if IMDb ID and title are provided.
    protected Media rowToMedia(List<String> strings) {
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
        }
        Media media = createMedia(imdbID, title);

        setReleaseYear(media, release);
        setDirector(media, director);
        setLength(media, length);
        setCountary(media, countary);
        setActors(media, actors);
        setEnglishSubs(media, englishSub);

        setSpecificFields(media, strings);

        return media;
    }

    // =====================
    // Abstract Methods
    // =====================

    // MODIFIES: AbstractMedia
    // EFFECTS: creats and returns a media with given ID and title
    protected abstract Media createMedia(String imdbID, String title);

    // MODIFIES: AbstractMedia
    // EFFECTS: sets media-specific fields based on CSV data
    protected abstract void setSpecificFields(Media media, List<String> string);

    // MODIFIES: MediaCollection
    // EFFECTS: reads each row of the CSV file and stores it as a Movie/Series
    // in MediaCollection
    protected abstract void loadMediaFromCSV() throws IOException,
            CsvValidationException;

    // =====================
    // Private Helper Methods
    // =====================

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

    // Setters for adding CSV data to media objects
    private void setReleaseYear(Media media, String release) {
        if (ParsingUtilities.isValidNum(release)) {
            int releaseYear = Integer.parseInt(release);
            media.setReleaseYear(releaseYear);
        }
    }

    private void setDirector(Media media, String director) {
        if (director.isBlank()) {
            director = "N/A";
        }
        media.setDirector(director);
    }

    private void setLength(Media media, String length) {
        if (ParsingUtilities.isValidNum(length)) {
            int num = Integer.parseInt(length);
            media.setLengthMinutes(num);
        }
    }

    private void setCountary(Media media, String countary) {
        if (countary.isBlank()) {
            countary = "N/A";
        }
        media.setCountary(countary);
    }

    private void setActors(Media media, String actors) {
        if (!actors.isBlank()) {
            List<String> actorsList = ParsingUtilities.actorsToList(actors);
            for (String actor : actorsList) {
                media.addActor(ParsingUtilities.trimMediaData(actor));
            }
        } else {
            actors = "N/A";
            media.addActor(actors);
        }
    }

    private void setEnglishSubs(Media media, String englishSub) {
        if (englishSub.equals("true") || englishSub.equals("false")) {
            boolean b = Boolean.parseBoolean(englishSub);
            media.setEnglishSubs(b);
        } else {
            media.setEnglishSubs(false);
        }
    }
}
