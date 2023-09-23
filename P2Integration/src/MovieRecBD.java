// --== CS400 Spring 2023 File Header Information ==--
// Name: Juntak Lee
// Email: lee2322@wisc.edu
// Team: AP
// TA: Gary Dahl
// Lecturer: Gary Dahl
// Notes to Grader: none

import java.io.FileNotFoundException;
import java.util.List;

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
        private boolean addMovieToTree(MovieInterface movie) {
             return m_rbt.insert(movie);
      	}
        @Override
        public void loadData(String filename) throws FileNotFoundException {
                m_list = m_movieReader.readMoviesFromFile(filename);

                for(int i=0,n=m_list.size();i<n;i++) {
                        this.addMovieToTree(m_list.get(i));
                }
        }

        @Override
        public List<MovieInterface> findMoviesByTitleLetter(String letter) {
                return m_rbt.get(letter);
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