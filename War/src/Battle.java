
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
public class Battle {

    CombatUnit[] Red;
    CombatUnit[] Blue;

    String[] report;
    Node battleLocation;
    int turnNo;

    public int getTotalBattleStrength(CombatUnit[] cUnit) {
        int total = 0;
        for (int i = 0; i < cUnit.length; i++) {
            total += cUnit[i].GetBattleStrengh();
            /*add weather effects*/

        }
        return total;

    }
    
    /*
        addCombatUnits to consider the temporary merging of units during team combat
    */
    
    public void doBattleOnNode ()
    {
        
    }
    
    public void doBattleOnRoad (boolean preemptiveRed, boolean preemptiveBlue)
    {
        // decides how many people were engaging in combat
        if (preemptiveRed == true && preemptiveBlue == true)
        {
            // simply do battle
        }
        else if (preemptiveRed == true && preemptiveBlue == false)
        {
            // team red would waits
            // team blue makes choice to retreat or proceed to battle
        }
        else if (preemptiveRed == false && preemptiveBlue == true)
        {
            // team blue would waits
            // team red makes choice to retreat or proceed to battle
        }
        else if (preemptiveRed == false && preemptiveBlue == false)
        {
            // both teams makes choice to retreat or proceed to battle
            // do battle based on the outcome of choices
        }
        /*
        
        */
    }

    public int doBattle(CombatUnit[] red, CombatUnit[] blue) {
        if (getTotalBattleStrength(red) > getTotalBattleStrength(blue)) {
            /*red wins*/
            return 0;
        } else if (getTotalBattleStrength(red) < getTotalBattleStrength(blue)) {
            /*blue wins*/
            return 1;
        } else {
            /*tie*/
            return 2;
        }
    }

}
