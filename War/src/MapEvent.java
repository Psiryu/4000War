/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;

/**
 *
 * @author Ben
 */
public class MapEvent {

    private static ArrayList<CombatUnit> combatUnitsRed = new ArrayList<CombatUnit>(); // list of all units held by the red faction in motion
    private static ArrayList<CombatUnit> combatUnitsBlue = new ArrayList<CombatUnit>(); // list of all units held by the blue faction in motion
    private static ArrayList<Road> redUnitRoad = new ArrayList<Road>(); // list of roads occupied by red faction
    private static ArrayList<Road> blueUnitRoad = new ArrayList<Road>(); // list of roads occupied by blue faction
    private static ArrayList<Node> redUnitEnd = new ArrayList<Node>(); // list of node end locations for red
    private static ArrayList<Node> blueUnitEnd = new ArrayList<Node>(); // list of node end locations for blue
    private static ArrayList<CombatUnit> redCombatListCollision = new ArrayList<CombatUnit>(); // list of red faction units in combat on road
    private static ArrayList<CombatUnit> blueCombatListCollision = new ArrayList<CombatUnit>(); // list of blue faction units in combat on road
    private static ArrayList<CombatUnit> redCombatListNode = new ArrayList<CombatUnit>(); // list of red faction units in combat on node
    private static ArrayList<CombatUnit> blueCombatListNode = new ArrayList<CombatUnit>(); // list of blue faction units in combat on node
    private static Player redPlayer, bluePlayer, currentPlayer; // player objects for use in the events
    private static ArrayList<Node> redCombatUnitPreviousLocation = new ArrayList<Node>(); // list of red faction previous locations pre-combat
    private static ArrayList<Node> redCombatUnitEndLocation = new ArrayList<Node>(); // list of red faction tentative end locations pre-combat
    private static ArrayList<Node> blueCombatUnitPreviousLocation = new ArrayList<Node>(); // list of blue faction previous locations pre-combat
    private static ArrayList<Node> blueCombatUnitEndLocation = new ArrayList<Node>(); // list of blue faction tentative end locations pre-combat
    private static Battle battle; // initialize the battle object for method calls
    private static ArrayList<Road> redCombatRoad = new ArrayList<Road>();
    private static ArrayList<Road> blueCombatRoad = new ArrayList<Road>();
    private static ArrayList<CombatInstance> combatQueue = new ArrayList<CombatInstance>();
    private static ArrayList<CombatUnit> resolvedQueueC = new ArrayList<CombatUnit>();
    private static ArrayList<ArrayList<Node>> resolvedQueueN = new ArrayList<ArrayList<Node>>();
    private static ArrayList<CombatUnit> defeatQueue = new ArrayList<CombatUnit>();
    private static HashMap ferryList = new HashMap();

    // Constructor for MapEvent
    public MapEvent() {
        // initialize the parameters
        /*
         combatUnitsRed = new ArrayList<CombatUnit>();
         combatUnitsBlue = new ArrayList<CombatUnit>();
         redUnitRoad = new ArrayList<Road>();
         blueUnitRoad = new ArrayList<Road>();
         redUnitEnd = new ArrayList<Node>();
         blueUnitEnd = new ArrayList<Node>();
         redCombatListCollision = new ArrayList<CombatUnit>();
         blueCombatListCollision = new ArrayList<CombatUnit>();
         redCombatListNode = new ArrayList<CombatUnit>();
         blueCombatListNode = new ArrayList<CombatUnit>();
         redPlayer = Scenario.redPlayer;
         bluePlayer = Scenario.bluePlayer;
         redCombatUnitPreviousLocation = new ArrayList<Node>();
         redCombatUnitEndLocation = new ArrayList<Node>();
         blueCombatUnitPreviousLocation = new ArrayList<Node>();
         blueCombatUnitEndLocation = new ArrayList<Node>();
         battle = new Battle();
         */
    }

    // Method called to to add a movement to the registry
    public static void addMovement(int unitNum, Road road, int endLocationNum) {
        CombatUnit unit = null; // initialize the unit for consideraton
        Node endLocation = Scenario.listOfNodes[endLocationNum]; // load the end locations based on provided index

        // determine the player to be used
        if (Global.curPlayer == 0) {
            currentPlayer = Scenario.redPlayer;
            for (CombatUnit CUnits : Scenario.redPlayer.combatUnits) {
                if (CUnits.cUnitID == unitNum) {
                    unit = CUnits;
                    break;
                }
            }
        } else {
            currentPlayer = Scenario.bluePlayer;
            for (CombatUnit CUnits : Scenario.bluePlayer.combatUnits) {
                if (CUnits.cUnitID == unitNum) {
                    unit = CUnits;
                    break;
                }
            }
        }

        // check if the unit has already been added to the list
        // if the unit has already been added, remove the current values
        if (currentPlayer.playerID == 0) {
            if (!combatUnitsRed.isEmpty()) {
                if (combatUnitsRed.contains(unit)) {
                    redUnitRoad.remove(combatUnitsRed.indexOf(unit));
                    redUnitEnd.remove(combatUnitsRed.indexOf(unit));
                    combatUnitsRed.remove(unit);
                    combatUnitsRed.add(unit);
                    redUnitRoad.add(road);
                    redUnitEnd.add(endLocation);
                } else {
                    combatUnitsRed.add(unit);
                    redUnitRoad.add(road);
                    redUnitEnd.add(endLocation);
                }
            } else {
                combatUnitsRed.add(unit);
                redUnitRoad.add(road);
                redUnitEnd.add(endLocation);
            }
        } else {
            if (!combatUnitsBlue.isEmpty()) {
                if (combatUnitsBlue.contains(unit)) {
                    blueUnitRoad.remove(combatUnitsBlue.indexOf(unit));
                    blueUnitEnd.remove(combatUnitsBlue.indexOf(unit));
                    combatUnitsBlue.remove(unit);
                    combatUnitsBlue.add(unit);
                    blueUnitRoad.add(road);
                    blueUnitEnd.add(endLocation);
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
        }

    }

    public static void deafeatedUnitsQueue(CombatUnit unit, Boolean addRemove) {
        if (addRemove) {
            if (!defeatQueue.contains(unit)) {
                defeatQueue.add(unit);
            }
        } else {
            if (defeatQueue.contains(unit)) {
                defeatQueue.remove(unit);
            }
        }
    }

    public static void addMovementFerry(int fleetNum, int unitNum, Road road, int endLocationNum) {
        CombatUnit unit = null;

        // determine the player to be used
        if (Global.curPlayer == 0) {
            for (CombatUnit CUnits : Scenario.redPlayer.combatUnits) {
                if (CUnits.cUnitID == unitNum) {
                    unit = CUnits;
                    ferryList.put(fleetNum, unit);
                    redPlayer.combatUnits.remove(unit);
                    addMovement(fleetNum, road, endLocationNum);
                    break;
                }
            }
        } else {
            for (CombatUnit CUnits : Scenario.bluePlayer.combatUnits) {
                if (CUnits.cUnitID == unitNum) {
                    unit = CUnits;
                    ferryList.put(fleetNum, unit);
                    bluePlayer.combatUnits.remove(unit);
                    addMovement(fleetNum, road, endLocationNum);
                    break;
                }
            }
        }
    }

    public static void removeMovementFerry(int fleetNum, Road road, int endLocationNum) {
        CombatUnit unit = null;
        unit = (CombatUnit) ferryList.get(fleetNum);
        ferryList.remove(fleetNum);

        // determine the player to be used
        if (Global.curPlayer == 0) {
            redPlayer.combatUnits.add(unit);
        } else {
            bluePlayer.combatUnits.add(unit);
        }

        addMovement(fleetNum, road, endLocationNum);
    }

    private static void fulfillMovement() {

    }

    private static void fulfillFerry(int fleetNum, Node endLocation) {
        CombatUnit fleet = null;
        CombatUnit unit = (CombatUnit) ferryList.get(fleetNum);
        ferryList.remove(fleetNum);

        if (unit.faction.playerID == 0) {
            redPlayer.combatUnits.add(unit);
            for (CombatUnit CUnits : Scenario.redPlayer.combatUnits) {
                if (CUnits.cUnitID == fleetNum) {
                    fleet = CUnits;
                    redPlayer.combatUnits.get(redPlayer.combatUnits.indexOf(fleet)).previousLocation = redPlayer.combatUnits.get(redPlayer.combatUnits.indexOf(fleet)).location;
                    redPlayer.combatUnits.get(redPlayer.combatUnits.indexOf(fleet)).location = endLocation;
                    redPlayer.combatUnits.get(redPlayer.combatUnits.indexOf(unit)).previousLocation = redPlayer.combatUnits.get(redPlayer.combatUnits.indexOf(unit)).location;
                    redPlayer.combatUnits.get(redPlayer.combatUnits.indexOf(unit)).location = endLocation;
                    break;
                }
            }
        } else {
            bluePlayer.combatUnits.add(unit);
            for (CombatUnit CUnits : Scenario.bluePlayer.combatUnits) {
                if (CUnits.cUnitID == fleetNum) {
                    fleet = CUnits;
                    bluePlayer.combatUnits.get(bluePlayer.combatUnits.indexOf(fleet)).previousLocation = bluePlayer.combatUnits.get(bluePlayer.combatUnits.indexOf(fleet)).location;
                    bluePlayer.combatUnits.get(bluePlayer.combatUnits.indexOf(fleet)).location = endLocation;
                    bluePlayer.combatUnits.get(bluePlayer.combatUnits.indexOf(unit)).previousLocation = bluePlayer.combatUnits.get(bluePlayer.combatUnits.indexOf(unit)).location;
                    bluePlayer.combatUnits.get(bluePlayer.combatUnits.indexOf(unit)).location = endLocation;
                    break;
                }
            }
        }
    }

    public static void postCombatSimulation() {

    }

    // at turn's end, add units to the list that may have remained stationary
    private static void cleanList() {
        boolean found = false; // flag for whether the unit has been found

        if (!combatUnitsRed.isEmpty()) {
            // obtain a list of red unit ids for reference
            int[] redIDs = new int[combatUnitsRed.size()];
            for (int i = 0; i < combatUnitsRed.size(); i++) {
                redIDs[i] = combatUnitsRed.get(i).cUnitID;
            }
            // determine which units have not been added to the lists
            for (int i = 0; i < Scenario.redPlayer.combatUnits.size(); i++) { // for each possible unit in the faction
                for (int j = 0; j < redIDs.length; j++) { // for each unit accounted for in the faction
                    if (Scenario.redPlayer.combatUnits.get(i).cUnitID == redIDs[j]) { // if the unit id is found in the lists
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
        } else {
            for (int i = 0; i < Scenario.redPlayer.combatUnits.size(); i++) { // for each possible unit in the faction
                combatUnitsRed.add(Scenario.redPlayer.combatUnits.get(i));
                redUnitRoad.add(null);
                redUnitEnd.add(Scenario.redPlayer.combatUnits.get(i).location);
            }
        }

        if (!combatUnitsBlue.isEmpty()) {
            // obtain a list of blue unit ids for reference
            int[] blueIDs = new int[combatUnitsBlue.size()];
            for (int i = 0; i < combatUnitsBlue.size(); i++) {
                blueIDs[i] = combatUnitsBlue.get(i).cUnitID;
            }

            // determine which units have not been added to the lists
            for (int i = 0; i < Scenario.bluePlayer.combatUnits.size(); i++) { // for each possible unit in the faction
                for (int j = 0; j < blueIDs.length; j++) { // for each unit accounted for in the faction
                    if (Scenario.bluePlayer.combatUnits.get(i).cUnitID == blueIDs[j]) { // if the unit id is found in the lists
                        found = true; // indicate that the unit has been found
                    }
                }
                if (found == false) { // if the unit was not found, add it to the lists
                    combatUnitsBlue.add(Scenario.bluePlayer.combatUnits.get(i));
                    blueUnitRoad.add(null);
                    blueUnitEnd.add(Scenario.bluePlayer.combatUnits.get(i).location);
                }
                found = false;
            }
        } else {
            for (int i = 0; i < Scenario.bluePlayer.combatUnits.size(); i++) { // for each possible unit in the faction
                combatUnitsBlue.add(Scenario.bluePlayer.combatUnits.get(i));
                blueUnitRoad.add(null);
                blueUnitEnd.add(Scenario.bluePlayer.combatUnits.get(i).location);
            }
        }

    }

    // Method called to in order to simulate simutanious movement
    public static void processEvents() {
        // storage of all the red and blue units participating in a battle
        ArrayList<CombatUnit> redList = new ArrayList<CombatUnit>();
        ArrayList<CombatUnit> blueList = new ArrayList<CombatUnit>();

        cleanList(); // first clean up the list
       /* 
        // for each of the possible roads, search for possible collisions
        for (int i = 0; i < Scenario.listOfRoads.length; i++) {
            for (int j = 0; j < combatUnitsRed.size(); j++) { // find the red units on this road
                if (redUnitRoad.get(j) != null) {
                    if (redUnitRoad.get(j).roadID == i) {
                        redCombatListCollision.add(combatUnitsRed.get(j));
                        redCombatUnitPreviousLocation.add(combatUnitsRed.get(j).location);
                        redCombatUnitEndLocation.add(redUnitEnd.get(j));
                        redCombatRoad.add(redUnitRoad.get(j));
                    }
                }
            }
            for (int j = 0; j < combatUnitsBlue.size(); j++) { // find the blue units on this road
                if (blueUnitRoad.get(j) != null) {
                    if (blueUnitRoad.get(j).roadID == i) {
                        blueCombatListCollision.add(combatUnitsBlue.get(j));
                        blueCombatUnitPreviousLocation.add(combatUnitsBlue.get(j).location);
                        blueCombatUnitEndLocation.add(blueUnitEnd.get(j));
                        blueCombatRoad.add(blueUnitRoad.get(j));
                    }
                }
            }

            // if there are both red and blue units on this road, activate a collision on road battle
            if (redCombatListCollision.size() > 0 && blueCombatListCollision.size() > 0) {
                CombatInstance combat = new CombatInstance(redCombatListCollision, blueCombatListCollision,
                        redCombatRoad, blueCombatRoad, redCombatUnitEndLocation, blueCombatUnitEndLocation,
                        redCombatUnitPreviousLocation, blueCombatUnitPreviousLocation, false);

                redList.addAll(redCombatListNode);
                blueList.addAll(blueCombatListNode);

                combatQueue.add(combat);
                
                 //redList.addAll(redCombatListCollision);
                 //blueList.addAll(blueCombatListCollision);
                 //battle.doBattleOnRoad(redCombatListCollision, redCombatUnitPreviousLocation, redCombatUnitEndLocation,
                 //blueCombatListCollision, blueCombatUnitPreviousLocation, blueCombatUnitEndLocation);
                 
            }

            // clear the lists for use in the next road case
            redCombatListCollision.clear();
            redCombatUnitPreviousLocation.clear();
            redCombatUnitEndLocation.clear();
            blueCombatListCollision.clear();
            blueCombatUnitPreviousLocation.clear();
            blueCombatUnitEndLocation.clear();
        }
        for (Node listOfNode : Scenario.listOfNodes) {
            for (int j = 0; j < redUnitEnd.size(); j++) {
                // find red units on this node
                if (redUnitEnd.get(j).id == listOfNode.id) {
                    redCombatListNode.add(combatUnitsRed.get(j));
                    redCombatUnitPreviousLocation.add(combatUnitsRed.get(j).location);
                    redCombatUnitEndLocation.add(redUnitEnd.get(j));
                    redCombatRoad.add(null);
                }
            }
            for (int j = 0; j < blueUnitEnd.size(); j++) {
                // find blue units on this node
                if (blueUnitEnd.get(j).id == listOfNode.id) {
                    blueCombatListNode.add(combatUnitsBlue.get(j));
                    blueCombatUnitPreviousLocation.add(combatUnitsBlue.get(j).location);
                    blueCombatUnitEndLocation.add(blueUnitEnd.get(j));
                    blueCombatRoad.add(null);
                }
            }
            // if both red and blue units were found on this node, then activate a battle on node
            if (redCombatListNode.size() > 0 && blueCombatListNode.size() > 0) {
                
                   
                CombatInstance combat = new CombatInstance(redCombatListNode, blueCombatListNode,
                        redCombatRoad, blueCombatRoad, redCombatUnitEndLocation, blueCombatUnitEndLocation,
                        redCombatUnitPreviousLocation, blueCombatUnitPreviousLocation, true);

                redList.addAll(redCombatListNode);
                blueList.addAll(blueCombatListNode);

                combatQueue.add(combat);
                
                 //battle.PVPdoCampBattleOnNode(Scenario.listOfNodes[i], redCombatListNode, redCombatUnitPreviousLocation, redCombatUnitEndLocation,
                 //blueCombatListNode, blueCombatUnitPreviousLocation, blueCombatUnitEndLocation);
                 
            }
            // clear the lists for use in the next node case
            redCombatListNode.clear();
            redCombatUnitPreviousLocation.clear();
            redCombatUnitEndLocation.clear();
            blueCombatListNode.clear();
            blueCombatUnitPreviousLocation.clear();
            blueCombatUnitEndLocation.clear();
        }
*/
        // check for red units that are not participating in combat
        for (int j = 0; j < combatUnitsRed.size(); j++) { // for each of the possible red units
            // check if the unit is present in either node or road conflicts
            if (!redList.contains(combatUnitsRed.get(j))) {
                final int j2 = j;
                ArrayList<Node> temp = new ArrayList<Node>() {
                    {
                        add(combatUnitsRed.get(j2).location);
                        add(redUnitEnd.get(j2));
                    }
                };
                combatUnitsRed.get(j2).previousLocation = combatUnitsRed.get(j2).location;
                combatUnitsRed.get(j2).location = redUnitEnd.get(j);
                resolvedQueueC.add(combatUnitsRed.get(j2));
                resolvedQueueN.add(temp); 
                 // if the unit did not participate in battle, move the unit to intended location
                 //combatUnitsRed.get(j).previousLocation = combatUnitsRed.get(j).location;
                 //combatUnitsRed.get(j).location = redUnitEnd.get(j);
                 

            }
        }

        // check for blue units that are not participating in combat
        for (int j = 0; j < combatUnitsBlue.size(); j++) { // for each of the possible blue units
            // check if the unit is present in either node or road conflict
            if (!blueList.contains(combatUnitsBlue.get(j))) {
                final int j2 = j;
                ArrayList<Node> temp = new ArrayList<Node>() {
                    {
                        add(combatUnitsBlue.get(j2).location);
                        add(blueUnitEnd.get(j2));
                    }
                };
                combatUnitsBlue.get(j2).previousLocation = combatUnitsBlue.get(j2).location;
                combatUnitsBlue.get(j2).location = blueUnitEnd.get(j);
                resolvedQueueC.add(combatUnitsBlue.get(j2));
                resolvedQueueN.add(temp);
                
                 // if the unit did not participate in battle, move the unit to the intended location
                 //combatUnitsBlue.get(j).previousLocation = combatUnitsBlue.get(j).location;
                 //combatUnitsBlue.get(j).location = blueUnitEnd.get(j);
                 
            }
        }
        //clearRegistry();
        simulateMovement();
    }

    public static void simulateMovement() {
        for (int i = 0; i < combatQueue.size(); i++) {
            CombatInstance combat = combatQueue.get(i);
            if (combat.isNode) {
                battle.PVPdoCampBattleOnNode(combat.redEndLocation.get(0),
                        combat.redUnits, combat.redFromLocation, combat.redEndLocation,
                        combat.blueUnits, combat.blueFromLocation, combat.blueEndLocation);
            } else {
                battle.doBattleOnRoad(combat.redUnits, combat.redFromLocation, combat.redEndLocation, 
                        combat.blueUnits, combat.blueFromLocation, combat.blueEndLocation);
            }
        }
    }

    // Method called to in order to handle unit merges
    public static void mergeUnits(int oneNum, int twoNum) {
        CombatUnit[] unit = new CombatUnit[2];
        CombatUnit temp; // temporary unit to store new unit
        //boolean found = false; // flag for use in the unit search
        //int c = -1; // initialize the counter value for use in the unit search

        /// determine the player to be used
        if (Global.curPlayer == 0) {
            currentPlayer = Scenario.redPlayer;
        } else {
            currentPlayer = Scenario.bluePlayer;
        }

        for (CombatUnit armies : currentPlayer.combatUnits) {
            if (armies.cUnitID == oneNum) {
                unit[0] = armies;

            }
            if (armies.cUnitID == twoNum) {
                unit[1] = armies;

            }
        }

//        for (int j = 0; j < 2; j++) {
//            // search for the unit in order to obtain reference and values
//            while (!found || c != currentPlayer.combatUnits.size()) { // while unit not found
//                c++; // increase the counter
//                if (currentPlayer.combatUnits.get(c).cUnitID == nums[j]) { // for each unit id held by player, check is match to passed unit id
//                    found = true;
//                }
//                if (found == true) { // if the unit has been found
//                /*
//                     out of bounds error on i
//                     */
//                    unit[j] = currentPlayer.combatUnits.get(c); // set the reference to the correct unit
//                }
//            }
//        }
        int[] scaled = {0, 0, 0};
        int sumSize = unit[0].size + unit[1].size;
        int[] sizes = {unit[0].size, unit[1].size, sumSize};
        int health = (unit[0].illnessRating + unit[1].illnessRating) / 2;

        if (sumSize > 15) {
            sumSize = 15;
        }

        for (int i = 0; i < 3; i++) {
            if (sizes[i] < 6) {
                scaled[i] = 0;
            } else if (sizes[i] < 11) {
                scaled[i] = 1;
            } else {
                scaled[i] = 2;
            }
        }

        // assume sanitization
        // works for case S+S only
        if (scaled[2] == scaled[0] || scaled[2] == scaled[1]) {
            if (sumSize < 6) {
                sumSize = 5;
            } else if (sumSize < 11) {
                sumSize = 10;
            } else {
                sumSize = 15;
            }
        }

        /*
         temp = new CombatUnit(false, unit[0].cUnitID, sumSize, health, unit[0].location, unit[0].faction);

         currentPlayer.combatUnits.add(temp);
         currentPlayer.combatUnits.remove(unit[0]);
         currentPlayer.combatUnits.remove(unit[1]);
         */
        if (Global.curPlayer == 0) {
            temp = new CombatUnit(false, unit[0].cUnitID, sumSize, health, unit[0].location, redPlayer);
            redPlayer.combatUnits.add(temp);
            redPlayer.combatUnits.remove(unit[0]);
            redPlayer.combatUnits.remove(unit[1]);
        } else {
            temp = new CombatUnit(false, unit[0].cUnitID, sumSize, health, unit[0].location, bluePlayer);
            bluePlayer.combatUnits.add(temp);
            bluePlayer.combatUnits.remove(unit[0]);
            bluePlayer.combatUnits.remove(unit[1]);
        }
        /*
         currentPlayer.addUnit(temp);
         currentPlayer.removeUnit(unit[0]);
         currentPlayer.removeUnit(unit[1]);
         */
    }

    public static void divideUnit(int unitNum) {
        CombatUnit unit = null;
        //boolean found = false; // flag for use in the unit search
        //int i = 0; // initialize the counter value for use in the unit search
        CombatUnit one, two;

        //int divSize = unit.size / 2;
        boolean idFind = true;
        int checker = 0;
        int id = 0;

        int i2 = 0;
        // determine the player to be used
        if (Global.curPlayer == 0) {
            currentPlayer = Scenario.redPlayer;
            for (CombatUnit army : Scenario.redPlayer.combatUnits) {
                if (army.cUnitID == unitNum) {
                    unit = Scenario.redPlayer.combatUnits.get(i2);
                }
                i2++;
            }
        } else {
            currentPlayer = Scenario.bluePlayer;
            for (CombatUnit army : Scenario.bluePlayer.combatUnits) {
                if (army.cUnitID == unitNum) {
                    unit = Scenario.bluePlayer.combatUnits.get(i2);
                }
                i2++;
            }
        }

        int divSize = (int) (unit.size / 2);

        /*// search for the unit in order to obtain reference and values
         while (!found && i != currentPlayer.combatUnits.size()) { // while unit not found

         if (currentPlayer.combatUnits.get(i).cUnitID == unitNum) { // for each unit id held by player, check is match to passed unit id
         found = true;
         }
         if (found == true) { // if the unit has been found
                
         unit = currentPlayer.combatUnits.get(i); // set the reference to the correct unit
         }
         i++; // increase the counter
         }*/
        while (idFind) {
            checker = 0;
            for (int j = 0; j < Scenario.redPlayer.combatUnits.size(); j++) {
                if (Scenario.redPlayer.combatUnits.get(j).cUnitID == id) {
                    checker++;
                }
            }
            for (int j = 0; j < Scenario.bluePlayer.combatUnits.size(); j++) {
                if (Scenario.bluePlayer.combatUnits.get(j).cUnitID == id) {
                    checker++;
                }
            }
            if (checker == 0) {
                idFind = false;
            }
            id++;
        }

        /*
         one = new CombatUnit(false, unit.cUnitID, divSize, unit.illnessRating, unit.location, unit.faction);
         two = new CombatUnit(false, id, divSize, unit.illnessRating, unit.location, unit.faction);

         currentPlayer.combatUnits.remove(unit);
         currentPlayer.combatUnits.add(one);
         currentPlayer.combatUnits.add(two);
         */
        if (Global.curPlayer == 0) {
            one = new CombatUnit(false, unit.cUnitID, divSize, unit.illnessRating, unit.location, redPlayer);
            two = new CombatUnit(false, id, divSize, unit.illnessRating, unit.location, redPlayer);

            redPlayer.combatUnits.remove(unit);
            redPlayer.combatUnits.add(one);
            redPlayer.combatUnits.add(two);
        } else {
            one = new CombatUnit(false, unit.cUnitID, divSize, unit.illnessRating, unit.location, bluePlayer);
            two = new CombatUnit(false, id, divSize, unit.illnessRating, unit.location, bluePlayer);

            bluePlayer.combatUnits.remove(unit);
            bluePlayer.combatUnits.add(one);
            bluePlayer.combatUnits.add(two);
        }
        /*one.faction.removeUnit(unit);
         one.faction.addUnit(one);
         one.faction.addUnit(two);
         */

    }

    // Method to reset the arrays at turn's end
    public static void clearRegistry() {
        combatUnitsRed = new ArrayList<CombatUnit>(); // list of all units held by the red faction in motion
        combatUnitsBlue = new ArrayList<CombatUnit>(); // list of all units held by the blue faction in motion
        redUnitRoad = new ArrayList<Road>(); // list of roads occupied by red faction
        blueUnitRoad = new ArrayList<Road>(); // list of roads occupied by blue faction
        redUnitEnd = new ArrayList<Node>(); // list of node end locations for red
        blueUnitEnd = new ArrayList<Node>(); // list of node end locations for blue
        redCombatListCollision = new ArrayList<CombatUnit>();
        blueCombatListCollision = new ArrayList<CombatUnit>();
        redCombatListNode = new ArrayList<CombatUnit>();
        blueCombatListNode = new ArrayList<CombatUnit>();
        redCombatUnitPreviousLocation = new ArrayList<Node>();
        redCombatUnitEndLocation = new ArrayList<Node>();
        blueCombatUnitPreviousLocation = new ArrayList<Node>();
        blueCombatUnitEndLocation = new ArrayList<Node>();
        redCombatRoad = new ArrayList<Road>();
        blueCombatRoad = new ArrayList<Road>();
    }
}
