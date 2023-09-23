import java.util.List;
import java.util.NoSuchElementException;
public interface GraphInterface<NodeType,EdgeType extends Number>
    extends GraphADT<NodeType, EdgeType> {
    public List<NodeType> shortestPathWithMid(NodeType start,NodeType mid,NodeType end);
    public double shortestPathCost(NodeType start, NodeType end);
    public NodeType getNode(String name) throws NoSuchElementException;
}
