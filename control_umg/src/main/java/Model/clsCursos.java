package Model;

import Conexion.clsConexion;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class clsCursos {

    public int id_curso;
    public String nombre;
    public String descripcion;

    public clsCursos() {
    }

    public clsCursos(int id_curso, String nombre, String descripcion) {
        this.id_curso = id_curso;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public int getId_curso() {
        return id_curso;
    }

    public void setId_curso(int id_curso) {
        this.id_curso = id_curso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void insertar(JTextField txtIdCurso, JTextField txtNombre,
            JTextArea txtDescripcion) {

        setId_curso(Integer.parseInt(txtIdCurso.getText()));
        setNombre(txtNombre.getText());
        setDescripcion(txtDescripcion.getText());

        clsConexion objco = new clsConexion();

        String consulta = "INSERT INTO Cursos (ID_curso,nombre,Descripcion)\n"
                + "VALUES (?,?,?);";
        try {
            CallableStatement cs = objco.conexion().prepareCall(consulta);
            cs.setInt(1, getId_curso());
            cs.setString(2, getNombre());
            cs.setString(3, getDescripcion());

            cs.execute();

            JOptionPane.showMessageDialog(null, "SE A INGRESADO EXITOSAMENTE");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR AL INGRESAR DATO A LA BASE DE DATOS " + e);
            System.out.println("error al insertar " + e);
        }

    }

    public void mostrar(JTable tableCursos) {
        clsConexion objco = new clsConexion();
        DefaultTableModel modelo = new DefaultTableModel();
        TableRowSorter<TableModel> OrdenarTabla = new TableRowSorter<TableModel>(modelo);
        tableCursos.setRowSorter(OrdenarTabla);

        String sql = "";
        modelo.addColumn("ID_CURSOS");
        modelo.addColumn("NOMBRE");
        modelo.addColumn("DESCRIPCION");

        sql = "SELECT ID_curso,nombre,Descripcion\n"
                + "FROM Cursos;";

        String[] datos = new String[3];
        Statement st;

        try {
            st = objco.conexion().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);

                modelo.addRow(datos);
            }
            tableCursos.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR AL MOSTRAR LOS DATOS DE LA BASE DE DATOS " + e);
            System.out.println("error al consultar ala base de datos " + e);
        }
    }

    public void modificar(JTable tableCursos, JTextField txtIdCurso, JTextField txtNombre,
            JTextArea txtDescripcion) {

        try {
            int fila = tableCursos.getSelectedRow();
            if (fila > 0) {
                txtIdCurso.setText((String) (tableCursos.getValueAt(fila, 0)));
                txtNombre.setText((String) (tableCursos.getValueAt(fila, 1)));
                txtDescripcion.setText((String) (tableCursos.getValueAt(fila, 2)));

            } else {
                JOptionPane.showMessageDialog(null, "FILA NO SELECIONADA");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR " + e);
        }
    }

    public void modificarDato(JTextField txtIdCurso, JTextField txtNombre,
            JTextArea txtDescripcion) {

        setId_curso(Integer.parseInt(txtIdCurso.getText()));
        setNombre(txtNombre.getText());
        setDescripcion(txtDescripcion.getText());

        clsConexion co = new clsConexion();

        String consulta = "UPDATE Cursos SET Nombre=?,Descripcion=?\n"
                + "WHERE ID_curso=?";

        try {
            CallableStatement cs = co.conexion().prepareCall(consulta);

            cs.setString(1, getNombre());
            cs.setString(2, getDescripcion());

            cs.setInt(3, getId_curso());
            cs.execute();
            JOptionPane.showMessageDialog(null, "SE ACUALIZO CORRECTAMENTE");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            System.out.print("error " + e);
        }
    }

    public void eliminar(JTextField txtIdCurso) {
        setId_curso(Integer.parseInt(txtIdCurso.getText()));
        clsConexion co = new clsConexion();

        String sql = "DELETE FROM Cursos WHERE Cursos.ID_curso =?";

        try {
            CallableStatement cs = co.conexion().prepareCall(sql);
            cs.setInt(1, getId_curso());
            cs.execute();

            JOptionPane.showMessageDialog(null, "SE BORRO EXITOSAMENTE");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            System.out.println("ERRO " + e);
        }

    }

    public void limpiar(JTextField txtIdCurso, JTextField txtNombre,
            JTextArea txtDescripcion) {

        txtIdCurso.setText("");
        txtNombre.setText("");
        txtDescripcion.setText("");

    }
}
