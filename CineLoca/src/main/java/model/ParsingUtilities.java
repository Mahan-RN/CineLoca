package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ParsingUtilities {

    private ParsingUtilities() {
    }

    // EFFECTS: removes leading and trailing whitespaces of the given string
    public static String trimMovieData(String s) {
        return s.strip();
    }

    // EFFECTS: removes ALL whitespaces in the given string. Converts the string
    // into all lower case
    public static String trimMovieID(String s) {
        return s.replaceAll("\\s+", " ").toLowerCase();
    }

    // EFFECTS: takes a string and splits it by ";" into a list of strings
    public static List<String> actorsToList(String actors) {
        String[] tempList = actors.split(";");
        ArrayList<String> actorsList = new ArrayList<>(Arrays.asList(tempList));
        return actorsList;
    }

    // EFFECTS: returns true if the given string contains only numbers
    public static boolean isValidNum(String s) {
        Pattern pattern = Pattern.compile("[^0-9]", Pattern.CASE_INSENSITIVE);
        Matcher match = pattern.matcher(s);
        boolean matchFound = match.find();
        return !matchFound;
    }

}
