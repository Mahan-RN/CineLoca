# CineLoca
A Java-based (Swing) desktop application for viewing the movies and TV shows on your hard drive.

<p align="center">
  <video width="600" src="readmeResources\CineLoca Demo.mp4" autoplay loop muted playsinline></video>
</p>

# 1- Intro
Managing a large collection of movies and TV shows on your hard drive can be an 
overwhelming task. CineLoca gives you a structured way to organize your collection
and view it in a user interface similar to the major streaming services.

## Features 
- Friendly user interface. No coding background required
- No network connection required
- Posters make it easy for you to view and scroll through your collection
- Keep track of movies and TV shows separately
- Sort your movies and TV shows based on name and release year
- Title-based searching
- View media metadata with ease

# 2- Installation
CineLoca is available on Windows, Mac, and Linux. Download the installer 
specific to your system [here](https://www.jdeploy.com/~cineloca).

> [!IMPORTANT]
> CineLoca was developed and tested on a Windows machine. However, it should work
> on other operating systems as well.

# 3- User Manual

For CineLoca to properly recognize and view your collection, you need to:

1. Organize your local files and folders (see below)

2. Provide metadata for your collection in `.csv` format (see below)

### Overview of Requirements:

You will need to provide 3 files for your movies and 3 for series (so 6 total):

1. A metadata file in `.csv` format

2. A directory containing your movies/series

3. A directory containing posters

## I- Organizing Local Data
Movies and TV shows should be organized slightly differently due to their nature.
Let's start with movies first:

### a) Movie Curation 
1. Store all of your movies in the same directory. The name of this directory doesn't
matter, but let's call it `movie` for the rest of this guide

- `movies` can contain as many movie files as you like, but any sub-directories will be ignored
- **Example:**
```. 
└── movies/ 
    ├── Dune 
    ├── Rambo 
    ├── Titanic 
    └── ... 
```
2. Add the IMDb ID of each movie to its file name in square (`[]`) brackets

- The IMDb ID can be found by going to the relevant IMDb page and looking at its URL. 
The ID is the part starting with `tt` followed by numbers.
- If the file name contains other square brackets, IMDb ID must be in the 
first set of square brackets in the file name

**Example:** suppose we have a file named `dune_part_one.mkv` in our `movie` folder. 
We grab the IMDb ID from the IMDb page and rename the file to 
`[tt1160419]_dune_part_one.mkv` (underscores are completely optional).

![Image](readmeResources/imdb_url.png)

### b) TV Show Curation 
1. Each TV show should be a directory containing the seasons of that show as its 
sub-directories. Each season directory should contain all the episodes of that season.
Store all TV show directories under a single parent directory. Let's call it `series`
for the rest of this tutorial. 

**Example:** suppose we have 2 TV shows: `tvshow1`, which has 2 seasons, 3 episodes 
each and `tvshow2`, which has 3 seasons, 1 episode each. The directory structure
should look like:

```
.
└── series/
    ├── tvshow1/
    │   ├── S01/
    │   │   ├── Episode1
    │   │   ├── Episode2
    │   │   └── Episode3
    │   └── S02/
    │       ├── Episode1
    │       ├── Episode2
    │       └── Episode3
    └── tvshow2/
        ├── S01/
        │   └── Episode1
        ├── S02/
        │   └── Episode2
        └── S03/
            └── Episode3
```

2. Add the IMDb ID of each TV show to its parent directory in square brackets,
just like how we did it for movies

3. Directories corresponding to each season should be named like `S01`, `S02`, ...

4. Each episode file should contain an episode number in `EXX` format, where `XX`
is the episode number. The file for episode 1 should contain `E01` (this shouldn't be a 
problem, as most files come with this format for episode naming anyway)

**Example:** here's a correctly formatted example (assuming it has only one season, with two episodes):
```
.
└── series/
    └── [tt0903747] Breaking Bad/
        └── S01/
            └── pilotS01E01.1080p.mkv
```

## II- Metadata Curation
Movie and TV show metadata can be curated manually using a spreadsheet like Google Sheets and then
be exported as a `.csv` file.
> [!NOTE]
> If you are comfortable with scripting, you can scrape metadata from an API like
> [OMDb API](https://www.omdbapi.com/) to automate this process (I have a quick & 
> dirty Python script that I can share --- let me know if you are interested).

- **Movies:** fill out the template spreadsheet [here](https://docs.google.com/spreadsheets/d/1bfY_o4ck6YlKGNrfBqvG0yTYy_j606Xsnb2Q06Qs9hU/edit?usp=sharing) 
and then export it as a `.csv` file. The first 3 rows are filled out as an example

- **TV Shows:** Just like for movies, fill out the template spreadsheet [here](https://docs.google.com/spreadsheets/d/1_CEbRZe4NAjfFcWyuesBwpxv_OfKA2vcN9wEeGqNHV0/edit?gid=0#gid=0)
and then export it as a `.csv` file. Again, the first 3 rows are filled out for your reference.

**Notes:**
- If a TV show doesn't have an end year (i.e., it's still running), enter `0` as its end year.
The app will process this as `Present`
- Actors' names can be separated via either comma `,` or semicolon `;`. 
I recommend entering no more than 3 names for proper display in the app

# 4- License
See [LICENSE.md](LICENSE).


# 5- Misc
Test video files are copyright-free, obtained from https://free-stock.videos 