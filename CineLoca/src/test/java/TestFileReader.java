import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import model.Movie;
import model.FileReader;
import model.MovieCollection;

public class TestFileReader {
    private FileReader testReader;
    private String path;

    @Test
    void testFilePathDoesNotExist() {
        try {
            path = "src//test//resources//movie//FAKEtestDirectory";
            testReader = new FileReader(path);
            fail("FileNotFoundException was not thrown!");
        } catch (FileNotFoundException e) {
            // expected
        }
    }

}
