package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class SeriesFileReader extends AbstractFileReader {

    // EFFECTS: creates a file reader for tv series
    // Throws FileNotFoundException if the provided file path is invalid
    public SeriesFileReader(String pathName) throws FileNotFoundException {
        super(pathName);
    }

    // MODIFIES: this, Series , MediaCollection
    // EFFECTS: adds the path of each file in the directory to the corresponding
    // media object in the collection based on the media id in the file name
    // Throws IOException if getCannoicalPath fails
    public void addPathsToCollection(boolean image) throws IOException {
        if (image) {
            for (File file : this.files) {
                addImagePath(file);
            }
        } else {
            for (File file : this.files) {
                addMediaPath(file);
            }
        }
    }

    // MODIFIES: Series, Season
    // EFFECTS: adds series and episodes paths if ID matches
    @Override
    protected void addMediaPath(File file) {
        if (file.isDirectory()) {
            String id = ParsingUtilities.fileNameToMediaID(file.getName());
            if (null != id) {
                Media media = collection.getMediaMap().get(id);
                if (media != null) {
                    Series series = (Series) media;
                    series.setFilePath(file.getPath());
                    readSeasons(series, file);

                }
            }
        }
    }

    // MODIFIES: Series, Season
    // EFFECTS: reads season directories inside a series parent directory:
    // - Skips any files, as each season should be a directory only
    // - For each directory, creates a Season object and keeps track of the
    // season number based on the seasons created so far
    private void readSeasons(Series series, File file) {
        ArrayList<File> seasons = new ArrayList<>(Arrays.asList(file.listFiles()));
        for (File season : seasons) {
            int num = 1;
            if (!season.isDirectory()) {
                continue;
            } else {
                Season s = new Season(num);
                series.addSeason(s);
                readSeason(s, season);
                num++;
            }
        }
    }

    // MODIFIES: Season
    // EFFECTS: reads episodes within a season directory:
    // - Skips any directories, as episodes should only be files
    // - Adds the path of each episode to the list of episodes
    private void readSeason(Season season, File file) {
        ArrayList<File> episodes = new ArrayList<>(Arrays.asList(file.listFiles()));
        for (File episode : episodes) {
            if (episode.isDirectory()) {
                continue;
            } else {
                String path = episode.getPath();
                season.addEpisode(path);
            }
        }
    }
}
