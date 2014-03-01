
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
    
    public int doBattle()
    {
        if(getTotalBattleStrength(Red)< getTotalBattleStrength(Blue))
        {
            return 0;
        }
        else if(getTotalBattleStrength(Red)> getTotalBattleStrength(Blue))
        {
            return 1;
        }
        else
        {
            Random random = new Random();
            if (random.nextDouble() <= 0.01)
                if(random.nextDouble() > 0.5)
                    return 2;
                else
                    return 3;
        }
        return 4;
    }
    
    
    
}
