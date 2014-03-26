
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

        JOptionPane.showMessageDialog(null, "Collision Alert!!!!!\n"
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
            
            JOptionPane.showMessageDialog(null, "Red Team is force to fight because his move was a pre emptive attack");
        }
        else
        {
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
            
            JOptionPane.showMessageDialog(null, "Blue Team is force to fight because his move was a pre emptive attack");
        }
        else
        {
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


            JOptionPane.showMessageDialog(null, "Blue wins and moves to their desired location");
           
            
        } 
        else if (redAggregateStrength > blueAggregateStrength) 
        {
            /*Red wins and moves to their desired location*/
            MapEvent.completeCombat(red, true, blue, false, redsIntendedNode.get(0));

           JOptionPane.showMessageDialog(null, "Red wins and moves to their desired location");
          

        } 
        else 
        {
            if (randNum.nextDouble() > 0.5) {
                /*Red wins and moves to their desired location*/
                MapEvent.completeCombat(red, true, blue, false, redsIntendedNode.get(0));

           JOptionPane.showMessageDialog(null, "Red wins and moves to their desired location");
            } 
            else 
            {
                /*Blue wins and moves to their desired location*/
                MapEvent.completeCombat(red, false, blue, true, blueIntendedNode.get(0));
            }
            JOptionPane.showMessageDialog(null, "Blue wins and moves to their desired location");
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
                    JOptionPane.showMessageDialog(null, "Red flees battle while Blue moves into their intended postion");
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Blue flees battle while Red moves into their intended postion");

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

                    JOptionPane.showMessageDialog(null, "Blue loses battle!! Their armies fall and Red moves into intended position");

                    }
                    else
                    { /*Cowards is blue for the case that cowards win*/ 

                        /*if cowards is blue then red loses their armies*/
                        MapEvent.completeCombat(attackers, false, cowards, true, node);

                        
                    JOptionPane.showMessageDialog(null, "Red loses battle!! Their armies fall and Blue moves into intended position");
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
                        
                        
                    JOptionPane.showMessageDialog(null, "Red is unable to Flee!! Red loses battle!!"
                            + " Their armies fall and Blue moves into intended position\n");

                    }
                    else 
                    {
                        /*Red Team wins Blue team Cowards armies will die*/
                        
                        MapEvent.completeCombat(attackers, true, cowards, false, node);
                        
                        
                    JOptionPane.showMessageDialog(null, "Blue is unable to Flee!! Blue loses battle!!\n"
                            + " Their armies fall and Red moves into intended position");
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

                            JOptionPane.showMessageDialog(null, "Red is unable to Flee!! Red loses battle!!\n"
                                    + "Their armies fall and Blue moves into intended position");
                        }
                        else
                        {
                            MapEvent.completeCombat(attackers, true, cowards, false, node);

                            JOptionPane.showMessageDialog(null, "Blue is unable to Flee!! Blue loses battle!!\n"
                                    + " Their armies fall and Red moves into intended position");
                        }
                    } 
                    else 
                    {
                        /*COWARDS WIN and end up heading towards their intened position*/
                        if (isCowards == "red") 
                        {
                            MapEvent.completeCombat(cowards, true, attackers, false, node);
                            
                            JOptionPane.showMessageDialog(null, "Blue loses battle!!\n"
                                    + "Their armies fall and Red moves into intended position");
                        }
                        else
                        { /*Cowards is blue*/ 
                            
                            MapEvent.completeCombat(attackers, false, cowards, true, node);

                            JOptionPane.showMessageDialog(null, "Red loses battle!!\n"
                                    + "Their armies fall and Blue moves into intended position");
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

                    JOptionPane.showMessageDialog(null, "Blue loses battle!!\n"
                            + "Their armies fall and Red moves into the position where the battle took place");
                    }
                    else
                    {
                        /*Attcker Red Dies*/
                        MapEvent.completeCombat(attackers, false, cowards, true, node);

                        JOptionPane.showMessageDialog(null, "Red loses battle!!\n"
                            + "Their armies fall and Blue moves into the position where the battle took place");
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
                        JOptionPane.showMessageDialog(null, "Red is unable to flee!! Blue wins battle!!\n"
                                + "Their armies fall and Blue moves into the position where the battle took place");
                    }
                    else
                    {
                        /*Blue Combat Unit will fall and Red will move into position*/
                        /*Coward Blue Dies*/
                        
                        MapEvent.completeCombat(attackers, true, cowards, false, node);
                        
                        /* Red (attackers) moves into postion */

                        JOptionPane.showMessageDialog(null, "Blue is unable to flee!! Red wins battle!!\n"
                                + "Their armies fall and Red moves into the position where the battle took place");
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

                            JOptionPane.showMessageDialog(null, "Red is unable to flee!! Blue wins battle!!"
                                    + "Their armies fall and Blue moves into the position where the battle took place");
                        }
                        else
                        {
                            /*Blue Combat Unit will fall and Red will move into position*/
                            /*Coward Blue Dies*/
                            MapEvent.completeCombat(attackers, true, cowards, false, node);

                            JOptionPane.showMessageDialog(null, "Blue is unable to flee!! Red wins battle!!\n"
                                    + "Their armies fall and Red moves into the position where the battle took place");
                        }  
                    } 
                    else 
                    {
                        /*COWARDS WIN and end up heading towards their intened position THE SAME POSITION AS NODE*/
                        if(isCowards == "red")
                        {
                            /*Attacker Blue dies*/
                            MapEvent.completeCombat(cowards, true, attackers, false, node);

                            JOptionPane.showMessageDialog(null, "Blue loses battle!!\n"
                                    + "Their armies fall and Red moves into the position where the battle took place");
                        }
                        else
                        {
                            /*Attcker Red Dies*/
                            MapEvent.completeCombat(attackers, false, cowards, true, node);

                            JOptionPane.showMessageDialog(null, "Red loses battle!!\n"
                                    + "Their armies fall and Blue moves into the position where the battle took place");
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
                        
                        JOptionPane.showMessageDialog(null, "Red are unable flees from their position therefore their unit is defeated!!!"+
                                "\nBlue proceeds to intended postion");
                    }
                    else
                    {   
                        /*Coward is blue and they die*/
                        MapEvent.completeCombat(attackers, true, cowards, false, node);

                       JOptionPane.showMessageDialog(null, "Blue are unable flees from their position therefore their unit is defeated!!!"+
                                "\nRed proceeds to intended postion");
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
                        JOptionPane.showMessageDialog(null, "Red flees from their position!!!\n"+
                        "\nBlue proceeds to intended postion and no battle is taken place");
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Blue flees from their position!!!\n"+
                        "\nRed proceeds to intended postion and no battle is taken place");
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

                        JOptionPane.showMessageDialog(null, "Red Combat Unit defeats Blue Combat Unit!!!\n"+
                        "\nRed will move to thier defended position");
                    }
                    else 
                    {
                        /*Blue (Cowards) UNIT WINS They Proceed their intended spot*/
                        /*Red dies*/
                        
                        MapEvent.completeCombat(attackers, false, cowards, true, node);

    
                      JOptionPane.showMessageDialog(null, "Blue Combat Unit defeats Red Combat unit !!!\n"+
                        "\nBlue will move to thier defended position");
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

                        
                    JOptionPane.showMessageDialog(null, "Blue Combat Unit defends their position and defeat Red!!!"+
                        "\nBlue move to the defended position");
 
                    }
                    else
                    { 
                        /*Red Wins and moves to node*/
                        /*Blue Combat Unit dies*/
                        
                        MapEvent.completeCombat(attackers, true, cowards, false, node);

                        
                        JOptionPane.showMessageDialog(null, "Red Combat Unit defends their position and defeat Blue!!!\n"+
                            "\nRed move to the defended position");
                    
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
                        
                        JOptionPane.showMessageDialog(null, "Blue Combat Unit defends their position and defeats Red!!!\n"+
                            "\nBlue move to the defended position");
                        }
                        else
                        { 
                            /*Red Wins and moves to node*/
                            /*Blue Combat Unit dies*/
                        MapEvent.completeCombat(attackers, true, cowards, false, node);

                            JOptionPane.showMessageDialog(null, "Red Combat Unit defends their position and defeat Blue!!!\n"+
                                "\nRed move to the defended position");
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

                            
                            JOptionPane.showMessageDialog(null, "Red Combat Unit defeat Blue Combat Unit!!!\n"+
                            "\nRed will move to thier intended position");


                        }
                        else 
                        {
                            /*Blue (Cowards) UNIT WINS They Proceed their intended spot*/
                            /*Red dies*/
                            MapEvent.completeCombat(attackers, false, cowards, true, node);

                            JOptionPane.showMessageDialog(null, "Blue Combat Unit defeat Red Combat !!!\n"+
                                "\nBlue will move to thier intended position");
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

                    
                    
                    JOptionPane.showMessageDialog(null, "Red Combat unit was able to retreat!!!\n"+
                    "\nNo battle was taken place");
                    
                }
                else
                {
                    MapEvent.successfullFlee(cowards, Scenario.findRoad(node, cowardsPreviousLocation.get(0)), cowardsPreviousLocation.get(0).id);
                    MapEvent.successfullFlee(attackers,null, node.id);
                    
                    JOptionPane.showMessageDialog(null, "Blue Combat unit was able to retreat!!!\n"+
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
                JOptionPane.showMessageDialog(null, "Red Combat Units decide to retreat "
                        + "\nNo battle was taken place, However both units move intended locations");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Blue Combat Units decide to retreat "
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
                    
                    JOptionPane.showMessageDialog(null, "Red Units Fall in battle"+
                    "\nBlue Units move to their intended location");
                }
                else
                {
                    /*Red Combat Army Falls in battle Blue Moves into position*/
                    MapEvent.completeCombat(attackers, true, cowards, false, attackersDesiredNode.get(0));

                    JOptionPane.showMessageDialog(null, "Blue Units Fall in battle"+
                    "\nRed Units move to their intended location");
                    
                }  
            } 
            else if (aggregateCowardsStrength > aggregateAttackerStrength) 
            {
                /*cowards win and move to their desired location*/
                if(isCowards =="red")
                {
                    /*red team defeats blue and moves to desired lovation*/
                    MapEvent.completeCombat(cowards, true, attackers, false, cowardsDesiredNode.get(0));

                    JOptionPane.showMessageDialog(null, "Blue Units Fall in battle"+
                    "\nRed Units move to their intended location");
                }
                else
                {
                    /*blue team defeats red and moves to desired lovation*/
                    MapEvent.completeCombat(attackers, true, cowards, true, cowardsDesiredNode.get(0));

                    JOptionPane.showMessageDialog(null, "Red Units Fall in battle"+
                    "\nBlue Units move to their intended location");
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

                        JOptionPane.showMessageDialog(null, "Blue Units Fall in battle"+
                        "\nRed Units move to desired position");
                    }
                    else
                    {
                        /*blue team defeats red and moves to desired lovation*/
                        MapEvent.completeCombat(attackers, true, cowards, true, cowardsDesiredNode.get(0));

                        JOptionPane.showMessageDialog(null, "Red Units Fall in battle"+
                        "\nBlue Units move to desired position");
                    }
                    
                }
                else 
                {
                    /*attackers win*/
                    if(isCowards =="red")
                    {
                        /*Red Combat Army Falls in battle Blue Moves into position*/
                        MapEvent.completeCombat(cowards, false, attackers, true, attackersDesiredNode.get(0));

                        JOptionPane.showMessageDialog(null, "Red Units Fall in battle"+
                        "\nBlue Units move to desired position");
                    }
                    else
                    {
                        /*Red Combat Army Falls in battle Blue Moves into position*/
                        MapEvent.completeCombat(attackers, true, cowards, false, attackersDesiredNode.get(0));

                        JOptionPane.showMessageDialog(null, "Blue Units Fall in battle"+
                        "\nRed Units move to desired position");

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
        
                JOptionPane.showMessageDialog(null, "Collision Alert!!!!!\n"
                        + "There is a collision of armies between " + redCombatUnitPreviousLocation.get(0).name
                        + " and " + redCombatUnitEndLocation.get(0).name);
                
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
            
            JOptionPane.showMessageDialog(null, "Blue Team is force to fight because his move was a pre emptive attack");
        }
        else
        {
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
                nodeValues = Scenario.bluePlayer.enemyIntelligence.get(i);
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
