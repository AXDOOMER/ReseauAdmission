/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Paquet_Client;

import java.awt.Point;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author Andy
 */
public class Client extends javax.swing.JFrame {
    
    public static OracleConnexion oracleConnexion;
    public static Object[][] data = null;
    /**
     * Creates new form Client
     */
    public Client() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TBL_Spectacle = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        BTN_Ajouter_Spec = new javax.swing.JButton();
        BTN_Ajouter_Client = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        BTN_Ajouter_Rep = new javax.swing.JButton();
        BTN_Ajouter_Salle = new javax.swing.JButton();
        BTN_Ajouter_Categorie = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Spectacle disponible"));

        TBL_Spectacle.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Prix", "Artiste", "Nom spectacle"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(TBL_Spectacle);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Ajouter quelque chose"));

        BTN_Ajouter_Spec.setText("Ajouter");
        BTN_Ajouter_Spec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTN_Ajouter_SpecActionPerformed(evt);
            }
        });

        BTN_Ajouter_Client.setText("Ajouter");
        BTN_Ajouter_Client.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTN_Ajouter_ClientActionPerformed(evt);
            }
        });

        jLabel1.setText("Spectacle");

        jLabel2.setText("Client");

        BTN_Ajouter_Rep.setText("Ajouter");
        BTN_Ajouter_Rep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTN_Ajouter_RepActionPerformed(evt);
            }
        });

        BTN_Ajouter_Salle.setText("Ajouter");
        BTN_Ajouter_Salle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTN_Ajouter_SalleActionPerformed(evt);
            }
        });

        BTN_Ajouter_Categorie.setText("Ajouter");
        BTN_Ajouter_Categorie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTN_Ajouter_CategorieActionPerformed(evt);
            }
        });

        jLabel3.setText("Représentation");

        jLabel4.setText("Salle");

        jLabel5.setText("Categorie");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BTN_Ajouter_Spec))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BTN_Ajouter_Client))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BTN_Ajouter_Rep))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BTN_Ajouter_Salle))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BTN_Ajouter_Categorie))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BTN_Ajouter_Spec)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BTN_Ajouter_Client)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BTN_Ajouter_Rep)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BTN_Ajouter_Salle)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BTN_Ajouter_Categorie)
                    .addComponent(jLabel5))
                .addContainerGap(41, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 131, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(93, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    // Pour se déconnecter à la fin du programme ( Sa ne fonctionne pas avec cette action )
    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        //oracleConnexion.deconnecter();
    }//GEN-LAST:event_formWindowClosed
    
    // Pour se déconnecter à la fin du programme ( Sa fonctionnne )
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        oracleConnexion.deconnecter();
    }//GEN-LAST:event_formWindowClosing

    private void BTN_Ajouter_ClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_Ajouter_ClientActionPerformed
        // Faire afficher le jFrame AddClient et lui envoyer la connexion
        AddClient addClient = new AddClient(oracleConnexion);
        Point centre = new Point(this.getLocation().x + this.getWidth()/2 - addClient.getWidth()/2,this.getLocation().y +  this.getHeight()/2 - addClient.getHeight()/2);
        addClient.setLocation(centre);
        addClient.setVisible(true);
    }//GEN-LAST:event_BTN_Ajouter_ClientActionPerformed

    private void BTN_Ajouter_SpecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_Ajouter_SpecActionPerformed
        // Faire afficher le jFrame AddSpectacle et lui envoyer la connexion
        AddSpectacle addSpectacle = new AddSpectacle(oracleConnexion);
        Point centre = new Point(this.getLocation().x + this.getWidth()/2 - addSpectacle.getWidth()/2,this.getLocation().y +  this.getHeight()/2 - addSpectacle.getHeight()/2);
        addSpectacle.setLocation(centre);
        addSpectacle.setVisible(true);
    }//GEN-LAST:event_BTN_Ajouter_SpecActionPerformed

    private void BTN_Ajouter_CategorieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_Ajouter_CategorieActionPerformed
        // Faire afficher le jFrame AddCategorie et lui envoyer la connexion
        AddCategorie addCategorie = new AddCategorie(oracleConnexion);
        Point centre = new Point(this.getLocation().x + this.getWidth()/2 - addCategorie.getWidth()/2,this.getLocation().y +  this.getHeight()/2 - addCategorie.getHeight()/2);
        addCategorie.setLocation(centre);
        addCategorie.setVisible(true);
    }//GEN-LAST:event_BTN_Ajouter_CategorieActionPerformed

    private void BTN_Ajouter_RepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_Ajouter_RepActionPerformed
        AddRepresentation addRepresentation = new AddRepresentation(oracleConnexion);
        addRepresentation.setSize(400, 250);       
        Point centre = new Point(this.getLocation().x + this.getWidth()/2 - addRepresentation.getWidth()/2,this.getLocation().y +  this.getHeight()/2 - addRepresentation.getHeight()/2);
        addRepresentation.setLocation(centre);
 
        addRepresentation.setVisible(true);
    }//GEN-LAST:event_BTN_Ajouter_RepActionPerformed

    private void BTN_Ajouter_SalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_Ajouter_SalleActionPerformed
        AddSalle addSalle = new AddSalle(oracleConnexion);
        Point centre = new Point(this.getLocation().x + this.getWidth()/2 - addSalle.getWidth()/2,this.getLocation().y +  this.getHeight()/2 - addSalle.getHeight()/2);
        addSalle.setLocation(centre);
        addSalle.setVisible(true);
    }//GEN-LAST:event_BTN_Ajouter_SalleActionPerformed

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
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Client().setVisible(true);
                // Toutes les méthodes nécaissaire pour commencer l'application
                Connexion();
                AfficherSpectacle();
            }
        });
    }
    public static void Connexion(){
        oracleConnexion = new OracleConnexion();
        oracleConnexion.setConnection("Labontel", "ORACLE2");
        oracleConnexion.connecter();
    }
    
    // Sa l'affiche les spectacles dans un Jtable(TBL_Spectacle)
    public static void AfficherSpectacle()
    {
        try {         
            DefaultTableModel model = (DefaultTableModel) TBL_Spectacle.getModel();  
            model.setRowCount(0);
            CallableStatement Callist =
            oracleConnexion.getConnection().prepareCall(" { call TPF_BD_JAVA.ListeSpectacle(?)}");
            Callist.registerOutParameter(1,OracleTypes.CURSOR);
            Callist.execute();
            ResultSet rstlist = (ResultSet)Callist.getObject(1);
                        
                       
            while(rstlist.next())
            {                              
                int prix = rstlist.getInt(3);  
                String artiste = rstlist.getString(4);
                String nomSpectacle = rstlist.getString("NOMSpECTACLE");
                model.addRow(new Object[]{Integer.toString(prix),artiste,nomSpectacle});
                System.out.println(prix + "-" + artiste + "-" + nomSpectacle);
            }
            Callist.clearParameters();
            Callist.close();
            rstlist.close();
            System.out.println("affichage");
        }
        catch(SQLException list)
        {
        System.out.println(list.getMessage());
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BTN_Ajouter_Categorie;
    private javax.swing.JButton BTN_Ajouter_Client;
    private javax.swing.JButton BTN_Ajouter_Rep;
    private javax.swing.JButton BTN_Ajouter_Salle;
    private javax.swing.JButton BTN_Ajouter_Spec;
    public static javax.swing.JTable TBL_Spectacle;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
