import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class MovieReaderBD implements MovieReaderInterface {
	public MovieReaderBD() {}
	@Override
	public List<MovieInterface> readMoviesFromFile(String filename) throws FileNotFoundException {
		List<MovieInterface> res = new ArrayList<>();
		res.add(new MovieBD("title1",2023,"genre1"));
		res.add(new MovieBD("title2",2023,"genre2"));
		res.add(new MovieBD("title3",2023,"genre3"));
		res.add(new MovieBD("title4",2023,"genre4"));
		return res;
	}

}
