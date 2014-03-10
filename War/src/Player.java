/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;

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
    protected int aggregateDistanceFromCapital; // distance from capital of all units
    protected int totalInitialArmy; // inital size of all units, sum
    protected int totalCurrentArmy; // current size of all units, sum

    // Constuctor of the player object
    public Player(boolean _isComp, Node _capital, int id, String _playerName) {
        // obtain the information for the player parameters
        playerID = id;
        politicalPower = 100;
        isComp = _isComp;
        capital = _capital;
        playerName = _playerName;
    }

    // Method to get political power level
    public int getPolitcalPower() {
        return politicalPower;
    }

    // Method to set the political power level
    public void setPoliticalPower(int x) {
        politicalPowerPreviousState = politicalPower; // store the previous state
        politicalPower = x; // update the current level

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
        int calculatedPoliticalPower = 0; // temporary value to store the calculated current state

        // store the current total distance and cap at 20
        int totalDistance = aggregateDistanceFromCapital;
        if (totalDistance > 20) {
            totalDistance = 20;
        }

        // store the current number of game turns and cap at 30
        int numTurns = turnCount;
        if (numTurns > 30) {
            numTurns = 30;
        }

        // calculate the current level of political power
        calculatedPoliticalPower = 100 - totalDistance - (int) (50 - ((double) totalCurrentArmy / (double) totalInitialArmy) * 50) - numTurns;
        setPoliticalPower(calculatedPoliticalPower);
    }

    // Method to calculate the total dispersion of a faction's units
    public void setAggDistance() {
        int calculatedDistance = 0; // temporary storage of caculation

        // obtain the distance from capital for each unit based on the location value
        for (int i = 0; i < combatUnits.size(); i++) {
            if (playerName.equals("red")) {
                calculatedDistance += combatUnits.get(i).location.distanceFromCapitalRed;
            } else {
                calculatedDistance += combatUnits.get(i).location.distanceFromCapitalBlue;
            }
        }

        // set the total distance based on calculation
        aggregateDistanceFromCapital = calculatedDistance;
    }

    // Method to add a unit to a faction
    public void addUnit(CombatUnit addition) {
        combatUnits.add(addition);
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
