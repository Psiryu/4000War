
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
        
        //re-establishes the variables in case mergers or divisions occured
        if(Global.chosenTeam == false) {
            robots = Scenario.bluePlayer;
            robotLegion = Scenario.bluePlayer.combatUnits; }
        else {
            robotLegion = Scenario.redPlayer.combatUnits;
            robots = Scenario.redPlayer; }
        
        Dividing(robotLegion);
        
        //re-establishes the variables in case mergers or divisions occured
        if(Global.chosenTeam == false) {
            robots = Scenario.bluePlayer;
            robotLegion = Scenario.bluePlayer.combatUnits; }
        else {
            robotLegion = Scenario.redPlayer.combatUnits;
            robots = Scenario.redPlayer; }
        
        //sends it to the initial movement method
        //WhereToMove(robotLegion, robots);
        Moves(robotLegion, robots);
    }
    
    //this method handles merging armies. It checks if an two units are able
    //to be merged, then adds weighting based on fog of war values of the nodes
    //surrounding the node the units are on
    private static void BeginMerging(ArrayList<CombatUnit> robotLegion, Player robots) {
        //MERGE RANDOMLY OR IF AN ENEMY IS AT AN ADJACENT NODE

        //creates a small array that keeps track of which armies have already merged
        ArrayList<Integer> mergers = new ArrayList<Integer>();
        //arrays to finalizes merges once the loops are done
        ArrayList<CombatUnit> merge1 = new ArrayList<CombatUnit>();
        ArrayList<CombatUnit> merge2 = new ArrayList<CombatUnit>();
        ArrayList<Integer> mergeWeight = new ArrayList<Integer>();
        
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
                    if(killBots != null) {
                        if(mergers.isEmpty() == true || mergers.contains(killBots.cUnitID) == false) {
                            for(CombatUnit killBots2 : robotLegion) {
                                if(killBots2 != null) {
                                    int weighting = 0;
                                    //checks if they are on the same location and not the same unit
                                    //and that their sizes are both small or both medium
                                    if(killBots.cUnitID != killBots2.cUnitID &&
                                            killBots.location.id == killBots2.location.id &&
                                            ((killBots.size <=10 && killBots.size >5 && 
                                            killBots2.size <= 10 && killBots.size > 5) ||
                                            ((killBots.size <=5 && killBots2.size <=5) &&
                                            killBots.isFleet == false && killBots2.isFleet == false))) {
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
                                        mergers.add(killBots.cUnitID);
                                        mergers.add(killBots2.cUnitID);
                                        merge1.add(killBots);
                                        merge2.add(killBots2);
                                        mergeWeight.add(weighting);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        
        if(merge1.isEmpty() == false) {
            int i = 0;
            for(CombatUnit unit : merge1) {
                Merging(mergeWeight.get(i), merge1.get(i), merge2.get(i));
                i++;
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
    private static void Dividing(ArrayList<CombatUnit> robotLegion) {
        int weight; //counter that adds weighting
        
        //list of which to divide
        ArrayList<CombatUnit> dividers = new ArrayList<CombatUnit>();
        
        //loop goes through each individual combat unit
        for(CombatUnit killBots : robotLegion) {
            //ensures no small or flets try to divide
            if(killBots.size > 5) {
                //weight starts off as ten, is subtracted for each road it cannot
                //travel due to size. Random number from 0 to weight is then used
                //for the probability of dividing.
                weight = 20;

                //loop goes through each road
                for(Road road : Scenario.listOfRoads) {
                    //checks if the unit is connected to this road
                    if(road.locationA.id == killBots.location.id || road.locationB.id == killBots.location.id) {
                        //checks if unit cannot travel it due to size
                        if(road.capacity < killBots.size && road.capacity > 0)
                            weight-=1;
                        if(road.capacity < killBots.size) {
                            weight-=3;                        
                        }
                    }
                }
                //sets weight to 1 if it is decremented to or below 0
                if(weight <=0)
                    weight = 3;

                //adds randomness and decides if it should divide or not
                //creates a random number generator
                Random randomizer = new Random();
                int divide = randomizer.nextInt(weight);

                //determines if it will divide or not
                if(divide < 3)
                    dividers.add(killBots);
            }
        }
        
        if(dividers.isEmpty() == false) {
            for(CombatUnit units : dividers) 
                MapEvent.divideUnit(units.cUnitID);
        }
    }
    
    //this method determines the possible movements    
    private static void Moves(ArrayList<CombatUnit> robotLegion, Player robots) {
        ArrayList<Road> roads = new ArrayList<Road>();
        ArrayList<Integer> weights = new ArrayList<Integer>();
        //checks if the ai can ferry a small army            
        ArrayList<Road> ferryRoad = new ArrayList<Road>();
        ArrayList<Integer> ferryDestination = new ArrayList<Integer>();
        ArrayList<CombatUnit> idToFerry = new ArrayList<CombatUnit>();
        int dest; //destination tracker
            
        for(CombatUnit killBots : robotLegion) {
            roads.clear();
            weights.clear();
            ferryRoad.clear();
            ferryDestination.clear();
            idToFerry.clear();
            
            for(Road road : Scenario.listOfRoads) {
                if(road.locationA == killBots.location || road.locationB == killBots.location) {
                    //first checks if it's a naval path
                    if(road.isNaval == true && killBots.isFleet == true && Global.season!=0) {
                        int weight = 0; int newLocation;
                        if(road.locationA == killBots.location)
                            newLocation = road.locationB.id;
                        else
                            newLocation = road.locationA.id;
                        //assigns weighting for current node
                        int increment = weight;

                        if(Scenario.listOfNodes[newLocation].isCapitalBlue || Scenario.listOfNodes[newLocation].isCapitalRed)
                            weight += 10;
                        if(robots.enemyIntelligence.get(newLocation).size() > 1)
                            weight += 40;
                        
                        if(weight == increment)
                            weight+= 20;
                        
                        for(Road road2 : Scenario.listOfRoads) {
                            if(road.locationA.id == newLocation || road.locationB.id == newLocation) {
                                
                                int nextLocation;
                                if(road.locationA.id == newLocation)
                                    nextLocation = road.locationB.id;
                                else
                                    nextLocation = road.locationA.id;
                                //assigns weighting for current node
                                increment = weight;

                                if(Scenario.listOfNodes[nextLocation].isCapitalBlue || Scenario.listOfNodes[nextLocation].isCapitalRed)
                                    weight += 10;
                                if(robots.enemyIntelligence.get(nextLocation).size() > 1)
                                    weight += 40;

                                if(weight == increment)
                                    weight+= 20;
                                
                                for(Road road3 : Scenario.listOfRoads) {
                                    if(road.locationA == killBots.location || road.locationB == killBots.location) {
                                        int finalLocation;
                                        if(road.locationA.id == nextLocation)
                                            finalLocation = road.locationB.id;
                                        else
                                            finalLocation = road.locationA.id;
                                        //assigns weighting for current node
                                        increment = weight;

                                        if(Scenario.listOfNodes[finalLocation].isCapitalBlue || Scenario.listOfNodes[finalLocation].isCapitalRed)
                                            weight += 10;
                                        if(robots.enemyIntelligence.get(finalLocation).size() > 1)
                                            weight += 40;

                                        if(weight == increment)
                                            weight+= 20;
                                    }
                                }
                            }
                        
                        }
                        
                        //creates a random number generator
                        Random randomizer = new Random();
                        //randomizes the number twice, to ensure randomness
                        int random = randomizer.nextInt(10);
                        random = randomizer.nextInt(10);
                        //randomizes the weighting, from 0 to 10
                        //0 nullifies the movement, 10 makes it much more appealing
                        weight = weight*random;
                        
                        roads.add(road);
                        weights.add(weight);
                        
                        //checks if the ai can ferry a small army            
                        
                        for(Road roads2 : Scenario.listOfRoads) {
                            if(roads2.locationA.id == killBots.location.id ||
                                    roads2.locationB.id == killBots.location.id &&
                                    roads2.isNaval == true) 
                                for(CombatUnit killBots2 : robotLegion) {
                                    if(killBots2.size < 6 &&
                                            killBots2.location.id == killBots.location.id) {
                                        if(roads2.locationA.id == killBots.location.id)
                                            dest = roads2.locationB.id;
                                        else
                                            dest = roads2.locationA.id;

                                        ferryRoad.add(roads2);
                                        ferryDestination.add(dest);
                                        idToFerry.add(killBots2);
                                    }
                                }
                        }
                        
                    
                    //next checks for other movements
                    } else if(road.capacity >= killBots.size && road.isNaval == false) {
                        int weight = 0; int newLocation;
                        if(road.locationA == killBots.location)
                            newLocation = road.locationB.id;
                        else
                            newLocation = road.locationA.id;
                        //assigns weighting for current node
                        int increment = weight;

                        if(Scenario.listOfNodes[newLocation].isCapitalBlue || Scenario.listOfNodes[newLocation].isCapitalRed)
                            weight += 10;
                        if(robots.enemyIntelligence.get(newLocation).size() > 1)
                            weight += 40;
                        
                        if(weight == increment)
                            weight+= 20;
                        
                        for(Road road2 : Scenario.listOfRoads) {
                            if(road.locationA.id == newLocation || road.locationB.id == newLocation) {
                                
                                int nextLocation;
                                if(road.locationA.id == newLocation)
                                    nextLocation = road.locationB.id;
                                else
                                    nextLocation = road.locationA.id;
                                //assigns weighting for current node
                                increment = weight;

                                if(Scenario.listOfNodes[nextLocation].isCapitalBlue || Scenario.listOfNodes[nextLocation].isCapitalRed)
                                    weight += 10;
                                if(robots.enemyIntelligence.get(nextLocation).size() > 1)
                                    weight += 40;

                                if(weight == increment)
                                    weight+= 20;
                                
                                for(Road road3 : Scenario.listOfRoads) {
                                    if(road.locationA == killBots.location || road.locationB == killBots.location) {
                                        int finalLocation;
                                        if(road.locationA.id == nextLocation)
                                            finalLocation = road.locationB.id;
                                        else
                                            finalLocation = road.locationA.id;
                                        //assigns weighting for current node
                                        increment = weight;

                                        if(Scenario.listOfNodes[finalLocation].isCapitalBlue || Scenario.listOfNodes[finalLocation].isCapitalRed)
                                            weight += 10;
                                        if(robots.enemyIntelligence.get(finalLocation).size() > 1)
                                            weight += 40;

                                        if(weight == increment)
                                            weight+= 20;
                                    }
                                }
                            }
                        
                        }
                        
                        //creates a random number generator
                        Random randomizer = new Random();
                        //randomizes the number twice, to ensure randomness
                        int random = randomizer.nextInt(10);
                        random = randomizer.nextInt(10);
                        //randomizes the weighting, from 0 to 10
                        //0 nullifies the movement, 10 makes it much more appealing
                        weight = weight*random;
                        
                        roads.add(road);
                        weights.add(weight);
                    }
                        
                        
                }
            }
         
            if(ferryRoad.isEmpty() == true) {
                //checks which weighting is the highest
                int indexer = 0;
                int greatest = 0; int index = 0;
                for(int highest : weights) {
                    if(highest>greatest) {
                        greatest = highest;
                        index = indexer;
                    }
                    indexer++;
                }

                if(roads.get(index).locationA.id == killBots.location.id)
                    FinalizeMovement(killBots, roads.get(index), roads.get(index).locationA.id);
                else
                    FinalizeMovement(killBots, roads.get(index), roads.get(index).locationB.id);
            } else {
                //creates a random number generator
                Random randomizer = new Random();
                //randomizes the number twice, to ensure randomness
                int random = randomizer.nextInt(10);
                if(random>3) {
                    //checks which weighting is the highest
                    int indexer = 0;
                    int greatest = 0; int index = 0;
                    for(int highest : weights) {
                        if(highest>greatest) {
                            greatest = highest;
                            index = indexer;
                        }
                        indexer++;
                    }

                    if(roads.get(index).locationA.id == killBots.location.id)
                        FinalizeMovement(killBots, roads.get(index), roads.get(index).locationA.id);
                    else
                        FinalizeMovement(killBots, roads.get(index), roads.get(index).locationB.id);
                } else {
                    int upper = ferryRoad.size();
                    random = randomizer.nextInt((upper-1));
                    if(roads.get(random).locationA.id == killBots.location.id)
                        FinalizeFerryMovement(killBots, idToFerry.get(random),ferryRoad.get(random), ferryRoad.get(random).locationA.id);
                    else
                        FinalizeFerryMovement(killBots, idToFerry.get(random),ferryRoad.get(random), ferryRoad.get(random).locationB.id);
                    
                }
                
            }
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
