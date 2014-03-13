
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
    CombatUnit[] Red;
    CombatUnit[] Blue;

    /*
    String[] report;
    Node battleLocation;
    int turnNo;
    */
    
    // calculate the total strength of all armies on one side of the battle
    public int getTotalBattleStrength(CombatUnit[] cUnit) {
        int total = 0; // initialize the total value
        
        for (int i = 0; i < cUnit.length; i++) { // sum all battle strengths
            total += cUnit[i].GetBattleStrengh();
            /*add weather effects*/
        }
        return total;

    }
    
    // for the case of battle occuring on a node
    public void PVPdoCampBattleOnNode (Node node,CombatUnit[] red, CombatUnit[] blue)
    { /*THE DECISION MAKER FUNCTION FOR COLLISIONS ON A NODE*/
        
        boolean redDecisionToFight= false;
        boolean blueDecisionToFight = false;
        
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
            PVPCommitedBattle(node, red, blue);
        }
        else if (!blueDecisionToFight && redDecisionToFight)
        {
            /*if Red wants to figh but blue does not*/
            PVPHalfCommitedBattle(node,red, blue);
        }
        else if (blueDecisionToFight && !redDecisionToFight)
        {
           /*if Blue wants to figh but Red does not*/
            PVPHalfCommitedBattle(node,blue, red);
        }
        else 
        {
            /*When No one wants to fight*/
            PVPNonCommitedBattle(node,red,blue);
        }
    }
    
    public void PVPCommitedBattle(Node node, CombatUnit[] red , CombatUnit[] blue)
    {
        
        /*STANDARD BATTLE*/
        /*WINNER MOVES TO HIS DESIRED LOCATION*/
    
    }
    
    public void PVPHalfCommitedBattle(Node node, CombatUnit[] attackers, CombatUnit[] cowards)
    {
        int retreatLocationCount=0;
        Road[] fleeingRoads=  new Road[100];
        Node[] fleeingNodes = new Node[100];
        Random randNum = new Random();
        
        /*FOR EACH ROAD CONNECTING TO NODE*/
        
            /*IF LOCATION IS FLEEABLE(), possible opportunity for funtion*/
                /*CODE: retreatLocationCount++; */
                /*ADD ROAD AND NODE TO RESPECTIVE ARRAY WITH SAME INDEX*/
                
        /*End For*/
        
        if (retreatLocationCount>0)
        {
            /*COWARDS RETREAT TO RAMDOM LOCATION*/
            /*APPLY PENALTIES TO COWARDS*/
            
        }
        else
        {
            /*COWARDS DIE*/
        }
        
        /* ATTACKERS MOVE INTO POSTITION*/
        
       
    
    }
    
    public void PVPNonCommitedBattle(Node node, CombatUnit[] red, CombatUnit[] blue)
    {
        /*This where no battle happens and all CombatUnits return to previous node*/
        
        
        /*Not very complicated*/
    
    }
    
    public void PVPChaseBattle(Node node, CombatUnit[] attackers, CombatUnit[] cowards)
    {
        /*This happpens when the play collide on Road and one should flee*/
        int attackerStrengh= 0;
        int cowardsStrength= 0;
        
        /*Gather combat strength from ATTACKERS and COWARDS*/
        /*USE GET COMBAT STRENGTH*/
        /*APPLY PENALTY TO COWARD*/
        
        /*THEY DO BATTLE*/
    
    }
    
    
    // for the case of a collision battle on a road
    public void doBattleOnRoad (boolean preemptiveRed, boolean preemptiveBlue)
    {
        // decides how many people were engaging in combat
        if (preemptiveRed == true && preemptiveBlue == true)
        {
            // simply do battle  
            /*call PVPHalfCommitedBattle*/
        }
        else if (preemptiveRed == true && preemptiveBlue == false)
        {
            // team red would wait unitll
            // team blue makes choice to retreat or proceed to battle
            
                /*if blue selects fight */
                /*call PVPHalfCommitedBattle*/
                /*else*/
                /*call PVPChaseBattle*/
            
        }
        else if (preemptiveRed == false && preemptiveBlue == true)
        {
            // team ble would wait unitll
            // team red makes choice to retreat or proceed to battle
            
                /*if red selects fight */
                /*call PVPHalfCommitedBattle*/
                /*else*/
                /*call PVPChaseBattle*/
        }
        else if (preemptiveRed == false && preemptiveBlue == false)
        {
            // both teams makes choice to retreat or proceed to battle
            // if one should should not attack call PVPChaseBattle with the non
            // attacker as the COWARD
            // If both do not attack call  PVPNonCommitedBattle
        }
        /*
        
        */
    }

    // general do battle method
    public int doBattle(CombatUnit[] red, CombatUnit[] blue, int redPenalty, int bluePenalty) 
    {
        if ((getTotalBattleStrength(red)-redPenalty) > (getTotalBattleStrength(blue)-bluePenalty))
        {
            /*red wins*/
            return 0;
        } else if ((getTotalBattleStrength(red)-redPenalty) < (getTotalBattleStrength(blue)-bluePenalty)) 
        {
            /*blue wins*/
            return 1;
        } else {
            /*tie*/
            return 2;
        }
    }
    public boolean isFleeable(Node node, CombatUnit[] red, CombatUnit[] blue)
    {
        for (int i = 0 ; i < Scenario.listOfNodes.length;i++)
            if (Scenario.listOfNodes[i].id)
    }
}
