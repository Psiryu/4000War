/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Fearless Jay
 */
public class CombatUnit extends Game {

    Player faction; // the faction(player) the unit belongs to
    int cUnitID; // unit id
    int size; // total size of the army {0..15}
    int illnessRating; // total illness rating
    int distanceFromCapital; // distance from capital given by node value
    int timeStationary; // total number of turns the unit does not move
    Node previousLocation; // the previous location occupied by the unit
    Node location; // the current location of the unit
    int checkIllnessToggle = 0; // the value corresponding to a possible size decrease
    Boolean isFleet; // true if the unit is a fleet
    int supplyLevel; // supply level held by the unit

    // Constructor of combat units
    public CombatUnit(Boolean _isFleet, int _cUnitID, int _size, int _healthRating, Node _location, Player _faction) {
        cUnitID = _cUnitID; // establish ID
        size = _size; // get size
        illnessRating = _healthRating; // set initial health rating
        location = _location; // set starting location
        faction = _faction; // set faction affililation
        isFleet = _isFleet; // set unit status, namely if it is a fleet
        supplyLevel = 3;
    }

    // Method to obtain a combat unit's battle strength
    int GetBattleStrengh() {
        int strength; // temp variable to store strength calculation

        // get the distance from capital based on node location, max at 20
        int distance = distanceFromCapital;
        if (distance > 20){
            distance = 20;
        }
        
        // calculate the cumulative strength of the unit
        // strength is a function of faction political power, size, illness, and distance
        strength = (faction.politicalPower / 100) * 25 + size - (illnessRating / 100) * 20 - distance;
        
        return strength;
    }

    // Method to caculate the illness rating of a unit
    void setHealthRating() {
        // calculate the illness rating based on time stationary and environment
        illnessRating = timeStationary + Global.season + Global.weather;
    }

    // Method to set the size after initialization 
    void setSize() {
        // calculate a sum of illness and political power decrease
        checkIllnessToggle = illnessRating + faction.getPoliticalPowerDecrease();
        
        // if the summation is greater than 30, size will be decremented
        if (checkIllnessToggle > 30) {
            size--;
        }
    }

    // Method to set the time stationary
    void setTimeStationary() {
        // if the unit has not moved
        if (location == previousLocation) {
            // increase the time stationary, cap at 3
            timeStationary++;
            if (timeStationary > 3)
                timeStationary = 3;
        } else { // if the unit moved, time stationary is 0
            timeStationary = 0;
        }
    }
    
    // Method to resupply the unit
    void addSupplies()
    {
        // increase the supply of the unit based on location supply value
        supplyLevel += location.suppliesAvailable;
        // set the location's supply to zero when all supplies are taken
        location.suppliesAvailable = 0;
    }
    
    // Method to use supplies
    void removeSupplies()
    {
        supplyLevel--;
    }
    
    // Method to obtain the current location
    Node GetLocation()
    {
        return location;
    }
    
    // Method to establish the current distance from faction capital
    void setDistanceFromCapital()
    {
        // determine the faction membership
        // obtain the correct distance from capital based on location value
        if (faction.playerName.equals("red")) 
        {
            distanceFromCapital = location.distanceFromCapitalRed;
        }
        else
        {
            distanceFromCapital = location.distanceFromCapitalBlue;
        }
    }
}
