
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
    public ArrayList<CombatUnit> redUnits, blueUnits;
    public ArrayList<Road> redRoad, blueRoad;
    public ArrayList<Node> redEndLocation, blueEndLocation;
    public ArrayList<Node> redFromLocation, blueFromLocation;
    public boolean isNode;
    
    public CombatInstance(ArrayList<CombatUnit> _redUnits, ArrayList<CombatUnit> _blueUnits, 
            ArrayList<Road> _redRoad, ArrayList<Road> _blueRoad,
            ArrayList<Node> _redEndLocation, ArrayList<Node> _blueEndLocation,
            ArrayList<Node> _redFromLocation, ArrayList<Node> _blueFromLocation, boolean _isNode){
        redUnits = _redUnits;
        blueUnits = _blueUnits;
        redRoad = _redRoad;
        blueRoad = _blueRoad;
        redEndLocation = _redEndLocation;
        blueEndLocation = _blueEndLocation;
        redFromLocation = _redFromLocation;
        blueFromLocation = _blueFromLocation;
        isNode = _isNode;
    }    
}
