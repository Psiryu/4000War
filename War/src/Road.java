
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Road Class
 *
 * Description: This class captures the data relevant to the Road object.
 *
 * Usage: Road objects are constructed in MapEvent.setUpMapParameters() and are
 * used throughout the project for determining the motions of units on the map.
 *
 * Maintenance notes: Parameters can be added with the existing parameters. If
 * changes to the constructor take place, then update the statement in MapEvent.
 * setUpMapParameters().
 */
public class Road {

	int roadID; // road ID
	boolean isNaval; // true if the road is located as a naval route
	Node locationA; // start location of the road
	Node locationB; // end location of the road
	int capacity; // size of unit able to traverse the road, simulation of terrain
	int fogValue; // value attributed to the route for use in fog of war calculation
	Random random = new Random(); // random number

	/*
	 Method: 
	 Input Parameters: int _roadID -> the unique identifier of the road object
	 Node _locationA -> the start location of the road
	 Node _locationB -> the end location of the road
	 boolean _isNaval -> represets if the road traverses a body of water
	 Output Parameters: none

	 This is the constructor of the Road object. Values are assigned based on 
	 the input parameters.
	 */
	Road(int _roadID, int _capacity, Node _locationA, Node _locationB, boolean _isNaval) {
		// set each of the properties of the road
		roadID = _roadID;
		isNaval = _isNaval;
		locationA = _locationA;
		locationB = _locationB;
		capacity = _capacity;
		fogValue = random.nextInt(4);
	}

	/*
	 Method: GetToLocation
	 Input Parameters: Node FromLocation -> the start location of the road
	 Output Parameters: Node location -> the end location of the road

	 This method is used to determine the end point of the road object.
	 */
	Node GetTOLocation(Node FromLocation) {
		if (locationA == FromLocation) {
			return locationB;
		} else {
			return locationA;
		}
	}

}
