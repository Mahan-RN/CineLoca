import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import view.util.Pagination;

public class TestPagination {

    private int totalResults;
    private int itemsPerPage;
    private int pageNumber;

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

    @Test
    void testStartIndex() {
        itemsPerPage = 25;
        pageNumber = 1;
        assertEquals(0, Pagination.startIndex(pageNumber, itemsPerPage));
        pageNumber = 2;
        assertEquals(25, Pagination.startIndex(pageNumber, itemsPerPage));
        pageNumber = 3;
        assertEquals(50, Pagination.startIndex(pageNumber, itemsPerPage));
    }

    @Test
    void testEndIndex() {
        itemsPerPage = 25;
        totalResults = 60;
        pageNumber = 1;
        assertEquals(24, Pagination.endIndex(pageNumber, itemsPerPage, totalResults));
        pageNumber = 2;
        assertEquals(49, Pagination.endIndex(pageNumber, itemsPerPage, totalResults));
        pageNumber = 3;
        assertEquals(59, Pagination.endIndex(pageNumber, itemsPerPage, totalResults));
    }
}
