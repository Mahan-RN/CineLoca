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
import model.Movie;
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
        void testLoadMediaFromCSV() {
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
            Media firstMovie = testCollection.getMediaMap().get("tt1877830");
            Media secondMovie = testCollection.getMediaMap().get("tt1160419");
            Media thirdMovie = testCollection.getMediaMap().get("tt0110413");
            testMovie1(firstMovie);
            testMovie2(secondMovie);
            testMovie3(thirdMovie);

        }

        private void testMovie1(Media m) {
            Movie movie = (Movie) m;
            assertEquals("tt1877830", movie.getImdbID());
            assertEquals("The Batman", movie.getTitle());
            assertEquals(2022, m.getReleaseYear());
            assertEquals("Matt Reeves", movie.getDirector());
            assertEquals(176, movie.getLengthMinutes());
            assertEquals("USA", movie.getCountary());
            List<String> actors = movie.getActors();
            assertEquals(4, actors.size());
            assertEquals("Robert Pattinson", actors.get(0));
            assertEquals("Zoë Kravitz", actors.get(1));
            assertEquals("Paul Dano", actors.get(2));
            assertEquals("Jeffrey Wright", actors.get(3));
            assertTrue(movie.hasEnglishSubtitle());
        }

        private void testMovie2(Media m) {
            Movie movie = (Movie) m;
            assertEquals("tt1160419", movie.getImdbID());
            assertEquals("Dune: Part One", movie.getTitle());
            assertEquals(2021, movie.getReleaseYear());
            assertEquals("Denis Villeneuve", movie.getDirector());
            assertEquals(155, movie.getLengthMinutes());
            assertEquals("USA", movie.getCountary());
            List<String> actors = movie.getActors();
            assertEquals(3, actors.size());
            assertEquals("Timothée Chalamet", actors.get(0));
            assertEquals("Rebecca Ferguson", actors.get(1));
            assertEquals("Zendaya", actors.get(2));
            assertFalse(movie.hasEnglishSubtitle());
        }

        private void testMovie3(Media m) {
            Movie movie = (Movie) m;
            assertEquals("tt0110413", movie.getImdbID());
            assertEquals("Léon: The Professional", movie.getTitle());
            assertEquals(1994, movie.getReleaseYear());
            assertEquals("Luc Besson", movie.getDirector());
            assertEquals(110, movie.getLengthMinutes());
            assertEquals("France", movie.getCountary());
            List<String> actors = movie.getActors();
            assertEquals(3, actors.size());
            assertEquals("Jean Reno", actors.get(0));
            assertEquals("Gary Oldman", actors.get(1));
            assertEquals("Natalie Portman", actors.get(2));
            assertTrue(movie.hasEnglishSubtitle());
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
            Media firstMovie = testCollection.getMediaMap().get("tt1877830");
            Media secondMovie = testCollection.getMediaMap().get("tt1160419");
            Media thirdMovie = testCollection.getMediaMap().get("tt0110413");
            testMovie1(firstMovie);
            testMovie2(secondMovie);
            testMovie3(thirdMovie);

        }

        private void testMovie1(Media m) {
            Movie movie = (Movie) m;
            assertEquals("tt1877830", movie.getImdbID());
            assertEquals("The Batman", movie.getTitle());
            assertEquals(0, movie.getReleaseYear());
            assertEquals("N/A", movie.getDirector());
            assertEquals(0, movie.getLengthMinutes());
            assertEquals("N/A", movie.getCountary());
            List<String> actors = movie.getActors();
            assertEquals(1, actors.size());
            assertEquals("N/A", actors.get(0));
            assertFalse(movie.hasEnglishSubtitle());
        }

        private void testMovie2(Media m) {
            Movie movie = (Movie) m;
            assertEquals("tt1160419", movie.getImdbID());
            assertEquals("Dune: Part One", movie.getTitle());
            assertEquals(0, movie.getReleaseYear());
            assertEquals("Denis Villeneuve", movie.getDirector());
            assertEquals(0, movie.getLengthMinutes());
            assertEquals("USA", movie.getCountary());
            List<String> actors = movie.getActors();
            assertEquals(3, actors.size());
            assertEquals("Timothée Chalamet", actors.get(0));
            assertEquals("Rebecca Ferguson", actors.get(1));
            assertEquals("Zendaya", actors.get(2));
            assertFalse(movie.hasEnglishSubtitle());
        }

        private void testMovie3(Media m) {
            Movie movie = (Movie) m;
            assertEquals("tt0110413", movie.getImdbID());
            assertEquals("Léon: The Professional", movie.getTitle());
            assertEquals(1994, movie.getReleaseYear());
            assertEquals("Luc Besson", movie.getDirector());
            assertEquals(110, movie.getLengthMinutes());
            assertEquals("France", movie.getCountary());
            List<String> actors = movie.getActors();
            assertEquals(3, actors.size());
            assertEquals("Jean Reno", actors.get(0));
            assertEquals("Gary Oldman", actors.get(1));
            assertEquals("Natalie Portman", actors.get(2));
            assertTrue(movie.hasEnglishSubtitle());
        }
    }

}
