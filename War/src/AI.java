
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Prem
 */
//constructor
public class AI {
    public void AI() {
        //creates a temporary arraylist so as to not require two loops
        //based on which team the human player chose
        ArrayList<CombatUnit> robotLegion;
        if(Global.chosenTeam == false)
            robotLegion = Scenario.bluePlayer.combatUnits;
        else
            robotLegion = Scenario.bluePlayer.combatUnits;
    
        //sends it to the initial method
        WhereToMove(robotLegion);
    }
    
    //this method determines the possible movements
    private void WhereToMove(ArrayList<CombatUnit> robotLegion) {
        //creates an array with the size of the amount of nodes
        //array indexing follows node id's from 0 up
        int[] weighting = new int[Scenario.listOfNodes.length];
        
        //first loop goes through each individual combat unit
        for(CombatUnit killBots : robotLegion) {
            //for evey road within the scenario
            for(Road road : Scenario.listOfRoads) {
                //creates and checks if the road location id's are the same
                //as the current combat unit's location id
                int location = killBots.GetLocation().id;
                if(road.locationA.id == location || road.locationB.id == location) {
                    
                }
            }
        }
    }
    
    //this method finalizes the movement to send to MapEvents
    private void FinalizeMovement() {
        
    }
}
