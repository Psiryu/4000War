
import java.util.Random;
import javax.swing.JOptionPane;

/**
 * Game Class
 *
 * Description: This class stores information regarding the game state and
 * variables that effect the overall behavior of the map. A multitude of set and
 * get style methods allow for the game to be configured and monitored.
 *
 * Usage: Game object is declared in Scenario and is called to from Map when a
 * player's turn passes.
 *
 * Maintenance notes: Modifications pertaining to weather, season, and turn
 * count can be modified in the appropriate methods. End game conditions can be
 * modified in IsGameEnd. The method endTurn performs modifications to player
 * and unit statistics and must be considered when modifying either of those
 * entities.
 */
public class Game {

	public static double turnCount = 0; // total number of turns in the game, increases in 0.5 intervals
	public static int maxTurnCount; // value at which the game will be terminated

	/*
	 Method: Game
	 Input Parameters: none
	 Output Parameters: none
    
	 This is the constructor for the Game object, in which the object is initialized
	 and a max turn count is established.
	 */
	public Game() {
		maxTurnCount = 10;
	}

	/*
	 Method: setMaxTurnCount
	 Input Parameters: int number -> the value to set the max turn count to
	 Output Parameters: none
    
	 Set the maximum turn count value.
	 */
	void setMaxTurnCount(int number) {
		maxTurnCount = number;
	}

	/*
	 Method: getMaxturnCount
	 Input Parameters: none
	 Output Parameters: int maxTurnCount -> the cap on the turn count to trigger end game
    
	 Get the value stored in maxTurnCount.
	 */
	int getMaxTurnCount() {
		return maxTurnCount;
	}

	/*
	 Method: getWeather
	 InputParameters: none
	 Output Parameters: int weather -> the current weather conditions on the map
    
	 Return the current value for weather.
	 */
	int getWeather() {
		return Global.weather;
	}

	/*
	 Method: updateWeather
	 Input Parameters: none
	 Output Parameters: none
    
	 Calculate the new value for weather at the end of each turn.
	 */
	void updateWeather() {
		Random random = new Random(); // establish random number generator
		Global.weather = (int) (5 * random.nextDouble()); // set value to be random number between 0 and 5
	}

	/*
	 Method: getTurnCount
	 Input Parameters: none
	 Output Parameters: double turnCount -> the current turn count
    
	 Returns the current turn count.
	 */
	double getTurnCount() {
		return turnCount;
	}

	/*
	 Method: endTurn
	 Input Parameters: none
	 Output Parameters: none
    
	 This method handles the conclusion of each turn. It will perform the processes
	 necessary to initialize the simulation of movement and adjust player, game,
	 and unit properties.
	 */
	void endTurn() {
		turnCount += 0.5; // increase the turn count
		if (turnCount % 1 == 0) { // if the turn count indicates that both players had their turn

			MapEvent.omnipresentSimulation(); // simulate movements and perform combat

			// update combat unit statistics
			for (int i = 0; i < Scenario.redPlayer.combatUnits.size(); i++) {
				// determin the new time spent stationary
				Scenario.redPlayer.combatUnits.get(i).setTimeStationary();
				// re-evaluate the illness rating
				Scenario.redPlayer.combatUnits.get(i).setIllnessRating();
				// determine if a size decrease will occur
				Scenario.redPlayer.combatUnits.get(i).setSize();
				// decriment the supply level of the unit
				Scenario.redPlayer.combatUnits.get(i).removeSupplies();
				// set the total distance from capital
				Scenario.redPlayer.combatUnits.get(i).setDistanceFromCapital();
			}

			// repeat the calculations for the blue faction
			for (int i = 0; i < Scenario.bluePlayer.combatUnits.size(); i++) {
				Scenario.bluePlayer.combatUnits.get(i).setTimeStationary();
				Scenario.bluePlayer.combatUnits.get(i).setIllnessRating();
				Scenario.bluePlayer.combatUnits.get(i).setSize();
				Scenario.bluePlayer.combatUnits.get(i).removeSupplies();
				Scenario.bluePlayer.combatUnits.get(i).setDistanceFromCapital();
			}

			// update the political power levels of each faction
			Scenario.redPlayer.AdjustPoliticalPower();
			Scenario.bluePlayer.AdjustPoliticalPower();

			// recalculate the rumour/enemy intelligence information
			Scenario.redPlayer.generateRumourList();
			Scenario.bluePlayer.generateRumourList();

			updateWeather(); // change the weather conditions
			Global.season = (Global.season + 1) % 4; // change the season

			// adjust the supply levels at each node based on occupancy
			for (Node location : Scenario.listOfNodes) { // for each node in scenario
				Boolean occupyCheck = true; // check if the node has been occupied
				for (CombatUnit unit : Scenario.redPlayer.combatUnits) { // for all red units
					if (unit.location.id == location.id) { // if a red unit is here
						unit.addSupplies(); // take supplies
						location.removeSupplies(); // set node supply level to 0
						occupyCheck = false; // indicate that the node is occupied
					}
				}
				// repeat for blue player units
				for (CombatUnit unit : Scenario.bluePlayer.combatUnits) {
					if (unit.location.id == location.id) {
						unit.addSupplies();
						location.removeSupplies();
						occupyCheck = false;
					}
				}

				if (occupyCheck) { // if the node is not occupied
					location.addSupplies(); // increase the supplies
				}
			}
		}

	}

	/*
	 Method: IsGameEnd
	 Input Parameters: none
	 Output Parameters: none
    
	 This method checks for all of the end game conditions. If one of the conditions
	 has been met, then the game will terminate and the winner determined.
	 */
	public static void IsGameEnd() {
		// storage of the total size of both faction's roster
		int aggregateRedSize = 0;
		int aggregateBlueSize = 0;
		String blue = Scenario.bluePlayer.playerName;
		String red = Scenario.redPlayer.playerName;

		// sum the sizes of all the units in each player's roster
		for (int i = 0; i < Scenario.redPlayer.combatUnits.size(); i++) {
			aggregateRedSize += Scenario.redPlayer.combatUnits.get(i).size;
		}
		for (int i = 0; i < Scenario.bluePlayer.combatUnits.size(); i++) {
			aggregateBlueSize += Scenario.bluePlayer.combatUnits.get(i).size;
		}

		// case 1: turn count or game timer meets limit
		if (turnCount >= maxTurnCount || Global.timer == 0) 
                {
            // if red player's political power is greater than blue players
			// indicate that red player is the winner
			if (Scenario.redPlayer.politicalPower > Scenario.bluePlayer.politicalPower) {
				Global.gameSummary = "Red Player Wins!";
				Global.gameSummary2 = " There are no more turns remaining"
					+ " or time ran out... Red player"
					+ " wins because their political"
					+ " power is greater than Blue's.";

				Global.intGameOver = 1;
				return;
			} else if (Scenario.redPlayer.politicalPower < Scenario.bluePlayer.politicalPower) {
                // if blue player's political power is greater than red players
				// indicate that blue player is the winner
				Global.gameSummary = "Blue Player Wins!";
				Global.gameSummary2 = " There are no more turns remaining"
					+ " or time ran out... Blue player"
					+ " wins because their political"
					+ " power is greater than Red's.";
				Global.intGameOver = 1;
				return;

			} else /* equal political power levels*/ {
				// if red has the larger remaining army, then red wins
				if (aggregateRedSize > aggregateBlueSize) {
					Global.gameSummary = "Red Player Wins!";
					Global.gameSummary2 = "There are no more turns remaining "
						+ "or time ran out... Red player "
						+ "wins because their political "
						+ "power is equal than Blue's "
						+ "but Red has a larger army.";

					Global.intGameOver = 1;
					return;
				} else if (aggregateRedSize < aggregateBlueSize) {
					// if blue has the larger remaining army, then blue wins
					Global.gameSummary = "Blue Player Wins!";
					Global.gameSummary2 = "There are no more turns remaining "
						+ "or time ran out... Blue player "
						+ "wins because their political "
						+ "power is equal than Red's "
						+ "but Blue has a larger army.";

					Global.intGameOver = 1;
					return;

				} else {
					// if they have equal army sizes, declare a tie
					Global.gameSummary = "Tie Game!";
					Global.gameSummary2 = "There are no more turns remaining "
						+ "or time ran out... Both "
						+ "players have equal army size "
						+ "and political power.";
					Global.intGameOver = 1;
					return;
				}
			}
		} 
                else if (Scenario.redPlayer.combatUnits.size() == 0)
                {
                    if (Scenario.bluePlayer.combatUnits.size() == 0)
                    {
                        Global.gameSummary = "Tie Game!";
                        Global.gameSummary2 = "Both players have lost all of their units";
                    }
                    else
                    {
                        /*blue wins*/
                        Global.gameSummary = "Blue Player Wins!";
                        Global.gameSummary2 = "Red team has no more units left";
                    }
                }
                else if(Scenario.bluePlayer.combatUnits.size() == 0)
                {
                    /*red wins*/
                    Global.gameSummary = "Red Player Wins!";
                    Global.gameSummary2 = "Blue team has no more units left";
                }
                else if (Scenario.redPlayer.combatUnits.size() == 1)
                {
                    if (Scenario.redPlayer.combatUnits.get(0).isFleet)
                    {
                        if (Scenario.bluePlayer.combatUnits.size() == 1)
                        {
                            if(Scenario.bluePlayer.combatUnits.get(0).isFleet)
                            {
                                Global.gameSummary = "Tie Game!";
                                Global.gameSummary2 = "Both players have only naval units left";
                            }
                        }
                        else
                        {
                            /*blue wins*/
                            Global.gameSummary = "Blue Player Wins!";
                            Global.gameSummary2 = "Red team has no more non-naval units left";
                            
                        }
                    }
                }
                else if (Scenario.bluePlayer.combatUnits.size() == 1)
                        {
                            if(Scenario.bluePlayer.combatUnits.get(0).isFleet)
                            {
                                /*red wins*/
                                Global.gameSummary = "Red Player Wins!";
                                Global.gameSummary2 = "Blue team has no more non-naval units left";
                            }
                        }
                
                
                
                else 
                {
			return;
		}
	}
}
