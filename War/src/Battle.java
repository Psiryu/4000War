
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;


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

    // Placeholders for combat units partaking in battle
    Random randNum = new Random();

    /*
     String[] report;
     Node battleLocation;
     int turnNo;
     */
    // for the case of battle occuring on a node
    public void PVPdoCampBattleOnNode(Node node, ArrayList<CombatUnit> redCombatUnit, ArrayList<Node> redCombatUnitPreviousLocation,
            ArrayList<Node> redCombatUnitEndLocation, ArrayList<CombatUnit> blueCombatUnit,
            ArrayList<Node> blueCombatUnitPreviousLocation, ArrayList<Node> blueCombatUnitEndLocation) { /*THE DECISION MAKER FUNCTION FOR COLLISIONS ON A NODE*/

        Road redRoad, blueRoad;
        boolean redDecisionToFight = false;
        boolean blueDecisionToFight = false;
        boolean blueDefender = false;
        boolean redDefender = false;

        /***********************************************************************************************/
        /*****DEFINITIONS*******************************************************************************/
        /***********************************************************************************************/
        /*****Attackers Imply that the CombatUnit wants to fight****************************************/
        /*****Cowards Imply that they want to flee *****************************************************/
        /*****Defenders Imply that the CombatUnit (or one of them) has not moved and battle initiated **/
        /***********************************************************************************************/

        for (int i = 0; i < blueCombatUnitPreviousLocation.size(); i++) {
            if (blueCombatUnitPreviousLocation.get(i).id == blueCombatUnitEndLocation.get(i).id) {
                blueDefender = true;
            }
        }

        for (int i = 0; i < redCombatUnitPreviousLocation.size(); i++) {
            if (redCombatUnitPreviousLocation.get(i).id == redCombatUnitEndLocation.get(i).id) {
                redDefender = true;
            }
        }
        Object[] options = {"Battle", "Flee!"};

        /*POINT TO WHERE THE COLLISION IS ON THE MAP*/
        /*UI CONTROL*/
        int yesNo;

        if(IsPreemptive(node,redCombatUnit.get(0).faction.playerID))
        {
            redDecisionToFight = true;
            /*Reds decision to fight is true by default if preemptive*/
            
            JOptionPane.showMessageDialog(null, "Red Team is force to fight because his move was a pre emptive attack");
        }
        else
        {
            /*PRRRRRRRRRRRRRRRRRREEEEEEEEEEEEEEEEEEEEEMMMMMM*/
            /*you question box is here*/

            yesNo = JOptionPane.showOptionDialog(null, ("Red Team... Would you like to battle on " + node.name + " ?"), "Battle?",
                    JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
            if (yesNo == 0) /*IF FIGHT*/ {
                redDecisionToFight = true;
            } else {
                redDecisionToFight = false;
            }
        }
       
        if(IsPreemptive(node,blueCombatUnit.get(0).faction.playerID))
        {
            blueDecisionToFight = true;
            /*Reds decision to fight is true by default if preemptive*/
            
            JOptionPane.showMessageDialog(null, "Blue Team is force to fight because his move was a pre emptive attack");
        }
        else
        {
            /*PRRRRRRRRRRRRRRRRRREEEEEEEEEEEEEEEEEEEEEMMMMMM*/
            /*you question box is here*/
            yesNo = JOptionPane.showOptionDialog(null, ("Blue... Would you like to battle on " + node.name + " ?"), "Battle?",
                    JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
            
            if (yesNo == 0) /*IF FIGHT*/ {
                blueDecisionToFight = true;
            } else {
                blueDecisionToFight = false;
            }
        }

        if (blueDecisionToFight && redDecisionToFight) {
            /*Both armies commit to battle*/
            PVPCommitedBattle(redCombatUnit, blueCombatUnit, redCombatUnitEndLocation, blueCombatUnitEndLocation);
        } else if (!blueDecisionToFight && redDecisionToFight) {
            /*if Red wants to fight but blue does not*/
            PVPHalfCommitedBattleOnNode(node, redCombatUnit, blueCombatUnit, redCombatUnitPreviousLocation, redCombatUnitEndLocation, blueCombatUnitPreviousLocation);
        } else if (blueDecisionToFight && !redDecisionToFight) {
            /*if Blue wants to fight but Red does not*/
            PVPHalfCommitedBattleOnNode(node, blueCombatUnit, redCombatUnit, blueCombatUnitPreviousLocation, blueCombatUnitEndLocation, redCombatUnitPreviousLocation);

            //PVPCommitedBattle(node, red, blue);
        } else {
            /*When No one wants to fight*/

            PVPNonCommitedBattle(redCombatUnit, blueCombatUnit, redCombatUnitEndLocation,redCombatUnitPreviousLocation, blueCombatUnitPreviousLocation,blueCombatUnitPreviousLocation);
            //PVPNonCommitedBattle(node,red,blue);

        }
    }

    public void PVPCommitedBattle(ArrayList<CombatUnit> red, ArrayList<CombatUnit> blue, ArrayList<Node> redsIntendedNode,
            ArrayList<Node> blueIntendedNode) {
        /*STANDARD BATTLE*/
        /*WINNER MOVES TO HIS DESIRED LOCATION*/
        
        int yesNo;
        int redAggregateStrength = 0;
        int blueAggregateStrength = 0;

        /*GET BATTLE STRENGTH for  red*/
        for (int i = 0; i < red.size(); i++) 
        {
            if (!red.get(i).isFleet)
            {
                redAggregateStrength += red.get(i).GetBattleStrengh();
            }
                else
            {
                redAggregateStrength += (int)(red.get(i).GetBattleStrengh()*0.85);
            }
        }
        /*GET BATTLE STRENGTH for Cowards*/
        for (int i = 0; i < blue.size(); i++) 
        {
            if (!blue.get(i).isFleet)
            {
                blueAggregateStrength += blue.get(i).GetBattleStrengh();
            }
                else
            {
                blueAggregateStrength += (int)(blue.get(i).GetBattleStrengh()*0.85);
            }
        }

        if (redAggregateStrength < blueAggregateStrength) {
            /*Blue wins and moves to their desired location*/

            // remove all of the losing red units from the player's roster
            if (Scenario.redPlayer.playerID == red.get(0).faction.playerID) {
                for (int i = 0; i < red.size(); i++) {
                    Scenario.redPlayer.combatUnits.remove(red.get(i));
                }
            } else {
                for (int i = 0; i < red.size(); i++) {
                    Scenario.bluePlayer.combatUnits.remove(red.get(i));
                }
            }

            // move the winning units to their desired location
            for (int i = 0; i < blue.size(); i++) {
                MapEvent.addMovement(blue.get(i).cUnitID, null, blueIntendedNode.get(i).id);
            }
            
            JOptionPane.showMessageDialog(null, "Blue wins and moves to their desired location"
            + "Red Battle Strength: " + redAggregateStrength + " Blue Battle Strength: "
                        + blueAggregateStrength);
            
        } 
        else if (redAggregateStrength > blueAggregateStrength) 
        {
            /*Red wins and moves to their desired location*/
            if (Scenario.bluePlayer.playerID == blue.get(0).faction.playerID) {
                for (int i = 0; i < blue.size(); i++) {
                    Scenario.bluePlayer.combatUnits.remove(blue.get(i));
                }
            } else {
                for (int i = 0; i < blue.size(); i++) {
                    Scenario.redPlayer.combatUnits.remove(blue.get(i));
                }
            }
            for (int i = 0; i < red.size(); i++) {
                MapEvent.addMovement(red.get(i).cUnitID, null, redsIntendedNode.get(i).id);
            }
           JOptionPane.showMessageDialog(null, "Red wins and moves to their desired location"
           + "Red Battle Strength: " + redAggregateStrength + " Blue Battle Strength: "
                        + blueAggregateStrength);

        } else {
            if (randNum.nextDouble() > 0.5) {
                /*Red wins and moves to their desired location*/
                if (Scenario.bluePlayer.playerID == blue.get(0).faction.playerID) {
                    for (int i = 0; i < blue.size(); i++) {
                        Scenario.bluePlayer.combatUnits.remove(blue.get(i));
                    }
                } else {
                    for (int i = 0; i < blue.size(); i++) {
                        Scenario.redPlayer.combatUnits.remove(blue.get(i));
                    }
                }
                for (int i = 0; i < red.size(); i++) {
                    MapEvent.addMovement(red.get(i).cUnitID, null, redsIntendedNode.get(i).id);
                }
           JOptionPane.showMessageDialog(null, "Red wins and moves to their desired location"
           + "Red Battle Strength: " + redAggregateStrength + " Blue Battle Strength: "
                        + blueAggregateStrength);

            } else {
                /*Blue wins and moves to their desired location*/
                if (Scenario.redPlayer.playerID == red.get(0).faction.playerID) {
                    for (int i = 0; i < red.size(); i++) {
                        Scenario.redPlayer.combatUnits.remove(red.get(i));
                    }
                } else {
                    for (int i = 0; i < red.size(); i++) {
                        Scenario.bluePlayer.combatUnits.remove(red.get(i));
                    }
                }

                // move the winning units to their desired location
                for (int i = 0; i < blue.size(); i++) {
                    MapEvent.addMovement(blue.get(i).cUnitID, null, blueIntendedNode.get(i).id);
                }
            }
            JOptionPane.showMessageDialog(null, "Blue wins and moves to their desired location\n"
            + "Red Battle Strength: " + redAggregateStrength + " Blue Battle Strength: "
                        + blueAggregateStrength);

        }

    }

    public void PVPHalfCommitedBattleOnNode(Node node, ArrayList<CombatUnit> attackers, ArrayList<CombatUnit> cowards, ArrayList<Node> attackerPreviousLocation, ArrayList<Node> attackerIntendedLocation, ArrayList<Node> cowardsPreviousLocation) {
        int retreatLocationCount = 0;
        Road[] fleeingRoads = new Road[100];
        Node[] fleeingNodes = new Node[100];

        String isCowards;
        String isAttackers;

        boolean isCowardDefender = false;
        boolean isAttackerDefender = false;

        int aggregateAttackersBattleStrength = 0;
        int aggregateCowardsBattleStrength = 0;

        if (cowards.get(0).faction.playerID == 0) {
            isCowards = "red";
            isAttackers = "blue";
        } else {
            isCowards = "blue";
            isAttackers = "red";
        }

        /*GET BATTLE STRENGTH for  Attackers*/
        for (int i = 0; i < attackers.size(); i++) 
        {
            if (!attackers.get(i).isFleet)
            {
                aggregateAttackersBattleStrength += attackers.get(i).GetBattleStrengh();
            }
                else
            {
                aggregateAttackersBattleStrength += (int)(attackers.get(i).GetBattleStrengh()*0.85);
            }
        }
        /*GET BATTLE STRENGTH for Cowards*/
        for (int i = 0; i < cowards.size(); i++) 
        {
            if (!attackers.get(i).isFleet)
            {
                aggregateCowardsBattleStrength += cowards.get(i).GetBattleStrengh();
            }
                else
            {
                aggregateCowardsBattleStrength += (int)(cowards.get(i).GetBattleStrengh()*0.85);
            }
        }

        aggregateCowardsBattleStrength = (int) ((double) (aggregateCowardsBattleStrength) * 0.8);

        /*We have to determine is the Cowards is a defender*/
        for (int i = 0; i < cowards.size(); i++) 
        {
            if (cowardsPreviousLocation.get(i).id == node.id) {
                isCowardDefender = true;
            }

        }

        for (int i = 0; i < attackers.size(); i++) 
        {
            if (attackerPreviousLocation.get(i).id == node.id) {
                isAttackerDefender = true;
            }

        }

        /*CASE 1 : No defenders*/
        if (!isCowardDefender && !isAttackerDefender) 
        {

            if (randNum.nextDouble() > 0.75) /*25% chance they can flee with no battle*/ 
            {
                /*COWARDS FLEE TO PREVIOUS LOCATION WITH NO BATTLB*/

                /*COWARDS FLEE TO PREVIOUS POSITION*/
                for (int i = 0; i < cowards.size(); i++) 
                {
                    MapEvent.addMovement(cowards.get(i).cUnitID, Scenario.findRoad(node, cowardsPreviousLocation.get(i)), cowardsPreviousLocation.get(i).id);
                }

                /*Attackers Move into intended position*/
                for (int i = 0; i < attackers.size(); i++) 
                {
                    MapEvent.addMovement(attackers.get(i).cUnitID, null, attackerIntendedLocation.get(i).id);
                }
                
                if (isCowards == "red")
                {
                    JOptionPane.showMessageDialog(null, "Blue flees battle while Red moves into their intended postion\n"
                            + "Red Strength: " + aggregateCowardsBattleStrength + " Blue Strength:" + attackerPreviousLocation);
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Red flees battle while Blue moves into their intended postion\n"
                            + "Blue Strength: " + aggregateCowardsBattleStrength + " Red Strength:" + attackerPreviousLocation);
                }

            } else {
                if (aggregateCowardsBattleStrength > aggregateAttackersBattleStrength) { /*attackers fall in battle*/
                    /*COWARDS WIN and end up heading towards their intened position*/
                    if (isCowards == "red") {
                        /*if cowards is red then blue loses their armies*/
                        if (Scenario.bluePlayer.playerID == attackers.get(0).faction.playerID) {
                            for (int i = 0; i < attackers.size(); i++) {
                                Scenario.bluePlayer.combatUnits.remove(attackers.get(i));
                            }
                        } else {
                            for (int i = 0; i < attackers.size(); i++) {
                                Scenario.redPlayer.combatUnits.remove(attackers.get(i));
                            }
                        }
                    JOptionPane.showMessageDialog(null, "Blue loses battle!! Their armies fall and Red moves into intended position\n"
                            + "Red Strength: " + aggregateCowardsBattleStrength + " Blue Strength:" + attackerPreviousLocation);

                    }
                    else
                    { /*Cowards is blue for the case that cowards win*/ 

                        /*if cowards is blue then red loses their armies*/
                        if (Scenario.redPlayer.playerID == attackers.get(0).faction.playerID) 
                        {
                            for (int i = 0; i < attackers.size(); i++) 
                            {
                                Scenario.redPlayer.combatUnits.remove(attackers.get(i));
                            }
                        } 
                        else 
                        {
                            for (int i = 0; i < attackers.size(); i++) 
                            {
                                Scenario.bluePlayer.combatUnits.remove(attackers.get(i));
                            }
                        }
                    JOptionPane.showMessageDialog(null, "Red loses battle!! Their armies fall and Blue moves into intended position\n"
                            + "Blue Strength: " + aggregateCowardsBattleStrength + " Red Strength:" + attackerPreviousLocation);

                    }

                    /*Move Cowards into desired location*/
                    for (int i = 0; i < cowards.size(); i++) 
                    {
                        MapEvent.addMovement(cowards.get(i).cUnitID, null, cowardsPreviousLocation.get(i).id);
                    }



                } 
                else if (aggregateCowardsBattleStrength < aggregateAttackersBattleStrength) 
                {
                    /*ATTACKERS WIN AT TAKE POSITION*/

                    if(isCowards == "red")
                    {
                        /*Blue Team wins Red team Cowards armies will die*/
                        if (Scenario.redPlayer.playerID == cowards.get(0).faction.playerID) 
                        {
                            for (int i = 0; i < cowards.size(); i++) 
                            {
                                Scenario.redPlayer.combatUnits.remove(cowards.get(i));
                            }
                        } 
                        else 
                        {
                            for (int i = 0; i < cowards.size(); i++) 
                            {
                                Scenario.bluePlayer.combatUnits.remove(cowards.get(i));
                            }
                        }
                    JOptionPane.showMessageDialog(null, "Red is unable to Flee!! Blue loses battle!!\n"
                            + " Their armies fall and Red moves into intended position\n"
                            + "Red Strength: " + aggregateCowardsBattleStrength + " Blue Strength:" + attackerPreviousLocation);

                    }
                    else /*Red Team wins Blue team Cowards armies will die*/
                    {
                        if (Scenario.bluePlayer.playerID == cowards.get(0).faction.playerID) 
                        {
                            for (int i = 0; i < cowards.size(); i++) 
                            {
                                Scenario.bluePlayer.combatUnits.remove(cowards.get(i));
                            }
                        } 
                        else 
                        {
                            for (int i = 0; i < cowards.size(); i++) 
                            {
                                Scenario.redPlayer.combatUnits.remove(cowards.get(i));
                            }
                        }
                    JOptionPane.showMessageDialog(null, "Blue is unable to Flee!! Red loses battle!!\n"
                            + " Their armies fall and Blue moves into intended position\n"
                            + "Blue Strength: " + aggregateCowardsBattleStrength + " Red Strength:" + attackerPreviousLocation);

                    }

                    /*Attackers move into their position*/

                    for (int i = 0; i < attackers.size(); i++) 
                    {
                        MapEvent.addMovement(attackers.get(i).cUnitID, Scenario.findRoad(node, attackerPreviousLocation.get(i)), node.id);
                    }



                } 
                else  /*****IF THE BATTLE STRENGTH IS TIED*****/
                {
                    if (randNum.nextDouble() > .5) 
                    {
                        /*ATTACKERS WIN AT TAKE POSITION*/

                        if(isCowards == "red")
                        {
                            /*Red team (Cowards) armies will die and Blue will take position*/
                            if (Scenario.redPlayer.playerID == cowards.get(0).faction.playerID) 
                            {
                                for (int i = 0; i < cowards.size(); i++) 
                                {
                                    Scenario.redPlayer.combatUnits.remove(cowards.get(i));
                                }
                            } 
                            else 
                            {
                                for (int i = 0; i < cowards.size(); i++) 
                                {
                                    Scenario.bluePlayer.combatUnits.remove(cowards.get(i));
                                }
                            }
                            JOptionPane.showMessageDialog(null, "Red is unable to Flee!! Blue loses battle!!\n"
                                    + "Their armies fall and Red moves into intended position\n"
                                    + "Red Strength: " + aggregateCowardsBattleStrength + " Blue Strength:" + attackerPreviousLocation);

                        }
                        else
                        {
                            if (Scenario.bluePlayer.playerID == cowards.get(0).faction.playerID) 
                            {
                                for (int i = 0; i < cowards.size(); i++) 
                                {
                                    Scenario.bluePlayer.combatUnits.remove(cowards.get(i));
                                }
                            } 
                            else 
                            {
                                for (int i = 0; i < cowards.size(); i++) 
                                {
                                    Scenario.redPlayer.combatUnits.remove(cowards.get(i));
                                }
                            }
                            JOptionPane.showMessageDialog(null, "Blue is unable to Flee!! Red loses battle!!\n"
                                    + " Their armies fall and Blue moves into intended position\n"
                                    + "Blue Strength: " + aggregateCowardsBattleStrength + " Red Strength:" + attackerPreviousLocation);

                        }

                        /*Attackers move into their position*/

                        for (int i = 0; i < attackers.size(); i++) 
                        {
                            MapEvent.addMovement(attackers.get(i).cUnitID, Scenario.findRoad(node, attackerPreviousLocation.get(i)), node.id);
                        }   
                    } 
                    else 
                    {
                        /*COWARDS WIN and end up heading towards their intened position*/
                        if (isCowards == "red") 
                        {
                            /*if cowards is red then blue loses their armies*/
                            if (Scenario.bluePlayer.playerID == attackers.get(0).faction.playerID) 
                            {
                                for (int i = 0; i < attackers.size(); i++) 
                                {
                                    Scenario.bluePlayer.combatUnits.remove(attackers.get(i));
                                }
                            } 
                            else 
                            {
                                for (int i = 0; i < attackers.size(); i++) 
                                {
                                    Scenario.redPlayer.combatUnits.remove(attackers.get(i));
                                }
                            }
                            JOptionPane.showMessageDialog(null, "Blue loses battle!!\n"
                                    + "Their armies fall and Red moves into intended position\n"
                                    + "Red Strength: " + aggregateCowardsBattleStrength + " Blue Strength:" + attackerPreviousLocation);

                            /*Red (cowards) Moves into position*/
                            for (int i = 0; i < cowards.size(); i++) 
                            {
                                MapEvent.addMovement(cowards.get(i).cUnitID, Scenario.findRoad(node, cowardsPreviousLocation.get(i)), node.id);
                            } 

                        }
                        else
                        { /*Cowards is blue*/ 

                            /*if cowards is blue then red loses their armies*/
                            if (Scenario.redPlayer.playerID == attackers.get(0).faction.playerID) 
                            {
                                for (int i = 0; i < attackers.size(); i++) 
                                {
                                    Scenario.redPlayer.combatUnits.remove(attackers.get(i));
                                }
                            } 
                            else 
                            {
                                for (int i = 0; i < attackers.size(); i++) 
                                {
                                    Scenario.bluePlayer.combatUnits.remove(attackers.get(i));
                                }
                            }
                            JOptionPane.showMessageDialog(null, "Red loses battle!!\n"
                                    + "Their armies fall and Blue moves into intended position\n"
                                    + "Blue Strength: " + aggregateCowardsBattleStrength + " Red Strength:" + attackerPreviousLocation);
                            /*Blue (cowards) Moves into position*/
                            for (int i = 0; i < cowards.size(); i++) 
                            {
                                MapEvent.addMovement(cowards.get(i).cUnitID, Scenario.findRoad(node, cowardsPreviousLocation.get(i)), node.id);
                            } 
                        }
                    }
                }
            }
        } 
        /*CASE 2 Cowards are defenders*/ 
        else if (!isAttackerDefender && isCowardDefender) 
        {
            if (randNum.nextDouble() > 0.25) /*75% chance they can  NOT flee */ 
            {
                if (aggregateCowardsBattleStrength > aggregateAttackersBattleStrength) 
                {
                    /*COWARDS WIN and end up heading towards their intened position THE SAME POSITION*/
                    /*So no Movement..... easy*/

                    if(isCowards == "red")
                    {
                        /*Attacker Blue dies*/
                        if (Scenario.bluePlayer.playerID == attackers.get(0).faction.playerID) 
                            {
                                for (int i = 0; i < attackers.size(); i++) 
                                {
                                    Scenario.bluePlayer.combatUnits.remove(attackers.get(i));
                                }
                            } 
                            else 
                            {
                                for (int i = 0; i < attackers.size(); i++) 
                                {
                                    Scenario.redPlayer.combatUnits.remove(attackers.get(i));
                                }
                            }
                    JOptionPane.showMessageDialog(null, "Blue loses battle!!\n"
                            + "Their armies fall and Red maintains their position\n"
                            + "Red Strength: " + aggregateCowardsBattleStrength + " Blue Strength:" + attackerPreviousLocation);

                    }
                    else
                    {
                        /*Attcker Red Dies*/
                        if (Scenario.redPlayer.playerID == attackers.get(0).faction.playerID) 
                        {
                            for (int i = 0; i < attackers.size(); i++) 
                            {
                                Scenario.redPlayer.combatUnits.remove(attackers.get(i));
                            }
                        } 
                        else 
                        {
                            for (int i = 0; i < attackers.size(); i++) 
                            {
                                Scenario.bluePlayer.combatUnits.remove(attackers.get(i));
                            }
                        }
                    JOptionPane.showMessageDialog(null, "Red loses battle!!\n"
                            + "Their armies fall and Blue maintains their position\n"
                            + "Blue Strength: " + aggregateCowardsBattleStrength + " Red Strength:" + attackerPreviousLocation);

                    }




                } 
                else if (aggregateCowardsBattleStrength < aggregateAttackersBattleStrength) 
                {
                    /*Cowards are defenders*/
                    /*ATTACKERS WIN AT TAKE POSITION*/
                    if(isCowards == "red")
                    {       
                        /*Red Combat Unit will fall and blue will move into position*/
                        /*Coward Red Dies*/
                        if (Scenario.redPlayer.playerID == cowards.get(0).faction.playerID) 
                        {
                            for (int i = 0; i < cowards.size(); i++) 
                            {
                                Scenario.redPlayer.combatUnits.remove(cowards.get(i));
                            }
                        } 
                        else 
                        {
                            for (int i = 0; i < cowards.size(); i++) 
                            {
                                Scenario.bluePlayer.combatUnits.remove(cowards.get(i));
                            }
                        }
                        
                        /* Blue (attackers) moves into postion */
                        
                        for (int i = 0; i < attackers.size(); i++) 
                            {
                                MapEvent.addMovement(attackers.get(i).cUnitID, Scenario.findRoad(node, attackerPreviousLocation.get(i)), node.id);
                            } 

                        JOptionPane.showMessageDialog(null, "Red is unable to flee!! Blue loses battle!!\n"
                                + "Their armies fall and Blue maintains their position\n"
                                + "Red Strength: " + aggregateCowardsBattleStrength + " Blue Strength:" + attackerPreviousLocation);

                    
                    }
                    else
                    {
                        /*Blue Combat Unit will fall and Red will move into position*/
                        /*Coward Blue Dies*/
                        if (Scenario.bluePlayer.playerID == cowards.get(0).faction.playerID) 
                        {
                            for (int i = 0; i < cowards.size(); i++) 
                            {
                                Scenario.bluePlayer.combatUnits.remove(cowards.get(i));
                            }
                        } 
                        else 
                        {
                            for (int i = 0; i < cowards.size(); i++) 
                            {
                                Scenario.redPlayer.combatUnits.remove(cowards.get(i));
                            }
                        }
                        
                        /* Red (attackers) moves into postion */
                        
                        for (int i = 0; i < attackers.size(); i++) 
                            {
                                MapEvent.addMovement(attackers.get(i).cUnitID, Scenario.findRoad(node, attackerPreviousLocation.get(i)), node.id);
                            } 
                        
                        JOptionPane.showMessageDialog(null, "Blue is unable to flee!! Red loses battle!!\n"
                                + "Their armies fall and Blue maintains their position\n"
                                + "Blue Strength: " + aggregateCowardsBattleStrength + " Red Strength:" + attackerPreviousLocation);
                    }
  
                } else {
                    if (randNum.nextDouble() > .5) {
                        /*ATTACKERS WIN AT TAKE POSITION*/
                        if(isCowards == "red")
                        {       
                            /*Red Combat Unit will fall and blue will move into position*/
                            /*Coward Red Dies*/
                            if (Scenario.redPlayer.playerID == cowards.get(0).faction.playerID) 
                            {
                                for (int i = 0; i < cowards.size(); i++) 
                                {
                                    Scenario.redPlayer.combatUnits.remove(cowards.get(i));
                                }
                            } 
                            else 
                            {
                                for (int i = 0; i < cowards.size(); i++) 
                                {
                                    Scenario.bluePlayer.combatUnits.remove(cowards.get(i));
                                }
                            }

                            /* Blue (attackers) moves into postion */

                            for (int i = 0; i < attackers.size(); i++) 
                                {
                                    MapEvent.addMovement(attackers.get(i).cUnitID, Scenario.findRoad(node, attackerPreviousLocation.get(i)), node.id);
                                } 

                            JOptionPane.showMessageDialog(null, "Red is unable to flee!! Blue loses battle!!"
                                    + "Their armies fall and Blue maintains their position\n"
                                    + "Red Strength: " + aggregateCowardsBattleStrength + " Blue Strength:" + attackerPreviousLocation);


                        }
                        else
                        {
                            /*Blue Combat Unit will fall and Red will move into position*/
                            /*Coward Blue Dies*/
                            if (Scenario.bluePlayer.playerID == cowards.get(0).faction.playerID) 
                            {
                                for (int i = 0; i < cowards.size(); i++) 
                                {
                                    Scenario.bluePlayer.combatUnits.remove(cowards.get(i));
                                }
                            } 
                            else 
                            {
                                for (int i = 0; i < cowards.size(); i++) 
                                {
                                    Scenario.redPlayer.combatUnits.remove(cowards.get(i));
                                }
                            }

                            /* Red (attackers) moves into postion */

                            for (int i = 0; i < attackers.size(); i++) 
                            {
                                MapEvent.addMovement(attackers.get(i).cUnitID, Scenario.findRoad(node, attackerPreviousLocation.get(i)), node.id);
                            } 

                            JOptionPane.showMessageDialog(null, "Blue is unable to flee!! Red loses battle!!\n"
                                    + "Their armies fall and Blue maintains their position\n"
                                    + "Blue Strength: " + aggregateCowardsBattleStrength + " Red Strength:" + attackerPreviousLocation);
                        }
                        
                        
                    } 
                    else 
                    {
                        /*COWARDS WIN and end up heading towards their intened position THE SAME POSITION*/
                        /*So no Movement..... easy*/

                        if(isCowards == "red")
                        {
                            /*Attacker Blue dies*/
                            if (Scenario.bluePlayer.playerID == attackers.get(0).faction.playerID) 
                            {
                                for (int i = 0; i < attackers.size(); i++) 
                                {
                                    Scenario.bluePlayer.combatUnits.remove(attackers.get(i));
                                }
                            } 
                            else 
                            {
                                for (int i = 0; i < attackers.size(); i++) 
                                {
                                    Scenario.redPlayer.combatUnits.remove(attackers.get(i));
                                }
                            }
                            JOptionPane.showMessageDialog(null, "Blue loses battle!!\n"
                                    + "Their armies fall and Red maintains their position\n"
                                    + "Red Strength: " + aggregateCowardsBattleStrength + " Blue Strength:" + attackerPreviousLocation);

                        }
                        else
                        {
                            /*Attcker Red Dies*/
                            if (Scenario.redPlayer.playerID == attackers.get(0).faction.playerID) 
                            {
                                for (int i = 0; i < attackers.size(); i++) 
                                {
                                    Scenario.redPlayer.combatUnits.remove(attackers.get(i));
                                }
                            } 
                            else 
                            {
                                for (int i = 0; i < attackers.size(); i++) 
                                {
                                    Scenario.bluePlayer.combatUnits.remove(attackers.get(i));
                                }
                            }
                            JOptionPane.showMessageDialog(null, "Red loses battle!!\n"
                                    + " Their armies fall and Blue maintains their position\n"
                                    + "Blue Strength: " + aggregateCowardsBattleStrength + " Red Strength:" + attackerPreviousLocation);
                        }
                    }
                }
            } 
            else
            { 
                /* THEY CAN FLEE*/
                
                ArrayList<Road> adjacentRoadsToNode = new ArrayList<Road>();
                int maxCowardsSize = 0;

                for (int i = 0; i < cowards.size(); i++) {
                    if (maxCowardsSize < cowards.get(i).size) {
                        maxCowardsSize = cowards.get(i).size;
                    }
                }

                for (int i = 0; i < Scenario.listOfRoads.length; i++) {
                    if (Scenario.listOfRoads[i].locationA.id == node.id || Scenario.listOfRoads[i].locationB.id == node.id) {
                        if (maxCowardsSize <= Scenario.listOfRoads[i].capacity) {
                            adjacentRoadsToNode.add(Scenario.listOfRoads[i]);

                        }

                    }
                }
                if (adjacentRoadsToNode.size() == 0) {
                    /*THEY DIE*/
                    
                    if (isCowards == "red")
                    {
                        if (Scenario.redPlayer.playerID == cowards.get(0).faction.playerID) 
                        {
                            for (int i = 0; i < cowards.size(); i++) 
                            {
                                Scenario.redPlayer.combatUnits.remove(cowards.get(i));
                            }
                        } 
                        else 
                        {
                            for (int i = 0; i < cowards.size(); i++) 
                            {
                                Scenario.bluePlayer.combatUnits.remove(cowards.get(i));
                            }
                        }
                        JOptionPane.showMessageDialog(null, "Red are unable flees from their position therefore their unit is defeated!!!"+
                                "\nBlue proceeds to intended postion\n"
                                + "Red Strength: " + aggregateCowardsBattleStrength + " Blue Strength:" + attackerPreviousLocation);
                    }
                    else
                    {   
                        /*Coward is blue and they die*/
                        if (Scenario.bluePlayer.playerID == cowards.get(0).faction.playerID) 
                        {
                            for (int i = 0; i < cowards.size(); i++) 
                            {
                                Scenario.bluePlayer.combatUnits.remove(cowards.get(i));
                            }
                        } 
                        else 
                        {
                            for (int i = 0; i < cowards.size(); i++) 
                            {
                                Scenario.redPlayer.combatUnits.remove(cowards.get(i));
                            }
                        }
                       JOptionPane.showMessageDialog(null, "Blue are unable flees from their position therefore their unit is defeated!!!"+
                                "\nRed proceeds to intended postion\n"
                               + "Blue Strength: " + aggregateCowardsBattleStrength + " Red Strength:" + attackerPreviousLocation);

                    }
                    
                    
                    
                } else {
                    /*RANDOMLY CHOOSES A ROAD TO TRAVEL*/
                    int roadChoice = randNum.nextInt(adjacentRoadsToNode.size());
                     
                    /*Move ATTACKERS to intended node and Cowards to Random Node*/
                    Node randomRoadDestination;
                    if(adjacentRoadsToNode.get(roadChoice).locationA.id == node.id)
                    {
                        randomRoadDestination = adjacentRoadsToNode.get(roadChoice).locationB;
                    }
                    else
                    {
                        randomRoadDestination = adjacentRoadsToNode.get(roadChoice).locationA;
                    }
                    
                   
                    /* COWARDS move to random spot*/
                    /* no team specification required*/
                    
                    for (int i = 0; i < cowards.size(); i++) 
                    {
                        MapEvent.addMovement(cowards.get(i).cUnitID, adjacentRoadsToNode.get(roadChoice) , randomRoadDestination.id);
                    } 
                    
                    if (isCowards == "red")
                    {
                        JOptionPane.showMessageDialog(null, "Red flees from their position!!!\n"+
                        "\nBlue proceeds to intended postion and no battle is taken place\n"
                        + "Red Strength: " + aggregateCowardsBattleStrength + " Blue Strength:" + attackerPreviousLocation);

                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Blue flees from their position!!!\n"+
                        "\nRed proceeds to intended postion and no battle is taken place\n"
                        + "Blue Strength: " + aggregateCowardsBattleStrength + " Red Strength:" + attackerPreviousLocation);
                    }
                    
                
                }
            }
        } /*CASE 3: Attackers are defenders (attackers did not move but have commited to battle)*/ 
        else 
        {
            if (randNum.nextDouble() > 0.25) /*Cowards are not able to flee*/ {
                if (aggregateCowardsBattleStrength > aggregateAttackersBattleStrength) 
                {
                    /*COWARDS WIN and end up heading towards their intened position*/
                    if (isCowards == "red")
                    {
                        /*RED (Cowards) UNIT WINS They Proceed their intended spot*/
                        
                        /*blue dies*/
                        if (Scenario.bluePlayer.playerID == attackers.get(0).faction.playerID) 
                            {
                                for (int i = 0; i < attackers.size(); i++) 
                                {
                                    Scenario.bluePlayer.combatUnits.remove(attackers.get(i));
                                }
                            } 
                            else 
                            {
                                for (int i = 0; i < attackers.size(); i++) 
                                {
                                    Scenario.redPlayer.combatUnits.remove(attackers.get(i));
                                }
                            }

                        JOptionPane.showMessageDialog(null, "Red Combat Unit defeat Blue Combat Unit!!!\n"+
                        "\nRed will move to thier intended position\n"
                        + "Red Strength: " + aggregateCowardsBattleStrength + " Blue Strength:" + attackerPreviousLocation);
                        
                        
                    }
                    else 
                    {
                        /*Blue (Cowards) UNIT WINS They Proceed their intended spot*/
                        /*Red dies*/
                        if (Scenario.redPlayer.playerID == attackers.get(0).faction.playerID) 
                            {
                                for (int i = 0; i < attackers.size(); i++) 
                                {
                                    Scenario.redPlayer.combatUnits.remove(attackers.get(i));
                                }
                            } 
                            else 
                            {
                                for (int i = 0; i < attackers.size(); i++) 
                                {
                                    Scenario.bluePlayer.combatUnits.remove(attackers.get(i));
                                }
                            }
                        
                    }
                    
                    JOptionPane.showMessageDialog(null, "Blue Combat Unit defeat Red Combat !!!\n"+
                    "\nBlue will move to thier intended position\n"
                    + "Blue Strength: " + aggregateCowardsBattleStrength + " Red Strength:" + attackerPreviousLocation);
                    
                    
                    /* Cowards will move to their intended location (Not team specific) */
                    for (int i = 0; i < cowards.size(); i++) 
                    {
                        MapEvent.addMovement(cowards.get(i).cUnitID, null , node.id);
                    }
                    
                    
                    
                    
                    
                } 
                else if (aggregateCowardsBattleStrength < aggregateAttackersBattleStrength) 
                {
                    /*ATTACKERS WIN AND REMAIN AT THEIR POSITION*/
                    if(isCowards == "red")
                    {
                        /*Blue Wins and remains at their postion*/
                        /*Red Combat Unit dies*/
                        
                        if (Scenario.redPlayer.playerID == cowards.get(0).faction.playerID) 
                        {
                            for (int i = 0; i < cowards.size(); i++) 
                            {
                                Scenario.redPlayer.combatUnits.remove(cowards.get(i));
                            }
                        } 
                        else 
                        {
                            for (int i = 0; i < cowards.size(); i++) 
                            {
                                Scenario.bluePlayer.combatUnits.remove(cowards.get(i));
                            }
                        }
                        
                    JOptionPane.showMessageDialog(null, "Blue Combat Unit defends their position and defeat Red!!!"+
                    "\nBlue remain at their original position\n"
                    + "Red Strength: " + aggregateCowardsBattleStrength + " Blue Strength:" + attackerPreviousLocation);  
 
                    }
                    else
                    { 
                        /*Red Wins and remains at their postion*/
                        /*Blue Combat Unit dies*/
                        if (Scenario.bluePlayer.playerID == cowards.get(0).faction.playerID) 
                        {
                            for (int i = 0; i < cowards.size(); i++) 
                            {
                                Scenario.bluePlayer.combatUnits.remove(cowards.get(i));
                            }
                        } 
                        else 
                        {
                            for (int i = 0; i < cowards.size(); i++) 
                            {
                                Scenario.redPlayer.combatUnits.remove(cowards.get(i));
                            }
                        }
                        JOptionPane.showMessageDialog(null, "Red Combat Unit defends their position and defeat Blue!!!\n"+
                        "\nRed remain at their original position\n"
                        + "Blue Strength: " + aggregateCowardsBattleStrength + " Red Strength:" + attackerPreviousLocation);
                    
                    }
                } 
                else 
                {
                    if (randNum.nextDouble() > .5) 
                    {
                        
                            /*ATTACKERS WIN AND REMAIN AT THEIR POSITION*/
                        if(isCowards == "red")
                        {
                            /*Blue Wins and remains at their postion*/
                            /*Red Combat Unit dies*/

                            if (Scenario.redPlayer.playerID == cowards.get(0).faction.playerID) 
                            {
                                for (int i = 0; i < cowards.size(); i++) 
                                {
                                    Scenario.redPlayer.combatUnits.remove(cowards.get(i));
                                }
                            } 
                            else 
                            {
                                for (int i = 0; i < cowards.size(); i++) 
                                {
                                    Scenario.bluePlayer.combatUnits.remove(cowards.get(i));
                                }
                            }

                        JOptionPane.showMessageDialog(null, "Blue Combat Unit defends their position and defeat Red!!!\n"+
                        "\nBlue remain at their original position\n"
                        + "Red Strength: " + aggregateCowardsBattleStrength + " Blue Strength:" + attackerPreviousLocation);  

                        }
                        else
                        { 
                            /*Red Wins and remains at their postion*/
                            /*Blue Combat Unit dies*/
                            if (Scenario.bluePlayer.playerID == cowards.get(0).faction.playerID) 
                            {
                                for (int i = 0; i < cowards.size(); i++) 
                                {
                                    Scenario.bluePlayer.combatUnits.remove(cowards.get(i));
                                }
                            } 
                            else 
                            {
                                for (int i = 0; i < cowards.size(); i++) 
                                {
                                    Scenario.redPlayer.combatUnits.remove(cowards.get(i));
                                }
                            }
                            JOptionPane.showMessageDialog(null, "Red Combat Unit defends their position and defeat Blue!!!\n"+
                            "\nRed remain at their original position\n"
                            + "Blue Strength: " + aggregateCowardsBattleStrength + " Red Strength:" + attackerPreviousLocation);

                        }
                        
                    } 
                    
                    else 
                    {
                        /*COWARDS WIN and end up heading towards their intened position*/
                        if (isCowards == "red")
                        {
                            /*RED (Cowards) UNIT WINS They Proceed their intended spot*/

                            /*blue dies*/
                            if (Scenario.bluePlayer.playerID == attackers.get(0).faction.playerID) 
                                {
                                    for (int i = 0; i < attackers.size(); i++) 
                                    {
                                        Scenario.bluePlayer.combatUnits.remove(attackers.get(i));
                                    }
                                } 
                                else 
                                {
                                    for (int i = 0; i < attackers.size(); i++) 
                                    {
                                        Scenario.redPlayer.combatUnits.remove(attackers.get(i));
                                    }
                                }

                            JOptionPane.showMessageDialog(null, "Red Combat Unit defeat Blue Combat Unit!!!\n"+
                            "\nRed will move to thier intended position\n"
                            + "Red Strength: " + aggregateCowardsBattleStrength + " Blue Strength:" + attackerPreviousLocation);


                        }
                        else 
                        {
                            /*Blue (Cowards) UNIT WINS They Proceed their intended spot*/
                            /*Red dies*/
                            if (Scenario.redPlayer.playerID == attackers.get(0).faction.playerID) 
                                {
                                    for (int i = 0; i < attackers.size(); i++) 
                                    {
                                        Scenario.redPlayer.combatUnits.remove(attackers.get(i));
                                    }
                                } 
                                else 
                                {
                                    for (int i = 0; i < attackers.size(); i++) 
                                    {
                                        Scenario.bluePlayer.combatUnits.remove(attackers.get(i));
                                    }
                                }

                        }

                        JOptionPane.showMessageDialog(null, "Blue Combat Unit defeat Red Combat !!!\n"+
                        "\nBlue will move to thier intended position\n"
                        + "Blue Strength: " + aggregateCowardsBattleStrength + " Red Strength:" + attackerPreviousLocation);


                        /* Cowards will move to their intended location (Not team specific) */
                        for (int i = 0; i < cowards.size(); i++) 
                        {
                            MapEvent.addMovement(cowards.get(i).cUnitID, null , node.id);
                        }
                        
                        
                    }
                }
            } 
            else 
            {
                /*BOTH TEAMS REMAIN AT THEIR ORIGNAL POSTION*/
                if (isCowards == "red")
                {
                    JOptionPane.showMessageDialog(null, "Red Combat unit was able to retreat!!!\n"+
                    "\nNo battle was taken place\n"
                    + "Red Strength: " + aggregateCowardsBattleStrength + " Blue Strength:" + attackerPreviousLocation);
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Blue Combat unit was able to retreat!!!\n"+
                    "\nNo battle was taken place\n"
                    + "Blue Strength: " + aggregateCowardsBattleStrength + " Red Strength:" + attackerPreviousLocation);
                }
            }
        }

        /*CASE 4: does not exist cause you can not have both defenders*/
    }

    public void PVPNonCommitedBattle(ArrayList<CombatUnit> red, ArrayList<CombatUnit> blue, 
            ArrayList<Node> redsIntendedNode,ArrayList<Node> redsPreviousNode,
            ArrayList<Node> blueIntendedNode, ArrayList<Node> bluePreviousNode) {
        /*This where no battle happens and all CombatUnits return to previous node*/
        /*Not very complicated*/
        
        for (int i = 0; i < red.size(); i++) 
                        {
                            MapEvent.addMovement(red.get(i).cUnitID, Scenario.findRoad(redsIntendedNode.get(i), redsPreviousNode.get(i) ) , redsPreviousNode.get(i).id);
                        }
        for (int i = 0; i < blue.size(); i++) 
                        {
                            MapEvent.addMovement(blue.get(i).cUnitID, Scenario.findRoad(bluePreviousNode.get(i),blueIntendedNode.get(i)) , bluePreviousNode.get(i).id);
                        }
        
        JOptionPane.showMessageDialog(null, "Both Combat Units decide to retreat "+
        "\nNo battle was taken place");
        
        
        
    }

    public void PVPChaseBattle(ArrayList<CombatUnit> attackers, ArrayList<CombatUnit> cowards, ArrayList<Node> attackersDesiredNode, ArrayList<Node> cowardsDesiredNode) 
    {
        /*This happpens when the play collide on Road and one should flee*/
        int aggregateAttackerStrength = 0;
        int aggregateCowardsStrength = 0;
        
        String isCowards;
        String isAttackers;
        
        if (cowards.get(0).faction.playerID == 0) {
            isCowards = "red";
            isAttackers = "blue";
        } 
        else 
        {
            isCowards = "blue";
            isAttackers = "red";
        }
        
        for (int i = 0; i < attackers.size(); i++) 
        {
            if (!attackers.get(i).isFleet)
            {
                aggregateAttackerStrength += attackers.get(i).GetBattleStrengh();
            }
                else
            {
                aggregateAttackerStrength += (int)(attackers.get(i).GetBattleStrengh()*0.85);
            }
        }
        /*GET BATTLE STRENGTH for Cowards*/
        for (int i = 0; i < cowards.size(); i++) 
        {
            if (!attackers.get(i).isFleet)
            {
                aggregateCowardsStrength += cowards.get(i).GetBattleStrengh();
            }
                else
            {
                aggregateCowardsStrength += (int)(cowards.get(i).GetBattleStrengh()*0.85);
            }
        }
        
        aggregateCowardsStrength = (int) ((double) aggregateCowardsStrength * 0.8);

        if (randNum.nextDouble() > 0.75) /*Cowards are able to flee*/ 
        {
            /* Both Armies move to their intended location*/
            for (int i = 0; i < attackers.size(); i++) 
                        {
                            MapEvent.addMovement(attackers.get(i).cUnitID, null, attackersDesiredNode.get(i).id);
                        }
            for (int i = 0; i < cowards.size(); i++) 
                        {
                            MapEvent.addMovement(cowards.get(i).cUnitID, null , cowardsDesiredNode.get(i).id);
                        }
            
            if(isCowards == "red")
            {
                JOptionPane.showMessageDialog(null, "Red Combat Units decide to retreat "
                        + "\nNo battle was taken place\n"
                        + "Red Battle Strength: " + aggregateCowardsStrength + " Blue Battle Strength: "
                        + aggregateAttackerStrength);
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Blue Combat Units decide to retreat "
                        +"\nNo battle was taken place\n"
                        + "Blue Battle Strength: " + aggregateCowardsStrength + " Red Battle Strength: "
                        + aggregateAttackerStrength);
            }
        }
        
        else /* Flee fails*/ 
        
        {
            if (aggregateCowardsStrength < aggregateAttackerStrength) 
            {
                /*Attackers win and move thier desired location*/
                if(isCowards =="red")
                {
                    /*Red Combat Army Falls in battle Blue Moves into position*/
                    if (Scenario.redPlayer.playerID == cowards.get(0).faction.playerID) 
                    {
                        for (int i = 0; i < cowards.size(); i++) 
                        {
                            Scenario.redPlayer.combatUnits.remove(cowards.get(i));
                        }
                    } 
                    else 
                    {
                        for (int i = 0; i < cowards.size(); i++) 
                        {
                            Scenario.bluePlayer.combatUnits.remove(cowards.get(i));
                        }
                    }
                    
                    JOptionPane.showMessageDialog(null, "Red Units Fall in battle"+
                    "\nBlue Units move to desired position\n"
                        + "Red Battle Strength: " + aggregateCowardsStrength + " Blue Battle Strength: "
                        + aggregateAttackerStrength);
                    
                }
                else
                {
                    /*Red Combat Army Falls in battle Blue Moves into position*/
                    if (Scenario.bluePlayer.playerID == cowards.get(0).faction.playerID) 
                    {
                        for (int i = 0; i < cowards.size(); i++) 
                        {
                            Scenario.bluePlayer.combatUnits.remove(cowards.get(i));
                        }
                    } 
                    else 
                    {
                        for (int i = 0; i < cowards.size(); i++) 
                        {
                            Scenario.redPlayer.combatUnits.remove(cowards.get(i));
                        }
                    }
                    JOptionPane.showMessageDialog(null, "Blue Units Fall in battle"+
                    "\nRed Units move to desired position\n"
                    + "Blue Battle Strength: " + aggregateCowardsStrength + " Red Battle Strength: "
                        + aggregateAttackerStrength);
                    
                }
                for (int i = 0; i < cowards.size(); i++) 
                        {
                            MapEvent.addMovement(attackers.get(i).cUnitID, null , attackersDesiredNode.get(i).id);
                        }
                
            } 
            else if (aggregateCowardsStrength > aggregateAttackerStrength) 
            {
                /*cowards win and move to their desired location*/
                if(isCowards =="red")
                {
                    /*red team defeats blue and moves to desired lovation*/
                    if (Scenario.bluePlayer.playerID == attackers.get(0).faction.playerID) 
                    {
                        for (int i = 0; i < attackers.size(); i++) 
                        {
                            Scenario.bluePlayer.combatUnits.remove(attackers.get(i));
                        }
                    } 
                    else 
                    {
                        for (int i = 0; i < attackers.size(); i++) 
                        {
                            Scenario.redPlayer.combatUnits.remove(attackers.get(i));
                        }
                    }
                    JOptionPane.showMessageDialog(null, "Blue Units Fall in battle"+
                    "\nRed Units move to desired position\n"
                    + "Red Battle Strength: " + aggregateCowardsStrength + " Blue Battle Strength: "
                        + aggregateAttackerStrength);     
                }
                else
                {
                    /*blue team defeats red and moves to desired lovation*/
                    if (Scenario.redPlayer.playerID == attackers.get(0).faction.playerID) 
                        {
                            for (int i = 0; i < attackers.size(); i++) 
                            {
                                Scenario.redPlayer.combatUnits.remove(attackers.get(i));
                            }
                        } 
                        else 
                        {
                            for (int i = 0; i < attackers.size(); i++) 
                            {
                                Scenario.bluePlayer.combatUnits.remove(attackers.get(i));
                            }
                        }
                    JOptionPane.showMessageDialog(null, "Red Units Fall in battle"+
                    "\nBlue Units move to desired position\n"
                    + "Blue Battle Strength: " + aggregateCowardsStrength + " Red Battle Strength: "
                        + aggregateAttackerStrength);
                }
            } 
            else 
            {
                if (randNum.nextDouble() > 0.5) 
                {
                    /*Attackers win and move thier desired location*/
                    if(isCowards =="red")
                    {
                        /*Red Combat Army Falls in battle Blue Moves into position*/
                        if (Scenario.redPlayer.playerID == cowards.get(0).faction.playerID) 
                        {
                            for (int i = 0; i < cowards.size(); i++) 
                            {
                                Scenario.redPlayer.combatUnits.remove(cowards.get(i));
                            }
                        } 
                        else 
                        {
                            for (int i = 0; i < cowards.size(); i++) 
                            {
                                Scenario.bluePlayer.combatUnits.remove(cowards.get(i));
                            }
                        }

                        JOptionPane.showMessageDialog(null, "Red Units Fall in battle"+
                        "\nBlue Units move to desired position\n"
                        + "Red Battle Strength: " + aggregateCowardsStrength + " Blue Battle Strength: "
                        + aggregateAttackerStrength);

                    }
                    else
                    {
                        /*Red Combat Army Falls in battle Blue Moves into position*/
                        if (Scenario.bluePlayer.playerID == cowards.get(0).faction.playerID) 
                        {
                            for (int i = 0; i < cowards.size(); i++) 
                            {
                                Scenario.bluePlayer.combatUnits.remove(cowards.get(i));
                            }
                        } 
                        else 
                        {
                            for (int i = 0; i < cowards.size(); i++) 
                            {
                                Scenario.redPlayer.combatUnits.remove(cowards.get(i));
                            }
                        }
                        JOptionPane.showMessageDialog(null, "Blue Units Fall in battle"+
                        "\nRed Units move to desired position\n"
                        + "Blue Battle Strength: " + aggregateCowardsStrength + " Red Battle Strength: "
                        + aggregateAttackerStrength);
                    
                }
                for (int i = 0; i < cowards.size(); i++) 
                        {
                            MapEvent.addMovement(attackers.get(i).cUnitID, null , attackersDesiredNode.get(i).id);
                        }

                } 
                else 
                {
                    /*cowards win and move to their desired location*/
                    if(isCowards =="red")
                    {
                        /*red team defeats blue and moves to desired lovation*/
                        if (Scenario.bluePlayer.playerID == attackers.get(0).faction.playerID) 
                        {
                            for (int i = 0; i < attackers.size(); i++) 
                            {
                                Scenario.bluePlayer.combatUnits.remove(attackers.get(i));
                            }
                        } 
                        else 
                        {
                            for (int i = 0; i < attackers.size(); i++) 
                            {
                                Scenario.redPlayer.combatUnits.remove(attackers.get(i));
                            }
                        }
                        JOptionPane.showMessageDialog(null, "Blue Units Fall in battle"+
                        "\nRed Units move to desired position"
                        + "Red Battle Strength: " + aggregateCowardsStrength + " Blue Battle Strength: "
                        + aggregateAttackerStrength);     
                    }
                    else
                    {
                        /*blue team defeats red and moves to desired lovation*/
                        if (Scenario.redPlayer.playerID == attackers.get(0).faction.playerID) 
                            {
                                for (int i = 0; i < attackers.size(); i++) 
                                {
                                    Scenario.redPlayer.combatUnits.remove(attackers.get(i));
                                }
                            } 
                            else 
                            {
                                for (int i = 0; i < attackers.size(); i++) 
                                {
                                    Scenario.bluePlayer.combatUnits.remove(attackers.get(i));
                                }
                            }
                        JOptionPane.showMessageDialog(null, "Red Units Fall in battle"+
                        "\nBlue Units move to desired position"
                        + "Blue Battle Strength: " + aggregateCowardsStrength + " Red Battle Strength: "
                        + aggregateAttackerStrength);
                    }
                }
            }
        }
    }

    // for the case of a collision battle on a road
    public void doBattleOnRoad(ArrayList<CombatUnit> redCombatUnit, ArrayList<Node> redCombatUnitPreviousLocation,
            ArrayList<Node> redCombatUnitEndLocation, ArrayList<CombatUnit> blueCombatUnit,
            ArrayList<Node> blueCombatUnitPreviousLocation, ArrayList<Node> blueCombatUnitEndLocation) {

        boolean redDecisionToFight = false;
        boolean blueDecisionToFight = false;
        boolean preemptiveRed = false;
        boolean preemptiveBlue = false;
        int yesNo;
        Object[] options = {"Battle", "Flee!"};
        
        
        /*Asking red if they want to do battle*/



        if(IsPreemptive(redCombatUnitEndLocation.get(0),redCombatUnit.get(0).faction.playerID))
        {
            redDecisionToFight = true;
            /*Reds decision to fight is true by default if preemptive*/
            
            JOptionPane.showMessageDialog(null, "Red Team is force to fight because his move was a pre emptive attack");
        }
        else
        {
            /*PRRRRRRRRRRRRRRRRRREEEEEEEEEEEEEEEEEEEEEMMMMMM*/
            /*you question box is here*/
            yesNo = JOptionPane.showOptionDialog(null, ("Red Team... Would you like to battle on the road between" +
                    redCombatUnitPreviousLocation.get(0).name + " and " +
                    redCombatUnitEndLocation.get(0).name), "Battle?",
                    JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
            if (yesNo == 0) /*IF FIGHT*/ {
                redDecisionToFight = true;
            } else {
                redDecisionToFight = false;
            }
        }
       
        if(IsPreemptive(blueCombatUnitEndLocation.get(0),blueCombatUnit.get(0).faction.playerID))
        {
            blueDecisionToFight = true;
            /*Reds decision to fight is true by default if preemptive*/
            
            JOptionPane.showMessageDialog(null, "Blue Team is force to fight because his move was a pre emptive attack");
        }
        else
        {
            /*PRRRRRRRRRRRRRRRRRREEEEEEEEEEEEEEEEEEEEEMMMMMM*/
            /*you question box is here*/
            
            yesNo = JOptionPane.showOptionDialog(null, ("Blue Team... Would you like to battle on the road between" +
                    redCombatUnitPreviousLocation.get(0).name + " and " +
                    redCombatUnitEndLocation.get(0).name), "Battle?",
                    JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
            if (yesNo == 0) /*IF FIGHT*/ {
                blueDecisionToFight = true;
            } else {
                blueDecisionToFight = false;
            }
        }

        if (redDecisionToFight && blueDecisionToFight) {
            PVPCommitedBattle(redCombatUnit, blueCombatUnit, redCombatUnitEndLocation, blueCombatUnitEndLocation);
        } else if (!redDecisionToFight && blueDecisionToFight) {
            PVPChaseBattle(blueCombatUnit, redCombatUnit, blueCombatUnitEndLocation, redCombatUnitEndLocation);

        } else if (redDecisionToFight && !blueDecisionToFight) {
            PVPChaseBattle(redCombatUnit, blueCombatUnit, redCombatUnitEndLocation, blueCombatUnitEndLocation);
        } else {
            PVPNonCommitedBattle(redCombatUnit, blueCombatUnit, redCombatUnitEndLocation,redCombatUnitPreviousLocation, blueCombatUnitEndLocation, blueCombatUnitPreviousLocation);
        }
    }

    private boolean IsPreemptive(Node location, int playerID) {
        ArrayList<Integer> nodeValues;

        if (Scenario.redPlayer.playerID == playerID) {
            for (int i = 0; i < Scenario.redPlayer.enemyIntelligence.size(); i++) {
                nodeValues = Scenario.redPlayer.enemyIntelligence.get(i);
                if (nodeValues.get(0) == location.id) {
                    if (nodeValues.size() > 1) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        } else {
            for (int i = 0; i < Scenario.bluePlayer.enemyIntelligence.size(); i++) {
                nodeValues = Scenario.redPlayer.enemyIntelligence.get(i);
                if (nodeValues.get(0) == location.id) {
                    if (nodeValues.size() > 1) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }

        return false;
    }
}
