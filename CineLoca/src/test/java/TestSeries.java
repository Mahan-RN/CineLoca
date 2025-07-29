import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Season;
import model.Series;

public class TestSeries {
    private Series series1;

    @BeforeEach
    void setup() {
        series1 = new Series("tt0944947", "Game of Thrones");
    }

    @Test
    void testConstructor() {
        assertTrue(series1.getSeaonsList().isEmpty());
    }

    @Test
    void testAddSeason() {
        Season season1 = new Season(1);
        Season season2 = new Season(2);
        series1.addSeason(season1);
        series1.addSeason(season2);
        assertEquals(2, series1.getAvailableSeasons());
        assertEquals(2, series1.getSeaonsList().size());
        assertEquals(season1, series1.getSeaonsList().get(0));
        assertEquals(season2, series1.getSeaonsList().get(1));
    }

    @Test
    void testIsSeasonCountUpToDate() {
        Season season1 = new Season(1);
        Season season2 = new Season(2);
        series1.addSeason(season1);
        series1.addSeason(season2);
        series1.setTotalSeasonsIMDb(1);
        assertFalse(series1.isSeasonCountUpToDate());
        series1.setTotalSeasonsIMDb(3);
        assertFalse(series1.isSeasonCountUpToDate());
        series1.setTotalSeasonsIMDb(2);
        assertTrue(series1.isSeasonCountUpToDate());
    }

    @Test
    void testSetTotalSeasons() {
        series1.setTotalSeasonsIMDb(8);
        assertEquals(8, series1.getTotalSeasonsIMDb());
    }

    @Test
    void testSetNetwork() {
        series1.setNetwork("HBO");
        assertEquals("HBO", series1.getNetwork());
    }
}
