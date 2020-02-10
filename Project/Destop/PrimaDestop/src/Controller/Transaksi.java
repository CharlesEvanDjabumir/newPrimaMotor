/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class Transaksi extends javax.swing.JFrame {
    private DefaultTableModel model;
    HashMap param = new HashMap();
    

    /**
     * Creates new form Transaksi
     */
    public Transaksi() {
        initComponents();
        txtnamabarang.requestFocus();
        txtnofaktur.disable();
        auto_key();
        txtprintnofaktur.hide();
        txthargamodal.hide();
        txtmodal.hide();
        txtstock.hide();
        txtDateTime.hide();
        
        
        model = new DefaultTableModel();
        TblDetail.setModel(model);
        model.addColumn("Kode Barang");
        model.addColumn("Nama Barang");
        model.addColumn("Harga Barang");
        model.addColumn("Qty");
        model.addColumn("Sub");
        model.addColumn("Jual");
        
        //START fungsi tidak menampilkan column ID barang(0) dan jual time (5)
        TblDetail.getColumnModel().getColumn(5).setMinWidth(0);
        TblDetail.getColumnModel().getColumn(5).setMaxWidth(0);
        TblDetail.getColumnModel().getColumn(5).setWidth(0);
        
        TblDetail.getColumnModel().getColumn(0).setMinWidth(0);
        TblDetail.getColumnModel().getColumn(0).setMaxWidth(0);
        TblDetail.getColumnModel().getColumn(0).setWidth(0);
        
        loadData();
        //date
        Date date = new Date();
        jDateJual.setDate(date);
    }
    
    
    public void  Batal () {
        int x, y, z;
        x = Integer.parseInt(txtstock.getText());
        y = Integer.parseInt(txtqtyjual.getText());
        z = x+y;
        
        String kodebarang = this.txtkodebarang.getText();
        try {
            Connection c = koneksi.getKoneksi();
            String sql = "UPDATE tbstock set stockbarang=? WHERE kodebarang=?";
            PreparedStatement p = (PreparedStatement)c.prepareStatement(sql);
            p.setInt(1,z);
           p.setString(2,kodebarang);//yang kode atau id letakkan di nomor terakhir  
           p.executeUpdate();  
           p.close();
        } catch (SQLException e) {
            System.out.println("Terjadi Kesalahan");
        }finally{
            //            JOptionPane.showMessageDialog(this, "Stock barang telah di update");
        }
        try {
            Connection c = koneksi.getKoneksi();
            String sql = "DELETE From dbjualdetail WHERE nofaktur='"+this.txtnofaktur.getText()+"' AND time ='"+this.txtDateTime.getText()+"'";
            PreparedStatement p = (PreparedStatement) c.prepareStatement(sql);
            p.executeUpdate();
            p.close();
        }catch(SQLException e){
            System.out.println("Terjadi kesalahan");
        }finally{
            loadData();
            JOptionPane.showMessageDialog(this, "Sukses hapus Data");
        }
    }
    
    public  void Cari_Kode () {
        int i = TblDetail.getSelectedRow();
        if(i==-1)
        { return; }
        String Kode=(String)model.getValueAt(i, 0);
        txtkodebarang.setText(Kode);
    }
    
    public void ShowData () {
//        koneksi ClassKoneksi = new koneksi();
        try {
            Connection c= koneksi.getKoneksi();
            String sql="SELECT * FROM dbjualdetail, tbstock WHERE dbjualdetail.kodebarang = tbstock.kodebarang AND dbjualdetail.kodebarang='"+this.txtkodebarang.getText()+"'";
            Statement st = koneksi.getKoneksi().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                this.txtqtyjual.setText(rs.getString("qty"));
                this.txtnamabarang.setText(rs.getString("namabarang"));
                this.txthargajual.setText(rs.getString("harga"));
                this.txtsubtotal.setText(rs.getString("subtotal"));
                this.txtDateTime.setText(rs.getString("time"));
            }
            rs.close(); st.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
        public void ShowSisa (){
//        koneksi ClassKoneksi = new koneksi();
        try {
            Connection c=koneksi.getKoneksi();
            String sql="SELECT * FROM tbstock WHERE kodebarang ='"+this.txtkodebarang.getText()+"'";
            Statement st = koneksi.getKoneksi().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                this.txtstock.setText(rs.getString("stockbarang"));
            }
            rs.close(); st.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
        
        public void UpdateStock () {
//         koneksi ClassKoneksi = new koneksi();
         int x, y, z;
         x = Integer.parseInt(txtstock.getText());
         y = Integer.parseInt(txtqtyjual.getText());
         z = x-y;
         
         String kodebarang=this.txtkodebarang.getText();
         try{
             Connection c= koneksi.getKoneksi();
             String sql = "UPDATE tbstock set stockbarang=? WHERE kodebarang=?";
             PreparedStatement p=(PreparedStatement)c.prepareStatement(sql);
                p.setInt(1, z);
                p.setString(2, kodebarang);
                p.executeUpdate();
                p.close();
         }catch(SQLException e){
             System.out.println("Terjaddi kesalahan");
         }finally{
             //JOptionPane.showMessageDialog(this,"Stock barang telah di update Diubah");
         }
     }
     
     public final void loadData () {
//         koneksi ClassKoneksi = new koneksi();
         model.getDataVector().removeAllElements();
         model.fireTableDataChanged();
         try {
             Connection c=koneksi.getKoneksi();
             Statement s =c.createStatement();
             String sql="SELECT * FROM dbjualdetail, tbstock WHERE dbjualdetail.kodebarang = tbstock.kodebarang AND dbjualdetail.nofaktur='"+this.txtnofaktur.getText()+"'";
             ResultSet r = s.executeQuery(sql);
             while(r.next()){
               Object[]o=new Object[6];
               o[0]=r.getString("kodebarang");
               o[1]=r.getString("namabarang");
               o[2]=r.getString("harga");
               o[3]=r.getString("qty");
               o[4]=r.getString("subtotal");
               o[5]=r.getString("stockbarang");
               model.addRow(o);
             }
             r.close();
             s.close();
         } catch (SQLException e) {
             System.err.println("terjadi kesalahan");
         }
         
         int total = 0;
         for (int i =0; i< TblDetail.getRowCount(); i++){
             int amount = Integer.parseInt((String)TblDetail.getValueAt(i, 4));
             total += amount;
         }
         txttotalharga.setText(""+total);
     }
     
         public void AutoSum() {     
             int harga,qty,total;


        if (txtqtyjual.getText().length() > 0) {
            harga=Integer.parseInt(txthargajual.getText());
            qty=Integer.parseInt(txtqtyjual.getText());
            total=harga*qty;
        
            txtsubtotal.setText(""+total);
        } else {
            txtsubtotal.setText(0 + "");
        }
//        int a, b, c;
//        a = Integer.parseInt(TxtHJual.getText());
//        b = Integer.parseInt(TxtQty.getText());
//        c = a*b;
//        TxtSubTotal.setText(""+c);
    }
         
         public void modal() {
             int modal,qty,total;


        if (txtqtyjual.getText().length() > 0) {
            modal=Integer.parseInt(txthargamodal.getText());
            qty=Integer.parseInt(txtqtyjual.getText());
            total=modal*qty;
        
            txtmodal.setText(""+total);
        } else {
            txtmodal.setText(0 + "");
        }
         }
         
        public void HitungKembali() {     
        int d, e, f;
        d = Integer.parseInt(txttotalharga.getText());
        e = Integer.parseInt(txtcash.getText());
        f = e-d;
        txtkembalian.setText(""+f);
        
    }
        
     public void auto_key(){
//         koneksi ClassKoneksi = new koneksi();
         
         try {
             java.util.Date tgl = new java.util.Date();
             java.text.SimpleDateFormat kal = new java.text.SimpleDateFormat("yyMMdd");  
             java.text.SimpleDateFormat tanggal = new java.text.SimpleDateFormat("yyyyMMdd");  
             Connection c=koneksi.getKoneksi();
             String sql = "SELECT MAX(nofaktur) FROM tbjual WHERE tanggal = "+tanggal.format(tgl);
             Statement st = koneksi.getKoneksi().createStatement();
             ResultSet rs = st.executeQuery(sql);
             while(rs.next()){  
             Long a =rs.getLong(1); //mengambil nilai tertinggi  
             if(a == 0){  
             this.txtnofaktur.setText(kal.format(tgl)+"0000"+(a+1));  
             }else{  
             this.txtnofaktur.setText(""+(a+1));  
                 }  
             }  
             rs.close(); st.close();
         } catch (Exception e) {
             JOptionPane.showMessageDialog(null, "Terjadi kesalaahan");  
         }
     }
     
     
     public void Selesai (){
//         koneksi ClassKoneksi = new koneksi();
         String nofaktur = this.txtnofaktur.getText();
         String total = this.txttotalharga.getText();
         String cash = this.txtcash.getText();
         String kembalian = this.txtkembalian.getText();
         
         SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
         Date tanggal = new Date(); 
         tanggal = jDateJual.getDate(); 
         String tgl = dateFormat.format(tanggal);
         
         
         try{  
     Connection c=koneksi.getKoneksi();  
     String sql="Insert into tbjual (nofaktur,tanggal,total,cash,kembalian) values (?,?,?,?,?)";  
     PreparedStatement p=(PreparedStatement)c.prepareStatement(sql);  
     p.setString(1,nofaktur);
     p.setString(2,tgl);
     p.setString(3,total);
     p.setString(4,cash);
     p.setString(5,kembalian);
     p.executeUpdate();
     p.close();
   }catch(SQLException e){ 
   System.out.println(e);  
   }finally{  
   //loadData();
       JOptionPane.showMessageDialog(this,"Data Telah Tersimpan");  
  }
   
  auto_key();
  loadData();  
     }
     
     public void TambahDetail(){
//         koneksi ClassKoneksi = new koneksi();
   Date HariSekarang = new Date( );
   SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
      
   String nofaktur =this.txtnofaktur.getText();  
   String kodebarang =this.txtkodebarang.getText();
   String namabarang = this.txtnamabarang.getText();
   String harga=this.txthargajual.getText();  
   String qty=this.txtqtyjual.getText();
   String subtotal =this.txtsubtotal.getText();
   String hargamodal=this.txtmodal.getText();
   String time = ft.format(HariSekarang);
   
   
   try{  
     Connection c=koneksi.getKoneksi();  
     String sql="Insert into dbjualdetail (nofaktur,kodebarang,namabarang,harga,qty,subtotal,hargamodal,time) values (?,?,?,?,?,?,?,?)";  
     PreparedStatement p=(PreparedStatement)c.prepareStatement(sql);  
     p.setString(1,nofaktur);
     p.setString(2,kodebarang);
     p.setString(3, namabarang);
     p.setString(4,harga);
     p.setString(5,qty);
     p.setString(6,subtotal);
     p.setString(7, hargamodal);
     p.setString(8,time);
     p.executeUpdate();
     p.close();
   }catch(SQLException e){ 
   System.out.println(e);  
   }finally{  
   //loadData();
       //JOptionPane.showMessageDialog(this,"Data Telah Tersimpan");  
  }
 }
    
    public void cari_nama() {
        try {
        Connection c=koneksi.getKoneksi();
        String sql = "SELECT * FROM tbstock WHERE namabarang='"+this.txtnamabarang.getText()+"'"; 
        Statement st = koneksi.getKoneksi().createStatement();
        ResultSet rs = st.executeQuery(sql);
        
        while(rs.next()){
        this.txtkodebarang.setText(rs.getString("kodebarang"));
        this.txthargajual.setText(rs.getString("hargajual"));
        this.txtstock.setText(rs.getString("stockbarang"));
        this.txthargamodal.setText(rs.getString("hargamodal"));
        txtprintnofaktur.setText(txtnofaktur.getText());
        }
        rs.close(); 
        st.close();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public void cari_id(){
//        koneksi ClassKoneksi = new koneksi();
        try {
        Connection c=koneksi.getKoneksi();
        String sql = "SELECT * FROM tbstock WHERE kodebarang='"+this.txtkodebarang.getText()+"'"; 
        Statement st = koneksi.getKoneksi().createStatement();
        ResultSet rs = st.executeQuery(sql);
        
        while(rs.next()){
        this.txtnamabarang.setText(rs.getString("namabarang"));
        this.txthargajual.setText(rs.getString("hargajual"));
        this.txtstock.setText(rs.getString("stockbarang"));
        this.txthargamodal.setText(rs.getString("hargamodal"));
        txtprintnofaktur.setText(txtnofaktur.getText());
        }
        rs.close(); 
        st.close();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
}    
    
    public  void bersihkan(){
        txtnamabarang.requestFocus();
        txtkodebarang.setText("");
        txtnamabarang.setText("");
        txthargajual.setText("");
        txtqtyjual.setText("");
        txtcash.setText("");
        txtsubtotal.setText("");
        txtkembalian.setText("");
       
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
        jTable1 = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        txtnofaktur = new javax.swing.JTextField();
        txttotalharga = new javax.swing.JTextField();
        jDateJual = new com.toedter.calendar.JDateChooser();
        txtkodebarang = new javax.swing.JTextField();
        txtnamabarang = new javax.swing.JTextField();
        txthargajual = new javax.swing.JTextField();
        txtqtyjual = new javax.swing.JTextField();
        txtsubtotal = new javax.swing.JTextField();
        txtcash = new javax.swing.JTextField();
        txtkembalian = new javax.swing.JTextField();
        txtDateTime = new javax.swing.JTextField();
        txtmodal = new javax.swing.JTextField();
        txtprintnofaktur = new javax.swing.JTextField();
        txthargamodal = new javax.swing.JTextField();
        txtstock = new javax.swing.JTextField();
        btndelete = new javax.swing.JLabel();
        btnadd = new javax.swing.JLabel();
        btnsimpan = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TblDetail = new javax.swing.JTable();
        home = new javax.swing.JLabel();
        transaksi = new javax.swing.JLabel();
        logout = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtnofaktur.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        txtnofaktur.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtnofaktur.setBorder(null);
        getContentPane().add(txtnofaktur, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 275, 311, 55));

        txttotalharga.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        txttotalharga.setBorder(null);
        txttotalharga.setOpaque(false);
        txttotalharga.setRequestFocusEnabled(false);
        getContentPane().add(txttotalharga, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 120, 670, 57));

        jDateJual.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        getContentPane().add(jDateJual, new org.netbeans.lib.awtextra.AbsoluteConstraints(394, 275, 310, 55));

        txtkodebarang.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        txtkodebarang.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtkodebarang.setBorder(null);
        txtkodebarang.setOpaque(false);
        txtkodebarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtkodebarangKeyPressed(evt);
            }
        });
        getContentPane().add(txtkodebarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(46, 385, 195, 55));

        txtnamabarang.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        txtnamabarang.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtnamabarang.setBorder(null);
        txtnamabarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtnamabarangKeyPressed(evt);
            }
        });
        getContentPane().add(txtnamabarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(315, 385, 310, 55));

        txthargajual.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        txthargajual.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txthargajual.setBorder(null);
        getContentPane().add(txthargajual, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 385, 310, 55));

        txtqtyjual.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        txtqtyjual.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtqtyjual.setBorder(null);
        txtqtyjual.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtqtyjualKeyPressed(evt);
            }
        });
        getContentPane().add(txtqtyjual, new org.netbeans.lib.awtextra.AbsoluteConstraints(1086, 385, 139, 55));

        txtsubtotal.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        txtsubtotal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtsubtotal.setBorder(null);
        txtsubtotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtsubtotalActionPerformed(evt);
            }
        });
        getContentPane().add(txtsubtotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(1300, 385, 243, 55));

        txtcash.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        txtcash.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtcash.setBorder(null);
        txtcash.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcashKeyPressed(evt);
            }
        });
        getContentPane().add(txtcash, new org.netbeans.lib.awtextra.AbsoluteConstraints(885, 753, 310, 55));

        txtkembalian.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        txtkembalian.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtkembalian.setBorder(null);
        getContentPane().add(txtkembalian, new org.netbeans.lib.awtextra.AbsoluteConstraints(1233, 753, 310, 55));
        getContentPane().add(txtDateTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 850, 131, 34));
        getContentPane().add(txtmodal, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 850, 131, 34));
        getContentPane().add(txtprintnofaktur, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 850, 131, 34));
        getContentPane().add(txthargamodal, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 850, 131, 34));
        getContentPane().add(txtstock, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 850, 131, 34));

        btndelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btndelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btndeleteMouseClicked(evt);
            }
        });
        getContentPane().add(btndelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(1335, 264, 210, 45));

        btnadd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnadd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnaddMouseClicked(evt);
            }
        });
        getContentPane().add(btnadd, new org.netbeans.lib.awtextra.AbsoluteConstraints(1105, 265, 210, 45));

        btnsimpan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnsimpan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnsimpanMouseClicked(evt);
            }
        });
        getContentPane().add(btnsimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(408, 753, 210, 45));

        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(638, 753, 210, 45));

        TblDetail.setModel(new javax.swing.table.DefaultTableModel(
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
        TblDetail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TblDetailMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(TblDetail);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 460, 1510, 250));

        home.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        home.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                homeMouseClicked(evt);
            }
        });
        getContentPane().add(home, new org.netbeans.lib.awtextra.AbsoluteConstraints(523, 23, 160, 54));

        transaksi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        transaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                transaksiMouseClicked(evt);
            }
        });
        getContentPane().add(transaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(723, 23, 160, 54));

        logout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        logout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoutMouseClicked(evt);
            }
        });
        getContentPane().add(logout, new org.netbeans.lib.awtextra.AbsoluteConstraints(923, 23, 160, 54));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/transaksi.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jMenu3.setText("File");

        jMenuItem1.setText("Laporan");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem1);

        jMenuBar2.add(jMenu3);

        setJMenuBar(jMenuBar2);

        setSize(new java.awt.Dimension(1620, 958));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtkodebarangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtkodebarangKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {     
            cari_id();
        txtqtyjual.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_txtkodebarangKeyPressed

    private void txtnamabarangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnamabarangKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {     
            cari_nama();
        txtqtyjual.requestFocus();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnamabarangKeyPressed

    private void txtqtyjualKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtqtyjualKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            AutoSum();
            modal();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_txtqtyjualKeyPressed

    private void btnaddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnaddMouseClicked
        TambahDetail();
        UpdateStock();
        loadData();
        bersihkan();
        txtcash.requestFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnaddMouseClicked

    private void btndeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btndeleteMouseClicked
        Batal();
        bersihkan();
        // TODO add your handling code here:
    }//GEN-LAST:event_btndeleteMouseClicked

    private void txtcashKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcashKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            HitungKembali();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcashKeyPressed

    private void btnsimpanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnsimpanMouseClicked
        Selesai();
        bersihkan();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnsimpanMouseClicked

    private void TblDetailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TblDetailMouseClicked
        this.Cari_Kode();
        this.ShowData();
        this.ShowSisa();
        // TODO add your handling code here:
    }//GEN-LAST:event_TblDetailMouseClicked

    private Connection sambung(){
        Connection khusus = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            khusus = (Connection)DriverManager.getConnection("jdbc:mysql://localhost/dbprimamotorsidomulyo","root", "");
            return khusus;
        } catch (ClassNotFoundException ex) {
            return null;
        } catch (SQLException ex) {
            return null;
        }
    }
    
    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked

        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel4MouseClicked

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

    private void txtsubtotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtsubtotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtsubtotalActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
new laporan().setVisible(true);
        dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

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
            java.util.logging.Logger.getLogger(Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Transaksi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TblDetail;
    private javax.swing.JLabel btnadd;
    private javax.swing.JLabel btndelete;
    private javax.swing.JLabel btnsimpan;
    private javax.swing.JLabel home;
    private com.toedter.calendar.JDateChooser jDateJual;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel logout;
    private javax.swing.JLabel transaksi;
    private javax.swing.JTextField txtDateTime;
    private javax.swing.JTextField txtcash;
    private javax.swing.JTextField txthargajual;
    private javax.swing.JTextField txthargamodal;
    private javax.swing.JTextField txtkembalian;
    private javax.swing.JTextField txtkodebarang;
    private javax.swing.JTextField txtmodal;
    private javax.swing.JTextField txtnamabarang;
    private javax.swing.JTextField txtnofaktur;
    private javax.swing.JTextField txtprintnofaktur;
    private javax.swing.JTextField txtqtyjual;
    private javax.swing.JTextField txtstock;
    private javax.swing.JTextField txtsubtotal;
    private javax.swing.JTextField txttotalharga;
    // End of variables declaration//GEN-END:variables
}
