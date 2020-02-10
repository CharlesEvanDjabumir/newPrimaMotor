/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class koneksi {
    public  static Connection koneksi;
    public static Connection getKoneksi(){
        if(koneksi==null){  
      try {  
        String url=new String();  
        String user=new String();  
        String password=new String();  
        url="jdbc:mysql://localhost/dbprimamotorsidomulyo";  
        user="root";  
        password="";  
          DriverManager.registerDriver(new com.mysql.jdbc.Driver());  
        koneksi=DriverManager.getConnection(url,user,password);  
      }catch (SQLException t){  
        System.out.println("Eror membuat koneksi");  
      }  
     }
        return koneksi;
    }
}
