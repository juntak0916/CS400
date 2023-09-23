import java.util.PriorityQueue;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GraphAE <NodeType, EdgeType extends Number> implements GraphInterface<NodeType, EdgeType>{
	// Codes from  the hw
    // Each node contains unique data along with two lists of directed edges
    protected class Node {
        public NodeType data;
        public List<Edge> edgesLeaving = new LinkedList<>();
        public List<Edge> edgesEntering = new LinkedList<>();
        public Node(NodeType data) { this.data = data; }
    }
    // Nodes can be retrieved from this hashtable by their unique data
    // key: airport name, value: nodes
    protected Hashtable<NodeType,Node> nodes = new Hashtable();

    // Each edge contains data/weight, and two nodes that it connects
    protected class Edge {
        public EdgeType data; // the weight or cost of this edge
        public Node predecessor;
        public Node successor;
        public Edge(EdgeType data, Node pred, Node succ) {
            this.data = data;
            this.predecessor = pred;
            this.successor = succ;
        }
    }
    protected int edgeCount = 0;
    // Edges can be retrieved through the edge lists in either connected node
    @Override
    public boolean insertNode(NodeType data) {
        if(nodes.containsKey(data)) return false; // throws NPE when data's null
        nodes.put(data,new Node(data));
        return true;
    }
    @Override
    public boolean removeNode(NodeType data) {
        // remove this node from nodes collection
        if(!nodes.containsKey(data)) return false; // throws NPE when data==null
        Node oldNode = nodes.remove(data);
        // remove all edges entering neighboring nodes from this one
        for(Edge edge : oldNode.edgesLeaving)
            edge.successor.edgesEntering.remove(edge);
        // remove all edges leaving neighboring nodes toward this one
        for(Edge edge : oldNode.edgesEntering)
            edge.predecessor.edgesLeaving.remove(edge);
        return true;
    }
    @Override
    public boolean containsNode(NodeType data) {
        return nodes.contains(data);
    }
    @Override
    public int getNodeCount() {
        return nodes.size();
    }
    @Override
    public boolean insertEdge(NodeType pred, NodeType succ, EdgeType weight) {
        // find nodes associated with node data, and return false when not found
        Node predNode = nodes.get(pred);
        Node succNode = nodes.get(succ);
        if(predNode == null || succNode == null) return false;
        try {
            // when an edge alread exists within the graph, update its weight
            Edge existingEdge = getEdgeHelper(pred,succ);
            existingEdge.data = weight;
        } catch(NoSuchElementException e) {
            // otherwise create a new edges
            Edge newEdge = new Edge(weight,predNode,succNode);
            this.edgeCount++;
            // and insert it into each of its adjacent nodes' respective lists
            predNode.edgesLeaving.add(newEdge);
            succNode.edgesEntering.add(newEdge);
        }
        return true;
    }
    @Override
    public boolean removeEdge(NodeType pred, NodeType succ) {
        try {
            // when an edge exists
            Edge oldEdge = getEdgeHelper(pred,succ);        
            // remove it from the edge lists of each adjacent node
            oldEdge.predecessor.edgesLeaving.remove(oldEdge);
            oldEdge.successor.edgesEntering.remove(oldEdge);
            // and decrement the edge count before removing
            this.edgeCount--;
            return true;
        } catch(NoSuchElementException e) {
            // when no such edge exists, return false instead
            return false;
        }
    }
    @Override
    public boolean containsEdge(NodeType pred, NodeType succ) {
        try { getEdgeHelper(pred,succ); return true; }
        catch(NoSuchElementException e) { return false; }
    }
    @Override
    public EdgeType getEdge(NodeType pred, NodeType succ) {
        return getEdgeHelper(pred,succ).data;
    }
    
    protected Edge getEdgeHelper(NodeType pred, NodeType succ) {
        Node predNode = nodes.get(pred);
        // search for edge through the predecessor's list of leaving edges
        for(Edge edge : predNode.edgesLeaving)
            // compare succ to the data in each leaving edge's successor
            if(edge.successor.data.equals(succ))
                return edge;
        // when no such edge can be found, throw NSE
        throw new NoSuchElementException("No edge from "+pred.toString()+" to "+
                                         succ.toString());
    }
    @Override
    public int getEdgeCount() {
        return this.edgeCount;
    }
    
    
    //Dijkstra
    protected class SearchNode implements Comparable<SearchNode> {
        public Node node;
        public double cost;
        public SearchNode predecessor;
        public SearchNode(Node node, double cost, SearchNode predecessor) {
            this.node = node;
            this.cost = cost;
            this.predecessor = predecessor;
        }
        public int compareTo(SearchNode other) {
            if( cost > other.cost ) return +1;
            if( cost < other.cost ) return -1;
            return 0;
        }
    }
    
    protected SearchNode computeShortestPath(NodeType start, NodeType end) {
    	//if hashmap doesnt contain input data throw exception
    	if(!nodes.containsKey(start) || !nodes.containsKey(end)) {
    		throw new NoSuchElementException("no element in the graph");
    	}
    	//create pq and initialized with starting node
    	PriorityQueue<SearchNode> que = new PriorityQueue<>();
    	SearchNode startSearchNode=new SearchNode(nodes.get(start), 0.0 ,null);
    	que.add(startSearchNode);
    	
    	//keep tracks of visited nodes
    	List<String> vis = new LinkedList<>();
    	//keep tracks of what is in the pq
    	Hashtable<NodeType,SearchNode> update = new Hashtable<>();
    	update.put(startSearchNode.node.data, startSearchNode);
    	
    	while(!que.isEmpty()) {
    		//we found the shortest path for starting to cur node 
    		SearchNode cur=que.remove();
    		update.remove(cur.node.data);
    		String str=((AirportNodeInterfaceDW)cur.node.data).getAirportName();
    		vis.add(str);
    		
    		//if cur is the end node
    		if(cur.node.data.equals(nodes.get(end).data)) {
    			return cur;
    		}
    		
    		//add to pq or update pq according to the adjacent nodes
    		for(Edge edge : cur.node.edgesLeaving ) {
    			Node NextNode=edge.successor;
    			
    			double NewCost = cur.cost + edge.data.doubleValue();
    			String visKey = ((AirportNodeInterfaceDW)NextNode.data).getAirportName();
    			if(vis.contains(visKey))continue;
    			
    			boolean contains = update.containsKey(NextNode.data);
    			if(!contains || NewCost < update.get(NextNode.data).cost) {
    				if(contains) {
    					que.remove(update.get(NextNode.data));
    					update.remove(NextNode.data);
    				}
    				SearchNode AddingNode=new SearchNode(NextNode, NewCost, cur);
        			que.add(AddingNode);
        			update.put(NextNode.data, AddingNode);
    			}
    		}
    		
    	}
    	throw new NoSuchElementException("no path from input start to end");
    }
    @Override
    public List<NodeType> shortestPathData(NodeType start, NodeType end) throws NoSuchElementException{
    	 List<NodeType> pathData=new LinkedList<>();
         SearchNode endNode = computeShortestPath(start, end);
         while (endNode != null) {
             pathData.add(0, endNode.node.data);
             endNode = endNode.predecessor;
         }
         return pathData;
    }

	@Override
	public List<NodeType> shortestPathWithMid(NodeType start, NodeType mid, NodeType end) throws NoSuchElementException{
		List<NodeType> res=new LinkedList<>();
		res.addAll(shortestPathData(start,mid));
		List<NodeType> arr=shortestPathData(mid,end);
		for(int i=1;i<arr.size();i++) {
			res.add(arr.get(i));
		}
		return res;
	}

	@Override
	public double shortestPathCost(NodeType start, NodeType end){
		SearchNode endNode = computeShortestPath(start, end);
        return endNode.cost;
	}
	@Override
	public NodeType getNode(String name) throws NoSuchElementException {
		Enumeration<NodeType> e = nodes.keys();
		while(e.hasMoreElements()) {
			AirportNodeInterfaceDW cur = (AirportNodeInterfaceDW)e.nextElement();
			
			if(cur.getAirportName().equals(name)) {
				return (NodeType)cur;
			}
		}
		throw new NoSuchElementException("no such element with airport name"+name);
	}
}
