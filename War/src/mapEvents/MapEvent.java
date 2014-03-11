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

    private ArrayList<Integer> combatUnitsRed; // list of all units held by the red faction
    private ArrayList<Integer> combatUnitsBlue; // list of all units held by the blue faction
    private ArrayList<Integer> redUnitRoad; // list of roads occupied by red faction
    private ArrayList<Integer> blueUnitRoad; // list of roads occupied by blue faction

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
        combatUnitsRed = new ArrayList<Integer>();
        combatUnitsBlue = new ArrayList<Integer>();
        redUnitRoad = new ArrayList<Integer>();
        blueUnitRoad = new ArrayList<Integer>();
    }

    // Method called to to add a movement to the registry
    public void addMovement(int unitID, int roadID) {

    }

    // Method called to in order to simulate simutanious movement
    public void processEvents() {

    }

    // Method called to in order to determine collisions
    public void checkCollisions() {

    }

    // Method called to in order to handle unit merges
    public void mergeUnits() {

    }

    // Method to reset the arrays at turn's end
    public void clearRegistry() {

    }
}
