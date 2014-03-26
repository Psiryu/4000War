
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 * Scenario Class
 *
 * @author Ben Yntema
 *
 * Through this class, the preliminary game information is tabulated and
 * established based on the parameters of the chosen scenario. This class
 * initializes the properties of the game and subsequent player, units, and map
 * properties.
 *
 * Data in the form of arrays contain information needed to initialize the game
 * state and map properties.
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

    public Scenario() {

    }

    /*
     Method: Initialize
     Parameters: int _scenarioID -> the index of the chosen scenario based on user selection
    
     Initialize selects the data from which to draw upon when setting up the game
     state.
     */
    public void Initialize(int _scenarioID) {
        switch (_scenarioID) { // perform correct scenario operations
            case 0:
                game = new Game(); // start a new game
                game.updateWeather(); // set the weather conditions
                game.setMaxTurnCount(20); // set the maximum turn count
                setMapParameters(); // set up the scenario map, all roads and nodes
                setPlayers(); // set the players based on faction properties
                setCombatUnits(); // set the combat units based on scenario
                break;
        }
    }

    /*
     Method: killSwitch
     Parameters: none
    
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
     Parameters: none
    
     Information regarding the locations present in the chosen scenario and the 
     roads which connect them are translated to structures to be used in user
     interface operations and unit movement.
     */
    private void setMapParameters() {
        // array of all the node names
        String[] names = {"Utica", "Fidenza", "Faenza", "Volterra", "Saturnia", "Senigalia", "Clusium", "Palestrina", "Naples", "Nola", "Brindis", "Dyrrhachulum", "Kavala", "Roma"};
        // storage of the distance to capital of Blue and Red respectively
        int[][] capitalDistances = {{1, 5}, {3, 6}, {2, 5}, {2, 5}, {2, 5}, {1, 4}, {1, 4}, {1, 4}, {1, 2}, {2, 1}, {3, 0}, {4, 1}, {5, 2}, {0, 3}};
        // storage of road capacity {0..2}, location a, and location b respectively
        int[][] locations = {{0, 0, 13}, {20, 1, 3}, {20, 2, 5}, {10, 3, 5}, {20, 3, 4}, {20, 4, 6}, {20, 6, 13}, {5, 5, 13}, {10, 7, 13}, {20, 8, 13}, {20, 8, 9}, {20, 9, 10}, {0, 10, 11}, {20, 11, 12}};
        // set up the arrays for roads and nodes
        listOfRoads = new Road[capitalDistances.length];
        listOfNodes = new Node[names.length];

        // for each of the cities
        for (int i = 0; i < 14; i++) {
            int supply = (int) (4 * random.nextDouble()); // determine the supply level

            // determine node properties
            boolean isCity = true;
            boolean redCapital = false;
            boolean blueCapital = false;
            boolean isPort = false;
            // add properties to the node based on scenario parameters
            // indicate capital and if node is port
            switch (names[i]) {
                case "Roma":
                    blueCapital = true;
                    isPort = true;
                    break;
                case "Brindis":
                    redCapital = true;
                    isPort = true;
                    break;
                case "Senigalia":
                    isPort = true;
                    break;
                case "Dyrrhachulum":
                    isPort = true;
                    break;
            }
            // initialize each of the new nodes based on the scenario data
            listOfNodes[i] = new Node(i, redCapital, blueCapital, capitalDistances[i][1], capitalDistances[i][0], names[i], supply, isCity, isPort);
        }

        // for each of the roads
        for (int j = 0; j < 14; j++) {
            // determine which roads should be registered as naval only
            boolean isNaval = false;
            if (j == 0 || j == 12) {
                isNaval = true;
            }
            // initialize each of the new roads
            listOfRoads[j] = new Road(j, locations[j][0], listOfNodes[locations[j][1]], listOfNodes[locations[j][2]], isNaval);
        }
    }

    /*
     Method: setPlayers
     Parameters: none
    
     Based on user selection, players are assigned to either computer or player
     control and to their selected faction, be it red or blue player
     */
    private void setPlayers() {
        if (Global.opponent) { // if player vs player is chosen
            if (Global.chosenTeam) { // if player one selected red faction
                redPlayer = new Player(false, listOfNodes[10], 0, "red");
                Global.curPlayer = 1;
                bluePlayer = new Player(false, listOfNodes[13], 1, "blue");
            } else { // if player one selected blue faction
                bluePlayer = new Player(false, listOfNodes[13], 1, "blue");
                Global.curPlayer = 0;
                redPlayer = new Player(false, listOfNodes[10], 0, "red");
            }
        } else { // if player vs computer is chosen
            if (Global.chosenTeam) { // if player choses red faction
                redPlayer = new Player(false, listOfNodes[10], 0, "red");
                Global.curPlayer = 1;
                bluePlayer = new Player(true, listOfNodes[13], 1, "blue");
            } else { // if player choses blue faction
                bluePlayer = new Player(false, listOfNodes[13], 1, "blue");
                Global.curPlayer = 0;
                redPlayer = new Player(true, listOfNodes[10], 0, "red");
            }
        }
    }

    /*
     Method: setCombatUnits
     Parameters: none
    
     Combat units are constructed, assigned to a player, and placed on the map based
     on the parameters outlined in the scenario.
     */
    private void setCombatUnits() {
        int temp; // temporary storage of the random unit placement value
        unitLocations = new ArrayList<Integer>() { // list of all start locations
            {
                add(13);
                add(5);
                add(8);
                add(10);
                add(12);
                add(11);
            }
        };

        // select two random starting points for the small armies
        for (int i = 0; i < 2; i++) {
            do {
                temp = random.nextInt(13);
            } while (unitLocations.contains(temp));
            unitLocations.add(temp);
        }

        // initialize the combat units as per scenario parameters
        listOfUnits.add(new CombatUnit(false, 0, 20, 0, listOfNodes[unitLocations.get(0)], bluePlayer));
        listOfUnits.add(new CombatUnit(false, 1, 10, 0, listOfNodes[unitLocations.get(1)], bluePlayer));
        listOfUnits.add(new CombatUnit(false, 2, 5, 0, listOfNodes[unitLocations.get(6)], bluePlayer));
        listOfUnits.add(new CombatUnit(true, 3, 5, 0, listOfNodes[unitLocations.get(2)], bluePlayer));
        listOfUnits.add(new CombatUnit(false, 4, 10, 0, listOfNodes[unitLocations.get(3)], redPlayer));
        listOfUnits.add(new CombatUnit(false, 5, 10, 0, listOfNodes[unitLocations.get(4)], redPlayer));
        listOfUnits.add(new CombatUnit(false, 6, 5, 0, listOfNodes[unitLocations.get(7)], redPlayer));
        listOfUnits.add(new CombatUnit(true, 7, 5, 0, listOfNodes[unitLocations.get(5)], redPlayer));
        // add the units to their respective players
        for (int i = 0; i < 4; i++) {
            bluePlayer.combatUnits.add(listOfUnits.get(i));
        }
        for (int i = 4; i < 8; i++) {
            redPlayer.combatUnits.add(listOfUnits.get(i));
        }

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

        redPlayer.AdjustPoliticalPower();
        bluePlayer.AdjustPoliticalPower();
    }

    /*
     Method: findRoad
     Parameters: Node locationA -> the possible start location of the road to be found
     Node locationB -> the possible end location of the road to be found
    
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

    public static int findMaxDistance(int playerID) {
        int maxCheck = 0;
        if (Scenario.redPlayer.playerID == playerID) {
            for (Node location : Scenario.listOfNodes) {
                if (location.distanceFromCapitalRed > maxCheck) {
                    maxCheck = location.distanceFromCapitalRed;
                }
            }
            return maxCheck;
        } else {
            for (Node location : Scenario.listOfNodes) {
                if (location.distanceFromCapitalBlue > maxCheck) {
                    maxCheck = location.distanceFromCapitalBlue;
                }
            }
            return maxCheck;
        }
    }
}
