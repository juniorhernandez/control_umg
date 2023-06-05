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

public class clsAsignacionProfesores {

    public int id_asignacionPrfesor;
    public int id_profesor;
    public String nombre_Profesor;
    public String Apellido_Profesor;
    public int id_curso;
    public String nombre_Curso;

    public clsAsignacionProfesores() {
    }

    public clsAsignacionProfesores(int id_asignacionPrfesor, int id_profesor, String nombre_Profesor, String Apellido_Profesor, int id_curso, String nombre_Curso) {
        this.id_asignacionPrfesor = id_asignacionPrfesor;
        this.id_profesor = id_profesor;
        this.nombre_Profesor = nombre_Profesor;
        this.Apellido_Profesor = Apellido_Profesor;
        this.id_curso = id_curso;
        this.nombre_Curso = nombre_Curso;
    }

    public int getId_asignacionPrfesor() {
        return id_asignacionPrfesor;
    }

    public void setId_asignacionPrfesor(int id_asignacionPrfesor) {
        this.id_asignacionPrfesor = id_asignacionPrfesor;
    }

    public int getId_profesor() {
        return id_profesor;
    }

    public void setId_profesor(int id_profesor) {
        this.id_profesor = id_profesor;
    }

    public String getNombre_Profesor() {
        return nombre_Profesor;
    }

    public void setNombre_Profesor(String nombre_Profesor) {
        this.nombre_Profesor = nombre_Profesor;
    }

    public String getApellido_Profesor() {
        return Apellido_Profesor;
    }

    public void setApellido_Profesor(String Apellido_Profesor) {
        this.Apellido_Profesor = Apellido_Profesor;
    }

    public int getId_curso() {
        return id_curso;
    }

    public void setId_curso(int id_curso) {
        this.id_curso = id_curso;
    }

    public String getNombre_Curso() {
        return nombre_Curso;
    }

    public void setNombre_Curso(String nombre_Curso) {
        this.nombre_Curso = nombre_Curso;
    }

    public void insertar(JTextField txtIdAsignacionProfesor, JTextField txtIdProfesor,
            JTextField txtIdCurso) {

        setId_asignacionPrfesor(Integer.parseInt(txtIdAsignacionProfesor.getText()));
        setId_profesor(Integer.parseInt(txtIdProfesor.getText()));
        setId_curso(Integer.parseInt(txtIdCurso.getText()));

        clsConexion objco = new clsConexion();

        String consulta = "INSERT INTO Asignaciones_profesores (ID_asignacion,ID_profesor,ID_curso)\n"
                + "VALUES (?,?,?);";
        try {
            CallableStatement cs = objco.conexion().prepareCall(consulta);
            cs.setInt(1, getId_asignacionPrfesor());
            cs.setInt(2, getId_profesor());
            cs.setInt(3, getId_curso());

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
        modelo.addColumn("ID ASIGNACION PROFESOR");
        modelo.addColumn("ID PROFESOR");
        modelo.addColumn("NOMBRE");
        modelo.addColumn("APELLIDO");
        modelo.addColumn("ID CURSO");
        modelo.addColumn("NOMBRE CURSO");

        sql = "SELECT Asignaciones_profesores.ID_asignacion, Profesores.ID_profesor, Profesores.nombre, Profesores.apellido, Cursos.ID_curso, Cursos.nombre\n"
                + "FROM Asignaciones_profesores\n"
                + "JOIN Profesores ON Asignaciones_profesores.ID_profesor = Profesores.ID_profesor\n"
                + "JOIN Cursos ON Asignaciones_profesores.ID_curso = Cursos.ID_curso;";

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
            tableEstudiantes.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR AL MOSTRAR LOS DATOS DE LA BASE DE DATOS " + e);
            System.out.println("error al consultar ala base de datos " + e);
        }
    }

    public void modificar(JTable tableAsignacionProfesores, JTextField txtIdAsignacionProfesor,
            JTextField txtIdProfesor, JTextField txtNombre,
            JTextField txtApellido, JTextField txtIdCurso, JTextField txtNombreCurso) {

        try {
            int fila = tableAsignacionProfesores.getSelectedRow();
            if (fila > 0) {
                txtIdAsignacionProfesor.setText((String) (tableAsignacionProfesores.getValueAt(fila, 0)));
                txtIdProfesor.setText((String) (tableAsignacionProfesores.getValueAt(fila, 1)));
                txtNombre.setText((String) (tableAsignacionProfesores.getValueAt(fila, 2)));
                txtApellido.setText((String) (tableAsignacionProfesores.getValueAt(fila, 3)));
                txtIdCurso.setText((String) (tableAsignacionProfesores.getValueAt(fila, 4)));
                txtNombreCurso.setText((String) (tableAsignacionProfesores.getValueAt(fila, 5)));

            } else {
                JOptionPane.showMessageDialog(null, "FILA NO SELECIONADA");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR " + e);
        }
    }

    public void modificarDato(JTextField txtIdAsignacionProfesor,
            JTextField txtIdProfesor, JTextField txtIdCurso) {

        setId_asignacionPrfesor(Integer.parseInt(txtIdAsignacionProfesor.getText()));
        setId_profesor(Integer.parseInt(txtIdProfesor.getText()));
        setId_curso(Integer.parseInt(txtIdCurso.getText()));

        clsConexion co = new clsConexion();

        String consulta = "UPDATE Asignaciones_profesores SET ID_profesor=?,ID_curso=?\n"
                + "WHERE ID_asignacion=?";

        try {
            CallableStatement cs = co.conexion().prepareCall(consulta);

            cs.setInt(1, getId_profesor());
            cs.setInt(2, getId_curso());

            cs.setInt(3, getId_asignacionPrfesor());
            cs.execute();
            JOptionPane.showMessageDialog(null, "SE ACUALIZO CORRECTAMENTE");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            System.out.print("error " + e);
        }
    }

    public void eliminar(JTextField txtIdAsignacionProfesor) {
        setId_asignacionPrfesor(Integer.parseInt(txtIdAsignacionProfesor.getText()));
        clsConexion co = new clsConexion();

        String sql = "DELETE FROM Asignaciones_profesores WHERE Asignaciones_profesores.ID_asignacion =?";

        try {
            CallableStatement cs = co.conexion().prepareCall(sql);
            cs.setInt(1, getId_asignacionPrfesor());
            cs.execute();

            JOptionPane.showMessageDialog(null, "SE BORRO EXITOSAMENTE");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            System.out.println("ERRO " + e);
        }

    }

    public void limpiar(JTextField txtIdAsignacionProfesor, JTextField txtIdProfesor,
            JTextField txtNombre, JTextField txtApellido,
            JTextField txtIdCurso, JTextField txtNombreCurso) {

        txtIdAsignacionProfesor.setText("");
        txtIdProfesor.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtIdCurso.setText("");
        txtNombreCurso.setText("");
    }

    public void selecionarProfesor(JTable tableProfesores,
            JTextField txtIdProfesor, JTextField txtNombre,
            JTextField txtApellido) {

        try {
            int fila = tableProfesores.getSelectedRow();
            if (fila > 0) {
                txtIdProfesor.setText((String) (tableProfesores.getValueAt(fila, 0)));
                txtNombre.setText((String) (tableProfesores.getValueAt(fila, 1)));
                txtApellido.setText((String) (tableProfesores.getValueAt(fila, 2)));

            } else {
                JOptionPane.showMessageDialog(null, "FILA NO SELECIONADA");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR " + e);
        }
    }

    public void selecionarCurso(JTable tableCursos,
            JTextField txtIdCurso, JTextField txtNombreCurso) {

        try {
            int fila = tableCursos.getSelectedRow();
            if (fila > 0) {
                txtIdCurso.setText((String) (tableCursos.getValueAt(fila, 0)));
                txtNombreCurso.setText((String) (tableCursos.getValueAt(fila, 1)));
                ;

            } else {
                JOptionPane.showMessageDialog(null, "FILA NO SELECIONADA");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR " + e);
        }
    }
}
