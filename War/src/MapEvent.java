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
    private static ArrayList<CombatUnit> redCombatListCollision = new ArrayList<CombatUnit>(); // list of red faction units in combat on road
    private static ArrayList<CombatUnit> blueCombatListCollision = new ArrayList<CombatUnit>(); // list of blue faction units in combat on road
    private static ArrayList<CombatUnit> redCombatListNode = new ArrayList<CombatUnit>(); // list of red faction units in combat on node
    private static ArrayList<CombatUnit> blueCombatListNode = new ArrayList<CombatUnit>(); // list of blue faction units in combat on node
    private static Player redPlayer, bluePlayer, currentPlayer; // player objects for use in the events
    private static ArrayList<Node> redCombatUnitPreviousLocation = new ArrayList<Node>(); // list of red faction previous locations pre-combat
    private static ArrayList<Node> redCombatUnitEndLocation; // list of red faction tentative end locations pre-combat
    private static ArrayList<Node> blueCombatUnitPreviousLocation; // list of blue faction previous locations pre-combat
    private static ArrayList<Node> blueCombatUnitEndLocation; // list of blue faction tentative end locations pre-combat
    private static Battle battle; // initialize the battle object for method calls

    // Constructor for MapEvent
    public MapEvent() {
        // initialize the parameters
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
    }

    // Method called to to add a movement to the registry
    public static void addMovement(int unitNum, Road road, int endLocationNum) {
        CombatUnit unit = null; // initialize the unit for consideraton
        Node endLocation = Scenario.listOfNodes[endLocationNum]; // load the end locations based on provided index
        
        
        boolean found = false; // flag for use in the unit search
        int i = 0; // initialize the counter value for use in the unit search

        // determine the player to be used
        if (Global.curPlayer == 0) {
            currentPlayer = Scenario.redPlayer;
            for(CombatUnit CUnits : Scenario.redPlayer.combatUnits) {
                if(CUnits.cUnitID == unitNum)
                    unit = CUnits;
            }
        } else {
            currentPlayer = Scenario.bluePlayer;
            for(CombatUnit CUnits : Scenario.bluePlayer.combatUnits) {
                if(CUnits.cUnitID == unitNum)
                    unit = CUnits;
            }
        }

        // check if the unit has already been added to the list
        // if the unit has already been added, remove the current values
        if (!combatUnitsRed.isEmpty() && !redUnitEnd.isEmpty()) {
            if (combatUnitsRed.contains(unit)) {
                combatUnitsRed.remove(unit);
                redUnitRoad.remove(combatUnitsRed.indexOf(unit));
                redUnitEnd.remove(combatUnitsRed.indexOf(unit));
                combatUnitsRed.add(unit);
                redUnitRoad.add(road);
                redUnitEnd.add(endLocation);
            } else if (combatUnitsBlue.contains(unit)) {
                combatUnitsBlue.remove(unit);
                blueUnitRoad.remove(combatUnitsBlue.indexOf(unit));
                blueUnitEnd.remove(combatUnitsBlue.indexOf(unit));
                combatUnitsBlue.add(unit);
                blueUnitRoad.add(road);
                blueUnitEnd.add(endLocation);
            }
        } else {
            if (currentPlayer.playerID == 0) {
                combatUnitsRed.add(unit);
                redUnitRoad.add(road);
                redUnitEnd.add(endLocation);
            } else if (currentPlayer.playerID == 1) {
                combatUnitsBlue.add(unit);
                blueUnitRoad.add(road);
                blueUnitEnd.add(endLocation);
            }
        }
    }

    // at turn's end, add units to the list that may have remained stationary
    private static void cleanList() {
        boolean found = false; // flag for whether the unit has been found

        // obtain a list of red unit ids for reference
        int[] redIDs = new int[combatUnitsRed.size()];
        for (int i = 0; i < combatUnitsRed.size(); i++) {
            redIDs[i] = combatUnitsRed.get(i).cUnitID;
        }

        // obtain a list of blue unit ids for reference
        int[] blueIDs = new int[combatUnitsBlue.size()];
        for (int i = 0; i < combatUnitsBlue.size(); i++) {
            blueIDs[i] = combatUnitsBlue.get(i).cUnitID;
        }

        // determine the player to be used
        if (Global.curPlayer == 0) {
            currentPlayer = Scenario.redPlayer;
        } else {
            currentPlayer = Scenario.bluePlayer;
        }

        // determine which units have not been added to the lists
        for (int i = 0; i < currentPlayer.combatUnits.size(); i++) { // for each possible unit in the faction
            for (int j = 0; j < redIDs.length; j++) { // for each unit accounted for in the faction
                if (currentPlayer.combatUnits.get(i).cUnitID == redIDs[j]) { // if the unit id is found in the lists
                    found = true; // indicate that the unit has been found
                }
                if (found == false) { // if the unit was not found, add it to the lists
                    combatUnitsRed.add(currentPlayer.combatUnits.get(i));
                    redUnitRoad.add(null);
                    redUnitEnd.add(currentPlayer.combatUnits.get(i).location);
                }
            }
        }
        // determine which units have not been added to the lists
        for (int i = 0; i < currentPlayer.combatUnits.size(); i++) { // for each possible unit in the faction
            for (int j = 0; j < blueIDs.length; j++) { // for each unit accounted for in the faction
                if (currentPlayer.combatUnits.get(i).cUnitID == blueIDs[j]) { // if the unit id is found in the lists
                    found = true; // indicate that the unit has been found
                }
                if (found == false) { // if the unit was not found, add it to the lists
                    combatUnitsBlue.add(currentPlayer.combatUnits.get(i));
                    blueUnitRoad.add(null);
                    blueUnitEnd.add(currentPlayer.combatUnits.get(i).location);
                }
            }

        }
    }

    public void removeMovement(/*information needed for item identification*/) {
        /*
         search for the item based on parameter(s)
         .remove(index) from the lists:
         combatUnits____
         ____UnitRoad
         ____UnitEnd
         */
    }

    // Method called to in order to simulate simutanious movement
    public static void processEvents() {
        // storage of all the red and blue units participating in a battle
        ArrayList<CombatUnit> redList = new ArrayList<CombatUnit>();
        ArrayList<CombatUnit> blueList = new ArrayList<CombatUnit>();

        cleanList(); // first clean up the list

        // for each of the possible roads, search for possible collisions
        for (int i = 0; i < Scenario.listOfRoads.length; i++) {
            for (int j = 0; j < combatUnitsRed.size(); j++) { // find the red units on this road
                if (redUnitRoad.get(j) != null)
                if (redUnitRoad.get(j).roadID == i) {
                    redCombatListCollision.add(combatUnitsRed.get(j));
                    redCombatUnitPreviousLocation.add(combatUnitsRed.get(j).location);
                    redCombatUnitEndLocation.add(redUnitEnd.get(j));
                }
            }
            for (int j = 0; j < combatUnitsBlue.size(); j++) { // find the blue units on this road
                if(blueUnitRoad.get(j) != null)
                if (blueUnitRoad.get(j).roadID == i) {
                    blueCombatListCollision.add(combatUnitsBlue.get(j));
                    blueCombatUnitPreviousLocation.add(combatUnitsBlue.get(j).location);
                    blueCombatUnitEndLocation.add(blueUnitEnd.get(j));
                }
            }

            // if there are both red and blue units on this road, activate a collision on road battle
            if (redCombatListCollision.size() > 0 && blueCombatListCollision.size() > 0) {
                redList.addAll(redCombatListCollision);
                blueList.addAll(blueCombatListCollision);
                battle.doBattleOnRoad(redCombatListCollision, redCombatUnitPreviousLocation, redCombatUnitEndLocation,
                        blueCombatListCollision, blueCombatUnitPreviousLocation, blueCombatUnitEndLocation);
            }

            // clear the lists for use in the next road case
            redCombatListCollision.clear();
            redCombatUnitPreviousLocation.clear();
            redCombatUnitEndLocation.clear();
            blueCombatListCollision.clear();
            blueCombatUnitPreviousLocation.clear();
            blueCombatUnitEndLocation.clear();
        }

        // for each of the possible nodes/locations, check if two opposing units collided
        for (int i = 0; i < Scenario.listOfNodes.length; i++) {
            for (int j = 0; j < redUnitEnd.size(); j++) { // find red units on this node
                if (redUnitEnd.get(j).id == i) {
                    redCombatListNode.add(combatUnitsRed.get(j));
                    redCombatUnitPreviousLocation.add(combatUnitsRed.get(j).location);
                    redCombatUnitEndLocation.add(redUnitEnd.get(j));
                }
            }
            for (int j = 0; j < blueUnitEnd.size(); j++) { // find blue units on this node
                if (blueUnitEnd.get(j).id == i) {
                    blueCombatListNode.add(combatUnitsBlue.get(j));
                    blueCombatUnitPreviousLocation.add(combatUnitsBlue.get(j).location);
                    blueCombatUnitEndLocation.add(blueUnitEnd.get(j));
                }
            }

            // if both red and blue units were found on this node, then activate a battle on node
            if (redCombatListNode.size() > 0 && blueCombatListNode.size() > 0) {
                redList.addAll(redCombatListCollision);
                blueList.addAll(blueCombatListCollision);
                battle.PVPdoCampBattleOnNode(Scenario.listOfNodes[i], redCombatListNode, redCombatUnitPreviousLocation, redCombatUnitEndLocation,
                        blueCombatListNode, blueCombatUnitPreviousLocation, blueCombatUnitEndLocation);
            }

            // clear the lists for use in the next node case
            redCombatListCollision.clear();
            redCombatUnitPreviousLocation.clear();
            redCombatUnitEndLocation.clear();
            blueCombatListCollision.clear();
            blueCombatUnitPreviousLocation.clear();
            blueCombatUnitEndLocation.clear();
        }

        // check for red units that are not participating in combat
        for (int j = 0; j < combatUnitsRed.size(); j++) { // for each of the possible red units
            // check if the unit is present in either node or road conflicts
            if (!redList.contains(combatUnitsRed.get(j)) && !redList.contains(combatUnitsRed.get(j))) {
                // if the unit did not participate in battle, move the unit to intended location
                combatUnitsRed.get(j).previousLocation = combatUnitsRed.get(j).location;
                combatUnitsRed.get(j).location = redUnitEnd.get(j);
            }
        }

        // check for blue units that are not participating in combat
        for (int j = 0; j < combatUnitsBlue.size(); j++) { // for each of the possible blue units
            // check if the unit is present in either node or road conflict
            if (!blueList.contains(combatUnitsBlue.get(j)) && !blueList.contains(combatUnitsBlue.get(j))) {
                // if the unit did not participate in battle, move the unit to the intended location
                combatUnitsBlue.get(j).previousLocation = combatUnitsBlue.get(j).location;
                combatUnitsBlue.get(j).location = blueUnitEnd.get(j);
            }
        }
    }

    // Method called to in order to handle unit merges
    public static void mergeUnits(int oneNum, int twoNum) {
        int[] nums = {oneNum, twoNum};
        CombatUnit[] unit = new CombatUnit[2];
        CombatUnit temp; // temporary unit to store new unit
        boolean found = false; // flag for use in the unit search
        int c = -1; // initialize the counter value for use in the unit search

        /// determine the player to be used
        if (Global.curPlayer == 0) {
            currentPlayer = Scenario.redPlayer;
        } else {
            currentPlayer = Scenario.bluePlayer;
        }

        for (int j = 0; j < 2; j++) {
            // search for the unit in order to obtain reference and values
            while (!found || c != currentPlayer.combatUnits.size()) { // while unit not found
                c++; // increase the counter
                if (currentPlayer.combatUnits.get(c).cUnitID == nums[j]) { // for each unit id held by player, check is match to passed unit id
                    found = true;
                }
                if (found == true) { // if the unit has been found
                /*
                     out of bounds error on i
                     */
                    unit[j] = currentPlayer.combatUnits.get(c); // set the reference to the correct unit
                }
            }
        }

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

        temp = new CombatUnit(false, unit[0].cUnitID, sumSize, health, unit[0].location, unit[0].faction);

        temp.faction.removeUnit(unit[0]);
        temp.faction.removeUnit(unit[1]);
        temp.faction.addUnit(temp);

    }

    public static void divideUnit(int unitNum) {
        CombatUnit unit = null;
        boolean found = false; // flag for use in the unit search
        int i = 0; // initialize the counter value for use in the unit search
        CombatUnit one, two;
        
        //int divSize = unit.size / 2;
        boolean idFind = false;
        int checker = 0;
        int id = 0;

        int i2 = 0;
        // determine the player to be used
        if (Global.curPlayer == 0) {
            currentPlayer = Scenario.redPlayer;
            for(CombatUnit army : Scenario.redPlayer.combatUnits) {
                if(army.cUnitID == unitNum)
                    unit = currentPlayer.combatUnits.get(i2);
                i2++;
            }
        } else {
            currentPlayer = Scenario.bluePlayer;
            for(CombatUnit army : Scenario.bluePlayer.combatUnits) {
                if(army.cUnitID == unitNum)
                    unit = currentPlayer.combatUnits.get(i2);
                i2++;
            }
        }
        
        int divSize = unit.size / 2;

        // search for the unit in order to obtain reference and values
        while (!found && i != currentPlayer.combatUnits.size()) { // while unit not found
            
            if (currentPlayer.combatUnits.get(i).cUnitID == unitNum) { // for each unit id held by player, check is match to passed unit id
                found = true;
            }
            if (found == true) { // if the unit has been found
                /*
                 out of bounds error on i
                 */
                unit = currentPlayer.combatUnits.get(i); // set the reference to the correct unit
            }
            i++; // increase the counter
        }

        while (idFind) {
            for (int j = 0; j < unit.faction.combatUnits.size(); j++) {
                if (unit.faction.combatUnits.get(j).cUnitID == id) {
                    checker++;
                }
            }
            if (checker != 0) {
                idFind = true;
            }
            id++;
        }

        one = new CombatUnit(false, unit.cUnitID, divSize, unit.illnessRating, unit.location, unit.faction);
        two = new CombatUnit(false, id, divSize, unit.illnessRating, unit.location, unit.faction);

        one.faction.removeUnit(unit);
        one.faction.addUnit(one);
        one.faction.addUnit(two);
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
        redPlayer = Scenario.redPlayer;
        bluePlayer = Scenario.bluePlayer;
        redCombatUnitPreviousLocation = new ArrayList<Node>();
        redCombatUnitEndLocation = new ArrayList<Node>();
        blueCombatUnitPreviousLocation = new ArrayList<Node>();
        blueCombatUnitEndLocation = new ArrayList<Node>();
    }
}
