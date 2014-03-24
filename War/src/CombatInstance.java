
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * CombatInstance Class
 *
 * @author Ben Yntema
 *
 * This class outlines the object used for the storage and execution of combat
 * instances. These instances are generated in MapEvent and are passed to Battle
 * for evaluation. Information regarding the units and their movement are
 * stored.
 */
public class CombatInstance {

    private ArrayList<CombatUnit> redUnits, blueUnits; // unit lists
    private ArrayList<Road> redRoad, blueRoad; // roads occupied by the units
    private ArrayList<Node> redEndLocation, blueEndLocation; // intended location for units
    private ArrayList<Node> redFromLocation, blueFromLocation; // previous location for units
    private boolean isNode; // flag for combat type, true if on node and false if on road

    /*
     Method: CombatInstance
     Parameters: none
    
     This is the constructor for the CombatInstance object. Information is gathered
     and stored in the appropriate structures.
     */
    public CombatInstance(ArrayList<CombatUnit> _redUnits, ArrayList<CombatUnit> _blueUnits,
            ArrayList<Road> _redRoad, ArrayList<Road> _blueRoad,
            ArrayList<Node> _redEndLocation, ArrayList<Node> _blueEndLocation,
            ArrayList<Node> _redFromLocation, ArrayList<Node> _blueFromLocation, boolean _isNode) {
        // store the given values in the correct locations
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

    /*
     Method: redUnits
     Parameters: none
    
     Returns redUnits.
     */
    public ArrayList<CombatUnit> redUnits() {
        return redUnits;
    }

    /*
     Method: blueUnits
     Parameters: none
    
     Returns blueUnits.
     */
    public ArrayList<CombatUnit> blueUnits() {
        return blueUnits;
    }

    /*
     Method: redRoad
     Parameters: none
    
     Returns redRoad. 
     */
    public ArrayList<Road> redRoad() {
        return redRoad;
    }

    /*
     Method: blueRoad
     Parameters: none
    
     Returns blueRoad.
     */
    public ArrayList<Road> blueRoad() {
        return blueRoad;
    }

    /*
     Method: redEndLocation
     Parameters: none
    
     Returns redEndLocation.
     */
    public ArrayList<Node> redEndLocation() {
        return redEndLocation;
    }

    /*
     Method: blueEndLocation
     Parameters: none
    
     Returns blueEndLocation.
     */
    public ArrayList<Node> blueEndLocation() {
        return blueEndLocation;
    }

    /*
     Method: redFromLocation
     Parameters: none
    
     Returns redFromLocation.
     */
    public ArrayList<Node> redFromLocation() {
        return redFromLocation;
    }

    /*
     Method: blueFromLocation
     Parameters: none
    
     Returns blueFromLocation.
     */
    public ArrayList<Node> blueFromLocation() {
        return blueFromLocation;
    }

    /*
     Method: isNode
     Parameters: none
    
     Returns isNode.
     */
    public Boolean isNode() {
        return isNode;
    }
}
