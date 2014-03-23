/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Prem
 */
public class Teams extends javax.swing.JFrame {

    /**
     * Creates new form Teams
     */
    public Teams() {
        initComponents();
        
        Global.chosenTeam = false;
        
        if(Global.intScenario == 1) {
            radioTeam1.setText("Optimates");
            radioTeam2.setText("Populares");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttongroupTeam = new javax.swing.ButtonGroup();
        butonX = new javax.swing.JButton();
        labelTitle = new javax.swing.JLabel();
        labelSubtitle = new javax.swing.JLabel();
        labelOr = new javax.swing.JLabel();
        radioTeam1 = new javax.swing.JRadioButton();
        radioTeam2 = new javax.swing.JRadioButton();
        buttonBack = new javax.swing.JButton();
        buttonBegin = new javax.swing.JButton();
        labelBackground = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBounds(new java.awt.Rectangle(100, 100, 0, 0));
        setMaximumSize(new java.awt.Dimension(260, 310));
        setMinimumSize(new java.awt.Dimension(260, 310));
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(260, 310));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        butonX.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/X.png"))); // NOI18N
        butonX.setBorderPainted(false);
        butonX.setContentAreaFilled(false);
        butonX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butonXActionPerformed(evt);
            }
        });
        getContentPane().add(butonX, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 0, 20, -1));

        labelTitle.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        labelTitle.setText("Player 1,");
        getContentPane().add(labelTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, -1, -1));

        labelSubtitle.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        labelSubtitle.setText("select your team:");
        getContentPane().add(labelSubtitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 110, -1, -1));

        labelOr.setText("Or");
        getContentPane().add(labelOr, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 170, -1, -1));

        buttongroupTeam.add(radioTeam1);
        radioTeam1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        radioTeam1.setForeground(new java.awt.Color(255, 0, 0));
        radioTeam1.setSelected(true);
        radioTeam1.setText("red team");
        radioTeam1.setContentAreaFilled(false);
        radioTeam1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioTeam1ActionPerformed(evt);
            }
        });
        getContentPane().add(radioTeam1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 140, -1, -1));

        buttongroupTeam.add(radioTeam2);
        radioTeam2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        radioTeam2.setForeground(new java.awt.Color(0, 0, 204));
        radioTeam2.setText("blue team");
        radioTeam2.setContentAreaFilled(false);
        radioTeam2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioTeam2ActionPerformed(evt);
            }
        });
        getContentPane().add(radioTeam2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 190, -1, -1));

        buttonBack.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        buttonBack.setText("Return to Menu");
        buttonBack.setContentAreaFilled(false);
        buttonBack.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        buttonBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonBackActionPerformed(evt);
            }
        });
        getContentPane().add(buttonBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 240, 60));

        buttonBegin.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        buttonBegin.setText("Begin Match");
        buttonBegin.setContentAreaFilled(false);
        buttonBegin.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        buttonBegin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonBeginActionPerformed(evt);
            }
        });
        getContentPane().add(buttonBegin, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 240, 240, 60));

        labelBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/TeamSelect-backdrop.png"))); // NOI18N
        labelBackground.setToolTipText(null);
        getContentPane().add(labelBackground, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 260, 310));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBackActionPerformed
        //button for quitting current scenario and returning to main menu
        Scenario.killSwitch();

        new MainMenu().setVisible(true);
        Teams.this.dispose();
    }//GEN-LAST:event_buttonBackActionPerformed

    private void radioTeam1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioTeam1ActionPerformed
        Global.chosenTeam = false;
    }//GEN-LAST:event_radioTeam1ActionPerformed

    private void radioTeam2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioTeam2ActionPerformed
        Global.chosenTeam = true;
    }//GEN-LAST:event_radioTeam2ActionPerformed

    private void butonXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butonXActionPerformed
        System.exit(0);
    }//GEN-LAST:event_butonXActionPerformed

    private void buttonBeginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBeginActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonBeginActionPerformed

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
            java.util.logging.Logger.getLogger(Teams.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Teams.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Teams.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Teams.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Teams().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butonX;
    private javax.swing.JButton buttonBack;
    private javax.swing.JButton buttonBegin;
    private javax.swing.ButtonGroup buttongroupTeam;
    private javax.swing.JLabel labelBackground;
    private javax.swing.JLabel labelOr;
    private javax.swing.JLabel labelSubtitle;
    private javax.swing.JLabel labelTitle;
    private javax.swing.JRadioButton radioTeam1;
    private javax.swing.JRadioButton radioTeam2;
    // End of variables declaration//GEN-END:variables
}
