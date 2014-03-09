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
    Boolean isFleet; // if the unit is a fleet
    int supplyLevel; // supply level held by the unit

    public CombatUnit(Boolean _isFleet, int _cUnitID, int _size, int _healthRating, Node _location, Player _faction) {
        cUnitID = _cUnitID;
        size = _size;
        illnessRating = _healthRating;
        location = _location;
        faction = _faction;
        isFleet = _isFleet;
    }

    int GetBattleStrengh() {
        int strength;

        /*strength = size + politicalPower*/
        int metricOne = distanceFromCapital;
        if (distanceFromCapital > 20) {
            metricOne = 20;
        }
        strength = (faction.politicalPower / 100) * 25 + size - (illnessRating / 100) * 20 - metricOne;
        return strength;
    }

    void setHealthRating() {
        illnessRating = timeStationary + Global.season + Global.weather;
    }

    void setSize() {
        checkIllnessToggle = illnessRating + faction.getPoliticalPowerDecrease();
        if (checkIllnessToggle > 30) {
            size--;
        }
    }

    void setTimeStationary() {
        if (location == previousLocation) {
            timeStationary++;
            if (timeStationary > 3) {
                timeStationary = 3;
            }
        } else {
            timeStationary = 0;
        }
    }
    
    void addSupplies()
    {
        supplyLevel += location.suppliesAvailable;
        location.suppliesAvailable = 0;
    }
    
    void removeSupplies()
    {
        supplyLevel--;
    }
    
    Node GetLocation()
    {
        return location;
    }
    
    void setDistanceFromCapital()
    {
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
