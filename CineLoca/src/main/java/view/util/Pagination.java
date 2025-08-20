package view.util;

// A utility class to provide logic for pagination
public final class Pagination {

    private Pagination() {
    }

    // EFFECTS: returns the total number of pages based on the total number of
    // items and the total number of items displayed per page
    public static int totalPageNumber(int totalResults, int itemsPerPage) {
        return (totalResults + itemsPerPage - 1) / itemsPerPage;
    }

}
