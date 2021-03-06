/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Menu_perpustakaan;

import Connection.koneksi;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class pilih_anggota extends javax.swing.JFrame {
    private Connection conn = new koneksi().connect();
    private DefaultTableModel tabmode;
    public input_peminjaman pnjm1= null;
    //public input_pengembalian kmbl= null;
    /**
     * Creates new form pilih_siswa
     */
    public pilih_anggota() {
        initComponents();
        setLocationRelativeTo(this);
        datatable();
    }
    
    protected void datatable(){
         Object [] Baris = {"ID Anggota","Nama Anggota","Kelas","Jenis Kelamin","Alamat","No. Telepon",};
            tabmode = new DefaultTableModel(null,Baris);
            String cariitem = txtcari.getText();
            
            try{
                String sql = "SELECT * FROM tb_anggota where id_anggota like '%"+cariitem+"%' or nama_anggota like '%"+cariitem+"%'";
                Statement stat = conn.createStatement();
                ResultSet hasil = stat.executeQuery(sql);
                while (hasil.next()){
                    tabmode.addRow(new Object [] {
                        hasil.getString(1),
                        hasil.getString(2),
                        hasil.getString(3),
                        hasil.getString(4),
                        hasil.getString(5),
                        hasil.getString(6)
                    });
                }
                tbpilih_angg.setModel(tabmode);
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tbpilih_angg = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        txtcari = new javax.swing.JTextField();
        bkembali = new javax.swing.JButton();
        bcariangg = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(245, 245, 245));

        tbpilih_angg.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbpilih_angg.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tbpilih_angg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbpilih_anggMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbpilih_angg);

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setText("Nama/ID Anggota");

        txtcari.setBackground(new java.awt.Color(245, 245, 245));
        txtcari.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtcari.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txtcari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcariActionPerformed(evt);
            }
        });

        bkembali.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        bkembali.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/back_arrow_20px.png"))); // NOI18N
        bkembali.setText("Kembali");
        bkembali.setBorder(null);
        bkembali.setBorderPainted(false);
        bkembali.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bkembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bkembaliActionPerformed(evt);
            }
        });

        bcariangg.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        bcariangg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/search_in_list_20px.png"))); // NOI18N
        bcariangg.setText("  Cari");
        bcariangg.setBorder(null);
        bcariangg.setBorderPainted(false);
        bcariangg.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bcariangg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bcarianggActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(bcariangg, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 294, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bkembali, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 667, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bkembali, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(9, 9, 9)
                        .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(bcariangg, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 690, 410));

        setSize(new java.awt.Dimension(705, 454));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcariActionPerformed

    private void bkembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bkembaliActionPerformed
        dispose();
    }//GEN-LAST:event_bkembaliActionPerformed

    private void tbpilih_anggMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbpilih_anggMouseClicked
        int bar = tbpilih_angg.getSelectedRow();
        pnjm1.a1 = tabmode.getValueAt(bar,0).toString();
        pnjm1.a2 = tabmode.getValueAt (bar, 1).toString();
        //pnjm1.a5 = tabmode.getValueAt (bar, 4).toString();

        pnjm1.anggotaterpilih();
        this.dispose();
    }//GEN-LAST:event_tbpilih_anggMouseClicked

    private void bcarianggActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bcarianggActionPerformed
        datatable();
    }//GEN-LAST:event_bcarianggActionPerformed

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
            java.util.logging.Logger.getLogger(pilih_anggota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(pilih_anggota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(pilih_anggota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(pilih_anggota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new pilih_anggota().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bcariangg;
    private javax.swing.JButton bkembali;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbpilih_angg;
    private javax.swing.JTextField txtcari;
    // End of variables declaration//GEN-END:variables
}
