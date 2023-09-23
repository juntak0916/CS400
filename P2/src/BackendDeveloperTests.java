import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.Test;

public class BackendDeveloperTests {
	@Test
	public void test1() {
		//this method tests loadData and addMovie method 
		RedBlackTreeInterface<MovieInterface> RBT=new RedBlackTreeBD<MovieInterface>();
		MovieReaderInterface movieReader=new MovieReaderBD();
		
		MovieRecBackendInterface MovieRec=new MovieRecBD(RBT,movieReader);
		try {
			MovieRec.loadData("test1");
		}catch(Exception e) {
			assertEquals(true,false);
		}
		MovieInterface movie=new MovieBD("testTitle1",2021,"testGenre");
		
		assertEquals(MovieRec.addMovie(movie),true);
	}
	@Test
	public void test2() {
		//this method statistic function 
		RedBlackTreeInterface<MovieInterface> RBT=new RedBlackTreeBD<MovieInterface>();
		MovieReaderInterface movieReader=new MovieReaderBD();
		
		MovieRecBackendInterface MovieRec=new MovieRecBD(RBT,movieReader);
		try {
			MovieRec.loadData("test1");
		}catch(Exception e) {
			assertEquals(true,false);
		}
		String str=MovieRec.getStatisticsString();
		assertEquals("total number of movies: 4\nmost common released year: 2023",str);
	}
	@Test
	public void test3() {
		//this method tests findMoviesByTitleLetter function 
		RedBlackTreeInterface<MovieInterface> RBT=new RedBlackTreeBD<MovieInterface>();
		MovieReaderInterface movieReader=new MovieReaderBD();
		
		MovieRecBackendInterface MovieRec=new MovieRecBD(RBT,movieReader);
		try {
			MovieRec.loadData("test1");
		}catch(Exception e) {
			assertEquals(true,false);
		}
		MovieRec.addMovie(new MovieBD("testTitle1",2021,"testGenre"));
		List<String> arr=MovieRec.findMoviesByTitleLetter("testTitle1");
		assertEquals(arr.get(0),"testTitle1");
		assertEquals(arr.get(1),"2021");
		assertEquals(arr.get(2),"testGenre");
	}
	@Test
	public void test4() {
		//this method tests all the functions in various orders
		RedBlackTreeInterface<MovieInterface> RBT=new RedBlackTreeBD<MovieInterface>();
		MovieReaderInterface movieReader=new MovieReaderBD();
		
		MovieRecBackendInterface MovieRec=new MovieRecBD(RBT,movieReader);
		try {
			MovieRec.loadData("test1");
		}catch(Exception e) {
			assertEquals(true,false);
		}
		MovieRec.addMovie(new MovieBD("testTitle1",2021,"testGenre1"));
		List<String> arr1=MovieRec.findMoviesByTitleLetter("testTitle1");
		assertEquals(arr1.get(0),"testTitle1");
		assertEquals(arr1.get(1),"2021");
		assertEquals(arr1.get(2),"testGenre1");
		
		MovieRec.addMovie(new MovieBD("testTitle2",2022,"testGenre2"));
		List<String> arr2=MovieRec.findMoviesByTitleLetter("testTitle2");
		assertEquals(arr2.get(0),"testTitle2");
		assertEquals(arr2.get(1),"2022");
		assertEquals(arr2.get(2),"testGenre2");
		
		String str=MovieRec.getStatisticsString();
		assertEquals("total number of movies: 6\nmost common released year: 2023",str);
	}
	@Test
	public void test5() {
		//changing statistics by adding many movies that was release in 2000
		RedBlackTreeInterface<MovieInterface> RBT=new RedBlackTreeBD<MovieInterface>();
		MovieReaderInterface movieReader=new MovieReaderBD();
		
		MovieRecBackendInterface MovieRec=new MovieRecBD(RBT,movieReader);
		try {
			MovieRec.loadData("test1");
		}catch(Exception e) {
			assertEquals(true,false);
		}
		MovieRec.addMovie(new MovieBD("testTitle1",2000,"testGenre"));
		MovieRec.addMovie(new MovieBD("testTitle2",2000,"testGenre"));
		MovieRec.addMovie(new MovieBD("testTitle3",2000,"testGenre"));
		MovieRec.addMovie(new MovieBD("testTitle4",2000,"testGenre"));
		MovieRec.addMovie(new MovieBD("testTitle5",2000,"testGenre"));
		String str=MovieRec.getStatisticsString();
		assertEquals("total number of movies: 9\nmost common released year: 2000",str);
	}

}
