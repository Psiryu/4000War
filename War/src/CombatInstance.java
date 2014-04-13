
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * CombatInstance Class
 *
 * Description: This class outlines the object used for the storage and
 * execution of combat instances. These instances are generated in MapEvent and
 * are passed to Battle for evaluation. Information regarding the units and
 * their movement are stored.
 *
 * Usage: Each CombatInstance object is created in MapEvent.processEvents() when
 * a collision is detected. These objects are used to convey information to the
 * Battle object in MapEvent.simulateCombat()
 *
 * Maintenance notes: Additional parameters may be added as variables when
 * necessary. For each new addition, add a declaration to the constructor and
 * modify existing declaration statements in MapEvent.processEvents() and
 * MapEvent.simulateCombat().
 */
public class CombatInstance {

	private ArrayList<CombatUnit> redUnits, blueUnits; // unit lists for each faction
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
	 Input Parameters: none
	 Output Parameters: ArrayList<CombatUnit> redUnits
    
	 Returns redUnits.
	 */
	public ArrayList<CombatUnit> redUnits() {
		return redUnits;
	}

	/*
	 Method: blueUnits
	 Input Parameters: none
	 Output Parameters: ArrayList<CombatUnit> blueUnits
    
	 Returns blueUnits.
	 */
	public ArrayList<CombatUnit> blueUnits() {
		return blueUnits;
	}

	/*
	 Method: redRoad
	 Input Parameters: none
	 Output Parameters: ArrayList<Road> redRoad
    
	 Returns redRoad. 
	 */
	public ArrayList<Road> redRoad() {
		return redRoad;
	}

	/*
	 Method: blueRoad
	 Input Parameters: none
	 Output Parameters: ArrayList<Road> blueRoad
    
	 Returns blueRoad.
	 */
	public ArrayList<Road> blueRoad() {
		return blueRoad;
	}

	/*
	 Method: redEndLocation
	 Input Parameters: none
	 Output Parameters: ArrayList<Node> redEndLocation
    
	 Returns redEndLocation.
	 */
	public ArrayList<Node> redEndLocation() {
		return redEndLocation;
	}

	/*
	 Method: blueEndLocation
	 Input Parameters: none
	 Output Parameters: ArrayList<Node> blueEndLocation
    
	 Returns blueEndLocation.
	 */
	public ArrayList<Node> blueEndLocation() {
		return blueEndLocation;
	}

	/*
	 Method: redFromLocation
	 Input Parameters: none
	 Output Parameters: ArrayList<Node> redFromLocation
    
	 Returns redFromLocation.
	 */
	public ArrayList<Node> redFromLocation() {
		return redFromLocation;
	}

	/*
	 Method: blueFromLocation
	 Input Parameters: none
	 Output Parameters: ArrayList<Node> blueFromLocation
    
	 Returns blueFromLocation.
	 */
	public ArrayList<Node> blueFromLocation() {
		return blueFromLocation;
	}

	/*
	 Method: isNode
	 Input Parameters: none
	 Output Parameters: Boolean isNode
    
	 Returns isNode.
	 */
	public Boolean isNode() {
		return isNode;
	}
}
