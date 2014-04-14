
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Player Class
 *
 * Description: This class represents the workings of the Player object. The
 * Player object represents the user and the faction they are playing as in the
 * game. As such, variables pertaining to the Player allow for tracking of unit
 * roster, capital, political power level, and enemy intelligence.
 *
 * Usage: Player objects are constructed in Scenario.setPlayers(). Subsequent
 * use of the object is used throughout the project. Major changes in this class
 * will require a project-wide review.
 *
 * Maintenance notes: Additional parameters can be added to the parameter list
 * and the constructor, so long as the construction statements in
 * Scenario.setPlayers() are also modified.
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
	protected int totalDistanceMax; // maximum possible distance a unit can have from capital
	protected Rumour rumours; // Rumour object to record enemy intelligence operations
	protected ArrayList<ArrayList<Integer>> enemyIntelligence; // storage of enemey intelligence

	/*
	 Method: Player
	 Input Parameters: boolean _isComp -> true if the player is AI
	 int id -> unique identifier for the player
	 String _playerName -> the word representation of the player
	 Output Parameters: none

	 This is the constructor for the player object. Object parameters are established
	 based on the input parameters.
	 */
	public Player(boolean _isComp, int id, String _playerName) {
		// obtain the information for the player parameters
		playerID = id;
		politicalPower = 100;
		isComp = _isComp;
		playerName = _playerName;
	}

	/*
	 Method: setUpRumours
	 Input Parameters: none
	 Output Parameters: none

	 Method called to at the start of a game. This initializes the rumour object.
	 */
	public void setUpRumours() {
		rumours = new Rumour(playerID);
	}

	/*
	 Method: generateRumourList
	 Input Parameters: none
	 Output Parameters: none

	 This method first establishes the Rumour object's awareness of the units
	 currently in play then obtains the enemy intelligence list.
	 */
	public void generateRumourList() {
		String report = "";
		rumours.updateUnitRegistry(); // update unit awareness
		enemyIntelligence = rumours.playerRumourSummary(); // obtain the list of rumoured units
//		for (ArrayList<Integer> nodal : enemyIntelligence) {
//			if (nodal.size() > 1) {
//				report += playerName+" ";
//				for (Integer value : nodal) {
//					if (nodal.indexOf(value) == 0) {
//						report += Scenario.listOfNodes[value].name;
//					} else {
//						report += " With " + value;
//					}
//				}
//				JOptionPane.showMessageDialog(null, report);
//				report = "";
//			}
//		}
	}

	/*
	 Method: setPoliticalPower
	 Input Parameters: double x -> the value that political power is to be set to
	 Output Parameters: none

	 This method captures the new political power level, registers the current
	 state, then obtains the statistics pertaining to a political power decrease.
	 */
	private void setPoliticalPower(double x) {
		politicalPowerPreviousState = politicalPower; // store the previous state
		politicalPower = (int) x; // update the current level

		if (politicalPower < politicalPowerPreviousState) // if a difference between previous and current states is found
		{
			politicalPowerDecrease = politicalPower - politicalPowerPreviousState; // register the decrease
		}
	}

	/*
	 Method: getPoliticalPowerDecrease
	 Input Parameters: none
	 Output Parameters: int politicalPowerDecrease -> the decrease in political power

	 Method to return the last registered political power decrease.
	 */
	public int getPoliticalPowerDecrease() {
		return politicalPowerDecrease;
	}

	/*
	 Method: AdjustPoliticalPower
	 Input Parameters: none 
	 Output Parameters: none

	 This method captures the state of a faction at the end of a turn and adjusts
	 the political power level correctly.
	 */
	public void AdjustPoliticalPower() {
		double calculatedPoliticalPower = 0; // temporary value to store the calculated current state

		totalDistanceMax = Scenario.findMaxDistance(playerID); // obtain the maximum possible distance a unit can be from capital

		setCurrentArmyLevel(); // calibrate the statistics pertaining to current unit roster level

		// store the current total distance
		int totalDistance = getAggDistance();

		// calculate the current level of political power
		calculatedPoliticalPower = 100
			- (20 - ((totalDistance / ((double) totalDistanceMax * (double) combatUnits.size())) * 20))
			- (50 - ((double) totalCurrentArmy / (double) totalInitialArmy) * 50)
			- ((Game.turnCount / Game.maxTurnCount) * 30);
		setPoliticalPower(calculatedPoliticalPower);
	}

	/*
	 Method: getAggDistance
	 Input Parameters: none
	 Output Parameters: int calculatedDistance -> the sum of capital distances for all players

	 This method provided the AdjustPoliticalPower method with information about
	 how far the faction's units are spread across the map. 
	 */
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

		// set the total distance based on calculation
		return calculatedDistance;
	}

	/*
	 Method: setInitialArmyLevel
	 Input Parameters: none
	 Output Parameters: none

	 This method is called to at the start of a game to establish the initial
	 unit roster and the current unit roster.
	 */
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

	/*
	 Method: setCurrentArmyLevel
	 Input Parameters: none
	 Output Parameters: none
	
	 This method captures the overall size of the faction's unit roster as it
	 is at the end of a turn.
	 */
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
