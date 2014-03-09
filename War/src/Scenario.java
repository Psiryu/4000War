
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

    public static String[] factionNamesOne = {"Populares","Optimates"}; // blue player, red player
    public static Node[] listOfNodes;
    public static Road[] listOfRoads;
    public static CombatUnit[] listOfUnits;
    //int scenarioID = 0;
    public static Player redPlayer;
    public static Player bluePlayer;
    public static Random random = new Random();
    public static int[] randomNums = new int[2];

    public void Initialize(int _scenarioID) {
        switch (_scenarioID) {
            case 0:
                setMapParameters();
                setPlayers();
                setCombatUnits();
                break;
        }
    }

    void setPlayers() {
        if (Global.opponent) {
            if (Global.chosenTeam) {
                redPlayer = new Player(false, listOfNodes[13], 0);
                bluePlayer = new Player(false, listOfNodes[10], 1);
            } else {
                bluePlayer = new Player(false, listOfNodes[13], 0);
                redPlayer = new Player(false, listOfNodes[10], 1);
            }
        } else {
            if (Global.chosenTeam) {
                redPlayer = new Player(false, listOfNodes[13], 0);
                bluePlayer = new Player(true, listOfNodes[10], 1);
            } else {
                bluePlayer = new Player(false, listOfNodes[13], 0);
                redPlayer = new Player(true, listOfNodes[10], 1);
            }
        }
    }

    void setMapParameters() {
        String[] names = {"Utica", "Fidenza", "Faenza", "Volterra", "Saturnia", "Senigalia", "Clusium", "Palestrina", "Naples", "Nola", "Brindis", "Dyrrhachulum", "Kavala", "Roma"};
        // storage of the distance to capital of Blue and Red respectively
        int[][] capitalDistances = {{1, 5}, {3, 6}, {2, 5}, {2, 5}, {2, 5}, {1, 4}, {1, 4}, {1, 4}, {1, 2}, {2, 1}, {3, 0}, {4, 1}, {5, 2}, {0, 3}};
        // storage of road capacity {0..2}, location a, and location b respectively
        int[][] locations = {{2, 0, 13}, {2, 1, 3}, {2, 2, 5}, {1, 3, 5}, {2, 3, 4}, {2, 4, 6}, {2, 7, 13}, {0, 5, 13}, {1, 7, 13}, {2, 8, 13}, {2, 8, 9}, {2, 9, 10}, {2, 10, 11}, {2, 11, 12}};
        listOfRoads = new Road[14];
        listOfNodes = new Node[14];
        listOfUnits = new CombatUnit[8];
        // for each of the cities
        for (int i =0; i < 14 ;i++)
        {
            int supply = (int) (4 * random.nextDouble());
            boolean redCapital = false;
            boolean blueCapital = false;
            switch (names[i]) {
                case "Roma":
                    blueCapital = true;
                    break;
                case "Brindis":
                    redCapital = true;
                    break;
            }
            listOfNodes[i] = new Node(i, redCapital, blueCapital, capitalDistances[i][1], capitalDistances[i][0], names[i], supply, true);
        }
        /*for (int i = 0; i < 14; i++) {
            listOfNodes[i].name = names[i];
            listOfNodes[i].distanceFromCapitalBlue = capitalDistances[i][0];
            listOfNodes[i].distanceFromCapitalRed = capitalDistances[i][1];
            // check for the capitals
            switch (names[i]) {
                case "Roma":
                    listOfNodes[i].isCapitalBlue = true;
                    break;
                case "Brindis":
                    listOfNodes[i].isCapitalRed = true;
                    break;
            }
        }*/
        // for each of the roads
        for (int j = 0; j < 14; j++) {
            boolean isNaval = false;
            if (j == 0 || j == 12) {
                isNaval = true;
            }
            listOfRoads[j] = new Road(j,locations[j][0], listOfNodes[locations[j][1]], listOfNodes[locations[j][2]], isNaval);
            /*listOfRoads[j].capacity = locations[j][0];
            listOfRoads[j].locationA = listOfNodes[locations[j][1]];
            listOfRoads[j].locationB = listOfNodes[locations[j][2]];
            if (j == 0 || j == 12) {
                listOfRoads[j].isNaval = true;
            }*/
        }
    }

    void setCombatUnits() {
        // select two random starting points for the small armies
        randomNums[0] = (int) (13 * random.nextDouble());
        randomNums[1] = (int) (13 * random.nextDouble());
        while (randomNums[0] == randomNums[1]) {
            randomNums[1] = (int) (13 * random.nextDouble());
        }

        // initialize the combat units as per scenario parameters
        listOfUnits[0] = new CombatUnit(false, 0, 15, 0, listOfNodes[13], bluePlayer);
        listOfUnits[1] = new CombatUnit(false, 0, 10, 0, listOfNodes[5], bluePlayer);
        listOfUnits[2] = new CombatUnit(false, 0, 5, 0, listOfNodes[randomNums[0]], bluePlayer);
        listOfUnits[3] = new CombatUnit(true, 0, 5, 0, listOfNodes[8], bluePlayer);
        listOfUnits[4] = new CombatUnit(false, 0, 10, 0, listOfNodes[10], redPlayer);
        listOfUnits[5] = new CombatUnit(false, 0, 10, 0, listOfNodes[12], redPlayer);
        listOfUnits[6] = new CombatUnit(false, 0, 5, 0, listOfNodes[randomNums[1]], redPlayer);
        listOfUnits[7] = new CombatUnit(true, 0, 5, 0, listOfNodes[11], redPlayer);
    }
}
