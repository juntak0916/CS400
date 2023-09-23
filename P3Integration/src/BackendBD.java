// --== CS400 Spring 2023 File Header Information ==--
// Name: David Li
// Email: sli857@wisc.edu
// Team: AP
// TA: LAIK RUETTEN
// Lecturer: Gary Dahl
// Notes to Grader: none

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class BackendBD implements BackendInterfaceBD{
    private GraphInterface<AirportNodeInterfaceDW, Double> airportMap;
    private AirportReaderInterfaceDW<AirportNodeInterfaceDW,
        BaseGraph<AirportNodeInterfaceDW,Double>.Edge> reader;

    public BackendBD(AirportReaderInterfaceDW reader,
        GraphInterface graph){
        this.airportMap = graph;
        this.reader = reader;
    }

    @Override 
    public boolean directRoute(String start, String end) {
        if(!isAirportInData(start) || !isAirportInData(end)){
            return false;
        }
        AirportNodeInterfaceDW orig = airportMap.getNode (start);
        AirportNodeInterfaceDW dest = airportMap.getNode(end);
        try{
            airportMap.shortestPathData(orig,dest);
            return true;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override public ArrayList<String> getShortestPath(String start, String end) {
        ArrayList<String> result = new ArrayList<>();
        if(directRoute(start,end)){
            AirportNodeInterfaceDW orig = airportMap.getNode(start);
            AirportNodeInterfaceDW dest = airportMap.getNode(end);
            List<AirportNodeInterfaceDW> path = airportMap.shortestPathData(orig,dest);
            for(AirportNodeInterfaceDW x: path){
                result.add(x.getAirportName());
            }
            return result;
        }
        result.add("No available route");
        return result;
    }

    @Override public void loadData(String filename) throws FileNotFoundException {
        List<AirportNodeInterfaceDW> airports = reader.readAirportsFromFile(filename);
        List<BaseGraph<AirportNodeInterfaceDW,Double>.Edge> edges = reader.readEdgesFromFile(filename);
        try {
            for (AirportNodeInterfaceDW x : airports) {
                airportMap.insertNode(x);
            }
            for (BaseGraph<AirportNodeInterfaceDW,Double>.Edge x: edges){
                airportMap.insertEdge(x.predecessor.data, x.successor.data, x.data);
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override public ArrayList<String> getNearbyHotels(String dest) {
        if(isAirportInData(dest)){
            AirportNodeInterfaceDW target = airportMap.getNode(dest);
            return target.getNearbyHotels();
        }
        return null;
    }

    @Override public double cost(String start, String end) {
        if(directRoute(start,end)){
            AirportNodeInterfaceDW orig = airportMap.getNode(start);
            AirportNodeInterfaceDW dest = airportMap.getNode(end);
            return airportMap.shortestPathCost(orig, dest);
        }
        return 0;
    }

    @Override
    public ArrayList<String> shortestPathWithLayover(String start, String mid, String end) {
        ArrayList<String> result = new ArrayList<>();
        if(directRoute(start, mid) && directRoute(mid, end)){
            AirportNodeInterfaceDW orig = airportMap.getNode(start);
            AirportNodeInterfaceDW layover = airportMap.getNode(mid);
            AirportNodeInterfaceDW dest = airportMap.getNode(end);
            List<AirportNodeInterfaceDW> path1 = airportMap.shortestPathData(orig,layover);
            List<AirportNodeInterfaceDW> path2 = airportMap.shortestPathData(layover,dest);
            for(AirportNodeInterfaceDW x: path1){
                result.add(x.getAirportName());
            }
            result.add("\n");
            for(AirportNodeInterfaceDW x: path2){
                result.add(x.getAirportName());
            }
            return result;
        }
        result.add("No available route");
        return result;

    }

    @Override public boolean isAirportInData(String airport) {
        try{
            return airportMap.getNode(airport) != null;
        }
        catch(NoSuchElementException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}