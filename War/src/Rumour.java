
import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javax.swing.JOptionPane;

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

    public Node[] listOfNodes = Scenario.listOfNodes; // storage of available location
    public ArrayList<CombatUnit> listOfUnitsRed = new ArrayList<CombatUnit>(); // storage of current red units
    public ArrayList<CombatUnit> listOfUnitsBlue = new ArrayList<CombatUnit>(); // storage of current blue units
    public Random random = new Random(); // establish a random variable
    public int maximum;
    public Player currentPlayer;

    // Constructor of the rumour system
    public Rumour(int currentPlayerID) {
        // set the necessary parameters
        listOfNodes = Scenario.listOfNodes;
        listOfUnitsRed = new ArrayList<CombatUnit>();
        listOfUnitsBlue = new ArrayList<CombatUnit>();
        // determine the player to be used
        if (Scenario.redPlayer.playerID == currentPlayerID) {
            currentPlayer = Scenario.redPlayer;
        } else {
            currentPlayer = Scenario.bluePlayer;
        }
        maximum = (collectedMapFog(currentPlayer.combatUnits.size())) + 200;
    }

    private int collectedMapFog(int numUnits) {
        int largestConnections = 0;
        int[] connections = connectedNodes();

        for (int i = connections.length - 1; i >= (connections.length - numUnits); i--) {
            largestConnections += connections[i];
        }
        return largestConnections;
    }

    private int[] connectedNodes() {
        int calculated[] = new int[Scenario.listOfNodes.length];
        int tempCalc = 0;

        for (int i = 0; i < Scenario.listOfNodes.length; i++) {
            for (int j = 0; j < Scenario.listOfRoads.length; j++) {
                if (Scenario.listOfRoads[j].locationA.id == listOfNodes[i].id || Scenario.listOfRoads[j].locationB.id == listOfNodes[i].id) {
                    tempCalc += Scenario.listOfRoads[j].fogValue;
                }
            }
            calculated[i] = tempCalc + Scenario.listOfNodes[i].fogValue;
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
        Node currentNode;
        int roadFogSum = 0;
        int nodeFogSum = 0;
        int fogSum;
        int randomFog;
        double calculatedFog;

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

        calculatedFog = (double) fogSum / maximum;

        return calculatedFog;
    }

    public ArrayList<ArrayList<Integer>> playerRumourSummary() {
        ArrayList<Integer> temp = new ArrayList<Integer>();
        ArrayList<ArrayList<Integer>> assembled = new ArrayList<ArrayList<Integer>>();

        for (Node listOfNode : Scenario.listOfNodes) {
            temp = reportRumour(listOfNode.id);
            assembled.add(temp);
        }

        return assembled;
    }

    private ArrayList<Integer> reportRumour(int nodeID) {
        ArrayList<Integer> rumourList = new ArrayList<Integer>();
        Node currentNode = Scenario.listOfNodes[nodeID];
        ArrayList<Integer> units = nodeOccupancy(nodeID);
        double fogUpdate = playerFogValueUpdate();
        double mapEffect = (1.0 - fogUpdate) * 9;
        double sigma;
        double timeAsAFunction;
        double randomGauss;
        int mean;
        double rumour;
        int report = 99;

        rumourList.add(currentNode.id);

        for (int i = 0; i < units.size(); i++) {
            do {
                randomGauss = abs(random.nextGaussian());
            } while (randomGauss > 1);

            timeAsAFunction = randomGauss * (3 - units.get(i + 1));

            if (timeAsAFunction > 1.5) {
                if (random.nextGaussian() > 1.8) {
                    rumourList.add(random.nextInt(3));
                }
                return rumourList;
            }

            do {
                randomGauss = abs(random.nextGaussian());
            } while (randomGauss > 1);

            timeAsAFunction = randomGauss * (3 - units.get(i + 1));

            if (timeAsAFunction > 0.5) {
                ArrayList<Integer> neighbours = neighbourFind(nodeID);
                int rand = random.nextInt(neighbours.size());
                rumourList.set(0, neighbours.get(rand));
            } else if (timeAsAFunction > 0.75) {
                ArrayList<Integer> neighbours = neighbourFind(nodeID);
                int rand = random.nextInt(neighbours.size());
                neighbours = neighbourFind(neighbours.get(rand));
                rand = random.nextInt(neighbours.size());
                rumourList.set(0, neighbours.get(rand));
            }

            mean = units.get(i);
            i++;
            sigma = mapEffect - (3 * units.get(i));
            if (sigma < 0) {
                sigma = 0;
            }

            do {
                randomGauss = abs(random.nextGaussian());
            } while (randomGauss > 1);

            rumour = (randomGauss * sigma) / 9;

            if (rumour >= 0.4 && rumour <= 0.7) {
                if (mean == 2) {
                    report = 0;
                    rumourList.add(0);
                    //JOptionPane.showMessageDialog(null, "At " + currentNode.name + ", rumour size " + report);
                } else if (mean == 1) {
                    report = 0;
                    rumourList.add(0);
                    //JOptionPane.showMessageDialog(null, "At " + currentNode.name + ", rumour size " + report);
                } else if (mean == 0) {
                    report = 2;
                    rumourList.add(2);
                    //JOptionPane.showMessageDialog(null, "At " + currentNode.name + ", rumour size " + report);
                } else {
                    report = 1;
                    rumourList.add(1);
                    //JOptionPane.showMessageDialog(null, "At " + currentNode.name + ", rumour size " + report);
                }
            } else if (rumour >= 0.065 && rumour < 0.4) {
                if (mean == 2) {
                    report = 1;
                    rumourList.add(1);
                    //JOptionPane.showMessageDialog(null, "At " + currentNode.name + ", rumour size " + report);
                } else if (mean == 1) {
                    report = 2;
                    rumourList.add(2);
                    //JOptionPane.showMessageDialog(null, "At " + currentNode.name + ", rumour size " + report);
                } else if (mean == 0) {
                    report = 1;
                    rumourList.add(1);
                    //JOptionPane.showMessageDialog(null, "At " + currentNode.name + ", rumour size " + report);
                } else {
                    report = 0;
                    rumourList.add(0);
                    //JOptionPane.showMessageDialog(null, "At " + currentNode.name + ", rumour size " + report);
                }
            } else if (rumour < 0.065) {
                report = mean;
                rumourList.add(mean);
            }
            //JOptionPane.showMessageDialog(null, "Rumour at: " + currentNode.name + " With weight: " + rumour + ", Mean: " + mean + ", Report: " + report);
        }
        
        return rumourList;
    }

    private ArrayList<Integer> neighbourFind(int nodeID) {
        ArrayList<Integer> neighbours = new ArrayList<Integer>();

        for (Road listOfRoad : Scenario.listOfRoads) {
            if (listOfRoad.locationA.id == nodeID) {
                neighbours.add(listOfRoad.locationB.id);
            } else if (listOfRoad.locationB.id == nodeID) {
                neighbours.add(listOfRoad.locationA.id);
            }
        }

        return neighbours;
    }

    private ArrayList<Integer> nodeOccupancy(int nodeID) {
        Node currentNode = Scenario.listOfNodes[nodeID];
        ArrayList<Integer> units = new ArrayList<Integer>();
        Player currentOpponent;
        int scaledSize;

        // determine the player to be used
        if (currentPlayer.playerID == 0) {
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
                if (currentOpponent.combatUnits.get(i).isFleet) {
                    scaledSize = 3;
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
