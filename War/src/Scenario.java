
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author BotBen
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

    // Method to coordinate the establishment of the scenario
    public void Initialize(int _scenarioID) {
        switch (_scenarioID) { // perform correct scenario operations
            case 0:
                game = new Game(); // start a new game
                game.updateWeather(); // set the weather conditions
                game.setMaxTurnCount(20);
                setMapParameters(); // set up the scenario map, all roads and nodes
                setPlayers(); // set the players based on faction properties
                setCombatUnits(); // set the combat units based on scenario
                break;
        }
    }

    public static void killSwitch() {
        Node[] listOfNodes = null;
        Road[] listOfRoads = null;
        ArrayList<CombatUnit> listOfUnits = new ArrayList<CombatUnit>();
        Player redPlayer = null;
        Player bluePlayer = null;
        Random random = new Random();
        ArrayList<Integer> unitLocations = null;
        Game game = null;
    }

// Method to establish the roads and nodes on the map
    void setMapParameters() {
        // array of all the node names
        String[] names = {"Utica", "Fidenza", "Faenza", "Volterra", "Saturnia", "Senigalia", "Clusium", "Palestrina", "Naples", "Nola", "Brindis", "Dyrrhachulum", "Kavala", "Roma"};
        // storage of the distance to capital of Blue and Red respectively
        int[][] capitalDistances = {{1, 5}, {3, 6}, {2, 5}, {2, 5}, {2, 5}, {1, 4}, {1, 4}, {1, 4}, {1, 2}, {2, 1}, {3, 0}, {4, 1}, {5, 2}, {0, 3}};
        // storage of road capacity {0..2}, location a, and location b respectively
        int[][] locations = {{0, 0, 13}, {15, 1, 3}, {15, 2, 5}, {10, 3, 5}, {15, 3, 4}, {15, 4, 6}, {15, 6, 13}, {5, 5, 13}, {10, 7, 13}, {15, 8, 13}, {15, 8, 9}, {15, 9, 10}, {0, 10, 11}, {15, 11, 12}};
        // set up the arrays for roads and nodes
        listOfRoads = new Road[14];
        listOfNodes = new Node[14];

        // for each of the cities
        for (int i = 0; i < 14; i++) {
            int supply = (int) (4 * random.nextDouble()); // determine the supply level

            // determine node properties
            boolean isCity = true;
            boolean redCapital = false;
            boolean blueCapital = false;
            boolean isPort = false;
            switch (names[i]) { // assign properties based on the scenario
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

    // Method to set the factions
    void setPlayers() {
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
        redPlayer.setUpRumours();
        redPlayer.generateRomourList();
        bluePlayer.setUpRumours();
        bluePlayer.generateRomourList();
    }

    // Method to set up the units
    void setCombatUnits() {
        int temp; // temporary storage of the random unit placement value
        unitLocations = new ArrayList<Integer>() {
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
        listOfUnits.add(new CombatUnit(false, 0, 15, 0, listOfNodes[unitLocations.get(0)], bluePlayer));
        listOfUnits.add(new CombatUnit(false, 1, 10, 0, listOfNodes[unitLocations.get(1)], bluePlayer));
        listOfUnits.add(new CombatUnit(false, 2, 5, 0, listOfNodes[unitLocations.get(6)], bluePlayer));
        listOfUnits.add(new CombatUnit(true, 3, 5, 0, listOfNodes[unitLocations.get(2)], bluePlayer));
        listOfUnits.add(new CombatUnit(false, 4, 10, 0, listOfNodes[unitLocations.get(3)], redPlayer));
        listOfUnits.add(new CombatUnit(false, 5, 10, 0, listOfNodes[unitLocations.get(4)], redPlayer));
        listOfUnits.add(new CombatUnit(false, 6, 5, 0, listOfNodes[unitLocations.get(7)], redPlayer));
        listOfUnits.add(new CombatUnit(true, 7, 5, 0, listOfNodes[unitLocations.get(5)], redPlayer));
        // add the units to their respective players
        for (int i = 0; i < 4; i++) {
            bluePlayer.addUnit(listOfUnits.get(i));
        }
        for (int i = 4; i < 8; i++) {
            redPlayer.addUnit(listOfUnits.get(i));
        }

        // establish the current/initial armysize for each faction
        redPlayer.setCurrentArmyLevel();
        redPlayer.setInitialArmyLevel();
        bluePlayer.setCurrentArmyLevel();
        bluePlayer.setInitialArmyLevel();
    }

    public static Road findRoad(Node locationA, Node locationB) {
        Road found = null;

        for (int i = 0; i < listOfRoads.length; i++) {
            if (locationA.id == listOfRoads[i].locationA.id && locationB.id == listOfRoads[i].locationB.id){
                found = listOfRoads[i];
                return found;
            } else if (locationB.id == listOfRoads[i].locationA.id && locationA.id == listOfRoads[i].locationB.id){
                found = listOfRoads[i];
                return found;
            }
        }
        
        return null;
    }
}
