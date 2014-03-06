
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
