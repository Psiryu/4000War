
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
        Player robots;
        ArrayList<CombatUnit> robotLegion;
        if(Global.chosenTeam == false) {
            robots = Scenario.bluePlayer;
            robotLegion = Scenario.bluePlayer.combatUnits; }
        else {
            robotLegion = Scenario.redPlayer.combatUnits;
            robots = Scenario.redPlayer; }
    
        //sends it to the initial method
        WhereToMove(robotLegion, robots);
    }
    
    //this method determines the possible movements
    private void WhereToMove(ArrayList<CombatUnit> robotLegion, Player robots) {
        
        //intitial loop to know all locations controlled
        ArrayList<Integer> controlledLocations = null;
        int count = 0;
        for(Node nodes : Scenario.listOfNodes) {
            //next loop for each combat unit
            for(CombatUnit killBots : robotLegion) {
                if(nodes.id == killBots.location.id)
                {
                    controlledLocations.add(nodes.id);
                    count++;
                }
            }
        }
        
        int[] weighting = null;
        int[] endLocation = null;
        //next loop goes through each individual combat unit
        for(CombatUnit killBots : robotLegion) {
            //creates an array with the size of the amount of nodes
            //array indexing follows node id's from 0 up, etc
            int indexer = 0;
            int location = killBots.GetLocation().id;
            int newLocation; int nextLocation;
            
            //creates an initial loop to know all controlled locations
            
            //an intitial loop for evey road within the scenario
            //to create a counter for roads connecting to current location
            for(Road road : Scenario.listOfRoads) {
                //creates and checks if the road location id's are the same
                //as the current combat unit's location id
                if(road.locationA.id == location || road.locationB.id == location) {
                    indexer++;
                }
                //sets array sizes to number of nodes connection to location
                weighting = new int[indexer];
                endLocation = new int[indexer];
            }
            
            //resets indexer, creates increment checker
            indexer = 0;
            int increment;
            
            for(Road road : Scenario.listOfRoads) {
                if(road.locationA.id == location || road.locationB.id == location) {
                    if(road.locationA.id != location)
                        newLocation = road.locationA.id;
                    else
                        newLocation = road.locationB.id;
                    
                    //assigns weighting for current node
                    increment = weighting[indexer];
                    
                    if(Scenario.listOfNodes[newLocation].isCapitalBlue || Scenario.listOfNodes[newLocation].isCapitalRed)
                        weighting[indexer] += 20;
                    if(robots.enemyIntelligence.get(indexer).isEmpty() == false)
                        weighting[indexer] += 45;
                    if(controlledLocations != null)
                        if(controlledLocations.contains(newLocation))
                            weighting[indexer] -= 20;
                    if(Scenario.listOfNodes[newLocation].suppliesAvailable < 3)
                        weighting[indexer] += 25;
                    else if(Scenario.listOfNodes[newLocation].suppliesAvailable <5)
                        weighting[indexer] += 15;
                    if(weighting[indexer] == increment)
                        weighting[indexer]+= 10;
                    
                    //checks adjacent nodes
                    for(Road road2 : Scenario.listOfRoads) {
                        if(road2 != road && (road2.locationA.id == newLocation
                                || road2.locationB.id == newLocation)){
                            if(road2.locationA.id != location)
                                newLocation = road2.locationA.id;
                            else
                                newLocation = road2.locationB.id;

                            //assigns weighting for current node
                            increment = weighting[indexer];

                            if(Scenario.listOfNodes[newLocation].isCapitalBlue || Scenario.listOfNodes[newLocation].isCapitalRed)
                                weighting[indexer] += 20;
                            if(robots.enemyIntelligence.get(indexer).isEmpty() == false)
                                weighting[indexer] += 45;
                            if(controlledLocations != null)
                                if(controlledLocations.contains(newLocation))
                                    weighting[indexer] -= 20;
                            if(Scenario.listOfNodes[newLocation].suppliesAvailable < 3)
                                weighting[indexer] += 25;
                            else if(Scenario.listOfNodes[newLocation].suppliesAvailable <5)
                                weighting[indexer] += 15;
                            if(weighting[indexer] == increment)
                                weighting[indexer]+= 10;
                        

                            //checks next level of adjacent nodes
                            for(Road road3 : Scenario.listOfRoads) {
                                if(road3 != road2 && (road3.locationA.id == newLocation
                                        || road3.locationB.id == newLocation)){
                                    if(road3.locationA.id != location)
                                        newLocation = road3.locationA.id;
                                    else
                                        newLocation = road3.locationB.id;

                                    //assigns weighting for current node
                                    increment = weighting[indexer];

                                    if(Scenario.listOfNodes[newLocation].isCapitalBlue || Scenario.listOfNodes[newLocation].isCapitalRed)
                                        weighting[indexer] += 20;
                                    if(robots.enemyIntelligence.get(indexer).isEmpty() == false)
                                        weighting[indexer] += 45;
                                    if(controlledLocations != null)
                                        if(controlledLocations.contains(newLocation))
                                            weighting[indexer] -= 20;
                                    if(Scenario.listOfNodes[newLocation].suppliesAvailable < 3)
                                        weighting[indexer] += 25;
                                    else if(Scenario.listOfNodes[newLocation].suppliesAvailable <5)
                                        weighting[indexer] += 15;
                                    if(weighting[indexer] == increment)
                                        weighting[indexer]+= 10;
                                }
                            }
                        
                        }
                    }
                    //moves on to next location
                    indexer++;
                }
                
                
            }
            
            //determines highest weighting value for movement
        
        
            indexer = 0;
            //stablishes the desired endlocation
            for(Road road : Scenario.listOfRoads) {
                //establishes the location id's of each possible movement
                if(road.locationA.id == location || road.locationB.id == location) {
                    if(road.locationA.id != location)
                        endLocation[indexer] = road.locationA.id;
                    else
                        endLocation[indexer] = road.locationB.id;
                }
            }
            
            //checks which weighting is the highest
            indexer = 0;
            int greatest = 0; int index = 0;
            for(int highest : weighting) {
                if(highest>greatest) {
                    greatest = highest;
                    index = indexer;
                }
                indexer++;
            }
            
            indexer = 0;
            //SEND MOVEMENT TO MAPEVENT HERE
            for(Road road : Scenario.listOfRoads) {
                //establishes the location id's of each possible movement
                if(road.locationA.id == location || road.locationB.id == location)
                    indexer++;
                
                if(indexer == index){
                    //SEND road AND killBot TO MAPEVENT
                }
                
            }
        }
    }
    
    //this method finalizes the movement to send to MapEvents
    private void FinalizeMovement(CombatUnit killBots, Road road) {
        
    }
}
