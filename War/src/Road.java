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
    int timeToTransverse;
    Node locationA;
    Node locationB;
    int capacity; 
    
    
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
