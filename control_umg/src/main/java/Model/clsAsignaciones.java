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

public class clsAsignaciones {

    public int id_asignacion;
    public int id_estudiante;
    public String nombre;
    public String apellido;
    public int id_curso;
    public String nombreCurso;

    public clsAsignaciones() {
    }

    public clsAsignaciones(int id_asignacion, int id_estudiante, String nombre, String apellido, int id_curso, String nombreCurso) {
        this.id_asignacion = id_asignacion;
        this.id_estudiante = id_estudiante;
        this.nombre = nombre;
        this.apellido = apellido;
        this.id_curso = id_curso;
        this.nombreCurso = nombreCurso;
    }

    public int getId_asignacion() {
        return id_asignacion;
    }

    public void setId_asignacion(int id_asignacion) {
        this.id_asignacion = id_asignacion;
    }

    public int getId_estudiante() {
        return id_estudiante;
    }

    public void setId_estudiante(int id_estudiante) {
        this.id_estudiante = id_estudiante;
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

    public int getId_curso() {
        return id_curso;
    }

    public void setId_curso(int id_curso) {
        this.id_curso = id_curso;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }

    public void insertar(JTextField txtIdAsignacionCurso, JTextField txtIdEstudiante,
            JTextField txtIdCurso) {

        setId_asignacion(Integer.parseInt(txtIdAsignacionCurso.getText()));
        setId_estudiante(Integer.parseInt(txtIdEstudiante.getText()));
        setId_curso(Integer.parseInt(txtIdCurso.getText()));

        clsConexion objco = new clsConexion();

        String consulta = "INSERT INTO Asignaciones_cursos (ID_asignacion,ID_estudiante,ID_curso)\n"
                + "VALUES (?,?,?);";
        try {
            CallableStatement cs = objco.conexion().prepareCall(consulta);
            cs.setInt(1, getId_asignacion());
            cs.setInt(2, getId_estudiante());
            cs.setInt(3, getId_curso());

            cs.execute();

            JOptionPane.showMessageDialog(null, "SE A INGRESADO EXITOSAMENTE");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR AL INGRESAR DATO A LA BASE DE DATOS " + e);
            System.out.println("error al insertar " + e);
        }

    }

    public void mostrar(JTable tableAsignacionCursos) {
        clsConexion objco = new clsConexion();
        DefaultTableModel modelo = new DefaultTableModel();
        TableRowSorter<TableModel> OrdenarTabla = new TableRowSorter<TableModel>(modelo);
        tableAsignacionCursos.setRowSorter(OrdenarTabla);

        String sql = "";
        modelo.addColumn("ID ASIGNACION");
        modelo.addColumn("ID ESTUDIANTE");
        modelo.addColumn("NOMBRE");
        modelo.addColumn("APELLIDO");
        modelo.addColumn("ID CURSO");
        modelo.addColumn("NOMBRE CURSO");

        sql = "SELECT Asignaciones_cursos.ID_asignacion, Estudiantes.ID_estudiante, Estudiantes.Nombre, Estudiantes.Apellido, Cursos.ID_curso, Cursos.Nombre\n"
                + "FROM Estudiantes\n"
                + "JOIN Asignaciones_cursos ON Estudiantes.ID_estudiante = Asignaciones_cursos.ID_estudiante\n"
                + "JOIN Cursos ON Asignaciones_cursos.ID_curso = Cursos.ID_curso;";

        String[] datos = new String[6];
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
                datos[5] = rs.getString(6);

                modelo.addRow(datos);
            }
            tableAsignacionCursos.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR AL MOSTRAR LOS DATOS DE LA BASE DE DATOS " + e);
            System.out.println("error al consultar ala base de datos " + e);
        }
    }

    public void modificar(JTable tableAsignacionCursos, JTextField txtIdAsignacionCurso,
            JTextField txtIdEstudiante, JTextField txtNombre, JTextField txtApellido,
            JTextField txtIdCurso, JTextField txtNombreCurso) {

        try {
            int fila = tableAsignacionCursos.getSelectedRow();
            if (fila > 0) {
                txtIdAsignacionCurso.setText((String) (tableAsignacionCursos.getValueAt(fila, 0)));
                txtIdEstudiante.setText((String) (tableAsignacionCursos.getValueAt(fila, 1)));
                txtNombre.setText((String) (tableAsignacionCursos.getValueAt(fila, 2)));
                txtApellido.setText((String) (tableAsignacionCursos.getValueAt(fila, 3)));
                txtIdCurso.setText((String) (tableAsignacionCursos.getValueAt(fila, 4)));
                txtNombreCurso.setText((String) (tableAsignacionCursos.getValueAt(fila, 5)));

            } else {
                JOptionPane.showMessageDialog(null, "FILA NO SELECIONADA");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR " + e);
        }
    }

    public void modificarDato(JTextField txtIdAsignacionCurso,
            JTextField txtIdEstudiante,
            JTextField txtIdCurso) {

        setId_asignacion(Integer.parseInt(txtIdAsignacionCurso.getText()));
        setId_estudiante(Integer.parseInt(txtIdEstudiante.getText()));
        setId_curso(Integer.parseInt(txtIdCurso.getText()));

        clsConexion co = new clsConexion();

        String consulta = "UPDATE Asignaciones_cursos SET ID_estudiante=?,ID_curso=?\n"
                + "WHERE ID_asignacion=?";

        try {
            CallableStatement cs = co.conexion().prepareCall(consulta);

            cs.setInt(1, getId_estudiante());
            cs.setInt(2, getId_curso());

            cs.setInt(3, getId_asignacion());
            cs.execute();
            JOptionPane.showMessageDialog(null, "SE ACUALIZO CORRECTAMENTE");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            System.out.print("error " + e);
        }
    }

    public void eliminar(JTextField txtIdAsignacionCurso) {
        setId_asignacion(Integer.parseInt(txtIdAsignacionCurso.getText()));
        clsConexion co = new clsConexion();

        String sql = "DELETE FROM Asignaciones_cursos WHERE Asignaciones_cursos.ID_asignacion =?";

        try {
            CallableStatement cs = co.conexion().prepareCall(sql);
            cs.setInt(1, getId_asignacion());
            cs.execute();

            JOptionPane.showMessageDialog(null, "SE BORRO EXITOSAMENTE");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            System.out.println("ERROR " + e);
        }

    }

    public void limpiar(JTextField txtIdAsignacionCurso, JTextField txtIdEstudiante, JTextField txtNombre,
            JTextField txtApellido, JTextField txtIdCurso, JTextField txtNombreCurso) {

        txtIdAsignacionCurso.setText("");
        txtIdEstudiante.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtIdCurso.setText("");
        txtNombreCurso.setText("");

    }

    public void selecionarEstudiante(JTable tableEstudiantes, JTextField txtIdEstudiante,
            JTextField txtNombre, JTextField txtApellido) {

        try {
            int fila = tableEstudiantes.getSelectedRow();
            if (fila > 0) {
                txtIdEstudiante.setText((String) (tableEstudiantes.getValueAt(fila, 0)));
                txtNombre.setText((String) (tableEstudiantes.getValueAt(fila, 1)));
                txtApellido.setText((String) (tableEstudiantes.getValueAt(fila, 2)));

            } else {
                JOptionPane.showMessageDialog(null, "FILA NO SELECIONADA");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR " + e);
        }
    }

    public void selecionarCurso(JTable tableCursos, JTextField txtIdCurso, JTextField txtNombreCurso) {

        try {
            int fila = tableCursos.getSelectedRow();
            if (fila > 0) {
                txtIdCurso.setText((String) (tableCursos.getValueAt(fila, 0)));
                txtNombreCurso.setText((String) (tableCursos.getValueAt(fila, 1)));

            } else {
                JOptionPane.showMessageDialog(null, "FILA NO SELECIONADA");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR " + e);
        }
    }
}
