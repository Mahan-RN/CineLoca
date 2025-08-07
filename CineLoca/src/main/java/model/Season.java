package model;

import java.util.ArrayList;
import java.util.Comparator;

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

    // EFFECTS: returns list of episodes sorted by increasing episode number
    public ArrayList<String> getSortedEpisodes() {
        episodes.sort(episodeNumberComparator);
        return episodes;
    } // TODO: add tests

    // EFFECTS: comparator to compare episode numbers in increasing order
    public static Comparator<String> episodeNumberComparator = new Comparator<String>() {
        @Override
        public int compare(String e1, String e2) {
            int num1 = ParsingUtilities.fileNameToEpisodeNumber(e1);
            int num2 = ParsingUtilities.fileNameToEpisodeNumber(e2);
            return num1 - num2;
        }
    };
}
