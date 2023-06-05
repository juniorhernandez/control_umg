
package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Junior
 */
public class clsConexion {
    
    Connection conectar = null;
    
    String usuario= "root";
    String contrasenia = "umg19";
    String db = "control_umg";
    String ip ="localhost";
    String puerto="3306";
    
    String cadena = "jdbc:mysql://"+ip+":"+puerto+"/"+db;
    
    public Connection conexion(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conectar = DriverManager.getConnection(cadena,usuario,contrasenia);
            System.out.println("conexion exitosa");
        }catch(Exception e){
            System.out.println("error en la conexion "+e);
        }
        return conectar;
    }
}
