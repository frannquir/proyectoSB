package com.quiroga.alumnos_api.Repositories;


import com.quiroga.alumnos_api.Connection.ConexionSQLite;
import com.quiroga.alumnos_api.Entities.AlumnosEntity;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Repository
public class AlumnosRepository implements Repository<AlumnosEntity> {

/*      Comenté algunos metodo porque al usar SpringBoot la anotación @Repository ya establece el punto
     *  de acceso global de la instancia.
     *  private static AlumnosRepository instance;
        public AlumnosRepository() {}
 */

    /**
     * Esta instancia de AlumnosRepository es global, utilizando el Patron Singleton, estableciendo
     * un punto de acceso global.
     *
     * @return Retorna la instancia.
     */
//    public static AlumnosRepository getInstance() {
//        if (instance == null) instance = new AlumnosRepository();
//        return instance;
//    }

    /**
     * Método para cargar alumnos en la base de datos.
     *
     * @param entity Alumno a cargar
     * @throws SQLException No se pudo conectar a la base de datos.
     */

    @Override
    public void save(AlumnosEntity entity) throws SQLException {
        // La conexion se cierra sola porque uso try-with-resources.
        try (Connection connection = ConexionSQLite.getConnection();
             PreparedStatement ps = connection.prepareStatement
                     ("INSERT INTO alumnos (nombre, apellido, edad, email) VALUES (?, ?, ?, ?)")) {
            // Establezco valores para la consulta
            ps.setString(1, entity.getNombre());
            ps.setString(2, entity.getApellido());
            ps.setInt(3, entity.getEdad());
            ps.setString(4, entity.getEmail());
            // Ejecuto la consulta
            ps.executeUpdate();
            // Generated keys es la id que se le genero al alumno.
            // Lo hice ya que probando en Postman la id siempre devolvia 0, hasta ver la lista completa.
            entity.setId(PreparedStatement.RETURN_GENERATED_KEYS);
        }
    }

    /**
     * Método para eliminar un alumno en la base de datos. No lo uso ya que por ID es mas rapido.
     * Llamo al método deleteByID()
     *
     * @param entity Alumno a eliminar.
     * @throws SQLException No se pudo conectar con la base de datos.
     */
    @Override
    public void delete(AlumnosEntity entity) throws SQLException {
        deleteByID(entity.getId());
    }

    /**
     * Método para eliminar a un alumno en la base de datos por ID.
     *
     * @param id ID del Alumno a eliminar.
     * @throws SQLException No se pudo conectar con la base de datos.
     */
    @Override
    public void deleteByID(int id) throws SQLException {
        try (Connection connection = ConexionSQLite.getConnection();
             PreparedStatement ps = connection.prepareStatement
                     ("DELETE FROM alumnos WHERE id = ?")) {
            // Establezco valores para la consulta.
            ps.setInt(1, id);
            // Ejecuto la actualizacion.
            ps.executeUpdate();
        }
    }

    /**
     * Método para buscar un Alumno por la ID.
     *
     * @param id ID del Alumno a buscar.
     * @return Retorna un Optional de Alumno, por si no fue encontrado.
     * @throws SQLException No se pudo conectar con la base de datos.
     */

    @Override
    public Optional<AlumnosEntity> findByID(int id) throws SQLException { // Retorno un optional por si no se encontro el alumno.
        AlumnosEntity alumno = null;
        try (Connection connection = ConexionSQLite.getConnection();
             PreparedStatement ps = connection.prepareStatement
                     ("SELECT * FROM alumnos WHERE id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    alumno = new AlumnosEntity();
                    alumno.setId(rs.getInt("id"));
                    alumno.setNombre(rs.getString("nombre"));
                    alumno.setApellido(rs.getString("apellido"));
                    alumno.setEdad(rs.getInt("edad"));
                    alumno.setEmail(rs.getString("email"));

                }
            }
        }
        return Optional.ofNullable(alumno);
    }

    /**
     * Método para contar Alumnos
     *
     * @return Retorna cantidad de alumnos
     * @throws SQLException No se pudo conectar con la base de datos.
     */
    @Override
    public int count() throws SQLException {
        int count = 0;
        try (Connection connection = ConexionSQLite.getConnection();
             PreparedStatement ps = connection.prepareStatement
                     ("SELECT count(*) FROM alumnos");
             // Como no necesito configurar la consulta, podria usar un simple Statement. De esta manera, no arriesgo SQL Injections.
             ResultSet rs = ps.executeQuery()) {
            if (rs.next())
                count = rs.getInt(1);
        }
        return count;
    }

    public List<AlumnosEntity> findAll() throws SQLException {
        List<AlumnosEntity> alumnos = new ArrayList<>();
        try (Connection connection = ConexionSQLite.getConnection();
             PreparedStatement ps = connection.prepareStatement
                     ("SELECT * FROM alumnos")) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    var alumno = new AlumnosEntity();
                    alumno.setId(rs.getInt("id"));
                    alumno.setNombre(rs.getString("nombre"));
                    alumno.setApellido(rs.getString("apellido"));
                    alumno.setEdad(rs.getInt("edad"));
                    alumno.setEmail(rs.getString("email"));
                    alumnos.add(alumno);
                }
            }
        }
        return alumnos;
    }


    public void updateAlumno(int id, AlumnosEntity alumno) throws SQLException {

        try (Connection connection = ConexionSQLite.getConnection();
             PreparedStatement ps = connection.prepareStatement
                     ("UPDATE alumnos SET nombre = ?, apellido = ?, edad = ?, email = ? WHERE id = ?")) {
            ps.setString(1, alumno.getNombre());
            ps.setString(2, alumno.getApellido());
            ps.setInt(3, alumno.getEdad());
            ps.setString(4, alumno.getEmail());
            ps.setInt(5, id);
            ps.executeUpdate();
        }
    }
}
