package test.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.model.Movie;
import main.model.MovieCollection;

import static org.junit.jupiter.api.Assertions.*;

public class TestMovieCollection {

    private MovieCollection testCollection;
    private Movie testMovie1;
    private Movie testMovie2;

    @BeforeEach
    void setup() {
        testCollection = new MovieCollection();
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
        assertTrue(testCollection.getAllMovieIDs().contains("TT1160419"));
        assertTrue(testCollection.getAllMovieIDs().contains("TT15239678"));
        assertEquals(0, testCollection.getDuplicateIDs().size());
    }

    @Test
    void testAddMovieDuplicate() {
        assertTrue(testCollection.addMovie(testMovie1));
        assertTrue(testCollection.addMovie(testMovie2));
        assertFalse(testCollection.addMovie(testMovie1));
        assertFalse(testCollection.addMovie(testMovie2));
        assertEquals(2, testCollection.getAllMovieIDs().size());
        assertTrue(testCollection.getAllMovieIDs().contains("TT1160419"));
        assertTrue(testCollection.getAllMovieIDs().contains("TT15239678"));
        assertEquals(2, testCollection.getDuplicateIDs().size());
        assertEquals("TT1160419", testCollection.getDuplicateIDs().get(0));
        assertEquals("TT15239678", testCollection.getDuplicateIDs().get(1));
    }

}
