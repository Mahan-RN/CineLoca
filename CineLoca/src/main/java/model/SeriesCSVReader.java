package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.opencsv.exceptions.CsvValidationException;

// Reads a CSV file of TV show metadata and loads them into MediaCollection
public class SeriesCSVReader extends AbstractCSVReader {

    // EFFECTS: creates a CSV reader to read and parse the provided CSV file
    // throws FileNotFoundException if the provided file path doesn't exist
    public SeriesCSVReader(String filePathName) throws FileNotFoundException {
        super(filePathName);
    }

    @Override
    public void loadMediaFromCSV() throws IOException, CsvValidationException {
        String[] row = null;
        while ((row = csvReader.readNext()) != null) {
            parsedRow = new ArrayList<>(Arrays.asList(row));
            Series series = (Series) rowToMedia(parsedRow);
            if (series != null) {
                collection.addMedia(series);
            }
        }
    }

    // =====================
    // Helper Methods
    // =====================

    @Override
    protected Media createMedia(String imdbID, String title) {
        Series series = new Series(imdbID, title);
        return series;
    }

    @Override
    protected void setSpecificFields(Media media, List<String> strings) {
        Series series = (Series) media;
        String totalSeasons = ParsingUtilities.trimMediaData(strings.get(8));
        String network = ParsingUtilities.trimMediaData(strings.get(9));
        if (ParsingUtilities.isValidNum(totalSeasons)) {
            int num = Integer.parseInt(totalSeasons);
            series.setTotalSeasonsIMDb(num);
        }
        if (network.isBlank()) {
            network = "N/A";
        }
        series.setNetwork(network);
    }
}
