package model;

// Represents a Movie
public class Movie extends AbstractMedia {

    // EFFECTS: creates a movie with the given ID and title
    public Movie(String imdbID, String title) {
        super(imdbID, title);
    }
}
