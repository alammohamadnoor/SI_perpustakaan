/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Menu_perpustakaan;

import Connection.koneksi;
import Login_perpustakaan.input_regist;
import Login_perpustakaan.login;
import java.io.File;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;


public class input_peminjaman extends javax.swing.JFrame {
    private Connection conn = new koneksi().connect();
    public static String nama;
    private DefaultTableModel TabelInpin, TabelDetil;
    String idUser, idAnggota, kd_buku, tanggalpinjam,tanggalkembali, idpin, idangg;
    int jmlpin; 
    int totpin;
    public String a1,a2,a5;
    public pilih_anggota pnjm1 = null;
    /**
     * Creates new form input_peminjaman
     */
    public input_peminjaman() {
        initComponents();
        txtnmlngkp.setText(input_regist.getnama());
        String KD = UserID.getUserLogin();
        txtnmlngkp.setText(KD);
        TABEL_PEMINJAMAN();
        kosong();
        aktif();
        comboIDUser();
        comboIDAnggota();
        comboKodeBuku();
        waktupinjam();
        waktukembali();
        auto();
        cbiduser.setSelectedItem(login.getusername());
    }
    
    public void kosongTbBuku (){
            DefaultTableModel model = (DefaultTableModel)TbBuku.getModel();
            if(TbBuku.getRowCount()>0){
                for (int i = TbBuku.getRowCount() - 1; i> -1; i--){
                    model.removeRow(i);
                }
            }
    }
    
    public void print () {
        try {
            Locale locale = new Locale( "id", "ID" );
            HashMap par = new HashMap();
            par.put(JRParameter.REPORT_LOCALE, locale);
            JasperPrint jp = JasperFillManager.fillReport
            (input_peminjaman.class.getResourceAsStream("/Menu_perpustakaan/Report_peminjamanBuku.jasper"), par, conn);
            JasperViewer.viewReport(jp, false);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane,"Dokumen Tidak Ada"+ex);
        }
    }
    
    public void printbuk () {
        try {
            Locale locale = new Locale( "id", "ID" );
            Map<String, Object> parameter = new HashMap <String, Object>();
            parameter.put("no_pinjam", txtcaripnjm.getText());
            File file = new File ("src/Menu_perpustakaan/Bukti_pinjamBuku.jrxml");
            //class.forName("com.mysql.jdbc.Driver");
            //Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/db_perpus","root","");
            parameter.put(JRParameter.REPORT_LOCALE, locale);
            JasperDesign jasperDesign = JRXmlLoader.load(file);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameter,conn);
            JasperViewer.viewReport(jasperPrint,false);    
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Data tidak dapat Dicetak"+ex.getMessage(),"Cetak Data", JOptionPane.ERROR_MESSAGE);
        }
    }    

    protected void aktif (){
        txtidpnjm.requestFocus();
        
    }
    
    protected void kosong(){
        txtidpnjm.setText("");
        cbiduser.setSelectedItem(null);
        txtnmuser.setText("");
        txtidangg.setText("");
        //cbidangg.setSelectedItem(null);
        txtnmangg.setText("");
        cbstatus.setSelectedItem(null);
        
        cbkdbk.setSelectedItem(null);
        txtjdlbk.setText("");
        txtpnls.setText("");
        txtpnrbt.setText("");
        txtjmlpnjm.setText("");
        
    }
    
    protected void TABEL_PEMINJAMAN(){
        Object [] Baris = {"No Pinjam","Tanggal Pinjam","Tanggal Kembali","ID Admin","ID Anggota","Kode Buku","Jumlah Pinjam","Status"};
            TabelInpin = new DefaultTableModel(null,Baris);
            String cariitem = txtcaripnjm.getText();
            
            try{
                String sql = "SELECT * FROM tb_pinjam where no_pinjam like '%"+cariitem+"%'";
                Statement stat = conn.createStatement();
                ResultSet hasil = stat.executeQuery(sql);
                while (hasil.next()){
                    TabelInpin.addRow(new Object [] {
                        hasil.getString(1),
                        hasil.getString(2),
                        hasil.getString(3),
                        hasil.getString(4),
                        hasil.getString(5),
                        hasil.getString(6),
                        hasil.getInt(7),
                        hasil.getString(8)
                    });
                }
                tbpinjam.setModel(TabelInpin);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "data gagal Dipanggil"+e);
            }
            
    }
    
    protected void comboIDUser (){
       cbiduser.removeAllItems();
       cbiduser.addItem("Pilih...");
       try {
          String sql ="select * from tb_admin";
          Statement st=conn.createStatement();
          ResultSet cuser = st.executeQuery(sql);
           while (cuser.next()) {
               String IdUser =cuser.getString("id_admin");
               cbiduser.addItem(IdUser);
           }
       } catch (Exception e) {
           JOptionPane.showMessageDialog(null,"Gagal Menampilkan Id User \n" +e.getMessage());
       }
   }
    
    protected void comboIDAnggota (){
       //cbidangg.removeAllItems();
       //cbidangg.addItem("Pilih...");
       try {
          String sql ="select * from tb_anggota";
          Statement st=conn.createStatement();
          ResultSet cangg = st.executeQuery(sql);
           while (cangg.next()) {
               String idAnggota =cangg.getString("id_anggota");
               //cbidangg.addItem(idAnggota);
           }
       } catch (Exception e) {
           JOptionPane.showMessageDialog(null,"Gagal Menampilkan Id User \n" +e.getMessage());
       }
   }
    
    protected void comboKodeBuku (){
       //cbkdbk.removeAllItems();
       cbkdbk.addItem("Pilih...");
       try {
          String sql ="select * from tb_buku";
          Statement st=conn.createStatement();
          ResultSet cbuku = st.executeQuery(sql);
           while (cbuku.next()) {
               String kd_buku =cbuku.getString("kode_buku");
               cbkdbk.addItem(kd_buku);
           }
       } catch (Exception e) {
           JOptionPane.showMessageDialog(null,"Gagal Menampilkan Id User \n" +e.getMessage());
       }
   }
    
    public void auto() {
    txtidpnjm.setText("");
        try {
            String sql1 = "select max(right(no_pinjam,3)) from tb_pinjam";
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql1);
            while(hasil.next()) {
                if(hasil.first()==false) {
                    txtidpnjm.setText("PJM001");
                    
                } else {
                    hasil.last();
                    int code = hasil.getInt(1)+1;
                    String Nomor = String.valueOf(code);
                    int noLong = Nomor.length();
                    
                    for (int a=0; a<3-noLong;a++) {
                        Nomor = "0" + Nomor;
                    }
                    txtidpnjm.setText("PJM" + Nomor);
                    
                }
            }            
            } catch (Exception e) {
        }
                   txtidpnjm.setEnabled(false);
                   //txtangg.requestFocus();
    }
    
    protected void TambahDetailPinjam(){
        try {
            DefaultTableModel tableModel = (DefaultTableModel)TbBuku.getModel();
            String[]data = new String[5];
            data[0]=String.valueOf(cbkdbk.getSelectedItem());
            data[1]=txtjdlbk.getText();
            data[2]=txtpnls.getText();
            data[3]=txtpnrbt.getText();
            data[4]=txtjmlpnjm.getText();
            
            tableModel.addRow(data);
    //        total_pinjam();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Eror Menampilkan Data.." +e.getMessage());
        }
    }
    
    public void waktupinjam(){
        Date tgl=new Date();
        jdpnjm.setDate(tgl);
    }
    
    public void waktukembali(){
        Date tgl1=new Date();
        jdkmbl.setDate(tgl1);
    }
    
    public void anggotaterpilih() {
    pilih_anggota pilsis = new pilih_anggota();
         pilsis.pnjm1 = this;
            txtidangg.setText(a1);
            txtnmangg.setText(a2);
    }
    
    //private void total_pinjam(){
    //    DefaultTableModel model = (DefaultTableModel)tbdetail_pinjam.getModel();
    //    int jumlah = model.getRowCount();
    //    int ttl = 0;
    //    //int jml = 0;
    //    
    //    for (int i = 0; i<jumlah; i++){
    //        totpin = Integer.valueOf(model.getValueAt(i,4).toString());
    //       jmlpin = Integer.parseInt(txtjmlpnjm.getText());
            
    //        ttl += totpin;
            //jml += jmlpin;
    //    }
        
    //    txttotalpnjm.setText(Integer.toString(ttl));
        //txtjmlpnjm.setText(Integer.toString(jml));
    //}
    

    
    //protected void simpandetail_peminjaman(){
    //    int jumlah_baris=tbdetail_pinjam.getRowCount();
    //    if (jumlah_baris == 0) {
    //        JOptionPane.showMessageDialog(rootPane, "Table Masih Kosong..!!");
    //    } else {
    //        try {
    //            int i=0;
    //            Statement stat = conn.createStatement();
    //            while(i<jumlah_baris){
    //                stat.executeUpdate("insert into detail_pinjam"
    //                        + "(no_pinjam,kode_buku,jumlah_pinjam,status)"
    //                        + "values('"+txtidpnjm.getText()+"',"
    //                        + "'"+tbdetail_pinjam.getValueAt(i,0)+"',"
    //                        + "'"+tbdetail_pinjam.getValueAt(i,4)+"',"
    //                        + "'"+cbstatus.getItemAt(cbstatus.getSelectedIndex())+"')");
    //                i++;
    //            }
    //        } catch (Exception e) {
    //           JOptionPane.showMessageDialog(rootPane, "Gagal Menyimpan..!!"+e);
    //        }
    //        
    //        DefaultTableModel model = (DefaultTableModel)tbdetail_pinjam.getModel();
    //        if(tbdetail_pinjam.getRowCount()>0){
    //            for (int i = tbdetail_pinjam.getRowCount() - 1; i> -1; i--){
    //                model.removeRow(i);
    //            }
    //        }
    //    }
    //}
    

    
    //public void ubahStatusDetail (){
    //    try {
    //        String SQL = "update detail_pinjam set status=? where no_pinjam = '"+txtcaripnjm.getText()+"'";
    //        PreparedStatement stut = conn.prepareStatement(SQL);
            //stut.setString(1, txtidpnjm.getText());
            //stut.setString(2, cbkdbk.getItemAt(cbkdbk.getSelectedIndex()));
            //stut.setString(3, txtjmlpnjm.getText());
    //        stut.setString(1, cbstatus.getItemAt(cbstatus.getSelectedIndex()));
                
    //        stut.executeUpdate();
    //        JOptionPane.showMessageDialog(null, "data berhasil Diubah");
            //kosong();
            //txtidpnjm.requestFocus();
            //auto();
    //    }
    //    catch (SQLException e){
    //        JOptionPane.showMessageDialog(null, "Data Gagal Diubah" +e);
    //    }
        //TABEL_PEMINJAMAN();
    //}
    

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
        jPanel3 = new javax.swing.JPanel();
        btentang = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        bexit = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        bdaftar_buku = new javax.swing.JButton();
        btambah_buku = new javax.swing.JButton();
        btambah_anggota = new javax.swing.JButton();
        txtnmlngkp = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        bpengembalian_buku = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtnmuser = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtidangg = new javax.swing.JTextField();
        txtnmangg = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cbstatus = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jdpnjm = new com.toedter.calendar.JDateChooser();
        jLabel10 = new javax.swing.JLabel();
        jdkmbl = new com.toedter.calendar.JDateChooser();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        cbkdbk = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        txtjdlbk = new javax.swing.JTextField();
        txtpnls = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtpnrbt = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtjmlpnjm = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TbBuku = new javax.swing.JTable();
        btmbhbk = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        txtidpnjm = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        cbiduser = new javax.swing.JComboBox<>();
        bcariangg = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbpinjam = new javax.swing.JTable();
        bcetakBukti = new javax.swing.JButton();
        bsave = new javax.swing.JButton();
        bedit = new javax.swing.JButton();
        bdelete = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        bcetakLaporan = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        txtcaripnjm = new javax.swing.JTextField();
        bcaripin = new javax.swing.JButton();
        bcancel2 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(245, 245, 245));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(0, 204, 102));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(245, 245, 245));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 240, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 530, 240, -1));

        btentang.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        btentang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/about_50px.png"))); // NOI18N
        btentang.setText("  Tentang");
        btentang.setBorder(null);
        btentang.setContentAreaFilled(false);
        btentang.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btentang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btentang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btentangActionPerformed(evt);
            }
        });
        jPanel2.add(btentang, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 550, 240, 50));

        jButton10.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/borrow_book_50px.png"))); // NOI18N
        jButton10.setText(" Peminjaman Buku");
        jButton10.setBorder(null);
        jButton10.setContentAreaFilled(false);
        jButton10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 330, 240, 50));

        bexit.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        bexit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/exit_50px.png"))); // NOI18N
        bexit.setText(" Keluar");
        bexit.setBorder(null);
        bexit.setContentAreaFilled(false);
        bexit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bexit.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        bexit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bexitActionPerformed(evt);
            }
        });
        jPanel2.add(bexit, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 470, 240, 50));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/administrator_male_30px.png"))); // NOI18N
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        jPanel4.setBackground(new java.awt.Color(245, 245, 245));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 320, 240, 70));

        bdaftar_buku.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        bdaftar_buku.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/books_50px.png"))); // NOI18N
        bdaftar_buku.setText(" Daftar Buku");
        bdaftar_buku.setBorder(null);
        bdaftar_buku.setBorderPainted(false);
        bdaftar_buku.setContentAreaFilled(false);
        bdaftar_buku.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bdaftar_buku.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        bdaftar_buku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bdaftar_bukuActionPerformed(evt);
            }
        });
        jPanel2.add(bdaftar_buku, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 240, 50));

        btambah_buku.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        btambah_buku.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/add_book_50px.png"))); // NOI18N
        btambah_buku.setText(" Tambah Buku");
        btambah_buku.setBorder(null);
        btambah_buku.setContentAreaFilled(false);
        btambah_buku.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btambah_buku.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btambah_buku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btambah_bukuActionPerformed(evt);
            }
        });
        jPanel2.add(btambah_buku, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, 240, 50));

        btambah_anggota.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        btambah_anggota.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/add_user_male_50px.png"))); // NOI18N
        btambah_anggota.setText(" Tambah Anggota");
        btambah_anggota.setBorder(null);
        btambah_anggota.setContentAreaFilled(false);
        btambah_anggota.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btambah_anggota.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btambah_anggota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btambah_anggotaActionPerformed(evt);
            }
        });
        jPanel2.add(btambah_anggota, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 260, 240, 50));

        txtnmlngkp.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtnmlngkp.setText("Nama Lengkap Admin");
        jPanel2.add(txtnmlngkp, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/borrow_book1_50px.png"))); // NOI18N
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 0, -1, -1));

        bpengembalian_buku.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        bpengembalian_buku.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/return_book_50px.png"))); // NOI18N
        bpengembalian_buku.setText(" Pengembalian Buku");
        bpengembalian_buku.setBorder(null);
        bpengembalian_buku.setContentAreaFilled(false);
        bpengembalian_buku.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bpengembalian_buku.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        bpengembalian_buku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bpengembalian_bukuActionPerformed(evt);
            }
        });
        jPanel2.add(bpengembalian_buku, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 400, 240, 50));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 240, 630));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("ID Admin");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 240, -1, -1));

        txtnmuser.setBackground(new java.awt.Color(245, 245, 245));
        txtnmuser.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtnmuser.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtnmuser, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 320, 360, 30));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Nama Admin");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 300, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("ID Anggota");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 360, -1, -1));

        txtidangg.setBackground(new java.awt.Color(245, 245, 245));
        txtidangg.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtidangg.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txtidangg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtidanggKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtidanggKeyTyped(evt);
            }
        });
        jPanel1.add(txtidangg, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 380, 250, 30));

        txtnmangg.setBackground(new java.awt.Color(245, 245, 245));
        txtnmangg.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtnmangg.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtnmangg, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 440, 360, 30));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Nama Anggota");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 420, -1, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Tanggal Pinjam");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 120, -1, -1));

        cbstatus.setBackground(new java.awt.Color(245, 245, 245));
        cbstatus.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cbstatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih ...", "Pinjam", "Kembali" }));
        jPanel1.add(cbstatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 500, 360, 30));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Status");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 480, -1, -1));

        jdpnjm.setDateFormatString("yyyy-MM-dd");
        jdpnjm.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jdpnjmPropertyChange(evt);
            }
        });
        jPanel1.add(jdpnjm, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 140, 360, 30));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Tanggal Kembali");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 180, -1, -1));

        jdkmbl.setDateFormatString("yyyy-MM-dd");
        jdkmbl.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jdkmblPropertyChange(evt);
            }
        });
        jPanel1.add(jdkmbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 200, 360, 30));

        jPanel5.setBackground(new java.awt.Color(51, 51, 51));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Kode Buku");
        jPanel5.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 20));

        cbkdbk.setBackground(new java.awt.Color(245, 245, 245));
        cbkdbk.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        cbkdbk.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbkdbkItemStateChanged(evt);
            }
        });
        cbkdbk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbkdbkActionPerformed(evt);
            }
        });
        jPanel5.add(cbkdbk, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 190, 20));

        jLabel12.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Penerbit");
        jPanel5.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, -1, -1));

        txtjdlbk.setBackground(new java.awt.Color(51, 51, 51));
        txtjdlbk.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtjdlbk.setForeground(new java.awt.Color(255, 255, 255));
        txtjdlbk.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(245, 245, 245)));
        txtjdlbk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtjdlbkActionPerformed(evt);
            }
        });
        jPanel5.add(txtjdlbk, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 300, 20));

        txtpnls.setBackground(new java.awt.Color(51, 51, 51));
        txtpnls.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtpnls.setForeground(new java.awt.Color(255, 255, 255));
        txtpnls.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(245, 245, 245)));
        jPanel5.add(txtpnls, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 300, 20));

        jLabel13.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Judul Buku");
        jPanel5.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        txtpnrbt.setBackground(new java.awt.Color(51, 51, 51));
        txtpnrbt.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtpnrbt.setForeground(new java.awt.Color(255, 255, 255));
        txtpnrbt.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(245, 245, 245)));
        jPanel5.add(txtpnrbt, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 300, 20));

        jLabel15.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Jumlah Pinjam");
        jPanel5.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, -1, -1));

        txtjmlpnjm.setBackground(new java.awt.Color(51, 51, 51));
        txtjmlpnjm.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtjmlpnjm.setForeground(new java.awt.Color(255, 255, 255));
        txtjmlpnjm.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(245, 245, 245)));
        jPanel5.add(txtjmlpnjm, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 190, 20));

        jLabel16.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Penulis");
        jPanel5.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, -1, -1));

        TbBuku.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kode Buku", "Judul", "Penulis", "Penerbit", "Jumlah  Pinjam"
            }
        ));
        TbBuku.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane2.setViewportView(TbBuku);

        jPanel5.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 10, 390, 180));

        btmbhbk.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        btmbhbk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/add_20px.png"))); // NOI18N
        btmbhbk.setText("Tambah");
        btmbhbk.setBorder(null);
        btmbhbk.setBorderPainted(false);
        btmbhbk.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btmbhbk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btmbhbkActionPerformed(evt);
            }
        });
        jPanel5.add(btmbhbk, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 220, 90, 30));

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 0, 720, 270));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("No. Pinjam");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 60, -1, -1));

        txtidpnjm.setBackground(new java.awt.Color(245, 245, 245));
        txtidpnjm.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtidpnjm.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtidpnjm, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 80, 200, 30));

        jTextField7.setBackground(new java.awt.Color(245, 245, 245));
        jTextField7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextField7.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jPanel1.add(jTextField7, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 80, 130, 30));

        cbiduser.setBackground(new java.awt.Color(245, 245, 245));
        cbiduser.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cbiduser.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih ...", "X", "XI", "XII" }));
        cbiduser.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbiduserItemStateChanged(evt);
            }
        });
        cbiduser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbiduserActionPerformed(evt);
            }
        });
        jPanel1.add(cbiduser, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 260, 360, 30));

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
        jPanel1.add(bcariangg, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 380, 90, 30));

        tbpinjam.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "No. Pinjam", "Tanggal Pinjam", "Tanggal Kembali", "ID Admin", "ID Anggota", "Kode Buku", "Jumlah Pinjam", "Status"
            }
        ));
        tbpinjam.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tbpinjam.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbpinjamMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbpinjam);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 330, 720, 220));

        bcetakBukti.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        bcetakBukti.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/print_20px.png"))); // NOI18N
        bcetakBukti.setText("Bukti");
        bcetakBukti.setBorder(null);
        bcetakBukti.setBorderPainted(false);
        bcetakBukti.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bcetakBukti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bcetakBuktiActionPerformed(evt);
            }
        });
        jPanel1.add(bcetakBukti, new org.netbeans.lib.awtextra.AbsoluteConstraints(1200, 560, 80, 30));

        bsave.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        bsave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/save_20px.png"))); // NOI18N
        bsave.setText("Simpan");
        bsave.setBorder(null);
        bsave.setBorderPainted(false);
        bsave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bsave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bsaveActionPerformed(evt);
            }
        });
        jPanel1.add(bsave, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 560, 80, 30));

        bedit.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        bedit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/edit_20px.png"))); // NOI18N
        bedit.setText("Ubah");
        bedit.setBorder(null);
        bedit.setBorderPainted(false);
        bedit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bedit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                beditActionPerformed(evt);
            }
        });
        jPanel1.add(bedit, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 560, 80, 30));

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
        jPanel1.add(bdelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 560, 80, 30));

        jButton4.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/back_arrow_20px.png"))); // NOI18N
        jButton4.setText("Kembali");
        jButton4.setBorder(null);
        jButton4.setBorderPainted(false);
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, 80, 30));

        bcetakLaporan.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        bcetakLaporan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/print_20px.png"))); // NOI18N
        bcetakLaporan.setText("Laporan");
        bcetakLaporan.setBorder(null);
        bcetakLaporan.setBorderPainted(false);
        bcetakLaporan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bcetakLaporan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bcetakLaporanActionPerformed(evt);
            }
        });
        jPanel1.add(bcetakLaporan, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 290, 90, 30));

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setText("No. Pinjam");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 280, -1, -1));

        txtcaripnjm.setBackground(new java.awt.Color(245, 245, 245));
        txtcaripnjm.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtcaripnjm.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        txtcaripnjm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcaripnjmActionPerformed(evt);
            }
        });
        jPanel1.add(txtcaripnjm, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 300, 250, 20));

        bcaripin.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        bcaripin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/search_in_list_20px.png"))); // NOI18N
        bcaripin.setText("  Cari");
        bcaripin.setBorder(null);
        bcaripin.setBorderPainted(false);
        bcaripin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bcaripin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bcaripinActionPerformed(evt);
            }
        });
        jPanel1.add(bcaripin, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 290, 90, 30));

        bcancel2.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        bcancel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/clear_symbol_20px.png"))); // NOI18N
        bcancel2.setText("Kosong");
        bcancel2.setBorder(null);
        bcancel2.setBorderPainted(false);
        bcancel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bcancel2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bcancel2ActionPerformed(evt);
            }
        });
        jPanel1.add(bcancel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 560, 80, 30));

        jPanel6.setBackground(new java.awt.Color(245, 245, 245));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 390, -1, 70));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1360, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 610, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setSize(new java.awt.Dimension(1376, 649));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btentangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btentangActionPerformed
        dispose();
        form_tentang fote;
        fote = new form_tentang();
        fote.setVisible(true);
    }//GEN-LAST:event_btentangActionPerformed

    private void btambah_anggotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btambah_anggotaActionPerformed
        dispose();
        input_anggota insis;
        insis = new input_anggota();
        insis.setVisible(true);
    }//GEN-LAST:event_btambah_anggotaActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton10ActionPerformed

    private void bexitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bexitActionPerformed
        int tny = JOptionPane.showConfirmDialog(null, "Yakin Ingin Keluar ?","Tanya",JOptionPane.YES_NO_OPTION);
        if (tny==0) {     
            try {
                dispose();
                dispose();
            } catch (Exception e) {
            }
        }
        new Login_perpustakaan.login().setVisible(true);
    }//GEN-LAST:event_bexitActionPerformed

    private void bdaftar_bukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bdaftar_bukuActionPerformed
        dispose();
        daftar_buku dabuk;
        dabuk = new daftar_buku();
        dabuk.setVisible(true);
    }//GEN-LAST:event_bdaftar_bukuActionPerformed

    private void btambah_bukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btambah_bukuActionPerformed
        dispose();
        input_buku inbuk;
        inbuk = new input_buku();
        inbuk.setVisible(true);
    }//GEN-LAST:event_btambah_bukuActionPerformed

    private void btmbhbkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btmbhbkActionPerformed
        TambahDetailPinjam();
    //    total_pinjam();

    }//GEN-LAST:event_btmbhbkActionPerformed

    private void cbkdbkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbkdbkActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbkdbkActionPerformed

    private void bcarianggActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bcarianggActionPerformed
        pilih_anggota pilsis =new pilih_anggota();
        pilsis.pnjm1=this;
        pilsis.setVisible(true);
        pilsis.setResizable(false);     
    }//GEN-LAST:event_bcarianggActionPerformed

    private void txtjdlbkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtjdlbkActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtjdlbkActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void bsaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bsaveActionPerformed
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        tanggalpinjam =format.format(jdpnjm.getDate());
        tanggalkembali =format1.format(jdkmbl.getDate());
        jmlpin=Integer.parseInt (txtjmlpnjm.getText());
        String sql = "insert into tb_pinjam values (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, txtidpnjm.getText());
            stat.setString(2, tanggalpinjam);
            stat.setString(3, tanggalkembali);
            stat.setString(4, cbiduser.getItemAt(cbiduser.getSelectedIndex()));
            stat.setString(5, txtidangg.getText());
            stat.setString(6, cbkdbk.getItemAt(cbkdbk.getSelectedIndex()));
            stat.setInt(7, jmlpin);
            stat.setString(8, cbstatus.getItemAt(cbstatus.getSelectedIndex()));
            
        //    simpandetail_peminjaman();
            kosongTbBuku();
            stat.executeUpdate();
            JOptionPane.showMessageDialog(null, "data berhasil Disimpan");
            kosong();
            txtidpnjm.requestFocus();
            auto();
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null, "data gagal disimpan"+e);
        }
        TABEL_PEMINJAMAN();
        
    }//GEN-LAST:event_bsaveActionPerformed

    private void beditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_beditActionPerformed
        jmlpin=Integer.parseInt (txtjmlpnjm.getText());
        try {
            String sql = "update tb_pinjam set tgl_pinjam=?, tgl_kembali=?, id_admin=?, id_anggota=?,kode_buku=?, jumlah_pinjam=?, status=? where no_pinjam = '"+txtcaripnjm.getText()+"'";
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, tanggalpinjam);
            stat.setString(2, tanggalkembali);
            stat.setString(3, cbiduser.getItemAt(cbiduser.getSelectedIndex()));
            stat.setString(4, txtidangg.getText());
            stat.setString(5, cbkdbk.getItemAt(cbkdbk.getSelectedIndex()));
            stat.setInt(6, jmlpin);
            stat.setString(7, cbstatus.getItemAt(cbstatus.getSelectedIndex()));
            
                //String SQL = "update detail_pinjam set kode_buku=?, jumlah_pinjam=?, status=? where no_pinjam ='"+txtcaripnjm.getText()+"'";
                //PreparedStatement stut = conn.prepareStatement(SQL);
                //stut.setString(1, cbkdbk.getItemAt(cbkdbk.getSelectedIndex()));
                //stut.setString(2, txtjmlpnjm.getText());
                //stut.setString(3, cbstatus.getItemAt(cbstatus.getSelectedIndex()));
                
            //ubahStatusDetail();    
            stat.executeUpdate();
            JOptionPane.showMessageDialog(null, "data berhasil Diubah");
            kosong();
            kosongTbBuku();
            txtidpnjm.requestFocus();
            auto();
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Data Gagal Diubah" +e);
        }
        TABEL_PEMINJAMAN();

    }//GEN-LAST:event_beditActionPerformed

    private void bdeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bdeleteActionPerformed
        int ok = JOptionPane.showConfirmDialog(null, "hapus", "konfirmasi dialog",JOptionPane.YES_NO_OPTION);
        if (ok==0){
            String sql = "delete from tb_pinjam where no_pinjam= '"+txtidpnjm.getText()+"'";
            try {
                PreparedStatement stat = conn.prepareStatement(sql);
                stat.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
                kosong();
                auto();
                txtidpnjm.requestFocus();
            } catch (SQLException e){
                JOptionPane.showMessageDialog(null,"Data Gagal Dihapus"+e);
            }
            TABEL_PEMINJAMAN();
        }
    }//GEN-LAST:event_bdeleteActionPerformed

    private void bcetakLaporanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bcetakLaporanActionPerformed
        print();
    }//GEN-LAST:event_bcetakLaporanActionPerformed

    private void cbkdbkItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbkdbkItemStateChanged
        try {
            String sql = "select * from tb_buku where kode_buku='"+ cbkdbk.getSelectedItem()+"'";
            Statement st=conn.createStatement();
            ResultSet rsbuku = st.executeQuery(sql);
            while (rsbuku.next()) {
                txtjdlbk.setText(rsbuku.getString(2));
                txtpnls.setText(rsbuku.getString(4));
                txtpnrbt.setText(rsbuku.getString(5));
                bdelete.enable(true);
                bedit.enable(true);
                } 
        } catch (Exception e) {
        }
    }//GEN-LAST:event_cbkdbkItemStateChanged

    private void cbiduserItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbiduserItemStateChanged
        try {
            String sql = "select * from tb_admin where id_admin ='"+ cbiduser.getSelectedItem()+"'";
            Statement st=conn.createStatement();
            ResultSet rsiduser = st.executeQuery(sql);
            while (rsiduser.next()) {
                txtnmuser.setText(rsiduser.getString(3));
                } 
        } catch (Exception e) {
        }
    }//GEN-LAST:event_cbiduserItemStateChanged

    private void tbpinjamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbpinjamMouseClicked
        int bar = tbpinjam.getSelectedRow();
        String a = tbpinjam.getValueAt(bar, 0). toString();
        String b = tbpinjam.getValueAt(bar, 1). toString();
        String c = tbpinjam.getValueAt(bar, 2). toString();
        String d = tbpinjam.getValueAt(bar, 3). toString();
        String e = tbpinjam.getValueAt(bar, 4). toString();
        String f = tbpinjam.getValueAt(bar, 5). toString();
        String g = tbpinjam.getValueAt(bar, 6). toString();
        String h = tbpinjam.getValueAt(bar, 7). toString();
        
        txtcaripnjm.setText(a);
        txtidpnjm.setText(a);
        jdpnjm.setDateFormatString(b);
        jdkmbl.setDateFormatString(c);        
        cbiduser.setSelectedItem(d);
        txtidangg.setText(e);
        cbkdbk.setSelectedItem(f);
        txtjmlpnjm.setText(g);
        cbstatus.setSelectedItem(h);
    }//GEN-LAST:event_tbpinjamMouseClicked

    private void bcaripinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bcaripinActionPerformed
        TABEL_PEMINJAMAN();
    }//GEN-LAST:event_bcaripinActionPerformed

    private void txtcaripnjmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcaripnjmActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcaripnjmActionPerformed

    private void txtidanggKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtidanggKeyPressed
        
    }//GEN-LAST:event_txtidanggKeyPressed

    private void txtidanggKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtidanggKeyTyped
        idangg=txtidangg.getText();
        int ascii = evt.getKeyCode();
        if(ascii==10){
            try {
                String sql="select * from tb_anggota where id_anggota ='"+ idangg +"'";
                Statement st=conn.createStatement();
                ResultSet rsidangg = st.executeQuery(sql);
                while (rsidangg.next()){
                    txtidangg.setText(rsidangg.getString(4));
                    txtnmuser.setText(rsidangg.getString(5));
                    bdelete.enable(true);
                    bedit.enable(true);

                }
            } catch (Exception e) {
                JOptionPane.showConfirmDialog(null, "Data Tidak Di Temukan ....!! \n"
                    +e.getMessage());
                jdpnjm.requestFocus();
            }
        }
    }//GEN-LAST:event_txtidanggKeyTyped

    private void bcetakBuktiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bcetakBuktiActionPerformed
        printbuk();
    }//GEN-LAST:event_bcetakBuktiActionPerformed

    private void jdkmblPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jdkmblPropertyChange
        if (jdkmbl.getDate()!=null) {
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            tanggalkembali =format1.format(jdkmbl.getDate());
        }
    }//GEN-LAST:event_jdkmblPropertyChange

    private void jdpnjmPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jdpnjmPropertyChange
        if (jdpnjm.getDate()!=null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            tanggalpinjam =format.format(jdpnjm.getDate());
        }
    }//GEN-LAST:event_jdpnjmPropertyChange

    private void bcancel2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bcancel2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bcancel2ActionPerformed

    private void bpengembalian_bukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bpengembalian_bukuActionPerformed
        dispose();
        input_pengembalian inpeng;
        inpeng = new input_pengembalian();
        inpeng.setVisible(true);
    }//GEN-LAST:event_bpengembalian_bukuActionPerformed

    private void cbiduserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbiduserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbiduserActionPerformed

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
            java.util.logging.Logger.getLogger(input_peminjaman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(input_peminjaman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(input_peminjaman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(input_peminjaman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new input_peminjaman().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TbBuku;
    private javax.swing.JButton bcancel2;
    private javax.swing.JButton bcariangg;
    private javax.swing.JButton bcaripin;
    private javax.swing.JButton bcetakBukti;
    private javax.swing.JButton bcetakLaporan;
    private javax.swing.JButton bdaftar_buku;
    private javax.swing.JButton bdelete;
    private javax.swing.JButton bedit;
    private javax.swing.JButton bexit;
    private javax.swing.JButton bpengembalian_buku;
    private javax.swing.JButton bsave;
    private javax.swing.JButton btambah_anggota;
    private javax.swing.JButton btambah_buku;
    private javax.swing.JButton btentang;
    private javax.swing.JButton btmbhbk;
    private javax.swing.JComboBox<String> cbiduser;
    private javax.swing.JComboBox<String> cbkdbk;
    private javax.swing.JComboBox<String> cbstatus;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField7;
    private com.toedter.calendar.JDateChooser jdkmbl;
    private com.toedter.calendar.JDateChooser jdpnjm;
    private javax.swing.JTable tbpinjam;
    private javax.swing.JTextField txtcaripnjm;
    private javax.swing.JTextField txtidangg;
    private javax.swing.JTextField txtidpnjm;
    private javax.swing.JTextField txtjdlbk;
    private javax.swing.JTextField txtjmlpnjm;
    private javax.swing.JTextField txtnmangg;
    private javax.swing.JLabel txtnmlngkp;
    private javax.swing.JTextField txtnmuser;
    private javax.swing.JTextField txtpnls;
    private javax.swing.JTextField txtpnrbt;
    // End of variables declaration//GEN-END:variables
}
