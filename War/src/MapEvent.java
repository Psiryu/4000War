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
    private ArrayList<CombatUnit> redCombatList;
    private ArrayList<CombatUnit> blueCombatList;

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
        redCombatList = new ArrayList<CombatUnit>();
        blueCombatList = new ArrayList<CombatUnit>();
    }

    // Method called to to add a movement to the registry
    public void addMovement(CombatUnit unit, Road road, Node endLocation) {
        if (unit.faction.playerID == 0) {
            combatUnitsRed.add(unit);
            redUnitRoad.add(road);
            redUnitEnd.add(endLocation);
        } else {
            combatUnitsBlue.add(unit);
            blueUnitRoad.add(road);
            redUnitEnd.add(endLocation);
        }
    }

    // Method called to in order to simulate simutanious movement
    public void processEvents() {
        for (int i = 0; i < Scenario.listOfRoads.length; i++) {
            for (int j = 0; j < redCombatList.size(); j++) {
                if (redUnitRoad.get(i).roadID == i) {
                    redCombatList.add(combatUnitsRed.get(i));
                }
            }
            for (int j = 0; j < blueCombatList.size(); j++) {
                if (blueUnitRoad.get(i).roadID == i) {
                    blueCombatList.add(combatUnitsBlue.get(i));
                }
            }
            if (redCombatList.size() > 0 && blueCombatList.size() > 0) {
                /*
                There was a collision battle triggered on this road
                the two arrays redCombatList and blueCombatList contain those involved
                */
            }
        }
        for (int i = 0; i < Scenario.listOfNodes.length; i++) {
            for (int j = 0; j < redUnitEnd.size(); j++) {
                if (redUnitEnd.get(i).id == i) {
                    redCombatList.add(combatUnitsRed.get(i));
                }
            }
            for (int j = 0; j < blueUnitEnd.size(); j++) {
                if (blueUnitEnd.get(i).id == i) {
                    blueCombatList.add(combatUnitsBlue.get(i));
                }
            }
            if (redCombatList.size() > 0 && blueCombatList.size() > 0) {
                /*
                There was a  battle triggered on this node
                the two arrays redCombatList and blueCombatList contain those involved
                */
            }
        }
    }

    // Method called to in order to handle unit merges
    public void mergeUnits() {
        /*
        scale the backend army size to account for variance in unit size ranges
        */
    }

    // Method to reset the arrays at turn's end
    public void clearRegistry() {
        combatUnitsRed = new ArrayList<CombatUnit>();
        combatUnitsBlue = new ArrayList<CombatUnit>();
        redUnitRoad = new ArrayList<Road>();
        blueUnitRoad = new ArrayList<Road>();
        redUnitEnd = new ArrayList<Node>();
        blueUnitEnd = new ArrayList<Node>();
        redCombatList = new ArrayList<CombatUnit>();
        blueCombatList = new ArrayList<CombatUnit>();
    }
}
