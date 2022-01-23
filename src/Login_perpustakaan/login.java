
package Login_perpustakaan;

import java.sql.*;
import Connection.koneksi;
import Menu_perpustakaan.UserID;
import javax.swing.JOptionPane;


public class login extends javax.swing.JFrame {
    private Connection conn = new koneksi().connect();
    public static String user;
    ResultSet rs = null;
    PreparedStatement pst = null;
    /**
     * Creates new form login
     */
    public login() {
        initComponents();
    }
    
    public static String getusername(){
        return user;
    }
    
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        bexit = new javax.swing.JButton();
        bregis_menu = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        bdaftar_buku1 = new javax.swing.JButton();
        bkelola = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtusername = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtpass = new javax.swing.JPasswordField();
        blogin = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(245, 245, 245));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(0, 204, 102));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bexit.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        bexit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/exit_50px.png"))); // NOI18N
        bexit.setText("  Keluar");
        bexit.setBorder(null);
        bexit.setBorderPainted(false);
        bexit.setContentAreaFilled(false);
        bexit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bexit.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        bexit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bexitActionPerformed(evt);
            }
        });
        jPanel2.add(bexit, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 330, 240, 50));

        bregis_menu.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        bregis_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/add_user_group_woman_man_50px.png"))); // NOI18N
        bregis_menu.setText("  Registrasi");
        bregis_menu.setBorder(null);
        bregis_menu.setBorderPainted(false);
        bregis_menu.setContentAreaFilled(false);
        bregis_menu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bregis_menu.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        bregis_menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bregis_menuActionPerformed(evt);
            }
        });
        jPanel2.add(bregis_menu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, 240, 50));

        jPanel4.setBackground(new java.awt.Color(245, 245, 245));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bdaftar_buku1.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        bdaftar_buku1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/enter_50px.png"))); // NOI18N
        bdaftar_buku1.setText("  Masuk");
        bdaftar_buku1.setBorder(null);
        bdaftar_buku1.setBorderPainted(false);
        bdaftar_buku1.setContentAreaFilled(false);
        bdaftar_buku1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bdaftar_buku1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        bdaftar_buku1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bdaftar_buku1ActionPerformed(evt);
            }
        });
        jPanel4.add(bdaftar_buku1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 240, 50));

        jPanel2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 240, 70));

        bkelola.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        bkelola.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/maintenance_50px.png"))); // NOI18N
        bkelola.setText("  Kelola Admin");
        bkelola.setBorder(null);
        bkelola.setBorderPainted(false);
        bkelola.setContentAreaFilled(false);
        bkelola.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bkelola.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        bkelola.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bkelolaActionPerformed(evt);
            }
        });
        jPanel2.add(bkelola, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 260, 240, 50));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Login_perpustakaan/minilogotransaparant2.png"))); // NOI18N
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, -1, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 240, 400));

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel5.setText("Username");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 100, -1, -1));

        txtusername.setBackground(new java.awt.Color(245, 245, 245));
        txtusername.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtusername.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtusername, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 130, 370, 30));

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel6.setText("Password");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 190, -1, -1));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/password_30px.png"))); // NOI18N
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 200, 30, 40));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/administrator_male_30px.png"))); // NOI18N
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 110, 30, 40));

        txtpass.setBackground(new java.awt.Color(245, 245, 245));
        txtpass.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtpass, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 220, 370, 30));

        blogin.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        blogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/login_35px.png"))); // NOI18N
        blogin.setText("Login");
        blogin.setBorder(null);
        blogin.setBorderPainted(false);
        blogin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        blogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bloginActionPerformed(evt);
            }
        });
        jPanel1.add(blogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 300, 170, 50));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("SELAMAT DATANG");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 40, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 710, 400));

        setSize(new java.awt.Dimension(726, 439));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void bexitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bexitActionPerformed
       int tny = JOptionPane.showConfirmDialog(null, "Yakin Ingin Keluar ?","Tanya",JOptionPane.YES_NO_OPTION);
        if (tny==0) {     
            try {
                System.exit(0);
            } catch (Exception e) {
            }
        }

    }//GEN-LAST:event_bexitActionPerformed

    private void bkelolaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bkelolaActionPerformed
        dispose();
        form_kelolaUser kelola;
        kelola = new form_kelolaUser();
        kelola.setVisible(true);
    }//GEN-LAST:event_bkelolaActionPerformed

    private void bdaftar_buku1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bdaftar_buku1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bdaftar_buku1ActionPerformed

    private void bregis_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bregis_menuActionPerformed
        dispose();
        input_regist regis;
        regis = new input_regist();
        regis.setVisible(true);
    }//GEN-LAST:event_bregis_menuActionPerformed

    private void bloginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bloginActionPerformed
        try{
            String username = txtusername.getText();
            String pass= String.valueOf(txtpass.getPassword());
            String SQL = "Select * from tb_admin where id_admin='" +username+"' and password='"+pass+"'";
            PreparedStatement state = conn.prepareStatement(SQL);
            ResultSet Result = state.executeQuery(SQL);
            if(Result.next()){
                UserID.setUserLogin(Result.getString("nama_admin"));
                if(txtusername.getText().equals(Result.getString("id_admin"))&&
                        String.valueOf(txtpass.getPassword()).equals(Result.getString("password"))){
                    user = Result.getString("id_admin");
                    JOptionPane.showMessageDialog(this, "Selamat Datang \n PERPUSTAKAAN SMK ADI LUHUR 2");
                    
                }
                dispose();
                new Menu_perpustakaan.main_menu().setVisible(true);
            }else{
                    JOptionPane.showMessageDialog(null, "Login Gagal!");
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Username/Password Salah "+e);
        }

    }//GEN-LAST:event_bloginActionPerformed

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
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bdaftar_buku1;
    private javax.swing.JButton bexit;
    private javax.swing.JButton bkelola;
    private javax.swing.JButton blogin;
    private javax.swing.JButton bregis_menu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPasswordField txtpass;
    private javax.swing.JTextField txtusername;
    // End of variables declaration//GEN-END:variables
}
