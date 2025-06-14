import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import com.opencsv.exceptions.CsvValidationException;

import model.Movie;
import model.MovieCSVReader;
import model.MovieCollection;

import java.util.Set;
import java.util.List;

public class TestMovieCSVReader {
    private MovieCSVReader testReader;
    private String path;

    @Nested
    class FirstNestedClass {
        @BeforeEach
        void setup() {
            try {
                path = "C:\\Users\\mahan\\OneDrive - UBC\\Desktop\\CineLoca\\"
                        + "CineLoca\\CineLoca\\src\\test\\resources\\csv\\"
                        + "SampleMovieDataCSV1.csv";
                testReader = new MovieCSVReader(path);
            } catch (FileNotFoundException e) {
                fail("FileNotFoundException should not be thrown!");
            }
        }

        @Test
        void testLoadMoviesFromCSV() {
            try {
                testReader.loadMoviesFromCSV();
            } catch (IOException e) {
                fail("IOException should not be thrown!");
            } catch (CsvValidationException e) {
                fail("CsvValidationException should not be thrown!");
            }
            MovieCollection testCollection = testReader.getMovieCollection();
            Set<String> allMovieIDs = testCollection.getAllMovieIDs();
            assertEquals(3, allMovieIDs.size());
            assertTrue(allMovieIDs.contains("tt1877830"));
            assertTrue(allMovieIDs.contains("tt1160419"));
            assertTrue(allMovieIDs.contains("tt0110413"));
            Movie firstMovie = testCollection.getMovieMap().get("tt1877830");
            Movie secondMovie = testCollection.getMovieMap().get("tt1160419");
            Movie thirdMovie = testCollection.getMovieMap().get("tt0110413");
            testMovie1(firstMovie);
            testMovie2(secondMovie);
            testMovie3(thirdMovie);

        }

        private void testMovie1(Movie m) {
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

        private void testMovie2(Movie m) {
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

        private void testMovie3 (Movie m) {
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
