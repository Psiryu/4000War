
import java.util.ArrayList;
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

    // Placeholders for combat units partaking in battle
    Random randNum = new Random();

    /*
    String[] report;
    Node battleLocation;
    int turnNo;
    */
    

    // for the case of battle occuring on a node
    public void PVPdoCampBattleOnNode (Node node, ArrayList<CombatUnit> redCombatUnit, ArrayList<Node> redCombatUnitPreviousLocation, 
            ArrayList<Node> redCombatUnitEndLocation, ArrayList<CombatUnit> blueCombatUnit, 
            ArrayList<Node> blueCombatUnitPreviousLocation, ArrayList<Node> blueCombatUnitEndLocation)
    { /*THE DECISION MAKER FUNCTION FOR COLLISIONS ON A NODE*/
        
        boolean redDecisionToFight= false;
        boolean blueDecisionToFight = false;
        boolean blueDefender=false;
        boolean redDefender = false;
        
        
        for(int i = 0; i < blueCombatUnitPreviousLocation.size(); i++ )
        {
            if (blueCombatUnitPreviousLocation.get(i).id == blueCombatUnitEndLocation.get(i).id )
            {
                blueDefender = true;
            }
        }
        
        for (int i = 0 ; i < redCombatUnitPreviousLocation.size();i++ )
        {
            if(redCombatUnitPreviousLocation.get(i).id == redCombatUnitEndLocation.get(i).id)
            {
                redDefender = true;
            }
        }
            
        
        /*POINT TO WHERE THE COLLISION IS ON THE MAP*/
            /*UI CONTROL*/
        
        /*IF RED MOVEMENT IS CATAGORIZED AS PRE EMPTIVE*/
            
            /*RED IS FORCED TO SELECT ATTACK*/
        
            /*CODE redDecisionToFight = true; */
        
        /*ELSE*/
            /*ASK RED IF THEY WANT TO FLEE */
            
                /*IF FIGHT*/
                /*CODE redDecisionToFight = true; */

                /*ELSE*/
                /*CODE redDecisionToFight = false; */

        
        
        /*IF BLUE MOVEMENT IS CATAGORIZED AS PRE EMPTIVE*/
        
            /*BLUE IS FORCED TO ATTACK*/

            /*CODE blueDecisionToFight = true; */

        
        /*ELSE*/
            
            /*ASK BLUE IF THEY WANT TO FLEE OR FIGHT*/
            
                /*IF FIGHT*/
                /*CODE blueDecisionToFight = true; */
                
                /*IF FLEE*/
                /*CODE blueDecisionToFight = false; */

        
        if(blueDecisionToFight && redDecisionToFight)
        {
            /*Both armies commit to battle*/

            PVPCommitedBattle(node, redCombatUnit, blueCombatUnit,redCombatUnitEndLocation,blueCombatUnitEndLocation );
        }
        else if (!blueDecisionToFight && redDecisionToFight)
        {
            /*if Red wants to fight but blue does not*/
            PVPHalfCommitedBattleOnNode(node,redCombatUnit, blueCombatUnit, redCombatUnitPreviousLocation,redCombatUnitEndLocation,blueCombatUnitPreviousLocation);
        }
        else if (blueDecisionToFight && !redDecisionToFight)
        {
           /*if Blue wants to fight but Red does not*/
            PVPHalfCommitedBattleOnNode(node,blueCombatUnit, redCombatUnit,blueCombatUnitPreviousLocation,blueCombatUnitEndLocation,redCombatUnitPreviousLocation);

            //PVPCommitedBattle(node, red, blue);
        }
        else if (!blueDecisionToFight && redDecisionToFight)
        {
            /*if Red wants to figh but blue does not*/
            //PVPHalfCommitedBattle(node,red, blue);
        }
        else if (blueDecisionToFight && !redDecisionToFight)
        {
           /*if Blue wants to figh but Red does not*/
            //PVPHalfCommitedBattle(node,blue, red);

        }
        else 
        {
            /*When No one wants to fight*/

            PVPNonCommitedBattle(node,redCombatUnit,blueCombatUnit,redCombatUnitEndLocation,blueCombatUnitPreviousLocation);
           //PVPNonCommitedBattle(node,red,blue);

        }
    }
    
    public void PVPCommitedBattle(Node node, ArrayList<CombatUnit> red , ArrayList<CombatUnit> blue, ArrayList<Node> redsIntendedNode,
            ArrayList<Node> blueIntendedNode)
    {
        /*STANDARD BATTLE*/
        /*WINNER MOVES TO HIS DESIRED LOCATION*/
    
        int redAggregateStrength = 0 ;
        int blueAggregateStrength = 0 ;
        
        for(int i = 0; i < red.size();i++)
        {
            redAggregateStrength += red.get(i).GetBattleStrengh();
        }
        
        for(int i = 0; i < blue.size();i++)
        {
            blueAggregateStrength += blue.get(i).GetBattleStrengh();
        }
        
        if(redAggregateStrength < blueAggregateStrength)
        {
            /*Blue wins and moves to their desired location*/
        }
        else if (redAggregateStrength > blueAggregateStrength)
        {
            /*Red wins and moves to their desired location*/
        }
        else
        {
            if(randNum.nextDouble() >0.5)
            {
                /*Red wins and moves to their desired location*/
            }
            else
            {
                /*Blue wins and moves to their desired location*/
            }
        }
        
        
    }
    
    public void PVPHalfCommitedBattleOnNode(Node node, ArrayList<CombatUnit> attackers, ArrayList<CombatUnit> cowards 
            , ArrayList<Node> attackerPreviousLocation,ArrayList<Node> attackerIntendedLocation, ArrayList<Node> cowardsPreviousLocation)
    {
        int retreatLocationCount=0;
        Road[] fleeingRoads=  new Road[100];
        Node[] fleeingNodes = new Node[100];
        
        
        boolean isCowardDefender = false;
        boolean isAttackerDefender = false;
        
        int aggregateAttackersBattleStrength = 0;
        int aggregateCowardsBattleStrength = 0 ;
        
        /*GET BATTLE STRENGTH for  Attackers*/
        for (int i = 0 ; i < attackers.size(); i ++ )
        {
            aggregateAttackersBattleStrength += attackers.get(i).GetBattleStrengh();
        }
        /*GET BATTLE STRENGTH for Cowards*/
        for (int i = 0 ; i < cowards.size(); i ++ )
        {
            aggregateCowardsBattleStrength += cowards.get(i).GetBattleStrengh();
        }        
        
        aggregateCowardsBattleStrength = (int)((double)(aggregateCowardsBattleStrength)* 0.8);
        
        /*We have to determine is the Cowards is a defender*/
        for(int i = 0; i < cowards.size(); i++)
        {
            if (cowardsPreviousLocation.get(i).id == node.id)
            {
                isCowardDefender = true; 
            }
            
        }
        
        for(int i = 0; i < attackers.size(); i++)
        {
            if (attackerPreviousLocation.get(i).id == node.id)
            {
                isAttackerDefender = true; 
            }
            
        }
        
        
        /*CASE 1 : No defenders*/
        if (!isCowardDefender && !isAttackerDefender)
        {
            
            if (randNum.nextDouble() > 0.7) /*30% chance they can flee with no battle*/
            {
                /*COWARDS FLEE TO PREVIOUS LOCATION WITH NO BATTLB*/

            }
            else
            {
               if (aggregateCowardsBattleStrength > aggregateAttackersBattleStrength)
               {
                   /*COWARDS WIN and end up heading towards their intened position*/
               }
               else if (aggregateCowardsBattleStrength < aggregateAttackersBattleStrength)
               {
                   /*ATTACKERS WIN AT TAKE POSITION*/
               }
               else
               {
                   if (randNum.nextDouble() > .5)
                   {
                       /*ATTACKERS WIN AT TAKE POSITION*/
                   }
                   else
                   {
                       /*COWARDS WIN and end up heading towards their intened position*/
                   }
               }
            }
        }
        /*CASE 2 Cowards are defenders*/
        else if(!isAttackerDefender && isCowardDefender)
        {
            if (randNum.nextDouble() > 0.3) /*70% chance they can  NOT flee */
            {
                if (aggregateCowardsBattleStrength > aggregateAttackersBattleStrength)
               {
                   /*COWARDS WIN and end up heading towards their intened position THE SAME POSITION*/
               }
               else if (aggregateCowardsBattleStrength < aggregateAttackersBattleStrength)
               {
                   /*ATTACKERS WIN AT TAKE POSITION*/
               }
               else
               {
                   if (randNum.nextDouble() > .5)
                   {
                       /*ATTACKERS WIN AT TAKE POSITION*/
                   }
                   else
                   {
                       /*COWARDS WIN and end up heading towards their intened position THE SAME POSTION*/
                   }
               }

            }
            else   /* THEY CAN FLEE*/
            {
                ArrayList<Road> adjacentRoadsToNode = new ArrayList<Road>();
                int maxCowardsSize = 0;
                
                for(int i = 0 ; i < cowards.size();i++)
                {
                    if(maxCowardsSize < cowards.get(i).size )
                    {
                        maxCowardsSize = cowards.get(i).size;
                    }
                }
                
                for (int i = 0 ; i < Scenario.listOfRoads.length;i++)
                {
                    if(Scenario.listOfRoads[i].locationA.id == node.id || Scenario.listOfRoads[i].locationB.id == node.id)
                    {
                        if (maxCowardsSize <= Scenario.listOfRoads[i].capacity)
                        {
                            adjacentRoadsToNode.add(Scenario.listOfRoads[i]);
                    
                        }
                
                    }
                }
                if(adjacentRoadsToNode.size() == 0)
                {
                    /*THEY DIE*/
                }
                else
                {
                    /*RANDOMLY CHOOSES A ROAD TO TRAVEL*/
                int roadChoice = randNum.nextInt(adjacentRoadsToNode.size());
                
                /*Move ATTACKERS to intended node and Cowards to Random Node*/
            
                }
            }
        }
        /*CASE 3: Attackers are defenders (attackers did not move but have commited to battle)*/
        else
        {
            if (randNum.nextDouble() > 0.3) /*Cowards are not able to flee*/
            {
                if (aggregateCowardsBattleStrength > aggregateAttackersBattleStrength)
               {
                   /*COWARDS WIN and end up heading towards their intened position*/
               }
               else if (aggregateCowardsBattleStrength < aggregateAttackersBattleStrength)
               {
                   /*ATTACKERS WIN AND REMAIN AT THEIR POSITION*/
               }
               else
               {
                   if (randNum.nextDouble() > .5)
                   {
                        /*ATTACKERS WIN AND REMAIN AT THEIR POSITION*/
                   }
                   else
                   {
                       /*COWARDS WIN and end up heading towards their intened position*/
                   }
               }
            }
            else
            {
                /*BOTH TEAMS REMAIN AT THEIR ORIGNAL POSTION*/
            }
        }
        
        /*CASE 4: does not exist cause you can not have both defenders*/
    
    }
    
    public void PVPNonCommitedBattle(Node node, ArrayList<CombatUnit> red , ArrayList<CombatUnit> blue, ArrayList<Node> redsIntendedNode,
            ArrayList<Node> blueIntendedNode)
    {
        /*This where no battle happens and all CombatUnits return to previous node*/
               
        /*Not very complicated*/
    
    }
    
    public void PVPChaseBattle(Node node, ArrayList<CombatUnit> attackers, ArrayList<CombatUnit> cowards)
    {
        /*This happpens when the play collide on Road and one should flee*/
        int aggregateAttackerStrength= 0;
        int aggregateCowardsStrength= 0;
        
        for(int i = 0; i < attackers.size();i++)
        {
            aggregateAttackerStrength += attackers.get(i).GetBattleStrengh();
        }
        
        for(int i = 0; i < cowards.size();i++)
        {
            aggregateCowardsStrength += cowards.get(i).GetBattleStrengh();
        }
            aggregateCowardsStrength = (int)((double)aggregateCowardsStrength *0.8);
        
        if (randNum.nextDouble() > 0.7) /*Cowards are able to flee*/
        {
            /* Both Armies move to their intended location*/
        }
        else /* Flee fails*/
        {
            if(aggregateCowardsStrength < aggregateAttackerStrength)
            {
                /*Attackers win and move thier desired location*/
            }
            else if(aggregateCowardsStrength > aggregateAttackerStrength)
            {
                /*cowards win and move to their desired location*/
            }
            else
            {
                if (randNum.nextDouble() >0.5)
                {
                    /*Attackers win and move thier desired location*/
                }
                else
                {
                    /*cowards win and move to their desired location*/
                }
            }
        }
    }
    
    
    // for the case of a collision battle on a road
    public void doBattleOnRoad (ArrayList<CombatUnit> redCombatUnit, ArrayList<Node> redCombatUnitPreviousLocation, 
            ArrayList<Node> redCombatUnitEndLocation, ArrayList<CombatUnit> blueCombatUnit, 
            ArrayList<Node> blueCombatUnitPreviousLocation, ArrayList<Node> blueCombatUnitEndLocation)
    {
        boolean preemptiveRed = false;
        boolean preemptiveBlue = false;
        
        
        
        // decides how many people were engaging in combat

    }
}
