import java.io.FileNotFoundException;
import java.util.List;

public interface MovieReaderInterface {
        // public MovieReaderInterface();
        public List<MovieInterface> readMoviesFromFile(String filename) throws FileNotFoundException;
}