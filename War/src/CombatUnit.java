/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Fearless Jay
 */
public class CombatUnit
{
    int cUnitID;
    int playerID;
    int size;
    int healthRating; /*OUT OF XX*/
    int distanceFromCapital; /*Possible can FUNCTION*/
    Node location;
    
    public CombatUnit(int _cUnitID, int _size, int _healthRating, int distance, int _playerID)
    {
        cUnitID = _cUnitID;
        playerID = _playerID;
        size = _size;
        healthRating = _healthRating;
        distanceFromCapital = distance;    /*probably have to get distance from node class*/
        
        
        /**/
        
    
    }
    
    int GetBattleStrengh()
    {
        int strength = 0;
        
        /*strength = size + politicalPower*/
        
        return strength;
    }
    
    
}
