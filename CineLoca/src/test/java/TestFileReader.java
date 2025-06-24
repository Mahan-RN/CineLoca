import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import model.Movie;
import model.FileReader;
import model.MovieCollection;

public class TestFileReader {
    private FileReader testReader;
    private String path;
    private Movie movie1;
    private Movie movie2;
    private Movie movie3;
    private MovieCollection collection;

    @Test
    void testDirectoryPathDoesNotExist() {
        try {
            path = "src//test//resources//movie//FAKEtestDirectory";
            testReader = new FileReader(path);
            fail("FileNotFoundException was not thrown!");
        } catch (FileNotFoundException e) {
            // expected
        }
    }

    @Test
    void testFilePathInsteadofDirectoryPath() {
        try {
            path = "src//test//resources//movie//testDirectory1//"
                    + "[tt1160419]movie3.mp4";
            testReader = new FileReader(path);
            fail("FileNotFoundException was not thrown!");
        } catch (FileNotFoundException e) {
            // expected
        }
    }

    @Nested
    class firstNestedClass {

        @BeforeEach
        void setup() {
            path = "src/test/resources/movie/testDirectory1";
            movie1 = new Movie("tt1160419", "Mad Max: Fury Road");
            movie2 = new Movie("tt1392190", "The Batman");
            movie3 = new Movie("tt1877830", "Dune: Part One");
            MovieCollection.resetSingleton();
            collection = MovieCollection.getInstance();
        }

        @Test
        void testConstructor() {
            try {
                testReader = new FileReader(path);
            } catch (FileNotFoundException e) {
                fail("FileNotFoundException should've not been thrown!");
            }
            assertTrue(testReader.getPathName().equals(path));
            ArrayList<File> files = testReader.getFiles();
            assertEquals(3, files.size());
            assertEquals("[tt1160419]movie3.mp4", files.get(0).getName());
            assertEquals("[tt1392190]movie1.mp4", files.get(1).getName());
            assertEquals("[tt1877830]movie2.mp4", files.get(2).getName());
        }

        @Test
        void testAddFilePathsToCollectionAllValidMovies() {
            collection.addMovie(movie1);
            collection.addMovie(movie2);
            collection.addMovie(movie3);
            try {
                testReader = new FileReader(path);
                testReader.addFilePathsToCollection(false);
            } catch (FileNotFoundException e) {
                fail("FileNotFoundException should've not been thrown!");
            } catch (IOException e) {
                fail("IOException should've not been thrown!");
            }
            String movie1Path = "src\\test\\resources\\movie\\testDirectory1\\"
                    + "[tt1160419]movie1.mp4";
            String movie2Path = "src\\test\\resources\\movie\\testDirectory1\\"
                    + "[tt1392190]movie2.mp4";
            String movie3Path = "src\\test\\resources\\movie\\testDirectory1\\"
                    + "[tt1877830]movie3.mp4";
            assertEquals(movie1Path, movie1.getFilePath());
            assertEquals(movie2Path, movie2.getFilePath());
            assertEquals(movie3Path, movie3.getFilePath());
        }

        @Test
        void testAddFilePathsToCollectionMovieIDNotInCollection() {
            collection.addMovie(movie1);
            try {
                testReader = new FileReader(path);
                testReader.addFilePathsToCollection(false);
            } catch (FileNotFoundException e) {
                fail("FileNotFoundException should've not been thrown!");
            } catch (IOException e) {
                fail("IOException should've not been thrown!");
            }
            String movie1Path = "src\\test\\resources\\movie\\testDirectory1\\"
                    + "[tt1160419]movie1.mp4";
            assertEquals(movie1Path, movie1.getFilePath());
            assertTrue(null == movie2.getFilePath());
            assertTrue(null == movie3.getFilePath());
        }

        @Test
        void testAddFilePathsToCollectionFileNameWithNoMovieID() {
            Movie movie4 = new Movie("tt4873118", "The Convenant");
            collection.addMovie(movie4);
            try {
                testReader = new FileReader(path);
                testReader.addFilePathsToCollection(false);
            } catch (FileNotFoundException e) {
                fail("FileNotFoundException should've not been thrown!");
            } catch (IOException e) {
                fail("IOException should've not been thrown!");
            }
            assertTrue(null == movie4.getFilePath());
        }

    }
}
