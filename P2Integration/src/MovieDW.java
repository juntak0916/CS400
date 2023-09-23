public class MovieDW implements MovieInterface{
        private String title;
        private int releaseYear;
        private String genre;

        /**
         * Instantiation of new movie objects requires the following data:
         * 
         * @param title       describing the movie's title
         * @param releaseYear of the movie
         * @param genre       of the movie
         */
        public MovieDW(String title, int releaseYear, String genre) {
                this.title = title;
                this.releaseYear = releaseYear;
                this.genre = genre;
        }

        /**
         * Getter method for the movie's title
         * 
         * @return movie's title
         */
        @Override
        public String getTitle() {
                return title; // movie's accessor method for title
        }

        /**
         * Getter method for the movie's release year
         * 
         * @return movie's release year
         */
        @Override
        public int getReleaseYear() {
                return releaseYear; // movie's accessor method for releaseYear
        }

        /**
         * Getter method for the movie's genre
         * 
         * @return movie's genre
         */
        @Override
        public String getGenre() {
                return genre; // movie's accessor method for genre
        }

        /**
         * Method for comparing movie objects by title
         * 
         * @param m is the movie object being compared to
         * @return result of the comparison
         */
        @Override
        public int compareTo(MovieInterface m) {
                return this.getTitle().compareTo(m.getTitle());
        }
        public String toString() {
                return this.getTitle()+this.getReleaseYear()+this.getGenre();
        }

}