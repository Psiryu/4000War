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

    private ArrayList<CombatUnit> combatUnitsRed; // list of all units held by the red faction
    private ArrayList<CombatUnit> combatUnitsBlue; // list of all units held by the blue faction
    private ArrayList<Road> redUnitRoad; // list of roads occupied by red faction
    private ArrayList<Road> blueUnitRoad; // list of roads occupied by blue faction
    private ArrayList<Node> redUnitEnd; // list of node end locations for red
    private ArrayList<Node> blueUnitEnd; // list of node end locations for blue
    private ArrayList<Node> redUnitStart; // list of node start locations for red
    private ArrayList<Node> blueUnitStart; // list of node start locations for blue
    private ArrayList<CombatUnit> redCombatListCollision;
    private ArrayList<CombatUnit> blueCombatListCollision;
    private ArrayList<CombatUnit> redCombatListNode;
    private ArrayList<CombatUnit> blueCombatListNode;
    private Player redPlayer, bluePlayer;

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
    }

    // Method called to to add a movement to the registry
    public void addMovement(int unitNum, Road road, int endLocationNum) {
        CombatUnit unit = Scenario.listOfUnits[unitNum];
        Node endLocation = Scenario.listOfNodes[endLocationNum];

        if (combatUnitsRed.contains(unit)){
            combatUnitsRed.remove(unit);
            redUnitRoad.remove(road);
            redUnitEnd.remove(endLocation);
        }
        else if (combatUnitsBlue.contains(unit)){
            combatUnitsBlue.remove(unit);
            blueUnitRoad.remove(road);
            blueUnitEnd.remove(endLocation);
        }
        else{
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
    
    public void removeMovement(/*information needed for item identification*/){
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
        
        /*
        take into consideration non-interacted units
        */
        for (int i = 0; i < Scenario.listOfRoads.length; i++) {
            for (int j = 0; j < combatUnitsRed.size(); j++) {
                if (redUnitRoad.get(i).roadID == i) {
                    redCombatListCollision.add(combatUnitsRed.get(i));
                }
            }
            for (int j = 0; j < combatUnitsBlue.size(); j++) {
                if (blueUnitRoad.get(i).roadID == i) {
                    blueCombatListCollision.add(combatUnitsBlue.get(i));
                }
            }
            if (redCombatListCollision.size() > 0 && blueCombatListCollision.size() > 0) {
                /*
                 There was a collision battle triggered on this road
                 the two arrays redCombatList and blueCombatList contain those involved
                 */
            }
        }
        for (int i = 0; i < Scenario.listOfNodes.length; i++) {
            for (int j = 0; j < redUnitEnd.size(); j++) {
                if (redUnitEnd.get(i).id == i) {
                    redCombatListNode.add(combatUnitsRed.get(i));
                }
            }
            for (int j = 0; j < blueUnitEnd.size(); j++) {
                if (blueUnitEnd.get(i).id == i) {
                    blueCombatListNode.add(combatUnitsBlue.get(i));
                }
            }
            if (redCombatListNode.size() > 0 && blueCombatListNode.size() > 0) {
                /*
                 There was a  battle triggered on this node
                 the two arrays redCombatList and blueCombatList contain those involved
                 */
            }
        }
        for (int j = 0; j < combatUnitsRed.size(); j++) {
            if (!redCombatListCollision.contains(combatUnitsRed.get(j)) && !redCombatListNode.contains(combatUnitsRed.get(j))) {
                moveUnit(combatUnitsRed.get(j), redUnitEnd.get(j));
            }
        }
        for (int j = 0; j < combatUnitsBlue.size(); j++) {
            if (!blueCombatListCollision.contains(combatUnitsBlue.get(j)) && !blueCombatListNode.contains(combatUnitsBlue.get(j))) {
                moveUnit(combatUnitsBlue.get(j), blueUnitEnd.get(j));
            }
        }
    }

    private void moveUnit(CombatUnit unit, Node endLocation) {
        unit.previousLocation = unit.location;
        unit.location = endLocation;
    }

    // Method called to in order to handle unit merges
    public void mergeUnits(CombatUnit one, CombatUnit two) {
        int[] scaled = {0, 0, 0};
        int sumSize = one.size + two.size;
        int[] sizes = {one.size, two.size, sumSize};

        for (int i = 0; i < 3; i++) {
            if (sizes[i] < 6) {
                scaled[i] = 0;
            } else if (sizes[i] < 11) {
                scaled[i] = 1;
            } else {
                scaled[i] = 2;
            }
        }

        /*
         scale the backend army size to account for variance in unit size ranges
         */
    }

    public void divideUnits(CombatUnit unit) {

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
