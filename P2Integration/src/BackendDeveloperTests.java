// --== CS400 Spring 2023 File Header Information ==--
// Name: Juntak Lee
// Email: lee2322@wisc.edu
// Team: AP
// TA: Gary Dahl
// Lecturer: Gary Dahl
// Notes to Grader: none
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.List;

import org.junit.jupiter.api.Test;

class BackendDeveloperTests {
    @Test
    public void CodeReviewOfDW() {
         //tests DW readMoviesFromFile method from MovieReader
    	 //tests DW get methods from Movie object
          MovieReaderDW reader= new MovieReaderDW();
          String filename = "./movies.csv";
          List<MovieInterface> arr = null;
          try {
        	  arr=reader.readMoviesFromFile(filename);
          }catch(Exception e) {
        	  assertEquals(true,false);
          }
          
          MovieInterface movie= arr.get(7);
          assertEquals(movie.getTitle(),"Tom and Huck");
          assertEquals(movie.getReleaseYear(),1995);
          assertEquals(movie.getGenre(),"Adventure|Children");
  }
    //it tests insert size and LevelOrder and InOrder traverse 
    @Test
    public void CodeReviewOfAE(){
    	RedBlackTree<MovieInterface> rbt = new RedBlackTree<>();
        MovieDW movie1 = new MovieDW("5",0,"test");
        MovieDW movie2 = new MovieDW("2",0,"test");
        MovieDW movie3 = new MovieDW("3",0,"test");
        MovieDW movie4 = new MovieDW("7",0,"test");
        rbt.insert(movie1);
        rbt.insert(movie2);
        rbt.insert(movie3);
        rbt.insert(movie4);
        boolean IllegalArgument=false;
        try {
        	rbt.insert(movie3);
        }catch(IllegalArgumentException e) {
        	IllegalArgument=true;
        }
        assertEquals(IllegalArgument,true);
        assertEquals(rbt.size(),4);
        assertEquals("[ 30test, 20test, 50test, 70test ]",rbt.toLevelOrderString());
        assertEquals("[ 20test, 30test, 50test, 70test ]",rbt.toInOrderString());
    }
    @Test
    public void BDIntegration1() {
            //Changing statistics by adding movies
            RedBlackTreeInterface<MovieInterface> RBT=new RedBlackTree<MovieInterface>();
            MovieReaderInterface movieReader=new MovieReaderDW();
            
            MovieRecBackendInterface MovieRec=new MovieRecBD(RBT,movieReader);
            try {
                    MovieRec.loadData("./movies.csv");
            }catch(Exception e) {
                    assertEquals(true,false);
            }
            String str=MovieRec.getStatisticsString();
            assertEquals("total number of movies: 98\nmost common released year: 1995",str);
            MovieRec.addMovie(new MovieDW("testTitle1",2000,"testGenre"));
            MovieRec.addMovie(new MovieDW("testTitle2",2000,"testGenre"));
            MovieRec.addMovie(new MovieDW("testTitle3",2000,"testGenre"));
            str=MovieRec.getStatisticsString();
            assertEquals("total number of movies: 101\nmost common released year: 1995",str);
    }
    @Test
    public void BDIntegration2(){
    	//Testing if movies are sucessfully added to the RBT
    	RedBlackTreeInterface<MovieInterface> RBT=new RedBlackTree<MovieInterface>();
        MovieReaderInterface movieReader=new MovieReaderDW();
        MovieRecBackendInterface MovieRec=new MovieRecBD(RBT,movieReader);
        try {
                MovieRec.loadData("./movies.csv");
        }catch(Exception e) {
                assertEquals(true,false);
        }
        String str=RBT.toLevelOrderString();
        assertEquals(str.length(),2844);
    }
}
