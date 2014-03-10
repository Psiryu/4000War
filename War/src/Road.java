
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Fearless Jay
 */
public class Road {

    int roadID; // road ID
    boolean isNaval; // true if the road is located as a naval route
    Node locationA; // start location of the road
    Node locationB; // end location of the road
    int capacity; // size of unit able to traverse the road, simulation of terrain
    int fogValue; // value attributed to the route for use in fog of war calculation
    Random random = new Random();

    // Constructor for the road object
    Road(int _roadID, int _capacity, Node _locationA, Node _locationB, boolean _isNaval) {
        // set each of the properties of the road
        roadID = _roadID;
        isNaval = _isNaval;
        locationA = _locationA;
        locationB = _locationB;
        capacity = _capacity;
        fogValue = random.nextInt(4);
    }

    // Method used to find out the destination NODE from FROM location
    Node GetTOLocation(Node FromLocation) {
        if (locationA == FromLocation) {
            return locationB;
        } else {
            return locationA;
        }
    }

}
