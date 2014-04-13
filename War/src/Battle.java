
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
    Random randNum;
    
    public Battle(){
        randNum = new Random();
    }

    /*
     String[] report;
     Node battleLocation;
     int turnNo;
     */
    // for the case of battle occuring on a node
    public void PVPdoCampBattleOnNode(Node node, ArrayList<CombatUnit> redCombatUnit, ArrayList<Node> redCombatUnitPreviousLocation,
            ArrayList<Node> redCombatUnitEndLocation, ArrayList<CombatUnit> blueCombatUnit,
            ArrayList<Node> blueCombatUnitPreviousLocation, ArrayList<Node> blueCombatUnitEndLocation) 
    { /*THE DECISION MAKER FUNCTION FOR COLLISIONS ON A NODE*/

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

        JOptionPane.showMessageDialog(null, "Collision Alert!\n"
                + "There is a collision of armies at " + node.name);
        
        
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
            
            Global.redPreEmptiveToString = "Red Team is force to fight because his move was a pre emptive attack\n";
            
        }
        else
        {
            Global.redPreEmptiveToString ="";
            /*PRRRRRRRRRRRRRRRRRREEEEEEEEEEEEEEEEEEEEEMMMMMM*/
            /*you question box is here*/
            if (Global.opponent == true) /*PVP*/
            {
                yesNo = JOptionPane.showOptionDialog(null, ("Red Team... Would you like to battle on " + node.name + " ?"), "Battle?",
                        JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
                if (yesNo == 0) /*IF FIGHT*/ {
                    redDecisionToFight = true;
                } else {
                    redDecisionToFight = false;
                }
            }
            else if (Global.chosenTeam == false) /*player is red*/
            {
                yesNo = JOptionPane.showOptionDialog(null, ("Red Team... Would you like to battle on " + node.name + " ?"), "Battle?",
                        JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
                if (yesNo == 0) /*IF FIGHT*/ {
                    redDecisionToFight = true;
                } else {
                    redDecisionToFight = false;
                }
            }
            else /*Red is computer AI*/
            {
                if (randNum.nextDouble() > 0.3)
                {
                    redDecisionToFight = true;
                } 
                else 
                {
                    redDecisionToFight = false;
                }
                
            }
        }
       
        if(IsPreemptive(node,blueCombatUnit.get(0).faction.playerID))
        {
            blueDecisionToFight = true;
            /*Reds decision to fight is true by default if preemptive*/
            Global.bluePreEmptiveToString = "Blue Team is force to fight because his move was a pre emptive attack\n";
        }
        else
        {
            Global.bluePreEmptiveToString = "";
            /*PRRRRRRRRRRRRRRRRRREEEEEEEEEEEEEEEEEEEEEMMMMMM*/
            /*you question box is here*/
            if (Global.opponent == true) /*PVP*/
            {
                yesNo = JOptionPane.showOptionDialog(null, ("Blue... Would you like to battle on " + node.name + " ?"), "Battle?",
                        JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);

                if (yesNo == 0) /*IF FIGHT*/ {
                    blueDecisionToFight = true;
                } else {
                    blueDecisionToFight = false;
                }
            }
            else if(Global.chosenTeam == true) /*player is Blue*/
            {
                yesNo = JOptionPane.showOptionDialog(null, ("Blue... Would you like to battle on " + node.name + " ?"), "Battle?",
                        JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);

                if (yesNo == 0) /*IF FIGHT*/ {
                    blueDecisionToFight = true;
                } else {
                    blueDecisionToFight = false;
                }
            
            }
            else /*Blue is AI*/
            {
                if (randNum.nextDouble() > 0.3)
                {
                    blueDecisionToFight = true;
                } 
                else 
                {
                    blueDecisionToFight = false;
                }
            }
            
        }

        if (blueDecisionToFight && redDecisionToFight) {
            /*Both armies commit to battle*/
            PVPCommitedBattle(redCombatUnit, blueCombatUnit, redCombatUnitEndLocation, blueCombatUnitEndLocation);
        } 
        else if (!blueDecisionToFight && redDecisionToFight) 
        {
            /*if Red wants to fight but blue does not*/
            PVPHalfCommitedBattleOnNode(node, redCombatUnit, blueCombatUnit, redCombatUnitPreviousLocation,
                    redCombatUnitEndLocation, blueCombatUnitPreviousLocation);
        } else if (blueDecisionToFight && !redDecisionToFight) {
            /*if Blue wants to fight but Red does not*/
            PVPHalfCommitedBattleOnNode(node, blueCombatUnit, redCombatUnit, blueCombatUnitPreviousLocation,
                    blueCombatUnitEndLocation, redCombatUnitPreviousLocation);

            //PVPCommitedBattle(node, red, blue);
        } else {
            /*When No one wants to fight*/

            PVPNonCommitedBattle(redCombatUnit, blueCombatUnit, redCombatUnitEndLocation,
                    redCombatUnitPreviousLocation, blueCombatUnitPreviousLocation,blueCombatUnitPreviousLocation);
           

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
                redAggregateStrength += (int)((double)(red.get(i).GetBattleStrengh())*(0.85));
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
                blueAggregateStrength += (int)((double)(blue.get(i).GetBattleStrengh())*(0.85));
            }
        }

        if (redAggregateStrength < blueAggregateStrength)
        {
            /*Blue wins and moves to their desired location*/

            // remove all of the losing red units from the player's roster
          
            MapEvent.completeCombat(red, false, blue, true, blueIntendedNode.get(0));


            JOptionPane.showMessageDialog(null, Global.redPreEmptiveToString + Global.bluePreEmptiveToString+
                    battleStatus(blueIntendedNode.get(0),redAggregateStrength,blueAggregateStrength, 
                            red, blue, null,
                            false, false));
           
            
        } 
        else if (redAggregateStrength > blueAggregateStrength) 
        {
            /*Red wins and moves to their desired location*/
            MapEvent.completeCombat(red, true, blue, false, redsIntendedNode.get(0));

           JOptionPane.showMessageDialog(null,  Global.redPreEmptiveToString + Global.bluePreEmptiveToString+
                   battleStatus(redsIntendedNode.get(0),redAggregateStrength,blueAggregateStrength, 
                            red, blue, null,
                            false, false));
          

        } 
        else 
        {
            if (randNum.nextDouble() > 0.5) {
                /*Red wins and moves to their desired location*/
                MapEvent.completeCombat(red, true, blue, false, redsIntendedNode.get(0));

           JOptionPane.showMessageDialog(null,  Global.redPreEmptiveToString + Global.bluePreEmptiveToString+
                   battleStatus(redsIntendedNode.get(0),redAggregateStrength,blueAggregateStrength, 
                            red, blue, "red",
                            false, false));
            } 
            else 
            {
                /*Blue wins and moves to their desired location*/
                MapEvent.completeCombat(red, false, blue, true, blueIntendedNode.get(0));
            }
            JOptionPane.showMessageDialog(null,  Global.redPreEmptiveToString + Global.bluePreEmptiveToString+
                    battleStatus(blueIntendedNode.get(0),redAggregateStrength,blueAggregateStrength, 
                            red, blue, "blue",
                            false, false) );
        }

    }

    public void PVPHalfCommitedBattleOnNode(Node node, ArrayList<CombatUnit> attackers,
            ArrayList<CombatUnit> cowards, ArrayList<Node> attackerPreviousLocation,
            ArrayList<Node> attackerIntendedLocation, ArrayList<Node> cowardsPreviousLocation) {
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
                aggregateAttackersBattleStrength += (int)((double)(attackers.get(i).GetBattleStrengh())*(0.85));
            }
        }
        /*GET BATTLE STRENGTH for Cowards*/
        for (int i = 0; i < cowards.size(); i++) 
        {
            if (!cowards.get(i).isFleet)
            {
                aggregateCowardsBattleStrength += cowards.get(i).GetBattleStrengh();
            }
                else
            {
                aggregateCowardsBattleStrength += (int)((double)(cowards.get(i).GetBattleStrengh())*(0.85));
            }
        }

        aggregateCowardsBattleStrength = (int) ((double) (aggregateCowardsBattleStrength) * (0.8));

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
                
                MapEvent.successfullFlee(cowards, Scenario.findRoad(node, cowardsPreviousLocation.get(0)), cowardsPreviousLocation.get(0).id);
                

                
                /*Attackers Move into intended position*/
                MapEvent.successfullFlee(attackers, Scenario.findRoad(node, attackerPreviousLocation.get(0)), attackerIntendedLocation.get(0).id);
                
                
                if (isCowards == "red")
                {
                    JOptionPane.showMessageDialog(null,Global.redPreEmptiveToString + Global.bluePreEmptiveToString
                            + "Red flees battle while Blue moves to " + node.name);
                }
                else
                {
                    JOptionPane.showMessageDialog(null,Global.redPreEmptiveToString + Global.bluePreEmptiveToString
                            + "Blue flees battle while Red moves to " + node.name);

                }
            } 
            else 
            {
                if (aggregateCowardsBattleStrength > aggregateAttackersBattleStrength) 
                { 
                    /*attackers fall in battle*/
                    /*COWARDS WIN and end up heading towards their intened position*/
                    if (isCowards == "red") 
                    {
                        /*if cowards is red then blue loses their armies*/
                        
                        MapEvent.completeCombat(cowards, true, attackers, false, node);

                    JOptionPane.showMessageDialog(null, Global.redPreEmptiveToString + Global.bluePreEmptiveToString
                            + battleStatus(node,aggregateCowardsBattleStrength,
                                    aggregateAttackersBattleStrength,cowards,
                                    attackers,null,true,false));

                    }
                    else
                    { /*Cowards is blue for the case that cowards win*/ 

                        /*if cowards is blue then red loses their armies*/
                        MapEvent.completeCombat(attackers, false, cowards, true, node);

                        
                    JOptionPane.showMessageDialog(null,Global.redPreEmptiveToString + Global.bluePreEmptiveToString
                            + battleStatus(node,aggregateAttackersBattleStrength,
                                    aggregateCowardsBattleStrength,attackers,
                                    cowards,null,false,true));
                    }

                    /*Move Cowards into desired location*/
                   /* for (int i = 0; i < cowards.size(); i++) 
                    {
                        MapEvent.addMovement(cowards.get(i).cUnitID, null, cowardsPreviousLocation.get(i).id);
                    }*/
                    
                    


                } 
                else if (aggregateCowardsBattleStrength < aggregateAttackersBattleStrength) 
                {
                    /*ATTACKERS WIN AT TAKE POSITION*/

                    if(isCowards == "red")
                    {
                        MapEvent.completeCombat(cowards, false, attackers, true, node);
                        
                        
                    JOptionPane.showMessageDialog(null, Global.redPreEmptiveToString + Global.bluePreEmptiveToString
                            + battleStatus(node,aggregateCowardsBattleStrength,
                                    aggregateAttackersBattleStrength,cowards,
                                    attackers,null,true,false));

                    }
                    else 
                    {
                        /*Red Team wins Blue team Cowards armies will die*/
                        
                        MapEvent.completeCombat(attackers, true, cowards, false, node);
                        
                        
                    JOptionPane.showMessageDialog(null, Global.redPreEmptiveToString + Global.bluePreEmptiveToString
                            + battleStatus(node,aggregateAttackersBattleStrength,
                                    aggregateCowardsBattleStrength,attackers,
                                    cowards,null,false,true));
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
                            
                            MapEvent.completeCombat(cowards, false, attackers, true, node);

                            JOptionPane.showMessageDialog(null,Global.redPreEmptiveToString + Global.bluePreEmptiveToString
                                    + battleStatus(node,aggregateCowardsBattleStrength,
                                    aggregateAttackersBattleStrength,cowards,
                                    attackers,"blue",true,false));
                        }
                        else
                        {
                            MapEvent.completeCombat(attackers, true, cowards, false, node);

                            JOptionPane.showMessageDialog(null,Global.redPreEmptiveToString + Global.bluePreEmptiveToString
                                    + battleStatus(node,aggregateAttackersBattleStrength,
                                    aggregateCowardsBattleStrength,attackers,
                                    cowards,"red",false,true));
                        }
                    } 
                    else 
                    {
                        /*COWARDS WIN and end up heading towards their intened position*/
                        if (isCowards == "red") 
                        {
                            MapEvent.completeCombat(cowards, true, attackers, false, node);
                            
                            JOptionPane.showMessageDialog(null,Global.redPreEmptiveToString + Global.bluePreEmptiveToString
                                    + battleStatus(node,aggregateCowardsBattleStrength,
                                    aggregateAttackersBattleStrength,cowards,
                                    attackers,"red",true,false));
                        }
                        else
                        { /*Cowards is blue*/ 
                            
                            MapEvent.completeCombat(attackers, false, cowards, true, node);

                            JOptionPane.showMessageDialog(null,Global.redPreEmptiveToString + Global.bluePreEmptiveToString
                                    + battleStatus(node,aggregateAttackersBattleStrength,
                                    aggregateCowardsBattleStrength,attackers,
                                    cowards,"blue",false,true));
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
                    /*COWARDS WIN and end up heading towards their intened position */

                    if(isCowards == "red")
                    {
                        /*Attacker Blue dies*/
                        MapEvent.completeCombat(cowards, true, attackers, false, node);

                    JOptionPane.showMessageDialog(null,Global.redPreEmptiveToString + Global.bluePreEmptiveToString
                            + battleStatus(node,aggregateCowardsBattleStrength,
                                    aggregateAttackersBattleStrength,cowards,
                                    attackers,null,true,false));
                    }
                    else
                    {
                        /*Attcker Red Dies*/
                        MapEvent.completeCombat(attackers, false, cowards, true, node);

                        JOptionPane.showMessageDialog(null, Global.redPreEmptiveToString + Global.bluePreEmptiveToString
                            + battleStatus(node,aggregateAttackersBattleStrength,
                                    aggregateCowardsBattleStrength,attackers,
                                    cowards,null,false,true));
                    }
                }
                else if (aggregateCowardsBattleStrength < aggregateAttackersBattleStrength) 
                {
                    /*Cowards are defenders*/
                    /*ATTACKERS WIN AT TAKE POSITION*/
                    if(isCowards == "red")
                    {       
                        /*Red Combat Unit will fall and blue will move into position*/
 
                        MapEvent.completeCombat(cowards, false, attackers, true, node);
                        
                        /* Blue (attackers) moves into postion */
                        JOptionPane.showMessageDialog(null,Global.redPreEmptiveToString + Global.bluePreEmptiveToString
                                + battleStatus(node,aggregateCowardsBattleStrength, aggregateAttackersBattleStrength,
                                        cowards,attackers,
                                        null,true,false));
                    }
                    else
                    {
                        /*Blue Combat Unit will fall and Red will move into position*/
                        /*Coward Blue Dies*/
                        
                        MapEvent.completeCombat(attackers, true, cowards, false, node);
                        
                        /* Red (attackers) moves into postion */

                        JOptionPane.showMessageDialog(null,Global.redPreEmptiveToString + Global.bluePreEmptiveToString
                                + battleStatus(node,aggregateAttackersBattleStrength,aggregateCowardsBattleStrength,
                                        attackers,cowards,
                                        null,false,true));
                    }
                 } 
                else 
                {
                    if (randNum.nextDouble() > .5) 
                    {
                        /*ATTACKERS WIN AT TAKE POSITION*/
                        if(isCowards == "red")
                        {       
                            /*Red Combat Unit will fall and blue will move into position*/
                            /*Coward Red Dies*/
                            
                            MapEvent.completeCombat(cowards, false, attackers, true, node);

                            JOptionPane.showMessageDialog(null,Global.redPreEmptiveToString + Global.bluePreEmptiveToString
                                    + battleStatus(node,aggregateCowardsBattleStrength,
                                    aggregateAttackersBattleStrength,cowards,
                                    attackers,"blue",true,false));
                        }
                        else
                        {
                            /*Blue Combat Unit will fall and Red will move into position*/
                            /*Coward Blue Dies*/
                            MapEvent.completeCombat(attackers, true, cowards, false, node);

                            JOptionPane.showMessageDialog(null,Global.redPreEmptiveToString + Global.bluePreEmptiveToString
                                    + battleStatus(node,aggregateAttackersBattleStrength,
                                    aggregateCowardsBattleStrength,attackers,
                                    cowards,"red",false,true));
                        }  
                    } 
                    else 
                    {
                        /*COWARDS WIN and end up heading towards their intened position THE SAME POSITION AS NODE*/
                        if(isCowards == "red")
                        {
                            /*Attacker Blue dies*/
                            MapEvent.completeCombat(cowards, true, attackers, false, node);

                            JOptionPane.showMessageDialog(null,Global.redPreEmptiveToString + Global.bluePreEmptiveToString
                                    + battleStatus(node,aggregateCowardsBattleStrength,
                                    aggregateAttackersBattleStrength,cowards,
                                    attackers,"red",true,false));
                        }
                        else
                        {
                            /*Attcker Red Dies*/
                            MapEvent.completeCombat(attackers, false, cowards, true, node);

                            JOptionPane.showMessageDialog(null,Global.redPreEmptiveToString + Global.bluePreEmptiveToString
                                    + battleStatus(node,aggregateAttackersBattleStrength,
                                    aggregateCowardsBattleStrength,attackers,
                                    cowards,"blue",false,true));
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
                        MapEvent.completeCombat(cowards, false, attackers, true, node);
                        
                        JOptionPane.showMessageDialog(null,Global.redPreEmptiveToString + Global.bluePreEmptiveToString+ "Red are unable flees from their position therefore their unit is defeated!!!"+
                                "\nBlue proceeds to "+ node.name);
                    }
                    else
                    {   
                        /*Coward is blue and they die*/
                        MapEvent.completeCombat(attackers, true, cowards, false, node);

                       JOptionPane.showMessageDialog(null,Global.redPreEmptiveToString + Global.bluePreEmptiveToString+ "Blue are unable flees from their position therefore their unit is defeated!!!"+
                                "\nRed proceeds to "+ node.name);
                    }
                } 
                else 
                {
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
                    
                   MapEvent.successfullFlee(cowards, adjacentRoadsToNode.get(roadChoice), randomRoadDestination.id);
                   MapEvent.successfullFlee(attackers,Scenario.findRoad(node, attackerPreviousLocation.get(0)) , node.id);


                    if (isCowards == "red")
                    {
                        JOptionPane.showMessageDialog(null,Global.redPreEmptiveToString + Global.bluePreEmptiveToString+ "Red flees from their position!!!\n"+
                        "\nBlue proceeds to "+ node.name+" and no battle is taken place");
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null,Global.redPreEmptiveToString + Global.bluePreEmptiveToString+ "Blue flees from their position!!!\n"+
                        "\nRed proceeds to "+ node.name+" and no battle is taken place");
                    }
                }
            }
        } /*CASE 3: Attackers are defenders (attackers did not move but have commited to battle)*/ 
        else 
        {
            if (randNum.nextDouble() > 0.25) /*Cowards are not able to flee*/ 
            {
                if (aggregateCowardsBattleStrength > aggregateAttackersBattleStrength) 
                {
                    /*COWARDS WIN and end up heading towards their intened position*/
                    if (isCowards == "red")
                    {
                        /*RED (Cowards) UNIT WINS They Proceed their intended spot*/
                        
                        /*blue dies*/
                        
                        MapEvent.completeCombat(cowards, true, attackers, false, node);

                        JOptionPane.showMessageDialog(null,Global.redPreEmptiveToString + Global.bluePreEmptiveToString
                                + battleStatus(node,aggregateCowardsBattleStrength,
                                    aggregateAttackersBattleStrength,cowards,
                                    attackers,null,true,false));
                    }
                    else 
                    {
                        /*Blue (Cowards) UNIT WINS They Proceed their intended spot*/
                        /*Red dies*/
                        
                        MapEvent.completeCombat(attackers, false, cowards, true, node);

    
                      JOptionPane.showMessageDialog(null,Global.redPreEmptiveToString + Global.bluePreEmptiveToString
                              + battleStatus(node,aggregateAttackersBattleStrength,
                                    aggregateCowardsBattleStrength,attackers,
                                    cowards,null,false,true));
                    }
                } 
                else if (aggregateCowardsBattleStrength < aggregateAttackersBattleStrength) 
                {
                    /*ATTACKERS WIN AND REMAIN AT THEIR POSITION*/
                    if(isCowards == "red")
                    {
                        /*Blue Wins and moves to node*/
                        /*Red Combat Unit dies*/
                        MapEvent.completeCombat(cowards, false, attackers, true, node);

                        
                    JOptionPane.showMessageDialog(null,Global.redPreEmptiveToString + Global.bluePreEmptiveToString
                            + battleStatus(node,aggregateCowardsBattleStrength,
                                    aggregateAttackersBattleStrength,cowards,
                                    attackers,null,true,false));
 
                    }
                    else
                    { 
                        /*Red Wins and moves to node*/
                        /*Blue Combat Unit dies*/
                        
                        MapEvent.completeCombat(attackers, true, cowards, false, node);

                        
                        JOptionPane.showMessageDialog(null,Global.redPreEmptiveToString + Global.bluePreEmptiveToString
                                + battleStatus(node,aggregateAttackersBattleStrength,
                                    aggregateCowardsBattleStrength,attackers,
                                    cowards,null,false,true));
                    
                    }
                } 
                else 
                {
                    if (randNum.nextDouble() > .5) 
                    {
                        /*ATTACKERS WIN AND moves to node*/
                        if(isCowards == "red")
                        {
                            /*Blue Wins and remains at their postion*/
                            /*Red Combat Unit dies*/

                        MapEvent.completeCombat(cowards, false, attackers, true, node);
                        
                        JOptionPane.showMessageDialog(null,Global.redPreEmptiveToString + Global.bluePreEmptiveToString
                                + battleStatus(node,aggregateCowardsBattleStrength,
                                    aggregateAttackersBattleStrength,cowards,
                                    attackers,"blue",true,false));
                        }
                        else
                        { 
                            /*Red Wins and moves to node*/
                            /*Blue Combat Unit dies*/
                        MapEvent.completeCombat(attackers, true, cowards, false, node);

                            JOptionPane.showMessageDialog(null,Global.redPreEmptiveToString + Global.bluePreEmptiveToString
                                    + battleStatus(node,aggregateAttackersBattleStrength,
                                    aggregateCowardsBattleStrength,attackers,
                                    cowards,"red",false,true));
                        }                       
                    } 
                    
                    else 
                    {
                        /*COWARDS WIN and end up heading towards their intened position*/
                        if (isCowards == "red")
                        {
                            /*RED (Cowards) UNIT WINS They Proceed their intended spot*/

                            /*blue dies*/
                            MapEvent.completeCombat(cowards, true, attackers, false, node);

                            
                            JOptionPane.showMessageDialog(null,Global.redPreEmptiveToString + Global.bluePreEmptiveToString
                                    + battleStatus(node,aggregateCowardsBattleStrength,
                                    aggregateAttackersBattleStrength,cowards,
                                    attackers,"red",true,false));


                        }
                        else 
                        {
                            /*Blue (Cowards) UNIT WINS They Proceed their intended spot*/
                            /*Red dies*/
                            MapEvent.completeCombat(attackers, false, cowards, true, node);

                            JOptionPane.showMessageDialog(null,Global.redPreEmptiveToString + Global.bluePreEmptiveToString
                                    + battleStatus(node,aggregateAttackersBattleStrength,
                                    aggregateCowardsBattleStrength,attackers,
                                    cowards,"blue",false,true));
                        }
                    }
                }
            } 
            else 
            {
                /*BOTH TEAMS REMAIN AT THEIR ORIGNAL POSTION*/
                if (isCowards == "red")
                {
                
                    MapEvent.successfullFlee(cowards, Scenario.findRoad(node, cowardsPreviousLocation.get(0)), cowardsPreviousLocation.get(0).id);
                    MapEvent.successfullFlee(attackers, null, node.id);

                    
                    
                    JOptionPane.showMessageDialog(null,Global.redPreEmptiveToString + Global.bluePreEmptiveToString
                            + "Red Combat unit was able to retreat!!!\n"+
                    "\nNo battle was taken place");
                    
                }
                else
                {
                    MapEvent.successfullFlee(cowards, Scenario.findRoad(node, cowardsPreviousLocation.get(0)), cowardsPreviousLocation.get(0).id);
                    MapEvent.successfullFlee(attackers,null, node.id);
                    
                    JOptionPane.showMessageDialog(null,Global.redPreEmptiveToString + Global.bluePreEmptiveToString
                            + "Blue Combat unit was able to retreat!!!\n"+
                        "\nNo battle was taken place");
                }
            }
        }

        /*CASE 4: does not exist cause you can not have both defenders*/
    }

    public void PVPNonCommitedBattle(ArrayList<CombatUnit> red, ArrayList<CombatUnit> blue, 
            ArrayList<Node> redsIntendedNode,ArrayList<Node> redsPreviousNode,
            ArrayList<Node> blueIntendedNode, ArrayList<Node> bluePreviousNode) 
    {
        /*This where no battle happens and all CombatUnits return to previous node*/
        /*Not very complicated*/
        
                    MapEvent.successfullFlee(red, Scenario.findRoad(redsIntendedNode.get(0), redsPreviousNode.get(0)), redsPreviousNode.get(0).id);
                    MapEvent.successfullFlee(blue, Scenario.findRoad(blueIntendedNode.get(0), bluePreviousNode.get(0)), bluePreviousNode.get(0).id);
        
        JOptionPane.showMessageDialog(null, "Both Combat Units decide to retreat "+
        "\nNo battle was taken place");
        
        
        
    }

    public void PVPChaseBattle(ArrayList<CombatUnit> attackers, ArrayList<CombatUnit> cowards, 
            ArrayList<Node> attackersDesiredNode,ArrayList<Node> attackersPreviousNode ,
            ArrayList<Node> cowardsDesiredNode,ArrayList<Node> cowardsPreviousNode) 
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
                aggregateAttackerStrength += (int)(double)(attackers.get(i).GetBattleStrengh())*(0.85);
            }
        }
        /*GET BATTLE STRENGTH for Cowards*/
        for (int i = 0; i < cowards.size(); i++) 
        {
            if (!cowards.get(i).isFleet)
            {
                aggregateCowardsStrength += cowards.get(i).GetBattleStrengh();
            }
                else
            {
                aggregateCowardsStrength += (int)(double)(cowards.get(i).GetBattleStrengh())*(0.85);
            }
        }
        
        aggregateCowardsStrength = (int) ((double) (aggregateCowardsStrength) * (0.8));

        if (randNum.nextDouble() > 0.75) /*Cowards are able to flee*/ 
        {
            /* Both Armies move to their intended location*/
            
                    MapEvent.successfullFlee(attackers, Scenario.findRoad(attackersDesiredNode.get(0), attackersPreviousNode.get(0)), attackersDesiredNode.get(0).id);
                    MapEvent.successfullFlee(cowards, Scenario.findRoad(cowardsDesiredNode.get(0), cowardsPreviousNode.get(0)), cowardsDesiredNode.get(0).id);
 
            if(isCowards == "red")
            {
                JOptionPane.showMessageDialog(null, Global.redPreEmptiveToString + Global.bluePreEmptiveToString
                        + "Red Combat Units decide to retreat "
                        + "\nNo battle was taken place, However both units move intended locations");
            }
            else
            {
                JOptionPane.showMessageDialog(null, Global.redPreEmptiveToString + Global.bluePreEmptiveToString
                        + "Blue Combat Units decide to retreat "
                        +"\nNo battle was taken place, However both units move intended locations");
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
                    MapEvent.completeCombat(cowards, false, attackers, true, attackersDesiredNode.get(0));
                    
                    JOptionPane.showMessageDialog(null, Global.redPreEmptiveToString + Global.bluePreEmptiveToString
                            +battleStatus(attackersDesiredNode.get(0),
                                    aggregateCowardsStrength, aggregateAttackerStrength,
                                        cowards,attackers,
                                        null,true,false));
                }
                else
                {
                    /*Red Combat Army Falls in battle Blue Moves into position*/
                    MapEvent.completeCombat(attackers, true, cowards, false, attackersDesiredNode.get(0));

                    JOptionPane.showMessageDialog(null, Global.redPreEmptiveToString + Global.bluePreEmptiveToString+
                            battleStatus(attackersDesiredNode.get(0),
                                    aggregateAttackerStrength, aggregateCowardsStrength,
                                        attackers,cowards,
                                        null,false,true));
                    
                }  
            } 
            else if (aggregateCowardsStrength > aggregateAttackerStrength) 
            {
                /*cowards win and move to their desired location*/
                if(isCowards =="red")
                {
                    /*red team defeats blue and moves to desired lovation*/
                    MapEvent.completeCombat(cowards, true, attackers, false, cowardsDesiredNode.get(0));

                    JOptionPane.showMessageDialog(null, Global.redPreEmptiveToString + Global.bluePreEmptiveToString+
                           battleStatus(cowardsDesiredNode.get(0),
                                    aggregateCowardsStrength, aggregateAttackerStrength,
                                        cowards,attackers,
                                        null,true,false));
                }
                else
                {
                    /*blue team defeats red and moves to desired lovation*/
                    MapEvent.completeCombat(attackers, true, cowards, true, cowardsDesiredNode.get(0));

                    JOptionPane.showMessageDialog(null, Global.redPreEmptiveToString + Global.bluePreEmptiveToString+
                            battleStatus(cowardsDesiredNode.get(0),
                                    aggregateAttackerStrength, aggregateCowardsStrength,
                                        attackers,cowards,
                                        null,false,true));
                }
            } 
            else 
            {
                if (randNum.nextDouble() > 0.5) 
                {
                    /*cowards win*/
                    /*cowards win and move to their desired location*/
                    if(isCowards =="red")
                    {
                        /*red team defeats blue and moves to desired lovation*/
                        MapEvent.completeCombat(cowards, true, attackers, false, cowardsDesiredNode.get(0));

                        JOptionPane.showMessageDialog(null, Global.redPreEmptiveToString + Global.bluePreEmptiveToString+
                               battleStatus(cowardsDesiredNode.get(0),
                                    aggregateCowardsStrength, aggregateAttackerStrength,
                                        cowards,attackers,
                                        "red",true,false));
                    }
                    else
                    {
                        /*blue team defeats red and moves to desired lovation*/
                        MapEvent.completeCombat(attackers, true, cowards, true, cowardsDesiredNode.get(0));

                        JOptionPane.showMessageDialog(null,Global.redPreEmptiveToString + Global.bluePreEmptiveToString+ 
                                battleStatus(cowardsDesiredNode.get(0),
                                    aggregateAttackerStrength, aggregateCowardsStrength,
                                        attackers,cowards,
                                        "blue",false,true));
                    }
                    
                }
                else 
                {
                    /*attackers win*/
                    if(isCowards =="red")
                    {
                        /*Red Combat Army Falls in battle Blue Moves into position*/
                        MapEvent.completeCombat(cowards, false, attackers, true, attackersDesiredNode.get(0));

                        JOptionPane.showMessageDialog(null,Global.redPreEmptiveToString + Global.bluePreEmptiveToString+ 
                                battleStatus(attackersDesiredNode.get(0),
                                    aggregateCowardsStrength, aggregateAttackerStrength,
                                        cowards,attackers,
                                        "blue",true,false));
                    }
                    else
                    {
                        /*Red Combat Army Falls in battle Blue Moves into position*/
                        MapEvent.completeCombat(attackers, true, cowards, false, attackersDesiredNode.get(0));

                        JOptionPane.showMessageDialog(null,Global.redPreEmptiveToString + Global.bluePreEmptiveToString+ 
                                battleStatus(attackersDesiredNode.get(0),
                                    aggregateAttackerStrength, aggregateCowardsStrength,
                                        attackers,cowards,
                                        "red",false,true));

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
        
                JOptionPane.showMessageDialog(null, "Collision Alert!\n"
                        + "There is a collision of armies between " + redCombatUnitPreviousLocation.get(0).name
                        + " and " + redCombatUnitEndLocation.get(0).name);
                
        /*Asking red if they want to do battle*/
        if(IsPreemptive(redCombatUnitEndLocation.get(0),redCombatUnit.get(0).faction.playerID))
        {
            redDecisionToFight = true;
            /*Reds decision to fight is true by default if preemptive*/
            Global.redPreEmptiveToString = "Red Team is force to fight because his move was a pre emptive attack\n";
            
        }
        else
        {
            Global.redPreEmptiveToString = "";
            /*PRRRRRRRRRRRRRRRRRREEEEEEEEEEEEEEEEEEEEEMMMMMM*/
            /*you question box is here*/
            if (Global.opponent == true) /*PVP*/
            {
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
            else if (Global.chosenTeam == false) /*player is red*/
            {
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
            else /*Red is computer AI*/
            {
                if (randNum.nextDouble() > 0.3)
                {
                    redDecisionToFight = true;
                } 
                else 
                {
                    redDecisionToFight = false;
                }
                
            }
        }
       
        if(IsPreemptive(blueCombatUnitEndLocation.get(0),blueCombatUnit.get(0).faction.playerID))
        {
            blueDecisionToFight = true;
            /*Reds decision to fight is true by default if preemptive*/
            Global.bluePreEmptiveToString = "Blue Team is force to fight because his move was a pre emptive attack\n";

        }
        else
        {
            Global.bluePreEmptiveToString = "";
            /*PRRRRRRRRRRRRRRRRRREEEEEEEEEEEEEEEEEEEEEMMMMMM*/
            /*you question box is here*/
            if (Global.opponent == true) /*PVP*/
            {
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
            else if(Global.chosenTeam == true) /*player is Blue*/
            {
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
            else /*Blue is AI*/
            {
                if (randNum.nextDouble() > 0.3)
                {
                    blueDecisionToFight = true;
                } 
                else 
                {
                    blueDecisionToFight = false;
                }
            }
        }

        if (redDecisionToFight && blueDecisionToFight) {
            PVPCommitedBattle(redCombatUnit, blueCombatUnit, redCombatUnitEndLocation, blueCombatUnitEndLocation);
        } else if (!redDecisionToFight && blueDecisionToFight) {
            PVPChaseBattle(blueCombatUnit, redCombatUnit, blueCombatUnitEndLocation,blueCombatUnitPreviousLocation,
                    redCombatUnitEndLocation,redCombatUnitPreviousLocation);

        } else if (redDecisionToFight && !blueDecisionToFight) {
            PVPChaseBattle(redCombatUnit, blueCombatUnit, redCombatUnitEndLocation,redCombatUnitPreviousLocation,
                    blueCombatUnitEndLocation,blueCombatUnitPreviousLocation);
        } else {
            PVPNonCommitedBattle(redCombatUnit, blueCombatUnit, redCombatUnitEndLocation,redCombatUnitPreviousLocation, blueCombatUnitEndLocation, blueCombatUnitPreviousLocation);
        }
    }

    private boolean IsPreemptive(Node location, int playerID) {
        if (Scenario.redPlayer.playerID == playerID) {
            for (ArrayList<Integer> nodeValues : Scenario.redPlayer.enemyIntelligence) {
                if (nodeValues.get(0) == location.id) {
                    if (nodeValues.size() > 1) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        } else {
            for (ArrayList<Integer> nodeValues : Scenario.bluePlayer.enemyIntelligence) {
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
    
    public String battleStatus(Node node, int redBatStr,
            int bluBatStr, ArrayList<CombatUnit> redCombatUnit, 
            ArrayList<CombatUnit> bluCombatUnit, String tieBreaker,
            boolean redFlee, boolean bluFlee)
    {
        String returnStr = "";
        
        int redSmallCount=0;
        int redMedCount=0; 
        int redLargeCount=0;
        
        String redArmyCount ="";
        
        int bluSmallCount=0;
        int bluMedCount=0; 
        int bluLargeCount=0;
        
        String bluArmyCount = "";
        
        /*COUNTING THE ARMIES INVOLVED IN THE BATTLE*/
        
        for(int i=0; i < redCombatUnit.size(); i++ )
        {
            if (redCombatUnit.get(i).size <6)
            {
                redSmallCount++;
            }
            else if (redCombatUnit.get(i).size <11)
            {
                redMedCount++;
            }
            else
            {
                redLargeCount++;
            }
        }
        
        /*IMPLEMENTING THE TO STRING OPERATION*/
        
        if (redSmallCount >0 && redMedCount > 0 && redLargeCount > 0)
        {
            redArmyCount = redSmallCount + "Small Combat Unit(s), " + redMedCount + "Medium Combat Unit(s), " + redLargeCount+ "Large Combat Unit(s) ";
        }
        else if(redSmallCount >0 && redMedCount > 0)
        {
            redArmyCount = redSmallCount + "Small Combat Unit(s), " + redMedCount + "Medium Combat Unit(s) ";
        }
        else if (redSmallCount >0 && redLargeCount > 0)
        {
            redArmyCount = redSmallCount + "Small Combat Unit(s), " + redLargeCount+ "Large Combat Unit(s) ";
        }
        else if  (redMedCount > 0 && redLargeCount > 0)
        {
            redArmyCount = redMedCount + "Medium Combat Unit(s), " + redLargeCount+ "Large Combat Unit(s) ";
        }
        else if(redSmallCount >0)
        {
            redArmyCount = redSmallCount + "Small Combat Unit(s) ";
        }
        else if (redMedCount > 0)
        {
            redArmyCount = redMedCount + "Medium Combat Unit(s) ";
        }
        else
        {
            redArmyCount = redLargeCount+ "Large Combat Unit(s) ";
        }
        
        /*COUNTING THE ARMIES INVOLVED IN THE BATTLE FOR BLUE*/
        
        for(int i=0; i < bluCombatUnit.size(); i++ )
        {
            if (bluCombatUnit.get(i).size <6)
            {
                bluSmallCount++;
            }
            else if (redCombatUnit.get(i).size <11)
            {
                bluMedCount++;
            }
            else
            {
                bluLargeCount++;
            }
        }
        
        /*IMPLEMENTING THE TO STRING OPERATION*/
        
        if (bluSmallCount >0 && bluMedCount > 0 && bluLargeCount > 0)
        {
            bluArmyCount = bluSmallCount + "Small Combat Unit(s), " + bluMedCount + "Medium Combat Unit(s), " + bluLargeCount+ "Large Combat Unit(s) ";
        }
        else if(bluSmallCount >0 && bluMedCount > 0)
        {
            bluArmyCount = bluSmallCount + "Small Combat Unit(s), " + bluMedCount + "Medium Combat Unit(s) ";
        }
        else if (bluSmallCount >0 && bluLargeCount > 0)
        {
            bluArmyCount = bluSmallCount + "Small Combat Unit(s), " + bluLargeCount+ "Large Combat Unit(s) ";
        }
        else if  (bluMedCount > 0 && bluLargeCount > 0)
        {
            bluArmyCount = bluMedCount + "Medium Combat Unit(s), " + bluLargeCount+ "Large Combat Unit(s) ";
        }
        else if(bluSmallCount >0)
        {
            bluArmyCount = bluSmallCount + "Small Combat Unit(s) ";
        }
        else if (bluMedCount > 0)
        {
            bluArmyCount = bluMedCount + "Medium Combat Unit(s) ";
        }
        else
        {
            bluArmyCount = bluLargeCount+ "Large Combat Unit(s) ";
        }
        
        
        String magnitudeOfVictory ="";
        if (Math.abs(bluBatStr - redBatStr)>35)
        {
            magnitudeOfVictory = " Decimated ";
        }
        else if (Math.abs(bluBatStr - redBatStr)>25)
        {
            magnitudeOfVictory = " Crushed ";
        }
        else if (Math.abs(bluBatStr - redBatStr)>15)
        {
            magnitudeOfVictory = " Defeated ";
        }
        else
        {
            magnitudeOfVictory = " Overcame ";
        }
        
    
        if (redFlee)
        {
            if(redBatStr > bluBatStr)
            {
                returnStr = "In the midst of Red Team fleeing with thier " + redArmyCount + 
                        ", \nBlue's army caught up to Red army with thier\n"
                        + bluArmyCount +".\n"
                        + "But Red team turned arount and"+magnitudeOfVictory+"\n"
                        + " them then moved to "+ node.name;
            }
            else if (redBatStr < bluBatStr)
            {
                returnStr = "While chasing the Red Army, Blue's army caught up with their\n"
                        + bluArmyCount + "and" +magnitudeOfVictory+ "Red's Army of\n"
                        + redArmyCount + "then proceeded to "+node.name;
            }
            else
            {
                if (tieBreaker == "red")
                {
                    returnStr = "Blue team with their "+ bluArmyCount +"caught up to Red's Army of"
                            + redArmyCount + "but Red\n"
                            + "turned arount and fought back. It was a close battle,\n"
                            + "but Red Team was victorious and moved to " + node.name;
                }
                else
                {
                    returnStr = "Blue team with their "+ bluArmyCount +"caught up to Red's Army of"
                            + redArmyCount + "but Red\n"
                            + "turned arount and fought back. It was a close battle,\n"
                            + "but Blue Team was victorious and moved to " + node.name;
                }
            }
        }
        else if (bluFlee)
        {
            if(redBatStr < bluBatStr)
            {
                returnStr = "In the midst of blue fleeing thier " + bluArmyCount + 
                        ", \nRed's army caught up to Blue's army with thier\n"
                        + redArmyCount +".\n "
                        + "But Blue team turned arount and "+magnitudeOfVictory+"\n"
                        + " them then moved to "+ node.name;
            }
            else if (redBatStr > bluBatStr)
            {
                returnStr = "While chasing the Blue Army, Red's army caught up with their\n"
                        + redArmyCount + "and" +magnitudeOfVictory+ "Blue's Army of\n"
                        + bluArmyCount + "then proceeded to "+node.name;
            }
            else
            {
                if (tieBreaker == "red")
                {
                    returnStr = "Red team with their "+ redArmyCount +"caught up to Blue's Army of"
                            + bluArmyCount + "but Blue\n"
                            + "turned arount and fought back. It was a close battle,\n"
                            + "but Red Team was victorious and moved to " + node.name;
                }
                else
                {
                    returnStr = "Red team with their "+ redArmyCount +"caught up to Blue's Army of"
                            + bluArmyCount + "but Blue\n"
                            + "turned arount and fought back. It was a close battle,\n"
                            + "but Blue Team was victorious and moved to " + node.name;
                }
            }
        }
        else
        {
            if(redBatStr < bluBatStr)
            {
                returnStr = "Blue Team arrives with " + bluArmyCount + ""
                        + "\nand Red Team arrives with"+ redArmyCount +
                        "\nBlue Team " + magnitudeOfVictory + "Red Team"
                        + "and moves to "+ node.name;
            }
            else if (redBatStr > bluBatStr)
            {
                returnStr = "Blue Team arrives with " + bluArmyCount + ""
                        + "\nand Red Team arrives with"+ redArmyCount +
                        "\nRed Team " + magnitudeOfVictory + "Blue Team"
                        + "and moves to "+ node.name;
            }
            else
            {
                if (tieBreaker == "red")
                {
                   returnStr = "Blue Team arrives with " + bluArmyCount + ""
                        + "\nand Red Team arrives with"+ redArmyCount +
                        "\nIt was a close battle...\n"
                        + "Red Team " + magnitudeOfVictory + "Blue Team"
                        + "and moves to "+ node.name; 
                }
                else
                {
                    returnStr = "Blue Team arrives with " + bluArmyCount + ""
                        + "\nand Red Team arrives with"+ redArmyCount +
                        "\nIt was a close battle...\n"
                        + "Blue Team " + magnitudeOfVictory + "Red Team"
                        + "and moves to "+ node.name;
                }
            }
        }
        
        return returnStr;
    }
}


