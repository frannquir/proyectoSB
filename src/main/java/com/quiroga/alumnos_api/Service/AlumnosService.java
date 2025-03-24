package com.quiroga.alumnos_api.Service;

import com.quiroga.alumnos_api.Entities.AlumnosEntity;
import com.quiroga.alumnos_api.Entities.DireccionEntity;
import com.quiroga.alumnos_api.Exceptions.AlumnoNoEncontradoException;
import com.quiroga.alumnos_api.Repositories.AlumnosRepository;
import com.quiroga.alumnos_api.Repositories.DireccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class AlumnosService {
    // Autowired convierte mi alumnosRepository en la instancia global, ventaja de SpringBoot, las anotaciones.
    @Autowired
    private AlumnosRepository alumnosRepository;
//    public AlumnosService() {
//        alumnosRepository = AlumnosRepository.getInstance();
//    }

    public List<AlumnosEntity> findAll() throws SQLException {
        return alumnosRepository.findAll();
    }

    public AlumnosEntity findByID(int id) throws SQLException, AlumnoNoEncontradoException {
        return alumnosRepository.findByID(id).orElseThrow(() -> new AlumnoNoEncontradoException("No se encontro el alumno"));
    }

    public void save(AlumnosEntity alumno) throws SQLException {
        alumnosRepository.save(alumno);
    }

    public void update(int id, AlumnosEntity alumno) throws SQLException {
        alumnosRepository.updateAlumno(id, alumno);
    }

    public void delete(int id) throws SQLException {
        alumnosRepository.deleteByID(id);
    }
}
