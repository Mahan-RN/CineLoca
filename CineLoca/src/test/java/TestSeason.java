import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Season;

public class TestSeason {
    private Season season;

    @BeforeEach
    void setup() {
        season = new Season(1);
    }

    @Test
    void testConstructor() {
        assertEquals(1, season.getSeasonNumber());
        assertTrue(season.getSortedEpisodes().isEmpty());
        assertEquals(0, season.getTotalEpisodes());
    }

    @Test
    void testAddEpisode() {
        season.addEpisode("One");
        season.addEpisode("Two");
        assertEquals(2, season.getTotalEpisodes());
        assertEquals(2, season.getSortedEpisodes().size());
        assertEquals("One", season.getSortedEpisodes().get(0));
        assertEquals("Two", season.getSortedEpisodes().get(1));
    }

}
