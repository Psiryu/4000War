
import java.util.ArrayList;
import java.util.Random;

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
    public static void AI() {
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
    
        //determines if any units controlled should merge or divide
        BeginMerging(robotLegion, robots);
        Dividing(robotLegion, robots);
        
        //re-establishes the variables in case mergers or divisions occured
        if(Global.chosenTeam == false) {
            robots = Scenario.bluePlayer;
            robotLegion = Scenario.bluePlayer.combatUnits; }
        else {
            robotLegion = Scenario.redPlayer.combatUnits;
            robots = Scenario.redPlayer; }
        
        //sends it to the initial movement method
        WhereToMove(robotLegion, robots);
    }
    
    //this method handles merging armies. It checks if an two units are able
    //to be merged, then adds weighting based on fog of war values of the nodes
    //surrounding the node the units are on
    private static void BeginMerging(ArrayList<CombatUnit> robotLegion, Player robots) {
        //MERGE RANDOMLY OR IF AN ENEMY IS AT AN ADJACENT NODE

        //creates a small array that keeps track of which armies have already merged
        ArrayList<Integer> mergers = new ArrayList<Integer>();
        
        //intitial loop to count all locations controlled
        int count = 0;
        for(Node nodes : Scenario.listOfNodes) {
            //next loop for each combat unit
            for(CombatUnit killBots : robotLegion) {
                if(nodes.id == killBots.location.id)
                {
                    count++;
                }
            }
            
            //checks if number of controlled nodes is less than size of army
            //(which implies at least two armies are on the same node)
            if(count < robotLegion.size()) {
                //establishes two arrays of all combat units to check locations
                for(CombatUnit killBots : robotLegion) {
                    if(mergers.contains(killBots.cUnitID) == false) {
                        for(CombatUnit killBots2 : robotLegion) {
                            //checks if they are on the same location and not the same unit
                            //and that their sizes are both small or both medium
                            if(killBots.cUnitID != killBots2.cUnitID &&
                                    killBots.location.id == killBots2.location.id &&
                                    ((killBots.size <=10 && killBots.size >5 && 
                                    killBots2.size <= 10 && killBots.size > 5) ||
                                    ((killBots.size <=5 && killBots2.size <=5) &&
                                    killBots.isFleet == false && killBots2.isFleet == false))) {
                                //establishes int for weight based on this possible merge
                                int weighting = 0;
                                //creates an instance of all roads to check locations for weighting
                                for(Road road : Scenario.listOfRoads) {
                                    //checks if road point A connects to location
                                    if(road.locationA.id == killBots.location.id) {
                                        for(Node node : Scenario.listOfNodes) {
                                            if(node.id == road.locationB.id) {
                                                //checks if fog of war at this node has any values
                                                if(robots.enemyIntelligence.get(node.id).size() > 1) {
                                                    weighting+=10;
                                                }
                                            }
                                        }

                                    } else if(road.locationB.id == killBots.location.id) {
                                        for(Node node : Scenario.listOfNodes) {
                                            if(node.id == road.locationB.id) {
                                                //checks if fog of war at this node has any values
                                                if(robots.enemyIntelligence.get(node.id).size() > 1) {
                                                    weighting+=10;
                                                }
                                            }
                                        }
                                    }
                                }
                                //sends the weighted value to Merging
                                Merging(weighting, killBots, killBots2);
                            }
                        }
                    }
                }
            }
        }
    }
    
    //manages the merge
    private static void Merging(int weighting, CombatUnit killBots, CombatUnit killBots2) {
        //creates a random number generator
        Random randomizer = new Random();
        //randomizes the number twice, to ensure randomness
        int random = randomizer.nextInt(5);
        random = randomizer.nextInt(5);
        //randomizes the weighting, from 0 to 10
        //0 nullifies the movement, 10 makes it much more appealing
        weighting = weighting*random;
        
        //merges if weighting*randomizer is greater than thirty
        if(weighting>30)
            MapEvent.mergeUnits(killBots.cUnitID, killBots2.cUnitID);
        
        //additional randomizer to merge even if no real need to
        if(weighting == 0) {
            random = randomizer.nextInt(5);
            if(random>3)
                MapEvent.mergeUnits(killBots.cUnitID, killBots2.cUnitID);
            
        }
        
    }
    
    //this method handles dividing armies
    private static void Dividing(ArrayList<CombatUnit> robotLegion, Player robots) {
        int weight; //counter that ad
        
        //loop goes through each individual combat unit
        for(CombatUnit killBots : robotLegion) {
            
        }
        
    }
    
    //this method determines the possible movements
    private static void WhereToMove(ArrayList<CombatUnit> robotLegion, Player robots) {
        
        //intitial loop to know all locations controlled
        ArrayList<Integer> controlledLocations = new ArrayList<Integer>();
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
                    if(road.capacity >= killBots.size || ((road.capacity == 0 && killBots.isFleet == true)
                            && Global.season != 0)){
                        if(road.locationA.id != location)
                            newLocation = road.locationA.id;
                        else
                            newLocation = road.locationB.id;

                        //assigns weighting for current node
                        increment = weighting[indexer];

                        if(Scenario.listOfNodes[newLocation].isCapitalBlue || Scenario.listOfNodes[newLocation].isCapitalRed)
                            weighting[indexer] += 30;
                        if(robots.enemyIntelligence.get(indexer).isEmpty() == false)
                            weighting[indexer] += 55;
                        if(controlledLocations.contains(newLocation))
                            weighting[indexer] -= 30;
                        if(Scenario.listOfNodes[newLocation].suppliesAvailable < 3)
                            weighting[indexer] += 35;
                        else if(Scenario.listOfNodes[newLocation].suppliesAvailable <5)
                            weighting[indexer] += 25;
                        if(weighting[indexer] == increment)
                            weighting[indexer]+= 20;

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
                                            weighting[indexer] += 10;
                                        if(robots.enemyIntelligence.get(indexer).isEmpty() == false)
                                            weighting[indexer] += 35;
                                        if(controlledLocations.contains(newLocation))
                                            weighting[indexer] -= 10;
                                        if(Scenario.listOfNodes[newLocation].suppliesAvailable < 3)
                                            weighting[indexer] += 15;
                                        else if(Scenario.listOfNodes[newLocation].suppliesAvailable <5)
                                            weighting[indexer] += 5;
                                        if(weighting[indexer] == increment)
                                            weighting[indexer]+= 5;
                                    }
                                }

                            }
                        }
                    } else
                        weighting[indexer] = 0; //assigns weight = 0 if path cannot be crossed
                    
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
            
            indexer = 0;
            //goes through each value of the weighting array and randomizes it
            for(int original : weighting) {
                //creates a random number generator
                Random randomizer = new Random();
                //randomizes the number twice, to ensure randomness
                int random = randomizer.nextInt(10);
                random = randomizer.nextInt(10);
                //randomizes the weighting, from 0 to 10
                //0 nullifies the movement, 10 makes it much more appealing
                weighting[indexer] = weighting[indexer]*random;
                
                //moves to next value
                indexer++;
            }
            
                        //checks if the ai can ferry a small army            
            ArrayList<Road> ferryRoad = new ArrayList<Road>();
            ArrayList<Integer> ferryDestination = new ArrayList<Integer>();
            ArrayList<CombatUnit> idToFerry = new ArrayList<CombatUnit>();
            int dest; //destination tracker
            if(killBots.isFleet == true)
            {
                for(Road roads2 : Scenario.listOfRoads) {
                    if(roads2.locationA.id == killBots.location.id ||
                            roads2.locationB.id == killBots.location.id &&
                            roads2.capacity == 0) 
                        for(CombatUnit killBots2 : robots.combatUnits) {
                            if(killBots2.size < 6 &&
                                    killBots2.location.id == killBots.location.id) {
                                if(roads2.locationA.id == killBots.location.id)
                                    dest = roads2.locationA.id;
                                else
                                    dest = roads2.locationB.id;
                                
                                ferryRoad.add(roads2);
                                ferryDestination.add(dest);
                                idToFerry.add(killBots2);
                            }
                        }
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
            if(ferryRoad.isEmpty() == true) {
                for(Road road : Scenario.listOfRoads) {
                    //establishes the location id's of each possible movement
                    if(road.locationA.id == location || road.locationB.id == location)
                        indexer++;
                    //once it reaches the proper index, send to finalize
                    if(indexer == index){
                        FinalizeMovement(killBots, road, location);
                    }

                }
            } else {
                Random randomizer = new Random();
                int random = randomizer.nextInt(10);
                int randomIndex = randomizer.nextInt(ferryRoad.size() - 1);
                
                if(random >8)
                    FinalizeFerryMovement(killBots, idToFerry.get(randomIndex),
                            ferryRoad.get(randomIndex), ferryDestination.get(randomIndex));
                else {
                    for(Road road : Scenario.listOfRoads) {
                    //establishes the location id's of each possible movement
                    if(road.locationA.id == location || road.locationB.id == location)
                        indexer++;
                    //once it reaches the proper index, send to finalize
                    if(indexer == index){
                        FinalizeMovement(killBots, road, location);
                    }

                }
                }
            }
            count++;
        }
    }
    
    //this method finalizes the movement to send to MapEvents
    private static void FinalizeMovement(CombatUnit killBots, Road road, int location) {
        if(road.locationA.id == location)
            MapEvent.addMovement(killBots.cUnitID, road, road.locationB.id);
        else
            MapEvent.addMovement(killBots.cUnitID, road, road.locationA.id);

    }
    //finalizes ferrying movement
    private static void FinalizeFerryMovement(CombatUnit killBots, 
            CombatUnit killBoats, Road road, int location) {
        if(road.locationA.id == location) {
            MapEvent.addMovement(killBoats.cUnitID, road, road.locationB.id);
            MapEvent.addMovement(killBots.cUnitID, road, road.locationB.id);
        }else {
            MapEvent.addMovement(killBoats.cUnitID, road, road.locationB.id);
            MapEvent.addMovement(killBots.cUnitID, road, road.locationB.id);
        }
    }
}
