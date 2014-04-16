
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 * Scenario Class
 *
 * Description: Through this class, the preliminary game information is
 * tabulated and established based on the parameters of the chosen scenario.
 * This class initializes the properties of the game and subsequent player,
 * units, and map properties. Data in the form of arrays contain information
 * needed to initialize the game state and map properties.
 *
 * Usage: Scenario is initialized in Team when the user opts to begin the game.
 * After that, Scenario is called to when a class requires knowledge regarding
 * players or map properties. Each variable used in Scenario is accessible by
 * referencing Scenario.X, where X is the name of the variable.
 *
 * Maintenance notes: New scenarios can be added to the Initialize method. To do
 * so, add: - a new case in the switch statement - populate with data either
 * using the template provided or by referencing the format of other switches
 * Modifications to current scenarios can be done by modifying the reference
 * arrays in each switch case. Additional information can be found in the
 * Maintenance Manual.
 */
public class Scenario {

	public static Node[] listOfNodes; // list of all nodes in the scenario
	public static Road[] listOfRoads; // list of all the roads in the scenario
	public static ArrayList<CombatUnit> listOfUnits = new ArrayList<CombatUnit>(); // list of all the units in the scenario
	public static Player redPlayer; // red faction representative
	public static Player bluePlayer; // blue faction representative
	public static Random random = new Random(); // random number for use in unit placement
	public static ArrayList<Integer> unitLocations; // initialize array to store unit locations
	public static Game game; // new game object
	public static String[] names; // the names of the scenario locations
	public static int[][] capitalDistances; // the number of nodes between a location and a capital
	public static int[][] locations; // road properties, including capacity, start location, and end location
	public static boolean[][] locationProperties; // additional location information including is red capital, is blue capital, and is port
	public static boolean[] isNavalRoute; // additional property for roads indicating if route traverses body of water
	public static boolean[] isCity; // additional location property, true if the location is a city, false if checkpoint

	/*
	 Method: Scenario
	 Input Parameters: none 
	 Output Parameters: none

	 The constructor for the Scenario object, no content needed
	 */
	public Scenario() {

	}

	/*
	 Method: Initialize
	 Input 0Parameters: int _scenarioID -> the index of the chosen scenario based on user selection
	 Output Parameters: none
    
	 Initialize selects the data from which to draw upon when setting up the game
	 state.
	 */
	public void Initialize(int _scenarioID) {
		game = new Game(); // start a new game
		game.updateWeather(); // set the weather conditions
		game.setMaxTurnCount(20); // set the maximum turn count
		setPlayers(); // set the players based on faction properties
		switch (_scenarioID) { // perform correct scenario operations, each case capturing the selection
			case 0:
				// strings for each location name
				names = new String[]{"Rome", "Aquileia", "Mursa", "Cibalea", "Salona", "Brundisium", "Sirmium", "Dyrrachium", "Singidunum", "Thessalonica", "Ratiaria", "Sardica", "Heralea", "Marcianopolis", "Adrianople", "Constantinople"};
				// distance from red capital, distance from blue capital
				capitalDistances = new int[][]{{0, 5}, {1, 5}, {2, 5}, {2, 5}, {2, 4}, {1, 4}, {3, 4}, {2, 3}, {4, 3}, {3, 2}, {5, 3}, {5, 2}, {4, 1}, {6, 2}, {6, 1}, {5, 0}};
				// is red capital, is blue capital, is port
				locationProperties = new boolean[][]{{true, false, false}, {false, false, false}, {false, false, false}, {false, false, false}, {false, false, false}, {false, false, false}, {false, false, false}, {false, false, false}, {false, false, false}, {false, false, false}, {false, false, false}, {false, false, false}, {false, false, false}, {false, false, false}, {false, false, false}, {false, true, false}};
				// is the location a city?
				isCity = new boolean[]{true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true};
				// road capacity, start location for road, end location for road
				locations = new int[][]{{20, 0, 5}, {20, 0, 1}, {10, 1, 2}, {10, 1, 3}, {10, 1, 4}, {20, 2, 6}, {20, 3, 6}, {10, 4, 7}, {10, 5, 7}, {10, 6, 8}, {10, 7, 9}, {20, 8, 12}, {10, 8, 11}, {10, 9, 11}, {20, 10, 13}, {20, 11, 14}, {20, 9, 12}, {20, 13, 14}, {20, 14, 15}, {20, 12, 15}};
				// true if the road is a naval only route
				isNavalRoute = new boolean[]{false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false};

				setMapParameters(); // set up the scenario map, all roads and nodes

				// set the player capitals
				redPlayer.capital = listOfNodes[0];
				bluePlayer.capital = listOfNodes[15];

				//add units to each player's roster
				listOfUnits.add(new CombatUnit(false, 0, 20, 0, listOfNodes[1], redPlayer));
				listOfUnits.add(new CombatUnit(false, 1, 15, 0, listOfNodes[0], redPlayer));
				redPlayer.combatUnits.add(listOfUnits.get(0));
				redPlayer.combatUnits.add(listOfUnits.get(1));
				listOfUnits.add(new CombatUnit(false, 2, 20, 0, listOfNodes[6], bluePlayer));
				listOfUnits.add(new CombatUnit(false, 3, 15, 0, listOfNodes[15], bluePlayer));
				bluePlayer.combatUnits.add(listOfUnits.get(2));
				bluePlayer.combatUnits.add(listOfUnits.get(3));

				setCombatUnits(); // set the combat units based on scenario
				break;
			case 1:
				// strings for each location name
				names = new String[]{"Rome", "Aquileia", "Mursa", "Cibalea", "Salona", "Brundisium", "Sirmium", "Dyrrachium", "Singidunum", "Thessalonica", "Ratiaria", "Sardica", "Heralea", "Marcianopolis", "Adrianople", "Constantinople"};
				// distance from red capital, distance from blue capital
				capitalDistances = new int[][]{{0, 5}, {1, 5}, {2, 5}, {2, 5}, {2, 4}, {1, 4}, {3, 4}, {2, 3}, {4, 3}, {3, 2}, {5, 3}, {5, 2}, {4, 1}, {6, 2}, {6, 1}, {5, 0}};
				// is red capital, is blue capital, is port
				locationProperties = new boolean[][]{{true, false, false}, {false, false, false}, {false, false, false}, {false, false, false}, {false, false, false}, {false, false, false}, {false, false, false}, {false, false, false}, {false, false, false}, {false, false, false}, {false, false, false}, {false, false, false}, {false, false, false}, {false, false, false}, {false, false, false}, {false, true, false}};
				// is the location a city?
				isCity = new boolean[]{true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true};
				// road capacity, start location for road, end location for road
				locations = new int[][]{{2, 0, 5}, {2, 0, 1}, {1, 1, 2}, {1, 1, 3}, {1, 1, 4}, {2, 2, 6}, {2, 3, 6}, {1, 4, 7}, {1, 5, 7}, {1, 6, 8}, {1, 7, 9}, {2, 8, 12}, {1, 8, 11}, {1, 9, 11}, {2, 10, 13}, {2, 11, 14}, {2, 9, 12}, {2, 13, 14}, {2, 14, 15}, {2, 12, 15}};
				// true if the road is a naval only route
				isNavalRoute = new boolean[]{false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false};

				setMapParameters(); // set up the scenario map, all roads and nodes

				// set the player capitals
				redPlayer.capital = listOfNodes[0];
				bluePlayer.capital = listOfNodes[15];

				//add units to each player's roster
				listOfUnits.add(new CombatUnit(false, 0, 20, 0, listOfNodes[1], redPlayer));
				listOfUnits.add(new CombatUnit(false, 1, 15, 0, listOfNodes[0], redPlayer));
				redPlayer.combatUnits.add(listOfUnits.get(0));
				redPlayer.combatUnits.add(listOfUnits.get(1));
				listOfUnits.add(new CombatUnit(false, 0, 20, 0, listOfNodes[15], bluePlayer));
				listOfUnits.add(new CombatUnit(false, 1, 15, 0, listOfNodes[6], bluePlayer));
				bluePlayer.combatUnits.add(listOfUnits.get(2));
				bluePlayer.combatUnits.add(listOfUnits.get(3));

				setCombatUnits(); // set the combat units based on scenario
				break;
			case 2:
				// strings for each location name
				names = new String[]{"Rome", "Aquileia", "Mursa", "Cibalea", "Salona", "Brundisium", "Sirmium", "Dyrrachium", "Singidunum", "Thessalonica", "Ratiaria", "Sardica", "Heralea", "Marcianopolis", "Adrianople", "Constantinople"};
				// distance from red capital, distance from blue capital
				capitalDistances = new int[][]{{0, 5}, {1, 5}, {2, 5}, {2, 5}, {2, 4}, {1, 4}, {3, 4}, {2, 3}, {4, 3}, {3, 2}, {5, 3}, {5, 2}, {4, 1}, {6, 2}, {6, 1}, {5, 0}};
				// is red capital, is blue capital, is port
				locationProperties = new boolean[][]{{true, false, false}, {false, false, false}, {false, false, false}, {false, false, false}, {false, false, false}, {false, false, false}, {false, false, false}, {false, false, false}, {false, false, false}, {false, false, false}, {false, false, false}, {false, false, false}, {false, false, false}, {false, false, false}, {false, false, false}, {false, true, false}};
				// is the location a city?
				isCity = new boolean[]{true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true};
				// road capacity, start location for road, end location for road
				locations = new int[][]{{2, 0, 5}, {2, 0, 1}, {1, 1, 2}, {1, 1, 3}, {1, 1, 4}, {2, 2, 6}, {2, 3, 6}, {1, 4, 7}, {1, 5, 7}, {1, 6, 8}, {1, 7, 9}, {2, 8, 12}, {1, 8, 11}, {1, 9, 11}, {2, 10, 13}, {2, 11, 14}, {2, 9, 12}, {2, 13, 14}, {2, 14, 15}, {2, 12, 15}};
				// true if the road is a naval only route
				isNavalRoute = new boolean[]{false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false};

				setMapParameters(); // set up the scenario map, all roads and nodes

				// set the player capitals
				redPlayer.capital = listOfNodes[0];
				bluePlayer.capital = listOfNodes[15];

				//add units to each player's roster
				listOfUnits.add(new CombatUnit(false, 0, 20, 0, listOfNodes[1], redPlayer));
				listOfUnits.add(new CombatUnit(false, 1, 15, 0, listOfNodes[0], redPlayer));
				redPlayer.combatUnits.add(listOfUnits.get(0));
				redPlayer.combatUnits.add(listOfUnits.get(1));
				listOfUnits.add(new CombatUnit(false, 0, 20, 0, listOfNodes[9], bluePlayer));
				listOfUnits.add(new CombatUnit(false, 1, 15, 0, listOfNodes[15], bluePlayer));
				bluePlayer.combatUnits.add(listOfUnits.get(2));
				bluePlayer.combatUnits.add(listOfUnits.get(3));

				setCombatUnits(); // set the combat units based on scenario
				break;
		}
	}

	/*
	 Method: killSwitch
	 Input Parameters: none
	 Output Parameters: none
    
	 Variables are set to default values at the end of the lifecycle of the game 
	 state or when a player opts to return to scenario selection mid game.
	 */
	public static void killSwitch() {
		listOfNodes = null;
		listOfRoads = null;
		listOfUnits = new ArrayList<CombatUnit>();
		redPlayer = null;
		bluePlayer = null;
		unitLocations = null;
		game = null;
	}

	/*
	 Method: setMapParameters
	 Input Parameters: none
	 Output Parameters: none
    
	 Information regarding the locations present in the chosen scenario and the 
	 roads which connect them are translated to structures to be used in user
	 interface operations and unit movement.
	 */
	private void setMapParameters() {
		// set up the arrays for roads and nodes
		listOfRoads = new Road[locations.length];
		listOfNodes = new Node[names.length];

		// for each of the cities
		for (int i = 0; i < listOfNodes.length; i++) {
			int supply = (int) (4 * random.nextDouble()); // determine the supply level randomly
			// initialize each of the new nodes based on the scenario data
			listOfNodes[i] = new Node(i, locationProperties[i][0], locationProperties[i][1], capitalDistances[i][0], capitalDistances[i][1], names[i], supply, isCity[i], locationProperties[i][2]);
		}

		// for each of the roads
		for (int j = 0; j < listOfRoads.length; j++) {
			// initialize the road object based on scenario data
			listOfRoads[j] = new Road(j, locations[j][0], listOfNodes[locations[j][1]], listOfNodes[locations[j][2]], isNavalRoute[j]);
		}
	}

	/*
	 Method: setPlayers
	 Input Parameters: none
	 Output Paramaeters: none
    
	 Based on user selection, players are assigned to either computer or player
	 control and to their selected faction, be it red or blue player
	 */
	private void setPlayers() {
		if (Global.opponent) { // if player vs player is chosen
			if (Global.chosenTeam) { // if player one selected red faction
				redPlayer = new Player(false, 0, "red");
				Global.curPlayer = 1;
				bluePlayer = new Player(false, 1, "blue");
			} else { // if player one selected blue faction
				bluePlayer = new Player(false, 1, "blue");
				Global.curPlayer = 0;
				redPlayer = new Player(false, 0, "red");
			}
		} else { // if player vs computer is chosen
			if (Global.chosenTeam) { // if player choses red faction
				redPlayer = new Player(false, 0, "red");
				Global.curPlayer = 1;
				bluePlayer = new Player(true, 1, "blue");
			} else { // if player choses blue faction
				bluePlayer = new Player(false, 1, "blue");
				Global.curPlayer = 0;
				redPlayer = new Player(true, 0, "red");
			}
		}
	}

	/*
	 Method: setCombatUnits
	 Input Parameters: none
	 Output Parameters: none
    
	 Combat units are constructed, assigned to a player, and placed on the map based
	 on the parameters outlined in the scenario.
	 */
	private void setCombatUnits() {
		// remove supplies where units have been places
		for (CombatUnit unit : listOfUnits) {
			unit.location.removeSupplies();
		}

		// establish the current/initial armysize for each faction
		redPlayer.setInitialArmyLevel();
		bluePlayer.setInitialArmyLevel();

		// with all units assigned, initialize the knowledge of enemy location for each player
		redPlayer.setUpRumours();
		redPlayer.generateRumourList();
		bluePlayer.setUpRumours();
		bluePlayer.generateRumourList();

		// establish the starting political power levels
		redPlayer.AdjustPoliticalPower();
		bluePlayer.AdjustPoliticalPower();
	}

	/*
	 Method: findRoad
	 Input Parameters: Node locationA -> the possible start location of the road to be found
	 Node locationB -> the possible end location of the road to be found
	 Output Parameters: Road found -> the road object found in the search
    
	 This method allows for the roads to be searched through based on a supposed
	 start and end location. The found road is returned.
	 */
	public static Road findRoad(Node locationA, Node locationB) {
		Road found = null; // storage of the found road

		// for each of the roads in the scenario
		for (int i = 0; i < listOfRoads.length; i++) {
			// compare the start and end location of each road to the current road
			if (locationA.id == listOfRoads[i].locationA.id && locationB.id == listOfRoads[i].locationB.id) {
				found = listOfRoads[i]; // when found, store the found road
				return found; // return the found road
			} else if (locationB.id == listOfRoads[i].locationA.id && locationA.id == listOfRoads[i].locationB.id) {
				found = listOfRoads[i];
				return found;
			}
		}
		return found;
	}

	/*
	 Method: findMaxDistance
	 Input Parameters: int playerID -> the player to be evaluated
	 Output Parameters: int maxCheck -> the largest distance from capital found in search

	 This method searches the the listOfNodes in the scenario for the largest
	 possible distance from capital for use in political power calculation.
	 */
	public static int findMaxDistance(int playerID) {
		int maxCheck = 0; // initialize the maximum value found
		if (Scenario.redPlayer.playerID == playerID) { // if the player is red player
			for (Node location : Scenario.listOfNodes) { // search all locations in Scenario
				if (location.distanceFromCapitalRed > maxCheck) { // if the distance is the largest seen, store it
					maxCheck = location.distanceFromCapitalRed;
				}
			}
			return maxCheck; // return the largest found
		} else { // repeat for blue player
			for (Node location : Scenario.listOfNodes) {
				if (location.distanceFromCapitalBlue > maxCheck) {
					maxCheck = location.distanceFromCapitalBlue;
				}
			}
			return maxCheck;
		}
	}
}
