package model;

import java.util.List;
import java.util.ArrayList;

// Represents a Movie
public class Movie {
    private String imdbID;
    private String title;
    private int releaseYear;
    private String director;
    private int lengthMinutes;
    private String countary;
    private List<String> actors;
    private boolean englishSubtitle;
    private String imagePath;
    private String filePath;

    // EFFECTS: creates a movie object with the given ID, title, path to poster
    // image and path to the movie file
    public Movie(String imdbID, String title) {
        setID(imdbID);
        setTitle(title);
        this.actors = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: sets the IMDB ID of this movie
    // Converts the input to all upper case and then uses replaceAll to remove
    // all whitespaces
    public void setID(String imdbID) {
        this.imdbID = imdbID;
    }

    // MODIFIES: this
    // EFFECTS: sets title of this movie
    // Strips leading and trailing whitespaces
    public void setTitle(String title) {
        this.title = title;
    }

    // MODIFIES: this
    // EFFECTS: sets the release year of this movie
    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    // MODIFIES: this
    // EFFECTS: sets the given director as the director of this movie
    // Strips leading and trailing whitespaces
    public void setDirector(String director) {
        this.director = director;
    }

    // MODIFIES: this
    // EFFECTS: sets the length of this movie (unit: minutes)
    public void setLengthMinutes(int lengthMinutes) {
        this.lengthMinutes = lengthMinutes;
    }

    // MODIFIES: this
    // EFFECTS: sets the countary of this movie
    // Strips leading and trailing whitespaces
    public void setCountary(String countary) {
        this.countary = countary;
    }

    // MODIFIES: this
    // EFFECTS: adds actor to the set of actors for this movie
    // Strips leading and trailing whitespaces
    public boolean addActor(String actor) {
        if (!actors.contains(actor)) {
            return this.actors.add(actor);
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: sets whether this movie has english subtitles available or not
    public void setEnglishSubs(boolean englishSubtitle) {
        this.englishSubtitle = englishSubtitle;
    }

    // MODIFIES: this
    // EFFECTS: sets the relative path to the image of the poster of this movie
    // Strips leading and trailing whitespacs
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    // MODIFIES: this
    // EFFECTS: sets the relative path to video file of this movie
    // Strips leading and trailing whitespacs
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((imdbID == null) ? 0 : imdbID.hashCode());
        return result;
    }

    // EFFECTS: two movies are equal if and only if they have the same
    // IMDB ID
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Movie other = (Movie) obj;
        if (imdbID == null) {
            if (other.imdbID != null)
                return false;
        } else if (!imdbID.equals(other.imdbID))
            return false;
        return true;
    }

    // getters

    public String getImdbID() {
        return imdbID;
    }

    public String getTitle() {
        return title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public String getDirector() {
        return director;
    }

    public int getLengthMinutes() {
        return lengthMinutes;
    }

    public String getCountary() {
        return countary;
    }

    public List<String> getActors() {
        return actors;
    }

    public boolean hasEnglishSubtitle() {
        return englishSubtitle;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getFilePath() {
        return filePath;
    }

}
