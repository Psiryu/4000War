
import java.util.ArrayList;
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

    // Constructor of the rumour system
    public Rumour() {
        // set the necessary parameters
        listOfNodes = Scenario.listOfNodes;
        listOfUnitsRed = new ArrayList<CombatUnit>();
        listOfUnitsBlue = new ArrayList<CombatUnit>();
    }

    // Method to update the rumour awareness of current units
    public void updateUnitRegistry() {
        // set the new unit lists
        listOfUnitsRed = Scenario.redPlayer.combatUnits;
        listOfUnitsBlue = Scenario.bluePlayer.combatUnits;
    }

    public void playerFogValueUpdate() {
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

        randomFog = -100 + (int) (random.nextDouble() * 200);
        fogSum = nodeFogSum + roadFogSum + currentPlayer.politicalPower + randomFog;
    }

    public void reportRumour(int nodeID) {
        ArrayList<Integer> units = nodeOccupancy(nodeID);
        
    }
    
    private ArrayList<Integer> nodeOccupancy (int nodeID){
        Node currentNode = Scenario.listOfNodes[nodeID];
        ArrayList<Integer> units = new ArrayList<Integer>();
        Player currentOpponent;
        
        // determine the player to be used
        if (Scenario.redPlayer.playerID == Global.curPlayer) {
            currentOpponent = Scenario.bluePlayer;
        } else {
            currentOpponent = Scenario.redPlayer;
        }
        
        for (int i = 0; i < currentOpponent.combatUnits.size(); i++){
            if (currentOpponent.combatUnits.get(i).location.id == currentNode.id){
                units.add(currentOpponent.combatUnits.get(i).size);
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
    
     public void ArmiesHere(int[][] armies)
     {
     String sizes = "";
     int i = 0;
     for (int[] armie : armies) {
     if (nodeSelected == armies[i][2])
     sizes += (ConvertSize(armies[i][1], armies[i][3]) + " ");
     i++;
     }
        
     if(sizes.equals(""))
     sizes = "none";
        
     labelInfo5.setText("Your armies here: " + sizes);
        
     //popupMenu.add(null)
     }
    
     public int[][] ObtainArmies(int[][] listArmy) {
     //creates list to use to keep track of armies.
     //first portion of rectangular array represents the differnt armies
     //within the list. Second portion is [id, size, location].  

     if(curPlayer == 0)
     {
     listArmy = new int[Scenario.redPlayer.combatUnits.size()][4];
     for(int i = 0; i < Scenario.redPlayer.combatUnits.size(); i++) {
     listArmy[i][0] = Scenario.redPlayer.combatUnits.get(i).cUnitID;
     listArmy[i][1] = Scenario.redPlayer.combatUnits.get(i).size;
     listArmy[i][2] = Scenario.redPlayer.combatUnits.get(i).GetLocation().id;
     if (Scenario.redPlayer.combatUnits.get(i).isFleet == true)
     listArmy[i][3] = 1;
     else
     listArmy[i][3] = 0;
     }
     } else {
     listArmy = new int[Scenario.bluePlayer.combatUnits.size()][4];
     for(int i = 0; i < Scenario.bluePlayer.combatUnits.size(); i++) {
     listArmy[i][0] = Scenario.bluePlayer.combatUnits.get(i).cUnitID;
     listArmy[i][1] = Scenario.bluePlayer.combatUnits.get(i).size;
     listArmy[i][2] = Scenario.bluePlayer.combatUnits.get(i).GetLocation().id;
     if (Scenario.bluePlayer.combatUnits.get(i).isFleet == true)
     listArmy[i][3] = 1;
     else
     listArmy[i][3] = 0;
     }
     }

     return listArmy;
     }
     */
}
