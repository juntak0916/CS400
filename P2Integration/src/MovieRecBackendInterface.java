import java.io.FileNotFoundException;
import java.util.List;

public interface MovieRecBackendInterface {   
    // public MovieRecBackendInterface(RedBlackTreeInterface<String,MovieInterface> redblacktree, MovieReaderInterface movieReader);
    public void loadData(String filename) throws FileNotFoundException;
    public List<MovieInterface> findMoviesByTitleLetter(String letter);
    public boolean addMovie(MovieInterface movie);

    public String getStatisticsString();
    //return statistic of certain search
        //return total number of movies, most common released year
}
