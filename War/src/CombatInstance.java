
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ben
 */
public class CombatInstance {
    private ArrayList<CombatUnit> redUnits, blueUnits;
    private ArrayList<Road> redRoad, blueRoad;
    private ArrayList<Node> redEndLocation, blueEndLocation;
    private ArrayList<Node> redFromLocation, blueFromLocation;
    
    public CombatInstance(ArrayList<CombatUnit> redUnits, ArrayList<CombatUnit> blueUnits, 
            ArrayList<Road> redRoad, ArrayList<Road> blueRoad,
            ArrayList<Node> redEndLocation, ArrayList<Node> blueEndLocation,
            ArrayList<Node> redFromLocation, ArrayList<Node> blueFromLocation){
        redUnits = this.redUnits;
        blueUnits = this.blueUnits;
        redRoad = this.redRoad;
        blueRoad = this.blueRoad;
        redEndLocation = this.redEndLocation;
        blueEndLocation = this.blueEndLocation;
        redFromLocation = this.redFromLocation;
        blueFromLocation = this.blueFromLocation;
    }
    
}
