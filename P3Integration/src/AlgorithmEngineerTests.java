import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AlgorithmEngineerTests {

	   private GraphAE<AirportNodeInterfaceDW, Double> graph;
	   private ArrayList<AirportNodeInterfaceDW> NTs;
	   private RouteFinderFrontend frontend;
	   private PrintStream originalOut;
	   BackendBD backend;
	    @BeforeEach
	    public void setup() {
	        originalOut = System.out;
	        
	        graph = new GraphAE<>();
	        NTs = new ArrayList<>();
	        
	        
	        backend = new BackendBD(new AirportReaderBD(),graph);
	        // add nodes
	        AirportNodeInterfaceDW NT1 = new AEPlaceHolderAiportNode("A",new ArrayList<String>(List.of("a")));
	        graph.insertNode(NT1);
	        
	        AirportNodeInterfaceDW NT2 = new AEPlaceHolderAiportNode("B",new ArrayList<String>(List.of("b","c")));
	        graph.insertNode(NT2);
	        
	        AirportNodeInterfaceDW NT3 = new AEPlaceHolderAiportNode("C",new ArrayList<String>(List.of("d","f")));
	        graph.insertNode(NT3);
	        
	        AirportNodeInterfaceDW NT4 = new AEPlaceHolderAiportNode("D",new ArrayList<String>(List.of("g")));
	        graph.insertNode(NT4);
	        AirportNodeInterfaceDW NT5 = new AEPlaceHolderAiportNode("E",new ArrayList<String>(List.of("f")));
	        graph.insertNode(NT5);
	        
	        NTs.add(NT1);
	        NTs.add(NT2);
	        NTs.add(NT3);
	        NTs.add(NT4);
	        NTs.add(NT5);
	        // add edges
	        // A -> B (4.0)
	        // A -> C (3.0)
	        // C -> A (2.0)
	        // D -> B (7.0)
	        // B -> E (5.0)
	        // C -> E (1.0)
	        
	        graph.insertEdge(NT1, NT3, 3.0);
	        graph.insertEdge(NT3, NT1, 2.0);
	        graph.insertEdge(NT1, NT2, 4.0);
	        graph.insertEdge(NT4, NT2, 7.0);
	        graph.insertEdge(NT2, NT5, 5.0);
	        graph.insertEdge(NT3, NT5, 1.0);
	    }
	    @Test
	    public void testShortestPath1() {
	    	assertEquals(List.of(NTs.get(0),NTs.get(2),NTs.get(4)), graph.shortestPathData(NTs.get(0), NTs.get(4)));
	        assertEquals(graph.shortestPathCost(NTs.get(0), NTs.get(4)), 4.0);
	    }
	    @Test
	    public void testShortestPath2() {
	    	assertEquals(List.of(NTs.get(0),NTs.get(1)), graph.shortestPathData(NTs.get(0), NTs.get(1)));
	        assertEquals(graph.shortestPathCost(NTs.get(0), NTs.get(1)), 4.0);
	    }
	    
	    @Test
	    public void testShortestPathMid() {
	    	assertEquals(List.of(NTs.get(0),NTs.get(1),NTs.get(4)), graph.shortestPathWithMid(NTs.get(0),NTs.get(1),NTs.get(4)));
	    }
	    
	   @Test
	    public void NoElement() {
	    	AirportNodeInterfaceDW tmp = new AEPlaceHolderAiportNode("T",new ArrayList<String>(List.of("f")));
	    	boolean except1=false;
	    	try {
	    		//tmp doesn't exist in the graph
	    		graph.shortestPathCost(NTs.get(0), tmp);
	    	}catch(NoSuchElementException e) {
	    		except1=true;
	    	}
	    	assertEquals(except1,true);
	    }
	   @Test
	    public void TestGet() {
	    	AirportNodeInterfaceDW tmp = graph.getNode("A");
	    	assertEquals(tmp.getAirportName(),"A");
	    	assertEquals(tmp.getNearbyHotels().get(0),"a");
	    }
	   //no path
	   @Test
	   public void noPath() {
		   boolean b=false;
		   try {
			   graph.shortestPathCost(NTs.get(0), NTs.get(3));
		   }catch(NoSuchElementException e) {
			   b=true;
		   }
		   assertEquals(b,true);
		   
	   }
	  /**
	   * this method checks if menu properly prints. 
	   * */ 
	  @Test
	  public void CodeReviewOfFD1(){
	        System.setIn(new ByteArrayInputStream("Q\n".getBytes()));
	        ByteArrayOutputStream testOut = new ByteArrayOutputStream();
	        System.setOut(new PrintStream(testOut));
	        frontend = new RouteFinderFrontend(new Scanner(System.in), backend);
	        frontend.runCommandLoop();
	        System.setOut(originalOut);
	        String output = testOut.toString();
	        System.out.print(output);
	        assertTrue(output.contains("Welcome to the Airport Route Finder App."));
	        assertTrue(output.contains("[L]oad data from file"));
	        assertTrue(output.contains("Check if [D]irect route exists between two airports"));
	        assertTrue(output.contains("Identify [S]hortest path between two airports"));
	  }
	  @Test
	  void testSearchAirportCommand() {
	        System.setIn(new ByteArrayInputStream("A\nA\nQ\n".getBytes()));
	        ByteArrayOutputStream testOut = new ByteArrayOutputStream();
	        System.setOut(new PrintStream(testOut));
	        frontend = new RouteFinderFrontend(new Scanner(System.in), backend);
	        frontend.runCommandLoop();
	        System.setOut(originalOut);
	        String output = testOut.toString();
	        System.out.println(output);
	        assertTrue(output.contains("Airport A is in the data."));
	  } 
	  @Test
	  void Integration1(){
		    //this tests interaction between AE's graph and FD's menu
		    graph.insertNode(new AEPlaceHolderAiportNode("Seattle",new ArrayList<String>(List.of("seattle nearby"))));
		    System.setIn(new ByteArrayInputStream("A\nSeattle\nH\nSeattle\nQ\n".getBytes()));
	        ByteArrayOutputStream testOut = new ByteArrayOutputStream();
	        System.setOut(new PrintStream(testOut));
	        frontend = new RouteFinderFrontend(new Scanner(System.in), backend);
	        frontend.runCommandLoop();
	        System.setOut(originalOut);
	        String output = testOut.toString();
	        assertTrue(output.contains("Airport Seattle is in the data."));
	        assertTrue(output.contains("- seattle nearby"));
	  }
	  @Test
	  void Integration2(){
		   //this tests interaction between BD and FD
		    System.setIn(new ByteArrayInputStream("C\nA\nE\nQ\n".getBytes()));
	        ByteArrayOutputStream testOut = new ByteArrayOutputStream();
	        System.setOut(new PrintStream(testOut));
	        frontend = new RouteFinderFrontend(new Scanner(System.in), backend);
	        frontend.runCommandLoop();
	        System.setOut(originalOut);
	        String output = testOut.toString();
	        System.out.println(output.toString());
		    Double b = backend.cost("A", "E");
		    assertTrue(output.contains(b.toString()));
	  }
	   
}
