public interface MovieInterface extends Comparable<MovieInterface>{
	// public MovieInterface(String title, int releaseYear, String genre);
	public String getTitle();
	public int getReleaseYear();
	public String getGenre();
	public int compareTo(MovieInterface o);
}

