import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import view.util.Pagination;

public class TestPagination {

    private int totalResults;
    private int itemsPerPage;

    @Test
    void testTotalPageNumberNoLeftOver() {
        totalResults = 100;
        itemsPerPage = 25;
        assertEquals(4, Pagination.totalPageNumber(totalResults, itemsPerPage));
    }

    @Test
    void testTotalPageNumberLeftOver() {
        totalResults = 110;
        itemsPerPage = 25;
        assertEquals(5, Pagination.totalPageNumber(totalResults, itemsPerPage));
    }

    @Test
    void testTotalPageNumberLessThanItemsPerPage() {
        totalResults = 24;
        itemsPerPage = 25;
        assertEquals(1, Pagination.totalPageNumber(totalResults, itemsPerPage));
    }
}
