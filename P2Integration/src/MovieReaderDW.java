import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class reads movie data from a text file in which each movie and related
 * information is stored as a single line of text. Each of these lines contains
 * a post title, then releaseYear, and then genre in that order and these three
 * fields are separated by a comma.
 * 
 * @author DataWrangler, Peizhe Li.
 */
public class MovieReaderDW implements MovieReaderInterface {

        /**
         * This method reads each line from the specified file, and stores Movie objects
         * with corresponding data into a list that is then returned.
         * 
         * @param filename contains the path and name of the data file that should be
         *                 read from
         * @return List of movies read from file
         * @throws FileNotFoundException when a file corresponding to the provided
         *                               filename cannot be read from
         */
        public List<MovieInterface> readMoviesFromFile(String filename) throws FileNotFoundException {
                List<MovieInterface> movies = new ArrayList<>();
                File file = new File(filename);
                Scanner in = new Scanner(file);

                // skip the first line
                in.nextLine();

                while (in.hasNextLine()) {
                        String line = in.nextLine();
                        String[] values = parseCsvLine(line);
                        String title = values[1];
                        int releaseYear = Integer.parseInt(values[2]);
                        String genre = values[3];
                        MovieDW movie = new MovieDW(title, releaseYear, genre);
                        movies.add(movie);
                }
                in.close();

                return movies;
        }

        /**
         * Separate each line in the csv file into three fields as required
         * 
         * @param line in csv file
         * @return an array of required fields: title, releaseYear and genre
         * @throws IllegalArgumentException if the CSV line is invalid
         */
        public String[] parseCsvLine(String line) {
                // Create a list to store the values
                List<String> values = new ArrayList<>();
                // Keep track of whether we are inside a quoted field
                boolean inQuotes = false;
                // Keep track of the start index of each value
                int start = 0;

                // Loop through each character in the input line
                for (int i = 0; i < line.length(); i++) {
                        char c = line.charAt(i);

                        // Toggle the inQuotes flag when we encounter a double quote
                        if (c == '\"') {
                                inQuotes = !inQuotes;
                        }
                        // If we encounter a comma and we're not inside a quoted field, extract the
                        // current value and add it to the list
                        else if (c == ',' && !inQuotes) {
                                // Extract the current value from the substring between the start index and the
                                // current index
                                String value = line.substring(start, i).trim();
                                // Remove surrounding quotes from the value if they exist
                                if (value.startsWith("\"") && value.endsWith("\"")) {
                                        value = value.substring(1, value.length() - 1);
                                }
                                // Add the value to the list and update the start index
                                values.add(value);
                                start = i + 1;
                        }
                }

                // Extract the final value from the substring between the start index and the
                // end of the line
                String value = line.substring(start).trim();
                // Remove surrounding quotes from the value if they exist
                if (value.startsWith("\"") && value.endsWith("\"")) {
                        value = value.substring(1, value.length() - 1);
                }
                // Add the final value to the list
                values.add(value);

                // Check that the CSV line has at least three values (i.e. id, title, and year)
                if (values.size() <= 3) {
                        throw new IllegalArgumentException("Invalid CSV line: " + line);
                }

                // Convert the list to an array and return it
                String[] result = values.toArray(new String[0]);
                return result;
        }

        public static void main(String[] args) throws FileNotFoundException {

        }
}