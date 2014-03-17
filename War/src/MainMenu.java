/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Prem
 */
public class MainMenu extends javax.swing.JFrame {

    /**
     * Creates new form MainMenu
     */
    public MainMenu() {
        initComponents();
        Global.opponent = false;
        Global.ResetGlobals();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        labelTitle = new javax.swing.JLabel();
        buttonScenario1 = new javax.swing.JButton();
        buttonScenario2 = new javax.swing.JButton();
        buttonScenario3 = new javax.swing.JButton();
        radioAI = new javax.swing.JRadioButton();
        radioPlayer = new javax.swing.JRadioButton();
        buttonEnd = new javax.swing.JButton();
        buttonHowTo = new javax.swing.JButton();
        labelBackgroundImage = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(204, 204, 204));
        setMaximumSize(new java.awt.Dimension(362, 253));
        setMinimumSize(new java.awt.Dimension(362, 253));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        labelTitle.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        labelTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelTitle.setText("Roman Civil War");
        getContentPane().add(labelTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 290, 50));

        buttonScenario1.setFont(new java.awt.Font("Times New Roman", 0, 26)); // NOI18N
        buttonScenario1.setText("Scenario 1");
        buttonScenario1.setContentAreaFilled(false);
        buttonScenario1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonScenario1ActionPerformed(evt);
            }
        });
        getContentPane().add(buttonScenario1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 150, 60));

        buttonScenario2.setFont(new java.awt.Font("Times New Roman", 0, 26)); // NOI18N
        buttonScenario2.setText("Scenario 2");
        buttonScenario2.setContentAreaFilled(false);
        buttonScenario2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonScenario2ActionPerformed(evt);
            }
        });
        getContentPane().add(buttonScenario2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, 150, 60));

        buttonScenario3.setFont(new java.awt.Font("Times New Roman", 0, 26)); // NOI18N
        buttonScenario3.setText("Scenario 3");
        buttonScenario3.setContentAreaFilled(false);
        buttonScenario3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonScenario3ActionPerformed(evt);
            }
        });
        getContentPane().add(buttonScenario3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, 150, 60));

        buttonGroup1.add(radioAI);
        radioAI.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        radioAI.setSelected(true);
        radioAI.setText("Against AI");
        radioAI.setContentAreaFilled(false);
        radioAI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioAIActionPerformed(evt);
            }
        });
        getContentPane().add(radioAI, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 90, 110, -1));

        buttonGroup1.add(radioPlayer);
        radioPlayer.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        radioPlayer.setText("Against Player");
        radioPlayer.setContentAreaFilled(false);
        radioPlayer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioPlayerActionPerformed(evt);
            }
        });
        getContentPane().add(radioPlayer, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 130, -1, -1));

        buttonEnd.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        buttonEnd.setText("End Game");
        buttonEnd.setContentAreaFilled(false);
        buttonEnd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonEndActionPerformed(evt);
            }
        });
        getContentPane().add(buttonEnd, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 240, 150, 40));

        buttonHowTo.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        buttonHowTo.setText("How to Play");
        buttonHowTo.setContentAreaFilled(false);
        buttonHowTo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonHowToActionPerformed(evt);
            }
        });
        getContentPane().add(buttonHowTo, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 200, 150, 40));

        labelBackgroundImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/MainMenu-Backdrop.png"))); // NOI18N
        labelBackgroundImage.setToolTipText("");
        getContentPane().add(labelBackgroundImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 390, 300));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonScenario1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonScenario1ActionPerformed
        Global.intScenario = 1;
        new Teams().setVisible(true);
        MainMenu.this.dispose();
    }//GEN-LAST:event_buttonScenario1ActionPerformed

    private void buttonScenario2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonScenario2ActionPerformed
        Global.intScenario = 2;
        new Teams().setVisible(true);
        MainMenu.this.dispose();
    }//GEN-LAST:event_buttonScenario2ActionPerformed

    private void buttonScenario3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonScenario3ActionPerformed
        Global.intScenario = 3;
        new Teams().setVisible(true);
        MainMenu.this.dispose();
    }//GEN-LAST:event_buttonScenario3ActionPerformed

    private void radioAIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioAIActionPerformed
        Global.opponent = false;
    }//GEN-LAST:event_radioAIActionPerformed

    private void radioPlayerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioPlayerActionPerformed
        Global.opponent = true;
    }//GEN-LAST:event_radioPlayerActionPerformed

    private void buttonEndActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonEndActionPerformed
        System.exit(0);
    }//GEN-LAST:event_buttonEndActionPerformed

    private void buttonHowToActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonHowToActionPerformed
        new HowToPlay().setVisible(true);
        MainMenu.this.dispose();
    }//GEN-LAST:event_buttonHowToActionPerformed

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
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonEnd;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton buttonHowTo;
    private javax.swing.JButton buttonScenario1;
    private javax.swing.JButton buttonScenario2;
    private javax.swing.JButton buttonScenario3;
    private javax.swing.JLabel labelBackgroundImage;
    private javax.swing.JLabel labelTitle;
    private javax.swing.JRadioButton radioAI;
    private javax.swing.JRadioButton radioPlayer;
    // End of variables declaration//GEN-END:variables
}
