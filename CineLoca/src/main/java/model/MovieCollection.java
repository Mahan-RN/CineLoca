package model;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

// Represents a collection of movies
public class MovieCollection {

    private Map<String, Movie> movieMap;
    private List<String> duplicateIDs;

    // EFFECTS: creats a movie collection
    public MovieCollection() {
        movieMap = new HashMap<>();
        duplicateIDs = new ArrayList<>();
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

    //EFFECTS: returns the set of all movie IDs in the collection
    public Set<String> getAllMovieIDs() {
        return movieMap.keySet();
    }

    // EFFECTS: returns the list of all dupliate movie IDs
    public List<String> getDuplicateIDs() {
        return duplicateIDs;
    }

    //getter
    public Map<String, Movie> getMovieMap() {
        return movieMap;
    }

}
