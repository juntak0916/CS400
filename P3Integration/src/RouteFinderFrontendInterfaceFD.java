// --== CS400 Project One File Header ==--
// Name: <Peizhe Li>
// CSL Username: <peizhe>
// Email: <pli239@wisc.edu>
// Lecture #: <Lec 001; Tuesdays and Thursdays at 9:30 a.m.>
// Notes to Grader: 

public interface RouteFinderFrontendInterfaceFD {
    //public RouteFinderFrontendXX(Scanner userInput, RouteFinderBackendInterface backend);
    public void runCommandLoop();
    public char mainMenuPrompt();
    public void loadDataCommand(); // hard code this
    public void searchAirportCommand(String airport);
    public void checkDirectRouteCommand(String start, String end);
    public void identifyShortestPath(String start, String end);
    public void identifyShortestPathWithLayover(String start, String mid, String end);
    public void shortestPathCost(String start, String end);
    public void checkAirportHotels(String airport);
}
