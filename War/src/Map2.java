//list of library imports required
import java.awt.Font;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.Timer;


/**
 * Map Class
 *
 * Description: This is the source code of the map front end. It controls all
 * ui level interaction (save for battle prompt) - such as movement and player
 * controls - of the map interface.
 * Method "Action" initiates all events that occur when a node is clicked,
 * GameStart initializes the map (primarily graphical elements, such as
 * text for labels or setting the initial node coloring, 
 * ObtainArmies populates a rectangular array with all armies at the selected 
 * node, ArmiesHere generates user text for which armies they control at the 
 * selected node, OpenPopup populates the popup menu that appears when a node
 * is clicked with actions the user can perform, InitializeMovement initializes
 * movement by determining all movable armies and adding them to the popup
 * menu, and calls to MoveTo to actually process movements and end locations.
 * DividingArmies controls all aspects of dividing any army in half, while
 * BeginMergingArmies determines which armies can be merged together - which
 * in turn calls to MergeArmy which determines which armies said army can merge
 * with and processes the request. ConvertSize converts an army's size to plain
 * text for the front end - using single letters. ConvertFullSize outputs
 * the full English words for the army sizes for the front end. ClearMenuInfo
 * clears all text located beneath the map, and ClearPopupMenu clears the
 * popup menu that appears when a node is clicked. SetColours, coupled with
 * SetDefaultColours controls which
 * node colors are to be set for standard node colours, SetCurrentColour
 * alters the selected node to become a unique colour. SetPlayerNodeColour and
 * SetEnemyColours set the player controlled colours and fog of war colours
 * respectively (for armies contained on each node). Lastly, GameOver is
 * called whenever an end-of-game is detected, and wraps up the game and calls
 * to GameOver.java, closing this class in the process.
 *
 * The map controls all UI elements. Nodes are controlled via a
 * primary method named "Action". All nodes simply designate the public
 * variable "nodeSelected" and call to the node method. From Action, it is
 * determined if the player controls any armies on the selected node, or has
 * any fog of war intelligence on the selected location, and triggers all
 * available actions. Action will call for a controlled and enemy check, which
 * in turn triggers node coloration changes, and calls to a method named
 * "OpenPopup", which populates and displays a popup menu. It will determine
 * if any movements, mergers, or divisions can occur, adding only actions
 * that can be performed.
 * 
 * Usage: the Map is initialized from Teams.java. No other calls to Map.java
 * or Map.form are made. The map does call to MapEvent on node actions, Global
 * for global variables (such as a gameover check and a check for who's turn
 * it presently is.
 *
 * Maintenance notes: Node locations will need to be adjusted for new scenarios.
 * Simply move, delete, or add new nodes (copy and paste, while maintaining
 * correct node id locations based on the scenario) on the wysiwyg editor.
 * Array of node id's will need to be adjusted for new total amounts of nodes
 * in the methods "SetPlayerNodeColour," "SetEnemyNodeColours", and
 * "SetDefaultColours".  Simply alter the switch statement within each method
 * to contain the same number of cases as nodes available. For example, as this
 * scenario has 16 nodes, the cases range from 0 to 15. (remember, indexes
 * begin at 0).
 *      IMPORTANT: remember, it is important that node id's and positions on 
 * the map corelate. Node's must be arranged with the same id's and labels as
 * they are listed within Scenario.java for the desired scenario.
 */

public class Map2 extends javax.swing.JFrame {

    //variable for keeping track of the current players' turns;
    public int x, y;
    //variables for if an army is on the selected node, and if it can be split/merged
    public Boolean armyHere = false;
    public Boolean divisableArmy = false;
    public Boolean mergableArmy = false;
    //nodeSelection is used for selecting a node
    public int nodeSelected = 0;
    public Timer timer;
    //public Font("Segoe UI",0,18);
    /**
     * Creates new form Map
     */
    public Map2() {
        
        initComponents();

        //clears off the information panel
        ClearMenuInfo();
        buttonNext.setVisible(false);
        frameFloatingInfo.setVisible(true);

        //calls to a game frontend initializing method
        GameStart();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popupMenu = new javax.swing.JPopupMenu();
        menuItemClose = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        frameFloatingInfo = new javax.swing.JFrame();
        labelTurnCount = new javax.swing.JLabel();
        labelCurPlayer = new javax.swing.JLabel();
        buttonFinishTurn = new javax.swing.JButton();
        buttonMenu = new javax.swing.JToggleButton();
        buttonExit = new javax.swing.JButton();
        labelOpponent = new javax.swing.JLabel();
        labelScenario = new javax.swing.JLabel();
        labelPoliticalPower = new javax.swing.JLabel();
        labelSeason = new javax.swing.JLabel();
        labelTimer = new javax.swing.JLabel();
        list1 = new java.awt.List();
        labelBackdrop = new javax.swing.JLabel();
        menuInfo = new javax.swing.JPanel();
        labelInfo1 = new javax.swing.JLabel();
        labelInfo2 = new javax.swing.JLabel();
        labelInfo3 = new javax.swing.JLabel();
        labelInfo4 = new javax.swing.JLabel();
        labelInfo5 = new javax.swing.JLabel();
        labelInfo6 = new javax.swing.JLabel();
        panelMap = new javax.swing.JPanel();
        nodePlaceholder0 = new javax.swing.JButton();
        nodePlaceholder1 = new javax.swing.JButton();
        nodePlaceholder2 = new javax.swing.JButton();
        nodePlaceholder3 = new javax.swing.JButton();
        nodePlaceholder4 = new javax.swing.JButton();
        nodePlaceholder5 = new javax.swing.JButton();
        nodePlaceholder6 = new javax.swing.JButton();
        nodePlaceholder7 = new javax.swing.JButton();
        nodePlaceholder8 = new javax.swing.JButton();
        nodePlaceholder9 = new javax.swing.JButton();
        nodePlaceholder10 = new javax.swing.JButton();
        nodePlaceholder11 = new javax.swing.JButton();
        nodePlaceholder12 = new javax.swing.JButton();
        nodePlaceholder13 = new javax.swing.JButton();
        nodePlaceholder14 = new javax.swing.JButton();
        nodePlaceholder15 = new javax.swing.JButton();
        jLabel0 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        buttonMapImage = new javax.swing.JButton();
        buttonNext = new javax.swing.JButton();
        buttonBackdrop = new javax.swing.JButton();

        popupMenu.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        popupMenu.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                popupMenuFocusLost(evt);
            }
        });

        menuItemClose.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        menuItemClose.setText("Close Menu");
        menuItemClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemCloseActionPerformed(evt);
            }
        });
        popupMenu.add(menuItemClose);

        jMenu1.setText("jMenu1");
        jMenu1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        jMenuItem1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenuItem1.setText("jMenuItem1");
        jMenu1.add(jMenuItem1);

        jMenuItem2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenuItem2.setText("jMenuItem2");
        jMenu1.add(jMenuItem2);

        popupMenu.add(jMenu1);

        frameFloatingInfo.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        frameFloatingInfo.setBounds(new java.awt.Rectangle(860, 390, 0, 0));
        frameFloatingInfo.setMinimumSize(new java.awt.Dimension(420, 295));
        frameFloatingInfo.setName(""); // NOI18N
        frameFloatingInfo.setResizable(false);
        frameFloatingInfo.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        labelTurnCount.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        labelTurnCount.setText("Turn: 1");
        frameFloatingInfo.getContentPane().add(labelTurnCount, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 80, -1, -1));

        labelCurPlayer.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        labelCurPlayer.setText("text");
        frameFloatingInfo.getContentPane().add(labelCurPlayer, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 80, -1, -1));

        buttonFinishTurn.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        buttonFinishTurn.setText("Finish Turn");
        buttonFinishTurn.setContentAreaFilled(false);
        buttonFinishTurn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonFinishTurnActionPerformed(evt);
            }
        });
        frameFloatingInfo.getContentPane().add(buttonFinishTurn, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 10, -1, 40));

        buttonMenu.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        buttonMenu.setText("Main Menu");
        buttonMenu.setToolTipText(null);
        buttonMenu.setContentAreaFilled(false);
        buttonMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonMenuActionPerformed(evt);
            }
        });
        frameFloatingInfo.getContentPane().add(buttonMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 100, 40));

        buttonExit.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        buttonExit.setText("End Game");
        buttonExit.setContentAreaFilled(false);
        buttonExit.setMaximumSize(new java.awt.Dimension(83, 23));
        buttonExit.setMinimumSize(new java.awt.Dimension(83, 23));
        buttonExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonExitActionPerformed(evt);
            }
        });
        frameFloatingInfo.getContentPane().add(buttonExit, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 10, 100, 40));

        labelOpponent.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        labelOpponent.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelOpponent.setText("text");
        labelOpponent.setName("labelOpponent"); // NOI18N
        frameFloatingInfo.getContentPane().add(labelOpponent, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        labelScenario.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        labelScenario.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelScenario.setText("text");
        frameFloatingInfo.getContentPane().add(labelScenario, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, -1, -1));

        labelPoliticalPower.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        labelPoliticalPower.setText("text");
        frameFloatingInfo.getContentPane().add(labelPoliticalPower, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 100, -1, -1));

        labelSeason.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        labelSeason.setText("text");
        frameFloatingInfo.getContentPane().add(labelSeason, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, -1, -1));

        labelTimer.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        frameFloatingInfo.getContentPane().add(labelTimer, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 100, 110, 10));

        list1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        frameFloatingInfo.getContentPane().add(list1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 380, 130));

        labelBackdrop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/MapScreen-Side-backdrop.png"))); // NOI18N
        labelBackdrop.setToolTipText(null);
        frameFloatingInfo.getContentPane().add(labelBackdrop, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 420, -1));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(204, 255, 255));
        setMinimumSize(new java.awt.Dimension(860, 580));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menuInfo.setMaximumSize(new java.awt.Dimension(700, 70));
        menuInfo.setMinimumSize(new java.awt.Dimension(700, 70));
        menuInfo.setOpaque(false);
        menuInfo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        labelInfo1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelInfo1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelInfo1.setText("city name");
        menuInfo.add(labelInfo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, -1, -1));

        labelInfo2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelInfo2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelInfo2.setText("node type");
        menuInfo.add(labelInfo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 10, -1, -1));

        labelInfo3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelInfo3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelInfo3.setText("supplies");
        menuInfo.add(labelInfo3, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 10, -1, -1));

        labelInfo4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelInfo4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelInfo4.setText("weather");
        menuInfo.add(labelInfo4, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 10, -1, -1));

        labelInfo5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelInfo5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelInfo5.setText("player's armies");
        menuInfo.add(labelInfo5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, -1, -1));

        labelInfo6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        labelInfo6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelInfo6.setText("enemy's armies");
        menuInfo.add(labelInfo6, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 40, -1, -1));

        getContentPane().add(menuInfo, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 480, -1, -1));

        panelMap.setMaximumSize(new java.awt.Dimension(820, 470));
        panelMap.setMinimumSize(new java.awt.Dimension(820, 470));
        panelMap.setOpaque(false);
        panelMap.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        nodePlaceholder0.setBackground(new java.awt.Color(0, 0, 0));
        nodePlaceholder0.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/map-node-town.png"))); // NOI18N
        nodePlaceholder0.setContentAreaFilled(false);
        nodePlaceholder0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nodePlaceholder0ActionPerformed(evt);
            }
        });
        panelMap.add(nodePlaceholder0, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 240, 30, 30));

        nodePlaceholder1.setBackground(new java.awt.Color(0, 0, 0));
        nodePlaceholder1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/map-node-town.png"))); // NOI18N
        nodePlaceholder1.setContentAreaFilled(false);
        nodePlaceholder1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nodePlaceholder1ActionPerformed(evt);
            }
        });
        panelMap.add(nodePlaceholder1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, 30, 30));

        nodePlaceholder2.setBackground(new java.awt.Color(0, 0, 0));
        nodePlaceholder2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/map-node-town.png"))); // NOI18N
        nodePlaceholder2.setContentAreaFilled(false);
        nodePlaceholder2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nodePlaceholder2ActionPerformed(evt);
            }
        });
        panelMap.add(nodePlaceholder2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 60, 30, 30));

        nodePlaceholder3.setBackground(new java.awt.Color(0, 0, 0));
        nodePlaceholder3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/map-node-town.png"))); // NOI18N
        nodePlaceholder3.setContentAreaFilled(false);
        nodePlaceholder3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nodePlaceholder3ActionPerformed(evt);
            }
        });
        panelMap.add(nodePlaceholder3, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 140, 30, 30));

        nodePlaceholder4.setBackground(new java.awt.Color(0, 0, 0));
        nodePlaceholder4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/map-node-town.png"))); // NOI18N
        nodePlaceholder4.setContentAreaFilled(false);
        nodePlaceholder4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nodePlaceholder4ActionPerformed(evt);
            }
        });
        panelMap.add(nodePlaceholder4, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 190, 30, 30));

        nodePlaceholder5.setBackground(new java.awt.Color(0, 0, 0));
        nodePlaceholder5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/map-node-town.png"))); // NOI18N
        nodePlaceholder5.setContentAreaFilled(false);
        nodePlaceholder5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nodePlaceholder5ActionPerformed(evt);
            }
        });
        panelMap.add(nodePlaceholder5, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 350, 30, 30));

        nodePlaceholder6.setBackground(new java.awt.Color(0, 0, 0));
        nodePlaceholder6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/map-node-town.png"))); // NOI18N
        nodePlaceholder6.setContentAreaFilled(false);
        nodePlaceholder6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nodePlaceholder6ActionPerformed(evt);
            }
        });
        panelMap.add(nodePlaceholder6, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 130, 30, 30));

        nodePlaceholder7.setBackground(new java.awt.Color(0, 0, 0));
        nodePlaceholder7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/map-node-town.png"))); // NOI18N
        nodePlaceholder7.setContentAreaFilled(false);
        nodePlaceholder7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nodePlaceholder7ActionPerformed(evt);
            }
        });
        panelMap.add(nodePlaceholder7, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 250, 30, 30));

        nodePlaceholder8.setBackground(new java.awt.Color(0, 0, 0));
        nodePlaceholder8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/map-node-town.png"))); // NOI18N
        nodePlaceholder8.setContentAreaFilled(false);
        nodePlaceholder8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nodePlaceholder8ActionPerformed(evt);
            }
        });
        panelMap.add(nodePlaceholder8, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 130, 30, 30));

        nodePlaceholder9.setBackground(new java.awt.Color(0, 0, 0));
        nodePlaceholder9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/map-node-town.png"))); // NOI18N
        nodePlaceholder9.setContentAreaFilled(false);
        nodePlaceholder9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nodePlaceholder9ActionPerformed(evt);
            }
        });
        panelMap.add(nodePlaceholder9, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 270, 30, 30));

        nodePlaceholder10.setBackground(new java.awt.Color(0, 0, 0));
        nodePlaceholder10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/map-node-town.png"))); // NOI18N
        nodePlaceholder10.setContentAreaFilled(false);
        nodePlaceholder10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nodePlaceholder10ActionPerformed(evt);
            }
        });
        panelMap.add(nodePlaceholder10, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 70, 30, 30));

        nodePlaceholder11.setBackground(new java.awt.Color(0, 0, 0));
        nodePlaceholder11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/map-node-town.png"))); // NOI18N
        nodePlaceholder11.setContentAreaFilled(false);
        nodePlaceholder11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nodePlaceholder11ActionPerformed(evt);
            }
        });
        panelMap.add(nodePlaceholder11, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 190, 30, 30));

        nodePlaceholder12.setBackground(new java.awt.Color(0, 0, 0));
        nodePlaceholder12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/map-node-town.png"))); // NOI18N
        nodePlaceholder12.setContentAreaFilled(false);
        nodePlaceholder12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nodePlaceholder12ActionPerformed(evt);
            }
        });
        panelMap.add(nodePlaceholder12, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 290, 30, 30));

        nodePlaceholder13.setBackground(new java.awt.Color(0, 0, 0));
        nodePlaceholder13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/map-node-town.png"))); // NOI18N
        nodePlaceholder13.setContentAreaFilled(false);
        nodePlaceholder13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nodePlaceholder13ActionPerformed(evt);
            }
        });
        panelMap.add(nodePlaceholder13, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 60, 30, 30));

        nodePlaceholder14.setBackground(new java.awt.Color(0, 0, 0));
        nodePlaceholder14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/map-node-town.png"))); // NOI18N
        nodePlaceholder14.setContentAreaFilled(false);
        nodePlaceholder14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nodePlaceholder14ActionPerformed(evt);
            }
        });
        panelMap.add(nodePlaceholder14, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 170, 30, 30));

        nodePlaceholder15.setBackground(new java.awt.Color(0, 0, 0));
        nodePlaceholder15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/map-node-town.png"))); // NOI18N
        nodePlaceholder15.setContentAreaFilled(false);
        nodePlaceholder15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nodePlaceholder15ActionPerformed(evt);
            }
        });
        panelMap.add(nodePlaceholder15, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 240, 30, 30));

        jLabel0.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel0.setText("Rome");
        panelMap.add(jLabel0, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, -1, -1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Aquileia");
        panelMap.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, -1, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Mursa");
        panelMap.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 40, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Cibalae");
        panelMap.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 120, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Salona");
        panelMap.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 190, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Brundisium");
        panelMap.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 380, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Sirmium");
        panelMap.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 160, -1, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Dyrrachium");
        panelMap.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 290, -1, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Singidunum");
        panelMap.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 110, -1, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Thessalonica");
        panelMap.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 310, -1, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("Ratiaria");
        panelMap.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 50, -1, -1));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("Sardica");
        panelMap.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 170, -1, -1));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setText("Heraclea");
        panelMap.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 310, -1, -1));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setText("Marcianopolis");
        panelMap.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 40, -1, -1));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setText("Adrianople");
        panelMap.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 170, -1, -1));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setText("Constantinople");
        panelMap.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 240, -1, -1));

        buttonMapImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Scenario-1-Map.png"))); // NOI18N
        buttonMapImage.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        buttonMapImage.setContentAreaFilled(false);
        buttonMapImage.setIconTextGap(0);
        buttonMapImage.setMaximumSize(new java.awt.Dimension(800, 450));
        buttonMapImage.setMinimumSize(new java.awt.Dimension(800, 450));
        buttonMapImage.setPreferredSize(new java.awt.Dimension(800, 450));
        buttonMapImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonMapImageActionPerformed(evt);
            }
        });
        panelMap.add(buttonMapImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        getContentPane().add(panelMap, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 810, 470));

        buttonNext.setBackground(new java.awt.Color(255, 255, 255));
        buttonNext.setFont(new java.awt.Font("Times New Roman", 0, 36)); // NOI18N
        buttonNext.setContentAreaFilled(false);
        buttonNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonNextActionPerformed(evt);
            }
        });
        getContentPane().add(buttonNext, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 800, 450));

        buttonBackdrop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/mapscreen-main.png"))); // NOI18N
        buttonBackdrop.setBorderPainted(false);
        buttonBackdrop.setContentAreaFilled(false);
        buttonBackdrop.setMaximumSize(new java.awt.Dimension(860, 560));
        buttonBackdrop.setMinimumSize(new java.awt.Dimension(860, 560));
        buttonBackdrop.setPreferredSize(new java.awt.Dimension(860, 560));
        buttonBackdrop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonBackdropActionPerformed(evt);
            }
        });
        getContentPane().add(buttonBackdrop, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 860, 560));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /*
	 Method: GameStart
	 Input Parameters: none.
    
	 this method applies values that are required at the very start of the
         game for player 1. (essentially "finish turn" button aftereffects
         that need to happen for th every first turn).
	 */
    public final void GameStart() {
        //sets a label that informs you whom you are versing.
        if ((Global.opponent) == true) {
            labelOpponent.setText("Against Player");
        } else {
            labelOpponent.setText("Against AI");
        }

        //sets labels for scenario selected, and that it is player 1's turn
        labelScenario.setText("Scenario: " + Global.intScenario);
        if (Global.curPlayer == 0) {
            labelCurPlayer.setText("Red team's turn");
        } else {
            labelCurPlayer.setText("Blue team's turn");
        }

        //sets label that displays season
        if (Global.season == 0) {
            labelSeason.setText("Season: Winter");
        } else if (Global.season == 1) {
            labelSeason.setText("Season: Spring");
        } else if (Global.season == 2) {
            labelSeason.setText("Season: Summer");
        } else if (Global.season == 3) {
            labelSeason.setText("Season: Autumn");
        }

        //sets label that displays current political power level
        if (Global.curPlayer == 0) {
            labelPoliticalPower.setText("Political power: " + Scenario.redPlayer.politicalPower);
        } else {
            labelPoliticalPower.setText("Political power: " + Scenario.bluePlayer.politicalPower);
        }

        labelTurnCount.setText("Turn: 1/" + Game.maxTurnCount);
        //creates the timer for the match
        ActionListener timerDone = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                //decrements the timer
                Global.timer--;
                //checks if timer has reached 0
                if (Global.timer == 0) {
                    //stops the timer and ends the game
                    timer.stop();
                    Game.IsGameEnd();
                }
                if (Global.intGameOver == 1) {
                    //also checks if game has ended by other means
                    //and stops the timer and calls gameover
                    timer.stop();
                    GameOver();

                } else {
                    //else, it displays teh current time on the sidescreen
                    //minuts is straightforward int/60
                    String minutes = (Global.timer / 60) + "";
                    //seconds is a little more complex
                    String seconds = "";
                    //first it checks if there are less than ten seconds
                    //remaining, per minute or otherwise
                    if ((Global.timer - (Global.timer / 60 * 60)) >= 10) {
                        //sets the seconds to be the int/60 (for minutes)
                        //SUBTRACTED from the int, so only the amount of
                        //seconds in the count remains
                        seconds = (Global.timer - (Global.timer / 60 * 60)) + "";
                    } else {
                        //adds a 0 to the start if less than ten
                        seconds = "0" + (Global.timer - (Global.timer / 60 * 60));
                    }

                    labelTimer.setText(minutes + ":" + seconds);
                }
            }
        };
        //sets up the timer for new timers at game start
        timer = new Timer(1000, timerDone);
        timer.start();

        //sets initial map node colours
        try {
            SetDefaultColours();
        } catch (IOException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            SetEnemyColours();
        } catch (IOException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        }
        SetColours();
    }


    /*
	 Method: Action
	 Input Parameters: none.
    
	 Action will control all node-based actions. Dimmed public because no need
         for hidden values, and allows information to be passed more easily.
         This method sets all front-end changes that need to occur when
         a node is clicked, or calls to the methods that will cause the front-
         end changes to occur. It then calls to have the popup menu created.
	 */
    public void Action() {
        //obtains the x and y coordinates of the mouse
        x = MouseInfo.getPointerInfo().getLocation().x;
        y = MouseInfo.getPointerInfo().getLocation().y; 
        
        //sets node colours
        ClearMenuInfo();
        try {
            SetDefaultColours();
        } catch (IOException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            //sets colours of nodes with current player's armies and fog of war info
            SetEnemyColours();
        } catch (IOException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        }
        SetColours();
        try {
            SetCurrentColour();
        } catch (IOException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        }

        //sets the info labels for: type of node, the current season,
        //and the current political power lvele, respectively
        labelInfo1.setText(Scenario.listOfNodes[nodeSelected].name);

        String nodeType;
        if (Scenario.listOfNodes[nodeSelected].isCapitalBlue) {
            nodeType = "Blue capital";
        } else if (Scenario.listOfNodes[nodeSelected].isCapitalRed) {
            nodeType = "Red capital";
        } else if (Scenario.listOfNodes[nodeSelected].isPort == true) {
            nodeType = "Port town";
        } else if (Scenario.listOfNodes[nodeSelected].isCity == true) {
            nodeType = "City";
        } else {
            nodeType = "Checkpoint";
        }

        labelInfo2.setText("Location type: " + nodeType);

        //displays current weather
        String weather = "";
        if (Scenario.game.getWeather() == 0) {
            weather = "clear";
        } else if (Scenario.game.getWeather() == 1) {
            weather = "light rain";
        } else if (Scenario.game.getWeather() == 2) {
            weather = "heavy rain";
        } else if (Scenario.game.getWeather() == 3) {
            weather = "drought";
        } else if (Scenario.game.getWeather() == 4) {
            weather = "flooding";
        } else if (Scenario.game.getWeather() == 5) {
            weather = "tornado warnings";
        }
        labelInfo4.setText("Weather: " + weather);

        EnemiesHere();

        mergableArmy = false;
        divisableArmy = false;
        armyHere = false;

        //establishes an army array of all player controlled armies
        int[][] armies = null;
        //calls to fill the array
        armies = ObtainArmies(armies);

        //displays if any of your armies are at the selected node
        ArmiesHere(armies);

        //creates the popup menu to be displayed, containing all actions
        //the user can do at the selected node
        OpenPopup(armies);

    }

        /*
	 Method: ObtainArmies
	 Input Parameters: a rectangular array of units controlled.
    
	 This method creates a list to use to keep track of armies.
         First portion of rectangular array represents the differnt armies
         within the list. Second portion is [id, size, location, fleet bool].
	 */
    public int[][] ObtainArmies(int[][] listArmy) {
        int i = 0;

        //first sets player 1's armies (red team), then does the same
        //for player 2 (blue team). Commented first half only, both sets
        //are exactly identical, just "redPlayer" changed to "bluePlayer"
        if (Global.curPlayer == 0) {
            //sets the array to be the size of player's total army count
            listArmy = new int[Scenario.redPlayer.combatUnits.size()][4];
            //loops for each army in the array
            for (CombatUnit army : Scenario.redPlayer.combatUnits) {
                //sets each army's id
                listArmy[i][0] = army.cUnitID;
                //sets each army's size
                listArmy[i][1] = army.size;
                //sets each army's location
                listArmy[i][2] = army.GetLocation().id;
                //sets an int value for the isFleet bool (1 for if true)
                if (army.isFleet == true) {
                    listArmy[i][3] = 1;
                } else {
                    listArmy[i][3] = 0;
                }
                i++;
            }
        } else if (Global.curPlayer == 1) {
            listArmy = new int[Scenario.bluePlayer.combatUnits.size()][4];
            for (CombatUnit army : Scenario.bluePlayer.combatUnits) {
                //sets each army's id
                listArmy[i][0] = army.cUnitID;
                //sets each army's size
                listArmy[i][1] = army.size;
                //sets each army's location
                listArmy[i][2] = army.GetLocation().id;
                //sets an int value for the isFleet bool (1 for if true)
                if (army.isFleet == true) {
                    listArmy[i][3] = 1;
                } else {
                    listArmy[i][3] = 0;
                }
                i++;
            }
        }

        return listArmy;
    }

        /*
	 Method: ArmiesHere
	 Input Parameters: rectangular array containing all controlled units.
    
	 This method determines if the current player controls any units
         on the node selected, displaying the results on the front end
         and setting a public variable to true if any exist.
	 */
    public void ArmiesHere(int[][] armies) {
        //creates a string for user display
        String sizes = "";
        //a counter for if armies can merge (mergable[0] counts small armies,
        //while mergable[1] counts mediums.
        int[] mergable = new int[2];
        mergable[0] = 0;
        mergable[1] = 0;

        //indexer i
        int i = 0;
        for (int[] armie : armies) {
            //checks if current army in array at index is located at this node
            if (nodeSelected == armies[i][2]) {
                //adds army size to user output string and sets "armyHere"
                //to true, for later use. Multiple looped true sets are fine.
                sizes += (ConvertSize(armies[i][1], armies[i][3]) + " ");
                armyHere = true;
                //checks if at least one medium army or higher exists on the
                //selected node, for later use
                if (armies[i][1] > 5) {
                    divisableArmy = true;
                }
                //checks if at least two smalls (that aren't fleets) exist here
                if (armies[i][1] < 6 && armies[i][3] == 0) {
                    mergable[0]++;
                }
                //checks if two or more mediums exist here
                if (armies[i][1] > 5 && armies[i][1] < 11) {
                    mergable[1]++;
                }
                //sets public boolean mergableArmy to true if either values in
                //the array are greater than 1
                if (mergable[0] > 1 || mergable[1] > 1) {
                    mergableArmy = true;
                }
            }
            i++;
        }

        //check for if no armies here, for user output
        if (sizes.equals("")) {
            sizes = "none";
        }

        labelInfo5.setText("Your armies here: " + sizes);

        //check if armies exist here
        if (sizes.equals("none")) {
            labelInfo3.setText("");
        } else //displays the supply level of selected node
        {
            labelInfo3.setText("Supply level: " + Scenario.listOfNodes[nodeSelected].suppliesAvailable);
        }
    }

        /*
	 Method: OpenPopup
	 Input Parameters: rectangular array containing all controlled troops.
    
	 This method will establish which actions the player can perform, and
         adds them to a popup menu if the actions are possible.
	 */
    private void OpenPopup(final int[][] armies) {
        //first clears the popup menu (in case another node was clicked
        //while the menu was still active. Then displays and repopulates it.
        ClearPopupMenu();

        //checks if there are armies on the selected node with armyHere
        if (armyHere.equals(true)) {
            //creates the menu item for movement, as only one army being
            //present is the only requirement for it
            final JMenuItem menuItemMove = new JMenuItem("Movement");
                menuItemMove.setFont(new Font("Segoe UI",Font.PLAIN,18));
                
            //adds the action for when this item is clicked
            menuItemMove.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    //sends the army list to InitializeMovement when clicked
                    InitializeMovement(armies);
                    
                }
            });

            //adds this item to the popup menu
            popupMenu.add(menuItemMove);
        }
        //checks if divisableArmy (bool for if an army of medium or higher
        //exists on selected node) is true
        if (divisableArmy.equals(true)) {
            //creates the menu item for dividing
            final JMenuItem menuItemDivide = new JMenuItem("Divide");
                    menuItemDivide.setFont(new Font("Segoe UI",Font.PLAIN,18));
            //adds the action for when this item is clicked
            menuItemDivide.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    //sends the army list to DividingArmies when clicked
                    DividingArmies(armies);
                }
            });
            popupMenu.add(menuItemDivide);
        }
        //checks if mergableArmy (bool for if two or more mediums or smalls
        //exists on selected node) is true
        if (mergableArmy.equals(true)) {
            //creates the menu item for merging
            final JMenuItem menuItemMerge = new JMenuItem("Merge");
                menuItemMerge.setFont(new Font("Segoe UI",Font.PLAIN,18));
            //adds the action for when this item is clicked
            menuItemMerge.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    //sends the army list to DividingArmies when clicked
                    BeginMergingArmies(armies);
                }
            });
            popupMenu.add(menuItemMerge);
        }

        //ensures gui is up to date and then displays it at the selected node
        popupMenu.setLocation(x, y);
        popupMenu.updateUI();
        popupMenu.setVisible(true);

    }

        /*
	 Method: InitializeMovement
	 Input Parameters: rectangular array of all units controlled.
    
	 This method will determine which army units the user controls
         in the present node that can be moved and adds them to the popup menu.
	 */
    private void InitializeMovement(final int[][] armies) {
        //clears menu and readds new items (armies at the node)
        ClearPopupMenu();

        //first, it re checks which armies are at this node
        //then it adds a new menu item for each individual army.
        //it loops for every item in the array.
        int i = 0;
        for (int[] armie : armies) {
            if (nodeSelected == armies[i][2]) {
                //obtains army size, because the method call being
                //within the next line after it caused issues
                final String armySize = ConvertFullSize(armies[i][1], armies[i][3]);

                //creates a new array item to send out, since nested method
                //can't use the indexing for the array.
                final int[][] armyToMove = new int[1][4];
                armyToMove[0] = armies[i];
                armyToMove[0][0] = armies[i][0];
                armyToMove[0][1] = armies[i][1];
                armyToMove[0][2] = armies[i][2];
                armyToMove[0][3] = armies[i][3];

                //creates the item for this army unit on this location.
                //The action is to send current armyToMove to MoveTo method.
                JMenuItem menuItemMove = new JMenuItem(armySize);
                    menuItemMove.setFont(new Font("Segoe UI",Font.PLAIN,18));
                //sets the action listener to call to MoveTo, sending the desired army
                menuItemMove.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        MoveTo(armyToMove);
                    }
                });
                //finally, adds this item to the popup menu.
                popupMenu.add(menuItemMove);
            }
            //increments so the loop can procede with proper indexing.
            i++;
        }
        popupMenu.setLocation(x, y);
        popupMenu.updateUI();
        popupMenu.setVisible(true);

    }

        /*
	 Method: MoveTo
	 Input Parameters: rectangular array of an army unit to move.
    
	 This method will determine where the input army unit can move to,
         populating the results into the popup menu.
	 */
    private void MoveTo(final int[][] army) {
        ClearPopupMenu();
        Boolean returnSet = false;

        //loops for every road in the list of roads in Scenario
        for (Road roads : Scenario.listOfRoads) {
            //checks if current locaion in array at index is the selected city
            //first it checks if locationA on the road is the current node,
            //then it will do the same commands for if it is locationB
            if (roads.locationA.id == army[0][2]) {
                if (roads.capacity >= army[0][1]) {
                    //adds locationB name to a final string
                    final String movingTo = roads.locationB.name;
                    //creates the menu item for this road
                    JMenuItem menuItemMove = new JMenuItem(movingTo);
                        menuItemMove.setFont(new Font("Segoe UI",Font.PLAIN,18));
                    final Road roads2 = roads;
                    final String listText = (ConvertFullSize(army[0][1], army[0][3])
                            + "army unit will move from " + roads.locationA.name + " to "
                            + roads.locationB.name);
                    //creates an action listener that calles to MapEvent, to
                    //add the movement to the queue. Then adds the item log
                    //to the map sidescreen textfield.
                    menuItemMove.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent event) {
                            MapEvent.addMovement(army[0][0], roads2, roads2.locationB.id);
                            list1.add(listText);

                            //try catch for setting default node colours
                            try {
                                SetDefaultColours();
                            } catch (IOException ex) {
                                Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            try {
                                //sets colours of nodes with current player's armies
                                SetEnemyColours();
                            } catch (IOException ex) {
                                Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            SetColours();
                            //clears the popup menu
                            ClearPopupMenu();
                        }
                    });
                    //finally, adds this item to the popup menu.
                    popupMenu.add(menuItemMove);
                //next if statement checks for fleet-only pathways
                } else if (roads.capacity == 0 && army[0][3] == 1) {
                    //ensures the season is not winter
                    if (Global.season != 0) {

                        //creates the movement for a fleet to travel this road alone
                        final String movingTo = roads.locationB.name;
                        //creates the menu item for this road
                        JMenuItem menuItemMove = new JMenuItem(movingTo);
                            menuItemMove.setFont(new Font("Segoe UI",Font.PLAIN,18));
                        //sets variables declared final
                        final Road roads2 = roads;
                        final String listText = (ConvertFullSize(army[0][1], army[0][3])
                                + "army unit will move from " + roads.locationA.name + " to "
                                + roads.locationB.name);
                        //creates an action listener that calles to MapEvent, to
                        //add the movement to the queue. Then adds the item log
                         //to the map sidescreen textfield.
                        menuItemMove.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                MapEvent.addMovement(army[0][0], roads2, roads2.locationB.id);
                                list1.add(listText);

                                //try catch for setting default node colours
                                try {
                                    SetDefaultColours();
                                } catch (IOException ex) {
                                    Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                try {
                                    //sets colours of nodes with current player's armies
                                    SetEnemyColours();
                                } catch (IOException ex) {
                                    Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                SetColours();

                                ClearPopupMenu();
                            }
                        });
                        //finally, adds this item to the popup menu.
                        popupMenu.add(menuItemMove);

                        //creates an array of all armies to check if the fleet
                        //can ferry a small army
                        int[][] allArmies = null;
                        allArmies = ObtainArmies(allArmies);
                        for (final int[] selectedArmy : allArmies) {
                            //if army is at node, is small, and is not fleet
                            if (selectedArmy[2] == nodeSelected && selectedArmy[1] == 5
                                    && selectedArmy[3] == 0) {
                                //adds locationB name to a final string
                                String movingToFerry = roads.locationB.name;
                                //creates the menu item for this road
                                JMenuItem menuItemMove2 = new JMenuItem("Ferry small army to " + movingToFerry);
                                    menuItemMove2.setFont(new Font("Segoe UI",Font.PLAIN,18));
                                final Road roads3 = roads;
                                final String listText2 = (ConvertFullSize(army[0][1], army[0][3])
                                        + "army unit will ferry a small army from " + roads.locationA.name + " to "
                                        + roads.locationB.name);
                                //creates an action listener that calles to MapEvent, to
                                //add the ferrying movement to the queue. Then adds the item log
                                //to the map sidescreen textfield.
                                menuItemMove2.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent event) {
                                        MapEvent.addMovement(army[0][0], roads3, roads3.locationB.id);
                                        MapEvent.addMovement(selectedArmy[0], roads3, roads3.locationB.id);
                                        list1.add(listText2);

                                        //try catch for setting default node colours
                                        try {
                                            SetDefaultColours();
                                        } catch (IOException ex) {
                                            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        try {
                                            //sets colours of nodes with current player's armies
                                            SetEnemyColours();
                                        } catch (IOException ex) {
                                            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        SetColours();
                                        ClearPopupMenu();
                                    }
                                });
                                //finally, adds this item to the popup menu.
                                popupMenu.add(menuItemMove2);

                            }
                        }

                    }
                }
                //else it checks if location B of this road is the selected node
            } else if (roads.locationB.id == army[0][2]) {
                if (roads.capacity >= army[0][1]) {
                    //adds locationB name to a final string
                    final String movingTo = roads.locationA.name;
                    //creates the menu item for this road
                    JMenuItem menuItemMove = new JMenuItem(movingTo);
                        menuItemMove.setFont(new Font("Segoe UI",Font.PLAIN,18));
                    final Road roads2 = roads;
                    final String listText = (ConvertFullSize(army[0][1], army[0][3])
                            + "army unit will move from " + roads.locationB.name + " to "
                            + roads.locationA.name);
                    //creates an action listener that calles to MapEvent, to
                    //add the movement to the queue. Then adds the item log
                    //to the map sidescreen textfield.
                    menuItemMove.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent event) {
                            MapEvent.addMovement(army[0][0], roads2, roads2.locationA.id);
                            list1.add(listText);

                            //try catch for setting default node colours
                            try {
                                SetDefaultColours();
                            } catch (IOException ex) {
                                Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            try {
                                //sets colours of nodes with current player's armies
                                SetEnemyColours();
                            } catch (IOException ex) {
                                Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            SetColours();

                            ClearPopupMenu();
                        }
                    });
                    //finally, adds this item to the popup menu.
                    popupMenu.add(menuItemMove);
                    //checks if this is a naval-only road
                } else if (roads.capacity == 0 && army[0][3] == 1) {
                    //ensures the season is not winter
                    if (Global.season != 0) {
                        //creates the movement for a fleet to travel this road alone
                        final String movingTo = roads.locationA.name;
                        //creates the menu item for this road
                        JMenuItem menuItemMove = new JMenuItem(movingTo);
                            menuItemMove.setFont(new Font("Segoe UI",Font.PLAIN,18));
                        final Road roads2 = roads;
                        final String listText = (ConvertFullSize(army[0][1], army[0][3])
                                + "army unit will move from " + roads.locationB.name + " to "
                                + roads.locationA.name);
                        //creates an action listener that calles to MapEvent, to
                    //add the movement to the queue. Then adds the item log
                    //to the map sidescreen textfield.
                        menuItemMove.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent event) {
                                MapEvent.addMovement(army[0][0], roads2, roads2.locationA.id);
                                list1.add(listText);

                                //try catch for setting default node colours
                                try {
                                    SetDefaultColours();
                                } catch (IOException ex) {
                                    Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                try {
                                    //sets colours of nodes with current player's armies
                                    SetEnemyColours();
                                } catch (IOException ex) {
                                    Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                SetColours();

                                ClearPopupMenu();
                            }
                        });
                        //finally, adds this item to the popup menu.
                        popupMenu.add(menuItemMove);

                        //creates an array of all armies to check if the fleet
                        //can ferry a small army
                        int[][] allArmies = null;
                        allArmies = ObtainArmies(allArmies);
                        for (final int[] selectedArmy : allArmies) {
                            //if army is at node, is small, and is not fleet
                            if (selectedArmy[2] == nodeSelected && selectedArmy[1] == 5
                                    && selectedArmy[3] == 0) {
                                //adds locationB name to a final string
                                final String movingTo2 = roads.locationA.name;
                                //creates the menu item for this road
                                JMenuItem menuItemMove2 = new JMenuItem("Ferry small army to " + movingTo2);
                                    menuItemMove2.setFont(new Font("Segoe UI",Font.PLAIN,18));
                                final Road roads22 = roads;
                                final String listText2 = (ConvertFullSize(army[0][1], army[0][3])
                                        + "army unit will ferry a small army from " + roads.locationB.name + " to "
                                        + roads.locationA.name);
                                //creates an action listener that calles to MapEvent, to
                                 //add the movement to the queue. Then adds the item log
                                //to the map sidescreen textfield.
                                menuItemMove2.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent event) {
                                        MapEvent.addMovement(army[0][0], roads22, roads22.locationA.id);
                                        MapEvent.addMovement(selectedArmy[0], roads22, roads22.locationA.id);
                                        list1.add(listText2);

                                        //try catch for setting default node colours
                                        try {
                                            SetDefaultColours();
                                        } catch (IOException ex) {
                                            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        try {
                                            //sets colours of nodes with current player's armies
                                            SetEnemyColours();
                                        } catch (IOException ex) {
                                            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        SetColours();
                                        ClearPopupMenu();
                                    }
                                });
                                //finally, adds this item to the popup menu.
                                popupMenu.add(menuItemMove2);

                            }
                        }

                    }
                }
            } //now it creates the option to have an army maintain position
            if (roads.locationA.id == army[0][2] && returnSet == false) {
                //returnSet becomes true, to establish that this list item
                //has already been created and added to the popup menu
                returnSet = true;
                //for keeping an army at the current node (to cancel a move)
                final String movingTo = roads.locationA.name;
                //creates the menu item for this road
                JMenuItem menuItemMove = new JMenuItem("Stay at " + movingTo);
                    menuItemMove.setFont(new Font("Segoe UI",Font.PLAIN,18));
                final Road roads2 = roads;
                final String listText = (ConvertFullSize(army[0][1], army[0][3])
                        + "army unit will remain at " + roads.locationA.name);
                //creates an action listener that calles to MapEvent, to
                //add the movement to the queue. Then adds the item log
                //to the map sidescreen textfield.
                menuItemMove.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        MapEvent.addMovement(army[0][0], roads2, roads2.locationA.id);
                        list1.add(listText);

                        //try catch for setting default node colours
                        try {
                            SetDefaultColours();
                        } catch (IOException ex) {
                            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        try {
                            //sets colours of nodes with current player's armies
                            SetEnemyColours();
                        } catch (IOException ex) {
                            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        SetColours();

                        ClearPopupMenu();
                    }
                });
                //finally, adds this item to the popup menu.
                popupMenu.add(menuItemMove);
            //now it checks if location B of this road is the selected node
            } else if (roads.locationB.id == army[0][2] && returnSet == false) {
                returnSet = true;
                //for keeping an army at the current node (to cancel a move)
                final String movingTo = "Stay at " + roads.locationB.name;
                //creates the menu item for this road
                JMenuItem menuItemMove = new JMenuItem(movingTo);
                    menuItemMove.setFont(new Font("Segoe UI",Font.PLAIN,18));
                final Road roads2 = roads;
                final String listText = (ConvertFullSize(army[0][1], army[0][3])
                        + "army unit will remain at " + roads.locationB.name);
                menuItemMove.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        MapEvent.addMovement(army[0][0], roads2, roads2.locationB.id);
                        list1.add(listText);

                        //try catch for setting default node colours
                        try {
                            SetDefaultColours();
                        } catch (IOException ex) {
                            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        try {
                            //sets colours of nodes with current player's armies
                            SetEnemyColours();
                        } catch (IOException ex) {
                            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        SetColours();

                        ClearPopupMenu();
                    }
                });
                //finally, adds this item to the popup menu.
                popupMenu.add(menuItemMove);
            }
        }
        //adds the menu item for canceling movement

        //re-sets the popupmenu as updated and visible
        popupMenu.setLocation(x, y);
        popupMenu.updateUI();
        popupMenu.setVisible(true);

    }

      /*
	 Method: DividingArmies
	 Input Parameters: rectangular array of all army units.
    
	 This method will determine which army units are at the selected
         node and are capable of being divided, populating the results
         into the popup menu.
	 */
    private void DividingArmies(final int[][] army) {
        //ensures popupmenu has been cleared
        ClearPopupMenu();
        //indexer
        int i = 0;
        //loops for every army in the list
        for (int[] army2 : army) {
            //checks if army on loop index is stationed on selected node
            if (army[i][2] == nodeSelected) {
                //checks to make sure the army is at least an M size
                if (army[i][1] > 5) {
                    //creates the menu item for this army to divide
                    JMenuItem menuItemMove = new JMenuItem(ConvertFullSize(army[i][1], army[i][3]));
                        menuItemMove.setFont(new Font("Segoe UI",Font.PLAIN,18));
                    final int i2 = i;
                    final String listItem = (ConvertFullSize(army[i][1], army[i][3]))
                            + "army at " + (Scenario.listOfNodes[nodeSelected].name)
                            + " has divided";
                    menuItemMove.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent event) {
                            //temporary actions. will change to send to Temp once Temp is done.
                            //labelInfo6.setText(army[0][1] + " is dividing");
                            MapEvent.divideUnit(army[i2][0]);
                            //adds event to list log
                            list1.add(listItem);
                            //try catch for setting default node colours
                            try {
                                SetDefaultColours();
                            } catch (IOException ex) {
                                Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            try {
                                //sets colours of nodes with current player's armies
                                SetEnemyColours();
                            } catch (IOException ex) {
                                Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            SetColours();

                            //resets the info panel text for which armies are here
                            //establishes an army array of all player controlled armies
                            int[][] armies = null;
                            //calls to fill the array
                            armies = ObtainArmies(armies);

                            //displays if any of your armies are at the selected node
                            ArmiesHere(armies);

                            ClearPopupMenu();
                        }
                    });
                    //finally, adds this item to the popup menu.
                    popupMenu.add(menuItemMove);
                }

            }
            i++;
        }

        //re-sets the popupmenu as updated and visible
        popupMenu.setLocation(x, y);
        popupMenu.updateUI();
        popupMenu.setVisible(true);

    }

      /*
	 Method: BeginMergingArmies
	 Input Parameters: rectangular array of all army units.
    
	 This method will determine which army units can be merged together,
         adding them to a popup menu.
	 */
    private void BeginMergingArmies(final int[][] armies) {
        ClearPopupMenu();
        int isFleet = 0;
        //first, it re checks which armies are at this node
        //then it adds a new menu item for each medium or small army so long
        //as there are at least two of that size. x is a secondary indexer.
        //Initial if statement to determine who's turn it is.
        if (Global.curPlayer == 0) {
            //for each red army, if curPlayer is 0 (red)
            for (CombatUnit army : Scenario.redPlayer.combatUnits) {
                //determines if the army unit is at the selected node
                if (army.location.id == nodeSelected) {
                    //creates a second loop of all armies, to find a match
                    for (CombatUnit army2 : Scenario.redPlayer.combatUnits) {
                        //determines if the size ranges match and are smaller than large
                        if (army2.size < 11 && army2.size == army.size) {
                            //ensures armies are on the same location, and not the same unit
                            if (army2.location.id == nodeSelected && army2.cUnitID != army.cUnitID) {
                                //ensures fleets cannot be merged
                                if (army.isFleet == true) {
                                    isFleet = 1;
                                } else {
                                    isFleet = 0;
                                }
                                if (isFleet == 0) {
                                    //creates the menu item for this army to merge
                                    final int indexer = army.cUnitID;
                                    final int size = army.size;

                                    JMenuItem menuItemMerged = new JMenuItem(ConvertFullSize(army.size, isFleet));
                                        menuItemMerged.setFont(new Font("Segoe UI",Font.PLAIN,18));
                                    menuItemMerged.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent event) {
                                            //sends current array item to MergeArmy method
                                            MergeArmy(indexer, size);
                                        }
                                    });
                                    //finally, adds this item to the popup menu.
                                    popupMenu.add(menuItemMerged);
                                    break;
                                }

                            }
                        }
                    }
                }
            }
        //all actions are the same as previous if statement, but swapped to be
            //for blue team if curPlayer is 1 (blue)
        } else if (Global.curPlayer == 1) {
            for (CombatUnit army : Scenario.bluePlayer.combatUnits) {
                //if(army.location.id == nodeSelected) {
                for (CombatUnit army2 : Scenario.bluePlayer.combatUnits) {
                    if (army2.size < 11 && army2.size == army.size) {
                        if (army2.location.id == nodeSelected && army2.cUnitID != army.cUnitID) {
                            //ensures fleets cannot be merged
                            if (army.isFleet == true) {
                                isFleet = 1;
                            } else {
                                isFleet = 0;
                            }
                            if (isFleet == 0) {
                                //creates the menu item for this army to merge
                                final int indexer = army.cUnitID;
                                final int size = army.size;

                                JMenuItem menuItemMerged = new JMenuItem(ConvertFullSize(army.size, isFleet));
                                    menuItemMerged.setFont(new Font("Segoe UI",Font.PLAIN,18));
                                menuItemMerged.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent event) {
                                        //sends current array item to MergeArmy method
                                        MergeArmy(indexer, size);
                                    }
                                });
                                //finally, adds this item to the popup menu.
                                popupMenu.add(menuItemMerged);
                                break;
                            }
                        }
                    }
                }
            }
            //}
        }

        popupMenu.setLocation(x, y);
        popupMenu.updateUI();
        popupMenu.setVisible(true);

    }

      /*
	 Method: MoveTo
	 Input Parameters: id of army to merge and its size.
    
	 This method will determine which army units the selected army unit
         can merge with, adding the results to a popup menu.
	 */
    private void MergeArmy(int indexer2, int size) {
        //clears the popup menu
        ClearPopupMenu();

        //check to ensure fleets are not attempting to merge
        int isFleet = 0;
        //performs actions for if curPlayer is 0 (red team's turn)
        if (Global.curPlayer == 0) {
            //for each army red player controls)
            for (final CombatUnit army : Scenario.redPlayer.combatUnits) {
                //if the army unit is on the selected node
                if (army.location.id == nodeSelected) {
                    //ensures sizes are the same and not the same unit id
                    if (army.cUnitID != indexer2 && army.size == size) {
                        //double checks the second army is not a fleet
                        if (army.isFleet == true) {
                            isFleet = 1;
                        } else {
                            isFleet = 0;
                        }
                        //if not fleet, create the menu item for merging
                        if (isFleet == 0) {
                            //creates the menu item for this army to merge
                            final int indexer = indexer2;
                            final String listItem = (ConvertFullSize(army.size, isFleet))
                                    + "armies at " + (Scenario.listOfNodes[nodeSelected].name)
                                    + " have merged";

                            JMenuItem menuItemMerge = new JMenuItem(ConvertFullSize(army.size, isFleet));
                                menuItemMerge.setFont(new Font("Segoe UI",Font.PLAIN,18));
                            menuItemMerge.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent event) {
                                    //sends current array item to MergeArmy method
                                    MapEvent.mergeUnits(indexer, army.cUnitID);

                                    list1.add(listItem);

                                    try {
                                        SetDefaultColours();
                                    } catch (IOException ex) {
                                        Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    try {
                                        //sets colours of nodes with current player's armies
                                        SetEnemyColours();
                                    } catch (IOException ex) {
                                        Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    SetColours();

                                    //establishes an army array of all player controlled armies
                                    int[][] armies = null;
                                    //calls to fill the array
                                    armies = ObtainArmies(armies);

                                    //displays if any of your armies are at the selected node
                                    ArmiesHere(armies);

                                    ClearPopupMenu();
                                }
                            });
                            //finally, adds this item to the popup menu.
                            popupMenu.add(menuItemMerge);
                        }
                    }
                }
            }
            //the same actions as the initial part of this if statement, but for blue team
        } else if (Global.curPlayer == 1) {
            for (final CombatUnit army : Scenario.bluePlayer.combatUnits) {
                if (army.location.id == nodeSelected) {
                    if (army.cUnitID != indexer2 && army.size == size) {
                        //creates the menu item for this army to merge
                        if (army.isFleet == true) {
                            isFleet = 1;
                        } else {
                            isFleet = 0;
                        }
                        if (isFleet == 0) {
                            //creates the menu item for this army to merge
                            final int indexer = indexer2;
                            final String listItem = (ConvertFullSize(army.size, isFleet))
                                    + "armies at " + (Scenario.listOfNodes[nodeSelected].name)
                                    + " have merged";

                            JMenuItem menuItemMerge = new JMenuItem(ConvertFullSize(army.size, isFleet));
                                menuItemMerge.setFont(new Font("Segoe UI",Font.PLAIN,18));
                            menuItemMerge.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent event) {
                                    //sends current array item to MergeArmy method
                                    MapEvent.mergeUnits(indexer, army.cUnitID);

                                    list1.add(listItem);

                                    try {
                                        SetDefaultColours();
                                    } catch (IOException ex) {
                                        Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    try {
                                        //sets colours of nodes with current player's armies
                                        SetEnemyColours();
                                    } catch (IOException ex) {
                                        Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    SetColours();

                                    //establishes an army array of all player controlled armies
                                    int[][] armies = null;
                                    //calls to fill the array
                                    armies = ObtainArmies(armies);

                                    //displays if any of your armies are at the selected node
                                    ArmiesHere(armies);

                                    ClearPopupMenu();
                                }
                            });
                            //finally, adds this item to the popup menu.
                            popupMenu.add(menuItemMerge);
                        }
                    }
                }
            }
        }

        popupMenu.setLocation(x, y);
        popupMenu.updateUI();
        popupMenu.setVisible(true);
    }

      /*
	 Method: ConvertSize
	 Input Parameters: size of the army and an int to check if it's a fleet
    
	 This method will output a single English letter based on the size of
         the army unit inputted.
	 */
    private String ConvertSize(int armieSize, int isFleet) {
        //creates a string for output
        String size;
        //checks the size for which letter size it is associated with
        if (isFleet == 1) //if it's a fleet, size is always "F" for "Fleet"
        {
            size = "F ";
        } else {
            if (armieSize > 10) //if it's in the "Large" range it gets an "L"
            {
                size = "L ";
            } else if (armieSize > 5) //if in "Medium" size range, it gets an "M"
            {
                size = "M ";
            } else //lastly, for anything under size 6, it gets an "S" for "Small"
            {
                size = "S ";
            }
        }
        return size;
    }

    /*
	 Method: ConvertFullSize
	 Input Parameters: size of the army and an int to check if it's a fleet
    
	 This method will output a full English word based on the size of
         the army unit inputted.
	 */
    private String ConvertFullSize(int armieSize, int isFleet) {
        //creates a string for output
        String size;
        //checks the size for which letter size it is associated with
        if (isFleet == 1) //if it's a fleet, size is always "F" for "Fleet"
        {
            size = "Fleet ";
        } else {
            if (armieSize > 10) //if it's in the "Large" range it gets an "L"
            {
                size = "Large ";
            } else if (armieSize > 5) //if in "Medium" size range, it gets an "M"
            {
                size = "Medium ";
            } else //lastly, for anything under size 6, it gets an "S" for "Small"
            {
                size = "Small ";
            }
        }
        return size;
    }

    /*
	 Method: ClearMenuInfo
	 Input Parameters: none.
    
	 This method clears all of the visible information presented to
         the player, to end their turn and ensure the next player is
         not given any of their information. 
	 */
    private void ClearMenuInfo() {
        //empties the text in the info panel
        labelInfo1.setText("");
        labelInfo2.setText("");
        labelInfo3.setText("");
        labelInfo4.setText("");
        labelInfo5.setText("");
        labelInfo6.setText("");
    }

        /*
	 Method: ClearPopupMenu
	 Input Parameters: none.
    
	 This method clears the popup menu and hides it from view
	 */
    public void ClearPopupMenu() {
        //empties and hides the popup menu.
        popupMenu.setVisible(false);
        popupMenu.removeAll();
        popupMenu.add(menuItemClose);
    }

        /*
	 Method: SetDefaultColours
	 Input Parameters: none.
    
	 This method will set the node colours of all nodes based on the node
         type (location are dark grey, port is light blue, checkpoint is beige)
	 */
    public void SetDefaultColours() throws IOException {
        //this method resets the node colours to default
        Image img;
        //indexer i, which is used as the curent node id in the loop
        int i = 0;
        for (Node nodes : Scenario.listOfNodes) {
            //creates the base image variable

            //checks which player's turn it is
            if (nodes.isPort == true) {
                //sets the image to be a port town node
                img = ImageIO.read(getClass().getResource("Images/map-node-port.png"));
            } else if (nodes.isCity == true) {
                //sets image for cities
                img = ImageIO.read(getClass().getResource("Images/map-node-town.png"));
            } else {
                //else catches the remainder, which are checkpoint locations
                img = ImageIO.read(getClass().getResource("Images/map-node-road.png"));
            }

            //casts the image to an icon
            ImageIcon img2 = new ImageIcon(img);

            //switch case for current indexed button object to change
            switch (i) {
                case 0:
                    nodePlaceholder0.setIcon(img2);
                    break;
                case 1:
                    nodePlaceholder1.setIcon(img2);
                    break;
                case 2:
                    nodePlaceholder2.setIcon(img2);
                    break;
                case 3:
                    nodePlaceholder3.setIcon(img2);
                    break;
                case 4:
                    nodePlaceholder4.setIcon(img2);
                    break;
                case 5:
                    nodePlaceholder5.setIcon(img2);
                    break;
                case 6:
                    nodePlaceholder6.setIcon(img2);
                    break;
                case 7:
                    nodePlaceholder7.setIcon(img2);
                    break;
                case 8:
                    nodePlaceholder8.setIcon(img2);
                    break;
                case 9:
                    nodePlaceholder9.setIcon(img2);
                    break;
                case 10:
                    nodePlaceholder10.setIcon(img2);
                    break;
                case 11:
                    nodePlaceholder11.setIcon(img2);
                    break;
                case 12:
                    nodePlaceholder12.setIcon(img2);
                    break;
                case 13:
                    nodePlaceholder13.setIcon(img2);
                    break;
                case 14:
                    nodePlaceholder14.setIcon(img2);
                    break;
                case 15:
                    nodePlaceholder15.setIcon(img2);
                    break;
            }

            //increments i at end of loop
            i++;
        }
    }

    /*
	 Method: SetColours
	 Input Parameters: none.
    
	 This method will determine which nodes the player controls army units
         on, and gets the node id's of the locations to change to the player's.
         It sends the node id's to change to SetPlayerNodeColour one at a time.
	 */
    public void SetColours() {
        //this method sets the node colours at the start of each turn.

        //establishes an army array of all player controlled armies
        int[][] armies = null;
        //calls to fill the array
        armies = ObtainArmies(armies);

        //indexer i, which is used as the curent node id in the loop, 
        //followed by a loop for every node (indexer i2) in the list of nodes
        int i = 0;
        for (Node nodes : Scenario.listOfNodes) {

            int i2 = 0;
            //nested loop for going through each army in the army array
            for (int[] armie : armies) {
                //checks if current army in array at index is located at this node
                if (i == armies[i2][2]) {
                    try {
                        SetPlayerNodeColour(i);
                    } catch (IOException ex) {
                        Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                i2++;
            }

            i++;
        }

    }

    /*
	 Method: SetPlayerNodeColour
	 Input Parameters: id of node to change
    
	 receives a node id and changes the node to be the colour of the current player
	 */
    private void SetPlayerNodeColour(int indexer) throws IOException {
        //creates the base image variable
        Image img;
        //checks which player's turn it is
        if (Global.curPlayer == 0) {
            //sets the image to be red or blue, depending on the player
            img = ImageIO.read(getClass().getResource("Images/map-node-red.png"));
            //casts the image to an icon
            ImageIcon img2 = new ImageIcon(img);
            //switch to change the node at index selected
            switch (indexer) {
                case 0:
                    nodePlaceholder0.setIcon(img2);
                    break;
                case 1:
                    nodePlaceholder1.setIcon(img2);
                    break;
                case 2:
                    nodePlaceholder2.setIcon(img2);
                    break;
                case 3:
                    nodePlaceholder3.setIcon(img2);
                    break;
                case 4:
                    nodePlaceholder4.setIcon(img2);
                    break;
                case 5:
                    nodePlaceholder5.setIcon(img2);
                    break;
                case 6:
                    nodePlaceholder6.setIcon(img2);
                    break;
                case 7:
                    nodePlaceholder7.setIcon(img2);
                    break;
                case 8:
                    nodePlaceholder8.setIcon(img2);
                    break;
                case 9:
                    nodePlaceholder9.setIcon(img2);
                    break;
                case 10:
                    nodePlaceholder10.setIcon(img2);
                    break;
                case 11:
                    nodePlaceholder11.setIcon(img2);
                    break;
                case 12:
                    nodePlaceholder12.setIcon(img2);
                    break;
                case 13:
                    nodePlaceholder13.setIcon(img2);
                    break;
                case 14:
                    nodePlaceholder14.setIcon(img2);
                    break;
                case 15:
                    nodePlaceholder15.setIcon(img2);
                    break;
            }
        } else if (Global.curPlayer == 1) {
            //exact same as above but with blue team png called to
            img = ImageIO.read(getClass().getResource("Images/map-node-blue.png"));
            ImageIcon img2 = new ImageIcon(img);
            switch (indexer) {
                case 0:
                    nodePlaceholder0.setIcon(img2);
                    break;
                case 1:
                    nodePlaceholder1.setIcon(img2);
                    break;
                case 2:
                    nodePlaceholder2.setIcon(img2);
                    break;
                case 3:
                    nodePlaceholder3.setIcon(img2);
                    break;
                case 4:
                    nodePlaceholder4.setIcon(img2);
                    break;
                case 5:
                    nodePlaceholder5.setIcon(img2);
                    break;
                case 6:
                    nodePlaceholder6.setIcon(img2);
                    break;
                case 7:
                    nodePlaceholder7.setIcon(img2);
                    break;
                case 8:
                    nodePlaceholder8.setIcon(img2);
                    break;
                case 9:
                    nodePlaceholder9.setIcon(img2);
                    break;
                case 10:
                    nodePlaceholder10.setIcon(img2);
                    break;
                case 11:
                    nodePlaceholder11.setIcon(img2);
                    break;
                case 12:
                    nodePlaceholder12.setIcon(img2);
                    break;
                case 13:
                    nodePlaceholder13.setIcon(img2);
                    break;
                case 14:
                    nodePlaceholder14.setIcon(img2);
                    break;
                case 15:
                    nodePlaceholder15.setIcon(img2);
                    break;
            }
        }
    }
    
/*
	 Method: SetEnemyColours
	 Input Parameters: none.
    
	 This method will set the colour of all nodes the player believes an
         enemy unit is located on (based on rumours) to the enemy's colour
	 */
    private void SetEnemyColours() throws IOException {
        int i = 0;
        ArrayList<ArrayList<Integer>> intelList;
        if (Global.curPlayer == 0) {
            intelList = Scenario.redPlayer.enemyIntelligence;
        } else {
            intelList = Scenario.bluePlayer.enemyIntelligence;
        }

        //first it checks if there is any intel
        for (ArrayList<Integer> intel : intelList) {
            if (intel.size() > 1) {
                //sets the node of index to opposing team colour
                Image img;
                if (Global.curPlayer == 0) {
                    img = ImageIO.read(getClass().getResource("Images/map-node-blue.png"));
                } else {
                    img = ImageIO.read(getClass().getResource("Images/map-node-red.png"));
                }

                ImageIcon img2 = new ImageIcon(img);
                
		int value = intel.get(0);

                switch (value) {
                    case 0:
                        nodePlaceholder0.setIcon(img2);
                        break;
                    case 1:
                        nodePlaceholder1.setIcon(img2);
                        break;
                    case 2:
                        nodePlaceholder2.setIcon(img2);
                        break;
                    case 3:
                        nodePlaceholder3.setIcon(img2);
                        break;
                    case 4:
                        nodePlaceholder4.setIcon(img2);
                        break;
                    case 5:
                        nodePlaceholder5.setIcon(img2);
                        break;
                    case 6:
                        nodePlaceholder6.setIcon(img2);
                        break;
                    case 7:
                        nodePlaceholder7.setIcon(img2);
                        break;
                    case 8:
                        nodePlaceholder8.setIcon(img2);
                        break;
                    case 9:
                        nodePlaceholder9.setIcon(img2);
                        break;
                    case 10:
                        nodePlaceholder10.setIcon(img2);
                        break;
                    case 11:
                        nodePlaceholder11.setIcon(img2);
                        break;
                    case 12:
                        nodePlaceholder12.setIcon(img2);
                        break;
                    case 13:
                        nodePlaceholder13.setIcon(img2);
                        break;
                    case 14:
                        nodePlaceholder14.setIcon(img2);
                        break;
                    case 15:
                        nodePlaceholder15.setIcon(img2);
                        break;
                }

            //increments i, which keeps track of indexing
            
            }
            i++;
        }
    }
    
    /*
	 Method: SetcurrentColour
	 Input Parameters: none.
    
	 This method will set the node selected to have a white colour
	 */
    private void SetCurrentColour() throws IOException {
        //sets the node selected to a white background
        Image img;
        img = ImageIO.read(getClass().getResource("Images/map-node-selected.png"));
        ImageIcon img2 = new ImageIcon(img);
        switch (nodeSelected) {
            case 0:
                nodePlaceholder0.setIcon(img2);
                break;
            case 1:
                nodePlaceholder1.setIcon(img2);
                break;
            case 2:
                nodePlaceholder2.setIcon(img2);
                break;
            case 3:
                nodePlaceholder3.setIcon(img2);
                break;
            case 4:
                nodePlaceholder4.setIcon(img2);
                break;
            case 5:
                nodePlaceholder5.setIcon(img2);
                break;
            case 6:
                nodePlaceholder6.setIcon(img2);
                break;
            case 7:
                nodePlaceholder7.setIcon(img2);
                break;
            case 8:
                nodePlaceholder8.setIcon(img2);
                break;
            case 9:
                nodePlaceholder9.setIcon(img2);
                break;
            case 10:
                nodePlaceholder10.setIcon(img2);
                break;
            case 11:
                nodePlaceholder11.setIcon(img2);
                break;
            case 12:
                nodePlaceholder12.setIcon(img2);
                break;
            case 13:
                nodePlaceholder13.setIcon(img2);
                break;
            case 14:
                nodePlaceholder14.setIcon(img2);
                break;
            case 15:
                nodePlaceholder15.setIcon(img2);
                break;
        }
    }

    /*
	 Method: SetDefaultColours
	 Input Parameters: none.
    
	 This method scans through the rumours list to determine if an enemy
         is present on this node. Sets frontend text based on the results.
	 */
    private void EnemiesHere() {
        String enemies = ""; // storage of output text
	ArrayList<ArrayList<Integer>> intel; // storage of intel list
	boolean first, check; // list check variables for the node id and if the node id matched the selected node
	
	if (Global.curPlayer == 0){ // obtain the correct intel list
		intel = Scenario.redPlayer.enemyIntelligence;
	} else {
		intel = Scenario.bluePlayer.enemyIntelligence;
	}
	
	for (ArrayList<Integer> nodal : intel){ // for each nodal intelligence list
		first = true; // if the first entry seen in the below loop is the first entry
		check = false; // if the first entry matched the id of the node selected
		for (Integer value : nodal){ // for each value in the intel list
			if (first){ // if it is the first value
				if (value == nodeSelected){ // if the node matches the selected node
					check = true; // verify that this is true
				}
				first = false; // toggle the first flag
			} else { // if the entry is not the first
				if (check){ // if the first entry matches the node selected
					// report the value rumoured to be there
					if (value == 3) {
						enemies += (" " + ConvertSize(5, 1));
					} else if (value == 2) {
						enemies += (" " + ConvertSize(15, 0));
					} else if (value == 1) {
					    enemies += (" " + ConvertSize(10, 0));
					} else if (value == 0) {
					    enemies += (" " + ConvertSize(5, 0));
					}
				}
			}
		}		
	}
        
        //sets text for no fog of war values
        if(enemies.equals(""))
            enemies = "none known to be here";
        labelInfo6.setText("Enemies here: " + enemies);
    }

    /*Placeholder nodes set the nodeSelected value, then call action.
     all of the nodes do the same actions: set the global value for
     the selected node's index value, sets the globals for the selected
     node's x and y values on the gui, then calls to the Action method */

    private void nodePlaceholder0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nodePlaceholder0ActionPerformed
        nodeSelected = 0;
        Action();
    }//GEN-LAST:event_nodePlaceholder0ActionPerformed

    private void menuItemCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemCloseActionPerformed
        //a base menu item always added to the popup menu that, when clicked,
        //clears and closes the popup menu.
        ClearPopupMenu();

        try {
            SetDefaultColours();
        } catch (IOException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            SetEnemyColours();
        } catch (IOException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        }
        SetColours();
    }//GEN-LAST:event_menuItemCloseActionPerformed

    private void popupMenuFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_popupMenuFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_popupMenuFocusLost

    private void buttonMapImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonMapImageActionPerformed
        ClearPopupMenu();
        try {
            SetDefaultColours();
        } catch (IOException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            SetEnemyColours();
        } catch (IOException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        }
        SetColours();
    }//GEN-LAST:event_buttonMapImageActionPerformed

    private void buttonMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonMenuActionPerformed
        //button for quitting current scenario and returning to main menu
        Scenario.killSwitch();

        ClearPopupMenu();
        ClearMenuInfo();

        new MainMenu().setVisible(true);
        this.dispose();
        this.frameFloatingInfo.dispose();
    }//GEN-LAST:event_buttonMenuActionPerformed

    private void buttonExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonExitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_buttonExitActionPerformed

    private void buttonNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonNextActionPerformed
        buttonNext.setVisible(false);
        buttonFinishTurn.setEnabled(true);
        panelMap.setVisible(true);
    }//GEN-LAST:event_buttonNextActionPerformed

    private void buttonFinishTurnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonFinishTurnActionPerformed
        //removes all text
        buttonFinishTurn.setEnabled(false);
        list1.removeAll();
        ClearMenuInfo();
        ClearPopupMenu();
        labelPoliticalPower.setText("");
        labelCurPlayer.setText("");
        panelMap.setVisible(false);

        Scenario.game.endTurn();

        //for the next player
        if (Global.curPlayer == 0) {
            Global.curPlayer = 1;
            labelCurPlayer.setText("Blue team's turn");
            buttonNext.setText("Blue team, click here to proceed.");
        } else {
            Global.curPlayer = 0;
            labelCurPlayer.setText("Red team's turn");
            buttonNext.setText("Red team, click here to proceed.");
        }

        //actions to finish the turn on the backend
        Game.IsGameEnd();
        if (Global.intGameOver == 1) {
            GameOver();
            timer.stop();
        }

        //check for if the second player is an ai
        if (Global.opponent == false) {
            //calls the AI
            AI.AI();

            Scenario.game.endTurn();

            //resets the curPlayer controller to the previous player
            if (Global.curPlayer == 0) {
                Global.curPlayer = 1;
                labelCurPlayer.setText("Blue team's turn");
                buttonNext.setText("Blue team, click here to proceed.");
            } else {
                Global.curPlayer = 0;
                labelCurPlayer.setText("Red team's turn");
                buttonNext.setText("Red team, click here to proceed.");
            }

            //ends turn again
            Game.IsGameEnd();
            if (Global.intGameOver == 1) {
                GameOver();
                timer.stop();
            }
        }

        buttonNext.setVisible(true);

        if (Global.intGameOver != 1) {
            int turns = (int) Game.turnCount + 1;
            labelTurnCount.setText("Turn: " + (turns) + "/" + Game.maxTurnCount);

            //sets label that displays season
            if (Global.season == 0) {
                labelSeason.setText("Season: Winter");
            } else if (Global.season == 1) {
                labelSeason.setText("Season: Spring");
            } else if (Global.season == 2) {
                labelSeason.setText("Season: Summer");
            } else if (Global.season == 3) {
                labelSeason.setText("Season: Autumn");
            }

            //sets label that displays current political power level
            if (Global.curPlayer == 0) {
                labelPoliticalPower.setText("Political power: " + Scenario.redPlayer.politicalPower);
            } else {
                labelPoliticalPower.setText("Political power: " + Scenario.bluePlayer.politicalPower);
            }

            //try catch for setting default node colours
            try {
                SetDefaultColours();
            } catch (IOException ex) {
                Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                SetEnemyColours();
            } catch (IOException ex) {
                Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
            }
            //sets colours of nodes with current player's armies
            SetColours();
        }
    }//GEN-LAST:event_buttonFinishTurnActionPerformed

    private void buttonBackdropActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBackdropActionPerformed
        ClearPopupMenu();
        try {
            SetDefaultColours();
        } catch (IOException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            SetEnemyColours();
        } catch (IOException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        }
        SetColours();
    }//GEN-LAST:event_buttonBackdropActionPerformed

    private void nodePlaceholder1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nodePlaceholder1ActionPerformed
        nodeSelected = 1;
        Action();
    }//GEN-LAST:event_nodePlaceholder1ActionPerformed

    private void nodePlaceholder2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nodePlaceholder2ActionPerformed
        nodeSelected = 2;
        Action();
    }//GEN-LAST:event_nodePlaceholder2ActionPerformed

    private void nodePlaceholder3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nodePlaceholder3ActionPerformed
        nodeSelected = 3;
        Action();
    }//GEN-LAST:event_nodePlaceholder3ActionPerformed

    private void nodePlaceholder4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nodePlaceholder4ActionPerformed
        nodeSelected = 4;
        Action();
    }//GEN-LAST:event_nodePlaceholder4ActionPerformed

    private void nodePlaceholder5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nodePlaceholder5ActionPerformed
        nodeSelected = 5;
        Action();
    }//GEN-LAST:event_nodePlaceholder5ActionPerformed

    private void nodePlaceholder6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nodePlaceholder6ActionPerformed
        nodeSelected = 6;
        Action();
    }//GEN-LAST:event_nodePlaceholder6ActionPerformed

    private void nodePlaceholder7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nodePlaceholder7ActionPerformed
        nodeSelected = 7;
        Action();
    }//GEN-LAST:event_nodePlaceholder7ActionPerformed

    private void nodePlaceholder8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nodePlaceholder8ActionPerformed
        nodeSelected = 8;
        Action();
    }//GEN-LAST:event_nodePlaceholder8ActionPerformed

    private void nodePlaceholder9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nodePlaceholder9ActionPerformed
        nodeSelected = 9;
        Action();
    }//GEN-LAST:event_nodePlaceholder9ActionPerformed

    private void nodePlaceholder10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nodePlaceholder10ActionPerformed
        nodeSelected = 10;
        Action();
    }//GEN-LAST:event_nodePlaceholder10ActionPerformed

    private void nodePlaceholder11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nodePlaceholder11ActionPerformed
        nodeSelected = 11;
        Action();
    }//GEN-LAST:event_nodePlaceholder11ActionPerformed

    private void nodePlaceholder12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nodePlaceholder12ActionPerformed
        nodeSelected = 12;
        Action();
    }//GEN-LAST:event_nodePlaceholder12ActionPerformed

    private void nodePlaceholder13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nodePlaceholder13ActionPerformed
        nodeSelected = 13;
        Action();
    }//GEN-LAST:event_nodePlaceholder13ActionPerformed

    private void nodePlaceholder14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nodePlaceholder14ActionPerformed
        nodeSelected = 14;
        Action();
    }//GEN-LAST:event_nodePlaceholder14ActionPerformed

    private void nodePlaceholder15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nodePlaceholder15ActionPerformed
        nodeSelected = 15;
        Action();
    }//GEN-LAST:event_nodePlaceholder15ActionPerformed

    /**
     * @param args the command line arguments
     */
    public void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Map.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Map.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Map.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Map.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Map().setVisible(true);
            }

        });

    }

    /*
	 Method: GameOver
	 Input Parameters: none.
    
	 This method will be called if a game has ended, closing the map, calling
         the scenario killswitch and loading the gameover screen.
	 */
    public void GameOver() {
        //button for quitting current scenario and returning to main menu
        Scenario.killSwitch();

        Game.turnCount = 0;
        ClearPopupMenu();
        ClearMenuInfo();

        new GameOver().setVisible(true);
        this.dispose();
        this.frameFloatingInfo.dispose();
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonBackdrop;
    private javax.swing.JButton buttonExit;
    private javax.swing.JButton buttonFinishTurn;
    private javax.swing.JButton buttonMapImage;
    private javax.swing.JToggleButton buttonMenu;
    private javax.swing.JButton buttonNext;
    private javax.swing.JFrame frameFloatingInfo;
    private javax.swing.JLabel jLabel0;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JLabel labelBackdrop;
    private javax.swing.JLabel labelCurPlayer;
    private javax.swing.JLabel labelInfo1;
    private javax.swing.JLabel labelInfo2;
    private javax.swing.JLabel labelInfo3;
    private javax.swing.JLabel labelInfo4;
    private javax.swing.JLabel labelInfo5;
    private javax.swing.JLabel labelInfo6;
    private javax.swing.JLabel labelOpponent;
    private javax.swing.JLabel labelPoliticalPower;
    private javax.swing.JLabel labelScenario;
    private javax.swing.JLabel labelSeason;
    private javax.swing.JLabel labelTimer;
    private javax.swing.JLabel labelTurnCount;
    private java.awt.List list1;
    private javax.swing.JPanel menuInfo;
    private javax.swing.JMenuItem menuItemClose;
    private javax.swing.JButton nodePlaceholder0;
    private javax.swing.JButton nodePlaceholder1;
    private javax.swing.JButton nodePlaceholder10;
    private javax.swing.JButton nodePlaceholder11;
    private javax.swing.JButton nodePlaceholder12;
    private javax.swing.JButton nodePlaceholder13;
    private javax.swing.JButton nodePlaceholder14;
    private javax.swing.JButton nodePlaceholder15;
    private javax.swing.JButton nodePlaceholder2;
    private javax.swing.JButton nodePlaceholder3;
    private javax.swing.JButton nodePlaceholder4;
    private javax.swing.JButton nodePlaceholder5;
    private javax.swing.JButton nodePlaceholder6;
    private javax.swing.JButton nodePlaceholder7;
    private javax.swing.JButton nodePlaceholder8;
    private javax.swing.JButton nodePlaceholder9;
    private javax.swing.JPanel panelMap;
    private javax.swing.JPopupMenu popupMenu;
    // End of variables declaration//GEN-END:variables
}
