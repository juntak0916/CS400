import java.util.ArrayList;
import java.util.List;

public class AirportNodeBD implements AirportNodeInterfaceDW {
    private String airportName;
    private ArrayList<String> hotels;

    public AirportNodeBD(String airport){
        this.airportName = airport;
        this.hotels = new ArrayList<>();
        hotels.add("one");
    }
    @Override public String getAirportName() {
        return airportName;
    }

    @Override public ArrayList<String> getNearbyHotels() {
        return hotels;
    }
}
