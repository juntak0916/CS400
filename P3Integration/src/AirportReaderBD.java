import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class AirportReaderBD<NodeType, EdgeType> implements AirportReaderInterfaceDW<NodeType,
    EdgeType>{

    @Override public List<NodeType> readAirportsFromFile(String filename) throws FileNotFoundException{
        throw new FileNotFoundException();
    }

    @Override public List<EdgeType> readEdgesFromFile(String filename) throws FileNotFoundException {
        throw new FileNotFoundException();
    }
}
