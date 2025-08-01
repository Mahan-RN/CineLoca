import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import model.MediaCollection;
import model.Season;
import model.Series;
import model.SeriesFileReader;

public class TestSeriesFileReader {

    private SeriesFileReader reader;
    private String seriesPath;
    private String imagePath;
    private Series series1;
    private Series series2;
    private MediaCollection collection;

    @Test
    void testDirectoryPathDoesNotExist() {
        try {
            seriesPath = "src//test//resources//movie//FAKEtestDirectory";
            reader = new SeriesFileReader(seriesPath);
            fail("FileNotFoundException was not thrown!");
        } catch (FileNotFoundException e) {
            // expected
        }
    }

    @Test
    void testFilePathInsteadofDirectoryPath() {
        try {
            seriesPath = "src\\test\\resources\\images\\series\\[tt0118475] Spawn.jpg";
            reader = new SeriesFileReader(seriesPath);
            fail("FileNotFoundException was not thrown!");
        } catch (FileNotFoundException e) {
            // expected
        }
    }

    @Nested
    class firstNestedClass {

        @BeforeEach
        void setup() {
            seriesPath = "src//test//resources//series";
            imagePath = "src//test//resources//images//series";
            series1 = new Series("tt0118475", "Spawn");
            series2 = new Series("tt2356777", "True Detective");
            MediaCollection.resetSingleton();
            collection = MediaCollection.getInstance();
        }

        @Test
        void testConstructor() {
            try {
                reader = new SeriesFileReader(seriesPath);
            } catch (FileNotFoundException e) {
                fail("FileNotFoundException should've not beeen thrown!");
            }
            assertTrue(reader.getPathName().equals(seriesPath));
            ArrayList<File> files = reader.getFiles();
            assertEquals(4, files.size());
        }

        @Test
        void testAddPathsToCollectionAllValidSeries() {
            collection.addMedia(series1);
            collection.addMedia(series2);
            try {
                reader = new SeriesFileReader(seriesPath);
                reader.addPathsToCollection(false);
            } catch (FileNotFoundException e) {
                fail("FileNotFoundException should've not been thrown!");
            } catch (IOException e) {
                fail("IOException should've not been thrown!");
            }
            String series1Path = "src\\test\\resources\\series\\[tt0118475] Spawn";
            String series2Path = "src\\test\\resources\\series\\[tt2356777] True Detective";
            assertEquals(series1Path, series1.getFilePath());
            assertEquals(series2Path, series2.getFilePath());
            assertEquals(3, series1.getAvailableSeasons());
            assertEquals(3, series1.getSeaonsList().size());
            Season series1Season1 = series1.getSeaonsList().get(0);
            testSeries1Season1(series1Season1);
            Season series1Season2 = series1.getSeaonsList().get(1);
            testSeries1Season2(series1Season2);
            Season series1Season3 = series1.getSeaonsList().get(2);
            testSeries1Season3(series1Season3);

            assertEquals(1, series2.getAvailableSeasons());
            assertEquals(1, series2.getSeaonsList().size());
            Season series2Season1 = series2.getSeaonsList().get(0);
            testSeries2Season1(series2Season1);
        }

        @Test
        void testAddPathsToCollectionIDNotInCollection() {
            collection.addMedia(series1);
            try {
                reader = new SeriesFileReader(seriesPath);
                reader.addPathsToCollection(false);
            } catch (FileNotFoundException e) {
                fail("FileNotFoundException should've not been thrown!");
            } catch (IOException e) {
                fail("IOException should've not been thrown!");
            }
            String series1Path = "src\\test\\resources\\series\\[tt0118475] Spawn";
            assertEquals(series1Path, series1.getFilePath());
            assertEquals(null, series2.getFilePath());
        }

        @Test
        void testAddPathsToCollectionImageIDNotInCollection() {
            collection.addMedia(series1);
            try {
                reader = new SeriesFileReader(imagePath);
                reader.addPathsToCollection(true);
            } catch (FileNotFoundException e) {
                fail("FileNotFoundException should've not been thrown!");
            } catch (IOException e) {
                fail("IOException should've not been thrown!");
            }
            String series1ImagePath = "src\\test\\resources\\images\\series\\[tt0118475] Spawn.jpg";
            assertEquals(series1ImagePath, series1.getImagePath());
            assertEquals(null, series2.getImagePath());
        }

        @Test
        void testAddMediaPathImages() {
            collection.addMedia(series1);
            collection.addMedia(series2);
            try {
                reader = new SeriesFileReader(imagePath);
                reader.addPathsToCollection(true);
            } catch (FileNotFoundException e) {
                fail("FileNotFoundException should've not been thrown!");
            } catch (IOException e) {
                fail("IOException should've not been thrown!");
            }
            String series1ImagePath = "src\\test\\resources\\images\\series\\[tt0118475] Spawn.jpg";
            String series2ImagePath = "src\\test\\resources\\images\\series\\[tt2356777] True Detective.jpg";
            assertEquals(series1ImagePath, series1.getImagePath());
            assertEquals(series2ImagePath, series2.getImagePath());
        }

        // private helpers
        private void testSeries1Season1(Season season) {
            assertEquals(1, season.getSeasonNumber());
            assertEquals(2, season.getTotalEpisodes());
            assertEquals(2, season.getEpisodes().size());
            String season1Episode1Path = season.getEpisodes().get(0);
            String expected = "src\\test\\resources\\series\\[tt0118475] Spawn\\S01\\[01] Episode1.mp4";
            assertEquals(expected, season1Episode1Path);
            String season1Episode2Path = season.getEpisodes().get(1);
            expected = "src\\test\\resources\\series\\[tt0118475] Spawn\\S01\\[02] Episode2.mp4";
            assertEquals(expected, season1Episode2Path);
        }

        private void testSeries1Season2(Season season) {
            assertEquals(2, season.getSeasonNumber());
            assertEquals(1, season.getTotalEpisodes());
            assertEquals(1, season.getEpisodes().size());
            String season2Episode1Path = season.getEpisodes().get(0);
            String expected = "src\\test\\resources\\series\\[tt0118475] Spawn\\S02\\[01] Episode1.mp4";
            assertEquals(expected, season2Episode1Path);
        }

        private void testSeries1Season3(Season season) {
            assertEquals(3, season.getSeasonNumber());
            assertEquals(0, season.getTotalEpisodes());
            assertEquals(0, season.getEpisodes().size());
            assertTrue(season.getEpisodes().isEmpty());
        }

        private void testSeries2Season1(Season season) {
            assertEquals(1, season.getSeasonNumber());
            assertEquals(2, season.getTotalEpisodes());
            assertEquals(2, season.getEpisodes().size());
            String season2Episode1Path = season.getEpisodes().get(0);
            String expected = "src\\test\\resources\\series\\[tt2356777] True Detective\\S01\\[01] Episode1.mp4";
            assertEquals(expected, season2Episode1Path);
            String season2Episode2Path = season.getEpisodes().get(1);
            expected = "src\\test\\resources\\series\\[tt2356777] True Detective\\S01\\[02] Episode2.mp4";
            assertEquals(expected, season2Episode2Path);
        }

    }

}
