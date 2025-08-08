package model;

// Represents a Movie
public class Movie extends Media {

    private String director;

    // EFFECTS: creates a movie with the given ID and title
    public Movie(String imdbID, String title) {
        super(imdbID, title);
    }

    // MODIFIES: this
    // EFFECTS: sets the director of this media
    public void setDirector(String director) {
        this.director = director;
    }

    // getters
    public String getDirector() {
        return director;
    }
}
