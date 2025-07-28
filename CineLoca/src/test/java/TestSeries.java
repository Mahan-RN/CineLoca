import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Series;

public class TestSeries {
    private Series series1;

    @BeforeEach
    void setup() {
        series1 = new Series("tt0944947", "Game of Thrones");
    }

    @Test
    void testSetTotalSeasons() {
        series1.setTotalSeasons(8);
        assertEquals(8, series1.getTotalSeasons());
    }

    @Test
    void testSetNetwork() {
        series1.setNetwork("HBO");
        assertEquals("HBO", series1.getNetwork());
    }
}
