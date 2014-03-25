
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * CombatUnit Class
 *
 * @author Jason Englehart
 *
 * This class outlines the construction, storage and use of the CombatUnit
 * object. The CombatUnit object outlines the entities which the player
 * manipulates through the interface. CombatUnits interact with each other
 * through merging, dividing, ferrying and combat.
 *
 * Each object stores information regarding it's position on the map, its
 * internal status, and some aggregate information regarding the player to which
 * it belongs.
 */
public class CombatUnit extends Game {

    Player faction; // the faction(player) the unit belongs to
    int cUnitID, size, illnessRating, distanceFromCapital, timeStationary; // unit properties  
    Node previousLocation; // the previous location occupied by the unit
    Node location; // the current location of the unit
    int checkIllnessToggle = 0; // the value corresponding to a possible size decrease
    Boolean isFleet; // true if the unit is a fleet
    int supplyLevel; // supply level held by the unit

    /*
     Method: CombatUnit
     Parameters: Boolean _isFleet -> true if the unit is a naval unit or fleet
     int _cUnitID -> the unique ID of the unit
     int _size -> the size of the unit, between 0 and 15
     int _illnessRating -> the value assigned to the current health rating of the units
     Node _location -> the current location occupied by the unit
     Player _faction -> the player to which the player belongs and is controlled by
    
     This is the constructor for the CombatUnit object. With the given parameters
     the state of the unit is established.
     */
    public CombatUnit(Boolean _isFleet, int _cUnitID, int _size, int _illnessRating, Node _location, Player _faction) {
        cUnitID = _cUnitID; // establish ID
        size = _size; // get size
        illnessRating = _illnessRating; // set initial health rating
        location = _location; // set starting location
        previousLocation = _location;
        faction = _faction; // set faction affililation
        isFleet = _isFleet; // set unit status, namely if it is a fleet
        supplyLevel = 3; // establish the supply level
        timeStationary = 0; // establish the time spent stationary
        setDistanceFromCapital(); // set the distance from faction capital
    }

    /*
     Method: GetBattleStrength
     Parameters: none
    
     Returns the integer value of battle strength, the value used to determine the 
     ability of the unit to fight in the case of a battle. The value calculated here
     is used in the Battle class. This is a function of both player and unit 
     properties. 
     */
    int GetBattleStrengh() {
        int strength; // temp variable to store strength calculation

        // get the distance from capital based on node location, max at 20
        int distance = distanceFromCapital;
        if (distance > 20) {
            distance = 20;
        }

        // calculate the cumulative strength of the unit
        // strength is a function of faction political power, size, illness, and distance
        strength = (faction.politicalPower / 100) * 25 + size - (illnessRating / 11) * 20 - distance;

        JOptionPane.showMessageDialog(null, faction.playerID +"'s unit has battle strength "+ strength);
        JOptionPane.showMessageDialog(null, faction.politicalPower +" "+size+" "+illnessRating);
        
        return strength;
    }

    /*
     Method: setIllnessRating
     Parameters: none
    
     This method is called to at the end of each turn to establish the health of
     the unit. The health, or conversely the illness level, of the unit is a 
     function of time exposed to the elements.
     */
    void setIllnessRating() {
        // calculate the illness rating based on time stationary and environment
        illnessRating = timeStationary + Global.season + Global.weather;
    }

    /*
     Method: setSize
     Parameters: none
    
     This method is called at turn's end in order to determine if illness has killed
     of individuals in this unit or if individuals have deserted due to a drop in 
     political power. Changes here will be reflected in a decrease in unit size.
     */
    void setSize() {
        // calculate a sum of illness and political power decrease
        checkIllnessToggle = illnessRating + faction.getPoliticalPowerDecrease() + (5 - supplyLevel);

        // if the summation is greater than 30, size will be decremented
        if (checkIllnessToggle > 30) {
            size--;
        }
    }

    /*
     Method: setTimeStationary
     Parameters: none
    
     This method is called to at turn's end and evaluates whether the unit has
     moved to a new location. This is based on an observed change between previous
     location and current location.
     */
    void setTimeStationary() {
        // if the unit has not moved
        if (location == previousLocation) {
            // increase the time stationary, cap at 3
            timeStationary++;
            if (timeStationary > 3) {
                timeStationary = 3;
            }
        } else { // if the unit moved, time stationary is 0
            timeStationary = 0;
        }
    }

    /*
     Method: addSupplies
     Parameters: none
    
     When a player occupies a location/node with supplies, the supplies at the
     location are added to the units storage.
     */
    void addSupplies() {
        // increase the supply of the unit based on location supply value
        supplyLevel += location.suppliesAvailable;

        // cap the supply level at 5
        if (supplyLevel > 5) {
            supplyLevel = 5;
        }
        // set the location's supply to zero when all supplies are taken
        location.suppliesAvailable = 0;
    }

    /*
     Method: removeSupplies
     Parameters: none
    
     At turn's end, the value of supplies is decremented by one to indicate
     consumption.
     */
    void removeSupplies() {
        supplyLevel--;
    }

    /*
     Method: GetLocation
     Parameters: none
    
     Returns the location of the unit.
     */
    Node GetLocation() {
        return location;
    }

    /*
     Method: setDistanceFromCapital
     Parameters: none
    
     When called to, this method will read the value stored at its location
     indicating how far away from its capital the unit is.
     */
    void setDistanceFromCapital() {
        // determine the faction membership
        // obtain the correct distance from capital based on location value
        if (faction.playerID == 0) {
            distanceFromCapital = location.distanceFromCapitalRed;
        } else {
            distanceFromCapital = location.distanceFromCapitalBlue;
        }
    }
}
