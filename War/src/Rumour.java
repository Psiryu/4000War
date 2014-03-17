
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
 * @author Ben
 */
public class Rumour {

    public static Node[] listOfNodes; // storage of available location
    public static ArrayList<CombatUnit> listOfUnitsRed; // storage of current red units
    public static ArrayList<CombatUnit> listOfUnitsBlue; // storage of current blue units
    public static Random random = new Random(); // establish a random variable
    public static int redMaximum;
    public static int blueMaximum;

    // Constructor of the rumour system
    public Rumour() {
        // set the necessary parameters
        listOfNodes = Scenario.listOfNodes;
        listOfUnitsRed = new ArrayList<CombatUnit>();
        listOfUnitsBlue = new ArrayList<CombatUnit>();
        redMaximum = (collectedMapFog(Scenario.redPlayer.combatUnits.size())) + 200;
        blueMaximum = (collectedMapFog(Scenario.bluePlayer.combatUnits.size())) + 200;
    }

    private int collectedMapFog(int numUnits) {
        int largestConnections = 0;
        int[] connections = connectedNodes();

        for (int i = connections.length - 1; i >= connections.length - numUnits; i--) {
            largestConnections += connections[i] * 10;
        }
        return largestConnections;
    }

    private int[] connectedNodes() {
        int calculated[] = new int[Scenario.listOfNodes.length];
        int tempCalc = 0;

        for (int i = 0; i < Scenario.listOfNodes.length; i++) {
            for (int j = 0; j < Scenario.listOfRoads.length; j++) {
                if (Scenario.listOfRoads[j].locationA.id == listOfNodes[i].id || Scenario.listOfRoads[j].locationB.id == listOfNodes[i].id) {
                    tempCalc++;
                }
            }
            calculated[i] = tempCalc * 4;
            tempCalc = 0;
        }
        Arrays.sort(calculated);
        return calculated;
    }

    // Method to update the rumour awareness of current units
    public void updateUnitRegistry() {
        // set the new unit lists
        listOfUnitsRed = Scenario.redPlayer.combatUnits;
        listOfUnitsBlue = Scenario.bluePlayer.combatUnits;
    }

    private double playerFogValueUpdate() {
        Player currentPlayer;
        Node currentNode;
        int roadFogSum = 0;
        int nodeFogSum = 0;
        int fogSum;
        int randomFog;

        // determine the player to be used
        if (Scenario.redPlayer.playerID == Global.curPlayer) {
            currentPlayer = Scenario.redPlayer;
        } else {
            currentPlayer = Scenario.bluePlayer;
        }

        for (int i = 0; i < currentPlayer.combatUnits.size(); i++) {
            currentNode = currentPlayer.combatUnits.get(i).location;

            for (int j = 0; j < Scenario.listOfRoads.length; j++) {
                if (Scenario.listOfRoads[j].locationA.id == currentNode.id || Scenario.listOfRoads[j].locationB.id == currentNode.id) {
                    roadFogSum += Scenario.listOfRoads[j].fogValue;
                }
            }

            nodeFogSum += currentNode.fogValue;
        }

        randomFog = (int) (random.nextDouble() * 100);
        fogSum = nodeFogSum + roadFogSum + currentPlayer.politicalPower + randomFog;

        if (Scenario.redPlayer.playerID == Global.curPlayer) {
            fogSum = (fogSum / redMaximum);
        } else {
            fogSum = (fogSum / blueMaximum);
        }

        return fogSum;
    }

    public ArrayList<Integer> reportRumour(int nodeID) {
        ArrayList<Integer> rumourList = new ArrayList<Integer>();
        Node currentNode = Scenario.listOfNodes[nodeID];
        ArrayList<Integer> units = nodeOccupancy(nodeID);
        double mapEffect = 1.0 - playerFogValueUpdate();
        double sigma;
        int mean;
        double rumour;
        boolean flip;
        int report;

        if (currentNode.timeUnoccupied > 0) {
            rumour = random.nextGaussian() * (3 - currentNode.timeUnoccupied);
            for (int i = 0; i < rumour; i++) {
                report = (int) (random.nextGaussian() * (3 - currentNode.timeUnoccupied));
                if (report > 3) {
                    report = 3;
                }
                rumourList.add(report);
            }
        } else {
            for (int i = 0; i < units.size(); i++) {
                mean = units.get(i);
                i++;
                sigma = mapEffect - (3 * units.get(i));
                if (sigma < 0) {
                    sigma = 0;
                }
                rumour = random.nextGaussian() * sigma;
                flip = random.nextBoolean();
                if (flip) {
                    rumour = rumour * -1;
                }
                if (rumour < 0.9 && rumour > -0.9) {
                    if (rumour > 0.5) {
                        if (mean == 2) {
                            report = 1;
                        } else if (mean == 1) {
                            report = 2;
                        } else {
                            report = 1;
                        }
                    } else if (rumour < -0.5) {
                        if (mean == 2) {
                            report = 0;
                        } else if (mean == 1) {
                            report = 0;
                        } else {
                            report = 2;
                        }
                    } else {
                        report = mean;
                    }
                    rumourList.add(report);
                }
            }
            if (mapEffect > 0.75) {
                if (random.nextBoolean()) {
                    rumourList.add(random.nextInt(2));
                    rumourList.add(random.nextInt(2));
                } else {
                    rumourList.add(random.nextInt(2));
                }
            }
        }

        return rumourList;
    }

    private ArrayList<Integer> nodeOccupancy(int nodeID) {
        Node currentNode = Scenario.listOfNodes[nodeID];
        ArrayList<Integer> units = new ArrayList<Integer>();
        Player currentOpponent;
        int scaledSize;

        // determine the player to be used
        if (Scenario.redPlayer.playerID == Global.curPlayer) {
            currentOpponent = Scenario.bluePlayer;
        } else {
            currentOpponent = Scenario.redPlayer;
        }

        for (int i = 0; i < currentOpponent.combatUnits.size(); i++) {
            if (currentOpponent.combatUnits.get(i).location.id == currentNode.id) {
                if (currentOpponent.combatUnits.get(i).size > 10) {
                    scaledSize = 2;
                } else if (currentOpponent.combatUnits.get(i).size > 5) {
                    scaledSize = 1;
                } else {
                    scaledSize = 0;
                }
                units.add(scaledSize);
                units.add(currentOpponent.combatUnits.get(i).timeStationary);
            }
        }

        return units;
    }
    /*
     From here we need to establish how to translate our fog functions
     FogOfWar = [(L+[surz])*numA[i]]+PoliticalPower+random
     where: L = location value {0..10}
     surz = surrounding path values {0..4}
     PoliticalPower {0..30}
     random {-100..100}
     0 < FogOfWar < 100
     */
}
