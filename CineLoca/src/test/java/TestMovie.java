import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Movie;

public class TestMovie {
    private Movie testMovie;

    @BeforeEach

    void runBefore() {
        testMovie = new Movie("tt1392190", "Mad Max: Fury Road");
    }

    @Test
    void testConstructor() {
        assertEquals("tt1392190", testMovie.getImdbID());
        assertEquals("Mad Max: Fury Road", testMovie.getTitle());
        assertEquals(0, testMovie.getLengthMinutes());
        assertEquals(null, testMovie.getDirector());
        assertEquals(null, testMovie.getCountary());
        assertEquals(0, testMovie.getActors().size());
        assertFalse(testMovie.hasEnglishSubtitle());
        assertEquals(null, testMovie.getImagePath());
        assertEquals(null, testMovie.getFilePath());
    }

    @Test
    void testSetID() {
        testMovie.setID("tt0372784");
        assertEquals("tt0372784", testMovie.getImdbID());
    }

    @Test
    void testSetTitle() {
        testMovie.setTitle("Batman");
        assertEquals("Batman", testMovie.getTitle());
    }

    @Test
    void testSetReleaseYear() {
        testMovie.setReleaseYear(1998);
        assertTrue(1998 == testMovie.getReleaseYear());
    }

    @Test
    void testSetDirector() {
        testMovie.setDirector("Christopher Nolan");
        assertEquals("Christopher Nolan", testMovie.getDirector());
    }

    @Test
    void testSetLengthMinutes() {
        testMovie.setLengthMinutes(153);
        assertTrue(153 == testMovie.getLengthMinutes());
    }

    @Test
    void testSetCountary() {
        testMovie.setCountary("USA");
        assertEquals("USA", testMovie.getCountary());
    }

    @Test
    void testAddActors() {
        assertTrue(testMovie.addActor("Michael Caine"));
        assertTrue(testMovie.addActor("Christopher Nolan"));
        assertTrue(2 == testMovie.getActors().size());
        assertEquals("Michael Caine", testMovie.getActors().get(0));
        assertEquals("Christopher Nolan", testMovie.getActors().get(1));
    }

    @Test
    void testAddActorsDuplicateActor() {
        assertTrue(testMovie.addActor("Michael Caine"));
        assertTrue(testMovie.addActor("Christopher Nolan"));
        assertFalse(testMovie.addActor("Michael Caine"));
        assertTrue(2 == testMovie.getActors().size());
        assertEquals("Michael Caine", testMovie.getActors().get(0));
        assertEquals("Christopher Nolan", testMovie.getActors().get(1));
    }

    @Test
    void testSetEnglishSubs() {
        testMovie.setEnglishSubs(true);
        assertTrue(testMovie.hasEnglishSubtitle());
        testMovie.setEnglishSubs(false);
        assertFalse(testMovie.hasEnglishSubtitle());
    }

    @Test
    void testSetImagePath() {
        String path = "CineLoca/00testData/images/testMoviePoster.jpg";
        testMovie.setImagePath(path);
        assertEquals(path, testMovie.getImagePath());
    }

    @Test
    void testSetFilePath() {
        String path = "CineLoca//00testData//movie//testMovie.mp4";
        testMovie.setFilePath(path);
        assertEquals(path, testMovie.getFilePath());
    }

    @Test
    void testActorsToStringEmpty() {
        String output = testMovie.actorsToString();
        assertEquals("", output);
    }

    @Test
    void testActorsToStringSingle() {
        testMovie.addActor("Sam");
        String output = testMovie.actorsToString();
        assertEquals("Sam", output);
    }

    @Test
    void testActorsToStringMultiple() {
        testMovie.addActor("Sam");
        testMovie.addActor("Lara");
        testMovie.addActor("Mahan");
        testMovie.addActor("Jackie");
        String output = testMovie.actorsToString();
        assertEquals("Sam, Lara, Mahan, Jackie", output);
    }

    @Test
    void testEquals() {
        Movie movie1 = new Movie("tt0372784", "Batman");
        Movie movie2 = new Movie("tt0372784", "Dune");
        assertTrue(movie1.equals(movie2));
    }

    @Test
    void testTitleComparatorAscending() {
        Movie movie0 = new Movie("123", "A");
        Movie movie1 = new Movie("1234", "B");
        Movie movie2 = new Movie("12345", "Z");
        Movie movie3 = new Movie("123456", "11");
        ArrayList<Movie> movies = new ArrayList<>(
                Arrays.asList(movie2, movie0, movie3, movie1));
        ArrayList<Movie> expected = new ArrayList<>(
                Arrays.asList(movie3, movie0, movie1, movie2));
        movies.sort(Movie.titleComparatorAscending);
        assertEquals(expected, movies);
    }

    @Test
    void testTitleComparatorDescending() {
        Movie movie0 = new Movie("123", "A");
        Movie movie1 = new Movie("1234", "B");
        Movie movie2 = new Movie("12345", "Z");
        Movie movie3 = new Movie("123456", "11");
        ArrayList<Movie> movies = new ArrayList<>(
                Arrays.asList(movie2, movie0, movie3, movie1));
        ArrayList<Movie> expected = new ArrayList<>(
                Arrays.asList(movie2, movie1, movie0, movie3));
        movies.sort(Collections.reverseOrder(Movie.titleComparatorAscending));
        assertEquals(expected, movies);
    }

    @Test
    void testYearComparatorAscending() {
        Movie movie0 = new Movie("123", "A");
        movie0.setReleaseYear(1960);
        Movie movie1 = new Movie("1234", "B");
        movie1.setReleaseYear(1960);
        Movie movie2 = new Movie("12345", "Z");
        movie2.setReleaseYear(1990);
        Movie movie3 = new Movie("123456", "11");
        movie3.setReleaseYear(2025);
        ArrayList<Movie> movies = new ArrayList<>(
                Arrays.asList(movie2, movie3, movie1, movie0));
        ArrayList<Movie> expected = new ArrayList<>(
                Arrays.asList(movie1, movie0, movie2, movie3));
        movies.sort(Movie.yearComparatorAscending);
        assertEquals(expected, movies);
    }

    @Test
    void testYearComparatorDescending() {
        Movie movie0 = new Movie("123", "A");
        movie0.setReleaseYear(1959);
        Movie movie1 = new Movie("1234", "B");
        movie1.setReleaseYear(1960);
        Movie movie2 = new Movie("12345", "Z");
        movie2.setReleaseYear(1961);
        Movie movie3 = new Movie("123456", "11");
        movie3.setReleaseYear(2025);
        ArrayList<Movie> movies = new ArrayList<>(
                Arrays.asList(movie2, movie3, movie1, movie0));
        ArrayList<Movie> expected = new ArrayList<>(
                Arrays.asList(movie3, movie2, movie1, movie0));
        movies.sort(Movie.yearComparatorDescending);
        assertEquals(expected, movies);
    }

}
