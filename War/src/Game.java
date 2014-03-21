
import java.util.Random;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Fearless Jay
 */
public class Game {

    public static double turnCount; // total number of turns in the game
    public static int maxTurnCount;

    // initialize the game with turn count 0
    public Game() {
        turnCount = 0;
    }
    
    void setMaxTurnCount(int number){
        maxTurnCount = number;
    }
    
    int getMaxTurnCount(){
        return maxTurnCount;
    }

    // get the current weather conditions
    int getWeather() {
        return Global.weather;
    }

    // set new randomized weather conditions
    void updateWeather() {
        Random random = new Random();
        Global.weather = (int) (5 * random.nextDouble());
    }

    // obtain the current turn count
    double getTurnCount() {
        return turnCount;
    }

    // Method to determine if an two opposing units collided while in transit
    void checkEnemyCollision() {
        // temporary storage of units in play
        CombatUnit[] armyArrayRed = null;
        CombatUnit[] armyArrayBlue = null;

        // for each unit in red faction
        for (int i = 0; i < armyArrayRed.length; i++) {
            // for each unit in blue faction
            for (int j = 0; j < armyArrayBlue.length; j++) {
                if (i != j) { // check if the road ids match, indicating collision
                    /*
                     if (armyArrayRed[i].road == armyArrayBlue[j].road) {
                        
                     insert logic for handling
                         
                     }
                     */
                }
            }
        }
    }

    // Method to handle the end turn calculations
    void endTurn() {
        turnCount += 0.5; // increase the turn count
        if (turnCount % 1 == 0) {
            MapEvent.processEvents();

            // update the political power levels of each faction
            Scenario.redPlayer.AdjustPoliticalPower();
            Scenario.bluePlayer.AdjustPoliticalPower();

            // update combat unit statistics
            for (int i = 0; i < Scenario.redPlayer.combatUnits.size(); i++) {
                // determin the new time spent stationary
                Scenario.redPlayer.combatUnits.get(i).setTimeStationary();
                // re-evaluate the illness rating
                Scenario.redPlayer.combatUnits.get(i).setHealthRating();
                // determine if a size decrease will occur
                Scenario.redPlayer.combatUnits.get(i).setSize();
                // decriment the supply level of the unit
                Scenario.redPlayer.combatUnits.get(i).removeSupplies();
            }
            // repeat the calculations for the blue faction
            for (int i = 0; i < Scenario.bluePlayer.combatUnits.size(); i++) {
                Scenario.bluePlayer.combatUnits.get(i).setTimeStationary();
                Scenario.bluePlayer.combatUnits.get(i).setHealthRating();
                Scenario.bluePlayer.combatUnits.get(i).setSize();
                Scenario.bluePlayer.combatUnits.get(i).removeSupplies();
            }
            updateWeather(); // change the weather conditions
            Global.season = (Global.season + 1) % 4; // change the season
            MapEvent.clearRegistry();

            for (Node listOfNode : Scenario.listOfNodes) {
                listOfNode.timeUnoccupied++;
                if (listOfNode.timeUnoccupied > 3) {
                    listOfNode.timeUnoccupied = 3;
                }
            }

            for (Node listOfNode : Scenario.listOfNodes) {
                for (int j = 0; j < Scenario.redPlayer.combatUnits.size(); j++) {
                    if (Scenario.redPlayer.combatUnits.get(j).location.id == listOfNode.id) {
                        listOfNode.timeUnoccupied = 0;
                    }
                }
                for (int j = 0; j < Scenario.bluePlayer.combatUnits.size(); j++) {
                    if (Scenario.bluePlayer.combatUnits.get(j).location.id == listOfNode.id) {
                        listOfNode.timeUnoccupied = 0;
                    }
                }
            }
        }

    }
}
