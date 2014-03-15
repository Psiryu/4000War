/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;

/**
 *
 * @author Ben
 */
public class MapEvent {

    private static ArrayList<CombatUnit> combatUnitsRed; // list of all units held by the red faction
    private static ArrayList<CombatUnit> combatUnitsBlue; // list of all units held by the blue faction
    private static ArrayList<Road> redUnitRoad; // list of roads occupied by red faction
    private static ArrayList<Road> blueUnitRoad; // list of roads occupied by blue faction
    private static ArrayList<Node> redUnitEnd; // list of node end locations for red
    private static ArrayList<Node> blueUnitEnd; // list of node end locations for blue
    private static ArrayList<Node> redUnitStart; // list of node start locations for red
    private static ArrayList<Node> blueUnitStart; // list of node start locations for blue
    private static ArrayList<CombatUnit> redCombatListCollision;
    private static ArrayList<CombatUnit> blueCombatListCollision;
    private static ArrayList<CombatUnit> redCombatListNode;
    private static ArrayList<CombatUnit> blueCombatListNode;
    private static Player redPlayer, bluePlayer;
    ArrayList<Node> redCombatUnitPreviousLocation;
    ArrayList<Node> redCombatUnitEndLocation;
    ArrayList<Node> blueCombatUnitPreviousLocation;
    ArrayList<Node> blueCombatUnitEndLocation;
    Battle battle;

    /*
     int[i][j] armies
     i {0} -> id
     j {0,1,2,3}
     0 -> id
     1 -> size
     2 -> location
     3 -> is fleet {0,1}
     */
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
        redUnitStart = new ArrayList<Node>();
        blueUnitStart = new ArrayList<Node>();
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
        CombatUnit unit = null;
        //CombatUnit unit = Scenario.listOfUnits.get(unitNum);
        Node endLocation = Scenario.listOfNodes[endLocationNum];

        boolean found = false;
        int i = -1;

        while (!found || i != Scenario.redPlayer.combatUnits.size()) {
            i++;
            if (Scenario.redPlayer.combatUnits.get(i).cUnitID == unitNum) {
                found = true;
            }
        }
        if (found == true) {
            /*
             out of bounds error on i
             */
            unit = Scenario.redPlayer.combatUnits.get(i);
        } else {
            i = -1;
            while (found || i != Scenario.bluePlayer.combatUnits.size()) {
                i++;
                if (Scenario.bluePlayer.combatUnits.get(i).cUnitID == unitNum) {
                    found = true;
                }
            }
            if (found == true) {
                unit = Scenario.bluePlayer.combatUnits.get(i);
            }
        }

        if (combatUnitsRed.contains(unit)) {
            combatUnitsRed.remove(unit);
            redUnitRoad.remove(road);
            redUnitEnd.remove(endLocation);
        } else if (combatUnitsBlue.contains(unit)) {
            combatUnitsBlue.remove(unit);
            blueUnitRoad.remove(road);
            blueUnitEnd.remove(endLocation);
        } else {
            if (unit.faction.playerID == redPlayer.playerID) {
                combatUnitsRed.add(unit);
                redUnitRoad.add(road);
                redUnitEnd.add(endLocation);
            } else {
                combatUnitsBlue.add(unit);
                blueUnitRoad.add(road);
                redUnitEnd.add(endLocation);
            }
        }
    }

    private void cleanList() {
        boolean found = false;

        int[] redIDs = new int[combatUnitsRed.size()];
        for (int i = 0; i < combatUnitsRed.size(); i++) {
            redIDs[i] = combatUnitsRed.get(i).cUnitID;
        }

        int[] blueIDs = new int[combatUnitsBlue.size()];
        for (int i = 0; i < combatUnitsBlue.size(); i++) {
            blueIDs[i] = combatUnitsBlue.get(i).cUnitID;
        }

        for (int i = 0; i < Scenario.redPlayer.combatUnits.size(); i++) {
            for (int j = 0; j < combatUnitsRed.size(); j++) {
                if (Scenario.redPlayer.combatUnits.get(i).cUnitID == redIDs[j]) {
                    found = true;
                }
            }
            if (found == false) {
                combatUnitsRed.add(Scenario.redPlayer.combatUnits.get(i));
                redUnitRoad.add(null);
                redUnitEnd.add(Scenario.redPlayer.combatUnits.get(i).location);
            }
        }
        for (int i = 0; i < Scenario.bluePlayer.combatUnits.size(); i++) {
            for (int j = 0; j < combatUnitsBlue.size(); j++) {
                if (Scenario.bluePlayer.combatUnits.get(i).cUnitID == redIDs[j]) {
                    found = true;
                }
            }
            if (found == false) {
                combatUnitsRed.add(Scenario.bluePlayer.combatUnits.get(i));
                blueUnitRoad.add(null);
                blueUnitEnd.add(Scenario.bluePlayer.combatUnits.get(i).location);
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
    public void processEvents() {
        cleanList();
        for (int i = 0; i < Scenario.listOfRoads.length; i++) {
            for (int j = 0; j < combatUnitsRed.size(); j++) {
                if (redUnitRoad.get(i).roadID == i) {
                    redCombatListCollision.add(combatUnitsRed.get(i));
                    redCombatUnitPreviousLocation.add(combatUnitsRed.get(i).location);
                    redCombatUnitEndLocation.add(redUnitEnd.get(i));
                }
            }
            for (int j = 0; j < combatUnitsBlue.size(); j++) {
                if (blueUnitRoad.get(i).roadID == i) {
                    blueCombatListCollision.add(combatUnitsBlue.get(i));
                    blueCombatUnitPreviousLocation.add(combatUnitsBlue.get(i).location);
                    blueCombatUnitEndLocation.add(blueUnitEnd.get(i));
                }
            }
            if (redCombatListCollision.size() > 0 && blueCombatListCollision.size() > 0) {
                battle.doBattleOnRoad(redCombatListCollision, redCombatUnitPreviousLocation, redCombatUnitEndLocation,
                        blueCombatListCollision, blueCombatUnitPreviousLocation, blueCombatUnitEndLocation);
            }
            redCombatListCollision.clear();
            redCombatUnitPreviousLocation.clear();
            redCombatUnitEndLocation.clear();
            blueCombatListCollision.clear();
            blueCombatUnitPreviousLocation.clear();
            blueCombatUnitEndLocation.clear();
        }
        for (int i = 0; i < Scenario.listOfNodes.length; i++) {
            for (int j = 0; j < redUnitEnd.size(); j++) {
                if (redUnitEnd.get(i).id == i) {
                    redCombatListNode.add(combatUnitsRed.get(i));
                    redCombatUnitPreviousLocation.add(combatUnitsRed.get(i).location);
                    redCombatUnitEndLocation.add(redUnitEnd.get(i));
                }
            }
            for (int j = 0; j < blueUnitEnd.size(); j++) {
                if (blueUnitEnd.get(i).id == i) {
                    blueCombatListNode.add(combatUnitsBlue.get(i));
                    blueCombatUnitPreviousLocation.add(combatUnitsBlue.get(i).location);
                    blueCombatUnitEndLocation.add(blueUnitEnd.get(i));
                }
            }
            if (redCombatListNode.size() > 0 && blueCombatListNode.size() > 0) {
                /*
                 There was a  battle triggered on this node
                 the two arrays redCombatList and blueCombatList contain those involved
                 */

                /*
                 need: previousLocation(ish), where their going, units involved
                 */
                /*
                 public void PVPdoCampBattleOnNode (Node node,CombatUnit[] red, CombatUnit[] blue)
                 */
                battle.PVPdoCampBattleOnNode(redCombatListNode, redCombatUnitPreviousLocation, redCombatUnitEndLocation,
                        blueCombatListNode, blueCombatUnitPreviousLocation, blueCombatUnitEndLocation);
            }
            redCombatListCollision.clear();
            redCombatUnitPreviousLocation.clear();
            redCombatUnitEndLocation.clear();
            blueCombatListCollision.clear();
            blueCombatUnitPreviousLocation.clear();
            blueCombatUnitEndLocation.clear();
        }
        for (int j = 0; j < combatUnitsRed.size(); j++) {
            if (!redCombatListCollision.contains(combatUnitsRed.get(j)) && !redCombatListNode.contains(combatUnitsRed.get(j))) {
                combatUnitsRed.get(j).previousLocation = combatUnitsRed.get(j).location;
                combatUnitsRed.get(j).location = redUnitEnd.get(j);
            }
        }
        for (int j = 0; j < combatUnitsBlue.size(); j++) {
            if (!blueCombatListCollision.contains(combatUnitsBlue.get(j)) && !blueCombatListNode.contains(combatUnitsBlue.get(j))) {
                combatUnitsBlue.get(j).previousLocation = combatUnitsBlue.get(j).location;
                combatUnitsBlue.get(j).location = blueUnitEnd.get(j);
            }
        }
    }

    // Method called to in order to handle unit merges
    public void mergeUnits(CombatUnit one, CombatUnit two) {
        CombatUnit temp;
        int[] scaled = {0, 0, 0};
        int sumSize = one.size + two.size;
        int[] sizes = {one.size, two.size, sumSize};
        int health = (one.illnessRating + two.illnessRating) / 2;

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

        temp = new CombatUnit(false, one.cUnitID, sumSize, health, one.location, one.faction);

        temp.faction.removeUnit(one);
        temp.faction.removeUnit(two);
        temp.faction.addUnit(temp);

    }

    public void divideUnits(CombatUnit unit) {
        CombatUnit one, two;
        int divSize = unit.size / 2;
        boolean idFind = false;
        int checker = 0;
        int id = 0;

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
    public void clearRegistry() {
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
    }
}
