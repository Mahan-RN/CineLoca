package model;

import java.util.List;
import java.util.Collections;
import java.util.Comparator;

// Represents a Movie
public class Movie extends AbstractMedia {

    // EFFECTS: creates a movie with the given ID and title
    public Movie(String imdbID, String title) {
        super(imdbID, title);
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

    // EFFECTS: comparator to compare movies lexicographically in ascending
    // order
    public static Comparator<Movie> titleComparatorAscending = new Comparator<Movie>() {
        @Override
        public int compare(Movie m1, Movie m2) {
            return m1.getTitle().compareTo(m2.getTitle());
        }
    };

    // EFFECTS: comparator to compare movies lexicographically in descending
    // order
    public static Comparator<Movie> titleComparatorDescending = Collections.reverseOrder(new Comparator<Movie>() {
        @Override
        public int compare(Movie m1, Movie m2) {
            return m1.getTitle().compareTo(m2.getTitle());
        }
    });

    // EFFECTS: comparator to compare movies based on release year in ascending
    // order
    public static Comparator<Movie> yearComparatorAscending = new Comparator<Movie>() {
        @Override
        public int compare(Movie m1, Movie m2) {
            return m1.getReleaseYear() - m2.getReleaseYear();
        }
    };

    // EFFECTS: comparator to compare movies based on release year in descending
    // order
    public static Comparator<Movie> yearComparatorDescending = Collections.reverseOrder(new Comparator<Movie>() {
        @Override
        public int compare(Movie m1, Movie m2) {
            return m1.getReleaseYear() - m2.getReleaseYear();
        }
    });

}
