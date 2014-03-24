/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
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
    private static Player redPlayer = Scenario.redPlayer;
    private static Player bluePlayer = Scenario.bluePlayer;
    private static Player currentPlayer; // player objects for use in the events
    private static Battle battle; // initialize the battle object for method calls
    private static ArrayList<CombatInstance> combatQueue = new ArrayList<CombatInstance>();
    private static ArrayList<CombatUnit> resolvedQueueC = new ArrayList<CombatUnit>();
    private static ArrayList<ArrayList<Node>> resolvedQueueN = new ArrayList<ArrayList<Node>>();
    private static ArrayList<CombatUnit> defeatQueue = new ArrayList<CombatUnit>();
    //private static HashMap ferryList = new HashMap();
    private static ArrayList<ArrayList<CombatUnit>> redCombatListCollisionL = new ArrayList<ArrayList<CombatUnit>>(); // list of red faction units in combat on road
    private static ArrayList<ArrayList<CombatUnit>> blueCombatListCollisionL = new ArrayList<ArrayList<CombatUnit>>(); // list of blue faction units in combat on road
    private static ArrayList<ArrayList<Node>> redCombatUnitPreviousLocationC = new ArrayList<ArrayList<Node>>(); // list of red faction previous locations pre-combat
    private static ArrayList<ArrayList<Node>> redCombatUnitEndLocationC = new ArrayList<ArrayList<Node>>(); // list of red faction tentative end locations pre-combat
    private static ArrayList<ArrayList<Node>> blueCombatUnitPreviousLocationC = new ArrayList<ArrayList<Node>>(); // list of blue faction previous locations pre-combat
    private static ArrayList<ArrayList<Node>> blueCombatUnitEndLocationC = new ArrayList<ArrayList<Node>>(); // list of blue faction tentative end locations pre-combat
    private static ArrayList<ArrayList<Road>> redCombatRoadC = new ArrayList<ArrayList<Road>>();
    private static ArrayList<ArrayList<Road>> blueCombatRoadC = new ArrayList<ArrayList<Road>>();
    private static ArrayList<ArrayList<CombatUnit>> redCombatListNodeL = new ArrayList<ArrayList<CombatUnit>>(); // list of red faction units in combat on node
    private static ArrayList<ArrayList<CombatUnit>> blueCombatListNodeL = new ArrayList<ArrayList<CombatUnit>>(); // list of blue faction units in combat on node
    private static ArrayList<ArrayList<Node>> redCombatUnitPreviousLocationN = new ArrayList<ArrayList<Node>>(); // list of red faction previous locations pre-combat
    private static ArrayList<ArrayList<Node>> redCombatUnitEndLocationN = new ArrayList<ArrayList<Node>>(); // list of red faction tentative end locations pre-combat
    private static ArrayList<ArrayList<Node>> blueCombatUnitPreviousLocationN = new ArrayList<ArrayList<Node>>(); // list of blue faction previous locations pre-combat
    private static ArrayList<ArrayList<Node>> blueCombatUnitEndLocationN = new ArrayList<ArrayList<Node>>(); // list of blue faction tentative end locations pre-combat
    private static ArrayList<ArrayList<Road>> redCombatRoadN = new ArrayList<ArrayList<Road>>();
    private static ArrayList<ArrayList<Road>> blueCombatRoadN = new ArrayList<ArrayList<Road>>();

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
        if (Scenario.redPlayer.playerID == Global.curPlayer) {
            currentPlayer = Scenario.redPlayer;
            for (CombatUnit CUnits : Scenario.redPlayer.combatUnits) {
                if (CUnits.cUnitID == unitNum) {
                    unit = CUnits;
                    //JOptionPane.showMessageDialog(null, "RED UNIT FOUND!");
                    break;
                }
            }
        } else {
            currentPlayer = Scenario.bluePlayer;
            for (CombatUnit CUnits : Scenario.bluePlayer.combatUnits) {
                if (CUnits.cUnitID == unitNum) {
                    unit = CUnits;
                    //JOptionPane.showMessageDialog(null, "BLUE UNIT FOUND!");
                    break;
                }
            }
        }

        if (unit == null) {
            JOptionPane.showMessageDialog(null, "UNIT NOT FOUND!");
        }
        // check if the unit has already been added to the list
        // if the unit has already been added, remove the current values
        if (currentPlayer.playerID == 0) {
            if (!combatUnitsRed.isEmpty()) {
                if (combatUnitsRed.contains(unit)) {
                    int i = combatUnitsRed.indexOf(unit);
//                    redUnitRoad.remove(combatUnitsRed.indexOf(unit));
//                    redUnitEnd.remove(combatUnitsRed.indexOf(unit));
//                    combatUnitsRed.remove(unit);
//                    combatUnitsRed.add(unit);
//                    redUnitRoad.add(road);
//                    redUnitEnd.add(endLocation);
                    combatUnitsRed.set(i, unit);
                    redUnitRoad.set(i, road);
                    redUnitEnd.set(i, endLocation);
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
                    int i = combatUnitsBlue.indexOf(unit);
//                    blueUnitRoad.remove(combatUnitsBlue.indexOf(unit));
//                    blueUnitEnd.remove(combatUnitsBlue.indexOf(unit));
//                    combatUnitsBlue.remove(unit);
//                    combatUnitsBlue.add(unit);
//                    blueUnitRoad.add(road);
//                    blueUnitEnd.add(endLocation);
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

//    public static void addMovementFerry(int fleetNum, int unitNum, Road road, int endLocationNum) {
//        CombatUnit unit = null;
//
//        // determine the player to be used
//        if (Global.curPlayer == 0) {
//            for (CombatUnit CUnits : Scenario.redPlayer.combatUnits) {
//                if (CUnits.cUnitID == unitNum) {
//                    unit = CUnits;
//                    ferryList.put(fleetNum, unit);
//                    //Scenario.redPlayer.combatUnits.remove(unit);
////        addMovement(unitNum, road, endLocationNum);
////        addMovement(fleetNum, road, endLocationNum);
//                    break;
//                }
//            }
//        } else {
//            for (CombatUnit CUnits : Scenario.bluePlayer.combatUnits) {
//                if (CUnits.cUnitID == unitNum) {
//                    unit = CUnits;
//                    ferryList.put(fleetNum, unit);
//                    //Scenario.bluePlayer.combatUnits.remove(unit);
////                    addMovement(unitNum, road, endLocationNum);
////                    addMovement(fleetNum, road, endLocationNum);
//                    break;
//                }
//            }
//        }
//    }
//    public static void removeMovementFerry(int fleetNum, Road road, int endLocationNum) {
//        CombatUnit unit = null;
//        unit = (CombatUnit) ferryList.get(fleetNum);
//        ferryList.remove(fleetNum);
//
//        addMovement(unit.cUnitID, road, endLocationNum);
//        addMovement(fleetNum, road, endLocationNum);
//    }
    private static void fulfillMovement() {

    }

//    private static void fulfillFerry(int fleetNum, Node endLocation) {
//        CombatUnit fleet = null;
//        CombatUnit unit = (CombatUnit) ferryList.get(fleetNum);
//        ferryList.remove(fleetNum);
//
//        if (unit.faction.playerID == 0) {
//            Scenario.redPlayer.combatUnits.add(unit);
//            for (CombatUnit CUnits : Scenario.redPlayer.combatUnits) {
//                if (CUnits.cUnitID == fleetNum) {
//                    fleet = CUnits;
//                    Scenario.redPlayer.combatUnits.get(redPlayer.combatUnits.indexOf(fleet)).previousLocation = redPlayer.combatUnits.get(redPlayer.combatUnits.indexOf(fleet)).location;
//                    Scenario.redPlayer.combatUnits.get(redPlayer.combatUnits.indexOf(fleet)).location = endLocation;
//                    Scenario.redPlayer.combatUnits.get(redPlayer.combatUnits.indexOf(unit)).previousLocation = redPlayer.combatUnits.get(redPlayer.combatUnits.indexOf(unit)).location;
//                    Scenario.redPlayer.combatUnits.get(redPlayer.combatUnits.indexOf(unit)).location = endLocation;
//                    break;
//                }
//            }
//        } else {
//            Scenario.bluePlayer.combatUnits.add(unit);
//            for (CombatUnit CUnits : Scenario.bluePlayer.combatUnits) {
//                if (CUnits.cUnitID == fleetNum) {
//                    fleet = CUnits;
//                    Scenario.bluePlayer.combatUnits.get(bluePlayer.combatUnits.indexOf(fleet)).previousLocation = bluePlayer.combatUnits.get(bluePlayer.combatUnits.indexOf(fleet)).location;
//                    Scenario.bluePlayer.combatUnits.get(bluePlayer.combatUnits.indexOf(fleet)).location = endLocation;
//                    Scenario.bluePlayer.combatUnits.get(bluePlayer.combatUnits.indexOf(unit)).previousLocation = bluePlayer.combatUnits.get(bluePlayer.combatUnits.indexOf(unit)).location;
//                    Scenario.bluePlayer.combatUnits.get(bluePlayer.combatUnits.indexOf(unit)).location = endLocation;
//                    break;
//                }
//            }
//        }
//    }
    public static void postCombatSimulation() {

    }

    // at turn's end, add units to the list that may have remained stationary
    private static void cleanList() {
        boolean found = false; // flag for whether the unit has been found

        if (!combatUnitsRed.isEmpty()) {
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
        } else {
            for (int i = 0; i < Scenario.redPlayer.combatUnits.size(); i++) { // for each possible unit in the faction
                combatUnitsRed.add(Scenario.redPlayer.combatUnits.get(i));
                redUnitRoad.add(null);
                redUnitEnd.add(Scenario.redPlayer.combatUnits.get(i).location);
            }
        }

        if (!combatUnitsBlue.isEmpty()) {
            // determine which units have not been added to the lists
            for (int i = 0; i < Scenario.bluePlayer.combatUnits.size(); i++) { // for each possible unit in the faction
                for (int j = 0; j < combatUnitsBlue.size(); j++) { // for each unit accounted for in the faction
                    if (Scenario.bluePlayer.combatUnits.get(i).cUnitID == combatUnitsBlue.get(j).cUnitID) { // if the unit id is found in the lists
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

        for (int i = 0; i < Scenario.listOfRoads.length; i++) {
            redCombatListCollisionL.add(new ArrayList<CombatUnit>());
            blueCombatListCollisionL.add(new ArrayList<CombatUnit>());
            redCombatUnitPreviousLocationC.add(new ArrayList<Node>());
            redCombatUnitEndLocationC.add(new ArrayList<Node>());
            blueCombatUnitPreviousLocationC.add(new ArrayList<Node>());
            blueCombatUnitEndLocationC.add(new ArrayList<Node>());
            redCombatRoadC.add(new ArrayList<Road>());
            blueCombatRoadC.add(new ArrayList<Road>());
        }

        for (int i = 0; i < Scenario.listOfNodes.length; i++) {
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
            /*
             ArrayList<CombatUnit> redCombatListCollision = new ArrayList<CombatUnit>(); // list of red faction units in combat on road
             ArrayList<CombatUnit> blueCombatListCollision = new ArrayList<CombatUnit>(); // list of blue faction units in combat on road
             ArrayList<Node> redCombatUnitPreviousLocation = new ArrayList<Node>(); // list of red faction previous locations pre-combat
             ArrayList<Node> redCombatUnitEndLocation = new ArrayList<Node>(); // list of red faction tentative end locations pre-combat
             ArrayList<Node> blueCombatUnitPreviousLocation = new ArrayList<Node>(); // list of blue faction previous locations pre-combat
             ArrayList<Node> blueCombatUnitEndLocation = new ArrayList<Node>(); // list of blue faction tentative end locations pre-combat
             ArrayList<Road> redCombatRoad = new ArrayList<Road>();
             ArrayList<Road> blueCombatRoad = new ArrayList<Road>();
             */
            for (int j = 0; j < combatUnitsRed.size(); j++) { // find the red units on this road
                if (redUnitRoad.get(j) != null) {
                    if (redUnitRoad.get(j).roadID == i) {
                        redCombatListCollisionL.get(i).add(combatUnitsRed.get(j));
                        redCombatUnitPreviousLocationC.get(i).add(combatUnitsRed.get(j).location);
                        redCombatUnitEndLocationC.get(i).add(redUnitEnd.get(j));
                        redCombatRoadC.get(i).add(redUnitRoad.get(j));
                    }
                }
            }

            for (int j = 0; j < combatUnitsBlue.size(); j++) { // find the blue units on this road
                if (blueUnitRoad.get(j) != null) {
                    if (blueUnitRoad.get(j).roadID == i) {
                        blueCombatListCollisionL.get(i).add(combatUnitsBlue.get(j));
                        blueCombatUnitPreviousLocationC.get(i).add(combatUnitsBlue.get(j).location);
                        blueCombatUnitEndLocationC.get(i).add(blueUnitEnd.get(j));
                        blueCombatRoadC.get(i).add(blueUnitRoad.get(j));
                    }
                }
            }

            // if there are both red and blue units on this road, activate a collision on road battle
            if (redCombatListCollisionL.get(i).size() > 0 && blueCombatListCollisionL.get(i).size() > 0) {

                CombatInstance combat = new CombatInstance(redCombatListCollisionL.get(i), blueCombatListCollisionL.get(i),
                        redCombatRoadC.get(i), blueCombatRoadC.get(i), redCombatUnitEndLocationC.get(i),
                        blueCombatUnitEndLocationC.get(i), redCombatUnitPreviousLocationC.get(i),
                        blueCombatUnitPreviousLocationC.get(i), false);

                redList.addAll(redCombatListCollisionL.get(i));
                blueList.addAll(blueCombatListCollisionL.get(i));

                combatQueue.add(combat);

                //redList.addAll(redCombatListCollision);
                //blueList.addAll(blueCombatListCollision);
                //battle.doBattleOnRoad(redCombatListCollision, redCombatUnitPreviousLocation, redCombatUnitEndLocation,
                //blueCombatListCollision, blueCombatUnitPreviousLocation, blueCombatUnitEndLocation);
            }

            // clear the lists for use in the next road case
        }
        for (int i = 0; i < Scenario.listOfNodes.length; i++) {
            /*
             ArrayList<CombatUnit> redCombatListNode = new ArrayList<CombatUnit>(); // list of red faction units in combat on node
             ArrayList<CombatUnit> blueCombatListNode = new ArrayList<CombatUnit>(); // list of blue faction units in combat on node
             ArrayList<Node> redCombatUnitPreviousLocation = new ArrayList<Node>(); // list of red faction previous locations pre-combat
             ArrayList<Node> redCombatUnitEndLocation = new ArrayList<Node>(); // list of red faction tentative end locations pre-combat
             ArrayList<Node> blueCombatUnitPreviousLocation = new ArrayList<Node>(); // list of blue faction previous locations pre-combat
             ArrayList<Node> blueCombatUnitEndLocation = new ArrayList<Node>(); // list of blue faction tentative end locations pre-combat
             ArrayList<Road> redCombatRoad = new ArrayList<Road>();
             ArrayList<Road> blueCombatRoad = new ArrayList<Road>();
             */
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
            // if both red and blue units were found on this node, then activate a battle on node
            if (redCombatListNodeL.get(i).size() > 0 && blueCombatListNodeL.get(i).size() > 0) {

                CombatInstance combat = new CombatInstance(redCombatListNodeL.get(i), blueCombatListNodeL.get(i),
                        redCombatRoadN.get(i), blueCombatRoadN.get(i), redCombatUnitEndLocationN.get(i),
                        blueCombatUnitEndLocationN.get(i), redCombatUnitPreviousLocationN.get(i),
                        blueCombatUnitPreviousLocationN.get(i), true);

                redList.addAll(redCombatListNodeL.get(i));
                blueList.addAll(blueCombatListNodeL.get(i));

                combatQueue.add(combat);

                //battle.PVPdoCampBattleOnNode(Scenario.listOfNodes[i], redCombatListNode, redCombatUnitPreviousLocation, redCombatUnitEndLocation,
                //blueCombatListNode, blueCombatUnitPreviousLocation, blueCombatUnitEndLocation);
            }
            // clear the lists for use in the next node case
        }
        // check for red units that are not participating in combat
        for (int j = 0; j < combatUnitsRed.size(); j++) { // for each of the possible red units
            // check if the unit is present in either node or road conflicts
            if (!redList.contains(combatUnitsRed.get(j))) {
                final int j2 = j;
                //ArrayList<Node> temp = new ArrayList<Node>() {
                //    {
                //        add(combatUnitsRed.get(j2).location);
                //        add(redUnitEnd.get(j2));
                //    }
                //};
//                if (ferryList.containsKey(combatUnitsRed.get(j).cUnitID) || ferryList.containsValue(combatUnitsRed.get(j2))) {
//                    combatUnitsRed.get(j2).previousLocation = combatUnitsRed.get(j2).location;
//                    combatUnitsRed.get(j2).location = redUnitEnd.get(j2);
//                } else {
                combatUnitsRed.get(j2).previousLocation = combatUnitsRed.get(j2).location;
                combatUnitsRed.get(j2).location = redUnitEnd.get(j2);
//                }
                //resolvedQueueC.add(combatUnitsRed.get(j2));
                //resolvedQueueN.add(temp);
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
                //ArrayList<Node> temp = new ArrayList<Node>() {
                //    {
                //        add(combatUnitsBlue.get(j2).location);
                //        add(blueUnitEnd.get(j2));
                //    }
                //};
//                if (ferryList.containsKey(combatUnitsBlue.get(j).cUnitID) || ferryList.containsValue(combatUnitsBlue.get(j))) {
//                    fulfillFerry(combatUnitsBlue.get(j).cUnitID, blueUnitEnd.get(j));
//                } else {
                combatUnitsBlue.get(j2).previousLocation = combatUnitsBlue.get(j2).location;
                combatUnitsBlue.get(j2).location = blueUnitEnd.get(j);
                //}
                //resolvedQueueC.add(combatUnitsBlue.get(j2));
                //resolvedQueueN.add(temp);

                // if the unit did not participate in battle, move the unit to the intended location
                //combatUnitsBlue.get(j).previousLocation = combatUnitsBlue.get(j).location;
                //combatUnitsBlue.get(j).location = blueUnitEnd.get(j);
            }
        }
        //clearRegistry();
        simulateMovement();
    }

    public static void simulateMovement() {
        for (CombatInstance combat : combatQueue) {
            if (combat.redEndLocation().size() > 0) {
                if (combat.isNode()) {

                    battle.PVPdoCampBattleOnNode(combat.redEndLocation().get(0),
                            combat.redUnits(), combat.redFromLocation(), combat.redEndLocation(),
                            combat.blueUnits(), combat.blueFromLocation(), combat.blueEndLocation());
                } else {
                    battle.doBattleOnRoad(combat.redUnits(), combat.redFromLocation(), combat.redEndLocation(),
                            combat.blueUnits(), combat.blueFromLocation(), combat.blueEndLocation());
                }
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
            temp = new CombatUnit(false, unit[0].cUnitID, sumSize, health, unit[0].location, Scenario.redPlayer);
            Scenario.redPlayer.combatUnits.add(temp);
            Scenario.redPlayer.combatUnits.remove(unit[0]);
            Scenario.redPlayer.combatUnits.remove(unit[1]);
        } else {
            temp = new CombatUnit(false, unit[0].cUnitID, sumSize, health, unit[0].location, Scenario.bluePlayer);
            Scenario.bluePlayer.combatUnits.add(temp);
            Scenario.bluePlayer.combatUnits.remove(unit[0]);
            Scenario.bluePlayer.combatUnits.remove(unit[1]);
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
            one = new CombatUnit(false, unit.cUnitID, divSize, unit.illnessRating, unit.location, Scenario.redPlayer);
            two = new CombatUnit(false, id, divSize, unit.illnessRating, unit.location, Scenario.redPlayer);

            Scenario.redPlayer.combatUnits.remove(unit);
            Scenario.redPlayer.combatUnits.add(one);
            Scenario.redPlayer.combatUnits.add(two);
        } else {
            one = new CombatUnit(false, unit.cUnitID, divSize, unit.illnessRating, unit.location, Scenario.bluePlayer);
            two = new CombatUnit(false, id, divSize, unit.illnessRating, unit.location, Scenario.bluePlayer);

            Scenario.bluePlayer.combatUnits.remove(unit);
            Scenario.bluePlayer.combatUnits.add(one);
            Scenario.bluePlayer.combatUnits.add(two);
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
        combatQueue = new ArrayList<CombatInstance>();
    }
}
