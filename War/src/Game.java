
import java.util.Random;

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

    public static int turnCount;

    public Game()
    {
        turnCount = 0;
    }
    
    int getWeather() {
        return Global.weather;
    }

    void updateWeather() {
        Random random = new Random();
        Global.weather = (int) (5 * random.nextDouble());
    }

    int getTurnCount() {
        return turnCount;
    }

    void checkEnemyCollision() {
        CombatUnit[] armyArrayRed = null;
        CombatUnit[] armyArrayBlue = null;
              
        for (int i = 0; i < armyArrayRed.length; i++) {
            for (int j = 0; j < armyArrayBlue.length; j++) {
                if (i != j) {
                    /*
                    if (armyArrayRed[i].road == armyArrayBlue[j].road) {
                        
                         insert logic for handling
                         
                    }
                    */
                }
            }
        }
    }
    
    void endTurn(){
        turnCount++;
        Scenario.redPlayer.AdjustPoliticalPower();
        Scenario.bluePlayer.AdjustPoliticalPower();
        for (int i = 0; i < Scenario.redPlayer.combatUnits.size(); i++)
        {
            Scenario.redPlayer.combatUnits.get(i).setHealthRating();
            Scenario.redPlayer.combatUnits.get(i).setSize();
        }
        for (int i = 0; i < Scenario.bluePlayer.combatUnits.size(); i++)
        {
            Scenario.bluePlayer.combatUnits.get(i).setHealthRating();
            Scenario.bluePlayer.combatUnits.get(i).setSize();
        }
        updateWeather();
        Global.season = (Global.season+1)%4;
    }
}
