package com.quiroga.alumnos_api.Service;

import com.quiroga.alumnos_api.Entities.AlumnosEntity;
import com.quiroga.alumnos_api.Entities.DireccionEntity;
import com.quiroga.alumnos_api.Exceptions.*;
import com.quiroga.alumnos_api.Repositories.DireccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class DireccionService {
    // Autowired convierte mi alumnosRepository en la instancia global, ventaja de SpringBoot, las anotaciones.
    @Autowired
    private DireccionRepository direccionRepository;

    public List<DireccionEntity> findAll() throws SQLException {
        return direccionRepository.findAll();
    }

    public DireccionEntity findByID(int id) throws SQLException {
        return direccionRepository.findByID(id).orElseThrow(() -> new DireccionNoEncontradaException("No se encontro la direccion"));
    }

    public void save(DireccionEntity direccion) throws SQLException {
        direccionRepository.save(direccion);
    }

    public void update(int id, DireccionEntity direccion) throws SQLException {
        direccionRepository.updateDireccion(id, direccion);
    }

    public void delete(int id) throws SQLException {
        direccionRepository.deleteByID(id);
    }

}
