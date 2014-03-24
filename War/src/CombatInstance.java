
import java.util.ArrayList;
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
public class CombatInstance {

    private ArrayList<CombatUnit> redUnits, blueUnits;
    private ArrayList<Road> redRoad, blueRoad;
    private ArrayList<Node> redEndLocation, blueEndLocation;
    private ArrayList<Node> redFromLocation, blueFromLocation;
    private boolean isNode;

    public CombatInstance(ArrayList<CombatUnit> _redUnits, ArrayList<CombatUnit> _blueUnits,
            ArrayList<Road> _redRoad, ArrayList<Road> _blueRoad,
            ArrayList<Node> _redEndLocation, ArrayList<Node> _blueEndLocation,
            ArrayList<Node> _redFromLocation, ArrayList<Node> _blueFromLocation, boolean _isNode) {
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
    
    public ArrayList<CombatUnit> redUnits(){
        return redUnits;
    }
    
    public ArrayList<CombatUnit> blueUnits(){
        return blueUnits;
    }
    
    public ArrayList<Road> redRoad(){
        return redRoad;
    }
    
    public ArrayList<Road> blueRoad(){
        return blueRoad;
    }
    
    public ArrayList<Node> redEndLocation(){
        return redEndLocation;
    } 
    
    public ArrayList<Node> blueEndLocation(){
        return blueEndLocation;
    }
    
    public ArrayList<Node> redFromLocation(){
        return redFromLocation;
    }
    
    public ArrayList<Node> blueFromLocation(){
        return blueFromLocation;
    }
    
    public Boolean isNode(){
        return isNode;
    }
}
