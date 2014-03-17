
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
public class Node
{
    int terrainHeight; /* TO BE UTILIZED*/
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
    int timeUnoccupied;
    Random random = new Random();
    
    // Constructor of the nodes
    public Node (int _id, boolean _isCapitalRed, boolean _isCapitalBlue, int _distanceFromCapitalRed, int _distanceFromCapitalBlue, String _name, int supply, boolean _isAnnexable, boolean _isPort)
    {
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
        timeUnoccupied = 0;
    }
    
    // Method to refresh supplies
    void addSupplies()
    {
        suppliesAvailable++;
        if (suppliesAvailable > 5)
            suppliesAvailable = 5;
    }
    
    // Method to remove supplies under occupation
    void removeSupplies()
    {
        suppliesAvailable = 0;
    }
}
