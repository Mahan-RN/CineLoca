package model;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

// Represents a collection of movies
public class MovieCollection {

    private static MovieCollection movieCollection;
    private Map<String, Movie> movieMap;
    private List<String> duplicateIDs;

    // EFFECTS: creats a private movie collection
    private MovieCollection() {
        movieMap = new HashMap<>();
        duplicateIDs = new ArrayList<>();
    }

    // EFFECTS: returns the single instance of MovieCollection for singleton
    // pattern
    public static MovieCollection getInstance() {
        if (movieCollection == null) {
            movieCollection = new MovieCollection();
        }
        return movieCollection;
    }

    // MODIFIES: this
    // EFFECTS: adds movie to this collection if not already there and returns
    // true. If movie is already in the collection, returns false and adds its
    // ID to the list of duplicate IDs
    public boolean addMovie(Movie movie) {
        String movieID = movie.getImdbID();
        if (movieMap.containsKey(movieID)) {
            duplicateIDs.add(movieID);
            return false;
        } else {
            movieMap.put(movieID, movie);
            return true;
        }
    }

    // EFFECTS: returns lexicographically sorted list of movies based title in
    // ascending order
    public ArrayList<Movie> moviesSortedByTitleAscending() {
        ArrayList<Movie> movies = new ArrayList<>(movieMap.values());
        movies.sort(Movie.titleComparatorAscending);
        return movies;
    }

    // EFFECTS: returns lexicographically sorted list of movies based title
    // in descending order
    public ArrayList<Movie> moviesSortedByTitleDescending() {
        ArrayList<Movie> movies = new ArrayList<>(movieMap.values());
        movies.sort(Movie.titleComparatorDescending);
        return movies;
    }

    // EFFECTS: returns a sorted list of movies based on release year in
    // ascending order
    public ArrayList<Movie> moviesSortedByYearAscending() {
        ArrayList<Movie> movies = new ArrayList<>(movieMap.values());
        movies.sort(Movie.yearComparatorAscending);
        return movies;
    }

    // EFFECTS: returns a sorted list of movies based on release year in
    // descending order
    public ArrayList<Movie> moviesSortedByYearDescending() {
        ArrayList<Movie> movies = new ArrayList<>(movieMap.values());
        movies.sort(Movie.yearComparatorAscending);
        return movies;
    }

    // EFFECTS: returns the set of all movie IDs in the collection
    public Set<String> getAllMovieIDs() {
        return movieMap.keySet();
    }

    // EFFECTS: returns the list of all dupliate movie IDs
    public List<String> getDuplicateIDs() {
        return duplicateIDs;
    }

    // getter
    public Map<String, Movie> getMovieMap() {
        return movieMap;
    }

    // TEST-ONLY method to prevent test pollution
    // created due to singleton pattern
    public static void resetSingleton() {
        movieCollection = null;
    }

}
