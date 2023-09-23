import java.io.FileNotFoundException;
import java.util.ArrayList;


public interface BackendInterfaceBD {
    // private Graph<Airport, Double> graph;
    // public BackendInterface();
    public boolean directRoute(String start, String end);
    public ArrayList<String> getShortestPath(String start, String end);
    public void loadData(String filename)throws FileNotFoundException;
    public ArrayList<String> getNearbyHotels(String dest);
    public double cost(String start, String end);
    public ArrayList<String> shortestPathWithLayover(String start, String mid, String end);
    public boolean isAirportInData(String airport);


}
