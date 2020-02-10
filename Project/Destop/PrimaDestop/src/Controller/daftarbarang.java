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
import javax.swing.table.DefaultTableModel;


public class daftarbarang extends javax.swing.JFrame {
public Connection con;
    public Statement st;
    public ResultSet rs;
    public DefaultTableModel model;
    /**
     * Creates new form daftarbarang
     */
    public daftarbarang() {
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
    
        String nama = txtcari.getText();
        String cari = txtcari.getText();
        try {
            con = classKoneksi.getKoneksi();
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM tbstock WHERE kodebarang LIKE '%"+cari+"%' OR namabarang LIKE '%"+nama+"%' ORDER BY kodebarang");
//            rs = st.executeQuery("SELECT * FROM tbstock ORDER BY kodebarang");
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tabel = new javax.swing.JTable();
        txtcari = new javax.swing.JTextField();
        databarang = new javax.swing.JLabel();
        cari = new javax.swing.JLabel();
        logout = new javax.swing.JLabel();
        transaksi = new javax.swing.JLabel();
        home = new javax.swing.JLabel();
        bg = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Home");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        tabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabel);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 218, 1560, 614));

        txtcari.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        txtcari.setForeground(new java.awt.Color(9, 11, 48));
        txtcari.setBorder(null);
        txtcari.setCaretColor(new java.awt.Color(9, 11, 48));
        txtcari.setOpaque(false);
        txtcari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcariActionPerformed(evt);
            }
        });
        txtcari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtcariKeyReleased(evt);
            }
        });
        getContentPane().add(txtcari, new org.netbeans.lib.awtextra.AbsoluteConstraints(355, 118, 740, 61));

        databarang.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        databarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                databarangMouseClicked(evt);
            }
        });
        getContentPane().add(databarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(1243, 125, 213, 47));

        cari.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        getContentPane().add(cari, new org.netbeans.lib.awtextra.AbsoluteConstraints(1107, 118, 78, 61));

        logout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        logout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoutMouseClicked(evt);
            }
        });
        getContentPane().add(logout, new org.netbeans.lib.awtextra.AbsoluteConstraints(918, 23, 165, 54));

        transaksi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        transaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                transaksiMouseClicked(evt);
            }
        });
        getContentPane().add(transaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(718, 23, 165, 54));

        home.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        home.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                homeMouseClicked(evt);
            }
        });
        getContentPane().add(home, new org.netbeans.lib.awtextra.AbsoluteConstraints(518, 23, 165, 54));

        bg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/daftarbarang.png"))); // NOI18N
        getContentPane().add(bg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        setSize(new java.awt.Dimension(1616, 939));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelMouseClicked
        
    }//GEN-LAST:event_tabelMouseClicked

    private void txtcariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcariKeyReleased
        tampil();        // TODO add your handling code here:
    }//GEN-LAST:event_txtcariKeyReleased

    private void txtcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcariActionPerformed

    private void homeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeMouseClicked
new daftarbarang().setVisible(true);  
dispose();// TODO add your handling code here:
    }//GEN-LAST:event_homeMouseClicked

    private void transaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_transaksiMouseClicked
new Transaksi().setVisible(true);  
dispose();     // TODO add your handling code here:
    }//GEN-LAST:event_transaksiMouseClicked

    private void logoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutMouseClicked
new loginkaryawan().setVisible(true);  
dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_logoutMouseClicked

    private void databarangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_databarangMouseClicked
new databarang().setVisible(true);  
dispose();         // TODO add your handling code here:
    }//GEN-LAST:event_databarangMouseClicked

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
            java.util.logging.Logger.getLogger(daftarbarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(daftarbarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(daftarbarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(daftarbarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new daftarbarang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bg;
    private javax.swing.JLabel cari;
    private javax.swing.JLabel databarang;
    private javax.swing.JLabel home;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel logout;
    private javax.swing.JTable tabel;
    private javax.swing.JLabel transaksi;
    private javax.swing.JTextField txtcari;
    // End of variables declaration//GEN-END:variables
}
