import java.io.FileNotFoundException;
import java.util.List;
public interface AirportReaderInterfaceDW<NodeType, EdgeType>{
    // public AirportReaderInterface();
    public List<NodeType> readAirportsFromFile(String filename) throws FileNotFoundException;
    public List<EdgeType> readEdgesFromFile(String filename) throws FileNotFoundException;
}
