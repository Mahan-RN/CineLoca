import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import model.ParsingUtilities;

public class TestParsingUtilities {
    private String string;

    @Test
    void testTrimMovieData() {
        string = "";
        assertEquals("", ParsingUtilities.trimMediaData(string));
        string = " Hello";
        assertEquals("Hello", ParsingUtilities.trimMediaData(string));
        string = "Hello ";
        assertEquals("Hello", ParsingUtilities.trimMediaData(string));
        string = " Hello ";
        assertEquals("Hello", ParsingUtilities.trimMediaData(string));
    }

    @Test
    void testTrimMovieID() {
        string = "";
        assertEquals("", ParsingUtilities.trimMediaID(string));
        string = " Hello";
        assertEquals("hello", ParsingUtilities.trimMediaID(string));
        string = "Hello ";
        assertEquals("hello", ParsingUtilities.trimMediaID(string));
        string = " HELLO12345 ";
        assertEquals("hello12345", ParsingUtilities.trimMediaID(string));
    }

    @Test
    void testActorsToList() {
        string = "";
        List<String> output1 = ParsingUtilities.actorsToList(string);
        assertEquals(1, output1.size());
        assertEquals("", output1.get(0));
        string = "Person1";
        List<String> output2 = ParsingUtilities.actorsToList(string);
        assertEquals(1, output2.size());
        assertEquals("Person1", output2.get(0));
        string = "Person1;Person2;Person3";
        List<String> output3 = ParsingUtilities.actorsToList(string);
        assertEquals(3, output3.size());
        assertEquals("Person1", output3.get(0));
        assertEquals("Person2", output3.get(1));
        assertEquals("Person3", output3.get(2));
    }

    @Test
    void testIsValidNum() {
        string = "";
        assertFalse(ParsingUtilities.isValidNum(string));
        string = "Cat";
        assertFalse(ParsingUtilities.isValidNum(string));
        string = "Cat123";
        assertFalse(ParsingUtilities.isValidNum(string));
        string = "0**0";
        assertFalse(ParsingUtilities.isValidNum(string));
        string = "1";
        assertTrue(ParsingUtilities.isValidNum(string));
        string = "12345";
        assertTrue(ParsingUtilities.isValidNum(string));
    }

    @Test
    void testfileNameToMovieID() {
        string = "";
        assertEquals(null, ParsingUtilities.fileNameToMediaID(string));
        string = "[tt12345_myMovie";
        assertEquals(null, ParsingUtilities.fileNameToMediaID(string));
        string = "tt12345]_myMovie";
        assertEquals(null, ParsingUtilities.fileNameToMediaID(string));
        string = "[t]";
        assertEquals(null, ParsingUtilities.fileNameToMediaID(string));
        string = "[]my_Movie";
        assertEquals(null, ParsingUtilities.fileNameToMediaID(string));
        string = "[tt1234]My_Movie";
        assertEquals("tt1234", ParsingUtilities.fileNameToMediaID(string));
        string = "[tt1234]My_Movie[1080P]_[2002]";
        assertEquals("tt1234", ParsingUtilities.fileNameToMediaID(string));
        string = "helloooo[tt1234]My_Movie[1080P]_[2002]";
        assertEquals("tt1234", ParsingUtilities.fileNameToMediaID(string));
    }
}
