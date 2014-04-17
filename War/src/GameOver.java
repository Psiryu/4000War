
import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * Game Over
 *
 * Description: This is the "game over" screen that displays the victor
 * of a scenario. It is simply a label and a text area that are populated
 * by a variable within Global.
 *
 * Usage: this screen is called to from the Map.java for a scenario. It
 * requires no other resources, and the only other external reference is that
 * it returns the user to the Main Menu when they are done here. 
 * 
 * Maintenance notes: none required here, unless more information than "who
 * won" and "why" are desired, or graphical changes.
 */


public class GameOver extends javax.swing.JFrame {

    /**
     * Creates new form GameOver
     */
    public GameOver() {
        initComponents();
        
        //sets window to be centered
        Dimension dimensions = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dimensions.width/2-this.getSize().width/2, dimensions.height/2-this.getSize().height/2);
        
        //sets the textboxes to contain who won and why
        labelWinner.setText(Global.gameSummary);
        textWhy.setText(Global.gameSummary2);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonMenu = new javax.swing.JButton();
        labelGameOver = new javax.swing.JLabel();
        labelWinner = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        textWhy = new javax.swing.JTextArea();
        labelBackground = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBounds(new java.awt.Rectangle(100, 100, 0, 0));
        setMaximumSize(new java.awt.Dimension(605, 515));
        setMinimumSize(new java.awt.Dimension(600, 515));
        setPreferredSize(new java.awt.Dimension(600, 520));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        buttonMenu.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        buttonMenu.setText("Main Menu");
        buttonMenu.setBorderPainted(false);
        buttonMenu.setContentAreaFilled(false);
        buttonMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonMenuActionPerformed(evt);
            }
        });
        getContentPane().add(buttonMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 410, 530, 50));

        labelGameOver.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        labelGameOver.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelGameOver.setText("Game over!");
        labelGameOver.setToolTipText(null);
        getContentPane().add(labelGameOver, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 530, 70));

        labelWinner.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        labelWinner.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(labelWinner, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 560, 40));

        textWhy.setEditable(false);
        textWhy.setColumns(20);
        textWhy.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        textWhy.setLineWrap(true);
        textWhy.setRows(5);
        textWhy.setToolTipText(null);
        textWhy.setWrapStyleWord(true);
        textWhy.setBorder(null);
        textWhy.setMaximumSize(new java.awt.Dimension(160, 90));
        textWhy.setMinimumSize(new java.awt.Dimension(160, 90));
        textWhy.setOpaque(false);
        jScrollPane1.setViewportView(textWhy);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 560, 160));

        labelBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/TeamSelect-backdrop.png"))); // NOI18N
        labelBackground.setToolTipText(null);
        labelBackground.setMaximumSize(new java.awt.Dimension(605, 520));
        labelBackground.setMinimumSize(new java.awt.Dimension(605, 520));
        labelBackground.setPreferredSize(new java.awt.Dimension(605, 520));
        getContentPane().add(labelBackground, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 610, 500));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonMenuActionPerformed
        //button for quitting current scenario and returning to main menu
        Scenario.killSwitch();

        new MainMenu().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_buttonMenuActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(GameOver.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GameOver.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GameOver.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GameOver.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GameOver().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonMenu;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelBackground;
    private javax.swing.JLabel labelGameOver;
    private javax.swing.JLabel labelWinner;
    private javax.swing.JTextArea textWhy;
    // End of variables declaration//GEN-END:variables
}
