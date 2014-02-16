/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Fearless Jay
 */
public class Battle 
{
    CombatUnit[] Red;
    CombatUnit[] Blue;
    
    String[] report;
    Node battleLocation;
    int turnNo;
    
    public int getTotalBattleStrength(CombatUnit[] cUnit)
    {
        int total = 0;
        for (int i = 0 ; i < cUnit.length; i++)
        {
            total += cUnit[i].GetBattleStrengh();
            /*add weather effects*/
            
        }
        return total;
        
    }
    
    public void doBattle()
    {
        if(getTotalBattleStrength(Red)< getTotalBattleStrength(Blue))
        {
            /*Blue Wins*/
        }
        else if(getTotalBattleStrength(Red)> getTotalBattleStrength(Blue))
        {
            /*Red Wins*/
        }
        else
        {
            /*Tie Condition*/
        }
    }
    
    
    
}
