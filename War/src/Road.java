/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Fearless Jay
 */
public class Road 
{
    int roadID;
    boolean isNaval;
    Node locationA;
    Node locationB;
    int capacity; 
    
    Road(int _roadID,int _capacity, Node _locationA, Node _locationB, boolean _isNaval)
    {
        roadID = _roadID;
        isNaval = _isNaval;
        locationA = _locationA;
        locationB = _locationB;
        capacity = _capacity;
    }
    
    Node GetTOLocation(Node FromLocation)    /* used to find out the destined NODE from FROM location*/
        {
            if (locationA == FromLocation)
            {
                return locationB;
            }
            else
            {
                return locationA;
            }
        }
    
}
