
import java.util.Random;

/**
 * Node Class
 *
 * Description: This class outlines the construction and usage of the Node
 * object.
 *
 * Usage: Nodes are created in Scenario.setMapParameters() and are accessed in
 * various classes and methods in the project in order to determine map
 * parameters, facilitate unit movement and manipulation, and as a mechanism for
 * battles.
 *
 * Maintenance notes: Properties can be added to the object by adding a variable
 * and initializing it in the constructor.
 */
public class Node {

	boolean isCapitalRed; // is this location the red faction capital
	boolean isCapitalBlue; // is this location the blue faction capital
	int distanceFromCapitalRed; // distance from red faction capital
	int distanceFromCapitalBlue; // distance from blue faction capital
	String name; // the location name
	int id; // the node ID
	int suppliesAvailable; // supply level
	boolean isCity; // if the node is a city, therefor annexable
	boolean isPort; // if the node is a coastal location with a port
	int fogValue; // value assigned to location for fog of war
	Random random = new Random();

	/*
	 Method: Node
	 Input Parameters: int _id -> unique identifier for the location
	 boolean _isCapitalRed -> true if the node is the red capital
	 boolean _isCapitalBlue -> true if the node is the blue capital
	 int _distanceFromCapitalRed -> number of nodes between this location and the red capital
	 int _distanceFromCapitalBlue -> number of nodes between this location and the blue capital
	 String _name -> the name of the location
	 int supply -> the initial supply level for the location
	 boolean _isAnnexable -> indicates if the location is a city, therefore is annexable, true if so
	 boolean _isPort -> true if the location is a port
	 Output Parameters: none 

	 This is the constructor for the Node object. the object properties will 
	 be set as per the declaration variables
	 */
	public Node(int _id, boolean _isCapitalRed, boolean _isCapitalBlue, int _distanceFromCapitalRed, int _distanceFromCapitalBlue, String _name, int supply, boolean _isAnnexable, boolean _isPort) {
		// assign the values of the nodes
		id = _id;
		isCapitalRed = _isCapitalRed;
		isCapitalBlue = _isCapitalBlue;
		distanceFromCapitalRed = _distanceFromCapitalRed;
		distanceFromCapitalBlue = _distanceFromCapitalBlue;
		name = _name;
		suppliesAvailable = supply;
		isCity = _isAnnexable;
		fogValue = random.nextInt(10);
		isPort = _isPort;
	}

	/*
	 Method: addSupplies
	 Input Parameters: none
	 Output Parameters: none

	 Called at turn's end in order to simulate a restocking of supplies
	 */
	void addSupplies() {
		suppliesAvailable++;
		if (suppliesAvailable > 5) {
			suppliesAvailable = 5;
		}
	}

	/*
	 Method: removeSupplies
	 Input Parameters: none
	 Output Parameters: none

	 When a unit occupies a location, remove all the supplies from the location
	 */
	void removeSupplies() {
		suppliesAvailable = 0;
	}
}
