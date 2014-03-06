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
        
        radioTeam1.setText(Global.teamOne);
        radioTeam2.setText(Global.teamTwo);
        
        Global.chosenTeam = false;
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
        labelTitle = new javax.swing.JLabel();
        labelSubtitle = new javax.swing.JLabel();
        labelOr = new javax.swing.JLabel();
        radioTeam1 = new javax.swing.JRadioButton();
        radioTeam2 = new javax.swing.JRadioButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        labelTitle.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        labelTitle.setText("Player 1,");

        labelSubtitle.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        labelSubtitle.setText("select your team:");

        labelOr.setText("Or");

        buttongroupTeam.add(radioTeam1);
        radioTeam1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        radioTeam1.setSelected(true);
        radioTeam1.setText("jRadioButton1");
        radioTeam1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioTeam1ActionPerformed(evt);
            }
        });

        buttongroupTeam.add(radioTeam2);
        radioTeam2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        radioTeam2.setText("jRadioButton2");

        jButton1.setText("Begin Match");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(84, 84, 84))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(106, 106, 106)
                        .addComponent(labelOr))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(radioTeam2)
                            .addComponent(radioTeam1)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelSubtitle)
                            .addComponent(labelTitle))))
                .addContainerGap(58, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(labelTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelSubtitle)
                .addGap(40, 40, 40)
                .addComponent(radioTeam1)
                .addGap(18, 18, 18)
                .addComponent(labelOr)
                .addGap(18, 18, 18)
                .addComponent(radioTeam2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new Map().setVisible(true);
        Teams.this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void radioTeam1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioTeam1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radioTeam1ActionPerformed

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
    private javax.swing.ButtonGroup buttongroupTeam;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel labelOr;
    private javax.swing.JLabel labelSubtitle;
    private javax.swing.JLabel labelTitle;
    private javax.swing.JRadioButton radioTeam1;
    private javax.swing.JRadioButton radioTeam2;
    // End of variables declaration//GEN-END:variables
}
