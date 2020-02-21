/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class databarang extends javax.swing.JFrame {
    public Connection con;
    public Statement st;
    public ResultSet rs;
    public DefaultTableModel model;

    /**
     * Creates new form databarang
     */
    public databarang() {
        initComponents();
        String [] header = {"No","Kode Barang","Nama Barang","Jenis Barang","Harga Jual","Harga Modal","Stock Barang","Keterangan"};//
        model = new DefaultTableModel(header,0);
        tabel.setModel(model);
        tampil();
    }
    
    //Kodingan Untuk Menampilkan data di Database
    public void tampil(){
        koneksi classKoneksi = new koneksi();
    
        int jumlahrow = tabel.getRowCount();
        for(int n=0;n<jumlahrow;n++){
            model.removeRow(0);
        }
    
//        String cari = txtcari.getText();
        try {
            con = classKoneksi.getKoneksi();
            st = con.createStatement();
//            rs = st.executeQuery("SELECT * FROM tbstock WHERE kodebarang LIKE '%"+cari+"%'  ORDER BY kodebarang");
            rs = st.executeQuery("SELECT * FROM tbstock ORDER BY kodebarang");
            int no = 1;
            while (rs.next()) {
                String[] row = {Integer.toString(no),rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7)};//)
                model.addRow(row);
                no++;
            }
            tabel.setModel(model);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }   
    }
    
    //Kodingan Untuk Menambahkan Data
    public void tambah(){
        String kodebarang = txtkodebarang.getText();
        String namabarang = txtnamabarang.getText();
        String jenisbarang = txtjenisbarang.getText();
        String hargajual = txthargajual.getText();
        String hargamodal = txthargamodal.getText();
        String stockbarang = txtstockbarang.getText();
        String keterangan = txtketerangan.getText();
        
        koneksi classKoneksi = new koneksi();
        
        try {
            con = classKoneksi.getKoneksi();
            st = con.createStatement();
            st.execute("INSERT INTO tbstock VALUES ('"+kodebarang+"','"+namabarang+"','"+jenisbarang+"','"+hargajual+"','"+hargamodal+"','"+stockbarang+"','"+keterangan+"')");
            JOptionPane.showMessageDialog(null,"Data Berhail DiTambahkan","Alert",JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Data Gagal DiTambahkan","Error",JOptionPane.ERROR_MESSAGE);
            System.out.println(ex.getMessage());
        }
            
    }
    
    //Kodingan Untuk Buat Data Baru
    public void newdata(){
        txtkodebarang.setText("");       
        txtnamabarang.setText("");     
        txtjenisbarang.setText("");    
        txthargajual.setText("");     
        txthargamodal.setText("");      
        txtstockbarang.setText("");      
        txtketerangan.setText(""); 
        txtkodebarang.requestFocus();
        
        txtkodebarang.setEnabled(true);
        
        btnsave.setEnabled(true);
        btndelete.setEnabled(false);
        btnupdate.setEnabled(false);
    }
    
     //Kodingan Untuk Hapus Data
    public void hapus(){
        String kodebarang = txtkodebarang.getText();
        try {
            st.executeUpdate("DELETE FROM tbstock WHERE kodebarang='"+kodebarang+"'");
            JOptionPane.showMessageDialog(null,"Hapus Data Berhasil","Alert",JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Hapus Data Gagal","Error",JOptionPane.ERROR_MESSAGE);
        }
        newdata();
        tampil();
    }
    
    //Kodingan Untuk Update Data
    public void ubah(){
    String kodebarang = txtkodebarang.getText();
    String namabarang = txtnamabarang.getText();
    String jenisbarang = txtjenisbarang.getText();
    String hargajual = txthargajual.getText();
    String hargamodal = txthargamodal.getText();
    String stockbarang = txtstockbarang.getText();
    String keterangan = txtketerangan.getText();
    
        try {
            st.executeUpdate("UPDATE tbstock SET namabarang='"+namabarang+"',jenisbarang='"+jenisbarang+"',hargajual='"+hargajual+"',hargamodal='"+hargamodal+"',stockbarang='"+stockbarang+"',keterangan='"+keterangan+"' WHERE kodebarang='"+kodebarang+"'");
            JOptionPane.showMessageDialog(null,"Update Data Berhasil","Alert",JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Update Data Gagal","Error",JOptionPane.ERROR_MESSAGE);
        }
        newdata();
        tampil();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        home = new javax.swing.JLabel();
        transaksi = new javax.swing.JLabel();
        logout = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel = new javax.swing.JTable();
        btnnew = new javax.swing.JButton();
        btnsave = new javax.swing.JButton();
        btndelete = new javax.swing.JButton();
        btnupdate = new javax.swing.JButton();
        txtketerangan = new javax.swing.JTextField();
        txthargamodal = new javax.swing.JTextField();
        txthargajual = new javax.swing.JTextField();
        txtstockbarang = new javax.swing.JTextField();
        txtjenisbarang = new javax.swing.JTextField();
        txtnamabarang = new javax.swing.JTextField();
        txtkodebarang = new javax.swing.JTextField();
        bg = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Data Barang");
        getContentPane().setLayout(null);

        home.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        home.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                homeMouseClicked(evt);
            }
        });
        getContentPane().add(home);
        home.setBounds(518, 23, 165, 54);

        transaksi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        transaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                transaksiMouseClicked(evt);
            }
        });
        getContentPane().add(transaksi);
        transaksi.setBounds(718, 23, 165, 54);

        logout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        logout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoutMouseClicked(evt);
            }
        });
        getContentPane().add(logout);
        logout.setBounds(918, 23, 165, 54);

        tabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tabel.setModel(new javax.swing.table.DefaultTableModel(
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
        tabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabel);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(427, 241, 881, 453);

        btnnew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Button/btnbaru.png"))); // NOI18N
        btnnew.setBorder(null);
        btnnew.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnnew.setOpaque(false);
        btnnew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnewActionPerformed(evt);
            }
        });
        getContentPane().add(btnnew);
        btnnew.setBounds(1334, 241, 213, 47);

        btnsave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Button/btnsimpan.png"))); // NOI18N
        btnsave.setBorder(null);
        btnsave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnsave.setOpaque(false);
        btnsave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsaveActionPerformed(evt);
            }
        });
        getContentPane().add(btnsave);
        btnsave.setBounds(1334, 314, 213, 47);

        btndelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Button/btnhapus.png"))); // NOI18N
        btndelete.setBorder(null);
        btndelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btndelete.setEnabled(false);
        btndelete.setOpaque(false);
        btndelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndeleteActionPerformed(evt);
            }
        });
        getContentPane().add(btndelete);
        btndelete.setBounds(1334, 460, 213, 47);

        btnupdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Button/btnperbarui.png"))); // NOI18N
        btnupdate.setBorder(null);
        btnupdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnupdate.setEnabled(false);
        btnupdate.setOpaque(false);
        btnupdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnupdateActionPerformed(evt);
            }
        });
        getContentPane().add(btnupdate);
        btnupdate.setBounds(1334, 387, 213, 47);

        txtketerangan.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        txtketerangan.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtketerangan.setBorder(null);
        txtketerangan.setOpaque(false);
        getContentPane().add(txtketerangan);
        txtketerangan.setBounds(850, 756, 680, 55);

        txthargamodal.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        txthargamodal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txthargamodal.setBorder(null);
        txthargamodal.setOpaque(false);
        getContentPane().add(txthargamodal);
        txthargamodal.setBounds(470, 756, 280, 55);

        txthargajual.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        txthargajual.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txthargajual.setBorder(null);
        txthargajual.setOpaque(false);
        getContentPane().add(txthargajual);
        txthargajual.setBounds(85, 756, 280, 55);

        txtstockbarang.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        txtstockbarang.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtstockbarang.setBorder(null);
        txtstockbarang.setOpaque(false);
        getContentPane().add(txtstockbarang);
        txtstockbarang.setBounds(85, 635, 280, 55);

        txtjenisbarang.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        txtjenisbarang.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtjenisbarang.setBorder(null);
        txtjenisbarang.setOpaque(false);
        getContentPane().add(txtjenisbarang);
        txtjenisbarang.setBounds(85, 514, 280, 55);

        txtnamabarang.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        txtnamabarang.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtnamabarang.setBorder(null);
        txtnamabarang.setOpaque(false);
        txtnamabarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnamabarangActionPerformed(evt);
            }
        });
        getContentPane().add(txtnamabarang);
        txtnamabarang.setBounds(85, 393, 280, 55);

        txtkodebarang.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        txtkodebarang.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtkodebarang.setBorder(null);
        txtkodebarang.setOpaque(false);
        getContentPane().add(txtkodebarang);
        txtkodebarang.setBounds(85, 272, 280, 55);

        bg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/databarang.png"))); // NOI18N
        getContentPane().add(bg);
        bg.setBounds(0, 0, 1600, 900);

        setSize(new java.awt.Dimension(1616, 939));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelMouseClicked
//    Kodingan Untuk Menseleksi Mana yang ingin di delete Button Delete (Enabled)
    int row = tabel.getSelectedRow();
    btnsave.setEnabled(false);
    btnupdate.setEnabled(true);
    btndelete.setEnabled(true);
    
    txtkodebarang.setEnabled(false);
    
    String kodebarang = tabel.getValueAt(row, 1).toString();
    String namabarang = tabel.getValueAt(row, 2).toString();
    String jenisbarang = tabel.getValueAt(row, 3).toString();
    String hargajual = tabel.getValueAt(row, 4).toString();
    String hargamodal = tabel.getValueAt(row, 5).toString();
    String stockbarang = tabel.getValueAt(row, 6).toString();
    String keterangan = tabel.getValueAt(row, 7).toString();
    
    txtkodebarang.setText(kodebarang);
    txtnamabarang.setText(namabarang);
    txtjenisbarang.setText(jenisbarang);
    txthargajual.setText(hargajual);
    txthargamodal.setText(hargamodal);
    txtstockbarang.setText(stockbarang);
    txtketerangan.setText(keterangan);
    }//GEN-LAST:event_tabelMouseClicked

    private void btnnewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnewActionPerformed
        newdata();
    }//GEN-LAST:event_btnnewActionPerformed

    private void btnsaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsaveActionPerformed
        tambah();
        tampil();// TODO add your handling code here:
    }//GEN-LAST:event_btnsaveActionPerformed

    private void btndeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteActionPerformed
        hapus();        // TODO add your handling code here:
    }//GEN-LAST:event_btndeleteActionPerformed

    private void btnupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnupdateActionPerformed
        ubah();
    }//GEN-LAST:event_btnupdateActionPerformed

    private void txtnamabarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnamabarangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnamabarangActionPerformed

    private void homeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeMouseClicked
        new daftarbarang().setVisible(true);
        dispose();// TODO add your handling code here:
    }//GEN-LAST:event_homeMouseClicked

    private void transaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_transaksiMouseClicked
        new Transaksi().setVisible(true);
        dispose();     // TODO add your handling code here:
    }//GEN-LAST:event_transaksiMouseClicked

    private void logoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutMouseClicked
        new loginadmin().setVisible(true);
        dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_logoutMouseClicked

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
            java.util.logging.Logger.getLogger(databarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(databarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(databarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(databarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new databarang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bg;
    private javax.swing.JButton btndelete;
    private javax.swing.JButton btnnew;
    private javax.swing.JButton btnsave;
    private javax.swing.JButton btnupdate;
    private javax.swing.JLabel home;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel logout;
    private javax.swing.JTable tabel;
    private javax.swing.JLabel transaksi;
    private javax.swing.JTextField txthargajual;
    private javax.swing.JTextField txthargamodal;
    private javax.swing.JTextField txtjenisbarang;
    private javax.swing.JTextField txtketerangan;
    private javax.swing.JTextField txtkodebarang;
    private javax.swing.JTextField txtnamabarang;
    private javax.swing.JTextField txtstockbarang;
    // End of variables declaration//GEN-END:variables
}
