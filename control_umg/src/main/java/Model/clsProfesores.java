package Model;

import Conexion.clsConexion;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class clsProfesores {

    public int id_profesor;
    public String nombre;
    public String apellido;
    public String telefono;
    public String correo;

    public clsProfesores() {
    }

    public clsProfesores(int id_profesor, String nombre, String apellido, String telefono, String correo) {
        this.id_profesor = id_profesor;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.correo = correo;
    }

    public int getId_profesor() {
        return id_profesor;
    }

    public void setId_profesor(int id_profesor) {
        this.id_profesor = id_profesor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void insertar(JTextField txtIdProfesor, JTextField txtNombre,
            JTextField txtApellido, JTextField txtTelefono,
            JTextField txtCorreo) {

        setId_profesor(Integer.parseInt(txtIdProfesor.getText()));
        setNombre(txtNombre.getText());
        setApellido(txtApellido.getText());
        setTelefono(txtTelefono.getText());
        setCorreo(txtCorreo.getText());

        clsConexion objco = new clsConexion();

        String consulta = "INSERT INTO Profesores (ID_profesor,nombre,apellido,telefono,correo)\n"
                + "VALUES (?,?,?,?,?);";
        try {
            CallableStatement cs = objco.conexion().prepareCall(consulta);
            cs.setInt(1, getId_profesor());
            cs.setString(2, getNombre());
            cs.setString(3, getApellido());
            cs.setString(4, getTelefono());
            cs.setString(5, getCorreo());

            cs.execute();

            JOptionPane.showMessageDialog(null, "SE A INGRESADO EXITOSAMENTE");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR AL INGRESAR DATO A LA BASE DE DATOS " + e);
            System.out.println("error al insertar " + e);
        }

    }

    public void mostrar(JTable tableProfesores) {
        clsConexion objco = new clsConexion();
        DefaultTableModel modelo = new DefaultTableModel();
        TableRowSorter<TableModel> OrdenarTabla = new TableRowSorter<TableModel>(modelo);
        tableProfesores.setRowSorter(OrdenarTabla);

        String sql = "";
        modelo.addColumn("ID_PROFESORES");
        modelo.addColumn("NOMBRE");
        modelo.addColumn("APELLIDO");
        modelo.addColumn("TELEFONO");
        modelo.addColumn("CORREO");

        sql = "SELECT ID_profesor, nombre, apellido, telefono, correo\n"
                + "FROM Profesores;";

        String[] datos = new String[5];
        Statement st;

        try {
            st = objco.conexion().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);

                modelo.addRow(datos);
            }
            tableProfesores.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR AL MOSTRAR LOS DATOS DE LA BASE DE DATOS " + e);
            System.out.println("error al consultar ala base de datos " + e);
        }
    }

    public void modificar(JTable tableProfesores, JTextField txtIdProfesor,
            JTextField txtNombre, JTextField txtApellido,
            JTextField txtTelefono, JTextField txtCorreo) {

        try {
            int fila = tableProfesores.getSelectedRow();
            if (fila > 0) {
                txtIdProfesor.setText((String) (tableProfesores.getValueAt(fila, 0)));
                txtNombre.setText((String) (tableProfesores.getValueAt(fila, 1)));
                txtApellido.setText((String) (tableProfesores.getValueAt(fila, 2)));
                txtTelefono.setText((String) (tableProfesores.getValueAt(fila, 3)));
                txtCorreo.setText((String) (tableProfesores.getValueAt(fila, 4)));

            } else {
                JOptionPane.showMessageDialog(null, "FILA NO SELECIONADA");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR " + e);
        }
    }

    public void modificarDato(JTextField txtIdProfesor,
            JTextField txtNombre, JTextField txtApellido,
            JTextField txtTelefono, JTextField txtCorreo) {

        setId_profesor(Integer.parseInt(txtIdProfesor.getText()));
        setNombre(txtNombre.getText());
        setApellido(txtApellido.getText());
        setTelefono(txtTelefono.getText());
        setCorreo(txtCorreo.getText());

        clsConexion co = new clsConexion();

        String consulta = "UPDATE Profesores SET Nombre=?,Apellido=?,telefono=?,correo=?\n"
                + "WHERE ID_profesor=?";

        try {
            CallableStatement cs = co.conexion().prepareCall(consulta);

            cs.setString(1, getNombre());
            cs.setString(2, getApellido());
            cs.setString(3, getTelefono());
            cs.setString(4, getCorreo());

            cs.setInt(5, getId_profesor());
            cs.execute();
            JOptionPane.showMessageDialog(null, "SE ACUALIZO CORRECTAMENTE");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            System.out.print("error " + e);
        }
    }

    public void eliminar(JTextField txtIdProfesor) {
        setId_profesor(Integer.parseInt(txtIdProfesor.getText()));
        clsConexion co = new clsConexion();

        String sql = "DELETE FROM Profesores WHERE Profesores.ID_profesor =?";

        try {
            CallableStatement cs = co.conexion().prepareCall(sql);
            cs.setInt(1, getId_profesor());
            cs.execute();

            JOptionPane.showMessageDialog(null, "SE BORRO EXITOSAMENTE");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            System.out.println("ERRO " + e);
        }

    }

    public void limpiar(JTextField txtIdProfesor,
            JTextField txtNombre, JTextField txtApellido,
            JTextField txtTelefono, JTextField txtCorreo) {

        txtIdProfesor.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
    }
}
