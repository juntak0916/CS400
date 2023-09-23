
public class MovieBD implements MovieInterface{
	//movie obj
	private String m_title;
	private int m_releaseYear;
	private String m_genre;
	public MovieBD(String title, int releaseYear, String genre) {
		m_title=title;
		m_releaseYear=releaseYear;
		m_genre=genre;
	}
	@Override
	public String getTitle() {
		return m_title;
	}

	@Override
	public int getReleaseYear() {
		return m_releaseYear;
	}

	@Override
	public String getGenre() {
		return m_genre;
	}

	@Override
	public int compareTo(MovieInterface o) {
		return 1;
	}

}
