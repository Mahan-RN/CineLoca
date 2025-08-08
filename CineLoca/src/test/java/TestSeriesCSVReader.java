import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.opencsv.exceptions.CsvValidationException;

import model.Media;
import model.MediaCollection;
import model.Series;
import model.SeriesCSVReader;

public class TestSeriesCSVReader {
    private SeriesCSVReader testReader;
    private String path;

    @Test
    void testCSVFileNotPresent() {
        try {
            path = "src//test//resources//csv//SampleMovieDataCSV9999.csv";
            testReader = new SeriesCSVReader(path);
            fail("FileNotFoundException was not thrown!");
        } catch (FileNotFoundException e) {
            // expected
        }
    }

    @Nested
    class FirstNestedClass {

        @BeforeEach
        void setup() {
            try {
                MediaCollection.resetSingleton();
                path = "src//test//resources//csv//SampleSeriesData.csv";
                testReader = new SeriesCSVReader(path);
            } catch (FileNotFoundException e) {
                fail("FileNotFoundException should not be thrown!");
            }
        }

        @Test
        void testLoadMediaFromCSV() {
            try {
                testReader.loadMediaFromCSV();
            } catch (IOException e) {
                fail("IOException should not be thrown!");
            } catch (CsvValidationException e) {
                fail("CsvValidationException should not be thrown!");
            }
            MediaCollection testCollection = MediaCollection.getInstance();
            Set<String> allMediaIDs = testCollection.getAllMediaIDs();
            assertEquals(3, allMediaIDs.size());
            assertTrue(allMediaIDs.contains("tt0944947"));
            assertTrue(allMediaIDs.contains("tt0903747"));
            assertTrue(allMediaIDs.contains("tt10919420"));
            Media firstSeries = testCollection.getMediaMap().get("tt0944947");
            Media secondSeries = testCollection.getMediaMap().get("tt0903747");
            Media thirdSeries = testCollection.getMediaMap().get("tt10919420");
            testSeries1(firstSeries);
            testSeries2(secondSeries);
            testSeries3(thirdSeries);
        }

        private void testSeries1(Media media) {
            Series series = (Series) media;
            assertEquals("tt0944947", series.getImdbID());
            assertEquals("Game of Thrones", series.getTitle());
            assertEquals(2011, series.getReleaseYear());
            assertEquals(2019, series.getEndYear());
            assertEquals("David Nutter", series.getCreator());
            // TV shows
            assertEquals(57, series.getLengthMinutes());
            assertEquals("USA", series.getCountary());
            List<String> actors = series.getActors();
            assertEquals(3, actors.size());
            assertEquals("Emilia Clarke", actors.get(0));
            assertEquals("Peter Dinklage", actors.get(1));
            assertEquals("Kit Harington", actors.get(2));
            assertFalse(series.hasEnglishSubtitle());
        }

        private void testSeries2(Media media) {
            Series series = (Series) media;
            assertEquals("tt0903747", series.getImdbID());
            assertEquals("Breaking Bad", series.getTitle());
            assertEquals(2008, series.getReleaseYear());
            assertEquals(2013, series.getEndYear());
            assertEquals("Vince Gilligan", series.getCreator());
            // for TV shows
            assertEquals(49, series.getLengthMinutes());
            assertEquals("USA", series.getCountary());
            List<String> actors = series.getActors();
            assertEquals(3, actors.size());
            assertEquals("Bryan Cranston", actors.get(0));
            assertEquals("Aaron Paul", actors.get(1));
            assertEquals("Anna Gunn", actors.get(2));
            assertTrue(series.hasEnglishSubtitle());
        }

        private void testSeries3(Media media) {
            Series series = (Series) media;
            assertEquals("tt10919420", series.getImdbID());
            assertEquals("Squid Game", series.getTitle());
            assertEquals(0, series.getReleaseYear());
            assertEquals(0, series.getEndYear());
            assertEquals("N/A", series.getCreator());
            assertEquals(0, series.getLengthMinutes());
            assertEquals("N/A", series.getCountary());
            List<String> actors = series.getActors();
            assertEquals(1, actors.size());
            assertEquals("N/A", actors.get(0));
            assertFalse(series.hasEnglishSubtitle());
        }
    }

}
