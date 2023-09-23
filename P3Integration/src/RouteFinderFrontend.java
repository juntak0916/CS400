
// --== CS400 Project One File Header ==--
// Name: <Peizhe Li>
// CSL Username: <peizhe>
// Email: <pli239@wisc.edu>
// Lecture #: <Lec 001; Tuesdays and Thursdays at 9:30 a.m.>
// Notes to Grader: 
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

/**
 * Provide a text-based user interface to the searching capaibilities of the
 * Airport Path Application.
 * 
 * @author FrontendDeveloper
 */
public class RouteFinderFrontend implements RouteFinderFrontendInterfaceFD {
	private Scanner userInput;
	private BackendInterfaceBD backend;

	/**
	 * Initialize frontend to make use of a provided Scanner and Backend.
	 * 
	 * @param userInput can be used to read input from use, or to read from files
	 *                  for testing
	 * @param backend   placeholder by me, working implementation by Backend
	 *                  Developer
	 */
	public RouteFinderFrontend(Scanner userInput, BackendInterfaceBD backend) {
		this.userInput = userInput;
		this.backend = backend;
	}

	/**
	 * Helper method to display a 79 column wide row of dashes: a horizontal rule.
	 */
	private void hr() {
		System.out.println("-------------------------------------------------------------------------------");
	}

	/**
	 * This loop repeated prompts the user for commands and displays appropriate
	 * feedback for each. This continues until the user enters 'Q' to quit.
	 */
	public void runCommandLoop() {
		hr();
		System.out.println("Welcome to the Airport Route Finder App.");
		hr();

		char command = '\0';
		while (command != 'Q') {
			command = this.mainMenuPrompt();
			switch (command) {
			case 'L':
				loadDataCommand();
				break;
			case 'A':
				System.out.print("Enter the airport code to search: ");
				String airport = userInput.nextLine().trim();
				searchAirportCommand(airport);
				break;
			case 'D':
				System.out.print("Enter the start airport code: ");
				String start = userInput.nextLine().trim();
				System.out.print("Enter the end airport code: ");
				String end = userInput.nextLine().trim();
				checkDirectRouteCommand(start, end);
				break;
			case 'S':
				System.out.print("Enter the start airport code: ");
				String s = userInput.nextLine().trim();
				System.out.print("Enter the end airport code: ");
				String e = userInput.nextLine().trim();
				identifyShortestPath(s, e);
				break;
			case 'W':
				System.out.print("Enter the start airport code: ");
				String s1 = userInput.nextLine().trim();
				System.out.print("Enter the layover airport code: ");
				String mid = userInput.nextLine().trim();
				System.out.print("Enter the end airport code: ");
				String e1 = userInput.nextLine().trim();
				identifyShortestPathWithLayover(s1, mid, e1);
				break;
			case 'C':
				System.out.print("Enter the start airport code: ");
				String s2 = userInput.nextLine().trim();
				System.out.print("Enter the end airport code: ");
				String e2 = userInput.nextLine().trim();
				shortestPathCost(s2, e2);
				break;
			case 'H':
				System.out.print("Enter the airport code to check hotels: ");
				String a = userInput.nextLine().trim();
				checkAirportHotels(a);
				break;
			case 'Q':
				break;
			default:
				System.out.println(
						"Didn't recognize that command. Please type one of the letters presented within []s to identify the command you would like to choose.");
				break;
			}
		}

		hr();
		System.out.println("Thank you for using the Airport Route Finder App.");
		hr();
	}

	/**
	 * Prints the command options to System.out and reads user's choice through
	 * userInput scanner.
	 */
	public char mainMenuPrompt() {
		System.out.println("Choose a command from the list below:");
		System.out.println("    [L]oad data from file");
		System.out.println("    Search if [A]irport is in data");
		System.out.println("    Check if [D]irect route exists between two airports");
		System.out.println("    Identify [S]hortest path between two airports");
		System.out.println("    Identify shortest path between two airports [W]ith layover");
		System.out.println("    Find shortest path’s [C]ost between two airports");
		System.out.println("    Check available [H]otels near airport");
		System.out.println("[Q]uit");
		System.out.print("Choose command: ");
		String input = userInput.nextLine().trim();
		if (input.length() == 0)
			return '\0';
		return Character.toUpperCase(input.charAt(0));
	}

	/**
	 * Prompt user to enter filename, and display error message when loading fails.
	 */
	public void loadDataCommand() {
		System.out.print("Enter the name of the file to load: ");
		String filename = userInput.nextLine().trim();
		try {
			backend.loadData(filename);
		} catch (FileNotFoundException e) {
			System.out.println("Error: Could not find or load file: " + filename);
		}
	}

	/**
	 * This method gives user the ability to interactively search for the existence
	 * of a airport
	 */
	public void searchAirportCommand(String airport) {
		boolean result = backend.isAirportInData(airport);
		if (result) {
			System.out.println("Airport " + airport + " is in the data.");
		} else {
			System.out.println("Airport " + airport + " is not in the data.");
		}
	}

	/**
	 * This method gives user the ability to interactively search for the existence
	 * of a direct route between two airports
	 */
	public void checkDirectRouteCommand(String start, String end) {
		boolean result = backend.directRoute(start, end);
		if (result) {
			System.out.println("There is a direct route between " + start + " and " + end + ".");
		} else {
			System.out.println("There is no direct route between " + start + " and " + end + ".");
		}
	}

	/**
	 * This method gives user the ability to interactively search for the shortest
	 * path between two airports
	 */
	public void identifyShortestPath(String start, String end) {
		List<String> path = backend.getShortestPath(start, end);
		if (path != null) {
			System.out.println("Shortest path: " + path.toString());
		} else {
			System.out.println("No path found between " + start + " and " + end + ".");
		}
	}

	/**
	 * This method allows the user to input a layover airport and the returned
	 * shortest path has to contain that layover airport.
	 */
	public void identifyShortestPathWithLayover(String start, String mid, String end) {
		List<String> path = backend.shortestPathWithLayover(start, mid, end);
		if (path != null) {
			System.out.println("Shortest path with layover: " + path.toString());
		} else {
			System.out.println("No path found between " + start + ", " + mid + ", and " + end + ".");
		}
	}

	/**
	 * This method gives user the ability to interactively search for the cost of
	 * the shortest path between two airports
	 */
	public void shortestPathCost(String start, String end) {
		double cost = backend.cost(start, end);
		if (cost != -1) {
			System.out.println("Shortest path cost between " + start + " and " + end + ": " + cost);
		} else {
			System.out.println("No path found between " + start + " and " + end + ".");
		}
	}

	/**
	 * This method gives user the ability to interactively search for hotels near
	 * each airport.
	 */
	public void checkAirportHotels(String airport) {
		List<String> hotels = backend.getNearbyHotels(airport);
		if (hotels != null && !hotels.isEmpty()) {
			System.out.println("Hotels near " + airport + ":");
			for (String hotel : hotels) {
				System.out.println("  - " + hotel);
			}
		} else {
			System.out.println("No hotels found near " + airport + ".");
		}
	}

}
