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

    // EFFECTS: given the page number and total results per page, returns the
    // index of the first item to be displayed in the given page
    public static int startIndex(int pageNumber, int itemsPerPage) {
        return (pageNumber - 1) * itemsPerPage;
    }

    // EFFECTS: given the page number, the total results per page, and total
    // results, returns the index of the last item to be displayed in the given
    // page
    public static int endIndex(int pageNumber, int itemsPerPage, int totalResults) {
        int lastIndex = (pageNumber * itemsPerPage) - 1;
        if (lastIndex <= totalResults - 1) {
            return lastIndex;
        } else {
            return totalResults - 1;
        }
    }

}
