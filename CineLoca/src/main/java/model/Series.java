package model;

import java.util.ArrayList;

public class Series extends AbstractMedia {

    private int totalSeasonsIMDb;
    private int availableSeasons;
    private String network;
    private ArrayList<Season> seasonsList;

    // EFFECTS: creats a series with the given ID and title
    public Series(String imdbID, String title) {
        super(imdbID, title);
        seasonsList = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds the given season to the list of seasons and increases the
    // number of seasons by 1
    public void addSeason(Season season) {
        seasonsList.add(season);
        availableSeasons++;
    }

    // EFFECTS: returns true if the number of available seasons (i.e., number
    // of seasons in user data) is equal to the total number of seasons in
    // IMDb data
    public boolean isSeasonCountUpToDate() {
        return totalSeasonsIMDb == availableSeasons;
    }

    // setters
    public void setTotalSeasonsIMDb(int num) {
        totalSeasonsIMDb = num;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    // getters
    public int getTotalSeasonsIMDb() {
        return totalSeasonsIMDb;
    }

    public String getNetwork() {
        return network;
    }

    public int getAvailableSeasons() {
        return availableSeasons;
    }

    public ArrayList<Season> getSeaonsList() {
        return seasonsList;
    }
}
