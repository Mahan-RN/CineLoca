
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.MediaCollection;
import model.Movie;
import model.Series;

public class TestMediaCollection {

    private MediaCollection testCollection;
    private Movie testMovie1;
    private Movie testMovie2;
    private Series testSeries1;
    private Series testSeries2;
    private Series testSeries3;
    private Series testSeries4;

    @BeforeEach
    void setup() {
        MediaCollection.resetSingleton();
        testCollection = MediaCollection.getInstance();
        testMovie1 = new Movie("tt1160419", "Dune: Part One");
        testMovie2 = new Movie("tt15239678", "Dune: Part Two");
        testSeries1 = new Series("tt0944947", "Game of Thrones");
        testSeries2 = new Series("tt0903747", "Breaking Bad");
        testSeries3 = new Series("tt1305826", "Adventure Time");
        testSeries4 = new Series("tt6517102", "Castlevania");
    }

    @Test
    void testConstructor() {
        assertEquals(0, testCollection.getAllMediaIDs().size());
        assertEquals(0, testCollection.getDuplicateIDs().size());
    }

    @Test
    void testAddMedia() {
        assertTrue(testCollection.addMedia(testMovie1));
        assertTrue(testCollection.addMedia(testMovie2));
        assertTrue(testCollection.addMedia(testSeries1));
        assertEquals(3, testCollection.getAllMediaIDs().size());
        assertTrue(testCollection.getAllMediaIDs().contains("tt1160419"));
        assertTrue(testCollection.getAllMediaIDs().contains("tt15239678"));
        assertTrue(testCollection.getAllMediaIDs().contains("tt0944947"));
        assertEquals(0, testCollection.getDuplicateIDs().size());
    }

    @Test
    void testAddMediaDuplicate() {
        assertTrue(testCollection.addMedia(testMovie1));
        assertTrue(testCollection.addMedia(testMovie2));
        assertFalse(testCollection.addMedia(testMovie1));
        assertFalse(testCollection.addMedia(testMovie2));
        assertEquals(2, testCollection.getAllMediaIDs().size());
        assertTrue(testCollection.getAllMediaIDs().contains("tt1160419"));
        assertTrue(testCollection.getAllMediaIDs().contains("tt15239678"));
        assertEquals(2, testCollection.getDuplicateIDs().size());
        assertEquals("tt1160419", testCollection.getDuplicateIDs().get(0));
        assertEquals("tt15239678", testCollection.getDuplicateIDs().get(1));
    }

    @Test
    void testGetMovies() {
        testCollection.addMedia(testMovie1);
        testCollection.addMedia(testMovie2);
        testCollection.addMedia(testSeries1);
        testCollection.addMedia(testSeries2);
        ArrayList<Movie> output = testCollection.getMovies();
        assertEquals(2, output.size());
        assertTrue(output.contains(testMovie1));
        assertTrue(output.contains(testMovie2));

    }

    @Test
    void testGetSeries() {
        testCollection.addMedia(testMovie1);
        testCollection.addMedia(testMovie2);
        testCollection.addMedia(testSeries1);
        testCollection.addMedia(testSeries2);
        ArrayList<Series> output = testCollection.getSeries();
        assertEquals(2, output.size());
        assertTrue(output.contains(testSeries1));
        assertTrue(output.contains(testSeries2));
    }

    @Test
    void testMoviesSortedByTitleAscending() {
        Movie testMovie3 = new Movie("123", "A");
        Movie testMovie4 = new Movie("1234", "1");
        testCollection.addMedia(testMovie1);
        testCollection.addMedia(testMovie2);
        testCollection.addMedia(testMovie3);
        testCollection.addMedia(testMovie4);
        ArrayList<Movie> expected = new ArrayList<>(Arrays.asList(testMovie4,
                testMovie3,
                testMovie1,
                testMovie2));
        assertEquals(expected, testCollection.moviesSortedByTitleAscending());
    }

    @Test
    void testMovieSortByTitleDescending() {
        Movie testMovie3 = new Movie("123", "A");
        Movie testMovie4 = new Movie("1234", "1");
        testCollection.addMedia(testMovie1);
        testCollection.addMedia(testMovie2);
        testCollection.addMedia(testMovie3);
        testCollection.addMedia(testMovie4);
        ArrayList<Movie> expected = new ArrayList<>(Arrays.asList(testMovie2,
                testMovie1,
                testMovie3,
                testMovie4));
        assertEquals(expected, testCollection.moviesSortedByTitleDescending());
    }

    @Test
    void testMoviesSortedByYearAscending() {
        testMovie1.setReleaseYear(1991);
        testMovie2.setReleaseYear(1992);
        Movie testMovie3 = new Movie("123", "A");
        testMovie3.setReleaseYear(1990);
        Movie testMovie4 = new Movie("1234", "1");
        testMovie4.setReleaseYear(2025);
        testCollection.addMedia(testMovie1);
        testCollection.addMedia(testMovie2);
        testCollection.addMedia(testMovie3);
        testCollection.addMedia(testMovie4);
        ArrayList<Movie> expected = new ArrayList<>(Arrays.asList(testMovie3,
                testMovie1,
                testMovie2,
                testMovie4));
        assertEquals(expected, testCollection.moviesSortedByYearAscending());
    }

    @Test
    void testMoviesSortedByYearDescending() {
        testMovie1.setReleaseYear(1991);
        testMovie2.setReleaseYear(1992);
        Movie testMovie3 = new Movie("123", "A");
        testMovie3.setReleaseYear(1990);
        Movie testMovie4 = new Movie("1234", "1");
        testMovie4.setReleaseYear(2025);
        testCollection.addMedia(testMovie1);
        testCollection.addMedia(testMovie2);
        testCollection.addMedia(testMovie3);
        testCollection.addMedia(testMovie4);
        ArrayList<Movie> expected = new ArrayList<>(Arrays.asList(testMovie4,
                testMovie2,
                testMovie1,
                testMovie3));
        assertEquals(expected, testCollection.moviesSortedByYearDescending());
    }

    @Test
    void testSeriesSortedByTitleAscending() {
        testCollection.addMedia(testSeries1);
        testCollection.addMedia(testSeries2);
        testCollection.addMedia(testSeries3);
        testCollection.addMedia(testSeries4);
        ArrayList<Series> output = testCollection.seriesSortedByTitleAscending();
        ArrayList<Series> expected = new ArrayList<>(Arrays.asList(testSeries3,
                testSeries2,
                testSeries4,
                testSeries1));
        assertEquals(output, expected);
    }

    @Test
    void testSeriesSortedByTitleDescending() {
        testCollection.addMedia(testSeries1);
        testCollection.addMedia(testSeries2);
        testCollection.addMedia(testSeries3);
        testCollection.addMedia(testSeries4);
        ArrayList<Series> output = testCollection.seriesSortedByTitleDescending();
        ArrayList<Series> expected = new ArrayList<>(Arrays.asList(testSeries1,
                testSeries4,
                testSeries2,
                testSeries3));
        assertEquals(output, expected);
    }

    @Test
    void testSeriesSortedByYearAscending() {
        testSeries1.setReleaseYear(2025);
        testSeries2.setReleaseYear(2024);
        testSeries3.setReleaseYear(2023);
        testSeries4.setReleaseYear(2022);
        testCollection.addMedia(testSeries1);
        testCollection.addMedia(testSeries2);
        testCollection.addMedia(testSeries3);
        testCollection.addMedia(testSeries4);
        ArrayList<Series> output = testCollection.seriesSortedByYearAscending();
        ArrayList<Series> expected = new ArrayList<>(Arrays.asList(testSeries4,
                testSeries3,
                testSeries2,
                testSeries1));
        assertEquals(output, expected);
    }

    @Test
    void testSeriesSortedByYearDescending() {
        testSeries1.setReleaseYear(2025);
        testSeries2.setReleaseYear(2024);
        testSeries3.setReleaseYear(2023);
        testSeries4.setReleaseYear(2022);
        testCollection.addMedia(testSeries1);
        testCollection.addMedia(testSeries2);
        testCollection.addMedia(testSeries3);
        testCollection.addMedia(testSeries4);
        ArrayList<Series> output = testCollection.seriesSortedByYearDescending();
        ArrayList<Series> expected = new ArrayList<>(Arrays.asList(testSeries1,
                testSeries2,
                testSeries3,
                testSeries4));
        assertEquals(output, expected);
    }

    @Test
    void testSearchTitleEmptySearch() {
        assertTrue(testCollection.searchTitle("null").isEmpty());
    }

    @Test
    void testSearchTitleSingleHit() {
        testCollection.addMedia(testMovie1);
        testCollection.addMedia(testMovie2);
        ArrayList<Movie> output = testCollection.searchTitle("Dune: Part One");
        assertEquals(1, output.size());
        assertEquals(testMovie1, output.get(0));
    }

    @Test
    void testSearchTitleMultipleHits() {
        testCollection.addMedia(testMovie1);
        testCollection.addMedia(testMovie2);
        ArrayList<Movie> output = testCollection.searchTitle("dune");
        assertEquals(2, output.size());
        assertEquals(testMovie1, output.get(0));
        assertEquals(testMovie2, output.get(1));
    }
}
