package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

// Represents a collection of media
public class MediaCollection {

    private static MediaCollection mediaCollection;
    private Map<String, AbstractMedia> mediaMap;
    private List<String> duplicateIDs;

    // EFFECTS: creats a private media collection
    private MediaCollection() {
        mediaMap = new HashMap<>();
        duplicateIDs = new ArrayList<>();
    }

    // EFFECTS: returns the single instance of MediaCollection for singleton
    // pattern
    public static MediaCollection getInstance() {
        if (mediaCollection == null) {
            mediaCollection = new MediaCollection();
        }
        return mediaCollection;
    }

    // MODIFIES: this
    // EFFECTS: adds media to this collection if not already there and returns
    // true. If media is already in the collection, returns false and adds its
    // ID to the list of duplicate IDs
    public boolean addMedia(AbstractMedia media) {
        String mediaID = media.getImdbID();
        if (mediaMap.containsKey(mediaID)) {
            duplicateIDs.add(mediaID);
            return false;
        } else {
            mediaMap.put(mediaID, media);
            return true;
        }
    }

    // EFFECTS: returns the list of all movies in the collection
    public ArrayList<Movie> getMovies() {
        ArrayList<AbstractMedia> media = new ArrayList<>(mediaMap.values());
        ArrayList<Movie> movies = new ArrayList<>();
        for (AbstractMedia m : media) {
            if (m instanceof Movie) {
                movies.add((Movie) m);
            }
        }
        return movies;
    }

    // EFFECTS: returns the list of all series in the collection
    public ArrayList<Series> getSeries() {
        ArrayList<AbstractMedia> media = new ArrayList<>(mediaMap.values());
        ArrayList<Series> series = new ArrayList<>();
        for (AbstractMedia m : media) {
            if (m instanceof Series) {
                series.add((Series) m);
            }
        }
        return series;
    }

    // EFFECTS: returns lexicographically sorted list of movies based on title
    // in ascending order
    public ArrayList<Movie> moviesSortedByTitleAscending() {
        ArrayList<Movie> movies = getMovies();
        movies.sort(AbstractMedia.titleComparatorAscending);
        return movies;
    }

    // EFFECTS: returns lexicographically sorted list of movies based title
    // in descending order
    public ArrayList<Movie> moviesSortedByTitleDescending() {
        ArrayList<Movie> movies = getMovies();
        movies.sort(AbstractMedia.titleComparatorDescending);
        return movies;
    }

    // EFFECTS: returns a sorted list of movies based on release year in
    // ascending order
    public ArrayList<Movie> moviesSortedByYearAscending() {
        ArrayList<Movie> movies = getMovies();
        movies.sort(AbstractMedia.yearComparatorAscending);
        return movies;
    }

    // EFFECTS: returns a sorted list of movies based on release year in
    // descending order
    public ArrayList<Movie> moviesSortedByYearDescending() {
        ArrayList<Movie> movies = getMovies();
        movies.sort(AbstractMedia.yearComparatorDescending);
        return movies;
    }

    // EFFECTS: returns lexicographically sorted list of series based on title
    // in ascending order
    public ArrayList<Series> seriesSortedByTitleAscending() {
        ArrayList<Series> series = getSeries();
        series.sort(AbstractMedia.titleComparatorAscending);
        return series;
    }

    // EFFECTS: returns lexicographically sorted list of series based title
    // in descending order
    public ArrayList<Series> seriesSortedByTitleDescending() {
        ArrayList<Series> series = getSeries();
        series.sort(AbstractMedia.titleComparatorDescending);
        return series;
    }

    // EFFECTS: returns a sorted list of series based on release year in
    // ascending order
    public ArrayList<Series> seriesSortedByYearAscending() {
        ArrayList<Series> series = getSeries();
        series.sort(AbstractMedia.yearComparatorAscending);
        return series;
    }

    // EFFECTS: returns a sorted list of series based on release year in
    // descending order
    public ArrayList<Series> seriesSortedByYearDescending() {
        ArrayList<Series> series = getSeries();
        series.sort(AbstractMedia.yearComparatorDescending);
        return series;
    }

    // EFFECTS: returns the list of movies with titles that contain the search
    // string. Returns an empty list if the search string is blank.
    public ArrayList<Movie> searchTitle(String str) {
        ArrayList<Movie> movies = getMovies();
        ArrayList<Movie> hits = new ArrayList<>();
        if (str.isBlank()) {
            return hits; //TODO: add test
        } else {
            for (Movie m : movies) {
                if (m.getTitle().toLowerCase().contains(str.toLowerCase())) {
                    hits.add(m);
                }
            }
        }
        return hits;
    }

    //TODO: add search for series

    // EFFECTS: returns the set of all movie IDs in the collection
    public Set<String> getAllMediaIDs() {
        return mediaMap.keySet();
    }

    // EFFECTS: returns the list of all dupliate movie IDs
    public List<String> getDuplicateIDs() {
        return duplicateIDs;
    }

    // getter
    public Map<String, AbstractMedia> getMediaMap() {
        return mediaMap;
    }

    // TEST-ONLY method to prevent test pollution
    // created due to singleton pattern
    public static void resetSingleton() {
        mediaCollection = null;
    }

}
