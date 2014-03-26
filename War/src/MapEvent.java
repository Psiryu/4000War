
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * MapEvent Class
 *
 * @author Ben Yntema
 *
 * This class mediates between the user interface and the back end mechanics,
 * namely the movement and combat mechanics.
 *
 * Several lists are maintained by this class, each of which records a
 * particular aspect of the behavior of units as they are manipulated through
 * the front end. Subsequently the methods in this class serve to manipulate the
 * back end. These methods are largely private as they are intended for internal
 * use only. Methods such as addMovement, mergeUnit, and divideUnit are used by
 * the interface to manipulate units while completeCombat and successfulFlee are
 * used by the Battle class to facilitate movement and unit deletion after
 * combat calculations. Internal methods include processEvents, removeInstance,
 * simulateCombat, cleanList, and clearRegistry serve to facilitate the flow and
 * processing of information generated in the game.
 */
public class MapEvent {

    private static ArrayList<CombatUnit> combatUnitsRed = new ArrayList<CombatUnit>(); // list of all units held by the red faction
    private static ArrayList<CombatUnit> combatUnitsBlue = new ArrayList<CombatUnit>(); // list of all units held by the blue faction
    private static ArrayList<Road> redUnitRoad = new ArrayList<Road>(); // list of roads occupied by red faction
    private static ArrayList<Road> blueUnitRoad = new ArrayList<Road>(); // list of roads occupied by blue faction
    private static ArrayList<Node> redUnitEnd = new ArrayList<Node>(); // list of node end locations for red
    private static ArrayList<Node> blueUnitEnd = new ArrayList<Node>(); // list of node end locations for blue
    private static Player currentPlayer; // player objects for use in the events
    private static Battle battle = new Battle(); // initialize the battle object for method calls
    private static ArrayList<CombatInstance> combatQueue = new ArrayList<CombatInstance>(); // storage of all detected combats and the necessary information
    private static ArrayList<ArrayList<CombatUnit>> redCombatListCollisionL = new ArrayList<ArrayList<CombatUnit>>(); // list of red faction units in combat on road
    private static ArrayList<ArrayList<CombatUnit>> blueCombatListCollisionL = new ArrayList<ArrayList<CombatUnit>>(); // list of blue faction units in combat on road
    private static ArrayList<ArrayList<Node>> redCombatUnitPreviousLocationC = new ArrayList<ArrayList<Node>>(); // list of red faction previous locations pre-combat
    private static ArrayList<ArrayList<Node>> redCombatUnitEndLocationC = new ArrayList<ArrayList<Node>>(); // list of red faction tentative end locations pre-combat
    private static ArrayList<ArrayList<Node>> blueCombatUnitPreviousLocationC = new ArrayList<ArrayList<Node>>(); // list of blue faction previous locations pre-combat
    private static ArrayList<ArrayList<Node>> blueCombatUnitEndLocationC = new ArrayList<ArrayList<Node>>(); // list of blue faction tentative end locations pre-combat
    private static ArrayList<ArrayList<Road>> redCombatRoadC = new ArrayList<ArrayList<Road>>(); // the road upon which red units collided with blue units
    private static ArrayList<ArrayList<Road>> blueCombatRoadC = new ArrayList<ArrayList<Road>>(); // the road upon which blue units collided with red units
    private static ArrayList<ArrayList<CombatUnit>> redCombatListNodeL = new ArrayList<ArrayList<CombatUnit>>(); // list of red faction units in combat on node
    private static ArrayList<ArrayList<CombatUnit>> blueCombatListNodeL = new ArrayList<ArrayList<CombatUnit>>(); // list of blue faction units in combat on node
    private static ArrayList<ArrayList<Node>> redCombatUnitPreviousLocationN = new ArrayList<ArrayList<Node>>(); // list of red faction previous locations pre-combat
    private static ArrayList<ArrayList<Node>> redCombatUnitEndLocationN = new ArrayList<ArrayList<Node>>(); // list of red faction tentative end locations pre-combat
    private static ArrayList<ArrayList<Node>> blueCombatUnitPreviousLocationN = new ArrayList<ArrayList<Node>>(); // list of blue faction previous locations pre-combat
    private static ArrayList<ArrayList<Node>> blueCombatUnitEndLocationN = new ArrayList<ArrayList<Node>>(); // list of blue faction tentative end locations pre-combat
    private static ArrayList<ArrayList<Road>> redCombatRoadN = new ArrayList<ArrayList<Road>>(); // the road upon which red traveled to the conficted node
    private static ArrayList<ArrayList<Road>> blueCombatRoadN = new ArrayList<ArrayList<Road>>(); // the road upon which blue traveled to the conflicted node
    private static ArrayList<CombatUnit> removeQueue = new ArrayList<CombatUnit>();
    private static ArrayList<Integer> postCombatUnit = new ArrayList<Integer>();
    private static ArrayList<Road> postCombatRoad = new ArrayList<Road>();
    private static ArrayList<Integer> postCombatNode = new ArrayList<Integer>();
    //private static ArrayList<String> movements = new ArrayList<String>();

    // Constructor for MapEvent
    public MapEvent() {

    }

    /*
     Method: addMovement
     Parameters: int unitNum -> ID of the unit being moved
     Road road -> the road on which the unit is traveling
     int endLocationNum -> the ID of the node the unit is traveling to
     
     The unit object will be found by searching the player's roster.
     The endLocation will be determined by searching the map information in Scenario.
     Information regarding the unit's projected travels will be stored in the
     appropriate list for evaluation at turn's end.
     */
    public static void addMovement(int unitNum, Road road, int endLocationNum) {
        CombatUnit unit = null; // initialize the unit for consideraton
        Node endLocation = null; // initialize the end location node

        // search for the node within the Scenario list and store in endLocation
        for (Node location : Scenario.listOfNodes) {
            if (location.id == endLocationNum) {
                endLocation = location;
                break;
            }
        }

        // determine the player to be used
        if (Scenario.redPlayer.playerID == Global.curPlayer) {
            currentPlayer = Scenario.redPlayer;
            for (CombatUnit CUnits : Scenario.redPlayer.combatUnits) { // search the player's roster for the unit
                if (CUnits.cUnitID == unitNum) {
                    unit = CUnits;
                    //JOptionPane.showMessageDialog(null, "RED UNIT FOUND!");
                    break;
                }
            }
        } else { // do the same for bluePlayer, should that be the current player
            for (CombatUnit CUnits : Scenario.bluePlayer.combatUnits) {
                currentPlayer = Scenario.bluePlayer;
                if (CUnits.cUnitID == unitNum) {
                    unit = CUnits;
                    //JOptionPane.showMessageDialog(null, "BLUE UNIT FOUND!");
                    break;
                }
            }
        }
        
        if (unit == null){
            currentPlayer = Scenario.redPlayer;
            for (CombatUnit CUnits : Scenario.redPlayer.combatUnits) { // search the player's roster for the unit
                if (CUnits.cUnitID == unitNum) {
                    unit = CUnits;
                    //JOptionPane.showMessageDialog(null, "RED UNIT FOUND!");
                    break;
                }
            }
        }

        // DEBUG - checks if the unit was not found in the lists for each player
        // if this message occurs, then the ID was not found to belong to any unit
        // in either player's roster. Check the passed parameter
        if (unit == null) {
            JOptionPane.showMessageDialog(null, "UNIT NOT FOUND!");
        }

        // add the passed unit to the list for movement consideration
        if (currentPlayer.playerID == 0) { // if the current player is red
            if (!combatUnitsRed.isEmpty()) { // if the current list is not empty
                if (combatUnitsRed.contains(unit)) { // if the list already contains the units
                    // re-write the unit's registered information
                    int i = combatUnitsRed.indexOf(unit);
                    combatUnitsRed.set(i, unit);
                    redUnitRoad.set(i, road);
                    redUnitEnd.set(i, endLocation);
                } else { // if not already in list, then add to the end
                    combatUnitsRed.add(unit);
                    redUnitRoad.add(road);
                    redUnitEnd.add(endLocation);
                }
            } else { // if this is the first entry, add to the end of list
                combatUnitsRed.add(unit);
                redUnitRoad.add(road);
                redUnitEnd.add(endLocation);
            }
        } else { // repeat checks and writes for blue player
            if (!combatUnitsBlue.isEmpty()) {
                if (combatUnitsBlue.contains(unit)) {
                    int i = combatUnitsBlue.indexOf(unit);
                    combatUnitsBlue.set(i, unit);
                    blueUnitRoad.set(i, road);
                    blueUnitEnd.set(i, endLocation);
                } else {
                    combatUnitsBlue.add(unit);
                    blueUnitRoad.add(road);
                    blueUnitEnd.add(endLocation);
                }
            } else {
                combatUnitsBlue.add(unit);
                blueUnitRoad.add(road);
                blueUnitEnd.add(endLocation);
            }
            //movements.add("AI moved unit: " + unit.cUnitID + " Size: " + unit.size + ", From " + unit.location.name + " To " + endLocation.name);
        }
    }

    /*
     Method: omnipresentSimulation()
     Parameters: none
    
     Actions will be performed based on the information provided by addMovement.
     The lists will be consolidated, processed, and terminated.
     */
    public static void omnipresentSimulation() {
        //String list = "";
        //for (String movement : movements){
        //    list = list + movement + "\n";
        //}
        //JOptionPane.showMessageDialog(null, list);
        //movements.clear();
        do { // loop until all conflicts are resolved
            cleanList(); // consolidate the lists
            processEvents(); // process the movements and detect collisions
            simulateCombat(); // send collision information to combat
            removeInstances(); // remove all the units from the movement registry
        } while (postCombatUnit.size() > 0);

        clearRegistry(); // reset the class to be prepared for the next turn
    }

    /*
     Method: processEvents
     Paramters: none
    
     Based on the movements added by player and cleanList, collisions are simulated.
     Where a collision occured, a CombatInstance object is created and stored.
     Units that did not collide with the opposing faction are moved to their
     destination. 
     */
    private static void processEvents() {
        // storage of all the red and blue units participating in a battle
        ArrayList<CombatUnit> redList = new ArrayList<CombatUnit>();
        ArrayList<CombatUnit> blueList = new ArrayList<CombatUnit>();

        // storage of all the instances of combat detected
        combatQueue = new ArrayList<CombatInstance>();

        // initialize all of the storage lists for road collision 
        for (int i = 0; i < Scenario.listOfRoads.length; i++) {
            redCombatListCollisionL.add(new ArrayList<CombatUnit>()); // red units in collision on road
            blueCombatListCollisionL.add(new ArrayList<CombatUnit>()); // blue units in collision on road
            redCombatUnitPreviousLocationC.add(new ArrayList<Node>()); // red units' previous location
            redCombatUnitEndLocationC.add(new ArrayList<Node>()); // red units' intended location
            blueCombatUnitPreviousLocationC.add(new ArrayList<Node>()); // blue units' previous location
            blueCombatUnitEndLocationC.add(new ArrayList<Node>()); // blue units' intended location
            redCombatRoadC.add(new ArrayList<Road>()); // the road which red engaged in collision
            blueCombatRoadC.add(new ArrayList<Road>()); // the road which blue engaged in collision
        }

        // initialize al of the storage lists for node/location collisions
        for (int i = 0; i < Scenario.listOfNodes.length; i++) {
            // lists mirror the function of the lists assigned for road case
            redCombatListNodeL.add(new ArrayList<CombatUnit>());
            blueCombatListNodeL.add(new ArrayList<CombatUnit>());
            redCombatUnitPreviousLocationN.add(new ArrayList<Node>());
            redCombatUnitEndLocationN.add(new ArrayList<Node>());
            blueCombatUnitPreviousLocationN.add(new ArrayList<Node>());
            blueCombatUnitEndLocationN.add(new ArrayList<Node>());
            redCombatRoadN.add(new ArrayList<Road>());
            blueCombatRoadN.add(new ArrayList<Road>());
        }

        // for each of the possible roads, search for possible collisions
        for (int i = 0; i < Scenario.listOfRoads.length; i++) {
            for (int j = 0; j < combatUnitsRed.size(); j++) { // find the red units on this road
                if (redUnitRoad.get(j) != null) { // if the units was in motion
                    if (redUnitRoad.get(j).roadID == i) { // check if the unit was on the road of interest
                        // if it was on the road, add the information to the lists
                        redCombatListCollisionL.get(i).add(combatUnitsRed.get(j));
                        redCombatUnitPreviousLocationC.get(i).add(combatUnitsRed.get(j).location);
                        redCombatUnitEndLocationC.get(i).add(redUnitEnd.get(j));
                        redCombatRoadC.get(i).add(redUnitRoad.get(j));
                    }
                }
            }
            // repeat for the blue units
            for (int j = 0; j < combatUnitsBlue.size(); j++) {
                if (blueUnitRoad.get(j) != null) {
                    if (blueUnitRoad.get(j).roadID == i) {
                        blueCombatListCollisionL.get(i).add(combatUnitsBlue.get(j));
                        blueCombatUnitPreviousLocationC.get(i).add(combatUnitsBlue.get(j).location);
                        blueCombatUnitEndLocationC.get(i).add(blueUnitEnd.get(j));
                        blueCombatRoadC.get(i).add(blueUnitRoad.get(j));
                    }
                }
            }

            // if there are both red and blue units on this road, record a collision on road
            if (redCombatListCollisionL.get(i).size() > 0 && blueCombatListCollisionL.get(i).size() > 0) {
                // create a combat instance of this collision
                CombatInstance combat = new CombatInstance(redCombatListCollisionL.get(i), blueCombatListCollisionL.get(i),
                        redCombatRoadC.get(i), blueCombatRoadC.get(i), redCombatUnitEndLocationC.get(i),
                        blueCombatUnitEndLocationC.get(i), redCombatUnitPreviousLocationC.get(i),
                        blueCombatUnitPreviousLocationC.get(i), false);

                // add the units partaking in this conflict to the check lists
                redList.addAll(redCombatListCollisionL.get(i));
                blueList.addAll(blueCombatListCollisionL.get(i));

                // add the combat instance to the list of combats for later consideration
                combatQueue.add(combat);
            }
        }

        // repeat the same operations for each node
        for (int i = 0; i < Scenario.listOfNodes.length; i++) {
            for (int j = 0; j < redUnitEnd.size(); j++) {
                // find red units on this node
                if (redUnitEnd.get(j).id == Scenario.listOfNodes[i].id) {
                    redCombatListNodeL.get(i).add(combatUnitsRed.get(j));
                    redCombatUnitPreviousLocationN.get(i).add(combatUnitsRed.get(j).location);
                    redCombatUnitEndLocationN.get(i).add(redUnitEnd.get(j));
                    redCombatRoadN.get(i).add(null);
                }
            }
            for (int j = 0; j < blueUnitEnd.size(); j++) {
                // find blue units on this node
                if (blueUnitEnd.get(j).id == Scenario.listOfNodes[i].id) {
                    blueCombatListNodeL.get(i).add(combatUnitsBlue.get(j));
                    blueCombatUnitPreviousLocationN.get(i).add(combatUnitsBlue.get(j).location);
                    blueCombatUnitEndLocationN.get(i).add(blueUnitEnd.get(j));
                    blueCombatRoadN.get(i).add(null);
                }
            }
            if (redCombatListNodeL.get(i).size() > 0 && blueCombatListNodeL.get(i).size() > 0) {
                CombatInstance combat = new CombatInstance(redCombatListNodeL.get(i), blueCombatListNodeL.get(i),
                        redCombatRoadN.get(i), blueCombatRoadN.get(i), redCombatUnitEndLocationN.get(i),
                        blueCombatUnitEndLocationN.get(i), redCombatUnitPreviousLocationN.get(i),
                        blueCombatUnitPreviousLocationN.get(i), true);

                redList.addAll(redCombatListNodeL.get(i));
                blueList.addAll(blueCombatListNodeL.get(i));

                combatQueue.add(combat);
            }
        }

        // check for red units that are not participating in combat
        for (int j = 0; j < combatUnitsRed.size(); j++) { // for each of the possible red units
            // check if the unit is present in either node or road conflicts
            if (!redList.contains(combatUnitsRed.get(j))) {
                // move the unit to the intended location
                final int j2 = j;
                combatUnitsRed.get(j2).previousLocation = combatUnitsRed.get(j2).location;
                combatUnitsRed.get(j2).location = redUnitEnd.get(j2);
                removeQueue.add(combatUnitsRed.get(j2)); // remove the movement instance from the list
            }
        }

        // repeat the same operations for blue units not participating in combat
        for (int j = 0; j < combatUnitsBlue.size(); j++) {
            if (!blueList.contains(combatUnitsBlue.get(j))) {
                final int j2 = j;
                combatUnitsBlue.get(j2).previousLocation = combatUnitsBlue.get(j2).location;
                combatUnitsBlue.get(j2).location = blueUnitEnd.get(j);
                removeQueue.add(combatUnitsBlue.get(j2));
            }
        }
    }

    /*
     Method: removeInstance
     Parameters: CombatUnit unit -> the unit which is to be removed from the movement lists
    
     This method will check which faction the unit belongs to and remove the unit
     from the movement lists
     */
    private static void removeInstances() {
        for (CombatUnit unit : removeQueue) {
            // check which faction the unit belongs to 
            if (Scenario.redPlayer.playerID == unit.faction.playerID) {
                // remove the unit and its information from the movement lists
                if (combatUnitsRed.contains(unit)) {
                    redUnitRoad.remove(combatUnitsRed.indexOf(unit));
                    redUnitEnd.remove(combatUnitsRed.indexOf(unit));
                    combatUnitsRed.remove(unit);
                }
            } else {
                // repeat for blue faction units
                if (combatUnitsBlue.contains(unit)) {
                    blueUnitRoad.remove(combatUnitsBlue.indexOf(unit));
                    blueUnitEnd.remove(combatUnitsBlue.indexOf(unit));
                    combatUnitsBlue.remove(unit);
                }
            }
        }
        redCombatListCollisionL.clear();
        blueCombatListCollisionL.clear();
        redCombatUnitPreviousLocationC.clear();
        redCombatUnitEndLocationC.clear();
        blueCombatUnitPreviousLocationC.clear();
        blueCombatUnitEndLocationC.clear();
        redCombatRoadC.clear();
        blueCombatRoadC.clear();
        redCombatListNodeL.clear();
        blueCombatListNodeL.clear();
        redCombatUnitPreviousLocationN.clear();
        redCombatUnitEndLocationN.clear();
        blueCombatUnitPreviousLocationN.clear();
        blueCombatUnitEndLocationN.clear();
        redCombatRoadN.clear();
        blueCombatRoadN.clear();

        for (int i = 0; i < postCombatUnit.size(); i++) {
            addMovement(postCombatUnit.get(i), postCombatRoad.get(i), postCombatNode.get(i));
        }

        postCombatUnit.clear();
        postCombatRoad.clear();
        postCombatNode.clear();
    }

    /*
     Method: simulateCombat
     Parameters: none
    
     Combat instances generated by processEvents are evaluated and sent to the
     Battle class to determine the outcome
     */
    private static void simulateCombat() {
        //ArrayList<CombatInstance> temp = new ArrayList<CombatInstance>();
        for (CombatInstance combat : combatQueue) { // for each entry in the combat list
            if (combat.isNode()) { // if it is a collision on node/location
                // call the battle on node function with the appropriate parameters
                battle.PVPdoCampBattleOnNode(combat.redEndLocation().get(0),
                        combat.redUnits(), combat.redFromLocation(), combat.redEndLocation(),
                        combat.blueUnits(), combat.blueFromLocation(), combat.blueEndLocation());
            } else { // if it is a collision on road
                // call the battle on road function with the appropriate paramters
                battle.doBattleOnRoad(combat.redUnits(), combat.redFromLocation(), combat.redEndLocation(),
                        combat.blueUnits(), combat.blueFromLocation(), combat.blueEndLocation());
            }
        }
    }

    /*
     Method : cleanList
     Parameters: none
     
     This method checks the movement information lists for both players, then
     for each unit that has not been explicitly moved, add the information to 
     the lists. End locations will be considered the current location and the 
     road will be considered null.
     */
    private static void cleanList() {
        boolean found = false; // flag for whether the unit has been found

        if (!combatUnitsRed.isEmpty()) { // if the current list is not empty
            // determine which units have not been added to the lists
            for (int i = 0; i < Scenario.redPlayer.combatUnits.size(); i++) { // for each possible unit in the faction
                for (int j = 0; j < combatUnitsRed.size(); j++) { // for each unit accounted for in the faction
                    if (Scenario.redPlayer.combatUnits.get(i).cUnitID == combatUnitsRed.get(j).cUnitID) { // if the unit id is found in the lists
                        found = true; // indicate that the unit has been found
                    }
                }
                if (found == false) { // if the unit was not found, add it to the lists
                    combatUnitsRed.add(Scenario.redPlayer.combatUnits.get(i));
                    redUnitRoad.add(null);
                    redUnitEnd.add(Scenario.redPlayer.combatUnits.get(i).location);
                }
                found = false;
            }
        } else { // if starting from an empty list, add all units in red's roster
            for (int i = 0; i < Scenario.redPlayer.combatUnits.size(); i++) {
                combatUnitsRed.add(Scenario.redPlayer.combatUnits.get(i));
                redUnitRoad.add(null);
                redUnitEnd.add(Scenario.redPlayer.combatUnits.get(i).location);
            }
        }
        // repeat for the blue player's roster
        if (!combatUnitsBlue.isEmpty()) {
            for (int i = 0; i < Scenario.bluePlayer.combatUnits.size(); i++) {
                for (int j = 0; j < combatUnitsBlue.size(); j++) {
                    if (Scenario.bluePlayer.combatUnits.get(i).cUnitID == combatUnitsBlue.get(j).cUnitID) {
                        found = true;
                    }
                }
                if (found == false) {
                    combatUnitsBlue.add(Scenario.bluePlayer.combatUnits.get(i));
                    blueUnitRoad.add(null);
                    blueUnitEnd.add(Scenario.bluePlayer.combatUnits.get(i).location);
                }
                found = false;
            }
        } else {
            for (int i = 0; i < Scenario.bluePlayer.combatUnits.size(); i++) {
                combatUnitsBlue.add(Scenario.bluePlayer.combatUnits.get(i));
                blueUnitRoad.add(null);
                blueUnitEnd.add(Scenario.bluePlayer.combatUnits.get(i).location);
            }
        }
    }

    /*
     Method: completeCombat
     Parameters: ArrayList<CombatUnit> redUnits -> list of red units in combat
     Boolean redVictor -> if the red units were victorious, true if so
     ArrayList<CombatUnit> blueUnits -> list of blue units in combat
     Boolean blueVictor -> if the blue units were victorious, true if so
     Node endLocation -> the resulting location of the victorious faction
    
     The method is called from the Battle class when the results of a combat are
     tabulated. Defeated units will be removed from play while victorious units
     are moved to their inteded location.
     */
    public static void completeCombat(ArrayList<CombatUnit> redUnits, Boolean redVictor, ArrayList<CombatUnit> blueUnits, Boolean blueVictor, Node endLocation) {
        for (CombatUnit unit : redUnits) { // for each of the red units
            removeQueue.add(unit); // remove the movement records for each unit
            if (!redVictor) { // if red lost, remove the units from play
//                ArrayList<CombatInstance> temp = new ArrayList<CombatInstance>();
//                for (CombatInstance combat : combatQueue) {
//                    if (combat.redUnits().contains(unit)) {
//                        temp.add(combat);
//                    }
//                }
//                for (CombatInstance combat : temp) {
//                    combatQueue.remove(combat);
//                }
                Scenario.redPlayer.combatUnits.remove(unit);
            } else { // if red won, move the unit to the intended location
                int index = Scenario.redPlayer.combatUnits.indexOf(unit);
                Scenario.redPlayer.combatUnits.get(index).previousLocation = unit.location;
                Scenario.redPlayer.combatUnits.get(index).location = endLocation;
            }
        }
        for (CombatUnit unit : blueUnits) { // repeat the processes for blue units
            removeQueue.add(unit);
            if (!blueVictor) {
//                ArrayList<CombatInstance> temp = new ArrayList<CombatInstance>();
//                for (CombatInstance combat : combatQueue) {
//                    if (combat.blueUnits().contains(unit)) {
//                        temp.add(combat);
//                    }
//                }
//                for (CombatInstance combat : temp) {
//                    combatQueue.remove(combat);
//                }
                Scenario.bluePlayer.combatUnits.remove(unit);
            } else {
                int index = Scenario.bluePlayer.combatUnits.indexOf(unit);
                Scenario.bluePlayer.combatUnits.get(index).previousLocation = unit.location;
                Scenario.bluePlayer.combatUnits.get(index).location = endLocation;
            }
        }
    }

    /*
     Method: successfulFlee
     Parameters: ArrayList<CombatUnit> units -> the units that participated in the flee
     Road road -> the road upon which the fleeing unit is traveling
     int endLocationNum -> the ID of the node/location which is being traveled to
    
     This method is called from Battle class when a flee is detected and is successful.
     Units passed to this method will be moved to the location indicated by post-combat
     evaluation.
     */
    public static void successfullFlee(ArrayList<CombatUnit> units, Road road, int endLocationNum) {
        for (CombatUnit unit : units) { // for each of the units
            postCombatUnit.add(unit.cUnitID);
            postCombatRoad.add(road);
            postCombatNode.add(endLocationNum);
            //addMovement(unit.cUnitID, road, endLocationNum); // add the movement for the units
        }
    }

    /*
     Method: mergeUnits
     Parameters: int oneNum -> the ID of the first unit
     int twoNum -> the ID of the second unit
    
     Units passed to this method will be summed and merged into one unit. The result
     of the summation will reflect the properties of the two units considered for
     the merge.
     */
    public static void mergeUnits(int oneNum, int twoNum) {
        CombatUnit[] unit = new CombatUnit[2]; // storage of the units involved in the merge
        CombatUnit temp; // temporary unit to store new unit
        int[] scaled = {0, 0, 0}; // storage of scaled values, 0..15 to 0..2
        int sumSize; // total size of both units
        int[] sizes = new int[3]; // storage of all three unit sizes
        int health; // health value for new unit

        /// determine the player to be used
        if (Global.curPlayer == 0) {
            currentPlayer = Scenario.redPlayer;
        } else {
            currentPlayer = Scenario.bluePlayer;
        }

        // assign the players by obtaining them from the player's roster
        for (CombatUnit armies : currentPlayer.combatUnits) {
            if (armies.cUnitID == oneNum) {
                unit[0] = armies;
            }
            if (armies.cUnitID == twoNum) {
                unit[1] = armies;
            }
        }

        sumSize = unit[0].size + unit[1].size; // add together the total size
        sizes[0] = unit[0].size; // store size of unit 1
        sizes[1] = unit[1].size; // store size of unit 2
        sizes[2] = sumSize; // store the summed size
        health = (unit[0].illnessRating + unit[1].illnessRating) / 2; // calculate average health value

        // cap the summed size to 20, a large unit
        if (sumSize > 20) {
            sumSize = 20;
        }

        // for each of the sizes stored, calculate the scaled value
        for (int i = 0; i < 3; i++) {
            if (sizes[i] < 6) { // scale 0..5 to 0 (small)
                scaled[i] = 0;
            } else if (sizes[i] < 11) { // scale 6..10 to 1 (medium)
                scaled[i] = 1;
            } else { // scale 11..20 to 2 (large)
                scaled[i] = 2;
            }
        }

        // if the sizes of the individual units do not increase the scaled size
        // increase the value to the nearest scaled tier
        if (scaled[2] == scaled[0] || scaled[2] == scaled[1]) {
            if (sumSize < 6) {
                sumSize = 5;
            } else if (sumSize < 11) {
                sumSize = 10;
            } else {
                sumSize = 20;
            }
        }

        // if the current player is red
        if (Global.curPlayer == 0) {
            // create new unit based on calculated information
            temp = new CombatUnit(false, unit[0].cUnitID, sumSize, health, unit[0].location, Scenario.redPlayer);
            Scenario.redPlayer.combatUnits.add(temp); // add the new unit to the player's roster
            Scenario.redPlayer.combatUnits.remove(unit[0]); // remove the old unit from the player's roster
            Scenario.redPlayer.combatUnits.remove(unit[1]); // remove the old unit from the player's roster
        } else { // repeat for blue units
            temp = new CombatUnit(false, unit[0].cUnitID, sumSize, health, unit[0].location, Scenario.bluePlayer);
            Scenario.bluePlayer.combatUnits.add(temp);
            Scenario.bluePlayer.combatUnits.remove(unit[0]);
            Scenario.bluePlayer.combatUnits.remove(unit[1]);
        }
    }

    /*
     Method: divideUnit
     Parameters: int unitNum -> the ID of unit to be divided
    
     The unit passed will be evaluated for division. The resulting two units will
     carry the properties of the parent unit.
     */
    public static void divideUnit(int unitNum) {
        CombatUnit unit = null; // storage of the parent unit
        CombatUnit one, two; // storage of the new units
        boolean idFind = true; // flag to check if a new, unique id is found
        int checker = 0; // number indicating when a matching id was found
        int id = -1; // the ID being compared to roster for uniqueness
        int i2 = 0; // index of the ID of the parent unit being searched for
        int divSize; // calculated size for child units based on parent value

        // determine the player to be used
        if (Global.curPlayer == 0) {
            currentPlayer = Scenario.redPlayer; // set the current player
            for (CombatUnit army : Scenario.redPlayer.combatUnits) { // assign the unit when found
                if (army.cUnitID == unitNum) {
                    unit = Scenario.redPlayer.combatUnits.get(i2);
                }
                i2++;
            }
        } else { // repeat for the blueplayer
            currentPlayer = Scenario.bluePlayer;
            for (CombatUnit army : Scenario.bluePlayer.combatUnits) {
                if (army.cUnitID == unitNum) {
                    unit = Scenario.bluePlayer.combatUnits.get(i2);
                }
                i2++;
            }
        }

        divSize = (int) (unit.size / 2); // calculate the halved unit size

        // search for a unique ID amungst the red and blue player's rosters
        while (idFind) {
            id++;
            checker = 0; // set check for 0
            // evaluate each player's rosters for the comparision ID
            for (int j = 0; j < Scenario.redPlayer.combatUnits.size(); j++) {
                if (Scenario.redPlayer.combatUnits.get(j).cUnitID == id) {
                    checker++; // increase if ID hit found
                }
            }
            for (int j = 0; j < Scenario.bluePlayer.combatUnits.size(); j++) {
                if (Scenario.bluePlayer.combatUnits.get(j).cUnitID == id) {
                    checker++; // increase if ID hit found
                }
            }
            if (checker == 0) { // if an ID hit had not occured
                idFind = false; // set to false to exit the loop
            }
        }

        // for the current player, build the child units
        if (Global.curPlayer == 0) {
            // create new units based on calculated values
            one = new CombatUnit(false, unit.cUnitID, divSize, unit.illnessRating, unit.location, Scenario.redPlayer);
            two = new CombatUnit(false, id, divSize, unit.illnessRating, unit.location, Scenario.redPlayer);

            Scenario.redPlayer.combatUnits.remove(unit); // remove the parent unit
            Scenario.redPlayer.combatUnits.add(one); // add the first child unit
            Scenario.redPlayer.combatUnits.add(two); // add the second child unit
        } else { // repeat for blue player
            one = new CombatUnit(false, unit.cUnitID, divSize, unit.illnessRating, unit.location, Scenario.bluePlayer);
            two = new CombatUnit(false, id, divSize, unit.illnessRating, unit.location, Scenario.bluePlayer);

            Scenario.bluePlayer.combatUnits.remove(unit);
            Scenario.bluePlayer.combatUnits.add(one);
            Scenario.bluePlayer.combatUnits.add(two);
        }
    }

    /*
     Method: clearRegistry
     Parameters: none
    
     At the end of movement simulation, clear the lists used for movement detection
     for the next turn.
     */
    private static void clearRegistry() {
        combatUnitsRed = new ArrayList<CombatUnit>();
        combatUnitsBlue = new ArrayList<CombatUnit>();
        redUnitRoad = new ArrayList<Road>();
        blueUnitRoad = new ArrayList<Road>();
        redUnitEnd = new ArrayList<Node>();
        blueUnitEnd = new ArrayList<Node>();
        redCombatListCollisionL.clear();
        blueCombatListCollisionL.clear();
        redCombatUnitPreviousLocationC.clear();
        redCombatUnitEndLocationC.clear();
        blueCombatUnitPreviousLocationC.clear();
        blueCombatUnitEndLocationC.clear();
        redCombatRoadC.clear();
        blueCombatRoadC.clear();
        redCombatListNodeL.clear();
        blueCombatListNodeL.clear();
        redCombatUnitPreviousLocationN.clear();
        redCombatUnitEndLocationN.clear();
        blueCombatUnitPreviousLocationN.clear();
        blueCombatUnitEndLocationN.clear();
        redCombatRoadN.clear();
        blueCombatRoadN.clear();
        postCombatUnit.clear();
        postCombatRoad.clear();
        postCombatNode.clear();
    }
}
