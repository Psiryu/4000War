
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
    */
}
