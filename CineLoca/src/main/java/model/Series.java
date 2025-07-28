package model;

public class Series extends AbstractMedia {

    private int totalSeasons;
    private String network;

    // EFFECTS: creats a series with the given ID and title
    public Series(String imdbID, String title) {
        super(imdbID, title);
    }

    // setters
    public void setTotalSeasons(int num) {
        totalSeasons = num;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    // getters
    public int getTotalSeasons() {
        return totalSeasons;
    }

    public String getNetwork() {
        return network;
    }
}
