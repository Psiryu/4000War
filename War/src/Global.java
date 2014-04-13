/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Global class
 *
 * Description: This class captures the variables which effect methods
 * throughout the project. Such information includes weather, season, scenario
 * information, and end game conditions
 *
 * Usage: Access to content in Global can be done throughout the project with
 * the statement Global.X, where X is the variable you wish to access
 *
 * Maintenance notes: Items can be added to Global simply by adding to the
 * variable list at the start of the class.
 */
public class Global {

	public static Boolean opponent = false; //if true, against player. false means AI
	public static Boolean chosenTeam = false; // toggle for the character player 1 choses, false means player 1 is team 1
	public static int weather; // storage for the weather value
	public static int season; // storage for the season value
	public static String teamOne; // team name 1
	public static String teamTwo; // team name 2
	public static int intScenario; // scenario number
	public static MapEvent mapEvent; // delcare a MapEvent object for event processing
	public static int curPlayer; //global variable for keeping track of whos turn it is
	public static String gameSummary; //will be populated with whom the winner of a match was
	public static String gameSummary2; //will be populated with why the winner won
	public static int intGameOver; // value to check if a game over condition has been met, initialized to 0    
	public static int timer; // Value to overidden in other compnent. game is to have time limit.
	public static String redPreEmptiveToString, bluePreEmptiveToString; // used to display the result of battles

	/*
	 Method: ResetGlobals
	 Input Parameters: none
	 Output Parameters: none

	 This method is called at the end of a game in order to reset the values
	 used in the game.
	 */
	public static void ResetGlobals() {
		opponent = false;
		chosenTeam = false;
		weather = 0;
		season = 0;
		teamOne = null;
		teamTwo = null;
		intScenario = 0;
		mapEvent = null;
		curPlayer = 0;
		gameSummary = "";
		gameSummary2 = "";
		intGameOver = 0;
		redPreEmptiveToString = "";
		bluePreEmptiveToString = "";
	}
}
