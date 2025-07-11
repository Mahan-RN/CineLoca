
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Movie;
import model.MovieCollection;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

public class TestMovieCollection {

    private MovieCollection testCollection;
    private Movie testMovie1;
    private Movie testMovie2;

    @BeforeEach
    void setup() {
        MovieCollection.resetSingleton();
        testCollection = MovieCollection.getInstance();
        testMovie1 = new Movie("tt1160419", "Dune: Part One");
        testMovie2 = new Movie("tt15239678", "Dune: Part Two");
    }

    @Test
    void testConstructor() {
        assertEquals(0, testCollection.getAllMovieIDs().size());
        assertEquals(0, testCollection.getDuplicateIDs().size());
    }

    @Test
    void testAddMovie() {
        assertTrue(testCollection.addMovie(testMovie1));
        assertTrue(testCollection.addMovie(testMovie2));
        assertEquals(2, testCollection.getAllMovieIDs().size());
        assertTrue(testCollection.getAllMovieIDs().contains("tt1160419"));
        assertTrue(testCollection.getAllMovieIDs().contains("tt15239678"));
        assertEquals(0, testCollection.getDuplicateIDs().size());
    }

    @Test
    void testAddMovieDuplicate() {
        assertTrue(testCollection.addMovie(testMovie1));
        assertTrue(testCollection.addMovie(testMovie2));
        assertFalse(testCollection.addMovie(testMovie1));
        assertFalse(testCollection.addMovie(testMovie2));
        assertEquals(2, testCollection.getAllMovieIDs().size());
        assertTrue(testCollection.getAllMovieIDs().contains("tt1160419"));
        assertTrue(testCollection.getAllMovieIDs().contains("tt15239678"));
        assertEquals(2, testCollection.getDuplicateIDs().size());
        assertEquals("tt1160419", testCollection.getDuplicateIDs().get(0));
        assertEquals("tt15239678", testCollection.getDuplicateIDs().get(1));
    }

    @Test
    void testMoviesSortedByTitle() {
        Movie testMovie3 = new Movie("123", "A");
        Movie testMovie4 = new Movie("1234", "1");
        testCollection.addMovie(testMovie1);
        testCollection.addMovie(testMovie2);
        testCollection.addMovie(testMovie3);
        testCollection.addMovie(testMovie4);
        ArrayList<Movie> expected = new ArrayList<>(Arrays.asList(testMovie4,
                testMovie3,
                testMovie1,
                testMovie2));
        assertEquals(expected, testCollection.moviesSortedByTitle());
    }

    @Test
    void testMoviesSortedByYear() {
        testMovie1.setReleaseYear(1991);
        testMovie2.setReleaseYear(1992);
        Movie testMovie3 = new Movie("123", "A");
        testMovie3.setReleaseYear(1990);
        Movie testMovie4 = new Movie("1234", "1");
        testMovie4.setReleaseYear(2025);
        testCollection.addMovie(testMovie1);
        testCollection.addMovie(testMovie2);
        testCollection.addMovie(testMovie3);
        testCollection.addMovie(testMovie4);
        ArrayList<Movie> expected = new ArrayList<>(Arrays.asList(testMovie3,
                testMovie1,
                testMovie2,
                testMovie4));
        assertEquals(expected, testCollection.moviesSortedByYear());
    }

}
