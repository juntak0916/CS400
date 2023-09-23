import java.util.ArrayList;

public class AEPlaceHolderAiportNode implements AirportNodeInterfaceDW{
	
	String name;
	ArrayList<String> getNearbyHotels;
	
	public AEPlaceHolderAiportNode(String name, ArrayList<String> hotels) {
		this.name=name;
		this.getNearbyHotels=hotels;
	}
	
	@Override
	public String getAirportName() {
		return this.name;
	}

	@Override
	public ArrayList<String> getNearbyHotels() {
		return this.getNearbyHotels;
	}

}
