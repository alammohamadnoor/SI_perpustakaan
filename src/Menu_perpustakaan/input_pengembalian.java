/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Menu_perpustakaan;

import Connection.koneksi;
import Login_perpustakaan.input_regist;
import Login_perpustakaan.login;
import Menu_perpustakaan.input_peminjaman;
import Menu_perpustakaan.pilih_peminjaman;
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
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class input_pengembalian extends javax.swing.JFrame {
    private Connection conn = new koneksi().connect();
    public static String nama;
    private DefaultTableModel TabelPeng;
    String idUser, idAnggota, kd_buku, tanggalpinjam,tanggalkembali, idpin, idangg;
    int jmlpin, totpin, terlambat, denda;
    public String a1,a2,a3,a4,a5,a6,a7,a8;
    //public int a6;
    public pilih_anggota1 kmbl = null;
    public pilih_peminjaman piljam = null;
    
    /**
     * Creates new form input_pengembalian
     */
    public input_pengembalian() {
        initComponents();
        txtnmlngkp.setText(input_regist.getnama());
        String KD = UserID.getUserLogin();
        txtnmlngkp.setText(KD);
        TABEL_PENGEMBALIAN();
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
    
    public void print () {
        try {
            Locale locale = new Locale( "id", "ID" );
            HashMap par = new HashMap();
            par.put(JRParameter.REPORT_LOCALE, locale);
            JasperPrint jp = JasperFillManager.fillReport
            (input_peminjaman.class.getResourceAsStream("/Menu_perpustakaan/Report_pengembalianBuku.jasper"), par, conn);
            JasperViewer.viewReport(jp, false);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane,"Dokumen Tidak Ada"+ex);
        }
    }
    
    public void printbuk () {
        try {
            Locale locale = new Locale( "id", "ID" );
            Map<String, Object> parameter = new HashMap <String, Object>();
            parameter.put("no_pengembalian", txtcaripeng.getText());
            File file = new File ("src/Menu_perpustakaan/Bukti_pengembalianBuku.jrxml");
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
        cbidangg.setSelectedItem(null);
        //cbidangg.setSelectedItem(null);
        txtnmangg.setText("");
        //cbtglkmbl.setSelectedItem(null);
        
        cbkdbk.setSelectedItem(null);
        txtjdlbk.setText("");
        txtpnls.setText("");
        txtpnrbt.setText("");
        txtjmlpnjm.setText("");
        
        txtidkmbl.setText("");
        txtketerlambatan.setText("");
        txtdenda.setText("");
        //txt.setText("");
    }
    
    protected void TABEL_PENGEMBALIAN(){
        Object [] Baris = {"No Pengembalian","No Pinjam","ID Admin","ID Anggota",
            "Tanggal Pinjam","Tanggal Kembali","Kode Buku","Jumlah Pinjam","Keterlambatan",
            "Denda","Status"};
            TabelPeng = new DefaultTableModel(null,Baris);
            String cariitem = txtcaripeng.getText();
            
            try{
                String sql = "SELECT * FROM tb_pengembalian where no_pengembalian like '%"+cariitem+"%'";
                Statement stat = conn.createStatement();
                ResultSet hasil = stat.executeQuery(sql);
                while (hasil.next()){
                    TabelPeng.addRow(new Object [] {
                        hasil.getString(1),
                        hasil.getString(2),
                        hasil.getString(3),
                        hasil.getString(4),
                        hasil.getString(5),
                        hasil.getString(6),
                        hasil.getString(7),
                        hasil.getInt(8),
                        hasil.getInt(9),
                        hasil.getInt(10),
                        hasil.getString(11)
                    });
                }
                tbkembali.setModel(TabelPeng);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "data gagal Dipanggil"+e);
            }       
    }
    
    
    protected void TambahBuku(){
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
    
    public void kosongTbBuku (){
            DefaultTableModel model = (DefaultTableModel)TbBuku.getModel();
            if(TbBuku.getRowCount()>0){
                for (int i = TbBuku.getRowCount() - 1; i> -1; i--){
                    model.removeRow(i);
                }
            }
    }    
    
    protected void comboIDUser (){
//        cbidangg.removeAllItems();
//        cbidangg.addItem("Pilih...");
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
    //   cbidangg.removeAllItems();
    //   cbidangg.addItem("Pilih...");
       try {
          String sql ="select * from tb_anggota";
          Statement st=conn.createStatement();
          ResultSet cangg = st.executeQuery(sql);
           while (cangg.next()) {
               String idAnggota =cangg.getString("id_anggota");
               cbidangg.addItem(idAnggota);
           }
       } catch (Exception e) {
           JOptionPane.showMessageDialog(null,"Gagal Menampilkan Id Anggota \n" +e.getMessage());
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
            String sql1 = "select max(right(no_pengembalian,3)) from tb_pengembalian";
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql1);
            while(hasil.next()) {
                if(hasil.first()==false) {
                    txtidkmbl.setText("KBL001");
                    
                } else {
                    hasil.last();
                    int code = hasil.getInt(1)+1;
                    String Nomor = String.valueOf(code);
                    int noLong = Nomor.length();
                    
                    for (int a=0; a<3-noLong;a++) {
                        Nomor = "0" + Nomor;
                    }
                    txtidkmbl.setText("KBL" + Nomor);
                    
                }
            }            
            } catch (Exception e) {
        }
                   txtidkmbl.setEnabled(false);
                   //txtangg.requestFocus();
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
        pilih_anggota1 pilsis1 = new pilih_anggota1();
        pilsis1.kmbl = this;
            cbidangg.setSelectedItem(a1);
            txtnmangg.setText(a2);
    }
    
    public void peminjamanterpilih() {
        pilih_peminjaman pil_jam = new pilih_peminjaman();
        pil_jam.piljam = this;
            txtidpnjm.setText(a1);
            jdpnjm.setDate(java.sql.Date.valueOf((a2)));
            jdkmbl.setDate(java.sql.Date.valueOf((a3)));
            cbiduser.setSelectedItem(a4);
            cbidangg.setSelectedItem(a5);
            cbkdbk.setSelectedItem(a6);
            txtjmlpnjm.setText(a7);
            cbstatus.setSelectedItem(a8);
            //jdpnjm.setDate(java.sql.Date.valueOf(tbpilih_peminjaman.getValueAt(bar, 1).toString())); 
    }
    
    public void denda (){
        int jmlpin, terlambat, hasil;
        int denda = 10000;
        
        jmlpin = Integer.parseInt(txtjmlpnjm.getText());
        terlambat = Integer.parseInt(txtketerlambatan.getText());
        
        hasil = denda * jmlpin * terlambat;
        txtdenda.setText(Integer.toString(hasil));
    }
    
    public void bcariangg (){
        pilih_anggota1 pilsis1 =new pilih_anggota1();
        pilsis1.kmbl=this;
        pilsis1.setVisible(true);
        pilsis1.setResizable(false);    
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
        jPanel3 = new javax.swing.JPanel();
        btentang = new javax.swing.JButton();
        btambah_anggota = new javax.swing.JButton();
        bpinjam_buku = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        bdaftar_buku = new javax.swing.JButton();
        txtnmlngkp = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        bexit = new javax.swing.JButton();
        btambah_buku = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        bpengembalian_buku = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtnmuser = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtnmangg = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtidpnjm = new javax.swing.JTextField();
        cbidangg = new javax.swing.JComboBox<>();
        bcaripnjm = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        txtidkmbl = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbkembali = new javax.swing.JTable();
        jLabel16 = new javax.swing.JLabel();
        txtcaripeng = new javax.swing.JTextField();
        bcancel = new javax.swing.JButton();
        bsave = new javax.swing.JButton();
        bedit = new javax.swing.JButton();
        bdelete = new javax.swing.JButton();
        cbstatus = new javax.swing.JComboBox<>();
        jdkmbl = new com.toedter.calendar.JDateChooser();
        jdpnjm = new com.toedter.calendar.JDateChooser();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        cbkdbk = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        txtjdlbk = new javax.swing.JTextField();
        txtpnls = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtpnrbt = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtjmlpnjm = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        TbBuku = new javax.swing.JTable();
        btmbhbk = new javax.swing.JButton();
        txtketerlambatan = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        txtdenda = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        hitung_denda = new javax.swing.JButton();
        cbiduser = new javax.swing.JComboBox<>();
        bcari = new javax.swing.JButton();
        bcetakBukti = new javax.swing.JButton();
        bcetakLaporan = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(245, 245, 245));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(245, 245, 245));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(0, 204, 102));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(245, 245, 245));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 530, 240, 10));

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

        bpinjam_buku.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        bpinjam_buku.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/borrow_book_50px.png"))); // NOI18N
        bpinjam_buku.setText(" Peminjaman Buku");
        bpinjam_buku.setBorder(null);
        bpinjam_buku.setContentAreaFilled(false);
        bpinjam_buku.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bpinjam_buku.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        bpinjam_buku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bpinjam_bukuActionPerformed(evt);
            }
        });
        jPanel2.add(bpinjam_buku, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 330, 240, 50));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/administrator_male_30px.png"))); // NOI18N
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

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

        txtnmlngkp.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtnmlngkp.setText("Nama Lengkap Admin");
        jPanel2.add(txtnmlngkp, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, -1, -1));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/return_book1_50px.png"))); // NOI18N
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 0, -1, -1));

        bexit.setFont(new java.awt.Font("SansSerif", 0, 20)); // NOI18N
        bexit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/exit_50px.png"))); // NOI18N
        bexit.setText(" Exit ");
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

        jPanel4.setBackground(new java.awt.Color(245, 245, 245));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        jPanel4.add(bpengembalian_buku, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 240, 50));

        jPanel2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 390, 240, 70));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 240, 610));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("ID Admin");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 300, -1, -1));

        txtnmuser.setBackground(new java.awt.Color(245, 245, 245));
        txtnmuser.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtnmuser.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtnmuser, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 380, 360, 30));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Nama Admin");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 360, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("ID Anggota");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 420, -1, -1));

        txtnmangg.setBackground(new java.awt.Color(245, 245, 245));
        txtnmangg.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtnmangg.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtnmangg, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 500, 360, 30));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Nama Anggota");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 480, -1, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Tanggal Pinjam");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 180, -1, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Status");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 540, -1, -1));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Tanggal Kembali");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 240, -1, -1));

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("No. Pinjam");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 120, -1, -1));

        txtidpnjm.setBackground(new java.awt.Color(245, 245, 245));
        txtidpnjm.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtidpnjm.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtidpnjm, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 140, 160, 30));

        cbidangg.setBackground(new java.awt.Color(245, 245, 245));
        cbidangg.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cbidangg.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih ...", "X", "XI", "XII" }));
        cbidangg.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbidanggItemStateChanged(evt);
            }
        });
        jPanel1.add(cbidangg, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 440, 360, 30));

        bcaripnjm.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        bcaripnjm.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/search_in_list_20px.png"))); // NOI18N
        bcaripnjm.setText("  Cari");
        bcaripnjm.setBorder(null);
        bcaripnjm.setBorderPainted(false);
        bcaripnjm.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bcaripnjm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bcaripnjmActionPerformed(evt);
            }
        });
        jPanel1.add(bcaripnjm, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 140, 90, 30));

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setText("No. Pengembalian");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 60, -1, -1));

        txtidkmbl.setBackground(new java.awt.Color(245, 245, 245));
        txtidkmbl.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtidkmbl.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtidkmbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 80, 160, 30));

        tbkembali.setModel(new javax.swing.table.DefaultTableModel(
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
        tbkembali.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tbkembali.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbkembaliMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbkembali);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 330, 720, 210));

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setText("No. Pengembalian");
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 270, -1, -1));

        txtcaripeng.setBackground(new java.awt.Color(245, 245, 245));
        txtcaripeng.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtcaripeng.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jPanel1.add(txtcaripeng, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 290, 270, 30));

        bcancel.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        bcancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/clear_symbol_20px.png"))); // NOI18N
        bcancel.setText("Kosong");
        bcancel.setBorder(null);
        bcancel.setBorderPainted(false);
        bcancel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bcancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bcancelActionPerformed(evt);
            }
        });
        jPanel1.add(bcancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 560, 80, 30));

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

        cbstatus.setBackground(new java.awt.Color(245, 245, 245));
        cbstatus.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cbstatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih ...", "Pinjam", "Kembali" }));
        cbstatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbstatusActionPerformed(evt);
            }
        });
        jPanel1.add(cbstatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 560, 360, 30));

        jdkmbl.setDateFormatString("yyyy-MM-dd");
        jdkmbl.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jdkmblPropertyChange(evt);
            }
        });
        jPanel1.add(jdkmbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 260, 360, 30));

        jdpnjm.setDateFormatString("yyyy-MM-dd");
        jdpnjm.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jdpnjmPropertyChange(evt);
            }
        });
        jPanel1.add(jdpnjm, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 200, 360, 30));

        jPanel5.setBackground(new java.awt.Color(51, 51, 51));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Kode Buku");
        jPanel5.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 20));

        cbkdbk.setBackground(new java.awt.Color(245, 245, 245));
        cbkdbk.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        cbkdbk.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih ..." }));
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

        jLabel18.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Penerbit");
        jPanel5.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, -1, -1));

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

        jLabel19.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Judul Buku");
        jPanel5.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        txtpnrbt.setBackground(new java.awt.Color(51, 51, 51));
        txtpnrbt.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtpnrbt.setForeground(new java.awt.Color(255, 255, 255));
        txtpnrbt.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(245, 245, 245)));
        jPanel5.add(txtpnrbt, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 300, 20));

        jLabel20.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Jumlah Pinjam");
        jPanel5.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, -1, -1));

        txtjmlpnjm.setBackground(new java.awt.Color(51, 51, 51));
        txtjmlpnjm.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtjmlpnjm.setForeground(new java.awt.Color(255, 255, 255));
        txtjmlpnjm.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(245, 245, 245)));
        jPanel5.add(txtjmlpnjm, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 190, 20));

        jLabel21.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Penulis");
        jPanel5.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, -1, -1));

        TbBuku.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kode Buku", "Judul", "Penulis", "Penerbit", "Jumlah  Pinjam"
            }
        ));
        TbBuku.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane3.setViewportView(TbBuku);

        jPanel5.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 10, 390, 180));

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

        txtketerlambatan.setBackground(new java.awt.Color(51, 51, 51));
        txtketerlambatan.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtketerlambatan.setForeground(new java.awt.Color(255, 255, 255));
        txtketerlambatan.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(245, 245, 245)));
        jPanel5.add(txtketerlambatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 230, 100, 20));

        jLabel22.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Keterlambatan");
        jPanel5.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 210, -1, -1));

        txtdenda.setBackground(new java.awt.Color(51, 51, 51));
        txtdenda.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtdenda.setForeground(new java.awt.Color(255, 255, 255));
        txtdenda.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(245, 245, 245)));
        jPanel5.add(txtdenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 230, 170, 20));

        jLabel23.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Denda");
        jPanel5.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 210, -1, -1));

        hitung_denda.setText("Hitung");
        hitung_denda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hitung_dendaActionPerformed(evt);
            }
        });
        jPanel5.add(hitung_denda, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 230, -1, -1));

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 0, 720, 270));

        cbiduser.setBackground(new java.awt.Color(245, 245, 245));
        cbiduser.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cbiduser.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih ..." }));
        cbiduser.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbiduserItemStateChanged(evt);
            }
        });
        jPanel1.add(cbiduser, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 320, 360, 30));

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
        jPanel1.add(bcari, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 290, 90, 30));

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
        jPanel1.add(bcetakBukti, new org.netbeans.lib.awtextra.AbsoluteConstraints(1200, 560, 90, 30));

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

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1360, 610));

        setSize(new java.awt.Dimension(1373, 649));
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

    private void bpinjam_bukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bpinjam_bukuActionPerformed
        dispose();
        input_peminjaman inpin;
        inpin = new input_peminjaman();
        inpin.setVisible(true);
    }//GEN-LAST:event_bpinjam_bukuActionPerformed

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

    private void btambah_bukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btambah_bukuActionPerformed
        dispose();
        input_buku inbuk;
        inbuk = new input_buku();
        inbuk.setVisible(true);                        // TODO add your handling code here:
    }//GEN-LAST:event_btambah_bukuActionPerformed

    private void bdaftar_bukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bdaftar_bukuActionPerformed
        dispose();
        daftar_buku dabuk;
        dabuk = new daftar_buku();
        dabuk.setVisible(true);
    }//GEN-LAST:event_bdaftar_bukuActionPerformed

    private void bpengembalian_bukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bpengembalian_bukuActionPerformed

    }//GEN-LAST:event_bpengembalian_bukuActionPerformed

    private void cbidanggItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbidanggItemStateChanged
        try {
            String sql = "select * from tb_anggota where id_anggota ='"+ cbidangg.getSelectedItem()+"'";
            Statement st=conn.createStatement();
            ResultSet rsidangg = st.executeQuery(sql);
            while (rsidangg.next()) {
                txtnmangg.setText(rsidangg.getString(2));
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_cbidanggItemStateChanged

    private void bcaripnjmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bcaripnjmActionPerformed
        pilih_peminjaman pp =new pilih_peminjaman();
        pp.piljam=this;
        pp.setVisible(true);
        pp.setResizable(false);
    }//GEN-LAST:event_bcaripnjmActionPerformed

    private void bcancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bcancelActionPerformed
        kosong();
        TABEL_PENGEMBALIAN();
    }//GEN-LAST:event_bcancelActionPerformed

    private void bsaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bsaveActionPerformed
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        tanggalpinjam =format.format(jdpnjm.getDate());
        tanggalkembali =format1.format(jdkmbl.getDate());
        jmlpin=Integer.parseInt (txtjmlpnjm.getText());
        terlambat=Integer.parseInt (txtketerlambatan.getText());
        denda=Integer.parseInt (txtdenda.getText());
        String sql = "insert into tb_pengembalian values (?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, txtidkmbl.getText());
            stat.setString(2, txtidpnjm.getText());
            stat.setString(3, cbiduser.getItemAt(cbiduser.getSelectedIndex()));
            stat.setString(4, cbidangg.getItemAt(cbidangg.getSelectedIndex()));
            stat.setString(5, tanggalpinjam);
            stat.setString(6, tanggalkembali);
            stat.setString(7, cbkdbk.getItemAt(cbkdbk.getSelectedIndex()));
            stat.setInt(8, jmlpin);
            stat.setInt(9, terlambat);
            stat.setInt(10, denda);
            stat.setString(11, cbstatus.getItemAt(cbstatus.getSelectedIndex()));
            
            //simpandetail_peminjaman();
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
        TABEL_PENGEMBALIAN();
    }//GEN-LAST:event_bsaveActionPerformed

    private void beditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_beditActionPerformed
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        tanggalpinjam =format.format(jdpnjm.getDate());
        tanggalkembali =format1.format(jdkmbl.getDate());
        jmlpin=Integer.parseInt (txtjmlpnjm.getText());
        terlambat=Integer.parseInt (txtketerlambatan.getText());
        denda=Integer.parseInt (txtdenda.getText());
        try {
            String sql = "update tb_pengembalian set keterlambatan=?, denda=?, status=? where no_pengembalian= '"+txtcaripeng.getText()+"'";
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setInt(1, terlambat);
            stat.setInt(2, denda);
            stat.setString(3, cbstatus.getItemAt(cbstatus.getSelectedIndex()));
            //stat.setString(4, txtidangg.getText());
            //stat.setString(4, cbidangg.getItemAt(cbidangg.getSelectedIndex()));
            //stat.setInt(5, totpin);
            //stat.setString(6, cbstatus.getItemAt(cbstatus.getSelectedIndex()));
            
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
            txtketerlambatan.requestFocus();
            auto();
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Data Gagal Diubah" +e);
        }
        TABEL_PENGEMBALIAN();

    }//GEN-LAST:event_beditActionPerformed

    private void bdeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bdeleteActionPerformed
        int ok = JOptionPane.showConfirmDialog(null, "hapus", "konfirmasi dialog",JOptionPane.YES_NO_OPTION);
        if (ok==0){
            String sql = "delete from tb_pengembalian where no_pengembalian= '"+txtcaripeng.getText()+"'";
            try {
                PreparedStatement stat = conn.prepareStatement(sql);
                stat.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
                kosong();
                txtcaripeng.requestFocus();
            } catch (SQLException e){
                JOptionPane.showMessageDialog(null,"Data Gagal Dihapus"+e);
            }
            TABEL_PENGEMBALIAN();
        }
    }//GEN-LAST:event_bdeleteActionPerformed

    private void hitung_dendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hitung_dendaActionPerformed
        denda();
    }//GEN-LAST:event_hitung_dendaActionPerformed

    private void jdkmblPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jdkmblPropertyChange
        if (jdkmbl.getDate()!=null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            tanggalpinjam =format.format(jdkmbl.getDate());
        }
    }//GEN-LAST:event_jdkmblPropertyChange

    private void jdpnjmPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jdpnjmPropertyChange
        if (jdpnjm.getDate()!=null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            tanggalpinjam =format.format(jdpnjm.getDate());
        }
    }//GEN-LAST:event_jdpnjmPropertyChange

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

    private void cbkdbkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbkdbkActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbkdbkActionPerformed

    private void txtjdlbkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtjdlbkActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtjdlbkActionPerformed

    private void btmbhbkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btmbhbkActionPerformed
        TambahBuku();
        //total_pinjam();
    }//GEN-LAST:event_btmbhbkActionPerformed

    private void cbiduserItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbiduserItemStateChanged
        try {
            String sql = "select * from tb_admin where id_admin='"+ cbiduser.getSelectedItem()+"'";
            Statement st=conn.createStatement();
            ResultSet rsiduser = st.executeQuery(sql);
            while (rsiduser.next()) {
                txtnmuser.setText(rsiduser.getString(3));
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_cbiduserItemStateChanged

    private void bcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bcariActionPerformed
        TABEL_PENGEMBALIAN();
    }//GEN-LAST:event_bcariActionPerformed

    private void bcetakBuktiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bcetakBuktiActionPerformed
        printbuk();
    }//GEN-LAST:event_bcetakBuktiActionPerformed

    private void bcetakLaporanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bcetakLaporanActionPerformed
        print();
    }//GEN-LAST:event_bcetakLaporanActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void tbkembaliMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbkembaliMouseClicked
        int bar = tbkembali.getSelectedRow();
        String a = tbkembali.getValueAt(bar, 0). toString();
        String b = tbkembali.getValueAt(bar, 1). toString();
        String c = tbkembali.getValueAt(bar, 2). toString();
        String d = tbkembali.getValueAt(bar, 3). toString();
        String e = tbkembali.getValueAt(bar, 4). toString();
        String f = tbkembali.getValueAt(bar, 5). toString();
        String g = tbkembali.getValueAt(bar, 6). toString();
        String h = tbkembali.getValueAt(bar, 7). toString();
        String i = tbkembali.getValueAt(bar, 8). toString();
        String j = tbkembali.getValueAt(bar, 9). toString();
        String k = tbkembali.getValueAt(bar, 10). toString();
        
        txtcaripeng.setText(a);
        txtidkmbl.setText(a);
        txtidpnjm.setText(b);
        jdpnjm.setDateFormatString(e);
        jdkmbl.setDateFormatString(f);        
        cbiduser.setSelectedItem(c);
        cbidangg.setSelectedItem(d);
        cbkdbk.setSelectedItem(g);
        txtjmlpnjm.setText(h);
        txtketerlambatan.setText(i);
        txtdenda.setText(j);
        cbstatus.setSelectedItem(k);                // TODO add your handling code here:
    }//GEN-LAST:event_tbkembaliMouseClicked

    private void cbstatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbstatusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbstatusActionPerformed

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
            java.util.logging.Logger.getLogger(input_pengembalian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(input_pengembalian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(input_pengembalian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(input_pengembalian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new input_pengembalian().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TbBuku;
    private javax.swing.JButton bcancel;
    private javax.swing.JButton bcari;
    private javax.swing.JButton bcaripnjm;
    private javax.swing.JButton bcetakBukti;
    private javax.swing.JButton bcetakLaporan;
    private javax.swing.JButton bdaftar_buku;
    private javax.swing.JButton bdelete;
    private javax.swing.JButton bedit;
    private javax.swing.JButton bexit;
    private javax.swing.JButton bpengembalian_buku;
    private javax.swing.JButton bpinjam_buku;
    private javax.swing.JButton bsave;
    private javax.swing.JButton btambah_anggota;
    private javax.swing.JButton btambah_buku;
    private javax.swing.JButton btentang;
    private javax.swing.JButton btmbhbk;
    private javax.swing.JComboBox<String> cbidangg;
    private javax.swing.JComboBox<String> cbiduser;
    private javax.swing.JComboBox<String> cbkdbk;
    private javax.swing.JComboBox<String> cbstatus;
    private javax.swing.JButton hitung_denda;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private com.toedter.calendar.JDateChooser jdkmbl;
    private com.toedter.calendar.JDateChooser jdpnjm;
    private javax.swing.JTable tbkembali;
    private javax.swing.JTextField txtcaripeng;
    private javax.swing.JTextField txtdenda;
    private javax.swing.JTextField txtidkmbl;
    private javax.swing.JTextField txtidpnjm;
    private javax.swing.JTextField txtjdlbk;
    private javax.swing.JTextField txtjmlpnjm;
    private javax.swing.JTextField txtketerlambatan;
    private javax.swing.JTextField txtnmangg;
    private javax.swing.JLabel txtnmlngkp;
    private javax.swing.JTextField txtnmuser;
    private javax.swing.JTextField txtpnls;
    private javax.swing.JTextField txtpnrbt;
    // End of variables declaration//GEN-END:variables
}
