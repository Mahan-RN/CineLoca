**Description of the csv files in the resouces of the test folder:**

**1- SampleMovieDataCSV1:** contains 3 valid rows. Contents of all cells are valid 
and reading it should result in addition of all 3 movies to the collection, with
complete metadata

**2- SampleMovieDataCSV2:** contains several edge cases to check correct parsing of 
movie metadata from the CSV file when the user has poorly curated the file:
*a)* The first movie should be loaded, since it has both title and ID. However, 
all the remaining metadata should be left empty

*b)* The second row should be skipped (i.e., NOT result in a movie), as title
is not provided

*c)* The third row should be loaded as a movie. However, the release year and 
length should not be loaded, as they are invalid (contain non-numeric 
characters). Additionally, the english sub should remain false, as yes is 
invalid ("true" should be used instead). The movie ID is not in all lower case, 
but that should be OK.

*d)* The last row should be skipped as well, since the movie title is provided, 
but not the ID