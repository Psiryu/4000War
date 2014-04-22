
import java.util.ArrayList;
import java.util.Random;

/**
 * Artificial Intelligence
 *
 * Description: The AI is a non-learning artificial intelligence designed
 * to combat a human player in a controlled but randomized manner. The AI
 * is designed with perceptrons in mind for weighted action values, with an
 * element of randomness included in order to ensure no two matches against
 * and AI lead to the same movements and results. Division occurs within its own
 * method, while merging is divided into two methods, and movement into three.
 * The constructor of the AI initiates all actions.
 * It first populates controlled array lists, to be used in each method,
 * containing the army units the AI will represent (whether it be Red or Blue
 * team). It then calls to BeginMerge, which manages mergers. Then, it will
 * re-establish the array lists - in case any mergers occur. It will proceed
 * to execute the division method, and once again re-establish the array lists
 * to ensure the accuracy of the list. Finally, it calls to Moves, which
 * initiates the movement algorithm. This is where perceptrons kick in.
 * Movement first cycles through each combat unit within the array list, and
 * will determine which possible movements that unit can make. It then adds
 * a weight to that node with a set of condition checks (namely, if an enemy
 * unit is believed to be on a node, if a node is a capital city, or if a node
 * is fully clean of all suspicion). It will proceed to check each node around
 * the target node, increasing the weighted value as needed, and will do this
 * for two more layers of adjacent nodes. It will then determine if any ferrying
 * movements are possible. Once it has calculated all weights, it will determine
 * which movement has the highest weighting, and then will randomly determine
 * if it shall execute the highest weighted movement, the highest weighted
 * ferrying movement, or not move that army unit at all.
 * 
 * Usage: the AI is only called to from the Map.java files.
 *
 * Maintenance notes: follow instances of the variable "weight" within the methods, to
 * change the weighting associated with different conditions, or for where to add new
 * condition checks.
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
    
    /*
	 Method: BeginMerging
	 Input Parameters: ArrayList of all controlled combat units, the player
         object of the player the AI is controlling.
    
	 This method establishes which combat units controlled can be merged
         together and assigns a weighting to the potential merger. 
         It checks if an two units are able
         to be merged, then adds weighting based on fog of war values of the nodes
         surrounding the node the units are on
         It then sends a selected potential merger to the Merging method.
	 */
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
							for (ArrayList<Integer> nodal : robots.enemyIntelligence){
								if (nodal.get(0)==node.id && nodal.size()>1){
									weighting+=10;
								}
							}
//                                                        if(robots.enemyIntelligence.get(robots.enemyIntelligence.indexOf(node.id)).size() > 1) {
//                                                            weighting+=10;
//                                                        }
                                                    }
                                                }

                                            } else if(road.locationB.id == killBots.location.id) {
                                                for(Node node : Scenario.listOfNodes) {
                                                    if(node.id == road.locationA.id) {
                                                        //checks if fog of war at this node has any values
							for (ArrayList<Integer> nodal : robots.enemyIntelligence){
								if (nodal.get(0)==node.id && nodal.size()>1){
									weighting+=10;
								}
							}
//                                                        if(robots.enemyIntelligence.get(robots.enemyIntelligence.indexOf(node.id)).size() > 1) {
//                                                            weighting+=10;
//                                                        }
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
        
        //completes the listed merge actions if any merges were declared
        if(merge1.isEmpty() == false) {
            int i = 0;
            for(CombatUnit unit : merge1) {
                Merging(mergeWeight.get(i), merge1.get(i), merge2.get(i));
                i++;
            }
        }
    }
    
    /*
	 Method: Merging
	 Input Parameters: a weight value, two combat units to merge
    
	 This method will randomly decide if a proposed merger should occur or
         not. If it elects to proceed with the merger, the combat unit id's
         are set to MapEvent
	 */
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
    
    /*
	 Method: Dividing
	 Input Parameters: ArrayList of all controlled combat units.
    
	 This method determines which combat units controlled can be divided,
         assigns weightings on if it should divide or not, then adds a level
         of randomness and determines if said unit will divide. If it does,
         it sends the unit id to MapEvent.
	 */
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
    
    /*
	 Method: Moves
	 Input Parameters: ArrayList of all controlled combat units, and player object for
         which player the AI is to control.
    
	 This method establishes which movements each army unit can perform, assigns weighted
         values to each movement (or ferrying movement), and determines which movement
         has the highest weighted value. Sends the highest weight and movement to
         the method FinalizeMovement or FinalizeFerryMovement
	 */
    private static void Moves(ArrayList<CombatUnit> robotLegion, Player robots) {
        ArrayList<Road> roads = new ArrayList<Road>();
        ArrayList<Integer> weights = new ArrayList<Integer>();
        //checks if the ai can ferry a small army            
        ArrayList<Road> ferryRoad = new ArrayList<Road>();
        ArrayList<Integer> ferryDestination = new ArrayList<Integer>();
        ArrayList<CombatUnit> idToFerry = new ArrayList<CombatUnit>();
        int dest; //destination tracker
            
        for(CombatUnit killBots : robotLegion) {
            //ensures all arrays are clear
            roads.clear();
            weights.clear();
            ferryRoad.clear();
            ferryDestination.clear();
            idToFerry.clear();
            
            //for every road in the scenario
            for(Road road : Scenario.listOfRoads) {
                //if the army unit is located on this road of the loop
                if(road.locationA == killBots.location || road.locationB == killBots.location) {
                    //first checks if it's a naval path
                    if(road.isNaval == true && killBots.isFleet == true && Global.season!=0) {
                        int weight = 0; int newLocation;
                        //sets the destination locaton
                        if(road.locationA == killBots.location)
                            newLocation = road.locationB.id;
                        else
                            newLocation = road.locationA.id;
                        //assigns weighting for current node
                        int increment = weight;

                        //determines the wighting for if the merge should take place
                        if(Scenario.listOfNodes[newLocation].isCapitalBlue || Scenario.listOfNodes[newLocation].isCapitalRed)
                            weight += 30;
			for (ArrayList<Integer> nodal : robots.enemyIntelligence){
				if (nodal.get(0)==newLocation && nodal.size()>1){
					weight+=50;
				}
			}
                        //if no weight change has occured, increment for a sense
                        //of randomness
                        if(weight == increment)
                            weight+= 20;
                        
                        //a second loop of all roads, for each adjacent node to
                        //the destination of the previous loop
                        for(Road road2 : Scenario.listOfRoads) {
                            //if the road within the loop is the destination
                            if(road2.locationA.id == newLocation || road2.locationB.id == newLocation) {
                                //sets the next location for another loop
                                int nextLocation;
                                if(road2.locationA.id == newLocation)
                                    nextLocation = road.locationB.id;
                                else
                                    nextLocation = road.locationA.id;
                                //assigns weighting for current node
                                increment = weight;

                                //adds the cumulative weighting of this node
                                if(Scenario.listOfNodes[nextLocation].isCapitalBlue || Scenario.listOfNodes[nextLocation].isCapitalRed)
                                    weight += 20;
				for (ArrayList<Integer> nodal : robots.enemyIntelligence){
					if (nodal.get(0)==nextLocation && nodal.size()>1){
						weight+=30;
					}
				}

                                if(weight == increment)
                                    weight+= 10;
                                
                                //another loop for nodes adjacent to the adjacent node
                                //(essentially the AI is now looking three spots away
                                //from the starting location of the army.
                                for(Road road3 : Scenario.listOfRoads) {
                                    if(road3.locationA.id == nextLocation || road3.locationB.id == nextLocation) {
                                        int thirdLocation;
                                        if(road3.locationA.id == nextLocation)
                                            thirdLocation = road.locationB.id;
                                        else
                                            thirdLocation = road.locationA.id;
                                        //assigns weighting for current node
                                        increment = weight;

                                        if(Scenario.listOfNodes[thirdLocation].isCapitalBlue || Scenario.listOfNodes[thirdLocation].isCapitalRed)
                                            weight += 10;
					for (ArrayList<Integer> nodal : robots.enemyIntelligence){
						if (nodal.get(0)==thirdLocation && nodal.size()>1){
							weight+=20;
						}
					}

                                        if(weight == increment)
                                            weight+= 5;
                                        
                                        for(Road road4 : Scenario.listOfRoads) {
                                            if(road4.locationA.id == thirdLocation || road.locationB.id == thirdLocation) {
                                                int finalLocation;
                                                if(road4.locationA.id == thirdLocation)
                                                    finalLocation = road.locationB.id;
                                                else
                                                    finalLocation = road.locationA.id;
                                                //assigns weighting for current node
                                                increment = weight;
						for (ArrayList<Integer> nodal : robots.enemyIntelligence){
							if (nodal.get(0)==finalLocation && nodal.size()>1){
								weight+=10;
							}
						}

                                                if(weight == increment)
                                                    weight+= 5;
                                            }
                                        }
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
                        
                        //for each road
                        for(Road roads2 : Scenario.listOfRoads) {
                            //checks if the army is on this road, and is a naval path
                            if(roads2.locationA.id == killBots.location.id ||
                                    roads2.locationB.id == killBots.location.id &&
                                    roads2.isNaval == true) 
                                //for each army unit (again)
                                for(CombatUnit killBots2 : robotLegion) {
                                    //if army unit is a small army and at the present node
                                    //and is not another naval unit
                                    if(killBots2.size < 6 &&
                                            killBots2.location.id == killBots.location.id
                                            && killBots2.isFleet == false) {
                                        //sends the destination node
                                        if(roads2.locationA.id == killBots.location.id)
                                            dest = roads2.locationB.id;
                                        else
                                            dest = roads2.locationA.id;

                                        //adds the fleet, road, and small army to arrays
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
                            weight += 15;
			for (ArrayList<Integer> nodal : robots.enemyIntelligence){
				if (nodal.get(0)==newLocation && nodal.size()>1){
					weight+=50;
				}
			}
//                        if(robots.enemyIntelligence.get(newLocation).size() > 1)
//                            weight += 50;
                        
                        if(weight == increment)
                            weight+= 30;
                        
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
				
				for (ArrayList<Integer> nodal : robots.enemyIntelligence){
					if (nodal.get(0)==nextLocation && nodal.size()>1){
						weight+=10;
					}
							
				}
//                                if(robots.enemyIntelligence.get(nextLocation).size() > 1)
//                                    weight += 40;

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
                                            weight += 5;
					for (ArrayList<Integer> nodal : robots.enemyIntelligence){
						if (nodal.get(0)==finalLocation && nodal.size()>1){
							weight+=30;
						}
					}
//                                        if(robots.enemyIntelligence.get(finalLocation).size() > 1)
//                                            weight += 30;

                                        if(weight == increment)
                                            weight+= 10;
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
         
            //if there are no possible ferrying movements
            if(ferryRoad.isEmpty() == true) {
                //checks which weighting is the highest
                int indexer = 0;
                int greatest = 0; int index = 0;
                //ensures the weight array isn't empty
                if(weights.isEmpty() == false) {
                    //determines the highest weight
                    for(int highest : weights) {
                        if(highest>greatest) {
                            greatest = highest;
                            index = indexer;
                        }
                        indexer++;
                    }

                    //submits the movement to FinalizeMovement for processing
                    if(roads.get(index).locationA.id == killBots.location.id)
                        FinalizeMovement(killBots, roads.get(index), roads.get(index).locationA.id);
                    else
                        FinalizeMovement(killBots, roads.get(index), roads.get(index).locationB.id);
                }
            //else ifthere are possible ferrying movements
            } else {
                //creates a random number generator
                Random randomizer = new Random();
                //randomizes the number twice, to ensure randomness
                int random = randomizer.nextInt(10);
                //if random is greater than 3, use the highest weighted movement
                if(random>3) {
                    if(weights.isEmpty() == false) {
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
                    }
                //else, if less than 3, use a ferrying movement
                } else {
                    //double checks there is a ferrying movement
                    if(ferryRoad.isEmpty() == false) {
                        int upper = ferryRoad.size();
                        //upper ensures the randomizer will not go out of bounds
                        if(upper >1)
                            random = randomizer.nextInt((upper-1));
                        else
                            random = 0;
                        
                        //submits to FinalizeFerryMovement for processing
                        if(roads.get(random).locationA.id == killBots.location.id)
                            FinalizeFerryMovement(killBots, idToFerry.get(random),ferryRoad.get(random), ferryRoad.get(random).locationA.id);
                        else
                            FinalizeFerryMovement(killBots, idToFerry.get(random),ferryRoad.get(random), ferryRoad.get(random).locationB.id);
                    }
                }
                
            }
        }
    }
    
    /*
	 Method: FinalizeMovement
	 Input Parameters: a CombatUnit to move, the road it will take, and the end location desired
    
	 This method will randomely determine if the desired movement will actually
         happen or not, and, if it does, sends the movement to MapEvent
	 */
    private static void FinalizeMovement(CombatUnit killBots, Road road, int location) {
        //creates a random number generator
        Random randomizer = new Random();
        //randomness generated
        int random = randomizer.nextInt(10);
                
        //if random number is greater than 2, the movement occurs
        if(random >2) {
            //one final check to ensure the ai cannot make illegal moves, it checks
            //if the road capacity is greater than or equal to the unit size
            if(road.capacity >= killBots.size) {
                //if road location a is the unit location, move to b. Else move to a.
                if(road.locationA.id == location)
                    MapEvent.addMovement(killBots.cUnitID, road, road.locationB.id);
                else
                    MapEvent.addMovement(killBots.cUnitID, road, road.locationA.id);
            }
        }
        //else, no movement will happen (for randomness' sake)
    }
    
     /*
	 Method: FinalizeFerryMovement
	 Input Parameters: a CombatUnit to move, naval combat unit, the road it will take, 
            and the end location desired
    
	 This method will randomely determine if the desired ferrying movement will actually
         happen or not, and, if it does, sends the movements to MapEvent
	 */
    private static void FinalizeFerryMovement(CombatUnit killBots, 
            CombatUnit killBoats, Road road, int location) {
        if(road.locationA.id == location) {
            MapEvent.addMovement(killBoats.cUnitID, road, road.locationB.id);
            MapEvent.addMovement(killBots.cUnitID, road, road.locationB.id);
        }else {
            MapEvent.addMovement(killBoats.cUnitID, road, road.locationA.id);
            MapEvent.addMovement(killBots.cUnitID, road, road.locationA.id);
        }
    }
}
