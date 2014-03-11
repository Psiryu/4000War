
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
    public static CombatUnit[] listOfUnitsRed; // storage of current red units
    public static CombatUnit[] listOfUnitsBlue; // storage of current blue units
    public static Random random = new Random(); // establish a random variable
    
    // Constructor of the rumour system
    public Rumour(Node[] _listOfNodes, CombatUnit[] _listOfUnitsRed, CombatUnit[] _listOfUnitsBlue) {
        // set the necessary parameters
        listOfNodes = _listOfNodes;
        listOfUnitsRed = _listOfUnitsRed;
        listOfUnitsBlue = _listOfUnitsBlue;
    }

    // Method to update the rumour awareness of current units
    public void updateUnitRegistry(CombatUnit[] _listOfUnitsRed, CombatUnit[] _listOfUnitsBlue) {
        // set the new unit lists
        listOfUnitsRed = _listOfUnitsRed;
        listOfUnitsBlue = _listOfUnitsBlue;
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
