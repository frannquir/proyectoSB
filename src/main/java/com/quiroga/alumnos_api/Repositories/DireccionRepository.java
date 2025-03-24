package com.quiroga.alumnos_api.Repositories;



import com.quiroga.alumnos_api.Connection.ConexionSQLite;
import com.quiroga.alumnos_api.Entities.AlumnosEntity;
import com.quiroga.alumnos_api.Entities.DireccionEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@org.springframework.stereotype.Repository

public class DireccionRepository implements Repository<DireccionEntity> {
    /* Comenté este metodo porque al usar SpringBoot la anotación @Repository ya establece el punto
    de acceso global de la instancia.
    private static DireccionRepository instance;
    public DireccionRepository() {} */

    /**
     * Esta instancia de DireccionRepository es global, utilizando el Patron Singleton, estableciendo
     * un punto de acceso global.
     *
     * @return Retorna la instancia.
     *
     */
//    public static DireccionRepository getInstance() {
//        if (instance == null) instance = new DireccionRepository();
//        return instance;
//    }

    /**
     * Método para guardar Direcciones en la base de datos.
     *
     * @param entity
     * @throws SQLException
     */

    @Override
    public void save(DireccionEntity entity) throws SQLException {
        // La conexion se cierra sola porque uso try-with-resources.
        try (Connection connection = ConexionSQLite.getConnection();
             PreparedStatement ps = connection.prepareStatement
                     ("INSERT INTO direcciones (calle, altura, alumno_id) VALUES (?, ?, ?)")) {
            // Establezco valores para la consulta
            ps.setString(1, entity.getCalle());
            ps.setInt(2, entity.getAltura());
            ps.setInt(3, entity.getAlumnoId());
            // Ejecuto la actualizacion.
            ps.executeUpdate();
            // Obtengo la id generada de la direccion
            entity.setId(PreparedStatement.RETURN_GENERATED_KEYS);
            }
        }

    /**
     * Método para eliminar una direccion
     * Llamo al método deleteByID()
     *
     * @param entity
     * @throws SQLException
     */
    @Override
    public void delete(DireccionEntity entity) throws SQLException {
        deleteByID(entity.getId());
    }

    /**
     * Método para eliminar una direccion segun su ID
     *
     * @param id de la direccion a eliminar.
     * @throws SQLException No se pudo conectar con la base de datos.
     */

    @Override
    public void deleteByID(int id) throws SQLException {
        try (Connection connection = ConexionSQLite.getConnection();
             PreparedStatement ps = connection.prepareStatement
                     ("DELETE FROM direcciones WHERE id = ?")) {
            // Establezco valores para la consulta.
            ps.setInt(1, id);
            // Ejecuto la actualizacion.
            ps.executeUpdate();
        }
    }

    /**
     * Método para encontrar una direccion segun su ID
     *
     * @param id de la direccion a encontrar
     * @return Optional<DireccionEntity> Retorna el objeto DireccionEntity envuelto en un Optional por si no se encontró.
     * @throws SQLException No se pudo conectar con la base de datos.
     */
    @Override
    public Optional<DireccionEntity> findByID(int id) throws SQLException {
        DireccionEntity direccion = null;
        try (Connection connection = ConexionSQLite.getConnection();
             PreparedStatement ps = connection.prepareStatement
                     ("SELECT * FROM direcciones WHERE id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    direccion = new DireccionEntity();
                    direccion.setAltura(rs.getInt("altura"));
                    direccion.setCalle(rs.getString("calle"));
                    direccion.setAlumnoId(rs.getInt("alumno_id"));
                }
            }
        }
        return Optional.ofNullable(direccion);
    }

    /**
     * Método para contar Direcciones
     *
     * @return Retorna cantidad de direcciones guardadas
     * @throws SQLException No se pudo conectar con la base de datos
     */
    @Override
    public int count() throws SQLException {
        int count = 0;
        try (Connection connection = ConexionSQLite.getConnection();
             // Podria usar un Statement, seria inseguro.
             PreparedStatement ps = connection.prepareStatement
                     ("SELECT sum(*) FROM direcciones");
             ResultSet rs = ps.executeQuery()) {
            if (rs.next())
                count = rs.getInt(1);
        }
        return count;
    }
    public List<DireccionEntity> findAll () throws SQLException{
        List<DireccionEntity> direcciones = new ArrayList<>();
        try (Connection connection = ConexionSQLite.getConnection();
        PreparedStatement ps = connection.prepareStatement
                ("SELECT * FROM direcciones")) {
            try(ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    var direccion = new DireccionEntity();
                    direccion.setId(rs.getInt("id"));
                    direccion.setCalle(rs.getString("calle"));
                    direccion.setAltura(rs.getInt("altura"));
                    direccion.setAlumnoId(rs.getInt("alumno_id"));
                    direcciones.add(direccion);
                }
            }
        }
        return direcciones;
    }

    public void updateDireccion(int id, DireccionEntity direccion) throws SQLException {

        try (Connection connection = ConexionSQLite.getConnection();
             PreparedStatement ps = connection.prepareStatement
                     ("UPDATE direcciones SET calle = ?, altura = ?, alumno_id = ? WHERE id = ?")) {
            ps.setString(1, direccion.getCalle());
            ps.setInt(2, direccion.getAltura());
            ps.setInt(3, direccion.getAlumnoId());
            ps.setInt(4, id);
            ps.executeUpdate();
        }
    }
}

