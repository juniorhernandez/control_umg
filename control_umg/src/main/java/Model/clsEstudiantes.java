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

public class clsEstudiantes {

    public int id_estudiante;
    public String nombre;
    public String apellido;
    public String telefono;
    public String correo;

    public clsEstudiantes() {
    }

    public clsEstudiantes(int id, String nombre, String apellido, String telefono, String correo) {
        this.id_estudiante = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.correo = correo;
    }

    public int getId() {
        return id_estudiante;
    }

    public void setId(int id) {
        this.id_estudiante = id;
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

    public void insertar(JTextField txtIdEstudiante, JTextField txtNombre,
            JTextField txtApellido, JTextField txtTelefono,
            JTextField txtCorreo) {

        setId(Integer.parseInt(txtIdEstudiante.getText()));
        setNombre(txtNombre.getText());
        setApellido(txtApellido.getText());
        setTelefono(txtTelefono.getText());
        setCorreo(txtCorreo.getText());

        clsConexion objco = new clsConexion();

        String consulta = "INSERT INTO estudiantes (ID_estudiante,nombre,apellido,telefono,correo)\n"
                + "VALUES (?,?,?,?,?);";
        try {
            CallableStatement cs = objco.conexion().prepareCall(consulta);
            cs.setInt(1, getId());
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

    public void mostrar(JTable tableEstudiantes) {
        clsConexion objco = new clsConexion();
        DefaultTableModel modelo = new DefaultTableModel();
        TableRowSorter<TableModel> OrdenarTabla = new TableRowSorter<TableModel>(modelo);
        tableEstudiantes.setRowSorter(OrdenarTabla);

        String sql = "";
        modelo.addColumn("ID_ESTUDIANTES");
        modelo.addColumn("NOMBRE");
        modelo.addColumn("APELLIDO");
        modelo.addColumn("TELEFONO");
        modelo.addColumn("CORREO");

        sql = "SELECT id_estudiante, nombre, apellido, telefono, correo\n"
                + "FROM estudiantes;";

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
            tableEstudiantes.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR AL MOSTRAR LOS DATOS DE LA BASE DE DATOS " + e);
            System.out.println("error al consultar ala base de datos " + e);
        }
    }

    public void modificar(JTable tableEstudiantes, JTextField txtIdEstudiante,
            JTextField txtNombre, JTextField txtApellido,
            JTextField txtTelefono, JTextField txtCorreo) {

        try {
            int fila = tableEstudiantes.getSelectedRow();
            if (fila > 0) {
                txtIdEstudiante.setText((String) (tableEstudiantes.getValueAt(fila, 0)));
                txtNombre.setText((String) (tableEstudiantes.getValueAt(fila, 1)));
                txtApellido.setText((String) (tableEstudiantes.getValueAt(fila, 2)));
                txtTelefono.setText((String) (tableEstudiantes.getValueAt(fila, 3)));
                txtCorreo.setText((String) (tableEstudiantes.getValueAt(fila, 4)));

            } else {
                JOptionPane.showMessageDialog(null, "FILA NO SELECIONADA");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR " + e);
        }
    }

    public void modificarDato(JTextField txtIdEstudiante,
            JTextField txtNombre, JTextField txtApellido,
            JTextField txtTelefono, JTextField txtCorreo) {

        setId(Integer.parseInt(txtIdEstudiante.getText()));
        setNombre(txtNombre.getText());
        setApellido(txtApellido.getText());
        setTelefono(txtTelefono.getText());
        setCorreo(txtCorreo.getText());

        clsConexion co = new clsConexion();

        String consulta = "UPDATE estudiantes SET Nombre=?,Apellido=?,telefono=?,correo=?\n"
                + "WHERE id_estudiante=?";

        try {
            CallableStatement cs = co.conexion().prepareCall(consulta);

            cs.setString(1, getNombre());
            cs.setString(2, getApellido());
            cs.setString(3, getTelefono());
            cs.setString(4, getCorreo());

            cs.setInt(5, getId());
            cs.execute();
            JOptionPane.showMessageDialog(null, "SE ACUALIZO CORRECTAMENTE");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            System.out.print("error " + e);
        }
    }

    public void eliminar(JTextField txtIdEstudiante) {
        setId(Integer.parseInt(txtIdEstudiante.getText()));
        clsConexion co = new clsConexion();

        String sql = "DELETE FROM estudiantes WHERE estudiantes.id_estudiante =?";

        try {
            CallableStatement cs = co.conexion().prepareCall(sql);
            cs.setInt(1, getId());
            cs.execute();

            JOptionPane.showMessageDialog(null, "SE BORRO EXITOSAMENTE");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            System.out.println("ERRO " + e);
        }

    }

    public void limpiar(JTextField txtIdEstudiante,
            JTextField txtNombre, JTextField txtApellido,
            JTextField txtTelefono, JTextField txtCorreo) {

        txtIdEstudiante.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
    }

}
