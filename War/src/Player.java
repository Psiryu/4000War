/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Fearless Jay
 */
public class Player extends Game {

    protected Node capital; // the faction capital
    protected ArrayList<CombatUnit> combatUnits = new ArrayList<CombatUnit>(); // list of all units held by the faction
    protected int politicalPower; // faction political power level
    protected int politicalPowerPreviousState; // storage of previous political power level for difference calculation
    protected int politicalPowerDecrease; // value resulting from difference calculation
    protected boolean isComp; // determine if this player is a computer
    protected int playerID; // player ID
    protected String playerName; // name of the player as per the scenario and chosen faction
    protected int totalInitialArmy; // inital size of all units, sum
    protected int totalCurrentArmy; // current size of all units, sum
    protected int totalDistanceMax;
    protected Rumour rumours;
    protected ArrayList<ArrayList<Integer>> enemyIntelligence;

    // Constuctor of the player object
    public Player(boolean _isComp, int id, String _playerName) {
        // obtain the information for the player parameters
        playerID = id;
        politicalPower = 100;
        isComp = _isComp;
        playerName = _playerName;
    }

    public void setUpRumours() {
        rumours = new Rumour(playerID);
    }

    public void generateRumourList() {
        rumours.updateUnitRegistry();
        enemyIntelligence = rumours.playerRumourSummary();
    }

    // Method to get political power level
    public int getPolitcalPower() {
        return politicalPower;
    }

    // Method to set the political power level
    private void setPoliticalPower(double x) {
        politicalPowerPreviousState = politicalPower; // store the previous state
        politicalPower = (int) x; // update the current level

        if (politicalPower < politicalPowerPreviousState) // if a difference between previous and current states is found
        {
            politicalPowerDecrease = politicalPower - politicalPowerPreviousState; // register the decrease
        }
    }

    // Method to get the political power decrease
    public int getPoliticalPowerDecrease() {
        return politicalPowerDecrease;
    }

    // Method to get if the player is a computer
    public boolean getIsComp() {
        return isComp;
    }

    // Method to calculate the current political power level
    public void AdjustPoliticalPower() {
        double calculatedPoliticalPower = 0; // temporary value to store the calculated current state

        totalDistanceMax = Scenario.findMaxDistance(playerID);

        setCurrentArmyLevel();

        // store the current total distance and cap at 20
        int totalDistance = getAggDistance();

        // store the current number of game turns and cap at 30
        // calculate the current level of political power
        calculatedPoliticalPower = 100 - (20 - ((totalDistance / ((double)totalDistanceMax * (double)combatUnits.size())) * 20))
                - (50 - ((double)totalCurrentArmy / (double)totalInitialArmy) * 50)
                - ((Game.turnCount / Game.maxTurnCount) * 30);
        setPoliticalPower(calculatedPoliticalPower);
        
        
        //JOptionPane.showMessageDialog(null, "Total distance: " + totalDistance + "Current Army: " + totalCurrentArmy / totalInitialArmy + "Turn Count: " + Game.turnCount);
    }

    // Method to calculate the total dispersion of a faction's units
    private int getAggDistance() {
        int calculatedDistance = 0; // temporary storage of caculation

        // obtain the distance from capital for each unit based on the location value
        for (int i = 0; i < combatUnits.size(); i++) {
            if (playerID == 0) {
                calculatedDistance += combatUnits.get(i).location.distanceFromCapitalRed;
            } else {
                calculatedDistance += combatUnits.get(i).location.distanceFromCapitalBlue;
            }
        }

        //JOptionPane.showMessageDialog(null, "Agg Distance "+calculatedDistance);

        // set the total distance based on calculation
        return calculatedDistance;
    }

    // Method to set the initial army size
    public void setInitialArmyLevel() {
        int sumSize = 0; // temporary storage of the sum of all unit sizes

        // obtain the size of each of the faction's units
        for (int i = 0; i < combatUnits.size(); i++) {
            sumSize += combatUnits.get(i).size;
        }

        // set the total army size based on calculation
        totalInitialArmy = sumSize;
        totalCurrentArmy = sumSize;
    }

    // Method to set the ongoing army size
    public void setCurrentArmyLevel() {
        int sumSize = 0; // temporary storage of the sum of all unit sizes

        // obtain the size of each of the faction's units
        for (int i = 0; i < combatUnits.size(); i++) {
            sumSize += combatUnits.get(i).size;
        }

        // set the current army size based on calculation
        totalCurrentArmy = sumSize;
    }

}
