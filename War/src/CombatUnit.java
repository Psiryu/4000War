/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Fearless Jay
 */
public class CombatUnit extends Game
{
    Player faction; /**/
    int cUnitID;
    int size;
    int healthRating; /*OUT OF XX*/
    int distanceFromCapital; /*Possible can FUNCTION*/
    int timeStationary;
    Node previousLocation;
    Node location;
    
    public CombatUnit(int _cUnitID, int _size, int _healthRating, int distance, int _playerID, Player _faction)
    {
        cUnitID = _cUnitID;
        size = _size;
        healthRating = _healthRating;
        distanceFromCapital = distance;    /*probably have to get distance from node class*/
        faction = _faction;
    }
        
    int GetBattleStrengh()
    {
        int strength;        
        
        /*strength = size + politicalPower*/
        int metricOne= distanceFromCapital;
        if (distanceFromCapital >20)
        {
            metricOne = 0;
        }        
        strength = metricOne + (faction.politicalPower / 100) *10 + size - (healthRating / 100)*20;        
        return strength;
    }
    
    void setHealthRating()
    {
        healthRating = timeStationary + Global.season + Global.weather;
    }
    
    void setSize()
    {
        
    }
    
    void setTimeStationary()
    {
        timeStationary++;
        if (timeStationary > 3)
            timeStationary = 3;
    }
}
