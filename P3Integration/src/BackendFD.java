// --== CS400 Project One File Header ==--
// Name: <Peizhe Li>
// CSL Username: <peizhe>
// Email: <pli239@wisc.edu>
// Lecture #: <Lec 001; Tuesdays and Thursdays at 9:30 a.m.>
// Notes to Grader: 
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

public class BackendFD implements BackendInterfaceBD {

    @Override
    public boolean directRoute(String start, String end) {
        return "ABC".equals(start) && "DEF".equals(end);
    }

    @Override
    public ArrayList<String> getShortestPath(String start, String end) {
        if ("GHI".equals(start) && "JKL".equals(end)) {
            return new ArrayList<>(Arrays.asList("GHI", "MNO", "JKL"));
        }
        return null;
    }

    @Override
    public void loadData(String filename) throws FileNotFoundException {
        // Hardcoded data loaded, no need for file loading in this example
    	  if ("non_valis_test".equals(filename)) {

    	    } else {
    	        throw new FileNotFoundException("File not found: " + filename);
    	    }
    }

    @Override
    public ArrayList<String> getNearbyHotels(String dest) {
        if ("BCD".equals(dest)) {
            return new ArrayList<>(Arrays.asList("Hotel 1", "Hotel 2", "Hotel 3"));
        }
        return null;
    }

    @Override
    public double cost(String start, String end) {
        if ("VWX".equals(start) && "YZA".equals(end)) {
            return 100.0;
        }
        return -1;
    }

    @Override
    public ArrayList<String> shortestPathWithLayover(String start, String mid, String end) {
        if ("MNO".equals(start) && "PQR".equals(mid) && "STU".equals(end)) {
            return new ArrayList<>(Arrays.asList("MNO", "PQR", "STU"));
        }
        return null;
    }

    @Override
    public boolean isAirportInData(String airport) {
        return "XYZ".equals(airport);
    }
}
