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

    Player faction; /**/

    int cUnitID;
    int size;
    int illnessRating; /*OUT OF XX*/

    int distanceFromCapital; /*Possible can FUNCTION*/

    int timeStationary;
    Node previousLocation;
    Node location;
    int checkToggle = 0;
    Boolean isFleet;

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
        checkToggle = illnessRating + faction.getPoliticalPowerDecrease();
        if (checkToggle > 30) {
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
}
