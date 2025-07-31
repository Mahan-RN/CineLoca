package model;

import java.util.ArrayList;

// Represents one season of a TV show
public class Season {

    private int totalEpisodes;
    private int seasonNumber;
    private ArrayList<String> episodes;

    // EFFECTS: creates a Season with the given season number
    public Season(int seasonNumber) {
        this.seasonNumber = seasonNumber;
        episodes = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds an episode to the list of episodes and increases total
    // episode number by one
    public void addEpisode(String episode) {
        episodes.add(episode);
        totalEpisodes++;
    }

    // getters

    public int getTotalEpisodes() {
        return totalEpisodes;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public ArrayList<String> getEpisodes() {
        return episodes;
    }
}
