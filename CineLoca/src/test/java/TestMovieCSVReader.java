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

import model.AbstractMedia;
import model.MediaCollection;
import model.MovieCSVReader;

public class TestMovieCSVReader {
    private MovieCSVReader testReader;
    private String path;

    @Test
    void testCSVFileNotPresent() {
        try {
            path = "src//test//resources//csv//SampleMovieDataCSV9999.csv";
            testReader = new MovieCSVReader(path);
            fail("FileNotFoundException was not thrown!");
        } catch (FileNotFoundException e) {
            // Expected
        }
    }

    // Test class for the SampleMovieData1.csv test file
    @Nested
    class FirstNestedClass {

        @BeforeEach
        void setup() {
            try {
                MediaCollection.resetSingleton();
                path = "src//test//resources//csv//SampleMovieDataCSV1.csv";
                testReader = new MovieCSVReader(path);
            } catch (FileNotFoundException e) {
                fail("FileNotFoundException should not be thrown!");
            }
        }

        @Test
        void testLoadMoviesFromCSV() {
            try {
                testReader.loadMediaFromCSV();
            } catch (IOException e) {
                fail("IOException should not be thrown!");
            } catch (CsvValidationException e) {
                fail("CsvValidationException should not be thrown!");
            }
            MediaCollection testCollection = MediaCollection.getInstance();
            Set<String> allMovieIDs = testCollection.getAllMediaIDs();
            assertEquals(3, allMovieIDs.size());
            assertTrue(allMovieIDs.contains("tt1877830"));
            assertTrue(allMovieIDs.contains("tt1160419"));
            assertTrue(allMovieIDs.contains("tt0110413"));
            AbstractMedia firstMovie = testCollection.getMediaMap().get("tt1877830");
            AbstractMedia secondMovie = testCollection.getMediaMap().get("tt1160419");
            AbstractMedia thirdMovie = testCollection.getMediaMap().get("tt0110413");
            testMovie1(firstMovie);
            testMovie2(secondMovie);
            testMovie3(thirdMovie);

        }

        private void testMovie1(AbstractMedia m) {
            assertEquals("tt1877830", m.getImdbID());
            assertEquals("The Batman", m.getTitle());
            assertEquals(2022, m.getReleaseYear());
            assertEquals("Matt Reeves", m.getDirector());
            assertEquals(176, m.getLengthMinutes());
            assertEquals("USA", m.getCountary());
            List<String> actors = m.getActors();
            assertEquals(4, actors.size());
            assertEquals("Robert Pattinson", actors.get(0));
            assertEquals("Zoë Kravitz", actors.get(1));
            assertEquals("Paul Dano", actors.get(2));
            assertEquals("Jeffrey Wright", actors.get(3));
            assertTrue(m.hasEnglishSubtitle());
        }

        private void testMovie2(AbstractMedia m) {
            assertEquals("tt1160419", m.getImdbID());
            assertEquals("Dune: Part One", m.getTitle());
            assertEquals(2021, m.getReleaseYear());
            assertEquals("Denis Villeneuve", m.getDirector());
            assertEquals(155, m.getLengthMinutes());
            assertEquals("USA", m.getCountary());
            List<String> actors = m.getActors();
            assertEquals(3, actors.size());
            assertEquals("Timothée Chalamet", actors.get(0));
            assertEquals("Rebecca Ferguson", actors.get(1));
            assertEquals("Zendaya", actors.get(2));
            assertFalse(m.hasEnglishSubtitle());
        }

        private void testMovie3(AbstractMedia m) {
            assertEquals("tt0110413", m.getImdbID());
            assertEquals("Léon: The Professional", m.getTitle());
            assertEquals(1994, m.getReleaseYear());
            assertEquals("Luc Besson", m.getDirector());
            assertEquals(110, m.getLengthMinutes());
            assertEquals("France", m.getCountary());
            List<String> actors = m.getActors();
            assertEquals(3, actors.size());
            assertEquals("Jean Reno", actors.get(0));
            assertEquals("Gary Oldman", actors.get(1));
            assertEquals("Natalie Portman", actors.get(2));
            assertTrue(m.hasEnglishSubtitle());
        }

    }

    // Test class for the SampleMovieData2.csv test file
    @Nested
    class SecondNestedClass {

        @BeforeEach
        void setup() {
            try {
                MediaCollection.resetSingleton();
                path = "src//test//resources//csv//SampleMovieDataCSV2.csv";
                testReader = new MovieCSVReader(path);
            } catch (FileNotFoundException e) {
                fail("FileNotFoundException should not be thrown!");
            }
        }

        @Test
        void testLoadMoviesFromCSV() {
            try {
                testReader.loadMediaFromCSV();
            } catch (IOException e) {
                fail("IOException should not be thrown!");
            } catch (CsvValidationException e) {
                fail("CsvValidationException should not be thrown!");
            }
            MediaCollection testCollection = MediaCollection.getInstance();
            Set<String> allMovieIDs = testCollection.getAllMediaIDs();
            assertEquals(3, allMovieIDs.size());
            assertTrue(allMovieIDs.contains("tt1877830"));
            assertTrue(allMovieIDs.contains("tt1160419"));
            assertTrue(allMovieIDs.contains("tt0110413"));
            AbstractMedia firstMovie = testCollection.getMediaMap().get("tt1877830");
            AbstractMedia secondMovie = testCollection.getMediaMap().get("tt1160419");
            AbstractMedia thirdMovie = testCollection.getMediaMap().get("tt0110413");
            testMovie1(firstMovie);
            testMovie2(secondMovie);
            testMovie3(thirdMovie);

        }

        private void testMovie1(AbstractMedia m) {
            assertEquals("tt1877830", m.getImdbID());
            assertEquals("The Batman", m.getTitle());
            assertEquals(0, m.getReleaseYear());
            assertEquals(null, m.getDirector());
            assertEquals(0, m.getLengthMinutes());
            assertEquals(null, m.getCountary());
            List<String> actors = m.getActors();
            assertEquals(0, actors.size());
            assertFalse(m.hasEnglishSubtitle());
        }

        private void testMovie2(AbstractMedia m) {
            assertEquals("tt1160419", m.getImdbID());
            assertEquals("Dune: Part One", m.getTitle());
            assertEquals(0, m.getReleaseYear());
            assertEquals("Denis Villeneuve", m.getDirector());
            assertEquals(0, m.getLengthMinutes());
            assertEquals("USA", m.getCountary());
            List<String> actors = m.getActors();
            assertEquals(3, actors.size());
            assertEquals("Timothée Chalamet", actors.get(0));
            assertEquals("Rebecca Ferguson", actors.get(1));
            assertEquals("Zendaya", actors.get(2));
            assertFalse(m.hasEnglishSubtitle());
        }

        private void testMovie3(AbstractMedia m) {
            assertEquals("tt0110413", m.getImdbID());
            assertEquals("Léon: The Professional", m.getTitle());
            assertEquals(1994, m.getReleaseYear());
            assertEquals("Luc Besson", m.getDirector());
            assertEquals(110, m.getLengthMinutes());
            assertEquals("France", m.getCountary());
            List<String> actors = m.getActors();
            assertEquals(3, actors.size());
            assertEquals("Jean Reno", actors.get(0));
            assertEquals("Gary Oldman", actors.get(1));
            assertEquals("Natalie Portman", actors.get(2));
            assertTrue(m.hasEnglishSubtitle());
        }
    }

}
