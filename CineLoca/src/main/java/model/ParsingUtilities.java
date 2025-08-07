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
    public static String trimMediaData(String s) {
        return s.strip();
    }

    // EFFECTS: removes ALL whitespaces in the given string. Converts the string
    // into all lower case
    public static String trimMediaID(String s) {
        return s.replaceAll("\\s+", "").toLowerCase();
    }

    // EFFECTS: takes a string and splits it by ";" into a list of strings
    public static List<String> actorsToList(String actors) {
        String[] tempList = actors.split(";");
        ArrayList<String> actorsList = new ArrayList<>(Arrays.asList(tempList));
        return actorsList;
    }

    // EFFECTS: returns true if the given string is not empty and
    // contains only numbers
    public static boolean isValidNum(String s) {
        if (s.isBlank()) {
            return false;
        } else {
            Pattern pattern = Pattern.compile("[^0-9]", Pattern.CASE_INSENSITIVE);
            Matcher match = pattern.matcher(s);
            boolean matchFound = match.find();
            return !matchFound;
        }
    }

    // EFFECTS: takes a file name and returns episode number if the regex match
    // is found. Else, returns zero
    public static int fileNameToEpisodeNumber(String s) {
        Pattern pattern = Pattern.compile("E\\d\\d", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(s);
        boolean found = matcher.find();
        if (found) {
            String matchNum = matcher.group().substring(1, 3);
            int episodeNumer = Integer.parseInt(matchNum);
            return episodeNumer;
        } else {
            return 0;
        }
    }

    // EFFECTS: gets the substring flanked by the first [] in the file name.
    // Example: given "[tt1234]My_Movie", should return "tt1234"
    public static String fileNameToMediaID(String fileName) {
        if (!fileName.contains("[") || !fileName.contains("]")) {
            return null;
        } else if (fileName.length() < 4) {
            return null;
        } else if (fileName.substring(fileName.indexOf("[") + 1,
                fileName.indexOf("]")).isEmpty()) {
            return null;
        } else {
            String id = fileName.substring(fileName.indexOf("[") + 1,
                    fileName.indexOf("]"));
            return trimMediaID(id);
        }
    }
}
