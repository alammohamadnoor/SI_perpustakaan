/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Login_perpustakaan;

import javax.swing.JOptionPane;
import Login_perpustakaan.input_regist;
import javax.swing.JOptionPane;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyEvent;
import Connection.koneksi;
import Menu_perpustakaan.input_peminjaman;
import java.util.HashMap;
import java.util.Locale;
import javax.swing.JFrame;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class form_kelolaUser extends javax.swing.JFrame {
    private Connection conn = new koneksi().connect();
    private DefaultTableModel TabelUser;

    public form_kelolaUser() {
        initComponents();
        datatable();
        kosong(); 
    }
    
    public void cetak () {
        try {
            String path ="src/Login_perpustakaan/Report_Admin.jasper";
            HashMap parameter = new HashMap ();
            JasperPrint print = JasperFillManager.fillReport(path,parameter,conn);
            JasperViewer.viewReport(print,false);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane,"Dokumen Tidak Ada"+ex);
        }
    }
    
    public void print () {
        try {
            Locale locale = new Locale( "id", "ID" );
            HashMap par = new HashMap();
            par.put(JRParameter.REPORT_LOCALE, locale);
            JasperPrint jp = JasperFillManager.fillReport
            (form_kelolaUser.class.getResourceAsStream("/Login_perpustakaan/Report_Admin.jasper"), par, conn);
            JasperViewer.viewReport(jp, false);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane,"Dokumen Tidak Ada"+ex);
        }
    }
    
    protected void kosong(){
        txtcari.setText("");
    }
    protected void datatable(){
         Object [] Baris = {"Nama Lengkap","Jenis Kelamin","E - Mail","No. Telp","Username","Password"};
            TabelUser = new DefaultTableModel(null,Baris);
            String cariitem = txtcari.getText();
            
            try{
                String sql = "SELECT * FROM tb_admin where id_admin like '%"+cariitem+"%' or nama_admin like '%"+cariitem+"%'";
                Statement stat = conn.createStatement();
                ResultSet hasil = stat.executeQuery(sql);
                while (hasil.next()){
                    TabelUser.addRow(new Object [] {
                        hasil.getString(3),
                        hasil.getString(4),
                        hasil.getString(5),
                        hasil.getString(6),
                        hasil.getString(1),
                        hasil.getString(2)
                    });
                }
                tbuser.setModel(TabelUser);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "data gagal Dipanggil"+e);
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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        bexit = new javax.swing.JButton();
        bkelola = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        blogin_menu = new javax.swing.JButton();
        bregis_menu = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbuser = new javax.swing.JTable();
        bdelete = new javax.swing.JButton();
        txtcari = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        bprint = new javax.swing.JButton();
        bcari = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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

        jPanel4.setBackground(new java.awt.Color(245, 245, 245));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, 240, 70));

        blogin_menu.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        blogin_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/enter_50px.png"))); // NOI18N
        blogin_menu.setText("  Masuk");
        blogin_menu.setBorder(null);
        blogin_menu.setBorderPainted(false);
        blogin_menu.setContentAreaFilled(false);
        blogin_menu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        blogin_menu.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        blogin_menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                blogin_menuActionPerformed(evt);
            }
        });
        jPanel2.add(blogin_menu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 240, 50));

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

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Login_perpustakaan/minilogotransaparant2.png"))); // NOI18N
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, -1, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 240, 400));

        tbuser.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Nama Lengkap", "Jenis Kelamin", "E - Mail", "No. Telepon", "Username", "Password"
            }
        ));
        tbuser.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tbuser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbuserMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbuser);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 70, 430, 260));

        bdelete.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        bdelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/delete_bin_20px.png"))); // NOI18N
        bdelete.setText("Hapus");
        bdelete.setBorder(null);
        bdelete.setBorderPainted(false);
        bdelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bdelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bdeleteActionPerformed(evt);
            }
        });
        jPanel1.add(bdelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 350, 80, 30));

        txtcari.setBackground(new java.awt.Color(245, 245, 245));
        txtcari.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtcari.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txtcari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcariKeyPressed(evt);
            }
        });
        jPanel1.add(txtcari, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 40, 220, 20));

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel1.setText("Username");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 20, -1, -1));

        bprint.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        bprint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/print_20px.png"))); // NOI18N
        bprint.setText("Cetak");
        bprint.setBorder(null);
        bprint.setBorderPainted(false);
        bprint.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bprint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bprintActionPerformed(evt);
            }
        });
        jPanel1.add(bprint, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 30, 90, 30));

        bcari.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        bcari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/search_in_list_20px.png"))); // NOI18N
        bcari.setText("  Cari");
        bcari.setBorder(null);
        bcari.setBorderPainted(false);
        bcari.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bcari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bcariActionPerformed(evt);
            }
        });
        jPanel1.add(bcari, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 30, 90, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 710, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

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

    private void bregis_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bregis_menuActionPerformed
        dispose();
        input_regist regis;
        regis = new input_regist();
        regis.setVisible(true);
    }//GEN-LAST:event_bregis_menuActionPerformed

    private void bkelolaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bkelolaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bkelolaActionPerformed

    private void blogin_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_blogin_menuActionPerformed
        dispose();
        login masuk;
        masuk = new login();
        masuk.setVisible(true);
    }//GEN-LAST:event_blogin_menuActionPerformed

    private void bprintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bprintActionPerformed
        print();
    }//GEN-LAST:event_bprintActionPerformed

    private void bcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bcariActionPerformed
        datatable();
    }//GEN-LAST:event_bcariActionPerformed

    private void bdeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bdeleteActionPerformed
        int ok = JOptionPane.showConfirmDialog(null, "hapus", "konfirmasi dialog",JOptionPane.YES_NO_OPTION);
        if (ok==0){
            String sql = "delete from tb_admin where id_admin like '"+txtcari.getText()+"'";
            try {
                PreparedStatement stat = conn.prepareStatement(sql);
                stat.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
                kosong();
                txtcari.requestFocus();
            } catch (SQLException e){
                JOptionPane.showMessageDialog(null,"Data Gagal Dihapus"+e);
            }
        }
        datatable();
    }//GEN-LAST:event_bdeleteActionPerformed

    private void tbuserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbuserMouseClicked
        int bar = tbuser.getSelectedRow();
        String a = tbuser.getValueAt(bar, 0). toString();
        String b = tbuser.getValueAt(bar, 1). toString();
        String c = tbuser.getValueAt(bar, 2). toString();
        String d = tbuser.getValueAt(bar, 3). toString();
        String e = tbuser.getValueAt(bar, 4). toString();
        String f = tbuser.getValueAt(bar, 5). toString();
        
        
        txtcari.setText(e);
    }//GEN-LAST:event_tbuserMouseClicked

    private void txtcariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcariKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcariKeyPressed

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
            java.util.logging.Logger.getLogger(form_kelolaUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(form_kelolaUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(form_kelolaUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(form_kelolaUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new form_kelolaUser().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bcari;
    private javax.swing.JButton bdelete;
    private javax.swing.JButton bexit;
    private javax.swing.JButton bkelola;
    private javax.swing.JButton blogin_menu;
    private javax.swing.JButton bprint;
    private javax.swing.JButton bregis_menu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbuser;
    private javax.swing.JTextField txtcari;
    // End of variables declaration//GEN-END:variables
}
