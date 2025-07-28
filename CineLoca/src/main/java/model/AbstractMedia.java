package model;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMedia {
    protected String imdbID;
    protected String title;
    protected int releaseYear;
    protected String director;
    protected int lengthMinutes;
    protected String countary;
    protected List<String> actors;
    protected boolean englishSubtitle;
    protected String imagePath;
    protected String filePath;

    // EFFECTS: creates an abstract media with the given ID and title
    protected AbstractMedia(String imdbID, String title) {
        this.imdbID = imdbID;
        this.title = title;
        this.actors = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: sets the IMDB ID of this media
    public void setID(String imdbID) {
        this.imdbID = imdbID;
    }

    // MODIFIES: this
    // EFFECTS: sets title of this media
    public void setTitle(String title) {
        this.title = title;
    }

    // MODIFIES: this
    // EFFECTS: sets the release year of this media
    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    // MODIFIES: this
    // EFFECTS: sets the director of this media
    public void setDirector(String director) {
        this.director = director;
    }

    // MODIFIES: this
    // EFFECTS: sets the length of this media (unit: minutes)
    public void setLengthMinutes(int lengthMinutes) {
        this.lengthMinutes = lengthMinutes;
    }

    // MODIFIES: this
    // EFFECTS: sets the countary of this media
    public void setCountary(String countary) {
        this.countary = countary;
    }

    // MODIFIES: this
    // EFFECTS: adds actor to the set of actors for this media if not already
    // added
    public boolean addActor(String actor) {
        if (!actors.contains(actor)) {
            return this.actors.add(actor);
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: sets whether this media has english subtitles available or not
    public void setEnglishSubs(boolean englishSubtitle) {
        this.englishSubtitle = englishSubtitle;
    }

    // MODIFIES: this
    // EFFECTS: sets the relative path to the poster image of this media
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    // MODIFIES: this
    // EFFECTS: sets the relative path to video file of this media
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    // EFFECTS: returns comma-separated string representation of actors in
    // the movie
    public String actorsToString() {
        String str = "";
        if (!actors.isEmpty()) {
            int len = actors.size();
            if (len == 1) {
                str = actors.get(0);
            } else {
                for (int i = 0; i < len - 1; i++) {
                    str += actors.get(i) + ", ";
                }
                str += actors.get(len - 1);
            }
        }
        return str;
    }

}
