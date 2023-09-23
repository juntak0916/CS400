import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

public class MovieRecBD implements MovieRecBackendInterface {
	private MovieReaderInterface m_movieReader;
	private RedBlackTreeInterface<MovieInterface> m_rbt;
	private List<MovieInterface> m_list;
	
	public MovieRecBD(RedBlackTreeInterface<MovieInterface> rbt, MovieReaderInterface movieReader) {
		m_movieReader=movieReader;
		m_rbt=rbt;
	}

	@Override
	public boolean addMovie(MovieInterface movie) {
		m_list.add(movie);
		return m_rbt.insert(movie);
	}
	private boolean addMovie(MovieInterface movie,boolean calledbyLoadData) {
		return m_rbt.insert(movie);
	}
	@Override
	public void loadData(String filename) throws FileNotFoundException {
		m_list = m_movieReader.readMoviesFromFile(filename);
		
		for(int i=0,n=m_list.size();i<n;i++) {
			this.addMovie(m_list.get(i),true);
		}
	}
	
	@Override
	public List<String> findMoviesByTitleLetter(String letter) {
		MovieInterface movie=m_rbt.get(letter);
		
		String Title=movie.getTitle(),Year=Integer.toString(movie.getReleaseYear()),Genre=movie.getGenre();
		List<String> res = new ArrayList<String>();
		res.add(Title);
		res.add(Year);
		res.add(Genre);
		return res;
	}

	@Override
	public String getStatisticsString() {
		String res="total number of movies: ";
		res+= m_list.size();
		
		res+="\nmost common released year: ";
		int cnt[]=new int[2100];
		for(MovieInterface movie:this.m_list) 
			cnt[movie.getReleaseYear()]++;
		
		int max=0,maxYear=0;
		for(int i=0;i<2100;i++) {
			if(max<cnt[i]) {
				max=cnt[i];
				maxYear=i;
			}
		}
		res+=Integer.toString(maxYear);
		return res;
	}
}
